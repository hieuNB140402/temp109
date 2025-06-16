

package com.document.allreader.allofficefilereader.fc.hssf.formula.function;

import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ErrorEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;

/**
 * Convenience base class for functions that must take exactly two arguments.
 *
 * @author Josh Micich
 */
public abstract class Fixed2ArgFunction implements Function2Arg {
	public final ValueEval evaluate(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
		if (args.length != 2) {
			return ErrorEval.VALUE_INVALID;
		}
		return evaluate(srcRowIndex, srcColumnIndex, args[0], args[1]);
	}
}
