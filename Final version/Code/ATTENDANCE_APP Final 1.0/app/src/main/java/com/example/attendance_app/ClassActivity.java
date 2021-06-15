package com.example.attendance_app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance_app.Adapter.ClassAdapter;
import com.example.attendance_app.Controller.ClassController;
import com.example.attendance_app.Model.ClassItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ClassAdapter classAdapter;
    ArrayList<ClassItem> classItems = new ArrayList<>();
    Toolbar toolbar;
    ClassController classController;
    private long uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        classController = new ClassController(this);
        Intent intent = getIntent();
        uid = intent.getIntExtra("uid_forClass", -1);

        fab = findViewById(R.id.fab_main);
        fab.setOnClickListener(v -> showDialog());


        setToolbar();
        loadData();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        classAdapter = new ClassAdapter(this, classItems);
        recyclerView.setAdapter(classAdapter);
        classAdapter.setOnItemClickListener(position -> gotoItemActivity(position));

        setToolbar();
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                showUpdateDialog(item.getGroupId());
                break;
            case 1:
                deleteClass(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }


    private void loadData() {
        Cursor cursor = classController.getClassTable(uid);

        classItems.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(classController.getDbHelper().C_ID));
            //int uid = cursor.getInt(cursor.getColumnIndex(dbHelper.U_ID));
            String className = cursor.getString(cursor.getColumnIndex(classController.getDbHelper().CLASS_NAME_KEY));
            String subjectName = cursor.getString(cursor.getColumnIndex(classController.getDbHelper().SUBJECT_NAME_KEY));

            classItems.add(new ClassItem(id, className, subjectName));
        }
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.title_toolbar);
        TextView subtitle = toolbar.findViewById(R.id.subtitle_toolbar);
        ImageButton back = toolbar.findViewById(R.id.back);
        ImageButton save = toolbar.findViewById(R.id.save);

        back.setOnClickListener(v -> onBackPressed());

        title.setText("Attendance App");
        subtitle.setVisibility(View.GONE);
        save.setVisibility(View.INVISIBLE);
    }

    private void gotoItemActivity(int position) {
        Intent intent = new Intent(this, StudentActivity.class);

        intent.putExtra("className", classItems.get(position).getClassName());
        intent.putExtra("subjectName", classItems.get(position).getSubjectName());
        intent.putExtra("position", position);
        intent.putExtra("cid", classItems.get(position).getCid());

        startActivity(intent);
    }

    private void showDialog() {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.CLASS_ADD_DIALOG);
        dialog.setListener((className, subjectName) -> addClass(className, subjectName));
    }

    private void addClass(String className, String subjectName) {
        if(classController.checkClassName(className)){
            Toast.makeText(ClassActivity.this,"This class has already exist", Toast.LENGTH_SHORT).show();
            return;
        }
        long cid = classController.addClass(uid, className, subjectName);
        ClassItem classItem = new ClassItem(cid, className, subjectName);
        classItems.add(classItem);
        classAdapter.notifyDataSetChanged();

    }

    private void showUpdateDialog(int position) {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), MyDialog.CLASS_UPDATE_DIALOG);
        dialog.setListener((className, subjectName) -> updateClass(position, className, subjectName));

    }

    private void updateClass(int position, String className, String subjectName) {
        classController.updateClass(classItems.get(position).getCid(), className, subjectName);
        classItems.get(position).setClassName(className);
        classItems.get(position).setSubjectName(subjectName);
        classAdapter.notifyItemChanged(position);

    }

    private void deleteClass(int position) {
        classController.deleteClass(classItems.get(position).getCid());
        classItems.remove(position);
        classAdapter.notifyItemRemoved(position);
    }
}