package com.farshid.customleitnerbox.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.farshid.customleitnerbox.Database.LeitnerEntity;
import com.farshid.customleitnerbox.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.graphics.Color.rgb;

public class QuestionAdapter extends ListAdapter<LeitnerEntity, QuestionAdapter.QuestionViewHolder> {
    private static final String TAG = "QuestionAdapter";

    public OnItemClickListener listener;
    private Context context;
    private CorrectCounterUpdater correctCounterUpdater;
    private WrongCounterUpdater wrongCounterUpdater;
    private ItemRemover itemRemover;
//    private final List<LeitnerEntity> leitnerEntities;

    public QuestionAdapter(Context context/*, List<LeitnerEntity> leitnerEntities*/) {
        super(DIFF_CALLBACK);
        this.context = context;
//        this.leitnerEntities = leitnerEntities;
    }
   /* public void updateList(ArrayList<LeitnerEntity> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilCallback(this.leitnerEntities, newList));
        diffResult.dispatchUpdatesTo(this);
    }*/


    public static DiffUtil.ItemCallback<LeitnerEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<LeitnerEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull LeitnerEntity oldItem, @NonNull LeitnerEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull LeitnerEntity oldItem, @NonNull LeitnerEntity newItem) {
            return oldItem.getQuestion().equals(newItem.getQuestion()) &&
                    oldItem.getCorrectAnswer().equals(newItem.getCorrectAnswer()) &&
                    oldItem.getWrongAnswer1().equals((newItem.getWrongAnswer1())) &&
                    oldItem.getWrongAnswer2().equals(newItem.getWrongAnswer2()) &&
                    oldItem.getCorrectCounter() == newItem.getCorrectCounter() &&
                    oldItem.getWrongCounter() == newItem.getWrongCounter() &&
//                    oldItem.getAskAgainDates() == newItem.getAskAgainDates() &&
                    oldItem.getIsAnsweredCorrect() == newItem.getIsAnsweredCorrect() &&
                    oldItem.getIsAnsweredWrong() == newItem.getIsAnsweredWrong();
        }

    };

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_layout, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionViewHolder holder, final int position) {
        final LeitnerEntity leitnerEntity = getItem(position);
//        final LeitnerEntity leitnerEntity = leitnerEntities.get(position);
        holder.mainQuestion.setText(leitnerEntity.getQuestion());
        holder.correctAnswerCounter.setText(String.valueOf(leitnerEntity.getCorrectCounter()));
        holder.wrongAnswerCounter.setText(String.valueOf(leitnerEntity.getWrongCounter()));
//        holder.askagainCounter.setText(String.valueOf(leitnerEntity.getAskAgainDates()));
       /* holder.btnAnswer1.setText(leitnerEntity.getCorrectAnswer());
        holder.btnAnswer2.setText(leitnerEntity.getWrongAnswer1());
        holder.btnAnswer3.setText(leitnerEntity.getWrongAnswer2());*/
//        int switchPicker = randomGenerator.randomRecGen();
        final String correctAnswer = leitnerEntity.getCorrectAnswer();
        final String wrongAnswer1 = leitnerEntity.getWrongAnswer1();
        final String wrongAnswer2 = leitnerEntity.getWrongAnswer2();
        String[] answers = {correctAnswer, wrongAnswer1, wrongAnswer2};
        List<String> myArray = new ArrayList<String>(Arrays.asList(answers));
        Collections.shuffle(myArray);
        holder.btnAnswer1.setText(myArray.get(0));
        holder.btnAnswer2.setText(myArray.get(1));
        holder.btnAnswer3.setText(myArray.get(2));
        if(getItem(position).getIsDefaultQuestion() == 1){
            holder.btnDelete.setVisibility(View.INVISIBLE);
        }else{
            holder.btnDelete.setVisibility(View.VISIBLE);
        }
    }

    public LeitnerEntity getQuestionAt(int position) {
        return getItem(position);
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView mainQuestion, correctAnswerCounter, correctAnswer, wrongAnswerCounter,
                wrongAnswer;
        public Button btnAnswer1, btnAnswer2, btnAnswer3;

        public ImageButton btnDelete;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            mainQuestion = itemView.findViewById(R.id.txt_main_question);
            correctAnswerCounter = itemView.findViewById(R.id.txt_correct_answer_counter);
            correctAnswer = itemView.findViewById(R.id.txt_correct_answer);
            wrongAnswerCounter = itemView.findViewById(R.id.txt_wrong_answer_counter);
            wrongAnswer = itemView.findViewById(R.id.txt_wrong_answer);
            btnAnswer1 = itemView.findViewById(R.id.btn_answer_1);
            btnAnswer2 = itemView.findViewById(R.id.btn_answer_2);
            btnAnswer3 = itemView.findViewById(R.id.btn_answer_3);
            btnDelete = itemView.findViewById(R.id.img_btn_delete_question);

            /*if(getItem(getAdapterPosition()).getIsDefaultQuestion() == 1){
                btnDelete.setVisibility(View.INVISIBLE);
            }else{*/
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        if (itemRemover != null && position != RecyclerView.NO_POSITION) {
                            itemRemover.itemRemove(getItem(position));
                        }
                    }
                });
//            }

          /*  if(getAdapterPosition() != RecyclerView.NO_POSITION){*/
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION && getItem(getAdapterPosition()).getIsDefaultQuestion() == 0) {
                            listener.onItemClick(getItem(position));
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, getItemCount());
                        }
                    }
                });
//            }


            btnAnswer1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (correctCounterUpdater != null && position != RecyclerView.NO_POSITION) {
                        if (btnAnswer1.getText().equals(getItem(position).getCorrectAnswer())) {
                            Log.i(TAG, "onClick: " + position);
                            correctCounterUpdater.correctCounterUpdate(getQuestionAt(position));
                            if(getItem(position).getIsAnsweredCorrect() == 1){
                                notifyItemChanged(position);
                            }else {
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                            }
                        } else {
                            wrongCounterUpdater.wrongCounterUpdate(getQuestionAt(position));
                            if(getItem(position).getIsAnsweredWrong() == 1){
                                notifyItemChanged(position);
                            }
                            else {
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                            }
                        }
                    }
                }
            });
            btnAnswer2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (wrongCounterUpdater != null && position != RecyclerView.NO_POSITION) {
                        if (btnAnswer2.getText().equals(getItem(position).getCorrectAnswer())) {
                            Log.i(TAG, "onClick: " + position);
                            correctCounterUpdater.correctCounterUpdate(getQuestionAt(position));
                            if(getItem(position).getIsAnsweredCorrect() == 1){
                                notifyItemChanged(position);
                            }else {
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                            }
                        } else {
                            wrongCounterUpdater.wrongCounterUpdate(getQuestionAt(position));
                            if(getItem(position).getIsAnsweredWrong() == 1){
                                notifyItemChanged(position);
                            }
                            else {
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                            }

                        }
                    }
                }
            });
            btnAnswer3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (wrongCounterUpdater != null && position != RecyclerView.NO_POSITION) {
                        if (btnAnswer3.getText().equals(getItem(position).getCorrectAnswer())) {
                            Log.i(TAG, "onClick: " + position);
                            correctCounterUpdater.correctCounterUpdate(getQuestionAt(position));
                            if(getItem(position).getIsAnsweredCorrect() == 1){
                                notifyItemChanged(position);
                            }else {
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                            }
                        } else {
                            wrongCounterUpdater.wrongCounterUpdate(getQuestionAt(position));
                            if(getItem(position).getIsAnsweredWrong() == 1){
                                notifyItemChanged(position);
                            }
                            else {
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                            }
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(LeitnerEntity leitnerEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface CorrectCounterUpdater {
        void correctCounterUpdate(LeitnerEntity leitnerEntity);
    }

    public void setCorrectCounterUpdate(CorrectCounterUpdater updater) {
        this.correctCounterUpdater = updater;
    }

    public interface WrongCounterUpdater {
        void wrongCounterUpdate(LeitnerEntity leitnerEntity);
    }

    public void setWrongCounterUpdate(WrongCounterUpdater wrongCounterUpdater) {
        this.wrongCounterUpdater = wrongCounterUpdater;
    }

    public interface ItemRemover{
        void itemRemove(LeitnerEntity leitnerEntity);
    }

    public void setItemRemove(ItemRemover itemRemover){
        this.itemRemover = itemRemover;
    }
}
