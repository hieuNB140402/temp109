

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;

import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * @author Glen Stampoultzis (glens at apache.org)
 */
public final class UnionPtg extends OperationPtg {
    public final static byte sid  = 0x10;

    public static final OperationPtg instance = new UnionPtg();

    private UnionPtg() {
    	// enforce singleton
    }

    public final boolean isBaseToken() {
        return true;
    }

    public int getSize()
    {
        return 1;
    }

    public void write(LittleEndianOutput out) {
        out.writeByte(sid + getPtgClass());
    }

    public String toFormulaString()
    {
        return ",";
    }


    /** implementation of method from OperationsPtg*/
    public String toFormulaString(String[] operands)
    {
         StringBuffer buffer = new StringBuffer();

         buffer.append(operands[ 0 ]);
         buffer.append(",");
         buffer.append(operands[ 1 ]);
         return buffer.toString();
     }

    public int getNumberOfOperands()
    {
        return 2;
    }

}
