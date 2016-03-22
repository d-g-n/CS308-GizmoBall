package view;

import javax.swing.JFileChooser;

/**
 * The FileManagerGUI creates a file chooser to either save
 * or load a board.
 *
 */
public class FileManagerGUI {

	public String getSaveFilePath(){
	    JFileChooser chooser = new JFileChooser("boards");
	    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	String path = chooser.getSelectedFile().getPath();
	    	if(path.substring(path.length() - 4).equals(".txt")){
	    		return path;
	    	}
	    	else{
	    		return path+".txt";
	    	}
	    }
	    return null;
		
	}

	public String getLoadFilePath() {
		JFileChooser chooser = new JFileChooser("boards");
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			java.io.File file = chooser.getSelectedFile();
			return file.getAbsolutePath();
		}
		return null;
	}
}
