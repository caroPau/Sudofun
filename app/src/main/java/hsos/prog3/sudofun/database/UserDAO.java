package hsos.prog3.sudofun.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

import hsos.prog3.sudofun.model.UserEntity;

/**
 * DAO der Entität UserEntity mit Methoden für Datenbankanfragen.
 *
 * @author C.Paul
 */
@Dao
public interface UserDAO {
    /**
     * Findet den Benutzer anhand der ID.
     *
     * @param username Benutzername
     * @return Benutzer
     */
    @Query("SELECT * FROM userentity WHERE username = :username")
    LiveData<UserEntity> findByName(String username);

    /**
     * Liest die 5 besten Spieler auf Level Easy aus.
     *
     * @return Liste der Spieler
     */
    @Query("SELECT * FROM userentity WHERE highscore_easy > 0 ORDER BY highscore_easy ASC LIMIT 5")
    LiveData<List<UserEntity>> getEasyHighscores();

    /**
     * Liest die 5 besten Spieler auf Level Medium aus.
     *
     * @return Liste der Spieler
     */
    @Query("SELECT * FROM userentity WHERE highscore_medium > 0 ORDER BY highscore_medium ASC LIMIT 5")
    LiveData<List<UserEntity>> getMediumHighscores();

    /**
     * Liest die 5 besten Spieler auf Level Hard aus.
     *
     * @return Liste der Spieler
     */
    @Query("SELECT * FROM userentity WHERE highscore_hard > 0 ORDER BY highscore_hard ASC LIMIT 5")
    LiveData<List<UserEntity>> getHardHighscores();

    /**
     * Liest die 5 besten Spieler auf Level Easy aus.
     */
    @Update
    void updateUser(UserEntity user);

    /**
     * Schreibt neue Benutzer in die Datenbank.
     *
     * @param users List mit neuen Benutzers
     */
    @Insert(onConflict = OnConflictStrategy.NONE)
    void insertAll(UserEntity... users);

}
