package com.theivision.aop_part3_chapter01

import android.os.Build
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService :FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        // 새로운 토큰으로 바뀔때마다 실행됨.
        // 새로운 토큰이 발행되면, 서버에 새로운 토큰을 알려줘야함.
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // Firebase에서 메시지를 수신할때마다 실행됨.
    }

    private fun createNotificationChannel() {
        // 안드로이드 버전 8.0이상과 7.1이하를 구분해줘야한다.
        // 둘의 알림 중요도 관리 방법이 조금 다르다.
        // https://developer.android.com/training/notify-user/channels 참고
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        }
    }
}