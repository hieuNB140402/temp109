

package com.document.allreader.allofficefilereader.fc.hssf.formula;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Josh Micich
 */
final class PlainCellCache {

	public static final class Loc {

		private final long _bookSheetColumn;

		private final int _rowIndex;

		public Loc(int bookIndex, int sheetIndex, int rowIndex, int columnIndex) {
			_bookSheetColumn = toBookSheetColumn(bookIndex, sheetIndex, columnIndex);
			_rowIndex = rowIndex;
		}

		public static long toBookSheetColumn(int bookIndex, int sheetIndex, int columnIndex) {
			return ((bookIndex   & 0xFFFFl) << 48)  +
                   ((sheetIndex  & 0xFFFFl) << 32) +
                   ((columnIndex & 0xFFFFl) << 0);
		}

		public Loc(long bookSheetColumn, int rowIndex) {
			_bookSheetColumn = bookSheetColumn;
			_rowIndex = rowIndex;
		}

		public int hashCode() {
			return (int)(_bookSheetColumn ^ (_bookSheetColumn >>> 32)) + 17 * _rowIndex;
		}

		public boolean equals(Object obj) {
			assert obj instanceof Loc : "these package-private cache key instances are only compared to themselves";
			Loc other = (Loc) obj;
			return _bookSheetColumn == other._bookSheetColumn && _rowIndex == other._rowIndex;
		}

		public int getRowIndex() {
			return _rowIndex;
		}

		public int getColumnIndex() {
            return (int)(_bookSheetColumn & 0x000FFFF);
		}

        public int getSheetIndex() {
            return (int)((_bookSheetColumn >> 32) & 0xFFFF);
        }

        public int getBookIndex() {
            return (int)((_bookSheetColumn >> 48) & 0xFFFF);
        }
	}

	private Map<Loc, PlainValueCellCacheEntry> _plainValueEntriesByLoc;

	public PlainCellCache() {
		_plainValueEntriesByLoc = new HashMap<Loc, PlainValueCellCacheEntry>();
	}
	public void put(Loc key, PlainValueCellCacheEntry cce) {
		_plainValueEntriesByLoc.put(key, cce);
	}
	public void clear() {
		_plainValueEntriesByLoc.clear();
	}
	public PlainValueCellCacheEntry get(Loc key) {
		return _plainValueEntriesByLoc.get(key);
	}
	public void remove(Loc key) {
		_plainValueEntriesByLoc.remove(key);
	}
}
