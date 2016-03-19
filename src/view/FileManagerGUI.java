package view;

import javax.swing.JFileChooser;

public class FileManagerGUI {

	public FileManagerGUI() {

	}
	
	public String getSaveFilePath(){
	    JFileChooser chooser = new JFileChooser("boards");
	    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
	        return chooser.getSelectedFile().getPath()+".txt";
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
