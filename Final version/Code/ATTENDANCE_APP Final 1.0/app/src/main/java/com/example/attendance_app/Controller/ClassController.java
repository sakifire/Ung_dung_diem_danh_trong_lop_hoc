package com.example.attendance_app.Controller;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.example.attendance_app.Service.ClassService;

public class ClassController extends ClassService {
    private ClassService classService;

    public ClassController(@Nullable Context context) {
        super(context);
        this.classService = new ClassService(context);
    }


    public long addClass(long uid, String className, String subjectName) {
        return classService.addClass(uid, className, subjectName);
    }

    public long updateClass(long cid, String className, String subjectName) {
        return classService.updateClass(cid, className, subjectName);
    }

    public int deleteClass(long cid) {
        return classService.deleteClass(cid);
    }

    public Boolean checkClassName(String classname) {
        return classService.checkClassName(classname);
    }

    public Cursor getClassTable(long uid) {
        return classService.getClassTable(uid);
    }


}
