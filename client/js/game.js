var name = "";
var webAddress = "http://localhost:8081";
var restAddress = webAddress + "/rest";

function submitName() {
    name = $("#playname")[0].value;
    createUser(name);
    console.log(name);
}

function createUser(userName) {
    url = restAddress + "/user/" + userName;

    $.getJSON(url, function (data) {
        console.log(data);
    })
}