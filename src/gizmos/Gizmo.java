package gizmos;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class Gizmo extends Observable{
	
	protected int xpos, ypos, width, height;
	protected List<Observer> observers = new ArrayList<Observer>();
	
	public Gizmo(int x,int y, int width, int height){
		this.xpos = x;
		this.ypos = y;
		this.width = width;
		this.height = height;
	}
	
	public void addObserver(Observer o){
		observers.add(o);
	}
}
