package com.example.fragmentproject.dao;

import com.example.fragmentproject.Bean.Student;

public interface StudentManger {
    int addStudent(Student stu);
    int deleteStudent(Student stu);
    int queryStudent(Student student);
    int updateStudent(Student ori,Student tar);
}
