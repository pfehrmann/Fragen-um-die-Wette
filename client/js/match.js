/**
 * Created by phili on 24.04.2017.
 */
class Match {

    constructor(id, userA, userB, questionIds, currentQuestionUserA, currentQuestionUserB, ansersUserA, answersUserB, matchFinished) {
        this.id = id;
        this.userA = userA;
        this.userB = userB;
        this.questionIds = questionIds;
        this.currentQuestionUserA = currentQuestionUserA;
        this.currentQuestionUserB = currentQuestionUserB;
        this.ansersUserA = ansersUserA;
        this.answersUserB = answersUserB;
        this.matchFinished = matchFinished;
    }

    static createFromId(id, callback) {
        let url = restAddress + "/match/" + id;

        $.ajax({
            dataType: "json",
            url: url,
            success: function (data) {
                let match = new Match(data.id, data.userA, data.userB, data.questionsIds, data.currentQuestionUserA, data.currentQuestionUserB, data.ansersUserA, data.answersUserB, data.matchFinished);
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
                let match = new Match(data.id, data.userA, data.userB, data.questionIds, data.currentQuestionUserA, data.currentQuestionUserB, data.ansersUserA, data.answersUserB, data.matchFinished);
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
                let match = new Match(data.id, data.userA, data.userB, data.questionIds, data.currentQuestionUserA, data.currentQuestionUserB, data.ansersUserA, data.answersUserB, data.matchFinished);
                callback(match);
            }
        });
    }

    toHTML() {
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
            //node.find("[answer2]").text(question.questionText);
            //node.find("[answer3]").text(question.questionText);
            //node.find("[answer4]").text(question.questionText);

            node.find("[answer1]").val(question.possibleAnswers[0].id);
        });

        node.find("[submit-answer]").submit(function () {
            let answerId = $('[submit-answer] input:radio:checked').val();
            currentMatch.answerQuestion(user.id, answerId);
            return false;
        });

        return node;
    }
}
