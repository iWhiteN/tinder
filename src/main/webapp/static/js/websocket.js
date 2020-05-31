async function getMessageId(idFrom, idTo) {
    const result = await fetch(`/api/v1/getIdMessages?idFrom=${idFrom}&idTo=${idTo}`);
    const data = await result.json();
    return data.messageId;
}

async function getUserById(id) {
    let response = await fetch(`api/v1/getUserById?id=${id}`);
    return await response.json();
}

async function getAllMessages(messagesId) {
    const result = await fetch(`/api/v1/getAllMessages?messagesId=${messagesId}`);
    return await result.json();
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

function openSocket(messageId) {
    const host = document.location.host;
    const pathname = document.location.pathname;
    const protocol = document.location.protocol;
    return new WebSocket(`${protocol === 'https:' ? 'wss://' : 'ws://'}${host}${pathname}/${messageId}`);
}

function parseDatetime(datetime) {
    const date = new Date(datetime);
    const dateTimeFormat = new Intl.DateTimeFormat(
        'en',
        {
            year: 'numeric',
            month: 'short',
            day: '2-digit',
            hour: 'numeric',
            minute: 'numeric'
        })
    const [{value: month}, ,
        {value: day}, ,
        {value: year}, ,
        {value: hour}, ,
        {value: minute}, ,
        {value: dayPeriod}] = dateTimeFormat.formatToParts(date);

    return `${year} ${month} ${day}, ${hour}:${minute} ${dayPeriod}`;
}

function renderMessageSelf(content, datetime) {
    const messagesElem = document.getElementById("messages");
    messagesElem.innerHTML +=
        `<li class="send-msg float-right mb-2">  
                <p class="pt-1 pb-1 pl-2 pr-2 m-0 rounded">
                    ${content}
                </p>
                <span class="send-msg-time">${parseDatetime(datetime)}</span>
         </li>`;
}

function renderMessageFrom(from, avatar, content, datetime) {
    const messagesElem = document.getElementById("messages");
    messagesElem.innerHTML +=
        `<li class="receive-msg float-left mb-2">
            <div class="sender-img">
                <img src="${avatar}" class="float-left" alt="avatar">
            </div>
            <div class="receive-msg-desc float-left ml-2">
                <p class="bg-white m-0 pt-1 pb-1 pl-2 pr-2 rounded">
                    ${content}
                </p>
                <span class="receive-msg-time">${from}, ${parseDatetime(datetime)}</span>
            </div>
         </li>`;
}

async function run() {
    let idFrom = getCookie("userId");
    let idTo = getUrlParam("id");
    const {value: user} = await getUserById(idTo);
    let nameTo = user.name
    let avatar = user.avatarUrl;
    let messageId = await getMessageId(idFrom, idTo)
    const ws = openSocket(messageId);
    const nameToEl = document.getElementById("nameTo");
    nameToEl.innerHTML = nameTo;
    const allMessages = await getAllMessages(messageId);
    const sendMessage = document.getElementById("sendMessage");

    allMessages.forEach(m => {
        const {content, datetimeSend, from, to} = m;
        from.userId === +idFrom ?
            renderMessageSelf(content, datetimeSend) :
            renderMessageFrom(to.name, to.avatarUrl, content, datetimeSend);
    })

    ws.onmessage = function (event) {
        const {content, datetime, from, to} = JSON.parse(event.data);
        from === +idFrom ?
            renderMessageSelf(content, datetime) :
            renderMessageFrom(nameTo, avatar, content, datetime);
    };

    sendMessage.addEventListener("keypress", e => {
        if (e.key === 'Enter') {
            const content = {
                "from": idFrom,
                "to": idTo,
                "content": e.currentTarget.value
            };
            ws.send(JSON.stringify(content));
            e.currentTarget.value = '';
            return false;
        }
        return true;
    });
}

run();