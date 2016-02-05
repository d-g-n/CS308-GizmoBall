package gizmos;

public abstract class Bumper extends Gizmo{

	protected boolean isTriggered;
	
	public Bumper(int x,int y, int width, int height){
		super(x,y,width,height);
	}
	
	protected void setTriggered(boolean isTriggered){
		this.isTriggered = isTriggered;
		super.setChanged();
		super.notifyObservers();
	}
}
