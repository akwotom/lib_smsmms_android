package com.afkanerd.smswithoutborders_libsmsmms.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.afkanerd.smswithoutborders_libsmsmms.data.Cryptography.getDatabasePassword
import com.afkanerd.smswithoutborders_libsmsmms.data.dao.ConversationsDao
import com.afkanerd.smswithoutborders_libsmsmms.data.dao.ThreadsDao
import com.afkanerd.smswithoutborders_libsmsmms.data.entities.Conversations
import com.afkanerd.smswithoutborders_libsmsmms.data.entities.Threads
import com.afkanerd.smswithoutborders_libsmsmms.extensions.context.eraseSettings
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import kotlin.concurrent.Volatile
import kotlin.jvm.java


@Database(
    entities = [
        Conversations::class,
        Threads::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 2, to = 3)
    ]
)
abstract class DatabaseImpl : RoomDatabase() {
    abstract fun conversationsDao(): ConversationsDao?
    abstract fun threadsDao(): ThreadsDao?

    init {
        System.loadLibrary("sqlcipher")
    }

    companion object {
        @Volatile
        private var datastore: DatabaseImpl? = null
        private var databaseName: String = "afkanerd.smswithoutborders.libsmsmms.db"
        private var dbKeystoreAlias: String = "afkanerd.smswithoutborders.sms_mms_keystore_alias"

        @Synchronized
        fun setDatabaseName(databaseName: String) {
            this.databaseName = databaseName
        }

        @Synchronized
        fun getDatabaseImpl(context: Context): DatabaseImpl {
            if (datastore == null) {
                create(context)
            }
            return datastore!!
        }

        private fun create(context: Context) {
            getDatabasePassword(context, dbKeystoreAlias).use { password ->
                val databaseFile = context.getDatabasePath(databaseName)

                password.useRaw { rawBytes ->
                    datastore = Room.databaseBuilder(
                        context = context.applicationContext,
                        klass = DatabaseImpl::class.java,
                        databaseFile.absolutePath,
                    )
                        .openHelperFactory(SupportOpenHelperFactory(rawBytes))
                        .fallbackToDestructiveMigration(false)
                        .build()
                }
            }
        }

        /**
         * This method erases the database, along with other associated settings.
         */
        fun erase(context: Context) {
            context.eraseSettings()
            java.io.File(databaseName).delete()
        }

        /**
         * This method checks if the database is okay, and readable.
         * Usually, this method is called during the launch of the app,
         * such that if the method returns false, the user would be prompted to erase everything
         * and start all over.
         */
        fun isOkay(context: Context): Boolean {
            try {
                getDatabaseImpl(context)
                return true
            } catch (e: Exception) {
                android.util.Log.d(
                    "DatabaseImpl",
                    "The database is not okay because\n${e}\n${e.stackTrace}"
                )
                return false
            }
        }
    }
}
