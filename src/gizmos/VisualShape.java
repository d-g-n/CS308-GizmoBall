package gizmos;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Declan on 06/02/2016.
 */
public class VisualShape {

	private String type;
	private Color colour;
	private List<Double> cList;

	public VisualShape(String t, Color c, Double... l){

		this.type = t;
		this.colour = c;
		this.cList = Arrays.asList(l);

	}


	public String getType() {
		return type;
	}

	public List<Double> getValList() {
		return cList;
	}

	public Color getColour() {
		return colour;
	}
}
