

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;


import com.document.allreader.allofficefilereader.fc.hssf.formula.ExternSheetReferenceToken;
import com.document.allreader.allofficefilereader.fc.hssf.formula.FormulaRenderingWorkbook;
import com.document.allreader.allofficefilereader.fc.hssf.formula.WorkbookDependentFormula;
import com.document.allreader.allofficefilereader.fc.ss.util.CellReference;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianInput;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * Title:        Reference 3D Ptg <P>
 * Description:  Defined a cell in extern sheet. <P>
 * REFERENCE:  <P>
 * @author Libin Roman (Vista Portal LDT. Developer)
 * @author Jason Height (jheight at chariot dot net dot au)
 */
public final class Ref3DPtg extends RefPtgBase implements WorkbookDependentFormula, ExternSheetReferenceToken {
    public final static byte sid  = 0x3a;

    private final static int  SIZE = 7; // 6 + 1 for Ptg
    private int             field_1_index_extern_sheet;


    public Ref3DPtg(LittleEndianInput in)  {
        field_1_index_extern_sheet = in.readShort();
        readCoordinates(in);
    }

    public Ref3DPtg(String cellref, int externIdx ) {
        this(new CellReference(cellref), externIdx);
    }

    public Ref3DPtg(CellReference c, int externIdx) {
        super(c);
        setExternSheetIndex(externIdx);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getClass().getName());
        sb.append(" [");
        sb.append("sheetIx=").append(getExternSheetIndex());
        sb.append(" ! ");
        sb.append(formatReferenceAsString());
        sb.append("]");
        return sb.toString();
    }

    public void write(LittleEndianOutput out) {
        out.writeByte(sid + getPtgClass());
        out.writeShort(getExternSheetIndex());
        writeCoordinates(out);
    }

    public int getSize() {
        return SIZE;
    }

    public int getExternSheetIndex() {
        return field_1_index_extern_sheet;
    }

    public void setExternSheetIndex(int index) {
        field_1_index_extern_sheet = index;
    }
    public String format2DRefAsString() {
        return formatReferenceAsString();
    }
    /**
     * @return text representation of this cell reference that can be used in text
     * formulas. The sheet name will get properly delimited if required.
     */
    public String toFormulaString(FormulaRenderingWorkbook book) {
        return ExternSheetNameResolver.prependSheetName(book, field_1_index_extern_sheet, formatReferenceAsString());
    }
    public String toFormulaString() {
        throw new RuntimeException("3D references need a workbook to determine formula text");
    }
}
