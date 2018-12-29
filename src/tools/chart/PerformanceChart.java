package tools.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;

/**
 * @ Date       ：Created in 13:57 2018/12/29
 * @ Modified By：
 * @ Version:     0.1
 */
public class PerformanceChart {
    private long maxScale;

    private Process[] processes;

    public PerformanceChart(long maxScale, Process... processes) {
        this.maxScale = maxScale;
        this.processes = processes;
    }

    public void drawChart() {
        long start, end;
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for (long scale = 10; scale < maxScale; scale *= 10) {
            System.out.println("start scale " + scale);
            for (Process process : processes) {
                start = System.currentTimeMillis();
                process.scale = scale;
                process.run();
                end = System.currentTimeMillis();
                dataSet.addValue(end - start, process.name, String.valueOf(scale));
            }
            System.out.println("end scale " + scale);
        }

        StandardChartTheme mChartTheme = new StandardChartTheme("CN");
        mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
        mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
        mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        ChartFactory.setChartTheme(mChartTheme);
        JFreeChart mChart = ChartFactory.createLineChart(
                "折线图",//图名字
                "scale",//横坐标
                "ms",//纵坐标
                dataSet,//数据集
                PlotOrientation.VERTICAL,
                true, // 显示图例
                true, // 采用标准生成器
                false);// 是否生成超链接
        CategoryPlot mPlot = (CategoryPlot) mChart.getPlot();
        mPlot.setBackgroundPaint(Color.LIGHT_GRAY);
        mPlot.setRangeGridlinePaint(Color.BLUE);//背景底部横虚线
        mPlot.setOutlinePaint(Color.RED);//边界线

        ChartFrame mChartFrame = new ChartFrame("折线图", mChart);
        mChartFrame.pack();
        mChartFrame.setVisible(true);
    }

    public abstract static class Process {

        protected String name;

        public long scale;

        public Process(String name) {
            this.name = name;
        }

        public abstract void run();

    }

}