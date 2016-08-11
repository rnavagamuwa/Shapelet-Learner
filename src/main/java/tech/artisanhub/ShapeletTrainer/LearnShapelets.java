package tech.artisanhub.ShapeletTrainer;

import weka.core.Instances;

import java.util.ArrayList;

public class LearnShapelets
{
    public static void main( String[] args )
    {
        try {
            // mandatory requirements: numShapelets (k), min shapelet length, max shapelet length, input
            // data
            // additional information: log output dir

            // example filter, k = 10, minLength = 20, maxLength = 40, data = , output = exampleOutput.txt

            int k = 15;
            int minLength = 2;
            int maxLength = 8;
            String ARFFName = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/pima-indians-diabetes/arff-pima-indians-diabetes.data";
            Instances data = ShapeletFilter.loadData(ARFFName);

            String outPutFile = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/pima-indians-diabetes/shapelets_out.txt";
            ShapeletFilter sf = new ShapeletFilter(k, minLength, maxLength);
            sf.setLogOutputFile(outPutFile); // log file stores shapelet output
            ArrayList<Shapelet> shapelets = sf.process(data);
            int i =4;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
