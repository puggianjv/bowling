package com.puggian.bowling.service.impl;

import com.puggian.bowling.dao.PlayerDAO;
import com.puggian.bowling.exceptions.BallsLimitExceededException;
import com.puggian.bowling.exceptions.InvalidFormatException;
import com.puggian.bowling.exceptions.WrongOrderException;
import com.puggian.bowling.input.Chance;
import com.puggian.bowling.model.Ball;
import com.puggian.bowling.model.Frame;
import com.puggian.bowling.model.Player;
import com.puggian.bowling.service.PinfallsService;

import java.util.Optional;

public class PinfallsServiceImpl implements PinfallsService {

    private final PlayerDAO playerDAO;
    private static int currentFrame = 0;
    private int currentPlayer = 1;
    private int totalPlayers = 0;

    public PinfallsServiceImpl(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public void computeChance(Chance chance) {
        Optional<Player> playerOpt = playerDAO.findByName(chance.getPlayerName());
        if (currentFrame == 0) {
            playerOpt.ifPresentOrElse(player -> {
                Frame frame = player.getFrame(currentFrame);
                if(player.getNumber() == 1 && frame.isFinished()) {
                    totalPlayers = currentPlayer - 1;
                    currentPlayer = 1;
                    currentFrame++;
                } else {
                    frame.addBall(chance.getPins(), chance.isFault());
                    playerDAO.savePlayer(player);
                    currentPlayer++;
                }
            }, () -> {
                Player player = new Player(chance.getPlayerName(), currentPlayer);
                Frame frame = player.getFrame(currentFrame);
                frame.addBall(chance.getPins(), chance.isFault());
                playerDAO.savePlayer(player);
                if (chance.getPins() == 10) {
                    currentPlayer++;
                }
            });
        }

        if (currentFrame > 0 && currentFrame < 9) {
            playerOpt.ifPresentOrElse(player -> {
                validateCurrentPlayerTime(currentPlayer, player.getName());
                boolean finished = player.getFrame(currentFrame).addBall(chance.getPins(), chance.isFault());
                playerDAO.savePlayer(player);
                if (finished) {
                    currentPlayer++;
                    if (currentPlayer > totalPlayers) {
                        currentPlayer = 1;
                        currentFrame++;
                    }
                }
            }, () -> {
                throw new InvalidFormatException("All players have to play the first frame");
            });
        } else if (currentFrame == 9) {
            playerOpt.ifPresentOrElse(player -> {
                validateCurrentPlayerTime(currentPlayer, player.getName());
                Ball firstBall = player.getFrame(currentFrame).getFirstBall();
                if (firstBall == null) {
                    player.getFrame(currentFrame).addBall(chance.getPins(), chance.isFault());
                    playerDAO.savePlayer(player);
                } else if (firstBall.getPins() == 10) {
                    boolean finished = player.addBonusBall(chance.getPins(), chance.isFault());
                    playerDAO.savePlayer(player);
                    if (finished) {
                        currentPlayer++;
                    }
                } else if (player.getFrame(currentFrame).getSecondBall() == null) {
                    player.getFrame(currentFrame).addBall(chance.getPins(), chance.isFault());
                    playerDAO.savePlayer(player);
                    if (firstBall.getPins() + player.getFrame(currentFrame).getSecondBall().getPins() < 10) {
                        currentPlayer++;
                    }
                } else if (firstBall.getPins() + player.getFrame(currentFrame).getSecondBall().getPins() == 10) {
                    player.addBonusBall(chance.getPins(), chance.isFault());
                    playerDAO.savePlayer(player);
                    currentPlayer++;
                } else {
                    throw new BallsLimitExceededException("Exceeded the limit of balls for this player");
                }
            }, () -> {
                throw new InvalidFormatException("All players have to play the first frame");
            });
        }
    }

    private void validateCurrentPlayerTime(int number, String name) {
        if (this.currentPlayer != number) {
            throw new WrongOrderException("Player " + name + " played in a wrong time.");
        }
    }
}
