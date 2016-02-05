package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import controller.RunListener;

public class CollisionManager extends Observable implements Observer{


	protected List<Observer> collisionObservers = new ArrayList<Observer>();
	
	public CollisionManager(){

	}
	
	@Override
	public void addObserver(Observer o){
		collisionObservers.add(o);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		notifyObservers();
	}
}
