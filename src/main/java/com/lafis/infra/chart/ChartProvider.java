package com.lafis.infra.chart;

import com.lafis.core.entity.day.DailySimulationData;
import com.lafis.core.ports.ChartPlotter;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.internal.series.MarkerSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ChartProvider implements ChartPlotter {


    @Override
    public void plotChart(List<DailySimulationData> dataList) {

        List<Date> dates = dataList.stream().
                map(DailySimulationData::date).
                map(localDate -> Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).
                collect(Collectors.toList());

        List<Double> hostPopulation = dataList.stream().
                map(DailySimulationData::hosts).
                map(Integer::doubleValue).
                toList();

        List<Double> infectedPopulation = dataList.stream().
                map(DailySimulationData::infected).
                map(Integer::doubleValue).
                toList();

        List<Double> parasitoidPopulation = dataList.stream().
                map(DailySimulationData::parasitoid).
                map(Integer::doubleValue).
                toList();


        XYChart chart = new XYChartBuilder()
                .width(1920)
                .height(1080)
                .title("Dinâmica Populacional ao longo do ano")
                .xAxisTitle("Dia")
                .yAxisTitle("Population")
                .build();


        chart.getStyler().setDatePattern("dd-MM-yyyy");
        chart.getStyler().setXAxisLabelRotation(45);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setAxisTitlesVisible(true);
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setPlotGridLinesVisible(true);


        chart.addSeries("Broca", dates, hostPopulation).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Broca infectada", dates, infectedPopulation).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Parasitoid", dates, parasitoidPopulation).setMarker(SeriesMarkers.NONE);

        String filePath = "./output/dinamica_populacional.png";
        try {
            BitmapEncoder.saveBitmap(chart, filePath, BitmapEncoder.BitmapFormat.PNG);
            System.out.println("Gráfico salvo em: " + filePath);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o gráfico: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public ChartProvider() {
    }
}
