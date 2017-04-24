/**
 * Created by phili on 20.04.2017.
 */
class User {
    constructor(id, matchIds, name) {
        this.id = id;
        this.matchIds = matchIds;
        this.name = name;
    }

    static createFromJson(data) {
        return new User(data.id, data.matchIds, data.name);
    }

    static create(name, callback) {
        let url = restAddress + "/user/" + name;

        $.ajax({
            dataType: "json",
            url: url,
            success: function (data) {
                callback(User.createFromJson(data));
            },
            error: function () {
                $.post(url, function (data) {
                    callback(User.createFromJson(data));
                })
            }
        });
    }

    getMatches(callback) {
        let matches = [];
        let c = 0;
        for (let index = 0; index < this.matchIds; ++index) {
            Match.createFromId(this.matchIds[index], function (match) {
                matches.push(match);
                c++;
            });
        }
        while (c != this.matchIds.length) {
            console.log(c);
        }
        return matches;
    }

    showMatches() {
        $("#area-matches").empty();
        $("#area-matches").append("<h2>Spiele</h2>");
        for (let index = 0; index < this.matchIds.length; index++) {
            Match.createFromId(this.matchIds[index], function (match) {
                $("#area-matches").append(match.toHTML());
            });
        }
    }
}

