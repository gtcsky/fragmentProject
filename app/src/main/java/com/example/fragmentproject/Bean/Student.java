package com.example.fragmentproject.Bean;

public class Student {
    private int stdId;
    private String name;
    private String gender;
    private String school;
    private String stdClass;
    private int age;

    public Student() {

    }

    public Student(String name, String gender, String school, String stdClass, int age) {
        this.name = name;
        this.gender = gender;
        this.school = school;
        this.stdClass = stdClass;
        this.age = age;
    }

    public Student(int stdId, String name, String gender, String school, String stdClass, int age) {
        this(name, gender, school, stdClass, age);
        this.stdId = stdId;
    }

    public int getStdId() {
        return stdId;
    }

    public void setStdId(int stdId) {
        this.stdId = stdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStdClass() {
        return stdClass;
    }

    public void setStdClass(String stdClass) {
        this.stdClass = stdClass;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stdId=" + stdId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", school='" + school + '\'' +
                ", stdClass='" + stdClass + '\'' +
                ", age=" + age +
                '}';
    }
}
