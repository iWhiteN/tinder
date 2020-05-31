async function setLikes(idFrom, idTo, typeLikes) {
    return await fetch(`/api/v1/setLikes?idFrom=${idFrom}&idTo=${idTo}&typeLikes=${typeLikes}`);
}

async function getUserById(id) {
     let response = await fetch(`api/v1/getUserById?id=${id}`);
     return await response.json();
}

function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

function getUrlParam(name) {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    return urlParams.get(name);
}

async function clickLikeDislike(e) {
    let from = getCookie("userId");
    let to = getUrlParam("id");
    let type = e.currentTarget.getAttribute("id");
    let host = document.location.host;
    let protocol = document.location.protocol;
    await setLikes(from, to, type);

    if (type === 'like') {
        window.location.href = `${protocol}//${host}/messages?id=${to}`;
    } else {
        window.location.href = `${protocol}//${host}/users`
    }
}

async function run() {
    const { value:user } = await getUserById(getUrlParam("id"));
    const likeEl = document.getElementById("like");
    const dislikeEL = document.getElementById("dislike");
    const userNameEl = document.getElementById("userName");
    const avatarEl = document.getElementById("avatar");
    const userName = user.name;
    const avatar = user.avatarUrl;

    likeEl.addEventListener("click", clickLikeDislike);
    dislikeEL.addEventListener("click", clickLikeDislike);

    userNameEl.innerText = userName;
    avatarEl.setAttribute("src", avatar);
}

run();