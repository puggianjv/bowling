package com.puggian.bowling;

import com.puggian.bowling.dao.PlayerDAO;
import com.puggian.bowling.input.Chance;
import com.puggian.bowling.dao.impl.PlayerDAOImpl;
import com.puggian.bowling.model.Frame;
import com.puggian.bowling.model.Player;
import com.puggian.bowling.service.PlayerService;
import com.puggian.bowling.service.PinfallsService;
import com.puggian.bowling.service.ScoreService;
import com.puggian.bowling.service.impl.PlayerServiceImpl;
import com.puggian.bowling.service.impl.PinfallsServiceImpl;
import com.puggian.bowling.service.impl.ScoreServiceImpl;

import java.io.IOException;
import java.util.List;

public class BowlingApplication {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("The application should receive a single parameter, the file path");
			System.exit(1);
		}
		List<String> inputLines = readFile(args[0]);

		PlayerDAO playerDAO = new PlayerDAOImpl();
		PinfallsService pinfallsService = new PinfallsServiceImpl(playerDAO);
		PlayerService playerService = new PlayerServiceImpl(playerDAO);
		ScoreService scoreService = new ScoreServiceImpl(playerService);

		processGame(inputLines, pinfallsService, playerService);
		printScore(scoreService);
	}

	private static void processGame(List<String> lines, PinfallsService pinfallsService, PlayerService playerService) {
		processPinfalls(lines, pinfallsService);
		List<Player> players = playerService.getPlayers();
		processScore(players);
	}

	private static void processScore(List<Player> players) {
		for (Player player : players) {
			player.calculateScore();
		}
	}

	private static void processPinfalls(List<String> lines, PinfallsService pinfallsService) {
		lines.stream()
				.map(Chance::fromString)
				.forEach(pinfallsService::computeChance);
	}

	private static List<String> readFile(String arg) throws IOException {
		FileReader fileReader = new FileReader(arg);
		return fileReader.readAsStringList();
	}

	private static void printScore(ScoreService scoreService) {
		System.out.println(scoreService.getGameScore());
	}
}
