async function getMessageId(idFrom, idTo) {
    const result = await fetch(`/api/v1/getIdMessages?idFrom=${idFrom}&idTo=${idTo}`);
    const data = await result.json();
    return data.messageId;
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
    return new WebSocket("ws://" + host + pathname + "/" + messageId);
}

async function run() {
    let idFrom = getCookie("userId");
    let idTo = getUrlParam("id");
    let messageId = await getMessageId(idFrom, idTo)
    const ws = openSocket(messageId);

    ws.onmessage = function (event) {
        const messagesElem = document.getElementById("messages");
        const message = JSON.parse(event.data);

        const date = new Date(message.datetime);
        const dateTimeFormat = new Intl.DateTimeFormat(
            'en',
            {
                year: 'numeric',
                month: 'short',
                day: '2-digit',
                hour: 'numeric',
                minute: 'numeric'
            })
        const [{value: month},,
            {value: day},,
            {value: year},,
            {value: hour},,
            {value: minute},,
            {value: dayPeriod}] = dateTimeFormat.formatToParts(date)

        messagesElem.innerHTML += message.from === +idFrom ?
            `<li class="send-msg float-right mb-2">  
                <p class="pt-1 pb-1 pl-2 pr-2 m-0 rounded">
                    ${message.content}
                </p>
                <span class="send-msg-time">${year} ${month} ${day}, ${hour}:${minute} ${dayPeriod}</span>
         </li>` :
            `<li class="receive-msg float-left mb-2">
            <div class="sender-img">
                <img src="http://nicesnippets.com/demo/image1.jpg" class="float-left" alt="avatar">
            </div>
            <div class="receive-msg-desc float-left ml-2">
                <p class="bg-white m-0 pt-1 pb-1 pl-2 pr-2 rounded">
                    ${message.content}
                </p>
                <span class="receive-msg-time">${message.to}, ${year} ${month} ${day}, ${hour}:${minute} ${dayPeriod}</span>
            </div>
         </li>`;
    };


    const sendMessage = document.getElementById("sendMessage");
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