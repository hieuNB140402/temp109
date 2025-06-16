

package com.document.allreader.allofficefilereader.fc.ss.usermodel.charts;

/**
 * @author Roman Kashitsyn
 */
public interface ValueAxis extends ChartAxis {

	/**
	 * @return cross between type
	 */
	AxisCrossBetween getCrossBetween();

	/**
	 * @param crossBetween cross between type
	 */
	void setCrossBetween(AxisCrossBetween crossBetween);
}
