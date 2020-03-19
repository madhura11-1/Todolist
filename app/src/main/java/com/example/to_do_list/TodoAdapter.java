package com.example.to_do_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private ArrayList<todocard> arrayList1;
    private OnItemClickListner mlistner;

    public interface OnItemClickListner{
        void onItemClick(int position);
        void onStatusCheck(View v,View view,int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner)
    {
        mlistner=listner;
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder{

        public TextView title ,status ,day_date ,data,time;
        public ImageView delete,tick;

        public TodoViewHolder(@NonNull final View itemView, final OnItemClickListner listner) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            day_date = itemView.findViewById(R.id.date_day);
            data = itemView.findViewById(R.id.data);
            delete = itemView.findViewById(R.id.delete);
            time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
            tick = itemView.findViewById(R.id.correct);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listner != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        listner.onItemClick(position);
                    }
                }
            });
            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listner != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                            listner.onStatusCheck(itemView,view,position);
                    }
                }
            });
        }
    }

    public TodoAdapter(ArrayList<todocard> arrayList)
    {
        arrayList1 = arrayList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_card,parent,false);
        TodoViewHolder todo = new TodoViewHolder(v,mlistner);
        return todo;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {

        Calendar c = Calendar.getInstance();
        todocard todocard = arrayList1.get(position);
        String date = todocard.getDate();
        DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        if(date.equals(dateFormat.format(c.getTime())))
            holder.day_date.setText("Today");
        else
            holder.day_date.setText(todocard.getDate());
        holder.title.setText(todocard.getTitle());
        holder.data.setText(todocard.getData());

        holder.time.setText(todocard.getTime());


        if(todocard.getStatus().equals("Completed") && todocard.getUrl().equals("@drawable/tick"))
            holder.tick.setVisibility(View.VISIBLE);
        else
            holder.status.setText("Pending");
    }

    @Override
    public int getItemCount() {
        return arrayList1.size();
    }


}
