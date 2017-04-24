/**
 * Created by phili on 24.04.2017.
 */
class Question {
    constructor(id, questionText, possibleAnswers) {
        this.id = id;
        this.questionText = questionText;
        this.possibleAnswers = possibleAnswers;
    }

    static createFromJson(data) {
        let possibleAnswers = [];
        for (let i = 0; i < data.possibleAnswers.length; i++) {
            let possibleAnswer = data.possibleAnswers[i];
            possibleAnswers.push(new Answer(possibleAnswer.id, possibleAnswer.text));
        }
        return new Question(data.id, data.questionText, possibleAnswers);
    }

    static create(id, callback) {
        let url = restAddress + "/question/" + id;

        $.ajax({
            dataType: "json",
            url: url,
            success: function (data) {
                callback(Question.createFromJson(data));
            }
        });
    }
}