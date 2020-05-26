let submit = document.getElementById("submit");

submit.addEventListener("submit", function () {
    let pass = document.getElementById("inputPassword").value;
    let confirmPass = document.getElementById("confirmPassword").value;
    if (pass !== confirmPass) {
        alert("Password confirmation failed! Please input same pass in both fields");
        return;
    }

    let user = {
        name: document.getElementById("name").value,
        pass: pass,
        email: document.getElementById("inputEmail").value
    };

    let response = fetch('/api/v1/newUser', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(user)
    });

    let result = response.json().then(id => {
        document.cookie = `userId=${id}`
    });
});