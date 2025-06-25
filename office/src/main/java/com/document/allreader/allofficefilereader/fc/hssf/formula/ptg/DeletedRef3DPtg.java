

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;



import com.document.allreader.allofficefilereader.fc.hssf.formula.FormulaRenderingWorkbook;
import com.document.allreader.allofficefilereader.fc.hssf.formula.WorkbookDependentFormula;
import com.document.allreader.allofficefilereader.fc.ss.usermodel.ErrorConstants;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianInput;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * Title:        Deleted Reference 3D Ptg <P>
 * Description:  Defined a cell in extern sheet. <P>
 * REFERENCE:  <P>
 * @author Patrick Luby
 * @version 1.0-pre
 */
public final class DeletedRef3DPtg extends OperandPtg implements WorkbookDependentFormula {
	public final static byte sid  = 0x3c;
	private final int field_1_index_extern_sheet;
	private final int unused1;

	/** Creates new DeletedRef3DPtg */
	public DeletedRef3DPtg(LittleEndianInput in)  {
		field_1_index_extern_sheet = in.readUShort();
		unused1 = in.readInt();
	}

	public DeletedRef3DPtg(int externSheetIndex) {
		field_1_index_extern_sheet = externSheetIndex;
		unused1 = 0;
	}

	public String toFormulaString(FormulaRenderingWorkbook book) {
		return ExternSheetNameResolver.prependSheetName(book, field_1_index_extern_sheet, 
				ErrorConstants.getText(ErrorConstants.ERROR_REF));
	}
	public String toFormulaString() {
		throw new RuntimeException("3D references need a workbook to determine formula text");
	}
	public byte getDefaultOperandClass() {
		return Ptg.CLASS_REF;
	}
	public int getSize() {
		return 7;
	}
	public void write(LittleEndianOutput out) {
		out.writeByte(sid + getPtgClass());
		out.writeShort(field_1_index_extern_sheet);
		out.writeInt(unused1);
	}
}
