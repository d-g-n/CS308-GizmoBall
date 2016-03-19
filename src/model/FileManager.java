package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gizmos.Absorber;
import gizmos.AbstractGizmo;
import gizmos.Ball;
import gizmos.CircleBumper;
import gizmos.LeftFlipper;
import gizmos.RightFlipper;
import gizmos.SquareBumper;
import gizmos.TriangleBumper;
import physics.Vect;

public class FileManager {

	private Scanner scan;
	private File file;
	private ProjectManager pm;

	private final String INTEGER = "([0-9]+)";
	private final String FLOAT = "([-+]?\\d+\\.\\d+)";
	private final String KEYNUM = INTEGER; // kinda redundant but it keeps the naming convention consistent
	private final String IDENTIFIER = "([0-9A-Za-z_]+)";

	private final String NUMBER_PAIR = INTEGER + " " + INTEGER;
	private final String FLOAT_PAIR = FLOAT + " " + FLOAT;
	private final String GIZMO_OP = "(Square|Circle|Triangle|RightFlipper|LeftFlipper)";
	private final String KEYID = "key" + " " + KEYNUM + " " + "(down|up)";


	private final Pattern addBallCommand = Pattern.compile("Ball" + " " + IDENTIFIER + " " + FLOAT_PAIR + " " + FLOAT_PAIR);
	private final Pattern addGizmoCommand = Pattern.compile(GIZMO_OP + " " + IDENTIFIER + " " + NUMBER_PAIR);
	private final Pattern rotateGizmoCommand = Pattern.compile("Rotate" + " " + IDENTIFIER);
	private final Pattern addAbsorberCommand = Pattern.compile("Absorber" + " " + IDENTIFIER + " " + NUMBER_PAIR + " " + NUMBER_PAIR);
	private final Pattern connectGizmoCommand = Pattern.compile("Connect" + " " + IDENTIFIER + " " + IDENTIFIER);
	private final Pattern keyConnectGizmoCommand = Pattern.compile("KeyConnect" + " " + KEYID + " " + IDENTIFIER);



	public FileManager(ProjectManager projectManager) {
		this.pm = projectManager;
	}
	
	public void saveFile(String filePath){
		System.out.println(filePath);
		PrintWriter writer;
		try {
			writer = new PrintWriter(filePath, "UTF-8");
			writer.println("First line");
			writer.println("Second line");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadFile(String fileName) {
		file = new File(fileName);
		String currentLine;
		Matcher lineMatch;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

			if ((lineMatch = keyConnectGizmoCommand.matcher(currentLine)).matches()){
				keyConnectGizmos(lineMatch);
			}

			if ((lineMatch = addBallCommand.matcher(currentLine)).matches()){
				addBall(lineMatch);
			}

			lineMatch.reset();
		}
	}

	private void addBall(Matcher lineMatch){
		String ballName = lineMatch.group(1);
		double initX = Double.parseDouble(lineMatch.group(2));
		double initY = Double.parseDouble(lineMatch.group(3));
		double velX = Double.parseDouble(lineMatch.group(4));
		double velY = Double.parseDouble(lineMatch.group(5));

		AbstractGizmo ball = new Ball(initX, initY, new Vect(velX, velY));
		ball.setName(ballName);

		pm.addBall((Ball) ball);
		pm.addGizmo(ball);
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

		int width = x2 - x1;
		int height = y2 - y1;

		AbstractGizmo giz = new Absorber(x1,y1,width,height);

		giz.setName(gizName);

		pm.addGizmo(giz);
	}

	// for connections, the first match should be
	public void connectGizmos(Matcher lineMatch){
		AbstractGizmo shouter = pm.getGizmoByName(lineMatch.group(1));
		AbstractGizmo listener = pm.getGizmoByName(lineMatch.group(2));

		shouter.addGizmoListener(listener);
	}

	public void keyConnectGizmos(Matcher lineMatch){
		int keyNum = Integer.parseInt(lineMatch.group(1));
		String activateOnDownOrUp = lineMatch.group(2);
		String gizmoName = lineMatch.group(3);

		pm.addKeyConnect(gizmoName, keyNum, activateOnDownOrUp);
	}

}
