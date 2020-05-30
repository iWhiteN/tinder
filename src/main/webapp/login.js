let submit = document.getElementById("submit");
let host = document.location.host;
let protocol = document.location.protocol;

submit.addEventListener("click", function (e) {
    e.preventDefault();

    let data = {
        pass: document.getElementById("inputPassword").value,
        email: document.getElementById("inputEmail").value
    };

    fetch(`${protocol}//${host}/api/v1/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                window.location.replace("likedUsers");
            } else {
                alert("Incorrect login or password")
            }
        })
});