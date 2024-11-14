package com.hassanwasfy.dialogme.util

object EnvironmentUtil {
    fun isComposeAvailable(): Boolean {
        return try {
            Class.forName("androidx.compose.runtime.Composable")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }
}
