

package com.document.allreader.allofficefilereader.fc.hssf.formula.function;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.NumberEval;
import com.document.allreader.allofficefilereader.fc.hssf.formula.eval.ValueEval;
import com.document.allreader.allofficefilereader.ss.util.DateUtil;



/**
 * Implementation of Excel TODAY() Function<br/>
 *
 * @author Frank Taffelt
 */
public final class Today extends Fixed0ArgFunction {

	public ValueEval evaluate(int srcRowIndex, int srcColumnIndex) {

		Calendar now = new GregorianCalendar();
		now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE),0,0,0);
		now.set(Calendar.MILLISECOND, 0);
		return new NumberEval(DateUtil.getExcelDate(now.getTime()));
	}
}
