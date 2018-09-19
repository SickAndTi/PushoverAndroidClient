package com.example.user.myapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(version = 2, entities = {Message.class}, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    abstract public MessageDao messageDao();
}