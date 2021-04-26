package com.puggian.bowling.service.impl;

import com.puggian.bowling.dao.PlayerDAO;
import com.puggian.bowling.model.Player;
import com.puggian.bowling.service.PlayerService;

import java.util.List;

public class PlayerServiceImpl implements PlayerService {

    private final PlayerDAO playerDAO;

    public PlayerServiceImpl(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public List<Player> getPlayers() {
        return playerDAO.findAll();
    }

}
