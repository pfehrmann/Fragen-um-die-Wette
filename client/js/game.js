var user;

$(document).ready(function () {

    // Bind actions
    $("#form-login").submit(login);
    $('#button-logout').click(logout);

});

function login() {

    // Do logical login stuff
    let name = $("#input-user")[0].value;
    User.create(name, function (newUser) {
        user = newUser;

        // Update page
        $('#element-logout-user').text("Eingeloggt als " + user.name);
        $('#area-login').addClass('hidden');
        $('#area-logout').removeClass('hidden');
    });

    return false; // To prevent page reload after submit
}

function logout() {

    user = undefined;

    $('#area-logout').addClass('hidden');
    $('#area-login').removeClass('hidden');

}
