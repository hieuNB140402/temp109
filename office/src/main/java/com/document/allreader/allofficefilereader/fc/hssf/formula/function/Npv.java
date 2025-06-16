

package com.document.allreader.allofficefilereader.fc.hssf.formula.function;



import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ErrorEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.EvaluationException;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.NumberEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;

/**
 * Calculates the net present value of an investment by using a discount rate
 * and a series of future payments (negative values) and income (positive
 * values). Minimum 2 arguments, first arg is the rate of discount over the
 * length of one period others up to 254 arguments representing the payments and
 * income.
 *
 * @author SPetrakovsky
 * @author Marcel May
 */
public final class Npv implements Function {

	public ValueEval evaluate(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
		int nArgs = args.length;
		if (nArgs < 2) {
			return ErrorEval.VALUE_INVALID;
		}

        try {
			double rate = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            // convert tail arguments into an array of doubles
            ValueEval[] vargs = new ValueEval[args.length-1];
            System.arraycopy(args, 1, vargs, 0, vargs.length);
            double[] values = AggregateFunction.ValueCollector.collectValues(vargs);

            double result = FinanceLib.npv(rate, values);
			NumericFunction.checkValue(result);
            return new NumberEval(result);
		} catch (EvaluationException e) {
			return e.getErrorEval();
		}
	}
}
