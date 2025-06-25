

package com.document.allreader.allofficefilereader.fc.hssf.formula.eval;

import com.document.allreader.allofficefilereader.fc.ss.usermodel.FormulaEvaluator;

/**
 * An exception thrown by implementors of {@link FormulaEvaluator} when attempting to evaluate
 * a formula which requires features that POI does not (yet) support.
 *
 * @author Josh Micich
 */
public final class NotImplementedException extends RuntimeException {

	public NotImplementedException(String message) {
		super(message);
	}
	public NotImplementedException(String message, NotImplementedException cause) {
		super(message, cause);
	}
}
