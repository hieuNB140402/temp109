

package com.document.allreader.allofficefilereader.fc.hssf.formula.eval;

/**
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt; This class is a
 *         marker class. It is a special value for empty cells.
 */
public final class BlankEval implements ValueEval {

	public static final BlankEval instance = new BlankEval();
	/**
	 * @deprecated (Nov 2009) use {@link #instance}
	 */
	public static final BlankEval INSTANCE = instance;

	private BlankEval() {
		// enforce singleton
	}
}
