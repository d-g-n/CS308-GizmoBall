package model;

import controller.MenuListener;
import controller.RunListener;
import gizmos.AbstractGizmo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ProjectManager extends Observable{
	
	private static CollisionManager cManager;
	private static FileManager fManager;
	private RunListener runListener = new RunListener();
	private MenuListener menuListener = new MenuListener();
	private List<AbstractGizmo> boardGizmos;
	
	public ProjectManager(){
		cManager = new CollisionManager();
		fManager = new FileManager();
		boardGizmos = new ArrayList<AbstractGizmo>();
	}
}
