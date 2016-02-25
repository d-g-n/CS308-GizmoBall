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

import gizmos.*;

public class FileManager {

	private Scanner scan;
	private File file;
	private List<AbstractGizmo> boardGizmos;

	private final String alphanumericName = "([0-9A-Za-z_]+)";
	private final String coords = "(\\d+) (\\d+)";

	private final Pattern addGizmoCommand = Pattern
			.compile("(Square|Circle|Triangle|RightFlipper|LeftFlipper) " + "([0-9A-Za-z_]+)" + " " + coords);
	private final Pattern rotateGizmoCommand = Pattern.compile("Rotate " + "([0-9A-Za-z_]+)");
	private final Pattern addAbsorberCommand = Pattern.compile("Absorber " + alphanumericName + " " + coords + " " + coords);


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
		Matcher lineMatch;

		while (scan.hasNextLine()) {
			currentLine = scan.nextLine();

			if ((lineMatch = addGizmoCommand.matcher(currentLine)).matches()) {
				addGizmo(lineMatch);
			}

			if ((lineMatch = rotateGizmoCommand.matcher(currentLine)).matches()) {
				rotateGizmo(lineMatch);
			}

			if ((lineMatch = addAbsorberCommand.matcher(currentLine)).matches()){
				addAbsorber(lineMatch);
			}
			lineMatch.reset();
		}
	}

	private void addGizmo(Matcher lineMatch) {
		String opCode = lineMatch.group(1);
		String gizmoName = lineMatch.group(2);
		int x = Integer.parseInt(lineMatch.group(3));
		int y = Integer.parseInt(lineMatch.group(4));

		switch (opCode) {
		case "Square":
			SquareBumper sb = new SquareBumper(x, y, 1, 1);
			sb.setName(gizmoName);
			boardGizmos.add(sb);
			break;
		case "Circle":
			CircleBumper cb = new CircleBumper(x, y, 1, 1);
			cb.setName(gizmoName);
			boardGizmos.add(cb);
			break;
		case "Triangle":
			TriangleBumper tb = new TriangleBumper(x, y, 1, 1);
			tb.setName(gizmoName);
			boardGizmos.add(tb);
			break;
		case "RightFlipper":
			RightFlipper rf = new RightFlipper(x, y);
			rf.setName(gizmoName);
			boardGizmos.add(rf);
			break;
		case "LeftFlipper":
			LeftFlipper lf = new LeftFlipper(x, y);
			lf.setName(gizmoName);
			boardGizmos.add(lf);
			break;
		}

	}

	private void rotateGizmo(Matcher lineMatch) {
		String gizmoName = lineMatch.group(1);

		for (AbstractGizmo abGizmo : boardGizmos) {
			if(abGizmo.getName().equals(gizmoName)){
				abGizmo.rotateClockwise();
				break;
			}
		}
	}

	private void addAbsorber(Matcher lineMatch){
		int x1 = Integer.parseInt(lineMatch.group(2));
		int y1= Integer.parseInt(lineMatch.group(3));
		int x2 = Integer.parseInt(lineMatch.group(4));
		int y2= Integer.parseInt(lineMatch.group(5));

		boardGizmos.add(new Absorber(x1,y1,x2,1));
	}

}
