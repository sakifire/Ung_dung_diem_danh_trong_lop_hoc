package com.example.attendance_app.Controller;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.example.attendance_app.Service.StudentService;

public class StudentController extends StudentService {
    private StudentService studentService;

    public StudentController(@Nullable Context context) {
        super(context);
        this.studentService = new StudentService(context);
    }

    public StudentService getStudentModel() {
        return studentService;
    }

    public long addStudent(long cid, int roll, String name) {
        return studentService.addStudent(cid, roll, name);
    }

    public Cursor getStudentTable(long cid) {
        return studentService.getStudentTable(cid);
    }

    public long addStatus(long sid, long cid, String date, String status) {
        return studentService.addStatus(sid, cid, date, status);
    }

    public long deleteStudent(long sid) {
        return studentService.deleteStudent(sid);
    }

    public long updateStudent(long sid, String name) {
        return studentService.updateStudent(sid, name);
    }

    public long updateStatus(long sid, String date, String status) {
        return studentService.updateStatus(sid, date, status);
    }

    public String getStatus(long sid, String date) {
        return studentService.getStatus(sid, date);
    }

    public Cursor getDistinctMonth(long cid) {
        return studentService.getDistinctMonth(cid);
    }
}
