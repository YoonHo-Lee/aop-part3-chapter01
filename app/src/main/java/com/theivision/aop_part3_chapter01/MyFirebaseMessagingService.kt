package com.theivision.aop_part3_chapter01

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService :FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        // 새로운 토큰으로 바뀔때마다 실행됨.
        // 새로운 토큰이 발행되면, 서버에 새로운 토큰을 알려줘야함.
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // Firebase에서 메시지를 수신할때마다 실행됨.

        // 채널 생성 및 등록
        // 수신쪽 담당
        createNotificationChannel()

        val title = remoteMessage.data["title"]
        val msg = remoteMessage.data["msg"]

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val fullScreenPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        // Notification 생성
        // 수신받은 내용을 noti로 띄우는 역할
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)                    // 아이콘
            .setContentTitle(title)                                             // noti 제목
            .setContentText(msg)                                                // noti 내용
            .setPriority(NotificationCompat.PRIORITY_HIGH)                      // 채널을 사용하지 않는 7.1이하에서 사용되는 중요도.
            .setAutoCancel(true)                                                // 알림 클릭시 자동 삭제
            .setFullScreenIntent(fullScreenPendingIntent, true)       // head up noti / pop up screen / 화면 위에 노티 띄워줌.

        NotificationManagerCompat.from(this)
            .notify(1, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        // 안드로이드 버전 8.0이상과 7.1이하를 구분해줘야한다. (7.1 이하는 채널이 없으므로 신경 안써도됨)
        // 둘의 알림 중요도 관리 방법이 조금 다르다.
        // https://developer.android.com/training/notify-user/channels 참고

        // 채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.description = CHANNEL_DESC


            // 채널 등록
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }

    }

    companion object {
        private const val CHANNEL_NAME = "Emoji party"
        private const val CHANNEL_DESC = "이모지 파티 채널"
        private const val CHANNEL_ID = "channel id"
    }
}