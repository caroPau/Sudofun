package hsos.prog3.sudofun.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM userentity")
    List<UserEntity> getAll();

    @Query("SELECT * FROM userentity WHERE username LIKE :username LIMIT 1")
    UserEntity findByName(String username);

    @Upsert
    void insertAll(UserEntity... users);

    @Delete
    void delete(UserEntity user);
}
