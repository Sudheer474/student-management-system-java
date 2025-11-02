package com.student.repository;

import com.student.model.Student;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentRepository {
    private final Path dbFile;

    public StudentRepository(String filePath) throws IOException {
        this.dbFile = Paths.get(filePath);
        if (!Files.exists(dbFile.getParent())) {
            Files.createDirectories(dbFile.getParent());
        }
        if (!Files.exists(dbFile)) {
            Files.createFile(dbFile);
        }
    }

    public List<Student> findAll() {
        try {
            return Files.lines(dbFile)
                    .map(Student::fromCsv)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Optional<Student> findById(long id) {
        return findAll().stream().filter(s -> s.getId() == id).findFirst();
    }

    public void save(Student student) {
        List<Student> all = findAll();
        all.removeIf(s -> s.getId() == student.getId()); // replace if exists
        all.add(student);
        writeAll(all);
    }

    public void deleteById(long id) {
        List<Student> all = findAll();
        boolean removed = all.removeIf(s -> s.getId() == id);
        if (removed) writeAll(all);
    }

    private void writeAll(List<Student> students) {
        try (BufferedWriter bw = Files.newBufferedWriter(dbFile, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Student s : students) {
                bw.write(s.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int nextId() {
        return findAll().stream().mapToInt(Student::getId).max().orElse(0) + 1;
    }
}