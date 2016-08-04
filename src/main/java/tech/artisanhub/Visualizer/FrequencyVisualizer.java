package tech.artisanhub.Visualizer;

import weka.core.Instances;
import java.io.FileReader;
import java.util.TreeMap;

public class FrequencyVisualizer {
    public static void main(String []args){
        String inputARFFDataSetPath = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/pima-indians-diabetes/arff-pima-indians-diabetes.data";
        Instances data = loadData(inputARFFDataSetPath);
        TreeMap<Double, Integer> classDistributions = getClassDistributions(data);
        System.out.print(classDistributions.get(0.0));
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

    public static Instances loadData(String fileName) {
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
}
