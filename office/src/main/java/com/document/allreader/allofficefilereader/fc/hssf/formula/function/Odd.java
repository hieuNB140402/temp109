

package com.document.allreader.allofficefilereader.fc.hssf.formula.function;

/**
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt;
 *
 */
public final class Odd extends NumericFunction.OneArg {
	private static final long PARITY_MASK = 0xFFFFFFFFFFFFFFFEL;

	protected double evaluate(double d) {
		if (d==0) {
			return 1;
		}
		if (d>0) {
			return calcOdd(d);
		}
		return -calcOdd(-d);
	}

	private static long calcOdd(double d) {
		double dpm1 = d+1;
		long x = ((long) dpm1) & PARITY_MASK;
		if (x == dpm1) {
			return x-1;
		}
		return x + 1;
	}
}
