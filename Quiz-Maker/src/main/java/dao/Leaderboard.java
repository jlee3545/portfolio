package dao;

import java.util.List;

public interface Leaderboard {

    List<Player> getLeaderboard();
    void addLeaderboard(Player playerToAdd);

}
