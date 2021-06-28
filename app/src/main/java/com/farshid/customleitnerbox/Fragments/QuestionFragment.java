package com.farshid.customleitnerbox.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farshid.customleitnerbox.Activity.AddEditQuestionActivity;
import com.farshid.customleitnerbox.Activity.MainActivity;
import com.farshid.customleitnerbox.Activity.QuestionsActivity;
import com.farshid.customleitnerbox.Database.LeitnerEntity;
import com.farshid.customleitnerbox.Database.LeitnerViewModel;
import com.farshid.customleitnerbox.Adapters.QuestionAdapter;
import com.farshid.customleitnerbox.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.farshid.customleitnerbox.Activity.MainActivity.EXTRA_SEASON_ID;

public class QuestionFragment extends Fragment {
    private LeitnerViewModel leitnerViewModel;
    public static final int ADD_QUESTION_REQUEST = 1;
    public static final int EDIT_QUESTION_REQUEST = 2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        View rootView = inflater.inflate(R.layout.question_fragment_layout, container, false);
        /*FloatingActionButton btnAddQuestion = rootView.findViewById(R.id.btn_add_question);
        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddEditQuestionActivity.class);
                startActivityForResult(intent, ADD_QUESTION_REQUEST);
            }
        });*/
        RecyclerView questionRecyclerView = rootView.findViewById(R.id.questions_recycler_view);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        final QuestionAdapter questionAdapter = new QuestionAdapter(getActivity());
        questionRecyclerView.setAdapter(questionAdapter);
//        Log.i(TAG, "onCreateView: "+ questionsViewModel.getAllQuestions());
/*questionsViewModel.getAllQuestions().observe(this, new Observer<List<LeitnerEntity>>() {
    @Override
    public void onChanged(List<LeitnerEntity> leitnerEntities) {
        questionAdapter.submitList(leitnerEntities);
    }
});*/
        Log.e(TAG, "onCreateView====: " + QuestionsActivity.seasonId );
        leitnerViewModel = ViewModelProviders.of(this).get(LeitnerViewModel.class);
        /*LiveData<List<LeitnerEntity>> lvQuestions = leitnerViewModel.getAllQuestionsTest(QuestionsActivity.seasonId);
        *//*leitnerViewModel.getAllQuestionsTest().observe(this, new Observer<List<LeitnerEntity>>() {
            @Override
            public void onChanged(List<LeitnerEntity> leitnerEntities) {
                //Update RecyclerView
                questionAdapter.submitList(leitnerEntities);
//                questionAdapter.notifyDataSetChanged();
            }
        });*//*
        lvQuestions.observe(this, new Observer<List<LeitnerEntity>>() {
            @Override
            public void onChanged(List<LeitnerEntity> leitnerEntities) {
                questionAdapter.submitList(leitnerEntities);
            }
        });*/
        leitnerViewModel.getAllQuestions().observe(this, new Observer<List<LeitnerEntity>>() {
            @Override
            public void onChanged(List<LeitnerEntity> leitnerEntities) {
                //Update RecyclerView
                questionAdapter.submitList(leitnerEntities);
//                questionAdapter.notifyDataSetChanged();
            }
        });

        /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                leitnerViewModel.deleteQuestion(questionAdapter.getQuestionAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Question deleted !", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(questionRecyclerView);*/

        questionAdapter.setOnItemClickListener(new QuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LeitnerEntity leitnerEntity) {
                Intent intent = new Intent(getActivity(), AddEditQuestionActivity.class);
                intent.putExtra(AddEditQuestionActivity.EXTRA_ID, leitnerEntity.getId());
                intent.putExtra(AddEditQuestionActivity.EXTRA_QUESTION, leitnerEntity.getQuestion());
                intent.putExtra(AddEditQuestionActivity.EXTRA_CORRECT_ANSWER, leitnerEntity.getCorrectAnswer());
                intent.putExtra(AddEditQuestionActivity.EXTRA_WRONG_ANSWER_1, leitnerEntity.getWrongAnswer1());
                intent.putExtra(AddEditQuestionActivity.EXTRA_WRONG_ANSWER_2, leitnerEntity.getWrongAnswer2());
                intent.putExtra(AddEditQuestionActivity.EXTRA_CORRECT_COUNTER, leitnerEntity.getCorrectCounter());
                intent.putExtra(AddEditQuestionActivity.EXTRA_WRONG_COUNTER, leitnerEntity.getWrongCounter());
//                intent.putExtra(AddEditQuestionActivity.EXTRA_ASK_AGAIN_DAYS, leitnerEntity.getAskAgainDates());
                intent.putExtra(AddEditQuestionActivity.EXTRA_IS_ANSWERED_CORRECT, leitnerEntity.getIsAnsweredCorrect());
                intent.putExtra(AddEditQuestionActivity.EXTRA_IS_ANSWERED_WRONG, leitnerEntity.getIsAnsweredWrong());
                intent.putExtra(MainActivity.EXTRA_SEASON_ID, leitnerEntity.getSeasonId());
                startActivityForResult(intent, EDIT_QUESTION_REQUEST);
            }
        });

        questionAdapter.setCorrectCounterUpdate(new QuestionAdapter.CorrectCounterUpdater() {
            @Override
            public void correctCounterUpdate(LeitnerEntity leitnerEntity) {
                leitnerEntity.setCorrectCounter(leitnerEntity.getCorrectCounter() + 1);
                leitnerEntity.setIsAnsweredWrong(0);
                leitnerEntity.setIsAnsweredCorrect(1);
                /*if(leitnerEntity.getCorrectCounter() == 0){
                    leitnerEntity.setAskAgainDates(0);
                } else {
                    leitnerEntity.setAskAgainDates(leitnerEntity.getCorrectCounter() + 1);
                }*/
                leitnerViewModel.updateQuestion(leitnerEntity);
            }
        });
        questionAdapter.setWrongCounterUpdate(new QuestionAdapter.WrongCounterUpdater() {
            @Override
            public void wrongCounterUpdate(LeitnerEntity leitnerEntity) {
                leitnerEntity.setWrongCounter(leitnerEntity.getWrongCounter() + 1);
                leitnerEntity.setIsAnsweredCorrect(0);
                leitnerEntity.setIsAnsweredWrong(1);
                /*if(leitnerEntity.getWrongCounter() == 0 && leitnerEntity.getCorrectCounter() == 0){
                    leitnerEntity.setAskAgainDates(0);
                } else {
                    leitnerEntity.setAskAgainDates(1);
                }*/
                leitnerViewModel.updateQuestion(leitnerEntity);
            }
        });

        questionAdapter.setItemRemove(new QuestionAdapter.ItemRemover() {
            @Override
            public void itemRemove(LeitnerEntity leitnerEntity) {
                leitnerViewModel.deleteQuestion(leitnerEntity);
                Toast.makeText(getActivity(), "Question deleted !", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == EDIT_QUESTION_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditQuestionActivity.EXTRA_ID, -1);
            if(id == -1){
                Toast.makeText(getActivity(), "Question can't be updated !", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "Question Updated !", Toast.LENGTH_SHORT).show();
        }
    }
}
