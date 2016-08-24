package tech.artisanhub.ShapeletTrainer;

import weka.core.Instances;

import java.util.ArrayList;
import java.util.Map;

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
            Map<Double,Integer> classDist = ShapeletFilter.getClassDistributions(data);
            ArrayList<Integer> arr = new ArrayList<Integer>();
            for (double val: classDist.keySet()){
                arr.add((int)val);
            }
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