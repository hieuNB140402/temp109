

package com.document.allreader.allofficefilereader.fc.hssf.formula.function;

import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;

/**
 * Implementation of Excel HYPERLINK function.<p/>
 *
 * In Excel this function has special behaviour - it causes the displayed cell value to behave like
 * a hyperlink in the GUI. From an evaluation perspective however, it is very simple.<p/>
 *
 * <b>Syntax</b>:<br/>
 * <b>HYPERLINK</b>(<b>link_location</b>, friendly_name)<p/>
 *
 * <b>link_location</b> The URL of the hyperlink <br/>
 * <b>friendly_name</b> (optional) the value to display<p/>
 *
 *  Returns last argument.  Leaves type unchanged (does not convert to {@link com.document.allreader.allofficefilereader.fc.hssf.formula.eval.StringEval}).
 *
 * @author Wayne Clingingsmith
 */
public final class Hyperlink extends Var1or2ArgFunction {

	public ValueEval evaluate(int srcRowIndex, int srcColumnIndex, ValueEval arg0) {
		return arg0;
	}
	public ValueEval evaluate(int srcRowIndex, int srcColumnIndex, ValueEval arg0, ValueEval arg1) {
		// note - if last arg is MissingArgEval, result will be NumberEval.ZERO,
		// but WorkbookEvaluator does that translation
		return arg1;
	}
}
