

package com.document.allreader.allofficefilereader.fc.hssf.util;

import com.document.allreader.allofficefilereader.fc.hssf.record.RecordInputStream;

/**
 * Implementation of the cell range address lists,like is described
 * in OpenOffice.org's Excel Documentation: excelfileformat.pdf sec 2.5.14 -
 * 'Cell Range Address List'
 * 
 * In BIFF8 there is a common way to store absolute cell range address lists in
 * several records (not formulas). A cell range address list consists of a field
 * with the number of ranges and the list of the range addresses. Each cell
 * range address (called an ADDR structure) contains 4 16-bit-values.
 * </p>
 * 
 * @deprecated use {@link com.document.allreader.allofficefilereader.fc.ss.util.CellRangeAddressList}
 * 
 * @author Dragos Buleandra (dragos.buleandra@trade2b.ro)
 */
public class CellRangeAddressList extends com.document.allreader.allofficefilereader.fc.ss.util.CellRangeAddressList {
	public CellRangeAddressList(int firstRow, int lastRow, int firstCol, int lastCol) {
		super(firstRow,lastRow,firstCol,lastCol);
	}
	public CellRangeAddressList() {
		super();
	}

	/**
	 * @param in the RecordInputstream to read the record from
	 */
	public CellRangeAddressList(RecordInputStream in) {
		super();
		int nItems = in.readUShort();

		for (int k = 0; k < nItems; k++) {
			_list.add(new CellRangeAddress(in));
		}
	}
}
