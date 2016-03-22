package controller;

import model.ProjectManager;
import view.CommandEnums;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Declan on 22/03/2016.
 */
public class CommandMapper {

	// format is string which is the keyname such as "add_absorber" or "delete_gizmo"
	// command is a wrapper for the various components of a command structure, should probably be backed by an interface
	private static Map<String, Command> commandMap = new HashMap<>();
	private static ProjectManager pm;

	public static void loadCommands(){
		new CommandList();
	}

	public static void addNewCommand(String uniqueID, CommandLevel cmLevel, String prettyName, CommandEnums category, String iconPath, CommandAction comAct){
		commandMap.put(uniqueID, new Command(cmLevel, prettyName, category, iconPath, comAct));
	}

	public static Map<String, Command> getCommandMap(){
		return commandMap;
	}

	public static void setPMRef(ProjectManager PMRef) {
		CommandMapper.pm = PMRef;
	}

	public static ProjectManager getPMRef(){
		return pm;
	}

	public static Command getCommandByUID(String uniqueID){
		return commandMap.get(uniqueID);
	}

	public enum CommandLevel{
		BOARD_LEVEL,
		BUTTON_LEVEL
	}

	public static class Command{

		private CommandLevel cmLevel;
		private String prettyName, iconPath;
		private CommandEnums category;
		private CommandAction comAct;


		public Command(CommandLevel cmLevel, String prettyName, CommandEnums cat, String iconPath, CommandAction comAct){

			this.cmLevel = cmLevel;
			this.prettyName = prettyName;
			this.category= cat;
			this.iconPath = iconPath;
			this.comAct = comAct;

		}

		public CommandLevel getCommandLevel(){
			return cmLevel;
		}

		public String getPrettyName(){
			return prettyName;
		}

		public CommandEnums getCategory(){
			return category;
		}

		public String getIconPath(){
			return iconPath;
		}

		public CommandAction getAction(){
			return comAct;
		}

	}

	public interface CommandAction{

		void onClickAndRelease(int firstX, int firstY, int secondX, int secondY);

	}
}
