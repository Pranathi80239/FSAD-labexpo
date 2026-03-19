import React, { useState } from "react";
import "./student.css";

function StudentManager() {

  // Initial students
  const [students, setStudents] = useState([
    { id: 1, name: "Mahesh", course: "CSE" },
    { id: 2, name: "Rahul", course: "ECE" },
    { id: 3, name: "Anjali", course: "IT" },
    { id: 4, name: "Priya", course: "EEE" },
    { id: 5, name: "Kiran", course: "MECH" }
  ]);

  // New student object
  const [newStudent, setNewStudent] = useState({
    id: "",
    name: "",
    course: ""
  });

  // Handle input change
  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewStudent({
      ...newStudent,
      [name]: value
    });
  };

  // Add student
  const addStudent = () => {
    if (!newStudent.id || !newStudent.name || !newStudent.course) {
      alert("Please fill all fields");
      return;
    }

    setStudents([...students, newStudent]);

    // Clear inputs
    setNewStudent({ id: "", name: "", course: "" });
  };

  // Delete student
  const deleteStudent = (id) => {
    const updatedList = students.filter((student) => student.id !== id);
    setStudents(updatedList);
  };

  return (
    <div className="container">
      <h2>Student Manager</h2>

      {/* Input Fields */}
      <div className="form">
        <input
          type="number"
          name="id"
          placeholder="Enter ID"
          value={newStudent.id}
          onChange={handleChange}
        />
        <input
          type="text"
          name="name"
          placeholder="Enter Name"
          value={newStudent.name}
          onChange={handleChange}
        />
        <input
          type="text"
          name="course"
          placeholder="Enter Course"
          value={newStudent.course}
          onChange={handleChange}
        />
        <button onClick={addStudent}>Add Student</button>
      </div>

      {/* Student List */}
      {students.length === 0 ? (
        <p>No students available</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Course</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {students.map((student) => (
              <tr key={student.id}>
                <td>{student.id}</td>
                <td>{student.name}</td>
                <td>{student.course}</td>
                <td>
                  <button
                    className="delete-btn"
                    onClick={() => deleteStudent(student.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default StudentManager;
