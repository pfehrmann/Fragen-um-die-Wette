/**
 * Created by phili on 20.04.2017.
 */
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
                    console.log(user);
                })
            }
        });
    }

    get matches() {
        let matches = [];
        let c = 0;
        for (let index = 0; index < this.matchIds; ++index) {
            Match.createFromId(this.matchIds[index], function (match) {
                matches.push(match);
                c++;
            });
        }
        while (c != this.matchIds.length) {

        }
        return matches;
    }
}
