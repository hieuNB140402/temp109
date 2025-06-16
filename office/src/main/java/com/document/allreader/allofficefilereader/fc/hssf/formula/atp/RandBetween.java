
package com.document.allreader.allofficefilereader.fc.hssf.formula.atp;


import com.document.allreader.allofficefilereader.fc.hssf.formula.OperationEvaluationContext;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ErrorEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.EvaluationException;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.NumberEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.OperandResolver;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.function.FreeRefFunction;


/**
 * Implementation of Excel 'Analysis ToolPak' function RANDBETWEEN()<br/>
 *
 * Returns a random integer number between the numbers you specify.<p/>
 *
 * <b>Syntax</b><br/>
 * <b>RANDBETWEEN</b>(<b>bottom</b>, <b>top</b>)<p/>
 *
 * <b>bottom</b> is the smallest integer RANDBETWEEN will return.<br/>
 * <b>top</b> is the largest integer RANDBETWEEN will return.<br/>

 * @author Brendan Nolan
 */
final class RandBetween implements FreeRefFunction{

	public static final FreeRefFunction instance = new RandBetween();

	private RandBetween() {
		//enforces singleton
	}

	/**
	 * Evaluate for RANDBETWEEN(). Must be given two arguments. Bottom must be greater than top.
	 * Bottom is rounded up and top value is rounded down. After rounding top has to be set greater
	 * than top.
	 * 
	 * @see com.document.allreader.allofficefilereader.fc.hssf.formula.function.FreeRefFunction#evaluate(com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval[], com.document.allreader.allofficefilereader.fc.hssf.formula.OperationEvaluationContext)
	 */
	public ValueEval evaluate(ValueEval[] args, OperationEvaluationContext ec) {
		
		double bottom, top;

		if (args.length != 2) {
			return ErrorEval.VALUE_INVALID;
		}
		
		try {
			bottom = OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(args[0], ec.getRowIndex(), ec.getColumnIndex()));
			top = OperandResolver.coerceValueToDouble(OperandResolver.getSingleValue(args[1], ec.getRowIndex(), ec.getColumnIndex()));
			if(bottom > top) {
				return ErrorEval.NUM_ERROR;
			}
		} catch (EvaluationException e) {
			return ErrorEval.VALUE_INVALID;
		}

		bottom = Math.ceil(bottom);
		top = Math.floor(top);

		if(bottom > top) {
			top = bottom;
		}
		
		return new NumberEval((bottom + (int)(Math.random() * ((top - bottom) + 1))));
		
	}
		
}
