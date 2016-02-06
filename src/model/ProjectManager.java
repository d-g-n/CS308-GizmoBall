package model;

import java.util.Observable;

import controller.MenuListener;
import controller.RunListener;

public class ProjectManager extends Observable{
	
	private static CollisionManager cManager;
	private static FileManager fManager;
	private RunListener runListener = new RunListener();
	private MenuListener menuListener = new MenuListener();
	
	public ProjectManager(){
		cManager = new CollisionManager();
		fManager = new FileManager();
	}
}
