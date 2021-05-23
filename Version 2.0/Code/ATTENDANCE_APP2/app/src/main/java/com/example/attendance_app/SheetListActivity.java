package com.example.attendance_app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class SheetListActivity extends AppCompatActivity {
    private ListView sheetList;
    private ArrayAdapter adapter;
    private ArrayList<String> listItems = new ArrayList();
    private long cid;

    Toolbar toolbar;
    private TextView subtitle;
    private String className;
    private String subjectName;
    private MyCalendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_list);

        cid= getIntent().getLongExtra("cid",-1);
        Log.i("1234567890","onCreate: "+cid);

        //setToolbar();
        loadListItems();
        sheetList = findViewById(R.id.sheetList);
        adapter = new ArrayAdapter(this, R.layout.sheet_list, R.id.date_list_item, listItems);
        sheetList.setAdapter(adapter);

        sheetList.setOnItemClickListener((parent, view, position, id) -> openSheetActivity(position));
    }

    private void openSheetActivity(int position) {
        long[] idArray = getIntent().getLongArrayExtra("idArray");
        int[] rollArray = getIntent().getIntArrayExtra("rollArray");
        String[] nameArray = getIntent().getStringArrayExtra("nameArray");

        Intent intent = new Intent(this, SheetActivity.class);
        intent.putExtra("idArray", idArray);
        intent.putExtra("rollArray", rollArray);
        intent.putExtra("nameArray", nameArray);
        intent.putExtra("month",listItems.get(position));

        startActivity(intent);
    }

    private void loadListItems() {
        Cursor cursor = new DbHelper(this).getDistinctMonth(cid);

        while (cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndex(DbHelper.DATE_KEY)); //01.04.2020
            listItems.add(date.substring(3));
        }
    }
    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.save);
        //save.setOnClickListener(v -> saveStatus());

        //title.setText(className);
        //subtitle.setText(subjectName + " | " + calendar.getDate());

        back.setOnClickListener(v -> onBackPressed());
        save.setVisibility(View.INVISIBLE);
        toolbar.inflateMenu(R.menu.student_menu);
        //toolbar.setOnMenuItemClickListener(menuItem -> onMenuItemClick(menuItem));
    }

}