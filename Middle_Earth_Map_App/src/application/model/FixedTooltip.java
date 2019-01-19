package application.model;

import javafx.scene.control.Tooltip;
import javafx.stage.Window;

/**
 * Subclass to Tooltip, to fix the bug that when the Tooltip is active and 
 * a second stage is opened, when the node on the primary stage is hovered, 
 * it will come in front of the second stage.
 */

public class FixedTooltip extends Tooltip {

    public FixedTooltip(String string) {
        super(string);
    }

    // overload the show() method to only show if the owning window is focused
    @Override
    protected void show() {        
        Window owner = getOwnerWindow();        
        if (owner.isFocused())
            super.show(); 
    }    

}