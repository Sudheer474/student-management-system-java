package com.student.service;

import com.student.model.Student;
import com.student.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

public class StudentServ {
    private final StudentRepository repo;

    public StudentServ(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> listAll() {
        return repo.findAll();
    }

    public Optional<Student> findById(long id) {
        return repo.findById(id);
    }

    public Student create(String name, int age, String email, String course) {
        int id = repo.nextId();
        Student s = new Student(id, name, age, email, course);
        repo.save(s);
        return s;
    }

    public boolean update(long id, String name, Integer age, String email, String course) {
        Optional<Student> opt = repo.findById(id);
        if (opt.isEmpty()) return false;
        Student s = opt.get();
        if (name != null) s.setName(name);
        if (age != null) s.setAge(age);
        if (email != null) s.setEmail(email);
        if (course != null) s.setCourse(course);
        repo.save(s);
        return true;
    }

    public boolean delete(long id) {
        Optional<Student> opt = repo.findById(id);
        if (opt.isEmpty()) return false;
        repo.deleteById(id);
        return true;
    }
}
