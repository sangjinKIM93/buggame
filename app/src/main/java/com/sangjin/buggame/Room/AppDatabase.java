package com.sangjin.buggame.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Bug.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BugDao bugDao();
}
