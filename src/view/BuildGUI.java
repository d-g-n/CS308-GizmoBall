package view;

import java.util.Set;
import java.util.HashSet;
import controller.BuildListener;

public class BuildGUI implements GBallGui {
   /**
    * <pre>
    *           1..1     0..*
    * BuildGUI ------------------------> BuildBoard
    *           &gt;       buildBoard
    * </pre>
    */
   private Set<BuildBoard> buildBoard;
   
   public Set<BuildBoard> getBuildBoard() {
      if (this.buildBoard == null) {
         this.buildBoard = new HashSet<BuildBoard>();
      }
      return this.buildBoard;
   }
   
   /**
    * <pre>
    *           1..1     0..*
    * BuildGUI ------------------------> BuildListener
    *           &lt;       buildListener
    * </pre>
    */
   private Set<BuildListener> buildListener;
   
   public Set<BuildListener> getBuildListener() {
      if (this.buildListener == null) {
         this.buildListener = new HashSet<BuildListener>();
      }
      return this.buildListener;
   }
   
   }
