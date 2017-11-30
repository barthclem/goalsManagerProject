package com.example.barthclem.goalsmanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

/**
 * Created by barthclem on 5/19/16.
 */

public class LineGraph {
        private double [] array;

        public Intent getIntent(Context context,double[] array){
            this.array=array;

            TimeSeries timeSeries=new TimeSeries("Line 1");

            for(int i=0;i<array.length;i++){

                timeSeries.add((i+1),(array[i]));
                Log.e("Graph Content",""+array[i]);
            }

            XYMultipleSeriesDataset dataset=new XYMultipleSeriesDataset();
            dataset.addSeries(timeSeries);


            XYSeriesRenderer renderer=new XYSeriesRenderer();
            renderer.setColor(Color.RED);
            renderer.setChartValuesTextSize(39);
            renderer.setPointStyle(PointStyle.DIAMOND);
            renderer.setFillPoints(true);
            renderer.setLineWidth(3);


            XYMultipleSeriesRenderer multipleSeriesRenderer=new XYMultipleSeriesRenderer();

            multipleSeriesRenderer.addSeriesRenderer(renderer);
            multipleSeriesRenderer.setAxisTitleTextSize(39);
            multipleSeriesRenderer.setApplyBackgroundColor(true);
            multipleSeriesRenderer.setPanEnabled(false,false);
            multipleSeriesRenderer.setXAxisMax(10);
            multipleSeriesRenderer.setMargins(new int[] { 120, 70, 70,30});
            multipleSeriesRenderer.setShowAxes(true);
            multipleSeriesRenderer.setLabelsTextSize(24);
            multipleSeriesRenderer.setXAxisMin(0);
            multipleSeriesRenderer.setYAxisMin(0);
            multipleSeriesRenderer.setYAxisMax(100);
            multipleSeriesRenderer.setZoomEnabled(false);
            multipleSeriesRenderer.setGridColor(Color.LTGRAY);
            multipleSeriesRenderer.setPointSize(5);
            multipleSeriesRenderer.setLabelsColor(Color.BLACK);
            multipleSeriesRenderer.setChartTitleTextSize(45);
            multipleSeriesRenderer.setAxesColor(Color.BLACK);
            multipleSeriesRenderer.setGridColor(Color.BLACK);
            multipleSeriesRenderer.setBackgroundColor(Color.LTGRAY);
            multipleSeriesRenderer.setFitLegend(true);
            multipleSeriesRenderer.setExternalZoomEnabled(false);

            multipleSeriesRenderer.setXLabelsColor(Color.RED);
            multipleSeriesRenderer.setYLabelsColor(0,Color.RED);
            multipleSeriesRenderer.setShowGrid(true);
            multipleSeriesRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
            multipleSeriesRenderer.setXTitle("Days");
            multipleSeriesRenderer.setYTitle("Performance");
            multipleSeriesRenderer.setChartTitle("Performance Index");


            Intent intent= ChartFactory.getLineChartIntent(context,dataset,multipleSeriesRenderer,"Line Graph");


            return intent;
        }
    }

