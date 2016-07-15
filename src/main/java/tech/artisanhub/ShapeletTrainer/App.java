package tech.artisanhub.ShapeletTrainer;

import weka.core.Instances;
public class App 
{
    public static void main( String[] args )
    {
        try {
            // mandatory requirements: numShapelets (k), min shapelet length, max shapelet length, input
            // data
            // additional information: log output dir

            // example filter, k = 10, minLength = 20, maxLength = 40, data = , output = exampleOutput.txt
            int k = 10;
            int minLength = 1;
            int maxLength = 8;
            String ARFFName = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/pima-indians-diabetes/pima-indians-diabetes.arff";
            Instances data = ShapeletFilter.loadData(ARFFName);

            String outPutFile = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/pima-indians-diabetes/shapeletsOut.txt";
            ShapeletFilter sf = new ShapeletFilter(k, minLength, maxLength);
            sf.setLogOutputFile(outPutFile); // log file stores shapelet output

            // Note: sf.process returns a transformed set of Instances. The first time that
            // thisFilter.process(data) is called, shapelet extraction occurs. Subsequent calls to process
            // uses the previously extracted shapelets to transform the data. For example:
            //
            // Instances transformedTrain = sf.process(trainingData); -> extracts shapelets and can be
            // used to transform training data
            // Instances transformedTest = sf.process(testData); -> uses shapelets extracted from
            // trainingData to transform testData
            Instances transformed = sf.process(data);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
