

package com.document.allreader.allofficefilereader.fc.ss.usermodel.charts;

/**
 * Specifies whether to layout the plot area by its inside (not including axis
 * and axis labels) or outside (including axis and axis labels).
 *
 * @author Roman Kashitsyn
 */
public enum LayoutTarget {
	/**
	 * Specifies that the plot area size shall determine the
	 * size of the plot area, not including the tick marks and
	 * axis labels.
	 */
	INNER,
	/**
	 * Specifies that the plot area size shall determine the
	 * size of the plot area, the tick marks, and the axis
	 * labels.
	 */
	OUTER
}
