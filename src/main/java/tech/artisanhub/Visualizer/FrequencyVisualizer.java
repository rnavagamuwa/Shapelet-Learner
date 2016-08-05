package tech.artisanhub.Visualizer;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;
import weka.core.Instances;

import java.io.*;
import java.util.TreeMap;

public class FrequencyVisualizer {
    public static void main(String []args) throws IOException {

        String inputARFFDataSetPath = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/pima-indians-diabetes/arff-pima-indians-diabetes.data";
        Instances data = loadData(inputARFFDataSetPath);
        TreeMap<Double, Integer> classDistributions = getClassDistributions(data);

        CategoryDataset categoryInputDataset = createInputDataSet(classDistributions,data.size());
        BarChart_AWT chartInputData = new BarChart_AWT("Shapelet Learner Visualizer", "Probabilities of the input dataset","Category","Probability",categoryInputDataset);
        chartInputData.pack( );
        RefineryUtilities.centerFrameOnScreen( chartInputData );
        chartInputData.setVisible( true );


        String shapeletPath = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/pima-indians-diabetes/shapeletsOut.txt";
        FileInputStream inputStream = new FileInputStream(shapeletPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        CategoryDataset categoryShapeletDataset = createShapeletDataSet(reader,100);
        BarChart_AWT chartShapelets = new BarChart_AWT("Shapelet Learner Visualizer", "Information gain of the shapelets","Category","Information Gain",categoryShapeletDataset);
        chartShapelets.pack( );
        RefineryUtilities.centerFrameOnScreen( chartShapelets );
        chartShapelets.setVisible( true );

    }



    private static TreeMap<Double, Integer> getClassDistributions(Instances data) {
        TreeMap<Double, Integer> classDistribution = new TreeMap<Double, Integer>();
        double classValue;
        for (int i = 0; i < data.numInstances(); i++) {
            classValue = data.instance(i).classValue();
            boolean classExists = false;
            for (Double d : classDistribution.keySet()) {
                if (d == classValue) {
                    int temp = classDistribution.get(d);
                    temp++;
                    classDistribution.put(classValue, temp);
                    classExists = true;
                }
            }
            if (classExists == false) {
                classDistribution.put(classValue, 1);
            }
        }
        return classDistribution;
    }

    private static Instances loadData(String fileName) {
        Instances data = null;
        try {
            FileReader r;
            r = new FileReader(fileName);
            data = new Instances(r);

            data.setClassIndex(data.numAttributes() - 1);
        }
        catch (Exception e) {
            System.out.println(" Error =" + e + " in method loadData");
            e.printStackTrace();
        }
        return data;
    }

    private static CategoryDataset createInputDataSet(TreeMap<Double, Integer> classDistributions,float totalRowCount){

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        final Object[] keySet = classDistributions.keySet().toArray();
        float percentage;

        for(int i=0;i<keySet.length;i++){
            percentage = classDistributions.get(keySet[i])/totalRowCount;
            percentage = percentage * 100;
            dataset.addValue(percentage,keySet[i].toString(),"Input");
        }
        return dataset;
    }

    private static CategoryDataset createShapeletDataSet(BufferedReader reader, int noOfShapeletsToVisualize) throws IOException {

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        int count =1;
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            String[] temp = line.split(",");
            Float percentageVal = Float.valueOf(temp[0])*100;
            dataset.addValue(percentageVal,String.valueOf(count),"Shapelets");
            reader.readLine();
            count++;
            if (count>noOfShapeletsToVisualize)
                break;
        }

        return  dataset;
    }
}
