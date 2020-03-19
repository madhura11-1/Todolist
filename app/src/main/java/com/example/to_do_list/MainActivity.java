package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends AppCompatActivity implements dialogbox.dialoginterface {

        private ArrayList<todocard> arrayList;
        private RecyclerView recyclerView;
        private TodoAdapter adapter;
        private RecyclerView.LayoutManager layoutManager;
        private TodoAdapter.TodoViewHolder holderv;
        public static database_helper database_helper;

        private ImageView delete;
        private FloatingActionButton floatingActionButton;
        private ExplosionField explosionField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();
        buildrecyclerview();

        delete = findViewById(R.id.delete);
        floatingActionButton = findViewById(R.id.addvalue);
        explosionField = ExplosionField.attach2Window(this);
        database_helper = Room.databaseBuilder(getApplicationContext(),database_helper.class,"databaseforapp").allowMainThreadQueries().build();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          opendialogue();
            }
        });


        List<todocard> retrivelist;
        retrivelist = database_helper.mydao().retrieve();
        for(todocard a : retrivelist)
        {
           if(a.getStatus().equals("Completed"))
           {
               a.setUrl("@drawable/tick");
           }
            arrayList.add(a);
            adapter.notifyDataSetChanged();
        }


    }

    private void buildrecyclerview() {

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new TodoAdapter(arrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListner(new TodoAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                todocard user=arrayList.get(position);
                arrayList.remove(position);
                adapter.notifyDataSetChanged();
                database_helper.mydao().deletedata(user);
            }

            @Override
            public void onStatusCheck(final View v,View view, int position) {
                explosionField.explode(view);

                todocard user = arrayList.get(position);
                String title_new = user.getTitle();
                database_helper.mydao().update_status("Completed",title_new);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageView img = v.findViewById(R.id.correct);
                        img.setVisibility(View.VISIBLE);
                    }
                },1500);

            }
        });
    }


    public void opendialogue()
    {
        dialogbox dialogbox = new dialogbox();
        dialogbox.show(getSupportFragmentManager(),"DIALOGUE");
    }

    @Override
    public void setinactivity(String title, String data, String date, String time) {


            todocard todo = new todocard();
            todo.setTitle(title);
            todo.setData(data);
            todo.setDate(date);
            todo.setTime(time);
            todo.setStatus("Pending");
            arrayList.add(todo);
            adapter.notifyDataSetChanged();
            database_helper.mydao().writedata(todo);
    }
}
