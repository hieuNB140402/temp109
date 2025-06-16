

package com.document.allreader.allofficefilereader.fc.hssf.formula;

/**
 * Enumeration of various formula types.<br/>
 *
 * For POI internal use only
 *
 * @author Josh Micich
 */
public final class FormulaType {
	private FormulaType() {
		// no instances of this class
	}
	public static final int CELL = 0;
	public static final int SHARED = 1;
	public static final int ARRAY =2;
	public static final int CONDFORMAT = 3;
	public static final int NAMEDRANGE = 4;
	// this constant is currently very specific.  The exact differences from general data
	// validation formulas or conditional format formulas is not known yet
	public static final int DATAVALIDATION_LIST = 5;

}
