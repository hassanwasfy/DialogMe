package com.hassanwasfy.dialogme.dialog

import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hassanwasfy.dialogme.R
import com.hassanwasfy.dialogme.ui.ButtonConfig
import com.hassanwasfy.dialogme.util.EnvironmentUtil


/**
 * A customizable dialog library that supports both XML-based Android projects and Jetpack Compose projects.
 *
 * `DialogMe` provides features such as customizable title, message, background color, and buttons,
 * with support for animations and Compose compatibility.
 *
 * Example Usage:
 *
 * ```kotlin
 * val dialog = DialogMe(this)
 *     .setTitle("Warning!", R.drawable.ic_warning, textColor = Color.RED)
 *     .setMessage("Are you sure you want to proceed?", textColor = Color.DKGRAY)
 *     .setDialogBackgroundColor(Color.LTGRAY)
 *     .addButton("Yes", R.drawable.ic_check, textColor = Color.WHITE, backgroundColor = Color.GREEN) {
 *         Toast.makeText(this, "Yes clicked", Toast.LENGTH_SHORT).show()
 *     }
 *     .addButton("No", R.drawable.ic_close, textColor = Color.WHITE, backgroundColor = Color.RED) {
 *         Toast.makeText(this, "No clicked", Toast.LENGTH_SHORT).show()
 *     }
 *     .build()
 *
 * dialog.show()
 * ```
 *
 * @constructor Creates an instance of `DialogMe` with the provided activity.
 * @param activity The activity context required to display the dialog.
 */
class DialogMe(private val activity: Activity) {

    private val dialog: Dialog = Dialog(activity)
    private var title: String = activity.getString(R.string.dialogme_default_title)
    private var titleIcon: Int? = null
    private var titleTextColor: Int? = null
    private var message: String = activity.getString(R.string.dialogme_default_message)
    private var messageAlignCenter: Boolean = true
    private var messageTextColor: Int? = null
    private var dialogBackgroundColor: Int? = null
    private val buttons: MutableList<ButtonConfig> = mutableListOf()


    init {
        dialog.setContentView(R.layout.dialog_me)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.attributes?.windowAnimations = R.style.DialogMeAnimation
    }

    /**
     * Sets the title of the dialog.
     *
     * @param title The title text to display. Defaults to a predefined string resource.
     * @param iconResId (Optional) The resource ID of the icon to display alongside the title.
     * @param textColor (Optional) The color of the title text.
     * @return The `DialogMe` instance for chaining calls.
     */
    fun setTitle(
        title: String = activity.getString(R.string.dialogme_default_title),
        iconResId: Int? = null,
        textColor: Int? = null
    ): DialogMe {
        val titleText = dialog.findViewById<TextView>(R.id.title_text)
        val titleIcon = dialog.findViewById<ImageView>(R.id.title_icon)
        titleText.text = title
        titleTextColor = textColor
        textColor?.let { titleText.setTextColor(it) }
        if (iconResId != null) {
            titleIcon.setImageResource(iconResId)
            titleIcon.visibility = View.VISIBLE
        } else {
            titleIcon.visibility = View.GONE
        }
        return this
    }

    /**
     * Sets the message content of the dialog.
     *
     * @param message The message text to display. Defaults to a predefined string resource.
     * @param alignCenter Whether to align the message text to the center (default) or start.
     * @param textColor (Optional) The color of the message text.
     * @return The `DialogMe` instance for chaining calls.
     */
    fun setMessage(
        message: String = activity.getString(R.string.dialogme_default_message),
        alignCenter: Boolean = true,
        textColor: Int? = null
    ): DialogMe {
        val messageText = dialog.findViewById<TextView>(R.id.message_text)
        messageText.text = message
        messageText.gravity = if (alignCenter) Gravity.CENTER else Gravity.START
        messageTextColor = textColor
        textColor?.let { messageText.setTextColor(it) }
        return this
    }

    /**
     * Sets the background color of the dialog.
     *
     * @param color The color to set as the dialog's background.
     * @return The `DialogMe` instance for chaining calls.
     */
    fun setDialogBackgroundColor(color: Int): DialogMe {
        val backgroundView = dialog.findViewById<View>(R.id.dialog_background)
        backgroundView?.setBackgroundColor(color)
        return this
    }

    /**
     * Adds a button to the dialog.
     *
     * @param text The button text.
     * @param iconResId (Optional) The resource ID of an icon to display on the button.
     * @param textColor (Optional) The color of the button text.
     * @param backgroundColor (Optional) The background color of the button.
     * @param onClick (Optional) A lambda function to execute when the button is clicked.
     * @return The `DialogMe` instance for chaining calls.
     */
    fun addButton(
        text: String = activity.getString(R.string.dialogme_ok),
        iconResId: Int? = null,
        textColor: Int? = null,
        backgroundColor: Int? = null,
        onClick: (() -> Unit)? = null
    ): DialogMe {
        buttons.add(ButtonConfig(text, iconResId, textColor, backgroundColor, onClick))
        return this
    }

    /**
     * Builds the dialog with the configured properties.
     *
     * @return The `DialogMe` instance for chaining calls.
     */
    fun build(): DialogMe {
        val actionsContainer = dialog.findViewById<LinearLayout>(R.id.actions_container)

        buttons.forEach { buttonConfig ->
            val button = Button(activity).apply {
                this.text = buttonConfig.text
                buttonConfig.iconResId?.let {
                    setCompoundDrawablesWithIntrinsicBounds(it, 0, 0, 0)
                }
                setOnClickListener {
                    buttonConfig.onClick?.invoke()
                    dialog.dismiss()
                }
                buttonConfig.textColor?.let { setTextColor(it) }
                buttonConfig.backgroundColor?.let { setBackgroundColor(it) }
            }
            actionsContainer.addView(button)
        }

        dialogBackgroundColor?.let {
            dialog.findViewById<View>(R.id.dialog_background)?.setBackgroundColor(it)
        }

        return this
    }

    /**
     * Displays the dialog in an XML-based project.
     *
     * @throws IllegalStateException If called in a Compose-based project.
     */
    fun show() {
        if (EnvironmentUtil.isComposeAvailable()) {
            throw IllegalStateException("XML-based DialogMe methods should not be used in Compose projects. Use ShowComposeDialog instead.")
        }
        dialog.show()
    }


    /**
     * Displays the dialog in a Compose-based project.
     *
     * @param onDismiss A lambda function to execute when the dialog is dismissed.
     * @throws IllegalStateException If Compose runtime is not available.
     */
    @Composable
    fun ShowComposeDialog(onDismiss: () -> Unit) {
        if (!EnvironmentUtil.isComposeAvailable()) {
            throw IllegalStateException("Compose runtime not available. Use XML-based DialogMe methods in non-Compose projects.")
        }

        androidx.compose.material.AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    titleIcon?.let {
                        Icon(
                            painter = painterResource(id = it),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp).padding(end = 8.dp)
                        )
                    }
                    Text(
                        text = title,
                        color = titleTextColor?.let { Color(it) } ?: Color.Black,
                        style = MaterialTheme.typography.h6
                    )
                }
            },
            text = {
                Text(
                    text = message,
                    color = messageTextColor?.let { Color(it) } ?: Color.DarkGray,
                    textAlign = if (messageAlignCenter) TextAlign.Center else TextAlign.Start
                )
            },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    buttons.forEach { button ->
                        Button(
                            onClick = {
                                button.onClick?.invoke()
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = button.backgroundColor?.let { Color(it) }
                                    ?: MaterialTheme.colors.primary
                            ),
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                        ) {
                            button.iconResId?.let {
                                Icon(
                                    painter = painterResource(id = it),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp).padding(end = 4.dp)
                                )
                            }
                            Text(
                                text = button.text,
                                color = button.textColor?.let { Color(it) } ?: Color.White
                            )
                        }
                    }
                }
            },
            backgroundColor = dialogBackgroundColor?.let { Color(it) } ?: Color.White
        )
    }

}