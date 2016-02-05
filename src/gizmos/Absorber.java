package gizmos;


public class Absorber extends Gizmo{

	public Absorber(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	protected void setTriggered(boolean isTriggered){
		super.setChanged();
		super.notifyObservers();
	}
}
