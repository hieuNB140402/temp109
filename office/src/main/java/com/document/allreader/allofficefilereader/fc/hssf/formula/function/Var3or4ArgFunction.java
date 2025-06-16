

package com.document.allreader.allofficefilereader.fc.hssf.formula.function;

import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ErrorEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;

/**
 * Convenience base class for any function which must take three or four
 * arguments
 *
 * @author Josh Micich
 */
abstract class Var3or4ArgFunction implements Function3Arg, Function4Arg {

	public final ValueEval evaluate(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
		switch (args.length) {
			case 3:
				return evaluate(srcRowIndex, srcColumnIndex, args[0], args[1], args[2]);
			case 4:
				return evaluate(srcRowIndex, srcColumnIndex, args[0], args[1], args[2], args[3]);
		}
		return ErrorEval.VALUE_INVALID;
	}
}
