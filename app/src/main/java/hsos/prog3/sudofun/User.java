package hsos.prog3.sudofun;

public class User {
    String name;
    int totalGames;
    int easyGames;
    int mediumGames;
    int hardGames;
    float bestTimeEasy;
    float bestTimeMedium;
    float bestTimeHard;

    public User(String name){
        totalGames = 0;
        easyGames = 0;
        mediumGames = 0;
        hardGames = 0;
        bestTimeEasy = 0.0F;
        bestTimeMedium = 0.0F;
        bestTimeHard = 0.0F;
    }
}
