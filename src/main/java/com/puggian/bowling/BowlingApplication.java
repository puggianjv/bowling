package com.puggian.bowling;

import com.puggian.bowling.dao.PlayerDAO;
import com.puggian.bowling.input.Chance;
import com.puggian.bowling.dao.impl.PlayerDAOImpl;
import com.puggian.bowling.model.Frame;
import com.puggian.bowling.model.Player;
import com.puggian.bowling.service.PlayerService;
import com.puggian.bowling.service.PinfallsService;
import com.puggian.bowling.service.impl.PlayerServiceImpl;
import com.puggian.bowling.service.impl.PinfallsServiceImpl;

import java.io.IOException;
import java.util.List;

public class BowlingApplication {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("The application should receive a single parameter, the file path");
			System.exit(1);
		}
		FileReader fileReader = new FileReader(args[0]);
		List<String> lines = fileReader.readAsStringList();

		PlayerDAO playerDAO = new PlayerDAOImpl();
		PinfallsService pinfallsService = new PinfallsServiceImpl(playerDAO);
		PlayerService playerService = new PlayerServiceImpl(playerDAO);

		lines.stream()
				.map(Chance::fromString)
				.forEach(pinfallsService::computeChance);

		printFrameLine();
		printBoard(playerService.getPlayers());

	}

	private static void printBoard(List<Player> players) {
		for (Player p: players) {
			System.out.println(p.getName());
			printPinfallsLine(p);
		}
	}

	private static void printPinfallsLine(Player p) {
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
		System.out.println(sb);
	}

	private static void printFrameLine() {
		StringBuilder sb = new StringBuilder();
		sb.append("Frame");
		for (int i = 1; i <= 10; i++) {
			sb.append("\t\t");
			sb.append(i);
		}
		System.out.println(sb);
	}
}
