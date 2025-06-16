

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;


import com.document.allreader.allofficefilereader.fc.hssf.formula.FormulaRenderingWorkbook;
import com.document.allreader.allofficefilereader.fc.hssf.formula.WorkbookDependentFormula;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianInput;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * 
 * @author andy
 * @author Jason Height (jheight at chariot dot net dot au)
 */
public final class NamePtg extends OperandPtg implements WorkbookDependentFormula {
	public final static short sid = 0x23;
	private final static int SIZE = 5;
	/** one-based index to defined name record */
	private int field_1_label_index;
	private short field_2_zero; // reserved must be 0

	/**
	 * @param nameIndex zero-based index to name within workbook
	 */
	public NamePtg(int nameIndex) {
		field_1_label_index = 1 + nameIndex; // convert to 1-based
	}

	/** Creates new NamePtg */

	public NamePtg(LittleEndianInput in)  {
		field_1_label_index = in.readShort();
		field_2_zero = in.readShort();
	}

	/**
	 * @return zero based index to a defined name record in the LinkTable
	 */
	public int getIndex() {
		return field_1_label_index - 1; // convert to zero based
	}

	public void write(LittleEndianOutput out) {
		out.writeByte(sid + getPtgClass());
		out.writeShort(field_1_label_index);
		out.writeShort(field_2_zero);
	}

	public int getSize() {
		return SIZE;
	}

	public String toFormulaString(FormulaRenderingWorkbook book) {
		return book.getNameText(this);
	}

	public String toFormulaString() {
		throw new RuntimeException("3D references need a workbook to determine formula text");
	}

	public byte getDefaultOperandClass() {
		return Ptg.CLASS_REF;
	}
}
