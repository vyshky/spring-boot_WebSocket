var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/serverWebSocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/server/get/hello', function (greeting) { // подписывается при вызове /server/get/hello с фронта, будет рисоваться надпись с именем (Будет вызван метод GreetingController.greeting). Это листенер который слушает по этому ендпоинту '/server/get/hello' .  Если на сервер ни чего не отправит , то приветствие с именем не выведется
            showGreeting(JSON.parse(greeting.body).content); // Print
        });

        stompClient.subscribe('/server/get/admin', function (greeting) {// подписывается при вызове /server/get/hello с фронта, будет рисоваться надпись с именем (Будет вызван метод GreetingController.greeting2). Это листенер которй слушает по этому ендпоинту '/server/get/admin' . Если на сервер ни чего не отправит , то приветствие с именем не выведется
            showGreeting(JSON.parse(greeting.body).content); // Print
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    //stompClient.send("/prefix/send/hello/name", {}, JSON.stringify({'name': $("#name").val()}));
    stompClient.send("/send/hello/name", {}, JSON.stringify({'name': $("#name").val()}));
    stompClient.send("/send/hello/admin", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

