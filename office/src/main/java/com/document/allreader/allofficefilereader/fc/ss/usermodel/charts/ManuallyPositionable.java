

package com.document.allreader.allofficefilereader.fc.ss.usermodel.charts;

/**
 * Abstraction of chart element that can be positioned with manual
 * layout.
 *
 * @author Roman Kashitsyn
 */
public interface ManuallyPositionable {

	/**
	 * Returns manual layout for the chart element.
	 * @return manual layout for the chart element.
	 */
	public ManualLayout getManualLayout();
}
