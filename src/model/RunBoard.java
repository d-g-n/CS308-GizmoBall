package model;

import java.util.Set;

import view.RunGUI;

import java.util.HashSet;

public class RunBoard implements Board {
   /**
    * <pre>
    *           0..*     0..*
    * RunBoard ------------------------- RunGUI
    *           runBoard        &gt;       runGUI
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
