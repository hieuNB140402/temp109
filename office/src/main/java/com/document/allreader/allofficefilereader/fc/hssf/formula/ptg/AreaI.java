

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;

/**
 * Common interface for AreaPtg and Area3DPtg, and their child classes.
 */
public interface AreaI {
	/**
	 * @return the first row in the area
	 */
	public int getFirstRow();

	/**
	 * @return last row in the range (x2 in x1,y1-x2,y2)
	 */
	public int getLastRow();

	/**
	 * @return the first column number in the area.
	 */
	public int getFirstColumn();

	/**
	 * @return lastcolumn in the area
	 */
	public int getLastColumn();

	class OffsetArea implements AreaI {

		private final int _firstColumn;
		private final int _firstRow;
		private final int _lastColumn;
		private final int _lastRow;

		public OffsetArea(int baseRow, int baseColumn, int relFirstRowIx, int relLastRowIx,
				int relFirstColIx, int relLastColIx) {
			_firstRow = baseRow + Math.min(relFirstRowIx, relLastRowIx);
			_lastRow = baseRow + Math.max(relFirstRowIx, relLastRowIx);
			_firstColumn = baseColumn + Math.min(relFirstColIx, relLastColIx);
			_lastColumn = baseColumn + Math.max(relFirstColIx, relLastColIx);
		}

		public int getFirstColumn() {
			return _firstColumn;
		}

		public int getFirstRow() {
			return _firstRow;
		}

		public int getLastColumn() {
			return _lastColumn;
		}

		public int getLastRow() {
			return _lastRow;
		}
	}
}
