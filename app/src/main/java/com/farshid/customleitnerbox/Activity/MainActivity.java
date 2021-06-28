package com.farshid.customleitnerbox.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.farshid.customleitnerbox.Database.LeitnerEntity;
import com.farshid.customleitnerbox.Database.LeitnerViewModel;
import com.farshid.customleitnerbox.R;
import com.farshid.customleitnerbox.Adapters.SeasonsAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_QUESTION_REQUEST = 1;
    public static final int EDIT_QUESTION_REQUEST = 2;
    public static final int UPDATE_CORRECT_COUNTER_REQUEST = 3;

    public static final String EXTRA_SEASON_ID =
            "com.farshid.customleitnerbox.EXTRA_SEASON_ID";
    public static final String EXTRA_SEASON_NAME =
            "com.farshid.customleitnerbox.EXTRA_SEASON_NAME";
    public static final String EXTRA_IS_DEAFAULT_QUESTION =
            "com.farshid.customleitnerbox.EXTRA_IS_DEFAULT_QUESTION";
    public ArrayList<LeitnerEntity> leitnerEntities = new ArrayList<>();
    CoordinatorLayout coordinatorLayout;
    private LeitnerViewModel leitnerViewModel;
    private static final String TAG = "MainActivity";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = findViewById(R.id.layout_seasons);


        RecyclerView seasonsRecyclerView = findViewById(R.id.seasons_recycler_view);
        seasonsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        final SeasonsAdapter seasonsAdapter = new SeasonsAdapter(MainActivity.this);
        seasonsRecyclerView.setAdapter(seasonsAdapter);

        leitnerViewModel = ViewModelProviders.of(this).get(LeitnerViewModel.class);
        leitnerViewModel.getAllSeasons().observe(this, new Observer<List<LeitnerEntity>>() {
            @Override
            public void onChanged(List<LeitnerEntity> leitnerEntities) {
                //Update RecyclerView
                seasonsAdapter.submitList(leitnerEntities);
//                questionAdapter.notifyDataSetChanged();
            }
        });

        seasonsAdapter.setOnItemClickListener(new SeasonsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LeitnerEntity leitnerEntity) {
                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                intent.putExtra(EXTRA_SEASON_ID, leitnerEntity.getSeasonId());
                intent.putExtra(EXTRA_SEASON_NAME,leitnerEntity.getSeasonName());
                intent.putExtra(EXTRA_IS_DEAFAULT_QUESTION,leitnerEntity.getIsDefaultQuestion());
                startActivity(intent);
            }
        });



        /*FloatingActionButton btnAddQuestion = findViewById(R.id.btn_add_question);
        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditQuestionActivity.class);
                startActivityForResult(intent, ADD_QUESTION_REQUEST);
            }
        });

        ViewPager viewPager = findViewById(R.id.main_view_pager);
        viewPager.setAdapter(new QuestionViewPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(viewPager);*/
//        Room.databaseBuilder(this, LeitnerDatabase.class, "TestDB").createFromAsset("questions.db").build();


        /*FloatingActionButton btnAddQuestion = findViewById(R.id.btn_add_question);
        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditQuestionActivity.class);
                startActivityForResult(intent, ADD_QUESTION_REQUEST);
            }
        });

        RecyclerView questionRecyclerView = findViewById(R.id.questions_recycler_view);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        final QuestionAdapter questionAdapter = new QuestionAdapter(MainActivity.this);
        questionRecyclerView.setAdapter(questionAdapter);

        leitnerViewModel = ViewModelProviders.of(this).get(LeitnerViewModel.class);
        leitnerViewModel.getAllQuestions().observe(this, new Observer<List<LeitnerEntity>>() {
            @Override
            public void onChanged(List<LeitnerEntity> leitnerEntities) {
                //Update RecyclerView
                questionAdapter.submitList(leitnerEntities);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                leitnerViewModel.deleteQuestion(questionAdapter.getQuestionAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Question deleted !", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(questionRecyclerView);

        questionAdapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LeitnerEntity leitnerEntity) {
                Intent intent = new Intent(MainActivity.this, AddEditQuestionActivity.class);
                intent.putExtra(AddEditQuestionActivity.EXTRA_ID, leitnerEntity.getId());
                intent.putExtra(AddEditQuestionActivity.EXTRA_QUESTION, leitnerEntity.getQuestion());
                intent.putExtra(AddEditQuestionActivity.EXTRA_CORRECT_ANSWER, leitnerEntity.getCorrectAnswer());
                intent.putExtra(AddEditQuestionActivity.EXTRA_WRONG_ANSWER_1, leitnerEntity.getWrongAnswer1());
                intent.putExtra(AddEditQuestionActivity.EXTRA_WRONG_ANSWER_2, leitnerEntity.getWrongAnswer2());
                intent.putExtra(AddEditQuestionActivity.EXTRA_CORRECT_COUNTER, leitnerEntity.getCorrectCounter());
                intent.putExtra(AddEditQuestionActivity.EXTRA_WRONG_COUNTER, leitnerEntity.getWrongCounter());
                intent.putExtra(AddEditQuestionActivity.EXTRA_ASK_AGAIN_DAYS, leitnerEntity.getAskAgainDates());
                intent.putExtra(AddEditQuestionActivity.EXTRA_IS_ANSWERED_CORRECT, leitnerEntity.getIsAnsweredCorrect());
                intent.putExtra(AddEditQuestionActivity.EXTRA_IS_ANSWERED_WRONG, leitnerEntity.getIsAnsweredWrong());
                startActivityForResult(intent, EDIT_QUESTION_REQUEST);
            }
        });

        questionAdapter.setCorrectCounterUpdate(new QuestionAdapter.CorrectCounterUpdater() {
            @Override
            public void correctCounterUpdate(LeitnerEntity leitnerEntity) {
                leitnerEntity.setCorrectCounter(leitnerEntity.getCorrectCounter() + 1);
                leitnerEntity.setIsAnsweredWrong(0);
                leitnerEntity.setIsAnsweredCorrect(1);
                if(leitnerEntity.getCorrectCounter() == 0){
                    leitnerEntity.setAskAgainDates(0);
                } else {
                    leitnerEntity.setAskAgainDates(leitnerEntity.getCorrectCounter() + 1);
                }
                leitnerViewModel.updateQuestion(leitnerEntity);
            }
        });

        questionAdapter.setWrongCounterUpdate(new QuestionAdapter.WrongCounterUpdater() {
            @Override
            public void wrongCounterUpdate(LeitnerEntity leitnerEntity) {
                leitnerEntity.setWrongCounter(leitnerEntity.getWrongCounter() + 1);
                leitnerEntity.setIsAnsweredCorrect(0);
                leitnerEntity.setIsAnsweredWrong(1);
                if(leitnerEntity.getWrongCounter() == 0 && leitnerEntity.getCorrectCounter() == 0){
                    leitnerEntity.setAskAgainDates(0);
                } else {
                    leitnerEntity.setAskAgainDates(1);
                }
                leitnerViewModel.updateQuestion(leitnerEntity);
            }
        });*/
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Press Back one more time to exit", Toast.LENGTH_SHORT).show();
        Snackbar.make(coordinatorLayout,"Press Back one more time to exit",Snackbar.LENGTH_LONG).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}
