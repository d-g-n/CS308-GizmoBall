package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gizmos.Absorber;
import gizmos.AbstractGizmo;
import gizmos.Ball;
import gizmos.BoosterGizmo;
import gizmos.CircleBumper;
import gizmos.DeathSquare;
import gizmos.LeftFlipper;
import gizmos.RightFlipper;
import gizmos.SquareBumper;
import gizmos.Teleporter;
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
	private final String GIZMO_OP = "(Square|Circle|Triangle|RightFlipper|LeftFlipper|Booster|DeathSquare|Teleporter)";
	private final String KEYID = "key" + " " + KEYNUM + " " + "(down|up)";


	private final Pattern addBallCommand = Pattern.compile("Ball" + " " + IDENTIFIER + " " + FLOAT_PAIR + " " + FLOAT_PAIR);
	private final Pattern addGizmoCommand = Pattern.compile(GIZMO_OP + " " + IDENTIFIER + " " + NUMBER_PAIR);
	private final Pattern rotateGizmoCommand = Pattern.compile("Rotate" + " " + IDENTIFIER);
	private final Pattern deleteGizmoCommand = Pattern.compile("Delete" + " " + IDENTIFIER);
	private final Pattern moveGizmoCommand = Pattern.compile("Move" + " " + IDENTIFIER + " " + NUMBER_PAIR);
	private final Pattern moveBallCommand = Pattern.compile("Move" + " " + IDENTIFIER + " " + FLOAT_PAIR);
	private final Pattern addAbsorberCommand = Pattern.compile("Absorber" + " " + IDENTIFIER + " " + NUMBER_PAIR + " " + NUMBER_PAIR);
	private final Pattern connectGizmoCommand = Pattern.compile("Connect" + " " + IDENTIFIER + " " + IDENTIFIER);
	private final Pattern keyConnectGizmoCommand = Pattern.compile("KeyConnect" + " " + KEYID + " " + IDENTIFIER);
	private final Pattern gravityCommand = Pattern.compile("Gravity " + FLOAT);
	private final Pattern frictionCommand = Pattern.compile("Friction " + FLOAT_PAIR);


	public FileManager(ProjectManager projectManager) {
		this.pm = projectManager;
	}
	
	public void saveFile(String filePath){
		List<AbstractGizmo> boardGizmos = pm.getBoardGizmos();
		List<Ball> ballList = new ArrayList<>();
		List<AbstractGizmo> gizmoListeners;
		List<String> connectList = new LinkedList<String>();

		for(AbstractGizmo g : boardGizmos){
			if(g.getClass().equals(Ball.class))
				ballList.add((Ball) g);
		}


		String gizmoType;		
		PrintWriter writer;
		try {
			writer = new PrintWriter(filePath, "UTF-8");
			
			for(AbstractGizmo gizmo: boardGizmos){
				gizmoListeners = gizmo.getGizmoListeners();
				gizmoType = gizmo.getType();
				
				switch(gizmoType){
					case "Square":
						commandSave(gizmo, writer, gizmoType);
						break;
						
					case "Circle":
						commandSave(gizmo, writer, gizmoType);
						break;
						
					case "Triangle":
						commandSave(gizmo, writer, gizmoType);
						break;
						
					case "LeftFlipper":
						commandSave(gizmo, writer, gizmoType);
						break;
						
					case "RightFlipper":
						commandSave(gizmo, writer, gizmoType);
						break;
					
					case "Booster":
						commandSave(gizmo, writer, gizmoType);
						break;
						
					case "DeathSquare":
						commandSave(gizmo, writer, gizmoType);
						break;
						
					case "Teleporter":
						commandSave(gizmo, writer, gizmoType);
						break;
						
					case "Absorber":					
						absorberSave((Absorber)gizmo, writer);
						break;
			}			
			
			for(AbstractGizmo listener: gizmoListeners){
				connectList.add("Connect" + " " + gizmo.getName() + " " + listener.getName());
			}
			
		}
			
		for(Map.Entry<Map.Entry<String, Integer>, List<AbstractGizmo>> ent : pm.getKeyConnects().entrySet()){
			Map.Entry<String, Integer> intStringMap = ent.getKey();
			List<AbstractGizmo> gizList = ent.getValue();

			for (AbstractGizmo giz : gizList) {
				writer.println("KeyConnect key " + intStringMap.getValue() + " " + intStringMap.getKey() + " " + giz.getName());
			}
		}
			
		for(Ball ball: ballList){
			ballSave(ball, writer);
		}
			
		for(String connection: connectList){
			writer.println(connection);
		}
			
		writer.println("Gravity " + pm.getGravity());
		writer.println("Friction " + pm.getMuFriction() + " " + pm.getMu2Friction());
			
		writer.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

	private String ballSave(Ball ball, PrintWriter writer) {
		writer.println("Ball " + ball.getName() + " " + ball.getXPos() + " " + ball.getYPos() + " " + ball.getVelocity().x() + " " + ball.getVelocity().y());
		return null;
	}

	private void absorberSave(Absorber abs, PrintWriter writer) {
		int xPos = (int)abs.getXPos();
		int yPos = (int)abs.getYPos();
		int width = (int)abs.getWidth();
		int height = (int)abs.getHeight();
		writer.println("Absorber " + abs.getName() + " " + xPos + " " + yPos + " " + (xPos+width) + " " + (yPos+height));		
	}

	private void commandSave(AbstractGizmo gizmo, PrintWriter writer, String gizmoType) {
		writer.println(gizmoType + " " + gizmo.getName() + " "  + (int)gizmo.getXPos() + " " + (int)gizmo.getYPos());
		for(int i=0;i<(gizmo.getGizAngle()/90);i++){
			writer.println("Rotate " + gizmo.getName());
		}
	}

	public void loadFile(String fileName) {
		// before doing anything else set gravity and friction to their default values
		// these will be overwritten if the appropriate commands are present in the file being loaded,
		// otherwise the board will use these defaults
		pm.setGravity(25.0);
		pm.setFriction(0.025, 0.025);
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
			
			if ((lineMatch = deleteGizmoCommand.matcher(currentLine)).matches()) {
				deleteGizmo(lineMatch);
			}
			
			if ((lineMatch = moveGizmoCommand.matcher(currentLine)).matches()) {
				moveGizmo(lineMatch);
			}
			
			if ((lineMatch = moveBallCommand.matcher(currentLine)).matches()) {
				moveBall(lineMatch);
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
			
			if ((lineMatch = gravityCommand.matcher(currentLine)).matches()){
				setGravity(lineMatch);
			}
			
			if ((lineMatch = frictionCommand.matcher(currentLine)).matches()){
				setFriction(lineMatch);
			}

			lineMatch.reset();
		}
	}

	private void setFriction(Matcher lineMatch) {
		double mu = Double.parseDouble(lineMatch.group(1));
		double mu2 = Double.parseDouble(lineMatch.group(1));
		pm.setFriction(mu, mu2);		
	}

	private void setGravity(Matcher lineMatch) {
		double gravity = Double.parseDouble(lineMatch.group(1));
		pm.setGravity(gravity);
		
	}

	private void addBall(Matcher lineMatch){
		String ballName = lineMatch.group(1);
		double initX = Double.parseDouble(lineMatch.group(2));
		double initY = Double.parseDouble(lineMatch.group(3));
		double velX = Double.parseDouble(lineMatch.group(4));
		double velY = Double.parseDouble(lineMatch.group(5));

		AbstractGizmo ball = new Ball(initX, initY, new Vect(velX, velY));
		ball.setName(ballName);

		pm.addGizmo(ball);
	}

	private void addGizmo(Matcher lineMatch) {
		String opCode = lineMatch.group(1);
		String gizmoName = lineMatch.group(2);
		int x = Integer.parseInt(lineMatch.group(3));
		int y = Integer.parseInt(lineMatch.group(4));

		switch (opCode) {
		case "Square":
			SquareBumper sb = new SquareBumper(x, y);
			sb.setName(gizmoName);
			pm.addGizmo(sb);
			break;
			
		case "Circle":
			CircleBumper cb = new CircleBumper(x, y);
			cb.setName(gizmoName);
			pm.addGizmo(cb);
			break;
			
		case "Triangle":
			TriangleBumper tb = new TriangleBumper(x, y);
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
			
		case "Booster":
			BoosterGizmo boost = new BoosterGizmo(x, y);
			boost.setName(gizmoName);
			pm.addGizmo(boost);
			break;
		case "DeathSquare":
			DeathSquare ds = new DeathSquare(x, y);
			ds.setName(gizmoName);
			pm.addGizmo(ds);
			break;
			
		case "Teleporter":
			Teleporter tele = new Teleporter(x, y);
			tele.setName(gizmoName);
			pm.addGizmo(tele);
			break;
		}

	}

	private void rotateGizmo(Matcher lineMatch) {
		String gizmoName = lineMatch.group(1);
		pm.getGizmoByName(gizmoName).rotateClockwise();
	}
	
	private void deleteGizmo(Matcher lineMatch) {
		String gizmoName = lineMatch.group(1);
		pm.deleteGizmo(pm.getGizmoByName(gizmoName));
	}
	
	private void moveGizmo(Matcher lineMatch) {
		String gizmoName = lineMatch.group(1);
		int x = Integer.parseInt(lineMatch.group(2));
		int y= Integer.parseInt(lineMatch.group(3));
		int numberOfRotations = pm.getGizmoByName(gizmoName).getGizAngle() / 90;
		
		pm.getGizmoByName(gizmoName).moveGizmo(x, y);
		
		for (int i = 0; i < numberOfRotations; i++) {
			pm.getGizmoByName(gizmoName).rotatePhysicsAroundPoint(
					pm.getGizmoByName(gizmoName).getXPos() + pm.getGizmoByName(gizmoName).getWidth() / 2,
					pm.getGizmoByName(gizmoName).getYPos() + (pm.getGizmoByName(gizmoName).getHeight() / 2), 90.0);
		}
	}
	
	private void moveBall(Matcher lineMatch) {
		String gizmoName = lineMatch.group(1);
		double x = Double.parseDouble(lineMatch.group(2));
		double y= Double.parseDouble(lineMatch.group(3));
		
		pm.getGizmoByName(gizmoName).moveGizmo((int)x, (int)y);
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

	public void connectGizmos(Matcher lineMatch){
		ArrayList<AbstractGizmo>  shouters = pm.getGizmoByNameList(lineMatch.group(1));
		AbstractGizmo listener = pm.getGizmoByName(lineMatch.group(2));

		
		for (AbstractGizmo shouter: shouters) {
			shouter.addGizmoListener(listener);
		}
	}

	public void keyConnectGizmos(Matcher lineMatch){
		int keyNum = Integer.parseInt(lineMatch.group(1));
		String activateOnDownOrUp = lineMatch.group(2);
		String gizmoName = lineMatch.group(3);

		pm.addKeyConnect(gizmoName, keyNum, activateOnDownOrUp);
	}

}
