package com.example.levunam_ktra2_bai2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btAdd, btAll, btGet, btUpdate, btDelete;
    private EditText txMaMon, txTenMon;
    private RecyclerView recyclerView;
    CheckBox checkboxActive;
    DatePicker datePicker;
    TimePicker timePicker;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SQLiteCalendarHelper sqLiteCalendarHelper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
        sqLiteCalendarHelper = new SQLiteCalendarHelper(this);

        btAdd.setOnClickListener(v -> {
            String name = txTenMon.getText().toString();
            try {
                String date = getDateFromDatePicker();
                String time = getTimeFromTimePicker();
                String active = "Không thi viết";
                if (checkboxActive.isChecked()) {
                    active = "Thi viết";
                }
                Calendar calendar = new Calendar(name, date, time, active);
                sqLiteCalendarHelper.addCalendar(calendar);
            }catch (NumberFormatException e){
                System.out.println(e + "Nhập lại");
            }
        });

        btAll.setOnClickListener(v -> {
            try {
                List<Calendar> calendars = sqLiteCalendarHelper.getAllCalendar();
                recyclerViewAdapter.setCalendars(calendars);
                recyclerView.setAdapter(recyclerViewAdapter);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        btGet.setOnClickListener(v -> {
            try {
                String name = txTenMon.getText().toString();
                List<Calendar> calendars = sqLiteCalendarHelper.searchCalendar(name);
                if (calendars == null){
                    Toast.makeText(getApplicationContext(), "Không có môn nào!", Toast.LENGTH_SHORT);
                }else {
                    recyclerViewAdapter.setCalendars(calendars);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
            }catch (NumberFormatException | ParseException e){
                System.out.println(e + "");
            }
        });

        btUpdate.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(txMaMon.getText().toString());
                String name = txTenMon.getText().toString();
                String date = getDateFromDatePicker();
                String time = getTimeFromTimePicker();
                String active = "Không thi viết";
                if (checkboxActive.isChecked()) {
                    active = "Thi viết";
                }
                Calendar calendar = new Calendar(id, name, date, time, active);
                sqLiteCalendarHelper.update(calendar);
            }catch (NumberFormatException e){
                System.out.println(e + "");
            }
        });

        btDelete.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(txMaMon.getText().toString());
                try {
                    int check = sqLiteCalendarHelper.deleteById(id);
                    if (check == -1){
                        Toast.makeText(getApplicationContext(), "Không xoá được!", Toast.LENGTH_SHORT);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }catch (NumberFormatException e){
                System.out.println(e + "");
            }
        });
    }

    public void init(){
        btAdd = findViewById(R.id.btAdd);
        btAll = findViewById(R.id.btAll);
        btGet = findViewById(R.id.btGet);
        btUpdate = findViewById(R.id.btUpdate);
        btDelete = findViewById(R.id.btDelete);
        txMaMon = findViewById(R.id.inId);
        checkboxActive = findViewById(R.id.inThiViet);

        txTenMon = findViewById(R.id.inTenMon);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);

        recyclerView = findViewById(R.id.recyclerView);
    }

    private String getDateFromDatePicker(){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        String result = day + "-" + month + "-" + year;
        return result;
    }

    private String getTimeFromTimePicker(){
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        String result = hour + "-" + minute;
        return result;
    }
}