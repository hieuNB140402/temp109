

package com.document.allreader.allofficefilereader.fc.hssf.formula.function;

/**
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt;
 *
 */
public abstract class MinaMaxa extends MultiOperandNumericFunction {

	protected MinaMaxa() {
		super(true, true);
	}

	public static final Function MAXA = new MinaMaxa() {
		protected double evaluate(double[] values) {
			return values.length > 0 ? MathX.max(values) : 0;
		}
	};
	public static final Function MINA = new MinaMaxa() {
		protected double evaluate(double[] values) {
			return values.length > 0 ? MathX.min(values) : 0;
		}
	};
}
