

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;

import com.document.allreader.allofficefilereader.fc.util.LittleEndianInput;

/**
 * Specifies a rectangular area of cells A1:A4 for instance.
 * @author Jason Height (jheight at chariot dot net dot au)
 */
public final class AreaNPtg extends Area2DPtgBase {
	public final static short sid = 0x2D;

	public AreaNPtg(LittleEndianInput in)  {
		super(in);
	}

	protected byte getSid() {
		return sid;
	}
}
