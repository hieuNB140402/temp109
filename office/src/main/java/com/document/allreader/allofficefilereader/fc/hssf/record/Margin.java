

package com.document.allreader.allofficefilereader.fc.hssf.record;

/**
 * The margin interface is a parent used to define left, right, top and bottom margins.
 * This allows much of the code to be generic when it comes to handling margins.
 *
 * @author Shawn Laubach (slaubach at apache dot org)
 */
public interface Margin {
	// TODO - introduce MarginBaseRecord
	/**
	 * Get the margin field for the Margin.
	 */
	public double getMargin();

	/**
	 * Set the margin field for the Margin.
	 */
	public void setMargin(double field_1_margin);
}
