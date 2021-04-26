package com.puggian.bowling.dao;

import com.puggian.bowling.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerDAO {

    Optional<Player> findByName(String name);

    List<Player> findAll();

    Player savePlayer(Player player);

}
