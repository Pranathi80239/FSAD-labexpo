// ================== Experiment 4 ==================
// Spring Dependency Injection (Constructor & Setter Injection)

// ================== Student.java ==================
package com.spring.di;

public class Student {
    private int studentId;
    private String name;
    private String course;
    private int year;

    // Constructor Injection
    public Student(int studentId, String name, String course, int year) {
        this.studentId = studentId;
        this.name = name;
        this.course = course;
        this.year = year;
    }

    // Setter Injection
    public void setCourse(String course) {
        this.course = course;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void display() {
        System.out.println("ID: " + studentId);
        System.out.println("Name: " + name);
        System.out.println("Course: " + course);
        System.out.println("Year: " + year);
    }
}

// ================== XML Config (applicationContext.xml) ==================
/*
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="student" class="com.spring.di.Student">
        <constructor-arg value="101"/>
        <constructor-arg value="Mahesh"/>
        <constructor-arg value="CSE"/>
        <constructor-arg value="3"/>
    </bean>

</beans>
*/

// ================== Main Class for XML ==================
package com.spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainXML {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        Student s = (Student) context.getBean("student");
        s.display();
    }
}

// ================== Annotation आधारित ==================
package com.spring.di;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class StudentAnno {

    private int studentId;
    private String name;
    private String course;
    private int year;

    public StudentAnno(@Value("102") int studentId,
                       @Value("Pranathi") String name,
                       @Value("IT") String course,
                       @Value("2") int year) {
        this.studentId = studentId;
        this.name = name;
        this.course = course;
        this.year = year;
    }

    public void display() {
        System.out.println(studentId + " " + name + " " + course + " " + year);
    }
}

// ================== Config Class ==================
package com.spring.di;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.spring.di")
class AppConfig {}

// ================== Main Class for Annotation ==================
package com.spring.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAnno {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        StudentAnno s = context.getBean(StudentAnno.class);
        s.display();
    }
}
