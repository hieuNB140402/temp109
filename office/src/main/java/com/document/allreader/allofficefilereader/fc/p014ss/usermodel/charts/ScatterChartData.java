package com.document.allreader.allofficefilereader.fc.p014ss.usermodel.charts;

import com.document.allreader.allofficefilereader.fc.p014ss.util.DataMarker;
import java.util.List;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.charts.ScatterChartData */

public interface ScatterChartData extends ChartData {
    ScatterChartSerie addSerie(DataMarker dataMarker, DataMarker dataMarker2);

    List<? extends ScatterChartSerie> getSeries();
}
