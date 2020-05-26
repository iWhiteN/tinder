let tableContainer = document.getElementsByClassName("table-container");
console.log(tableContainer);

fetch("http://localhost:8080/api/v1/getAllWithoutLikes")
    .then(response => response.json())
    .then(
        function (data) {
            tableContainer[0].innerHTML += `<span>${data.name}</span>`
        }
    );
