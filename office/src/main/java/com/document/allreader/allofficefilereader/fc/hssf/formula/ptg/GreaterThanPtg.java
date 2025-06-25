

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;


/**
 * Greater than operator PTG ">"
 * @author  Cameron Riley (criley at ekmail.com)
 */
public final class GreaterThanPtg extends ValueOperatorPtg {
    public final static byte sid  = 0x0D;    
    private final static String GREATERTHAN = ">";

    public static final ValueOperatorPtg instance = new GreaterThanPtg();

    private GreaterThanPtg() {
    	// enforce singleton
    }
    
    protected byte getSid() {
    	return sid;
    }

    /**
     * Get the number of operands for the Less than operator
     * @return int the number of operands
     */
    public int getNumberOfOperands() {
        return 2;
    }
    
    /** 
     * Implementation of method from OperationsPtg
     * @param operands a String array of operands
     * @return String the Formula as a String
     */  
    public String toFormulaString(String[] operands) 
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append(operands[ 0 ]);
        buffer.append(GREATERTHAN);
        buffer.append(operands[ 1 ]);
        return buffer.toString();
    }
}
