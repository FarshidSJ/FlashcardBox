package com.farshid.customleitnerbox.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.farshid.customleitnerbox.Database.LeitnerEntity;
import com.farshid.customleitnerbox.Database.LeitnerViewModel;
import com.farshid.customleitnerbox.Adapters.QuestionViewPagerAdapter;
import com.farshid.customleitnerbox.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import static com.farshid.customleitnerbox.Activity.MainActivity.EXTRA_IS_DEAFAULT_QUESTION;
import static com.farshid.customleitnerbox.Activity.MainActivity.EXTRA_SEASON_ID;
import static com.farshid.customleitnerbox.Activity.MainActivity.EXTRA_SEASON_NAME;

public class QuestionsActivity extends AppCompatActivity {
    private static final String TAG = "QuestionsActivity";
    public static final int ADD_QUESTION_REQUEST = 1;
    public static final int EDIT_QUESTION_REQUEST = 2;
    public static int seasonId, isDefaultQuestion;
    public static String seasonName;
    private LeitnerViewModel leitnerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

//        getActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        Intent intent = getIntent();
        seasonId = intent.getIntExtra(EXTRA_SEASON_ID, 0);
        Log.e(TAG, "onCreate******: " + seasonId);
        seasonName = intent.getStringExtra(EXTRA_SEASON_NAME);
        isDefaultQuestion = intent.getIntExtra(EXTRA_IS_DEAFAULT_QUESTION, 1);

        FloatingActionButton btnAddQuestion = findViewById(R.id.btn_add_question);
        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionsActivity.this, AddEditQuestionActivity.class);
                intent.putExtra(EXTRA_SEASON_ID, seasonId);
                startActivityForResult(intent, ADD_QUESTION_REQUEST);
            }
        });

        ViewPager viewPager = findViewById(R.id.main_view_pager);
        viewPager.setAdapter(new QuestionViewPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        leitnerViewModel = ViewModelProviders.of(this).get(LeitnerViewModel.class);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + resultCode );
        if (requestCode == ADD_QUESTION_REQUEST && resultCode == RESULT_OK) {
            String question = data.getStringExtra(AddEditQuestionActivity.EXTRA_QUESTION);
            String correctAnswer = data.getStringExtra(AddEditQuestionActivity.EXTRA_CORRECT_ANSWER);
            String wrongAnswer1 = data.getStringExtra(AddEditQuestionActivity.EXTRA_WRONG_ANSWER_1);
            String wrongAnswer2 = data.getStringExtra(AddEditQuestionActivity.EXTRA_WRONG_ANSWER_2);
            int correctCounter = data.getIntExtra(AddEditQuestionActivity.EXTRA_CORRECT_COUNTER, 0);
            int wrongCounter = data.getIntExtra(AddEditQuestionActivity.EXTRA_WRONG_COUNTER, 0);
            int isAnsweredCorrect = data.getIntExtra(AddEditQuestionActivity.EXTRA_IS_ANSWERED_CORRECT, 0);
            int isAnsweredWrong = data.getIntExtra(AddEditQuestionActivity.EXTRA_IS_ANSWERED_WRONG, 0);
            int seasonId = data.getIntExtra(EXTRA_SEASON_ID, 0);
            Log.e(TAG, "SEASON ID"+ seasonId );
            LeitnerEntity leitnerEntity = new LeitnerEntity(question, correctAnswer, wrongAnswer1, wrongAnswer2, correctCounter, wrongCounter, isAnsweredCorrect, isAnsweredWrong,seasonId);
            leitnerViewModel.addQuestion(leitnerEntity);

            Toast.makeText(this, "Question Saved !", Toast.LENGTH_SHORT).show();
        } /*else if (requestCode == EDIT_QUESTION_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditQuestionActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(this, "Question can't be updated !", Toast.LENGTH_SHORT).show();
                return;
            }
            String question = data.getStringExtra(AddEditQuestionActivity.EXTRA_QUESTION);
            String correctAnswer = data.getStringExtra(AddEditQuestionActivity.EXTRA_CORRECT_ANSWER);
            String wrongAnswer1 = data.getStringExtra(AddEditQuestionActivity.EXTRA_WRONG_ANSWER_1);
            String wrongAnswer2 = data.getStringExtra(AddEditQuestionActivity.EXTRA_WRONG_ANSWER_2);
            int correctCounter = data.getIntExtra(AddEditQuestionActivity.EXTRA_CORRECT_COUNTER, 0);
            int wrongCounter = data.getIntExtra(AddEditQuestionActivity.EXTRA_WRONG_COUNTER, 0);
            int isAnsweredCorrect = data.getIntExtra(AddEditQuestionActivity.EXTRA_IS_ANSWERED_CORRECT, 0);
            int isAnsweredWrong = data.getIntExtra(AddEditQuestionActivity.EXTRA_IS_ANSWERED_WRONG, 0);
            int seasonId = data.getIntExtra(EXTRA_SEASON_ID, 0);
            LeitnerEntity leitnerEntity = new LeitnerEntity(question, correctAnswer, wrongAnswer1, wrongAnswer2, correctCounter, wrongCounter, isAnsweredCorrect, isAnsweredWrong,seasonId);
            Log.e(TAG, "onActivityResult: " + leitnerEntity.getIsAnsweredCorrect());
            leitnerEntity.setId(id);
            leitnerViewModel.updateQuestion(leitnerEntity);
            Toast.makeText(this, "Question Updated !", Toast.LENGTH_SHORT).show();
        }*/else if (resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Question NOT Saved !", Toast.LENGTH_SHORT).show();
        }
    }
}
