package hsos.prog3.sudofun.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;


@Dao
public interface UserDAO {
    @Query("SELECT * FROM userentity WHERE username LIKE :username LIMIT 1")
    UserEntity findByName(String username);

    @Query("SELECT COUNT(*) FROM userentity WHERE username LIKE :username")
    int countByName(String username);

    @Update
    void updateUser(UserEntity user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserEntity... users);

    @Delete
    void delete(UserEntity user);
}
