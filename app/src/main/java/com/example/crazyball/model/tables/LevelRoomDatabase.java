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
import com.example.crazyball.model.EComponentType;
import com.example.crazyball.model.tables.entities.ComponentEntityBuilder;
import com.example.crazyball.model.tables.entities.LevelComponentEntity;
import com.example.crazyball.model.tables.entities.LevelEntity;
import com.example.crazyball.model.tables.relations.LevelWithComponents;

@Database(entities = {LevelComponentEntity.class, LevelEntity.class}, version = 4, exportSchema = false)
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
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
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
//            levelDao.deleteAllLevels();
            // todo factory method for components
            // todo use builder pattern
            // todo retrofit get data

            LevelEntity level = new LevelEntity("Level I");
            level.setDifficultyLevel("Easy");
            long insertedId = levelDao.insert(level);

            levelComponentDao.insert(new ComponentEntityBuilder(insertedId)
                    .setType(EComponentType.target)
                    .setLocationX(30)
                    .setLocationY(6)
                    .setImageId(R.drawable.ic_yellow_2x2_check)
                    .build());

            level = new LevelEntity("Level II");
            level.setDifficultyLevel("Easy");
            insertedId = levelDao.insert(level);

            levelComponentDao.insert(new ComponentEntityBuilder(insertedId)
                    .setType(EComponentType.wall)
                    .setLocationX(12)
                    .setLocationY(0)
                    .setImageId(R.drawable.ic_wall_vert)
                    .build());

            levelComponentDao.insert(new ComponentEntityBuilder(insertedId)
                    .setType(EComponentType.wall)
                    .setLocationX(18)
                    .setLocationY(8)
                    .setImageId(R.drawable.ic_wall_vert)
                    .build());

            levelComponentDao.insert(new ComponentEntityBuilder(insertedId)
                    .setType(EComponentType.wall)
                    .setLocationX(24)
                    .setLocationY(0)
                    .setImageId(R.drawable.ic_wall_vert)
                    .build());

            for(int i = 0; i < 5; i++){
                levelComponentDao.insert(new ComponentEntityBuilder(insertedId)
                        .setType(EComponentType.trap)
                        .setLocationX(14 + 2 * i)
                        .setLocationY(0)
                        .setImageId(R.drawable.ic_red_2x2_trap)
                        .build());
            }

            levelComponentDao.insert(new ComponentEntityBuilder(insertedId)
                    .setType(EComponentType.target)
                    .setLocationX(30)
                    .setLocationY(6)
                    .setImageId(R.drawable.ic_yellow_2x2_check)
                    .build());

            level = new LevelEntity("Level III");
            level.setDifficultyLevel("Easy");
            insertedId = levelDao.insert(level);

            for(int i = 0; i < 5; i++){
                levelComponentDao.insert(new ComponentEntityBuilder(insertedId)
                        .setType(EComponentType.trap)
                        .setLocationX(8 + 2 * i)
                        .setLocationY(10 - 2 * i)
                        .setImageId(R.drawable.ic_8x2_red_stripes)
                        .build());
            }

            for(int i = 0; i < 5; i++){
                levelComponentDao.insert(new ComponentEntityBuilder(insertedId)
                        .setType(EComponentType.trap)
                        .setLocationX(21 + 2 * i)
                        .setLocationY(2 * i)
                        .setImageId(R.drawable.ic_8x2_red_stripes)
                        .build());
            }

            levelComponentDao.insert(new ComponentEntityBuilder(insertedId)
                    .setType(EComponentType.target)
                    .setLocationX(33)
                    .setLocationY(14)
                    .setImageId(R.drawable.ic_yellow_2x2_check)
                    .build());

            return null;
        }
    }
}
