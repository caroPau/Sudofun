package hsos.prog3.sudofun.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

import hsos.prog3.sudofun.model.UserEntity;


@Dao
public interface UserDAO {
    @Query("SELECT * FROM userentity WHERE username = :username")
    LiveData<UserEntity> findByName(String username);

    @Query("SELECT COUNT(*) FROM userentity WHERE username LIKE :username")
    int countByName(String username);

    @Query("SELECT * FROM userentity WHERE highscore_easy > 0 ORDER BY highscore_easy ASC LIMIT 5")
    LiveData<List<UserEntity>> getEasyHighscores();

    @Query("SELECT * FROM userentity WHERE highscore_medium > 0 ORDER BY highscore_medium ASC LIMIT 5")
    LiveData<List<UserEntity>> getMediumHighscores();

    @Query("SELECT * FROM userentity WHERE highscore_hard > 0 ORDER BY highscore_hard ASC LIMIT 5")
    LiveData<List<UserEntity>> getHardHighscores();


    @Update
    void updateUser(UserEntity user);

    @Insert(onConflict = OnConflictStrategy.NONE)
    void insertAll(UserEntity... users);

    @Delete
    void delete(UserEntity user);
}
