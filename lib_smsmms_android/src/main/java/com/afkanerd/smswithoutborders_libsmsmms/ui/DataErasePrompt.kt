package com.afkanerd.smswithoutborders_libsmsmms.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.afkanerd.smswithoutborders_libsmsmms.data.DatabaseImpl
import com.afkanerd.smswithoutborders_libsmsmms.ui.viewModels.ThreadsViewModel

/**
 * This view shows the user a prompt, asking him to erase all app data.
 * This is usually the effect of corrupted data, especially coming from accidental backups.
 */
@Composable
fun DataErasePrompt(
    threads: ThreadsViewModel,
    context: android.content.Context,
    onEraseComplete: () -> Unit
) {
    val consentActive = remember { mutableStateOf(false) }

    MainPrompt(isBlur = consentActive.value, onAction = { consentActive.value = true })

    if (consentActive.value) ConsentPopup(
        onHide = { consentActive.value = false },
        onAgree = {
            android.util.Log.d("DataErasePrompt", "The user agreeeeed!")
            onEraseComplete()
            DatabaseImpl.erase(context);
            onEraseComplete()
        }
    )


}


/**
 * This subsection of the view is the main area, where the user is explained the situation,
 * and given the action button to erase data.
 */
@Composable
private fun MainPrompt(isBlur: Boolean, onAction: () -> Unit) {
    Column(
        modifier = (if (isBlur) Modifier
            .blur(6.dp)
            .alpha(0.4f) else Modifier).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(
            modifier = Modifier.widthIn(max = 360.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    Icons.Rounded.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )

                Box(modifier = Modifier.width(18.dp))

                Column {
                    Text(
                        text = "Data is corrupt",
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "The app's data is corrupt. Don't worry, any message stored within your system's storage is not harmed. This only concerns app-specific data. If you had previously disabled writing to system message storage, then your messages following that decision would be lost.",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Box(modifier = Modifier.height(20.dp))
            CustomButton(onAction = onAction)
            {
                Text("Erase & restart", fontSize = 18.sp)
            }

        }
    }
}

/**
 * This view serves to ask the user to really consent to loosing all his app data.
 * "Do you really want to do this, dear user?"
 */
@Composable
private fun ConsentPopup(onHide: () -> Unit, onAgree: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Popup(
            onDismissRequest = onHide,
            alignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 300.dp)
                    .background(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(corner = CornerSize(10.dp))
                    )
                    .padding(20.dp)
            ) {
                Text(
                    text = "Confirm Action",
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    "This action would absolutely wipe all app data in a manner that's irreversible. Do you want to continue?",
                    color = MaterialTheme.colorScheme.onBackground
                )
                Box(modifier = Modifier.height(35.dp))
                CustomButton(
                    onAction = onAgree,
                    content = {
                        Text("Yes, Erase", fontSize = 18.sp)
                    }
                )
            }

        }
    }
}

/**
 * This widget renders a custom button adapted for the style of this view
 */
@Composable
private fun CustomButton(onAction: () -> Unit, content: @Composable () -> Unit) {
    Button(
        onClick = onAction,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colorScheme.primary,
        ), modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 6.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(
                    topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 16.dp, topStart = 16.dp
                )
            )
            .padding(start = 4.dp, end = 4.dp), content = {
            content()
        }
    )
}