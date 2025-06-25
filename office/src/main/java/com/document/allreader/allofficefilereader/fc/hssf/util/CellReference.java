

package com.document.allreader.allofficefilereader.fc.hssf.util;

/**
 * Common conversion functions between Excel style A1, C27 style
 *  cell references, and POI usermodel style row=0, column=0
 *  style references.
 * @author  Avik Sengupta
 * @author  Dennis Doubleday (patch to seperateRowColumns())
 */
public final class CellReference extends com.document.allreader.allofficefilereader.fc.ss.util.CellReference {
    /**
     * Create an cell ref from a string representation.  Sheet names containing special characters should be
     * delimited and escaped as per normal syntax rules for formulas.
     */
    public CellReference(String cellRef) {
    	super(cellRef);
    }

    public CellReference(int pRow, int pCol) {
    	super(pRow, pCol, true, true);
    }
    
    public CellReference(int pRow, int pCol, boolean pAbsRow, boolean pAbsCol) {
        super(null, pRow, pCol, pAbsRow, pAbsCol);
    }

    public CellReference(String pSheetName, int pRow, int pCol, boolean pAbsRow, boolean pAbsCol) {
    	super(pSheetName, pRow, pCol, pAbsRow, pAbsCol);
    }
}
