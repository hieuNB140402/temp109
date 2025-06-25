

package com.document.allreader.allofficefilereader.fc.hssf.util;


public final class AreaReference extends com.document.allreader.allofficefilereader.fc.ss.util.AreaReference {
    /**
     * Create an area ref from a string representation.  Sheet names containing special characters should be
     * delimited and escaped as per normal syntax rules for formulas.<br/> 
     * The area reference must be contiguous (i.e. represent a single rectangle, not a union of rectangles)
     */
    public AreaReference(String reference) {
		super(reference);
    }
    
    /**
     * Creates an area ref from a pair of Cell References.
     * Also normalises such that the top-left
     */
    public AreaReference(CellReference topLeft, CellReference botRight) {
		super(topLeft, botRight);
    }
}
