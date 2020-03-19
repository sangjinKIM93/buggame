package com.sangjin.buggame.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BugDao {
    @Query("SELECT * FROM Bug")
    LiveData<List<Bug>> getAll();

    @Query("SELECT * FROM Bug")
    List<Bug> getAllList();

    @Insert
    void insert(Bug bug);

    @Update
    void update(Bug todo);

    @Delete
    void delete(Bug todo);
}
