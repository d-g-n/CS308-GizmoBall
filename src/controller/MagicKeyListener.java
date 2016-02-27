package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import gizmos.AbstractGizmo;
import model.ProjectManager;

public class MagicKeyListener implements KeyListener {
	private ProjectManager pm;
	
	public MagicKeyListener(ProjectManager pm){
		this.pm = pm;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		for(Map.Entry<Map.Entry<String, Integer>, AbstractGizmo> ent : pm.getKeyConnects().entrySet()){
			Map.Entry<String, Integer> inEnt = ent.getKey();
			AbstractGizmo giz = ent.getValue();

			if(!inEnt.getKey().equals("down"))
				continue;

			if(key == inEnt.getValue())
				giz.doTrigger();
		}

	}
		
	@Override
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();

		for(Map.Entry<Map.Entry<String, Integer>, AbstractGizmo> ent : pm.getKeyConnects().entrySet()){
			Map.Entry<String, Integer> inEnt = ent.getKey();
			AbstractGizmo giz = ent.getValue();

			if(!inEnt.getKey().equals("up"))
				continue;

			if(key == inEnt.getValue())
				giz.doTrigger();
		}
	}


}
