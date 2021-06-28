package com.farshid.customleitnerbox.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "leitner_box")
public class LeitnerEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "question")
    private String question;
    @ColumnInfo(name = "correct_answer")
    private String correctAnswer;
    @ColumnInfo(name = "wrong_answer_1")
    private String wrongAnswer1;
    @ColumnInfo(name = "wrong_answer_2")
    private String wrongAnswer2;
    @ColumnInfo(name = "correct_counter")
    private int correctCounter;
    @ColumnInfo(name = "wrong_counter")
    private int wrongCounter;
    /*@ColumnInfo(name = "ask_again_dates")
    private int askAgainDates;*/
    @ColumnInfo(name = "is_answered_correct")
    private int isAnsweredCorrect;
    @ColumnInfo(name = "is_answered_wrong")
    private int isAnsweredWrong;
    @ColumnInfo(name = "season_id")
    private int seasonId;
    @ColumnInfo(name = "season_name")
    private String seasonName;
    @ColumnInfo(name = "is_default_question", defaultValue = "1")
    private int isDefaultQuestion;

    @Ignore
    public LeitnerEntity(String question, String correctAnswer, String wrongAnswer1, String wrongAnswer2, int correctCounter, int wrongCounter, int isAnsweredCorrect, int isAnsweredWrong, int seasonId){
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.correctCounter = correctCounter;
        this.wrongCounter = wrongCounter;
//        this.askAgainDates = askAgainDates;
        this.isAnsweredCorrect = isAnsweredCorrect;
        this.isAnsweredWrong = isAnsweredWrong;
        this.seasonId = seasonId;
    }

    @Ignore
    public LeitnerEntity(int seasonId){
        this.seasonId = seasonId;
    }

    @Ignore
    public LeitnerEntity(int id, String question, String correctAnswer, String wrongAnswer1, String wrongAnswer2, int correctCounter, int wrongCounter, int isAnsweredCorrect, int isAnsweredWrong, int seasonId){
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.correctCounter = correctCounter;
        this.wrongCounter = wrongCounter;
//        this.askAgainDates = askAgainDates;
        this.isAnsweredCorrect = isAnsweredCorrect;
        this.isAnsweredWrong = isAnsweredWrong;
        this.seasonId = seasonId;
    }

    public LeitnerEntity() {}

    public void setCorrectCounter(int correctCounter) {
        this.correctCounter = correctCounter;
    }

    public void setWrongCounter(int wrongCounter) {
        this.wrongCounter = wrongCounter;
    }

//    public void setAskAgainDates(int askAgainDates) {
//        this.askAgainDates = askAgainDates;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public int getCorrectCounter() {
        return correctCounter;
    }

    public int getWrongCounter() {
        return wrongCounter;
    }

//    public int getAskAgainDates() {
//        return askAgainDates;
//    }

    public int getId() {
        return id;
    }

    public int getIsAnsweredWrong() {
        return isAnsweredWrong;
    }

    public void setIsAnsweredWrong(int isAnsweredWrong) {
        this.isAnsweredWrong = isAnsweredWrong;
    }

    public int getIsAnsweredCorrect() {
        return isAnsweredCorrect;
    }

    public void setIsAnsweredCorrect(int isAnsweredCorrect) {
        this.isAnsweredCorrect = isAnsweredCorrect;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public int getIsDefaultQuestion() {
        return isDefaultQuestion;
    }

    public void setIsDefaultQuestion(int isDefaultQuestion) {
        this.isDefaultQuestion = isDefaultQuestion;
    }

    public void setQuestion(String question) { this.question = question; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
    public void setWrongAnswer1(String wrongAnswer1) { this.wrongAnswer1 = wrongAnswer1; }
    public void setWrongAnswer2(String wrongAnswer2) { this.wrongAnswer2 = wrongAnswer2; }
}
