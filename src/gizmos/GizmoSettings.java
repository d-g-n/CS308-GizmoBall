package gizmos;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Declan on 06/02/2016.
 */
public class GizmoSettings {

	private double reflectionCoefficient;
	private Color gizmoColour;
	private List<Circle> parsedCircles;
	private List<LineSegment> parsedLines;
	private List<VisualShape> visualShapes;

	public GizmoSettings(AbstractGizmo g){

		this.parsedCircles = new ArrayList<Circle>();
		this.parsedLines = new ArrayList<LineSegment>();
		this.visualShapes = new ArrayList<VisualShape>();


		String jsonToLoad = "settings/gizmo/" + g.getClass().getSimpleName() + ".json";

		try {

			JsonValue jv = Json.parse(new FileReader(jsonToLoad));

			// first get reflection coefficient
			
			reflectionCoefficient = jv.asObject().getFloat("ReflectionCoefficient", 0);

			// then grab the stored colour

			try {

				Field field = Color.class.getField(jv.asObject().getString("ShapeColour", ""));
				gizmoColour = (Color)field.get(null);

			} catch (Exception e) {
				gizmoColour = Color.white; // Not defined
			}

			// now get the MIT physics line definitions and store them

			for(JsonValue entry : jv.asObject().get("PhysicsDef").asArray()){
				String type = entry.asArray().get(0).asString();


				if(type.equals("Line")){ // if is line index 1 and 2 are coords
					double xSrc = entry.asArray().get(1).asArray().get(0).asDouble();
					double ySrc = entry.asArray().get(1).asArray().get(1).asDouble();
					double xDest = entry.asArray().get(2).asArray().get(0).asDouble();
					double yDest = entry.asArray().get(2).asArray().get(1).asDouble();

					parsedLines.add(new LineSegment(xSrc, ySrc, xDest, yDest));
				} else if(type.equals("Circle")){ // if is circle index 1 is origin and index 2 is radius
					double xSrc = entry.asArray().get(1).asArray().get(0).asDouble();
					double ySrc = entry.asArray().get(1).asArray().get(1).asDouble();
					double radius = entry.asArray().get(2).asDouble();

					parsedCircles.add(new Circle(xSrc, ySrc, radius));
				}
			}


			// now finally get the stored coordinated for the visible shapes and encapsulate them in a VisualShape obj

			for(JsonValue entry : jv.asObject().get("VisualDef").asArray()){
				String type = entry.asArray().get(0).asString();

				if(type.equals("Square") || type.equals("Triangle")){ // if is line index 1 and 2 are coords
					double xSrc = entry.asArray().get(1).asArray().get(0).asDouble();
					double ySrc = entry.asArray().get(1).asArray().get(1).asDouble();
					double xDest = entry.asArray().get(2).asArray().get(0).asDouble();
					double yDest = entry.asArray().get(2).asArray().get(1).asDouble();

					visualShapes.add(new VisualShape(type, gizmoColour, xSrc, ySrc, xDest, yDest));
				} else if(type.equals("Circle")){ // if is circle index 1 is origin and index 2 is radius
					double xSrc = entry.asArray().get(1).asArray().get(0).asDouble();
					double ySrc = entry.asArray().get(1).asArray().get(1).asDouble();
					double radius = entry.asArray().get(2).asDouble();

					visualShapes.add(new VisualShape(type, gizmoColour, xSrc, ySrc, radius));
				}
			}
		} catch (IOException e) {
		}
	}

	public List<Circle> getParsedCircles() {
		return parsedCircles;
	}

	public double getReflectionCoefficient() {
		return reflectionCoefficient;
	}

	public List<LineSegment> getParsedLines() {
		return parsedLines;
	}

	public List<VisualShape> getVisualShapes() { return visualShapes; }
}
