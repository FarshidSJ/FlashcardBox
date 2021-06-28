package com.farshid.customleitnerbox.Database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.farshid.customleitnerbox.Activity.QuestionsActivity;

import java.util.List;

public class LeitnerRepository {

    private LeitnerDao leitnerDao;
    private LiveData<List<LeitnerEntity>> allQuestions;
    private LiveData<List<LeitnerEntity>> correctQuestions;
    private LiveData<List<LeitnerEntity>> wrongQuestions;
    private LiveData<List<LeitnerEntity>> allSeasons;
    private static final String TAG = "LeitnerRepository";
    int seasonId = LeitnerViewModel.seasonId;
    public LeitnerRepository(Application application){
        LeitnerDatabase leitnerDatabase = LeitnerDatabase.getInstance(application);
        leitnerDao = leitnerDatabase.leitnerDao();
        Log.e(TAG, "LeitnerRepository: "+ seasonId );
        allQuestions = leitnerDao.getAllQuestions(QuestionsActivity.seasonId);
        correctQuestions = leitnerDao.getCorrects(QuestionsActivity.seasonId);
        wrongQuestions = leitnerDao.getWrongs(QuestionsActivity.seasonId);
        allSeasons = leitnerDao.getSeasons();
    }

    public void addQuestion(LeitnerEntity leitnerEntity){
        new AddQuestionAsyncTask(leitnerDao).execute(leitnerEntity);
    }

    public void deleteQuestion(LeitnerEntity leitnerEntity){
        new DeleteQuestionAsyncTask(leitnerDao).execute(leitnerEntity);
    }

    public void updateQuestion(LeitnerEntity leitnerEntity){
        new UpdateQuestionAsyncTask(leitnerDao).execute(leitnerEntity);
    }

    public void deleteAllQuestions(){
        new DeleteAllQuestionsAsyncTask(leitnerDao).execute();
    }

    public LiveData<List<LeitnerEntity>> getAllQuestions(){
        return allQuestions;
    }

    public LiveData<List<LeitnerEntity>> getCorrects(){return correctQuestions; }

    public LiveData<List<LeitnerEntity>> getWrongs(){return wrongQuestions; }

    public LiveData<List<LeitnerEntity>> getAllSeasons(){return allSeasons;}

    private static class AddQuestionAsyncTask extends AsyncTask<LeitnerEntity, Void, Void>{
        private LeitnerDao leitnerDao;
        private AddQuestionAsyncTask(LeitnerDao leitnerDao){
            this.leitnerDao = leitnerDao;
        }

        @Override
        protected Void doInBackground(LeitnerEntity... leitnerEntities) {
            leitnerDao.addQuestion(leitnerEntities[0]);
            return null;
        }
    }

    private static class DeleteQuestionAsyncTask extends AsyncTask<LeitnerEntity, Void, Void>{
        private LeitnerDao leitnerDao;
        private DeleteQuestionAsyncTask(LeitnerDao leitnerDao){
            this.leitnerDao = leitnerDao;
        }

        @Override
        protected Void doInBackground(LeitnerEntity... leitnerEntities) {
            leitnerDao.deleteQuestion(leitnerEntities[0]);
            return null;
        }
    }

    private static class UpdateQuestionAsyncTask extends AsyncTask<LeitnerEntity, Void, Void>{
        private LeitnerDao leitnerDao;
        private UpdateQuestionAsyncTask(LeitnerDao leitnerDao){
            this.leitnerDao = leitnerDao;
        }

        @Override
        protected Void doInBackground(LeitnerEntity... leitnerEntities) {
            leitnerDao.updateQuestion(leitnerEntities[0]);
            return null;
        }
    }

    private static class DeleteAllQuestionsAsyncTask extends AsyncTask<Void, Void, Void>{
        private LeitnerDao leitnerDao;
        private DeleteAllQuestionsAsyncTask (LeitnerDao leitnerDao){
            this.leitnerDao = leitnerDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            leitnerDao.deleteAllQuestions();
            return null;
        }
    }

}
