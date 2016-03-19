package model;

import java.util.*;

import gizmos.*;
import physics.Vect;

public class ProjectManager extends Observable{
	
	private CollisionManager cManager;
	private FileManager fManager;
	private List<AbstractGizmo> boardGizmos;
	private Map<Map.Entry<String, Integer>, List<AbstractGizmo>> gizmoKeyPressMap;
	private List<Ball> ballList;
	private String focusedButton;
	private boolean buildModeOn = false;
	private AbstractGizmo gizmoToConnect = null;
	private AbstractGizmo gizmoToDisconnect = null;
	private AbstractGizmo gizmoToKeyConnect = null;
	private AbstractGizmo gizmoToKeyDisconnect = null;
	private AbstractGizmo gizmoToMove = null;
	private int absorberToBeAddedX = -1, absorberToBeAddedY = -1;
	private String statusLabel,currentBoard;




	public ProjectManager(){
		boardGizmos = new ArrayList<>();
		ballList = new ArrayList<>();
		gizmoKeyPressMap = new HashMap<>();
		cManager = new CollisionManager(this);
		currentBoard = null;
		focusedButton = "Square";
		setStatusLabel("");
		// HARDCODED GIZMO DEFS (mind the outer walls are never supposed to actually be in 0 -> 19)

		addGizmo(new OuterWall(-1, -1, 22, 1)); // start at top left, 20 along x
		addGizmo(new OuterWall(-1, -1, 1, 22)); // start at top left, 20 down y

		addGizmo(new OuterWall(20, -1, 1, 22)); // start at top right, 22 down y
		addGizmo(new OuterWall(-1, 20, 22, 1)); // start at bottom left, 22 along x

		this.setChanged();
		this.notifyObservers();

	}
	
	public void loadFile(String fileName) {
		currentBoard = fileName;
		fManager = new FileManager(this);
		fManager.loadFile(fileName);

		this.setChanged();
		this.notifyObservers();
	}
	
	public void saveAs(String filePath) {
		fManager = new FileManager(this);
		fManager.saveFile(filePath);		
	}

	public String getFocusedButton() {
		return focusedButton;
	}

	public void setFocusedButton(String focusedButton) {
		
		// reset all projectmanager build mode gizmos 
		setAbsorberToBeAddedX(-1);
		setAbsorberToBeAddedY(-1);
		setGizmoToConnect(null);
		setGizmoToKeyConnect(null);
		setGizmoToMove(null);
		setGizmoToKeyConnect(null);
		setGizmoToKeyDisconnect(null);
		this.focusedButton = focusedButton;
	}

	public void addKeyConnect(String gizName, int keyNum, String onDownOrUp){
		AbstractGizmo giz = getGizmoByName(gizName);

		Map.Entry<String, Integer> key = new AbstractMap.SimpleEntry<String, Integer>(onDownOrUp, keyNum);

		if(gizmoKeyPressMap.containsKey(key)) {
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
		} catch (NullPointerException e) {
			
		}

	}


	public void addGizmo(AbstractGizmo g){

		// ideally we'll give it a random name here but irght now
		// also need to do square checking in here to prevent overlapping gizmos
		if(canPlaceGizmoAt(g) || g.getClass().equals(OuterWall.class))
			boardGizmos.add(g);
	}

	public boolean canPlaceGizmoAt(AbstractGizmo sg){

		double x = sg.getXPos();
		double y = sg.getYPos();
		double w = sg.getWidth();
		double h = sg.getHeight();

		if((x < 0 || x > 19) || (y < 0 || y > 19)){
			return false;
		}

		List<Vect> requestedPoints = new ArrayList<>();

		for(double ix = x; ix < (x+w); ix++){
			for(double iy = y; iy < (y+h); iy++){
				requestedPoints.add(new Vect(ix, iy));
			}
		}

		for(AbstractGizmo giz : this.boardGizmos){

			double gx = giz.getXPos();
			double gy = giz.getYPos();
			double gw = giz.getWidth();
			double gh = giz.getHeight();

			for(double ix = gx; ix < (gx+gw); ix++){
				for(double iy = gy; iy < (gy+gh); iy++){
					if(requestedPoints.contains(new Vect(ix, iy)))
						return false;
				}
			}

		}

		return true;

	}
	
	public boolean canPlaceGizmoAt(AbstractGizmo sg, int x, int y){

		double w = sg.getWidth();
		double h = sg.getHeight();

		if((x < 0 || x > 19) || (y < 0 || y > 19)){
			return false;
		}

		List<Vect> requestedPoints = new ArrayList<>();

		for(double ix = x; ix < (x+w); ix++){
			for(double iy = y; iy < (y+h); iy++){
				requestedPoints.add(new Vect(ix, iy));
			}
		}

		for(AbstractGizmo giz : this.boardGizmos){

			double gx = giz.getXPos();
			double gy = giz.getYPos();
			double gw = giz.getWidth();
			double gh = giz.getHeight();

			for(double ix = gx; ix < (gx+gw); ix++){
				for(double iy = gy; iy < (gy+gh); iy++){
					if(requestedPoints.contains(new Vect(ix, iy)))
						return false;
				}
			}

		}

		return true;

	}

	public AbstractGizmo getGizmoByName(String name){
		for(AbstractGizmo giz : boardGizmos){
			if(giz.getName().equals(name))
				return giz;
		}

		return null;
	}

	public List<AbstractGizmo> getBoardGizmos(){
		return boardGizmos;
	}
	public Map<Map.Entry<String, Integer>, List<AbstractGizmo>> getKeyConnects() { return gizmoKeyPressMap; }

	public void moveBall(){

		cManager.moveBall();

		for (AbstractGizmo giz : boardGizmos){
			giz.doPhysicsCalculations();
		}

	}

	public void pushVisualUpdate(){
		this.setChanged();
		this.notifyObservers();
	}
	
	public void restartGame(){
		if(currentBoard != null)
		fManager.loadFile(currentBoard);
	}

	public void addBall(Ball ball){
		this.ballList.add(ball);
	}
	
	

	public List<Ball> getBallList(){
		return ballList;
	}

	public boolean isBuildModeOn() {
		return buildModeOn;
	}

	public void setBuildModeOn(boolean buildModeOn) {
		this.buildModeOn = buildModeOn;
	}

	public AbstractGizmo getGizmoToConnect() {
		return gizmoToConnect;
	}

	public void setGizmoToConnect(AbstractGizmo gizmoToConnect) {
		this.gizmoToConnect = gizmoToConnect;
	}
	
	public void deleteGizmo(AbstractGizmo a) {
		boardGizmos.remove(a);
		
	}
	
	public AbstractGizmo getGizmoToMove() {
		return gizmoToMove;
	}

	public void setGizmoToMove(AbstractGizmo gizmoToMove) {
		this.gizmoToMove = gizmoToMove;
	}

	public AbstractGizmo getGizmoToDisconnect() {
		return gizmoToDisconnect;
	}

	public void setGizmoToDisconnect(AbstractGizmo gizmoToDisconnect) {
		this.gizmoToDisconnect = gizmoToDisconnect;
	}

	public int getAbsorberToBeAddedX() {
		return absorberToBeAddedX;
	}

	public void setAbsorberToBeAddedX(int absorberToBeAddedX) {
		this.absorberToBeAddedX = absorberToBeAddedX;
	}

	public int getAbsorberToBeAddedY() {
		return absorberToBeAddedY;
	}

	public void setAbsorberToBeAddedY(int absorberToBeAddedY) {
		this.absorberToBeAddedY = absorberToBeAddedY;
	}
	
	public void setGravity(double newGravity) {
		
		cManager.setGravity(newGravity);
	}
	
	public void setFriction(double newFriction) {
		
		cManager.setFriction(newFriction, newFriction);
	}

	public double getGravity() {
		
		return cManager.getGravity();
	}
	
	public double getFriction() {
		
		return cManager.getFriction();
	}

	public String getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}
	
	public AbstractGizmo getGizmoToKeyConnect() {
		return gizmoToKeyConnect;
	}

	public void setGizmoToKeyConnect(AbstractGizmo gizmoToKeyConnect) {
		this.gizmoToKeyConnect = gizmoToKeyConnect;
	}

	public AbstractGizmo getGizmoToKeyDisconnect() {
		return gizmoToKeyDisconnect;
	}

	public void setGizmoToKeyDisconnect(AbstractGizmo gizmoToKeyDisconnect) {
		this.gizmoToKeyDisconnect = gizmoToKeyDisconnect;
	}


	public void clearAllBoardGizmos () {
		boardGizmos.clear();

		// this is required along with boardGizmos.clear() as otherwise we have invisible balls roaming around the board
		this.ballList.clear();

		// forgot this first time round, need to clear the key connect map too
		gizmoKeyPressMap.clear();

		// re add the outwalls, maybe a silly way of doing things, could change it

		addGizmo(new OuterWall(-1, -1, 22, 1)); // start at top left, 20 along x
		addGizmo(new OuterWall(-1, -1, 1, 22)); // start at top left, 20 down y

		addGizmo(new OuterWall(20, -1, 1, 22)); // start at top right, 22 down y
		addGizmo(new OuterWall(-1, 20, 22, 1)); // start at bottom left, 22 along x
	}
}
