var name = "";
var webAddress = "http://192.168.99.100:18082";
var restAddress = webAddress + "/rest";
var user;

function submitName() {
    name = $("#playname")[0].value;
    createUser(name);
    console.log(name);
}

function createUser(userName) {
    url = restAddress + "/user/" + userName;

    $.post(url, function (data) {
        user = new User(data.id, data.matchIds, name);
    })
}

class User {
    constructor(id, matchIds, name) {
        this.id = id;
        this.matchIds = matchIds;
        this.name = name;
    }
}