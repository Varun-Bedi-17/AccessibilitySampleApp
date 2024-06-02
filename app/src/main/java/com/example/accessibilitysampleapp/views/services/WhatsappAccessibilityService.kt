package com.example.accessibilitysampleapp.views.services

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import com.example.accessibilitysampleapp.TAG

class WhatsappAccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            val packageName = event.packageName?.toString() ?: return
            Log.i(TAG, "onAccessibilityEvent: $packageName")

            // Check if the event is related to the WhatsApp package
            if (packageName == "com.whatsapp") {
                // Get SharedPreferences to store if the toast has been shown before
                val sharedPreferences = applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                // Check if the toast has not been shown before
                val isToastShown = sharedPreferences.getBoolean("TOAST_SHOW", false)
                if (!isToastShown) {
                    Toast.makeText(applicationContext, "WhatsApp is opened", Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "Whatsapp open")

                    // Mark the toast as shown and save it in SharedPreferences
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("TOAST_SHOW", true)
                    editor.apply()
                }
            }
        }

    }

    override fun onInterrupt() {
        Log.i(TAG, "onInterrupt: something went wrong")
    }

}