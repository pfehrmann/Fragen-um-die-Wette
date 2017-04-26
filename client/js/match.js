/**
 * Created by phili on 24.04.2017.
 */
class Match {

    constructor(id, userA, userB, questionIds, currentQuestionUserA, currentQuestionUserB, answersUserA, answersUserB, matchFinished, whoWon) {
        this.id = id;
        this.userA = userA;
        this.userB = userB;
        this.questionIds = questionIds;
        this.currentQuestionUserA = currentQuestionUserA;
        this.currentQuestionUserB = currentQuestionUserB;
        this.answersUserA = answersUserA;
        this.answersUserB = answersUserB;
        this.matchFinished = matchFinished;
        this.whoWon = whoWon;
    }

    static createMatchFromJSON(data) {
        if (data.whoWon == null) {
            return new Match(data.id, data.userA, data.userB, data.questionIds, data.currentQuestionUserA, data.currentQuestionUserB, data.answersUserA, data.answersUserB, data.matchFinished, null);
        }
        return new Match(data.id, data.userA, data.userB, data.questionIds, data.currentQuestionUserA, data.currentQuestionUserB, data.answersUserA, data.answersUserB, data.matchFinished, User.createFromJson(data.whoWon));
    }

    static createFromId(id, callback) {
        let url = restAddress + "/match/" + id;

        $.ajax({
            dataType: "json",
            url: url,
            success: function (data) {
                let match = Match.createMatchFromJSON(data);
                callback(match);
            }
        });
    }

    answerQuestion(userId, answerId, callback) {
        let url = restAddress + "/answerQuestion/" + this.id + "/answer/" + answerId;
        let data = "{\"userId\":  " + userId + "}";
        $.post({
            dataType: "json",
            url: url,
            data: data,
            success: function (data) {
                let match = Match.createMatchFromJSON(data);
                callback(match);
            }
        });
        user.showMatches();
    }

    static createNew(userAId, userBId, callback) {
        let url = restAddress + "/match/" + userAId + "/" + userBId;
        $.post({
            dataType: "json",
            url: url,
            success: function (data) {
                let match = Match.createMatchFromJSON(data);
                callback(match);
            }
        });
    }

    toHTML() {
        // Check if match if finished.
        if (this.matchFinished) {
            let node = $("#match-over").clone().removeClass("hidden");

            let enemyId;
            // set the enemy and question
            if (user.id == this.userA) {
                enemyId = this.userB;
            } else {
                enemyId = this.userA;
            }

            User.create(enemyId, function (enemy) {
                node.find("[enemy]").text(enemy.name);
            });

            if (this.whoWon == null) {
                node.find("[winner]").text("Unentschieden");
            } else {
                node.find("[winner]").text(this.whoWon.name);
            }

            return node;
        }
        // clone the match prototype
        let node = $("#match").clone().removeClass("hidden");

        let enemyId;
        let questionId;
        let currentMatch = this;

        // set the enemy and question
        if (user.id == this.userA) {
            enemyId = this.userB;
            questionId = this.questionIds[this.currentQuestionUserA];
        } else {
            enemyId = this.userA;
            questionId = this.questionIds[this.currentQuestionUserB];
        }

        User.create(enemyId, function (enemy) {
            node.find("[enemy]").text(enemy.name);
        });

        Question.create(questionId, function (question) {
            node.find("[question]").text(question.questionText);
            node.find("[answer1text]").text(question.possibleAnswers[0].text);
            node.find("[answer2text]").text(question.possibleAnswers[1].text);
            node.find("[answer3text]").text(question.possibleAnswers[2].text);
            node.find("[answer4text]").text(question.possibleAnswers[3].text);

            node.find("[answer1]").val(question.possibleAnswers[0].id);
            node.find("[answer2]").val(question.possibleAnswers[1].id);
            node.find("[answer3]").val(question.possibleAnswers[2].id);
            node.find("[answer4]").val(question.possibleAnswers[3].id);
        });

        node.find("[submit-answer]").submit(function () {
            let answerId = $('[submit-answer] input:radio:checked').val();
            currentMatch.answerQuestion(user.id, answerId, function (match) {
                node.replaceWith(match.toHTML())
            });
            return false;
        });

        return node;
    }
}
