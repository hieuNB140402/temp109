

package com.document.allreader.allofficefilereader.fc.hssf.formula.eval;

/**
 * @author Josh Micich
 */
public final class NameEval implements ValueEval {

	private final String _functionName;

	/**
	 * Creates a NameEval representing a function name
	 */
	public NameEval(String functionName) {
		_functionName = functionName;
	}


	public String getFunctionName() {
		return _functionName;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(64);
		sb.append(getClass().getName()).append(" [");
		sb.append(_functionName);
		sb.append("]");
		return sb.toString();
	}
}
