

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;

/**
 * defines a Ptg that is an operation instead of an operand
 * @author  andy
 */
public abstract class OperationPtg extends Ptg {
    public final static int TYPE_UNARY    = 0;
    public final static int TYPE_BINARY   = 1;
    public final static int TYPE_FUNCTION = 2;

    /**
     *  returns a string representation of the operations
     *  the length of the input array should equal the number returned by 
     *  @see #getNumberOfOperands
     *  
     */
    public abstract String toFormulaString(String[] operands);
    
    /**
     * The number of operands expected by the operations
     */
    public abstract int getNumberOfOperands();
    
    public byte getDefaultOperandClass() {
        return Ptg.CLASS_VALUE;
    }
}
