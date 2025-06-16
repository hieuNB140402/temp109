

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;

/**
 * Unary Plus operator
 * does not have any effect on the operand
 * @author Avik Sengupta
 */
public final class UnaryPlusPtg extends ValueOperatorPtg {
    public final static byte sid  = 0x12;
    
    private final static String ADD = "+";

    public static final ValueOperatorPtg instance = new UnaryPlusPtg();

    private UnaryPlusPtg() {
    	// enforce singleton
    }
    
    protected byte getSid() {
    	return sid;
    }

    public int getNumberOfOperands() {
        return 1;
    }
    
   /** implementation of method from OperationsPtg*/  
    public String toFormulaString(String[] operands) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(ADD);
        buffer.append(operands[ 0]);
        return buffer.toString();
    }
}
