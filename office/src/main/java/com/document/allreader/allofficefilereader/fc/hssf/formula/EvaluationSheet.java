

package com.document.allreader.allofficefilereader.fc.hssf.formula;

/**
 * Abstracts a sheet for the purpose of formula evaluation.<br/>
 * 
 * For POI internal use only
 * 
 * @author Josh Micich
 */
public interface EvaluationSheet {

	/**
	 * @return <code>null</code> if there is no cell at the specified coordinates
	 */
	EvaluationCell getCell(int rowIndex, int columnIndex);
}
