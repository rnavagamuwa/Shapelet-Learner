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
            int maxLength = 3;
            String ARFFName = "/home/jawadhsr/Desktop/FYP/FIles/Air/air_3columns.arff";
//            String ARFFName = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/IRIS/iris_replacedNamedWithInts.arff";
            Instances data = ShapeletFilter.loadData(ARFFName);

            String outPutFile =  "/home/jawadhsr/Desktop/FYP/FIles/Air/Shaplets.txt";
//            String outPutFile = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/IRIS/shapelets.txt";
            ShapeletFilter sf = new ShapeletFilter(k, minLength, maxLength);
            sf.setLogOutputFile(outPutFile); // log file stores shapelet output
            ArrayList<Shapelet> generatedShapelets = sf.process(data);
            int [] arr = {1,2,3};
            ArrayList<Shapelet> outPut = new ImportantShapelets().GetImportantShapelets(new MergeShapelets().mergeShapelets(generatedShapelets,15),data,arr);

            int i=0;
            for(Shapelet val : outPut){
                if(val != null)
                 System.out.println(val.contentInMergedShapelets);
                i++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}