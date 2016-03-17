package model;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import controller.MenuListener;
import gizmos.*;
import physics.Vect;
import view.Board;
import view.RunGUI;

public class ProjectManager extends Observable{
	
	private CollisionManager cManager;
	private FileManager fManager;
	private MenuListener menuListener = new MenuListener();
	private List<AbstractGizmo> boardGizmos;
	private Map<Map.Entry<String, Integer>, List<AbstractGizmo>> gizmoKeyPressMap;
	private static Ball ball;
	private String focusedButton;
	private boolean buildModeOn = false;
	private AbstractGizmo gizmoToConnect = null;
	private AbstractGizmo gizmoToMove = null;
	private AbstractGizmo gizmoToDisconnect = null;
	private int absorberToBeAddedX = -1, absorberToBeAddedY = -1;
	private String statusLabel;



	public ProjectManager(){
		boardGizmos = new ArrayList<>();
		gizmoKeyPressMap = new HashMap<>();
		cManager = new CollisionManager(this);
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

	public String getFocusedButton() {
		return focusedButton;
	}

	public void setFocusedButton(String focusedButton) {
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

	public void loadFile(String fileName) {
		fManager = new FileManager(this, fileName);
		fManager.loadFile();

		this.setChanged();
		this.notifyObservers();
	}

	public void setBall(Ball ball){
		this.ball = ball;
	}

	public static Ball getBall(){
		return ball;
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
	


}
