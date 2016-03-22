package controller;

import gizmos.*;
import model.ProjectManager;
import physics.Vect;
import view.CommandEnums;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.*;

/**
 * Created by Declan on 22/03/2016.
 */
final class CommandList {



	static {

		// add gizmo commands

		CommandMapper.addNewCommand(
				"add_ball",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Add Ball",
				CommandEnums.CATEGORY_GIZMO,
				"icons/gizmos/ball.png",
				(firstX, firstY, secondX, secondY) -> CommandMapper.getPMRef().addGizmo(new Ball(firstX + 0.5, firstY + 0.5, new Vect(0, 0)))
		);

		CommandMapper.addNewCommand(
				"add_squarebumper",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Add SquareBumper",
				CommandEnums.CATEGORY_GIZMO,
				"icons/gizmos/square.png",
				(firstX, firstY, secondX, secondY) -> CommandMapper.getPMRef().addGizmo(new SquareBumper(firstX, firstY))
		);

		CommandMapper.addNewCommand(
				"add_trianglebumper",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Add TriangleBumper",
				CommandEnums.CATEGORY_GIZMO,
				"icons/gizmos/triangle.png",
				(firstX, firstY, secondX, secondY) -> CommandMapper.getPMRef().addGizmo(new TriangleBumper(firstX, firstY))
		);

		CommandMapper.addNewCommand(
				"add_circlebumper",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Add CircleBumper",
				CommandEnums.CATEGORY_GIZMO,
				"icons/gizmos/circle.png",
				(firstX, firstY, secondX, secondY) -> CommandMapper.getPMRef().addGizmo(new CircleBumper(firstX, firstY))
		);

		CommandMapper.addNewCommand(
				"add_leftflipper",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Add LeftFlipper",
				CommandEnums.CATEGORY_GIZMO,
				"icons/gizmos/leftflipper.png",
				(firstX, firstY, secondX, secondY) -> CommandMapper.getPMRef().addGizmo(new LeftFlipper(firstX, firstY))
		);

		CommandMapper.addNewCommand(
				"add_rightflipper",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Add RightFlipper",
				CommandEnums.CATEGORY_GIZMO,
				"icons/gizmos/rightflipper.png",
				(firstX, firstY, secondX, secondY) -> CommandMapper.getPMRef().addGizmo(new RightFlipper(firstX, firstY))
		);

		CommandMapper.addNewCommand(
				"add_deathsquare",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Add DeathSquare",
				CommandEnums.CATEGORY_GIZMO,
				"icons/gizmos/deathsquare.png",
				(firstX, firstY, secondX, secondY) -> CommandMapper.getPMRef().addGizmo(new DeathSquare(firstX, firstY))
		);

		CommandMapper.addNewCommand(
				"add_booster",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Add Booster",
				CommandEnums.CATEGORY_GIZMO,
				"icons/gizmos/booster.png",
				(firstX, firstY, secondX, secondY) -> CommandMapper.getPMRef().addGizmo(new BoosterGizmo(firstX, firstY))
		);

		CommandMapper.addNewCommand(
				"add_teleporter",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Add Teleporter",
				CommandEnums.CATEGORY_GIZMO,
				"icons/gizmos/teleporter.png",
				(firstX, firstY, secondX, secondY) -> CommandMapper.getPMRef().addGizmo(new Teleporter(firstX, firstY))
		);

		CommandMapper.addNewCommand(
				"add_absorber",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Add Absorber",
				CommandEnums.CATEGORY_GIZMO,
				"icons/gizmos/absorber.png",
				(firstX, firstY, secondX, secondY) -> CommandMapper.getPMRef().addGizmo(new Absorber(firstX, firstY, Math.abs(secondX - firstX + 1), Math.abs(secondY - firstY + 1)))
		);

		// manipulate gizmos

		CommandMapper.addNewCommand(
				"manip_rotate",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Rotate Gizmo",
				CommandEnums.CATEGORY_GIZMOMANIPULATION,
				"icons/commands/rotate.png",
				(firstX, firstY, secondX, secondY) -> {
					ProjectManager pm = CommandMapper.getPMRef();
					AbstractGizmo gizAt = pm.getGizmoByCoordinate(firstX, firstY);
					if(gizAt != null)
						gizAt.rotateClockwise();
				}
		);

		CommandMapper.addNewCommand(
				"manip_move",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Move Gizmo",
				CommandEnums.CATEGORY_GIZMOMANIPULATION,
				"icons/commands/move.png",
				(firstX, firstY, secondX, secondY) -> {
					ProjectManager pm = CommandMapper.getPMRef();
					AbstractGizmo gizAt = pm.getGizmoByCoordinate(firstX, firstY);
					if(gizAt != null && pm.canPlaceGizmoAt(secondX, secondY, gizAt.getWidth(), gizAt.getHeight()))
						gizAt.moveGizmo(secondX, secondY);
				}
		);

		CommandMapper.addNewCommand(
				"manip_delete",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Delete Gizmo",
				CommandEnums.CATEGORY_GIZMOMANIPULATION,
				"icons/commands/delete.png",
				(firstX, firstY, secondX, secondY) -> {
					ProjectManager pm = CommandMapper.getPMRef();
					AbstractGizmo gizAt = pm.getGizmoByCoordinate(firstX, firstY);
					if(gizAt != null)
						pm.deleteGizmo(gizAt);
					pm.playSound("delete");
				}
		);

		// commands

		CommandMapper.addNewCommand(
				"reload_board",
				CommandMapper.CommandLevel.BUTTON_LEVEL,
				"Reload GizmoConstants",
				CommandEnums.CATEGORY_COMMANDS,
				"icons/commands/reload.png",
				(firstX, firstY, secondX, secondY) -> {
					ProjectManager pm = CommandMapper.getPMRef();
					pm.setStatusLabel("GizmoConstants Restored");
					int confirmation1 = JOptionPane.YES_NO_OPTION;
					int result1 = JOptionPane.showConfirmDialog(null, "Are you sure you want to restore the board?",
							"Warning", confirmation1);
					if (result1 == 0) {
						pm.clearAllBoardGizmos();
						pm.restartGame();
					}
				}
		);

		CommandMapper.addNewCommand(
				"dynamic_play",
				CommandMapper.CommandLevel.BUTTON_LEVEL,
				"Dynamic Play",
				CommandEnums.CATEGORY_COMMANDS,
				"icons/commands/play.png",
				(firstX, firstY, secondX, secondY) -> {
					ProjectManager pm = CommandMapper.getPMRef();
					if(!pm.isDynamicMode()){
						pm.dynamicModeOn();
						pm.setStatusLabel("Dynamic Mode ON");
						pm.getTimer().start();
					}else {
						pm.dynamicModeOff();
						pm.setStatusLabel("Dynamic Mode OFF");
						pm.getTimer().stop();
					}
				}
		);

		CommandMapper.addNewCommand(
				"clear_board",
				CommandMapper.CommandLevel.BUTTON_LEVEL,
				"Clear GizmoConstants",
				CommandEnums.CATEGORY_COMMANDS,
				"icons/commands/clear.png",
				(firstX, firstY, secondX, secondY) -> {
					ProjectManager pm = CommandMapper.getPMRef();
					pm.setStatusLabel("Clearing GizmoConstants..");
					int confirmation = JOptionPane.YES_NO_OPTION;
					int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the entire board?",
							"Warning", confirmation);
					if (result == 0) {
						pm.clearAllBoardGizmos();
						pm.playSound("clearboard");
					}
				}
		);

		CommandMapper.addNewCommand(
				"connect_gizmos",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Connect Gizmos",
				CommandEnums.CATEGORY_COMMANDS,
				"icons/commands/connect.png",
				(firstX, firstY, secondX, secondY) -> {
					ProjectManager pm = CommandMapper.getPMRef();
					AbstractGizmo gizAt = pm.getGizmoByCoordinate(firstX, firstY);
					AbstractGizmo destGiz = pm.getGizmoByCoordinate(secondX, secondY);

					if(gizAt != null && destGiz != null)
						gizAt.addGizmoListener(destGiz);
				}
		);

		CommandMapper.addNewCommand(
				"disconnect_gizmos",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Disconnect Gizmos",
				CommandEnums.CATEGORY_COMMANDS,
				"icons/commands/disconnect.png",
				(firstX, firstY, secondX, secondY) -> {
					ProjectManager pm = CommandMapper.getPMRef();
					AbstractGizmo gizAt = pm.getGizmoByCoordinate(firstX, firstY);
					AbstractGizmo destGiz = pm.getGizmoByCoordinate(secondX, secondY);

					if(gizAt != null && destGiz != null)
						gizAt.removeGizmoListener(destGiz);
				}
		);

		CommandMapper.addNewCommand(
				"keyconnect_gizmos",
				CommandMapper.CommandLevel.BOARD_LEVEL,
				"Keyconnect Gizmo",
				CommandEnums.CATEGORY_COMMANDS,
				"icons/commands/keyconnect.png",
				(firstX, firstY, secondX, secondY) -> {
					ProjectManager pm = CommandMapper.getPMRef();
					AbstractGizmo gizAt = pm.getGizmoByCoordinate(firstX, firstY);

					if(gizAt != null){
						String k = JOptionPane.showInputDialog(null, "Enter the key to connect this gizmo to: ");

						if(k != null && k.length() > 0){
							int cCode = KeyEvent.getExtendedKeyCodeForChar(k.charAt(0));

							Object[] opt = {"down", "up"};

							String downOrUp = (String) JOptionPane.showInputDialog(
									null,
									"Choose when to activate this keypress.",
									"Activate on...",
									JOptionPane.PLAIN_MESSAGE,
									null,
									opt,
									"up"
									);

							if(downOrUp != null){
								pm.addKeyConnect(gizAt.getName(), cCode, downOrUp);
							}

						}
					}
				}
		);

		CommandMapper.addNewCommand(
				"keydisconnect_gizmos",
				CommandMapper.CommandLevel.BUTTON_LEVEL,
				"Keydisconnect Gizmos",
				CommandEnums.CATEGORY_COMMANDS,
				"icons/commands/keydisconnect.png",
				(firstX, firstY, secondX, secondY) -> {

					ProjectManager pm = CommandMapper.getPMRef();

					List<String> keys = new ArrayList<String>();

					for(Map.Entry<Map.Entry<String, Integer>, List<AbstractGizmo>> e : pm.getKeyConnects().entrySet()){
						keys.add(KeyEvent.getKeyText(e.getKey().getValue()) + " - " + e.getKey().getKey());
					}

					Object[] opt = keys.toArray();

					if(opt.length == 0)
						return;

					String keyToDisconnect = (String) JOptionPane.showInputDialog(
							null,
							"Choose key to disconnect:",
							"Disconnect key",
							JOptionPane.PLAIN_MESSAGE,
							null,
							opt,
							opt[0]
					);

					if(keyToDisconnect != null){
						String[] keyExplode = keyToDisconnect.split(" - ");
						int keyCode = KeyEvent.getExtendedKeyCodeForChar(keyExplode[0].charAt(0));
						String downOrUp = keyExplode[1];

						List<String> gizArray = new ArrayList<>();

						List<AbstractGizmo> gizList = pm.getKeyConnects().get(new AbstractMap.SimpleEntry<String, Integer>(downOrUp, keyCode));

						if(gizList == null)
							return;

						int i = 0;
						for(AbstractGizmo gizmo : gizList){
							gizArray.add(i + ": " + gizmo.getType() + " at (" + gizmo.getXPos() + ", " + gizmo.getYPos() + ")");

							i++;
						}

						Object[] optg = gizArray.toArray();

						if(optg.length == 0)
							return;

						String gizmoToDisconnect = (String) JOptionPane.showInputDialog(
								null,
								"Choose gizmo to disconnect from:",
								"Disconnect gizmo",
								JOptionPane.PLAIN_MESSAGE,
								null,
								optg,
								optg[0]
						);

						if(gizmoToDisconnect != null){

							int gizIndex = Integer.valueOf(gizmoToDisconnect.substring(0, gizmoToDisconnect.indexOf(':')));

							pm.removeKeyConnect(gizList.get(gizIndex).getName(), keyCode, downOrUp);
						}
					}

				}
		);

	}

}
