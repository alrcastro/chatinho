package com.quickblox.sample.chat.core;

/**
 * Created by andre.castro on 26/08/14.
 */
public class ChatControl {

    private static SingleChat singleChat;

    private static RoomChat roomChat;

    public static SingleChat getSingleChatInstance() {

        if (singleChat == null)
            singleChat = new SingleChat();

        return singleChat;
    }

    public static RoomChat getRoomChatInstance() {

        if (roomChat == null)
            roomChat = new RoomChat();

        return roomChat;
    }

}
