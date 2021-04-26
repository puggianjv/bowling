package com.puggian.bowling.service.impl;

import com.puggian.bowling.model.Frame;
import com.puggian.bowling.model.Player;
import com.puggian.bowling.service.ScoreService;
import com.puggian.bowling.service.PlayerService;

import java.util.List;

public class ScoreServiceImpl implements ScoreService {

    private final PlayerService playerService;

    public ScoreServiceImpl(PlayerService playerService) {
        this.playerService = playerService;
    }

    public String getGameScore() {
        List<Player> players = playerService.getPlayers();

        StringBuilder sb = new StringBuilder();
        sb.append(createFrameLine());
        for (Player p: players) {
            sb.append(p.getName());
            sb.append("\n");
            sb.append(createPinfallsLine(p));
            sb.append(createScore(p));
        }
        return sb.toString();
    }

    private StringBuilder createScore(Player p) {
        StringBuilder sb = new StringBuilder();
        sb.append("Score");
        for (int i = 0; i < 10; i++) {
            sb.append("\t\t");
            sb.append(p.getFrame(i).getScore());
        }
        return sb.append("\n");
    }

    private StringBuilder createPinfallsLine(Player p) {
        StringBuilder sb = new StringBuilder();
        sb.append("Pinfalls\t");
        for (int i = 0; i < 9; i++) {
            Frame frame = p.getFrame(i);
            if (frame.getFirstBallPins() == 10) {
                sb.append("\tX\t");
                continue;
            }
            sb.append(frame.getFirstBallPins());
            sb.append("\t");
            if (frame.getFirstBallPins() + frame.getSecondBallPins() == 10) {
                sb.append("/\t");
            } else {
                sb.append(frame.getSecondBallPins());
                sb.append("\t");
            }
        }
        Frame lastFrame = p.getFrame(9);
        if (lastFrame.getFirstBallPins() == 10) {
            sb.append("X\t");
            sb.append(p.getBonusBallPins(0));
            sb.append("\t");
            sb.append(p.getBonusBallPins(1));
        } else {
            sb.append(lastFrame.getFirstBallPins());
            sb.append("\t");
            if (lastFrame.getFirstBallPins() + lastFrame.getSecondBallPins() == 10) {
                sb.append("/\t");
                sb.append(p.getBonusBallPins(0));
            } else {
                sb.append(lastFrame.getSecondBallPins());
            }
        }
        sb.append("\n");
        return sb;
    }

    private StringBuilder createFrameLine() {
        StringBuilder sb = new StringBuilder();
        sb.append("Frame");
        for (int i = 1; i <= 10; i++) {
            sb.append("\t\t");
            sb.append(i);
        }
        return sb.append("\n");
    }

}
