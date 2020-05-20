let userName = 123;

const host = document.location.host;
const pathname = document.location.pathname;
const ws = new WebSocket("ws://" + host + pathname + "/" + userName);

ws.onmessage = function (event) {
    const messagesId = document.getElementById("messages");
    const message = JSON.parse(event.data);

    messagesId.innerHTML += message.from === userName ?
        `<li class="send-msg float-right mb-2">
                <p class="pt-1 pb-1 pl-2 pr-2 m-0 rounded">
                    ${message.content}
                </p>
         </li>` :
        `<li class="receive-msg float-left mb-2">
            <div class="sender-img">
                <img src="http://nicesnippets.com/demo/image1.jpg" class="float-left" alt="avatar">
            </div>
            <div class="receive-msg-desc float-left ml-2">
                <p class="bg-white m-0 pt-1 pb-1 pl-2 pr-2 rounded">
                    ${message.content}
                </p>
                <span class="receive-msg-time">${message.from}, Jan 25, 6:20 PM</span>
            </div>
         </li>`;
};


const sendMessage = document.getElementById("sendMessage");

sendMessage.addEventListener("keypress", e => {
    if (e.key === 'Enter') {
        console.log(e)
        const content = {
            "content": e.currentTarget.value
        };
        ws.send(JSON.stringify(content));
        e.currentTarget.value = '';
        return false;
    }
    return true;
});