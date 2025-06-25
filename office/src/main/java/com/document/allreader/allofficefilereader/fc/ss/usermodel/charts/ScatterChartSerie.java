

package com.document.allreader.allofficefilereader.fc.ss.usermodel.charts;


import com.document.allreader.allofficefilereader.fc.ss.util.DataMarker;


/**
 * @author Roman Kashitsyn
 */
public interface ScatterChartSerie {

	/**
	 * @param xMarker data marker to use for X values.
	 */
	void setXValues(DataMarker xMarker);

	/**'
	 * @param yMarker data marker to use for Y values.
	 */
	void setYValues(DataMarker yMarker);

}
