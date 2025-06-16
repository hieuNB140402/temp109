

package com.document.allreader.allofficefilereader.fc.hssf.util;

import com.document.allreader.allofficefilereader.fc.hssf.record.RecordInputStream;
import com.document.allreader.allofficefilereader.fc.hssf.record.SelectionRecord;

/**
 * See OOO documentation: excelfileformat.pdf sec 2.5.14 - 'Cell Range Address'<p/>
 *
 * Note - {@link SelectionRecord} uses the BIFF5 version of this structure
 * @deprecated use {@link com.document.allreader.allofficefilereader.fc.ss.util.HSSFCellRangeAddress}
 * @author Dragos Buleandra (dragos.buleandra@trade2b.ro)
 */
public class CellRangeAddress extends com.document.allreader.allofficefilereader.fc.ss.util.HSSFCellRangeAddress {

	public CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol) {
		super(firstRow, lastRow, firstCol, lastCol);
	}
	public CellRangeAddress(RecordInputStream in) {
		super(in);
	}
}
