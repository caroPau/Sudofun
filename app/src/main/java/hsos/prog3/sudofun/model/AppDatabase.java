package hsos.prog3.sudofun.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Quelle: https://developer.android.com/codelabs/android-room-with-a-view#7
@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();

    private static volatile AppDatabase INSTANCE;
    private static final int THREADS = 4;
    public static final ExecutorService databaseWriter = Executors.newFixedThreadPool(THREADS);

    public static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database").addCallback(appDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static AppDatabase.Callback appDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriter.execute(() ->{
                UserDAO dao = INSTANCE.userDAO();
                UserEntity user = new UserEntity("Caro", 0, 0, 0, 0, 0, 0);
                dao.insertAll(user);
            });
        }
    };
}
