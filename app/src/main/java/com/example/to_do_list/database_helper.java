package com.example.to_do_list;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {todocard.class},version = 1,exportSchema = false)
public abstract class database_helper extends RoomDatabase {
    public abstract dataaccessobject mydao();
}
