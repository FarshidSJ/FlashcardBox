package com.farshid.customleitnerbox.Fragments;

import android.content.Intent;
import android.os.Bundle;
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
import com.farshid.customleitnerbox.Database.LeitnerEntity;
import com.farshid.customleitnerbox.Database.LeitnerViewModel;
import com.farshid.customleitnerbox.Adapters.QuestionAdapter;
import com.farshid.customleitnerbox.R;

import java.util.List;

public class CorrectsFragment extends Fragment {
    private LeitnerViewModel leitnerViewModel;
    public static final int EDIT_QUESTION_REQUEST = 2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        View rootView = inflater.inflate(R.layout.corrects_fragment_layout, container, false);
        RecyclerView questionRecyclerView = rootView.findViewById(R.id.corrects_recycler_view);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        final QuestionAdapter questionAdapter = new QuestionAdapter(getActivity());
        questionRecyclerView.setAdapter(questionAdapter);

        leitnerViewModel = ViewModelProviders.of(this).get(LeitnerViewModel.class);
        leitnerViewModel.getCorrects().observe(this, new Observer<List<LeitnerEntity>>() {
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
                }
                leitnerEntity.setAskAgainDates(leitnerEntity.getCorrectCounter() + 1);*/
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
                }
                leitnerEntity.setAskAgainDates(leitnerEntity.getCorrectCounter() + 1);*/
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
}
