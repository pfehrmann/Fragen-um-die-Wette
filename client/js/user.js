/**
 * Created by phili on 20.04.2017.
 */
var webAddress = "http://192.168.99.100:18082";
var restAddress = webAddress + "/rest";
class User {
    constructor(id, matchIds, name) {
        this.id = id;
        this.matchIds = matchIds;
        this.name = name;
    }

    static create(name, callback) {
        let url = restAddress + "/user/" + name;

        $.ajax({
            dataType: "json",
            url: url,
            success: function (data) {
                let user = new User(data.id, data.matchIds, name);
                callback(user);
            },
            error: function () {
                $.post(url, function (data) {
                    let user = new User(data.id, data.matchIds, name);
                    callback(user);
                })
            }
        });

    }
}
