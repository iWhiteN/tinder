let submit = document.getElementById("submit");
let host = document.location.host;
let protocol = document.location.protocol;

submit.addEventListener("click", function (e) {
    e.preventDefault();
    let pass = document.getElementById("inputPassword").value;
    let confirmPass = document.getElementById("confirmPassword").value;
    if (pass !== confirmPass) {
        alert("Password confirmation failed! Please input same pass in both fields");
        return;
    }

    let user = {
        name: document.getElementById("name").value,
        pass: pass,
        email: document.getElementById("inputEmail").value,
        lastLogin: new Date(),
        avatarUrl: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxhcCYW4QDWMOjOuUTxOd50KcJvK-rop9qE9zRltSbVS_bO-cfWA"
    };

    fetch(`${protocol}//${host}/api/v1/newUser`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    })
        .then(res => window.location.replace("users"))
});