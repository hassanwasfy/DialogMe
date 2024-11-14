package com.hassanwasfy.dialogme.ui

data class ButtonConfig(
    val text: String,
    val iconResId: Int? = null,
    val textColor: Int? = null,
    val backgroundColor: Int? = null,
    val onClick: (() -> Unit)? = null
)
