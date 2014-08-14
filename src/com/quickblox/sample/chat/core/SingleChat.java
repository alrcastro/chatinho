package com.quickblox.sample.chat.core;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.quickblox.module.chat.QBChatService;
import com.quickblox.module.chat.listeners.ChatMessageListener;
import com.quickblox.module.chat.xmpp.QBPrivateChat;
import com.quickblox.sample.chat.miscellaneous.Constants;
import com.quickblox.sample.chat.model.ChatMessage;
import com.quickblox.sample.chat.ui.activities.ChatActivity;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.packet.ChatStateExtension;

import java.util.Calendar;
import java.util.Random;

public class SingleChat implements Chat, ChatMessageListener {

    public static final String EXTRA_USER_ID = "user_id";
    private static final String COMPOSING = "composing";
    private static final String PAUSED = "paused";
    private ChatActivity chatActivity;
    private QBPrivateChat chat;
    private int companionId;

    public SingleChat(ChatActivity chatActivity) {
        this.chatActivity = chatActivity;
        companionId = chatActivity.getIntent().getIntExtra(EXTRA_USER_ID, 0);
        chat = QBChatService.getInstance().createChat();

        chat.addChatMessageListener(this);
    }

    @Override
    public void sendMessage(String message) throws XMPPException {
        chat.sendMessage(companionId, message);
    }

    @Override
    public void release() {
        chat.removeChatMessageListener(this);
    }

    @Override
    public void processMessage(Message message) {
        final String messageBody = message.getBody();
        // QUando o cara ta digitando

        Object status = message.getExtensions().toArray()[0];

        //if (status.equals(COMPOSING) || status.equals(PAUSED))
        if (status instanceof ChatStateExtension)
            return;

       // ChatStateExtension

        String from = message.getFrom().split("-")[0];

        if (from.equals(String.valueOf(companionId))) {
            // Show message
            chatActivity.showMessage(new ChatMessage(messageBody, Calendar.getInstance().getTime(), true));
        } else {
            createNotification(messageBody);
        }

    }


    private void createNotification(String text) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(chatActivity)
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        .setContentTitle("Chat")
                        .setContentText(text);

        NotificationManager mNotifyMgr =
                (NotificationManager) chatActivity.getSystemService(chatActivity.NOTIFICATION_SERVICE);

        Random r = new Random();
        int notificationId = r.nextInt();

        chatActivity.getIntent().putExtra(Constants.notificationId,notificationId);

        mNotifyMgr.notify(notificationId, mBuilder.getNotification());

        Intent resultIntent = new Intent(chatActivity, ChatActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        chatActivity,
                        0,
                        resultIntent,
                        0
                );

        mBuilder.setContentIntent(resultPendingIntent);
    }

    @Override
    public boolean accept(Message.Type messageType) {
        switch (messageType) {
            case chat:
                return true;
            default:
                return false;
        }
    }
}
