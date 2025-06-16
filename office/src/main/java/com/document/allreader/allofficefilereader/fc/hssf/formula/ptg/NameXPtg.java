

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;


import com.document.allreader.allofficefilereader.fc.hssf.formula.FormulaRenderingWorkbook;
import com.document.allreader.allofficefilereader.fc.hssf.formula.WorkbookDependentFormula;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianInput;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * 
 * @author aviks
 */
public final class NameXPtg extends OperandPtg implements WorkbookDependentFormula {
	public final static short sid = 0x39;
	private final static int SIZE = 7;

	/** index to REF entry in externsheet record */
	private final int _sheetRefIndex;
	/** index to defined name or externname table(1 based) */
	private final int _nameNumber;
	/** reserved must be 0 */
	private final int _reserved;

	private NameXPtg(int sheetRefIndex, int nameNumber, int reserved) {
		_sheetRefIndex = sheetRefIndex;
		_nameNumber = nameNumber;
		_reserved = reserved;
	}

	/**
	 * @param sheetRefIndex index to REF entry in externsheet record
	 * @param nameIndex index to defined name or externname table
	 */
	public NameXPtg(int sheetRefIndex, int nameIndex) {
		this(sheetRefIndex, nameIndex + 1, 0);
	}

	public NameXPtg(LittleEndianInput in)  {
		this(in.readUShort(), in.readUShort(), in.readUShort());
	}

	public void write(LittleEndianOutput out) {
		out.writeByte(sid + getPtgClass());
		out.writeShort(_sheetRefIndex);
		out.writeShort(_nameNumber);
		out.writeShort(_reserved);
	}

	public int getSize() {
		return SIZE;
	}

	public String toFormulaString(FormulaRenderingWorkbook book) {
		// -1 to convert definedNameIndex from 1-based to zero-based
		return book.resolveNameXText(this);
	}
	public String toFormulaString() {
		throw new RuntimeException("3D references need a workbook to determine formula text");
	}
	
	public String toString(){
	   String retValue = "NameXPtg:[sheetRefIndex:" + _sheetRefIndex + 
	      " , nameNumber:" + _nameNumber + "]" ;
	   return retValue;
	}
	
	public byte getDefaultOperandClass() {
		return Ptg.CLASS_VALUE;
	}

	public int getSheetRefIndex() {
		return _sheetRefIndex;
	}
	public int getNameIndex() {
		return _nameNumber - 1;
	}
}
