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
	private ProjectManager pm;

	private final String alphanumericName = "([0-9A-Za-z_]+)";
	private final String coords = "(\\d+) (\\d+)";

	private final Pattern addGizmoCommand = Pattern
			.compile("(Square|Circle|Triangle|RightFlipper|LeftFlipper) " + alphanumericName + " " + coords);
	private final Pattern rotateGizmoCommand = Pattern.compile("Rotate " + alphanumericName);
	private final Pattern addAbsorberCommand = Pattern.compile("Absorber " + alphanumericName + " " + coords + " " + coords);
	private final Pattern connectGizmoCommand = Pattern.compile("Connect " + alphanumericName + " " + alphanumericName);


	public FileManager(ProjectManager projectManager, String fileName ) {
		file = new File(fileName);
		this.pm = projectManager;
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

			if ((lineMatch = connectGizmoCommand.matcher(currentLine)).matches()){
				connectGizmos(lineMatch);
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
			pm.addGizmo(sb);
			break;
		case "Circle":
			CircleBumper cb = new CircleBumper(x, y, 1, 1);
			cb.setName(gizmoName);
			pm.addGizmo(cb);
			break;
		case "Triangle":
			TriangleBumper tb = new TriangleBumper(x, y, 1, 1);
			tb.setName(gizmoName);
			pm.addGizmo(tb);
			break;
		case "RightFlipper":
			RightFlipper rf = new RightFlipper(x, y);
			rf.setName(gizmoName);
			pm.addGizmo(rf);
			break;
		case "LeftFlipper":
			LeftFlipper lf = new LeftFlipper(x, y);
			lf.setName(gizmoName);
			pm.addGizmo(lf);
			break;
		}

	}

	private void rotateGizmo(Matcher lineMatch) {
		String gizmoName = lineMatch.group(1);

		pm.getGizmoByName(gizmoName).rotateClockwise();

	}

	private void addAbsorber(Matcher lineMatch){
		String gizName = lineMatch.group(1);
		int x1 = Integer.parseInt(lineMatch.group(2));
		int y1= Integer.parseInt(lineMatch.group(3));
		int x2 = Integer.parseInt(lineMatch.group(4));
		int y2= Integer.parseInt(lineMatch.group(5));

		AbstractGizmo giz = new Absorber(x1,y1,x2,1);

		giz.setName(gizName);

		pm.addGizmo(giz);
	}

	// for connections, the first match should be
	public void connectGizmos(Matcher lineMatch){
		AbstractGizmo shouter = pm.getGizmoByName(lineMatch.group(1));
		AbstractGizmo listener = pm.getGizmoByName(lineMatch.group(2));

		shouter.addGizmoListener(listener);
	}

}
