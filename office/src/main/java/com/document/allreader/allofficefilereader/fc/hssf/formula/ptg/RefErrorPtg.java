

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;


import com.document.allreader.allofficefilereader.fc.ss.usermodel.ErrorConstants;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianInput;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * RefError - handles deleted cell reference
 * @author Jason Height (jheight at chariot dot net dot au)
 */
public final class RefErrorPtg extends OperandPtg {

    private final static int SIZE = 5;
    public final static byte sid  = 0x2A;
    private int              field_1_reserved;

    public RefErrorPtg() {
        field_1_reserved = 0;
    }
    public RefErrorPtg(LittleEndianInput in)  {
        field_1_reserved = in.readInt();
    }

    public String toString() {
        return getClass().getName();
    }

    public void write(LittleEndianOutput out) {
        out.writeByte(sid + getPtgClass());
        out.writeInt(field_1_reserved);
    }

    public int getSize()
    {
        return SIZE;
    }

    public String toFormulaString() {
        return ErrorConstants.getText(ErrorConstants.ERROR_REF);
    }
    
    public byte getDefaultOperandClass() {
        return Ptg.CLASS_REF;
    }
}
