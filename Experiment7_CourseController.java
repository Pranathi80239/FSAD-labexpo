package com.example.course;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// -------------------- ENTITY --------------------
class Course {
    private int courseId;
    private String title;
    private String duration;
    private double fee;

    public Course() {}

    public Course(int courseId, String title, String duration, double fee) {
        this.courseId = courseId;
        this.title = title;
        this.duration = duration;
        this.fee = fee;
    }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }
}

// -------------------- SERVICE --------------------
class CourseService {
    private Map<Integer, Course> courseMap = new HashMap<>();

    // CREATE
    public Course addCourse(Course course) {
        courseMap.put(course.getCourseId(), course);
        return course;
    }

    // READ ALL
    public List<Course> getAllCourses() {
        return new ArrayList<>(courseMap.values());
    }

    // READ BY ID
    public Course getCourseById(int id) {
        return courseMap.get(id);
    }

    // UPDATE
    public Course updateCourse(int id, Course course) {
        if (courseMap.containsKey(id)) {
            course.setCourseId(id);
            courseMap.put(id, course);
            return course;
        }
        return null;
    }

    // DELETE
    public boolean deleteCourse(int id) {
        return courseMap.remove(id) != null;
    }

    // SEARCH BY TITLE
    public List<Course> searchByTitle(String title) {
        List<Course> result = new ArrayList<>();
        for (Course c : courseMap.values()) {
            if (c.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(c);
            }
        }
        return result;
    }
}

// -------------------- CONTROLLER --------------------
@RestController
@RequestMapping("/courses")
public class Experiment7_CourseController {

    private CourseService service = new CourseService();

    // CREATE
    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        if (course.getTitle() == null || course.getTitle().isEmpty()) {
            return new ResponseEntity<>("Title cannot be empty", HttpStatus.BAD_REQUEST);
        }
        Course saved = service.addCourse(course);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        return new ResponseEntity<>(service.getAllCourses(), HttpStatus.OK);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable int id) {
        Course course = service.getCourseById(id);
        if (course == null) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable int id, @RequestBody Course course) {
        Course updated = service.updateCourse(id, course);
        if (updated == null) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Course updated successfully", HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable int id) {
        boolean deleted = service.deleteCourse(id);
        if (!deleted) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Course deleted successfully", HttpStatus.OK);
    }

    // SEARCH BY TITLE
    @GetMapping("/search/{title}")
    public ResponseEntity<?> searchCourses(@PathVariable String title) {
        List<Course> results = service.searchByTitle(title);
        if (results.isEmpty()) {
            return new ResponseEntity<>("No courses found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
