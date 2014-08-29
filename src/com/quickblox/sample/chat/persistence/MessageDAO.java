package com.quickblox.sample.chat.persistence;

import android.content.Context;

import com.quickblox.sample.chat.model.ChatMessage;

import java.util.List;

/**
 * Created by andre.castro on 18/08/14.
 */
public class MessageDAO extends BaseDAOChat<ChatMessage> {

    public MessageDAO(Context context) {

        super(context);
    }

    private static volatile MessageDAO instance;

    public static MessageDAO getInstance(Context context) {
        if(instance == null) {
            instance = new MessageDAO(context);
        }

        return instance;
    }

    public List<ChatMessage> getMessageBySenderId(String senderId) {

        return getElements("sender = ?", new String[] { senderId });
    }

    @Override
    public Class<ChatMessage> getEntitieClass() {
        return ChatMessage.class;
    }
}
