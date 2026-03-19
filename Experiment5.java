// ================== Experiment 5 ==================
// Spring Autowiring using @Autowired

package com.spring.autowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

// ================== Certification Class ==================
@Component
class Certification {

    @Value("C101")
    private String id;

    @Value("Java Full Stack")
    private String name;

    @Value("2026-03-01")
    private String dateOfCompletion;

    public void display() {
        System.out.println("Certification ID: " + id);
        System.out.println("Certification Name: " + name);
        System.out.println("Completion Date: " + dateOfCompletion);
    }
}

// ================== Student Class ==================
@Component
class Student {

    @Value("1")
    private int id;

    @Value("Pranathi")
    private String name;

    @Value("Female")
    private String gender;

    @Autowired
    private Certification certification;

    public void display() {
        System.out.println("Student ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Gender: " + gender);
        System.out.println("---- Certification Details ----");
        certification.display();
    }
}

// ================== Config Class ==================
@Configuration
@ComponentScan(basePackages = "com.spring.autowire")
class AppConfig {}

// ================== Main Class ==================
public class Experiment5 {
    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        Student student = context.getBean(Student.class);
        student.display();
    }
}
