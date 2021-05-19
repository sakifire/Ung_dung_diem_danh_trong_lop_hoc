package com.example.attendance_app;

public class ClassItem {
    private String className;
    private String subjectName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public ClassItem (String className, String subjectName)
    {
        this.className = className;
        this.subjectName = subjectName;
    }
}
