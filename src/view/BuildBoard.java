package view;
import java.util.HashSet;
import java.util.Set;

public class BuildBoard implements Board{
/**
    * <pre>
    *           0..*     0..*
    * BuildBoard ------------------------- BuildGUI
    *           buildBoard        &lt;       buildGUI
    * </pre>
    */
   private Set<BuildGUI> buildGUI;
   
   public Set<BuildGUI> getBuildGUI() {
      if (this.buildGUI == null) {
         this.buildGUI = new HashSet<BuildGUI>();
      }
      return this.buildGUI;
   }
   
   
}
