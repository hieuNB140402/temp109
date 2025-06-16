

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;


import com.document.allreader.allofficefilereader.fc.ss.util.CellReference;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianInput;


/**
 * ReferencePtg - handles references (such as A1, A2, IA4)
 * @author  Andrew C. Oliver (acoliver@apache.org)
 * @author Jason Height (jheight at chariot dot net dot au)
 */
public final class RefPtg extends Ref2DPtgBase {
	public final static byte sid = 0x24;

	/**
	 * Takes in a String representation of a cell reference and fills out the
	 * numeric fields.
	 */
	public RefPtg(String cellref) {
		super(new CellReference(cellref));
	}

	public RefPtg(int row, int column, boolean isRowRelative, boolean isColumnRelative) {
		super(row, column, isRowRelative, isColumnRelative);
	}

	public RefPtg(LittleEndianInput in)  {
		super(in);
	}

	public RefPtg(CellReference cr) {
		super(cr);
	}

	protected byte getSid() {
		return sid;
	}
}
