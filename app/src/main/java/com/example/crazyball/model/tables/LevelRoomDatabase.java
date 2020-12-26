package com.example.crazyball.model.tables;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.crazyball.R;
import com.example.crazyball.model.tables.entities.LevelComponentEntity;
import com.example.crazyball.model.tables.entities.LevelEntity;
import com.example.crazyball.model.tables.relations.LevelWithComponents;

@Database(entities = {LevelComponentEntity.class, LevelEntity.class}, version = 3, exportSchema = false)
public abstract class LevelRoomDatabase extends RoomDatabase {
    public abstract LevelDao levelDao();
    public abstract LevelComponentDao levelComponentDao();
    public static LevelRoomDatabase INSTANCE;

    public static LevelRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (LevelRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LevelRoomDatabase.class, "level_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(databaseCreatedCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public static RoomDatabase.Callback databaseCreatedCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    // todo async task is deprecated
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final LevelDao levelDao;
        private final LevelComponentDao levelComponentDao;

        public PopulateDbAsync(LevelRoomDatabase db) {
            levelDao = db.levelDao();
            levelComponentDao = db.levelComponentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            levelDao.deleteAllLevels();
            LevelEntity level = new LevelEntity("5th level");
            level.setDifficultyLevel("Hard");
            long insertedId = levelDao.insert(level);

            // todo factory method for components
            // todo use builder pattern
            // todo retrofit get data

            LevelComponentEntity component = new LevelComponentEntity(
                    "wall", 0, 10, R.drawable.ic_wall_hor, insertedId);
            levelComponentDao.insert(component);

            component = new LevelComponentEntity(
                    "wall", 8, 14, R.drawable.ic_wall_hor, insertedId);
            levelComponentDao.insert(component);

            component = new LevelComponentEntity(
                    "wall", 0, 18, R.drawable.ic_wall_hor, insertedId);
            levelComponentDao.insert(component);

            level = new LevelEntity("6th level");
            level.setDifficultyLevel("Medium");
            insertedId = levelDao.insert(level);

            // todo factory method for components
            // todo use builder pattern
            // todo retrofit get data

            component = new LevelComponentEntity(
                    "wall", 30, 30, R.drawable.ic_wall_hor, insertedId);
            levelComponentDao.insert(component);

            component = new LevelComponentEntity(
                    "wall", 45, 330, R.drawable.ic_wall_hor, insertedId);
            levelComponentDao.insert(component);

            return null;
        }
    }
}
