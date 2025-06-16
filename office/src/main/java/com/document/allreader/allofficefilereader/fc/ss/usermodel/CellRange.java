

package com.document.allreader.allofficefilereader.fc.ss.usermodel;

import java.util.Iterator;



/**
 * Represents a rectangular region of a {@link Sheet}
 *
 * @author Josh Micich
 */
public interface CellRange<C extends ICell> extends Iterable<C> {

	int getWidth();
	int getHeight();
	/**
	 * Gets the number of cells in this range.
	 * @return <tt>height * width </tt>
	 */
	int size();
	String getReferenceText();

	/**
	 * @return the cell at relative coordinates (0,0).  Never <code>null</code>.
	 */
	C getTopLeftCell();

	/**
	 * @param relativeRowIndex must be between <tt>0</tt> and <tt>height-1</tt>
	 * @param relativeColumnIndex must be between <tt>0</tt> and <tt>width-1</tt>
	 * @return the cell at the specified coordinates.  Never <code>null</code>.
	 */
	C getCell(int relativeRowIndex, int relativeColumnIndex);
	/**
	 * @return a flattened array of all the cells in this {@link CellRange}
	 */
	C[] getFlattenedCells();
	/**
	 * @return a 2-D array of all the cells in this {@link CellRange}.  The first
	 * array dimension is the row index (values <tt>0...height-1</tt>)
	 * and the second dimension is the column index (values <tt>0...width-1</tt>)
	 */
	C[][] getCells();

	/**
	 * @return an {@link Iterator} over all cells in this range.  Iteration starts
	 * with all cells in the first row followed by all cells in the next row, etc.
	 */
	Iterator<C> iterator();
}
