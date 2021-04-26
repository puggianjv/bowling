package com.puggian.bowling.dao.impl;

import com.puggian.bowling.dao.PlayerDAO;
import com.puggian.bowling.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class PlayerDAOImpl implements PlayerDAO {

    private static final HashMap<String, Player> playersMap = new HashMap<>();

    public Optional<Player> findByName(String name) {
        return Optional.ofNullable(playersMap.get(name));
    }

    public List<Player> findAll() {
        return new ArrayList<>(playersMap.values());
    }

    public Player savePlayer(Player player) {
        return playersMap.put(player.getName(), player);
    }

}
