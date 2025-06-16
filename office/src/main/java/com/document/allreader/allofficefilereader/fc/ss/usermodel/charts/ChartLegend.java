

package com.document.allreader.allofficefilereader.fc.ss.usermodel.charts;

/**
 * High level representation of chart legend.
 *
 * @author Roman Kashitsyn
 */
public interface ChartLegend extends ManuallyPositionable {
	
	/**
	 * @return legend position
	 */
	LegendPosition getPosition();

	/**
	 * @param position new legend position
	 */
	void setPosition(LegendPosition position);
}
