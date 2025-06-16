

package com.document.allreader.allofficefilereader.fc.hssf.formula.function;

import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ErrorEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.NotImplementedException;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;
import com.document.allreader.allofficefilereader.fc.ss.usermodel.ErrorConstants;

/**
 *
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt;
 * This is the default implementation of a Function class.
 * The default behaviour is to raise a POI internal error
 * ({@link NotImplementedException}). This error should alert
 * the user that the formula contained a function that is not
 * yet implemented.
 */
public final class NotImplementedFunction implements Function {
	private final String _functionName;
	protected NotImplementedFunction() {
		_functionName = getClass().getName();
	}
	public NotImplementedFunction(String name) {
		_functionName = name;
	}

	public ValueEval evaluate(ValueEval[] operands, int srcRow, int srcCol) {
	    
	    return ErrorEval.valueOf(ErrorConstants.ERROR_NAME);
		//throw new NotImplementedException(_functionName);
	}
	public String getFunctionName() {
		return _functionName;
	}
}
