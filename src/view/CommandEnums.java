package view;

/**
 * Created by Declan on 22/03/2016.
 */
public enum CommandEnums {
	CATEGORY_GIZMO ("Gizmos"),
	CATEGORY_GIZMOMANIPULATION ("Manipulate Gizmos"),
	CATEGORY_COMMANDS ("Commands"),
	CATEGORY_OTHER ("Settings");

	private String catName;

	CommandEnums(String com){
		this.catName = com;
	}

	String getCategory(){
		return catName;
	}
}
