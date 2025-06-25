

package com.document.allreader.allofficefilereader.fc.ss.usermodel;

import java.util.List;

import com.document.allreader.allofficefilereader.fc.ss.usermodel.charts.ChartAxis;
import com.document.allreader.allofficefilereader.fc.ss.usermodel.charts.ChartAxisFactory;
import com.document.allreader.allofficefilereader.fc.ss.usermodel.charts.ChartData;
import com.document.allreader.allofficefilereader.fc.ss.usermodel.charts.ChartDataFactory;
import com.document.allreader.allofficefilereader.fc.ss.usermodel.charts.ChartLegend;
import com.document.allreader.allofficefilereader.fc.ss.usermodel.charts.ManuallyPositionable;


/**
 * High level representation of a chart.
 *
 * @author Roman Kashitsyn
 */
public interface Chart extends ManuallyPositionable {
	
	/**
	 * @return an appropriate ChartDataFactory implementation
	 */
	ChartDataFactory getChartDataFactory();

	/**
	 * @return an appropriate ChartAxisFactory implementation
	 */
	ChartAxisFactory getChartAxisFactory();

	/**
	 * @return chart legend instance
	 */
	ChartLegend getOrCreateLegend();

	/**
	 * Delete current chart legend.
	 */
	void deleteLegend();

	/**
	 * @return list of all chart axis
	 */
	List<? extends ChartAxis> getAxis();

	/**
	 * Plots specified data on the chart.
	 *
	 * @param data a data to plot
	 */
	void plot(ChartData data, ChartAxis... axis);
}
