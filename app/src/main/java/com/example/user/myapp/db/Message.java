package com.example.user.myapp.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Message {
    @PrimaryKey
    public Long id;

    public int status;
    public String request;
    public String messageText;
    public String userKey;

}
