package com.example.to_do_list;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class dialogbox extends AppCompatDialogFragment {

    private EditText title, time_box, date_box, data;
    private String day_date;
    private Button datepick,time_pick;
    private dialoginterface dialoginterface;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_file, null);

        builder.setView(view)
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String s1 = title.getText().toString();
                        String s2 = data.getText().toString();
                        String s3 = date_box.getText().toString();
                        String s4 = time_box.getText().toString();
                        if(s1.isEmpty() || s2.isEmpty() || s3.isEmpty() || s4.isEmpty()) {
                            Toast.makeText(getContext(), "Please enter the required data", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        dialoginterface.setinactivity(s1,s2,s3,s4);

                    }
                });

        title = view.findViewById(R.id.title_box);
        date_box = view.findViewById(R.id.date_box);
        time_box = view.findViewById(R.id.time_box);
        data = view.findViewById(R.id.data_box);
        datepick = view.findViewById(R.id.datepick);
        time_pick = view.findViewById(R.id.timepick);

        datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                day_date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                date_box.setText(day_date);
                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });

        time_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String time= sHour + ":" + sMinute;
                                time_box.setText(time);
                            }
                        }, hour, minutes, true);
               timePickerDialog.show();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialoginterface = (dialoginterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "interface not available");
        }

    }

    public interface dialoginterface {
        void setinactivity(String title, String data, String date, String day);
    }
}
