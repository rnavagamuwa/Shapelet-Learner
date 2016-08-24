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
            int k = Integer.MAX_VALUE; // number of shapelets

            int minLength = 2;
            int maxLength = 4;
            String ARFFName = "/home/jawadhsr/Desktop/FYP/FIles/IRIS/iris_replacedNamedWithInts.arff";
//            String ARFFName = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/IRIS/iris_replacedNamedWithInts.arff";
            Instances data = ShapeletFilter.loadData(ARFFName);

            String outPutFile =  "/home/jawadhsr/Desktop/FYP/FIles/IRIS/Shaplets.txt";
//            String outPutFile = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/IRIS/shapelets.txt";
            ShapeletFilter sf = new ShapeletFilter(k, minLength, maxLength);
            sf.setLogOutputFile(outPutFile); // log file stores shapelet output
            ArrayList<Shapelet> generatedShapelets = sf.process(data);
            int [] arr = {1,2,3};
            ArrayList<Shapelet> outPut = new ImportantShapelets().GetImportantShapelets(new MergeShapelets().mergeShapelets(generatedShapelets,15),data,arr);

            for(Shapelet val : outPut){
                if(val != null)
                 System.out.println(val.contentInMergedShapelets);
            }

            ArrayList<ArrayList<Double>> shapelets = new ArrayList<ArrayList<Double>>();
            ArrayList<Double> currentSHapelet  = new ArrayList<Double>();

            int size = 0;
            int startPos =0;
            int sizeOfTheShapelet = 0;
            for(Shapelet val : outPut){
                shapelets = new ArrayList<ArrayList<Double>>();
                if(val != null) {
                    sizeOfTheShapelet = val.contentInMergedShapelets.size();
                    for (int i = 0; i < sizeOfTheShapelet; i++) {
                        size = val.contentInMergedShapelets.get(i).size() - 4;
                        startPos = val.contentInMergedShapelets.get(i).get(size + 2).intValue();
                        currentSHapelet = new ArrayList<Double>();
                        for (int j = 0; j < 4; j++) {
                            if (startPos > j) {
                                currentSHapelet.add(-100.00);
                            } else if (startPos + size < j) {
                                currentSHapelet.add(-100.00);
                            } else {
                                currentSHapelet.add(val.contentInMergedShapelets.get(i).get(j));
                            }
                        }
                        shapelets.add(currentSHapelet);
                    }
                    //plot graph for each event using shapelets 2d arraylist
                    System.out.println(val.contentInMergedShapelets);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}