

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;

/**
 * @author Josh Micich
 */
public abstract class OperandPtg extends Ptg implements Cloneable {

	/**
	 * All Operand {@link Ptg}s are classified ('relative', 'value', 'array')
	 */
	public final boolean isBaseToken() {
		return false;
	}
	public final OperandPtg copy() {
		try {
			return (OperandPtg) clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
