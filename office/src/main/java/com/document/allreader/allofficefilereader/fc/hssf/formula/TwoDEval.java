

package com.document.allreader.allofficefilereader.fc.hssf.formula;

import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.AreaEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;

/**
 * Common interface of {@link AreaEval} and {@link com.document.allreader.allofficefilereader.fc.hssf.formula.eval.AreaEvalBase}
 *
 * @author Josh Micich
 */
public interface TwoDEval extends ValueEval {

	/**
	 * @param rowIndex relative row index (zero based)
	 * @param columnIndex relative column index (zero based)
	 * @return element at the specified row and column position
	 */
	ValueEval getValue(int rowIndex, int columnIndex);

	int getWidth();
	int getHeight();

	/**
	 * @return <code>true</code> if the area has just a single row, this also includes
	 * the trivial case when the area has just a single cell.
	 */
	boolean isRow();

	/**
	 * @return <code>true</code> if the area has just a single column, this also includes
	 * the trivial case when the area has just a single cell.
	 */
	boolean isColumn();

	/**
	 * @param rowIndex relative row index (zero based)
	 * @return a single row {@link TwoDEval}
	 */
	TwoDEval getRow(int rowIndex);
	/**
	 * @param columnIndex relative column index (zero based)
	 * @return a single column {@link TwoDEval}
	 */
	TwoDEval getColumn(int columnIndex);


    /**
     * @return true if the  cell at row and col is a subtotal
     */
    boolean isSubTotal(int rowIndex, int columnIndex);

}
