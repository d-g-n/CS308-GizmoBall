package controller;
import java.util.HashSet;
import java.util.Set;
import view.RunGUI;

public class MenuListener {
/**
    * <pre>
    *           0..*     0..*
    * MenuListener ------------------------- RunGUI
    *           menuListener        &gt;       runGUI
    * </pre>
    */
   private Set<RunGUI> runGUI;
   
   public Set<RunGUI> getRunGUI() {
      if (this.runGUI == null) {
         this.runGUI = new HashSet<RunGUI>();
      }
      return this.runGUI;
   }
   
   
}
