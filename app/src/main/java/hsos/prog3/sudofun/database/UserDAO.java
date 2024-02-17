package hsos.prog3.sudofun.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;



@Dao
public interface UserDAO {
    @Query("SELECT * FROM userentity WHERE username LIKE :username LIMIT 1")
    UserEntity findByName(String username);

    @Query("SELECT COUNT(*) FROM userentity WHERE username LIKE :username")
    int countByName(String username);

    @Query("SELECT * FROM userentity ORDER BY highscore_easy DESC LIMIT 5")
    LiveData<List<UserEntity>> getEasyHighscores();

    @Query("SELECT * FROM userentity ORDER BY highscore_medium DESC LIMIT 5")
    LiveData<List<UserEntity>> getMediumHighscores();

    @Query("SELECT * FROM userentity ORDER BY highscore_hard DESC LIMIT 5")
    LiveData<List<UserEntity>> getHardHighscores();

    @Update
    void updateUser(UserEntity user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserEntity... users);

    @Delete
    void delete(UserEntity user);
}
