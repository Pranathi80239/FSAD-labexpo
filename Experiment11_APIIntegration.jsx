import React, { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [page, setPage] = useState("home");

  return (
    <div className="container">
      <h1>React API Integration</h1>

      {/* Navigation */}
      <div className="nav">
        <button onClick={() => setPage("local")}>Local Users</button>
        <button onClick={() => setPage("api")}>Users API</button>
        <button onClick={() => setPage("fake")}>Fake API Posts</button>
        <button onClick={() => setPage("home")}>Home</button>
      </div>

      {/* Routing */}
      {page === "home" && <h2>Welcome to Dashboard</h2>}
      {page === "local" && <LocalUserList />}
      {page === "api" && <UserList />}
      {page === "fake" && <FakePostList />}
    </div>
  );
}

/* ------------------ Part A: Local JSON ------------------ */
function LocalUserList() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("/users.json")
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch local data");
        return res.json();
      })
      .then((data) => {
        setUsers(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Loading Local Users...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div>
      <h2>Local Users</h2>
      {users.map((u) => (
        <div key={u.id} className="card">
          <p><b>{u.name}</b></p>
          <p>{u.email}</p>
          <p>{u.phone}</p>
        </div>
      ))}
    </div>
  );
}

/* ------------------ Part B: Fetch API ------------------ */
function UserList() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/users")
      .then((res) => {
        if (!res.ok) throw new Error("API Error");
        return res.json();
      })
      .then((data) => {
        setUsers(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Loading API Users...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div>
      <h2>Users API</h2>
      {users.map((u) => (
        <div key={u.id} className="card">
          <p><b>{u.name}</b></p>
          <p>{u.email}</p>
          <p>{u.phone}</p>
        </div>
      ))}
    </div>
  );
}

/* ------------------ Part C: Axios + Filter ------------------ */
function FakePostList() {
  const [posts, setPosts] = useState([]);
  const [filter, setFilter] = useState("all");

  const fetchPosts = () => {
    axios
      .get("https://dummyjson.com/posts")
      .then((res) => {
        setPosts(res.data.posts);
      })
      .catch(() => alert("Error fetching posts"));
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  const filteredPosts =
    filter === "all"
      ? posts
      : posts.filter((p) => p.userId === Number(filter));

  return (
    <div>
      <h2>Fake API Posts</h2>

      {/* Filter */}
      <select onChange={(e) => setFilter(e.target.value)}>
        <option value="all">All</option>
        <option value="1">User 1</option>
        <option value="2">User 2</option>
        <option value="3">User 3</option>
      </select>

      {/* Refresh Button */}
      <button onClick={fetchPosts}>Refresh</button>

      {filteredPosts.map((p) => (
        <div key={p.id} className="card">
          <p><b>{p.title}</b></p>
          <p>{p.body}</p>
        </div>
      ))}
    </div>
  );
}

export default App;   


[
  { "id": 1, "name": "Mahesh", "email": "mahesh@gmail.com", "phone": "9876543210" },
  { "id": 2, "name": "Varun", "email": "varun@gmail.com", "phone": "9123456780" },
  { "id": 3, "name": "Vijay", "email": "vijay@gmail.com", "phone": "9012345678" },
  { "id": 4, "name": "Pavan", "email": "pavan@gmail.com", "phone": "9988776655" },
  { "id": 5, "name": "Satya", "email": "satya@gmail.com", "phone": "8899776655" }
]


.container {
  text-align: center;
  font-family: Arial;
}

.nav button {
  margin: 5px;
  padding: 8px 12px;
  cursor: pointer;
}

.card {
  border: 1px solid #ccc;
  margin: 10px auto;
  padding: 10px;
  width: 60%;
  border-radius: 8px;
}
