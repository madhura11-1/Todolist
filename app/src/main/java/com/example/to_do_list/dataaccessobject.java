package com.example.to_do_list;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface dataaccessobject {

    @Insert
    public void writedata(todocard todocard);

    @Delete
    public void deletedata(todocard todocard);

    @Query("select * from TO_DO_TABLE")
    public List<todocard> retrieve();

    @Query("UPDATE TO_DO_TABLE SET status = :status_new WHERE title = :title_new")
    public void update_status(String status_new,String title_new);

}
