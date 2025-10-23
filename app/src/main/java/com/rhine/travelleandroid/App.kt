package com.rhine.travelleandroid

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // FirebaseApp.initializeApp(this)
        // OneSignal.initWithContext(this)
        // Branch.getAutoInstance(this)
    }
    //    private fun oneSignal() {
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
//        OneSignal.initWithContext(this)
//        OneSignal.setAppId(getString(R.string.onesignal_app_id))
//        OneSignal.unsubscribeWhenNotificationsAreDisabled(true)
//        OneSignal.setNotificationOpenedHandler { result ->
//            val notification = result.notification
//
//            if (notification != null) {
//                var action: String? = null
//                var id: String? = null
//
//                action = (notification.additionalData.get("action") as? String) ?: ""
//                id = (notification.additionalData.get("id") as? String) ?: ""
//
//                val temp = NotificationMessage()
//                if (id == "") {
//                    id = action
//                }
//                temp.id = UUID.randomUUID().toString()
//                temp.profileId = id.toString()
//                temp.action = action
//
//                RealmHelper.save(temp)
//            }
//        }
//        OneSignal.setNotificationWillShowInForegroundHandler { notificationReceivedEvent: OSNotificationReceivedEvent ->
//            OneSignal.onesignalLog(
//                OneSignal.LOG_LEVEL.VERBOSE,
//                "NotificationWillShowInForegroundHandler fired!" +
//                        " with notification event: " + notificationReceivedEvent.toString()
//            )
//        }
//    }
}