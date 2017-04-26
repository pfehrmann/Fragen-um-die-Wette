var user;

$(document).ready(function () {

    // Bind actions
    $("#form-login").submit(login);
    $('#button-logout').click(logout);
    $('#new-match-form').submit(startNewGame);
});

function startNewGame() {
    opponentId = $('#opponent-name').val();
    Match.createNew(user.id, opponentId, function (match) {
        $("#area-matches").append(match.toHTML());
    });
    return false;
}

function login() {

    // Do logical login stuff
    let name = $("#input-user")[0].value;
    User.create(name, function (newUser) {
        user = newUser;

        // Update page
        $('#element-logout-user').text("Eingeloggt als " + user.name);
        $('#area-login').addClass('hidden');
        $('#area-logout').removeClass('hidden');
        $('#area-matches').removeClass('hidden');
        $('#area-new-match').removeClass('hidden');
        newUser.showMatches();
    });

    return false; // To prevent page reload after submit
}

function logout() {

    user = undefined;

    $('#area-logout').addClass('hidden');
    $('#area-login').removeClass('hidden');

}
