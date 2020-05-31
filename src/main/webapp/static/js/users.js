let table = document.getElementById("table-body");

let host = document.location.host;
let protocol = document.location.protocol;

fetch(`${protocol}//${host}/api/v1/getAllWithoutLikes`)
    .then(response => response.json())
    .then(
        function (data) {
            data.forEach(el => {
                let now = new Date().getTime();
                let lastLogin = new Date(el.lastLogin).getTime();
                let diffInDays = Math.round((now - lastLogin)/(1000 * 3600 * 24));
                table.innerHTML += `<tr class="user-list" id=${el.userId}>
                <td width="10">
                  <div class="avatar-img">
                    <img class="img-circle" src=${el.avatarUrl} />  
                  </div>

                </td>
                <td class="align-middle">
                  ${el.name}
                </td>
<!--                <td class="align-middle">-->
<!--                  Builder Sales Agent-->
<!--                </td>-->
                <td  class="align-middle">
                  Last Login:  ${el.lastLogin}<br><small class="text-muted">${diffInDays} days ago</small>
                </td>
              </tr>`
            })
        }
    );

table.addEventListener("click", function (e) {
    window.location.replace(`like-page?id=${e.target.parentElement.id}`)
    console.log(e.target.parentElement.id);
})