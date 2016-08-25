package tech.artisanhub.ShapeletTrainer;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;
import tech.artisanhub.Visualizer.XYLineChart_AWT;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Map;

public class LearnShapelets
{
    public static void main( String[] args )
    {       long startTime = System.currentTimeMillis();
        try {

            String ARFFName = "/home/jawadhsr/Desktop/FYP/FIles/IRIS/example.arff";
//            String ARFFName = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/IRIS/iris_replacedNamedWithInts.arff";
            Instances data = ShapeletFilter.loadData(ARFFName);

            int k = Integer.MAX_VALUE; // number of shapelets
            int minLength = 2;
            int maxLength = data.get(1).numValues()-1;
            int shapeletClusterSize = 5;

            String outPutFile = "/home/jawadhsr/Desktop/FYP/FIles/IRIS/shapelets.txt";
//            String outPutFile = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/IRIS/shapelets.txt";
            ShapeletFilter sf = new ShapeletFilter(k, minLength, maxLength);
            sf.setLogOutputFile(outPutFile); // log file stores shapelet output
            ArrayList<Shapelet> generatedShapelets = sf.process(data);
            Map<Double,Integer> classDist = ShapeletFilter.getClassDistributions(data);
            ArrayList<Integer> arr = new ArrayList<Integer>();
            for (double val: classDist.keySet()){
                arr.add((int)val);
            }
            ArrayList<Shapelet> outPut = new ImportantShapelets().GetImportantShapelets(new MergeShapelets().mergeShapelets(generatedShapelets,shapeletClusterSize),data,arr);

            ArrayList<ArrayList<Double>> shapelets = new ArrayList<ArrayList<Double>>();
            ArrayList<Double> currentSHapelet  = new ArrayList<Double>();

            int size = 0;
            int startPos =0;
            int shapeletVal = 0;
            int sizeOfTheShapelet = 0;
            for(Shapelet val : outPut){
                shapelets = new ArrayList<ArrayList<Double>>();
                if(val != null) {
                    sizeOfTheShapelet = val.contentInMergedShapelets.size();
                    for (int i = 0; i < sizeOfTheShapelet; i++) {
                        size = val.contentInMergedShapelets.get(i).size() - 4;
                        startPos = val.contentInMergedShapelets.get(i).get(size + 2).intValue();
                        currentSHapelet = new ArrayList<Double>();
                        shapeletVal = 0;
                        for (int j = 0; j < data.get(1).numValues()-1; j++) {
                            if (startPos > j) {
                                shapeletVal ++;
                                currentSHapelet.add(-100.00);
                            } else if (startPos + size < j) {
                                shapeletVal ++;
                                currentSHapelet.add(-100.00);
                            } else {
                                currentSHapelet.add(val.contentInMergedShapelets.get(i).get(j-shapeletVal));
                            }
                        }
                        shapelets.add(currentSHapelet);
                    }

                    XYLineChart_AWT chart = new XYLineChart_AWT("Shapelets stats", "Shapelets stats",createDataset(shapelets,data.get(1).numValues()-1));
                    chart.pack( );
                    RefineryUtilities.centerFrameOnScreen( chart );
                    chart.setVisible( true );
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("\nExecution time in milli seconds: "+totalTime);
    }

    private static XYDataset createDataset(ArrayList<ArrayList<Double>> shapelets ,int rowSize)
    {
        XYSeriesCollection dataset = new XYSeriesCollection( );

        for (int i = 0; i < shapelets.size(); i++) {
            XYSeries series = new XYSeries(i);
            for (int j = 0; j < rowSize; j++) {
                double val = shapelets.get(i).get(j).doubleValue();
                series.add(j,val);
            }
            dataset.addSeries(series);
        }
        return dataset;
    }
}