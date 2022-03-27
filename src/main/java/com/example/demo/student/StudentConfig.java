package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repo){
        return args -> {
            Student john = new Student(
                    "John",
                    LocalDate.of(2000, Month.JANUARY,5),
                    "john@something.com");
            Student alex = new Student(
                    "Alex",
                    LocalDate.of(1999, Month.JUNE,23),
                    "alex@something.com");

            repo.saveAll(List.of(john,alex));
        };
    }
}
