

package com.document.allreader.allofficefilereader.fc.ss.usermodel.charts;

/**
 * Specifies the possible crossing points for an axis.
 *
 * @author Roman Kashitsyn
 */
public enum AxisCrosses {
	/**
	 * The category axis crosses at the zero point of the value axis (if
	 * possible), or the minimum value (if the minimum is greater than zero) or
	 * the maximum (if the maximum is less than zero).
	 */
	AUTO_ZERO,
	/**
	 * The axis crosses at the maximum value.
	 */
	MIN,
	/**
	 * Axis crosses at the minimum value of the chart.
	 */
	MAX
}
