package com.farshid.customleitnerbox.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LeitnerDao {
    @Insert
    void addQuestion(LeitnerEntity leitnerEntity);

    @Delete
    void deleteQuestion(LeitnerEntity leitnerEntity);

    @Update
    void updateQuestion(LeitnerEntity leitnerEntity);

    @Query("DELETE FROM leitner_box")
    void deleteAllQuestions();

    @Query("SELECT * FROM leitner_box WHERE season_id = :seasonId" )
    LiveData<List<LeitnerEntity>> getAllQuestions(int seasonId);

    @Query("Select * FROM leitner_box WHERE is_answered_correct = 1 AND season_id = :seasonId")
    LiveData<List<LeitnerEntity>> getCorrects(int seasonId);

    @Query("Select * FROM leitner_box WHERE is_answered_wrong = 1 AND season_id = :seasonId")
    LiveData<List<LeitnerEntity>> getWrongs(int seasonId);

//    @Query("SELECT DISTINCT id, season_id FROM leitner_box ORDER BY season_id ASC")
//    LiveData<List<LeitnerEntity>> getSeasons();

    @Query("SELECT * FROM leitner_box WHERE is_default_question = 1 GROUP BY season_id ")
    LiveData<List<LeitnerEntity>> getSeasons();

    @Query("SELECT DISTINCT id, season_id, correct_counter, wrong_counter, is_answered_correct, " +
            "is_answered_wrong, season_id, is_default_question FROM leitner_box GROUP BY season_id")
    List<LeitnerEntity> get();
}
