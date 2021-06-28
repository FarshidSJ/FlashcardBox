package com.farshid.customleitnerbox.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(exportSchema = false, entities = LeitnerEntity.class, version = 2)
public abstract class LeitnerDatabase extends RoomDatabase {

    private static LeitnerDatabase instance;

    public abstract LeitnerDao leitnerDao();

    public static synchronized LeitnerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    LeitnerDatabase.class, "testDB")
                    .createFromAsset("database/questions.db")
//                    .fallbackToDestructiveMigration()
//                    .createFromAsset("questions.db")
                    /*.addCallback(roomCallback)*/
                    .build();
        }
        return instance;
    }

    /*private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };*/

    /*private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private LeitnerDao leitnerDao;

        private PopulateDbAsyncTask(LeitnerDatabase db) {
            leitnerDao = db.leitnerDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            leitnerDao.addQuestion(new LeitnerEntity("Question 1", "Correct answer 1",
                    "First wrong Answer 1", "Second wrong answer 1",
                    0, 0, 0, 0, 0));
            leitnerDao.addQuestion(new LeitnerEntity("Question 2", "Correct answer 2",
                    "First wrong Answer 2", "Second wrong answer 2",
                    0, 0, 0, 0, 0));
            leitnerDao.addQuestion(new LeitnerEntity("Question 3", "Correct answer 3",
                    "First wrong Answer 3", "Second wrong answer 3",
                    0, 0, 0, 0, 0));
            return null;
        }
    }*/
}
