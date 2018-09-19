package com.example.user.myapp.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertMessage(Message message);

    @Query("SELECT * FROM Message ORDER BY id ASC")
    Flowable<List<Message>> getMessages();
}

