package com.quickblox.sample.chat.model;

import com.lvc.database.EntitiePersistable;
import com.lvc.database.annotation.PrimaryKey;

import java.util.Date;

public class ChatMessage implements EntitiePersistable {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PrimaryKey(autoIncrement = true)
    private Long id = 0L;
	
    private boolean incoming;
    private String text;
    private Date time;

    public void setIncoming(boolean incoming) {
        this.incoming = incoming;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    private String sender;

    public ChatMessage() {


    }

    public ChatMessage(String text, Date time, boolean incoming) {
        this(text, null, time, incoming);
    }

    public ChatMessage(String text, String sender, Date time, boolean incoming) {
        this.text = text;
        this.sender = sender;
        this.time = time;
        this.incoming = incoming;
    }

    public boolean isIncoming() {
        return incoming;
    }

    public String getText() {
        return text;
    }

    public Date getTime() {
        return time;
    }

    public String getSender() {
        return sender;
    }
}
