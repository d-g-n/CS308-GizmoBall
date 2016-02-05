package view;

import java.util.Set;
import java.util.HashSet;
import controller.RunListener;
import controller.MenuListener;

public class RunGUI implements GBallGui {
   /**
    * <pre>
    *           0..*     0..*
    * RunGUI ------------------------- RunBoard
    *           runGUI        &lt;       runBoard
    * </pre>
    */
   private Set<RunBoard> runBoard;
   
   public Set<RunBoard> getRunBoard() {
      if (this.runBoard == null) {
         this.runBoard = new HashSet<RunBoard>();
      }
      return this.runBoard;
   }
   
   /**
    * <pre>
    *           1..1     0..*
    * RunGUI ------------------------> RunListener
    *           &lt;       runListener
    * </pre>
    */
   private Set<RunListener> runListener;
   
   public Set<RunListener> getRunListener() {
      if (this.runListener == null) {
         this.runListener = new HashSet<RunListener>();
      }
      return this.runListener;
   }
   
   /**
    * <pre>
    *           1..1     0..*
    * RunGUI ------------------------> MenuListener
    *           &lt;       menuListener
    * </pre>
    */
   private Set<MenuListener> menuListener;
   
   public Set<MenuListener> getMenuListener() {
      if (this.menuListener == null) {
         this.menuListener = new HashSet<MenuListener>();
      }
      return this.menuListener;
   }
   
   }
