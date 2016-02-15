package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gizmos.AbstractGizmo;
import gizmos.CircleBumper;
import gizmos.LeftFlipper;
import gizmos.RightFlipper;
import gizmos.SquareBumper;
import gizmos.TriangleBumper;

public class FileManager {

	private Scanner scan;
	private File file;
	private List<AbstractGizmo> boardGizmos;
	private Map<String, List<Integer>> nameMap = new HashMap<String, List<Integer>>(); //Maps gizmo names to coordinates stored in Integer LinkedList

	private final Pattern addGizmoCommand = Pattern
			.compile("(Square|Circle|Triangle|RightFlipper|LeftFlipper) " + "([0-9A-Za-z_]+)" + " " + "(\\d+) (\\d+)");

	public FileManager(String fileName, List<AbstractGizmo> boardGizmos) {
		file = new File(fileName);
		this.boardGizmos = boardGizmos;
		try {
			this.scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void loadFile() {
		String currentLine;
		Matcher match;

		while (scan.hasNextLine()) {
			currentLine = scan.nextLine();

			match = addGizmoCommand.matcher(currentLine);

			if (match.matches()) {
				addGizmo(match);
			}
			match.reset();
		}
	}

	private void addGizmo(Matcher match) {
		String opCode = match.group(1);
		String gizmoName = match.group(2);
		int x = Integer.parseInt(match.group(3));
		int y = Integer.parseInt(match.group(4));
		
		List<Integer> coordList = new LinkedList<Integer>();
		coordList.add(x);
		coordList.add(y);
		
		nameMap.put(gizmoName, coordList);

		switch (opCode) {
		case "Square":
			boardGizmos.add(new SquareBumper(x, y, 1, 1, 0));
			break;
		case "Circle":
			boardGizmos.add(new CircleBumper(x, y, 1, 1, 0));
			break;
		case "Triangle":
			boardGizmos.add(new TriangleBumper(x, y, 1, 1, 0));
			break;
		case "RightFlipper":
			boardGizmos.add(new RightFlipper(x, y, 1, 1, 0));
			break;
		case "LeftFlipper":
			boardGizmos.add(new LeftFlipper(x, y, 1, 1, 0));
			break;
		}
		
		
	}

}
