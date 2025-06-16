

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;


import com.document.allreader.allofficefilereader.fc.ss.util.AreaReference;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianInput;


/**
 * Specifies a rectangular area of cells A1:A4 for instance.
 * @author Jason Height (jheight at chariot dot net dot au)
 */
public final class AreaPtg extends Area2DPtgBase {
	public final static short sid  = 0x25;

	public AreaPtg(int firstRow, int lastRow, int firstColumn, int lastColumn, boolean firstRowRelative, boolean lastRowRelative, boolean firstColRelative, boolean lastColRelative) {
		super(firstRow, lastRow, firstColumn, lastColumn, firstRowRelative, lastRowRelative, firstColRelative, lastColRelative);
	}
	public AreaPtg(LittleEndianInput in)  {
		super(in);
	}
	public AreaPtg(String arearef) {
		super(new AreaReference(arearef));
	}
	public AreaPtg(AreaReference areaRef) {
		super(areaRef);
	}
	protected byte getSid() {
		return sid;
	}
}
