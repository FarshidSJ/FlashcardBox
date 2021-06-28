package com.farshid.customleitnerbox.Database;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LeitnerViewModel extends AndroidViewModel {
    private LeitnerRepository leitnerRepository;
    private LiveData<List<LeitnerEntity>> allQuestions;
    private LiveData<List<LeitnerEntity>> correctQuestions;
    private LiveData<List<LeitnerEntity>> wrongQuestions;
    private LiveData<List<LeitnerEntity>> allSeasons;
    public static int seasonId;
    private static final String TAG = "LeitnerViewModel";
    public LeitnerViewModel(@NonNull Application application) {
        super(application);
        leitnerRepository = new LeitnerRepository(application);
        allQuestions = leitnerRepository.getAllQuestions();
        correctQuestions = leitnerRepository.getCorrects();
        wrongQuestions = leitnerRepository.getWrongs();
        allSeasons = leitnerRepository.getAllSeasons();
    }

    public void addQuestion(LeitnerEntity leitnerEntity){
        leitnerRepository.addQuestion(leitnerEntity);
    }

    public void deleteQuestion(LeitnerEntity leitnerEntity){
        leitnerRepository.deleteQuestion(leitnerEntity);
    }

    public void updateQuestion(LeitnerEntity leitnerEntity){
        leitnerRepository.updateQuestion(leitnerEntity);
    }

    public LiveData<List<LeitnerEntity>> getAllQuestions() {
        return allQuestions;
    }

    public LiveData<List<LeitnerEntity>> getCorrects(){ return correctQuestions; }

    public LiveData<List<LeitnerEntity>> getWrongs(){ return wrongQuestions; }

    public LiveData<List<LeitnerEntity>> getAllSeasons(){ return allSeasons; }

    public LiveData<List<LeitnerEntity>> getAllQuestionsTest(int seasonId) {
        LeitnerViewModel.seasonId = seasonId;
        Log.e(TAG, "getAllQuestionsTest: " + seasonId );
        return leitnerRepository.getAllQuestions();
    }
}
