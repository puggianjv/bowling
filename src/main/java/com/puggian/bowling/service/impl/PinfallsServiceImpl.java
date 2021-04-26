package com.puggian.bowling.service.impl;

import com.puggian.bowling.dao.PlayerDAO;
import com.puggian.bowling.exceptions.BallsLimitExceededException;
import com.puggian.bowling.exceptions.InvalidFormatException;
import com.puggian.bowling.exceptions.WrongOrderException;
import com.puggian.bowling.input.Chance;
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
                if(player.getNumber() == 1 && player.getFrame(currentFrame).isFinished()) {
                    totalPlayers = currentPlayer - 1;
                    currentPlayer = 1;
                    currentFrame++;
                } else {
                    player.getFrame(currentFrame).addBall(chance.getPins());
                    playerDAO.savePlayer(player);
                    currentPlayer++;
                }
            }, () -> {
                Player player = new Player(chance.getPlayerName(), currentPlayer);
                player.getFrame(currentFrame).addBall(chance.getPins());
                playerDAO.savePlayer(player);
                if (chance.getPins() == 10) {
                    currentPlayer++;
                }
            });
        }

        if (currentFrame > 0 && currentFrame < 9) {
            playerOpt.ifPresentOrElse(player -> {
                boolean finished = player.getFrame(currentFrame).addBall(chance.getPins());
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
                Integer firstBallPins = player.getFrame(currentFrame).getFirstBallPins();
                Integer secondBallPins = player.getFrame(currentPlayer).getSecondBallPins();
                if (firstBallPins == null) {
                    player.getFrame(currentFrame).addBall(chance.getPins());
                    playerDAO.savePlayer(player);
                } else if (firstBallPins == 10) {
                    boolean finished = player.addBonusBall(chance.getPins());
                    playerDAO.savePlayer(player);
                    if (finished) {
                        currentPlayer++;
                    }
                } else if (secondBallPins == null) {
                    player.getFrame(currentFrame).addBall(chance.getPins());
                    playerDAO.savePlayer(player);
                    Integer firstBall = player.getFrame(currentPlayer).getFirstBallPins();
                    Integer secondBall = player.getFrame(currentPlayer).getSecondBallPins();
                    if (firstBall + secondBall < 10) {
                        currentPlayer++;
                    }
                } else if (firstBallPins + secondBallPins == 10) {
                    player.addBonusBall(chance.getPins());
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
