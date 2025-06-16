

package com.document.allreader.allofficefilereader.fc.hssf.formula.ptg;


import com.document.allreader.allofficefilereader.fc.ss.util.NumberToTextConverter;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianInput;
import com.document.allreader.allofficefilereader.fc.util.LittleEndianOutput;


/**
 * Number Stores a floating point value in a formula value stored in a 8 byte
 * field using IEEE notation
 * 
 * @author Avik Sengupta
 * @author Jason Height (jheight at chariot dot net dot au)
 */
public final class NumberPtg extends ScalarConstantPtg {
	public final static int SIZE = 9;
	public final static byte sid = 0x1f;
	private final double field_1_value;

	public NumberPtg(LittleEndianInput in)  {
		this(in.readDouble());
	}

	/**
	 * Create a NumberPtg from a string representation of the number Number
	 * format is not checked, it is expected to be validated in the parser that
	 * calls this method.
	 * 
	 * @param value String representation of a floating point number
	 */
	public NumberPtg(String value) {
		this(Double.parseDouble(value));
	}

	public NumberPtg(double value) {
		field_1_value = value;
	}

	public double getValue() {
		return field_1_value;
	}

	public void write(LittleEndianOutput out) {
		out.writeByte(sid + getPtgClass());
		out.writeDouble(getValue());
	}

	public int getSize() {
		return SIZE;
	}

	public String toFormulaString() {
		return NumberToTextConverter.toText(field_1_value);
	}
}
