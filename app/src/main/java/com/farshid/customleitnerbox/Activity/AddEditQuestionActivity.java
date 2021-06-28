package com.farshid.customleitnerbox.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.farshid.customleitnerbox.R;

public class AddEditQuestionActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.farshid.customleitnerbox.EXTRA_ID";
    public static final String EXTRA_QUESTION =
            "com.farshid.customleitnerbox.EXTRA_QUESTION";
    public static final String EXTRA_CORRECT_ANSWER =
            "com.farshid.customleitnerbox.EXTRA_CORRECT_ANSWER";
    public static final String EXTRA_WRONG_ANSWER_1 =
            "com.farshid.customleitnerbox.EXTRA_WRONG_ANSWER_1";
    public static final String EXTRA_WRONG_ANSWER_2 =
            "com.farshid.customleitnerbox.EXTRA_WRONG_ANSWER_2";
    public static final String EXTRA_CORRECT_COUNTER =
            "com.farshid.customleitnerbox.EXTRA_CORRECT_COUNTER";
    public static final String EXTRA_WRONG_COUNTER =
            "com.farshid.customleitnerbox.EXTRA_WRONG_COUNTER";
    public static final String EXTRA_ASK_AGAIN_DAYS =
            "com.farshid.customleitnerbox.EXTRA_ASK_AGAIN_DAYS";
    public static final String EXTRA_IS_ANSWERED_CORRECT =
            "com.farshid.customleitnerbox.EXTRA_IS_ANSWERED_CORRECT";
    public static final String EXTRA_IS_ANSWERED_WRONG =
            "com.farshid.customleitnerbox.EXTRA_IS_ANSWERED_WRONG";

    private EditText editTxtQuestion, editTxtCorrectAnswer, editTxtWrongAnswer1, editTxtWrongAnswer2;
    private int correctCounter, wrongCounter, askAgainDays, isAnsweredCorrect, isAnsweredWrong, seasonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        editTxtQuestion = findViewById(R.id.edit_txt_question);
        editTxtCorrectAnswer = findViewById(R.id.edit_txt_correct_answer);
        editTxtWrongAnswer1 = findViewById(R.id.edit_txt_wrong_answer_1);
        editTxtWrongAnswer2 = findViewById(R.id.edit_txt_wrong_answer_2);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_1);

        Intent intent = getIntent();
        seasonId = intent.getIntExtra(MainActivity.EXTRA_SEASON_ID,0);
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Question");
            editTxtQuestion.setText(intent.getStringExtra(EXTRA_QUESTION));
            editTxtCorrectAnswer.setText(intent.getStringExtra(EXTRA_CORRECT_ANSWER));
            editTxtWrongAnswer1.setText(intent.getStringExtra(EXTRA_WRONG_ANSWER_1));
            editTxtWrongAnswer2.setText(intent.getStringExtra(EXTRA_WRONG_ANSWER_2));
            correctCounter = intent.getIntExtra(EXTRA_CORRECT_COUNTER, 0);
            wrongCounter = intent.getIntExtra(EXTRA_WRONG_COUNTER, 0);
            askAgainDays = intent.getIntExtra(EXTRA_ASK_AGAIN_DAYS, 0);
            isAnsweredCorrect = intent.getIntExtra(EXTRA_IS_ANSWERED_CORRECT, 0);
            isAnsweredWrong = intent.getIntExtra(EXTRA_IS_ANSWERED_WRONG, 0);
        } else {
            setTitle("Add Question");
        }
    }

    private void saveQuestion() {
        String question = editTxtQuestion.getText().toString();
        String correctAnswer = editTxtCorrectAnswer.getText().toString();
        String wrongAnswer1 = editTxtWrongAnswer1.getText().toString();
        String wrongAnswer2 = editTxtWrongAnswer2.getText().toString();

        if (question.trim().isEmpty() || correctAnswer.trim().isEmpty() || wrongAnswer1.trim().isEmpty() || wrongAnswer2.trim().isEmpty()) {
            Toast.makeText(this, "Please fill the empty fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(correctAnswer.equals(wrongAnswer1) || correctAnswer.equals(wrongAnswer2)){
            Toast.makeText(this, "Please make sure you entered a proper Correct Answer !", Toast.LENGTH_SHORT).show();
            return;
        } else if (wrongAnswer1.equals(wrongAnswer2)){
            Toast.makeText(this, "Wrong Answers are equal to each other !", Toast.LENGTH_SHORT).show();
            return;
        } else if (correctAnswer.equals(question) || wrongAnswer1.equals(question) || wrongAnswer2.equals(question)){
            Toast.makeText(this, "Answers cannot be the same as Question !", Toast.LENGTH_SHORT).show();
            return;
        }
        question = question.substring(0,1).toUpperCase() + question.substring(1);
        Intent questionData = new Intent();
        questionData.putExtra(EXTRA_QUESTION, question);
        questionData.putExtra(EXTRA_CORRECT_ANSWER, correctAnswer);
        questionData.putExtra(EXTRA_WRONG_ANSWER_1, wrongAnswer1);
        questionData.putExtra(EXTRA_WRONG_ANSWER_2, wrongAnswer2);
        questionData.putExtra(EXTRA_CORRECT_COUNTER, correctCounter);
        questionData.putExtra(EXTRA_WRONG_COUNTER, wrongCounter);
//        questionData.putExtra(EXTRA_ASK_AGAIN_DAYS, askAgainDays);
        questionData.putExtra(EXTRA_IS_ANSWERED_CORRECT, isAnsweredCorrect);
        questionData.putExtra(EXTRA_IS_ANSWERED_WRONG, isAnsweredWrong);
        questionData.putExtra(MainActivity.EXTRA_SEASON_ID, seasonId);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            questionData.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, questionData);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_question_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_question:
                saveQuestion();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }
}
