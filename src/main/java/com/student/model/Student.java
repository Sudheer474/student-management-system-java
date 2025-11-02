package com.student.model;

public class Student {
    private int id;
    private String name;
    private int age;
    private String email;
    private String course;

    public Student() {}

    public Student(int id, String name, int age, String email, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.course = course;
    }

    // CSV serialization helper
    public String toCsv() {
        // escape commas in text if needed (basic)
        return id + "," + escape(name) + "," + age + "," + escape(email) + "," + escape(course);
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace(",", ""); // simple escape: drop commas
    }

    public static Student fromCsv(String line) {
        String[] parts = line.split(",");
        if (parts.length < 5) return null;
        try {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            int age = Integer.parseInt(parts[2]);
            String email = parts[3];
            String course = parts[4];
            return new Student(id, name, age, email, course);
        } catch (Exception e) {
            return null;
        }
    }

    //getters
    public int getId() {return id;}
    public String getName() {return name;}
    public int getAge() {return age;}
    public String getEmail() {return email;}
    public String getCourse() {return course;}

    //setters
    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setAge(int age) {this.age = age;}
    public void setEmail(String email) {this.email = email;}
    public void setCourse(String course) {this.course = course;}

    @Override
    public java.lang.String toString() {
        return "Student{" + "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", course='" + course + '\'' +
                '}';
    }


}