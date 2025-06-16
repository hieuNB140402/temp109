

package com.document.allreader.allofficefilereader.fc.hssf.formula.function;

/**
 * Implementation of Excel function SUMXMY2()<p/>
 *
 * Calculates the sum of squares of differences between two arrays of the same size.<br/>
 * <b>Syntax</b>:<br/>
 * <b>SUMXMY2</b>(<b>arrayX</b>, <b>arrayY</b>)<p/>
 *
 * result = &Sigma;<sub>i: 0..n</sub>(x<sub>i</sub>-y<sub>i</sub>)<sup>2</sup>
 *
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt;
 */
public final class Sumxmy2 extends XYNumericFunction {

	private static final Accumulator XMinusYSquaredAccumulator = new Accumulator() {
		public double accumulate(double x, double y) {
			double xmy = x - y;
			return xmy * xmy;
		}
	};

	protected Accumulator createAccumulator() {
		return XMinusYSquaredAccumulator;
	}
}
