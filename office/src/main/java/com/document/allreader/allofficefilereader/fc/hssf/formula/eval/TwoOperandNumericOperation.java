

package com.document.allreader.allofficefilereader.fc.hssf.formula.eval;

import com.document.allreader.allofficefilereader.fc.hssf.formula.function.Fixed2ArgFunction;
import com.document.allreader.allofficefilereader.fc.hssf.formula.function.Function;

/**
 * @author Josh Micich
 */
public abstract class TwoOperandNumericOperation extends Fixed2ArgFunction {

	protected final double singleOperandEvaluate(ValueEval arg, int srcCellRow, int srcCellCol) throws EvaluationException {
		ValueEval ve = OperandResolver.getSingleValue(arg, srcCellRow, srcCellCol);
		return OperandResolver.coerceValueToDouble(ve);
	}
	public ValueEval evaluate(int srcRowIndex, int srcColumnIndex, ValueEval arg0, ValueEval arg1) {
		double result;
		try {
			double d0 = singleOperandEvaluate(arg0, srcRowIndex, srcColumnIndex);
			double d1 = singleOperandEvaluate(arg1, srcRowIndex, srcColumnIndex);
			result = evaluate(d0, d1);
			if (result == 0.0) { // this '==' matches +0.0 and -0.0
				// Excel converts -0.0 to +0.0 for '*', '/', '%', '+' and '^'
				if (!(this instanceof SubtractEvalClass)) {
					return NumberEval.ZERO;
				}
			}
			if (Double.isNaN(result) || Double.isInfinite(result)) {
				return ErrorEval.NUM_ERROR;
			}
		} catch (EvaluationException e) {
			return e.getErrorEval();
		}
		return new NumberEval(result);
	}

	protected abstract double evaluate(double d0, double d1) throws EvaluationException;

	public static final Function AddEval = new TwoOperandNumericOperation() {
		protected double evaluate(double d0, double d1) {
			return d0+d1;
		}
	};
	public static final Function DivideEval = new TwoOperandNumericOperation() {
		protected double evaluate(double d0, double d1) throws EvaluationException {
			if (d1 == 0.0) {
				throw new EvaluationException(ErrorEval.DIV_ZERO);
			}
			return d0/d1;
		}
	};
	public static final Function MultiplyEval = new TwoOperandNumericOperation() {
		protected double evaluate(double d0, double d1) {
			return d0*d1;
		}
	};
	public static final Function PowerEval = new TwoOperandNumericOperation() {
		protected double evaluate(double d0, double d1) {
			return Math.pow(d0, d1);
		}
	};
	private static final class SubtractEvalClass extends TwoOperandNumericOperation {
		public SubtractEvalClass() {
			//
		}
		protected double evaluate(double d0, double d1) {
			return d0-d1;
		}
	}
	public static final Function SubtractEval = new SubtractEvalClass();
}
