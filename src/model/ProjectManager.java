package model;

import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 * The ProjectManager class provides general information about the state
 * of the game.
 *
 */
import javax.swing.Timer;

import gizmos.Absorber;
import gizmos.AbstractGizmo;
import gizmos.Ball;
import gizmos.CircleBumper;
import gizmos.OuterWall;
import gizmos.SquareBumper;
import gizmos.Teleporter;
import gizmos.TriangleBumper;
import physics.Vect;

public class ProjectManager extends Observable {

	private CollisionManager cManager;
	private FileManager fManager;
	private List<AbstractGizmo> boardGizmos;
	private Map<Map.Entry<String, Integer>, List<AbstractGizmo>> gizmoKeyPressMap;
	private List<Ball> ballList;
	private boolean buildModeOn = false;
	private String statusLabel, currentBoard;
	private int totalScore,highestScore,numLives;
	private boolean gameOver,dynamicMode;

	private String currentCommand;
	private Timer runTimer;

	public ProjectManager() {
		boardGizmos = new ArrayList<>();
		gizmoKeyPressMap = new HashMap<>();
		cManager = new CollisionManager(this);
		currentBoard = null;
		currentCommand = "";
		totalScore = 0;
		highestScore = 0;
		setLives(10);
		setStatusLabel("Score: " + getScore() + " High Score: " + getHighScore() + " Lives: " + getLives());
		gameOver = false;
		dynamicMode = false;
		// HARDCODED GIZMO DEFS (mind the outer walls are never supposed to
		// actually be in 0 -> 19)

		addGizmo(new OuterWall(-1, -1, 22, 1)); // start at top left, 20 along x
		addGizmo(new OuterWall(-1, -1, 1, 22)); // start at top left, 20 down y

		addGizmo(new OuterWall(20, -1, 1, 22)); // start at top right, 22 down y
		addGizmo(new OuterWall(-1, 20, 22, 1)); // start at bottom left, 22
												// along x

		this.setChanged();
		this.notifyObservers();

	}



	public void saveAs(String filePath) {
		fManager = new FileManager(this);
		fManager.saveFile(filePath);
	}

	public void addKeyConnect(String gizName, int keyNum, String onDownOrUp) {
		AbstractGizmo giz = getGizmoByName(gizName);

		Map.Entry<String, Integer> key = new AbstractMap.SimpleEntry<String, Integer>(onDownOrUp, keyNum);

		if (gizmoKeyPressMap.containsKey(key)) {
			List<AbstractGizmo> tempList = gizmoKeyPressMap.get(key);
			tempList.add(giz);
			gizmoKeyPressMap.put(key, tempList);
		} else {
			gizmoKeyPressMap.put(key, new ArrayList<>(Arrays.asList(giz)));
		}
	}

	public void removeKeyConnect(String gizName, int keyNum, String onDownOrUp) {

		try {
			Map.Entry<String, Integer> key = new AbstractMap.SimpleEntry<String, Integer>(onDownOrUp, keyNum);
			gizmoKeyPressMap.get(key).remove(getGizmoByName(gizName));
			if (gizmoKeyPressMap.get(key).isEmpty()) {
				gizmoKeyPressMap.remove(key);
			}
		} catch (NullPointerException e) {

		}

	}

	public void addGizmo(AbstractGizmo g) {
		// ideally we'll give it a random name here but irght now
		// also need to do square checking in here to prevent overlapping gizmos
		if (canPlaceGizmoAt(g) || g.getClass().equals(OuterWall.class) || (g.getClass().equals(Ball.class) && this.getGizmoByCoordinate((int)Math.floor(g.getXPos()),(int) Math.floor(g.getYPos())).getClass().equals(Absorber.class))) {
			boardGizmos.add(g);
			this.pushVisualUpdate();
		}
	}

	public boolean canPlaceGizmoAt(double x, double y, double w, double h) {

		if ((x < 0 || x >= 20) || (y < 0 || y >= 20)) {
			return false;
		}

		List<Vect> requestedSquares = new ArrayList<>();

		for(int gx = (int) Math.floor(x); gx < (Math.floor(x) + w); gx++){
			for(int gy = (int) Math.floor(y); gy < (Math.floor(y) + h); gy++){
				requestedSquares.add(new Vect(gx, gy));
			}
		}

		for (AbstractGizmo giz : this.boardGizmos) {
			if(giz.getClass().equals(OuterWall.class))
				continue;

			for(int gx = (int) Math.floor(giz.getXPos()); gx < (Math.floor(giz.getXPos()) + giz.getWidth()); gx++){
				for(int gy = (int) Math.floor(giz.getYPos()); gy < (Math.floor(giz.getYPos()) + giz.getHeight()); gy++){
					Vect notAllowed = new Vect(gx, gy);

					if(requestedSquares.contains(notAllowed)){
						playSound("cannotplace");
						return false;	
					}
				}
			}
		}

		playSound("canplace");
		return true;

	}

	public boolean canPlaceGizmoAt(AbstractGizmo g) {
		return canPlaceGizmoAt(g.getXPos(), g.getYPos(), g.getWidth(), g.getHeight());
	}

	public AbstractGizmo getGizmoByName(String name) {
		
		if (!getGizmoByNameList(name).isEmpty()) {
		return getGizmoByNameList(name).get(0);
		} else {
			return null;
		}
	}
	
	public ArrayList<AbstractGizmo> getGizmoByNameList(String name) {
		ArrayList<AbstractGizmo> gizList = new ArrayList<AbstractGizmo>();
		for (AbstractGizmo giz : boardGizmos) {
			if (giz.getName().equals(name))
				gizList.add(giz);
		}

		return gizList;
	}

	public AbstractGizmo getGizmoByCoordinate(int x, int y){

		Vect lookFor = new Vect(x, y);

		for (AbstractGizmo giz : this.boardGizmos) {
			for(int gx = (int) Math.floor(giz.getXPos()); gx < (Math.floor(giz.getXPos()) + giz.getWidth()); gx++){
				for(int gy = (int) Math.floor(giz.getYPos()); gy < (Math.floor(giz.getYPos()) + giz.getHeight()); gy++){
					Vect gizSquare = new Vect(gx, gy);
					if(lookFor.equals(gizSquare))
						return giz;
				}
			}
		}

		return null;
	}

	public List<AbstractGizmo> getBoardGizmos() {
		return boardGizmos;
	}

	public Map<Map.Entry<String, Integer>, List<AbstractGizmo>> getKeyConnects() {
		return gizmoKeyPressMap;
	}

	public void moveBall() {

		cManager.moveBall();

		for (AbstractGizmo giz : boardGizmos) {
			giz.doPhysicsCalculations();
		}

	}

	public void pushVisualUpdate() {
		this.setChanged();
		this.notifyObservers();
	}

	public void loadFile(String fileName) {
		currentBoard = fileName;
		fManager = new FileManager(this);
		fManager.loadFile(fileName);
		resetScore();
		playSound("load");
		this.setChanged();
		this.notifyObservers();
	}

	public void restartGame() {
		if (currentBoard != null)
			fManager.loadFile(currentBoard);
		resetScore();
		gameOver = false;
	}

	public String getCurrentBoard(){
		return currentBoard;
	}

	public boolean isBuildModeOn() {
		return buildModeOn;
	}

	public void setBuildModeOn(boolean buildModeOn) {
		this.buildModeOn = buildModeOn;
	}


	public void deleteGizmo(AbstractGizmo a) {
		boardGizmos.remove(a);

	}

	public void setGravity(double newGravity) {

		cManager.setGravity(newGravity);
	}
	
	public void setFriction(double mu, double mu2) {

		cManager.setFriction(mu, mu2);
	}

	public double getGravity() {

		return cManager.getGravity();
	}
	
	public double getMuFriction() {
		return cManager.getMuFriction();
	}

	public double getMu2Friction(){
		return cManager.getMu2Friction();
	}

	public String getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}


	public void updateScore(AbstractGizmo giz){
		if(giz != null && !dynamicMode){

		if(giz.getClass().equals(SquareBumper.class))
			totalScore+=1;
		else if(giz.getClass().equals(CircleBumper.class))
			totalScore+=2;
		else if(giz.getClass().equals(TriangleBumper.class))
			totalScore+=3;
		else if(giz.getClass().equals(Absorber.class)){
			numLives--;
			playSound("fireball");	
		}else if(giz.getClass().equals(Teleporter.class))
			playSound("teleporter");

		if(highestScore <= totalScore){
			
			highestScore = totalScore;
			//playSound("highscore");
		}

		if(getLives() < 0){
			playSound("gameover");
		gameOver = true;
		return;
		}
		
		
		setStatusLabel("Score: " + totalScore + " High Score: " + getHighScore() + " Lives: " + getLives());
		}
	}

	public void startGame(){
		gameOver = false;
	}

	public void resetScore(){
		totalScore = 0;
		numLives = 10;
		setStatusLabel("Score: " + totalScore + "HighScore: " + highestScore + " Lives: " + getLives());
	}

	public boolean gameOver(){
		return gameOver;
	}
	public boolean isDynamicMode(){
		return dynamicMode;
	}
	public void dynamicModeOn(){
		dynamicMode = true;
	}
	public void dynamicModeOff(){
		dynamicMode = false;
	}

	public void setLives(int lives){
		numLives = lives;
	}

	public int getLives(){
		return numLives;
	}

	public int getHighScore(){
		return highestScore;
	}

	public int getScore(){
		return totalScore;
	}
	
	public void clearAllBoardGizmos() {
		boardGizmos.clear();

		// forgot this first time round, need to clear the key connect map too
		gizmoKeyPressMap.clear();

		// re add the outwalls, maybe a silly way of doing things, could change
		// it

		addGizmo(new OuterWall(-1, -1, 22, 1)); // start at top left, 20 along x
		addGizmo(new OuterWall(-1, -1, 1, 22)); // start at top left, 20 down y

		addGizmo(new OuterWall(20, -1, 1, 22)); // start at top right, 22 down y
		addGizmo(new OuterWall(-1, 20, 22, 1)); // start at bottom left, 22
												// along x
	}

	public void setTimer(Timer timer) {
		this.runTimer = timer;
	}

	public Timer getTimer(){
		return runTimer;
	}

	public void setCurrentCommand(String currentCommand) {
		this.currentCommand = currentCommand;
	}

	public String getCurrentCommand() {
		return currentCommand;
	}
	
	public void playSound(String soundURL){
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("clips/" + soundURL + ".wav")));
			clip.start();
		}catch(Exception e){
			
		}
		
	}

}
