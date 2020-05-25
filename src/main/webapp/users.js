let response = fetch("http://localhost:8080/users");
let users = response.json();
console.log(users);