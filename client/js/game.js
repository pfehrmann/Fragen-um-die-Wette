var name = "";
var webAddress = "http://localhost:18082";
var restAddress = webAddress + "/rest";
var user;

$(document).ready(function () {

    // Bind actions
    $("#form-login").submit(login);

});

function login() {

    // Do logical login stuff
    name = $("#input-user")[0].value;
    createUser(name);
    console.log(name);

    // Update page
    $('#area-login').hide();
    $('#area-logout').show();

    return false; // To prevent page reload after submit
}

function createUser(userName) {
    url = restAddress + "/user/" + userName;

    $.post(url, function (data) {
        user = new User(data.id, data.matchIds, name);
    })
}