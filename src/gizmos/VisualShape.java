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
	private List<Integer> cList;

	public VisualShape(String t, Color c, Integer... l){

		this.type = t;
		this.colour = c;
		this.cList = Arrays.asList(l);

	}
}
