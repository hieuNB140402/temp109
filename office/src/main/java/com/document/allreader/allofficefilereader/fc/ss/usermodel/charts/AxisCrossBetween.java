

package com.document.allreader.allofficefilereader.fc.ss.usermodel.charts;

/**
 *  Specifies the possible crossing states of an axis.
 *
 * @author Roman Kashitsyn
 */
public enum AxisCrossBetween {
	/**
	 * Specifies the value axis shall cross the category axis
	 * between data markers.
	 */
	BETWEEN,
	/**
	 * Specifies the value axis shall cross the category axis at
	 * the midpoint of a category.
	 */
	MIDPOINT_CATEGORY
}
