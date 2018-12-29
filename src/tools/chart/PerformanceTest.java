package tools.chart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ Date       ：Created in 14:30 2018/12/29
 * @ Modified By：
 * @ Version:     0.1
 */
public class PerformanceTest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            list.add(i);
        }
        List<String> stringList = new ArrayList<>();
        stringList=list.stream().map(i -> String.valueOf(i * 2)).collect(Collectors.toList());
        PerformanceChart.Process lambda = new PerformanceChart.Process("lambda") {
            @Override
            public void run() {
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < scale; i++) {
                    list.add(i);
                }
                List<String> stringList = new ArrayList<>();
                stringList=list.stream().map(i -> String.valueOf(i * 2)).collect(Collectors.toList());
            }
        };
        PerformanceChart.Process foreach = new PerformanceChart.Process("foreach") {

            @Override
            public void run() {
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < scale; i++) {
                    list.add(i);
                }
                List<String> stringList = new ArrayList<>();
                for (Integer integer : list) {
                    stringList.add(String.valueOf(integer * 2));
                }
            }
        };
        PerformanceChart pc = new PerformanceChart(10000*10000,lambda, foreach);
        pc.drawChart();
    }
}