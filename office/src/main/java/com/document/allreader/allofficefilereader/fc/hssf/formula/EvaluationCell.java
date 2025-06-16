

package com.document.allreader.allofficefilereader.fc.hssf.formula;

/**
 * Abstracts a cell for the purpose of formula evaluation.  This interface represents both formula
 * and non-formula cells.<br/>
 * 
 * For POI internal use only
 * 
 * @author Josh Micich
 */
public interface EvaluationCell {
	/**
	 * @return an Object that identifies the underlying cell,
     * suitable for use as a key in a {@link java.util.HashMap}
	 */
	Object getIdentityKey();

	EvaluationSheet getSheet();
	int getRowIndex();
	int getColumnIndex();
	int getCellType();

	double getNumericCellValue();
	String getStringCellValue();
	boolean getBooleanCellValue();
	int getErrorCellValue();
}
