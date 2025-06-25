

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;

import com.document.allreader.allofficefilereader.fc.util.LittleEndianInput;

/**
 * RefNPtg
 * @author Jason Height (jheight at apache dot com)
 */
public final class RefNPtg extends Ref2DPtgBase {
	public final static byte sid = 0x2C;

	public RefNPtg(LittleEndianInput in)  {
		super(in);
	}

	protected byte getSid() {
		return sid;
	}
}
