let table = document.getElementById("table-body");

fetch("http://localhost:8080/api/v1/getAllLikedUsers")
    .then(response => response.json())
    .then(
        function (data) {
            data.forEach(el => {
                let now = new Date().getTime();
                let lastLogin = new Date(el.lastLogin).getTime();
                let diffInDays = Math.round((now - lastLogin)/(1000 * 3600 * 24));
                table.innerHTML += `<tr>
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
