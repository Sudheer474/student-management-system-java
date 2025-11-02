package com.student;

import com.student.model.Student;
import com.student.repository.StudentRepository;
import com.student.service.StudentServ;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final String DB_PATH = "data/students.csv";

    public static void main(String[] args) throws IOException {

        StudentRepository repo = new StudentRepository(DB_PATH);
        StudentServ service = new StudentServ(repo);
        Scanner sc = new Scanner(System.in);

        while (true) {
            showMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> addStudent(sc, service);
                case "2" -> listStudents(service);
                case "3" -> findStudent(sc, service);
                case "4" -> updateStudent(sc, service);
                case "5" -> deleteStudent(sc, service);
                case "0" -> {
                    System.out.println("Exiting. Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add student");
        System.out.println("2. List students");
        System.out.println("3. Find student by id");
        System.out.println("4. Update student");
        System.out.println("5. Delete student");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private static void addStudent(Scanner sc, StudentServ service) {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Age: ");
        int age = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Course: ");
        String course = sc.nextLine().trim();

        Student s = service.create(name, age, email, course);
        System.out.println("Created: " + s);
    }

    private static void listStudents(StudentServ service) {
        List<Student> all = service.listAll();
        if (all.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        all.forEach(System.out::println);
    }

    private static void findStudent(Scanner sc, StudentServ service) {
        System.out.print("Enter id: ");
        long id = Long.parseLong(sc.nextLine().trim());
        Optional<Student> s = service.findById(id);

        System.out.println(s.map(student -> {
            System.out.println(student);
            return student; // return something to satisfy map()
        }).orElseGet(() -> {
            System.out.println("Not found.");
            return null;
        }));
    }

    private static void updateStudent(Scanner sc, StudentServ service) {
        try {
            System.out.print("Enter id to update: ");
            long id = Long.parseLong(sc.nextLine().trim());
            System.out.print("New name (leave blank to keep): ");
            String name = sc.nextLine().trim();
            System.out.print("New age (leave blank to keep): ");
            String ageStr = sc.nextLine().trim();
            System.out.print("New email (leave blank to keep): ");
            String email = sc.nextLine().trim();
            System.out.print("New course (leave blank to keep): ");
            String course = sc.nextLine().trim();

            Integer age = ageStr.isEmpty() ? null : Integer.parseInt(ageStr);
            boolean updated = service.update(id, name.isEmpty() ? null : name, age, email.isEmpty() ? null : email, course.isEmpty() ? null : course);
            System.out.println(updated ? "Updated successfully." : "Student not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private static void deleteStudent(Scanner sc, StudentServ service) {
        System.out.print("Enter id to delete: ");
        long id = Long.parseLong(sc.nextLine().trim());
        boolean deleted = service.delete(id);
        System.out.println(deleted ? "Deleted." : "Student not found.");
    }
}
