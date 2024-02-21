package hsos.prog3.sudofun.model;

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
    @Query("SELECT * FROM userentity WHERE username LIKE :username LIMIT 1")
    UserEntity findByName(String username);

    @Query("SELECT COUNT(*) FROM userentity WHERE username LIKE :username")
    int countByName(String username);

    @Query("SELECT * FROM userentity ORDER BY highscore_easy DESC LIMIT 5")
    List<UserEntity> getEasyHighscores();

    @Query("SELECT * FROM userentity ORDER BY highscore_medium DESC LIMIT 5")
    List<UserEntity> getMediumHighscores();

    @Query("SELECT * FROM userentity ORDER BY highscore_hard DESC LIMIT 5")
    List<UserEntity> getHardHighscores();

    @Update
    void updateUser(UserEntity user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserEntity... users);

    @Delete
    void delete(UserEntity user);
}
