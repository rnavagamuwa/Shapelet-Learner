package tech.artisanhub.ShapeletTrainer;

import weka.core.Instances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jawadhsr on 8/14/16.
 */
public class ImportantShapelets {

    public ArrayList<Shapelet> GetImportantShapelets(ArrayList<Shapelet> shapelets, Instances dataSet, int[] classValues) {
        ArrayList<Shapelet> shapeletsArr = new ArrayList<Shapelet>();
        ArrayList<Double> classValProbs = new ArrayList<Double>();
        Map<Integer, ShapeletBucket> shapeletBucket = new HashMap<Integer, ShapeletBucket>();

        for (int i = 0; i < classValues.length; i++) {
            ShapeletBucket temp = new ShapeletBucket(classValues[i]);

            classValProbs.add(findProb(dataSet, classValues[i]));
            shapeletBucket.put(classValues[i], temp);
            //remember Above can be optimized.
        }
        System.out.println(classValues.length);
        System.out.println(shapeletBucket.size());
        Map<Integer,Double> clasNprob = new HashMap<Integer, Double>();
        for (Shapelet s : shapelets) {
            for(Integer val : MaxProbClassVal(s).keySet()){
                clasNprob.put(val,MaxProbClassVal(s).get(val));
            }

            // above method has to be changed and for that attributes
            // of shapelets also has to be changed.
            Integer clas = Integer.parseInt(clasNprob.keySet().toArray()[0].toString());
            shapeletBucket.get(clas).put(s);
        }
        Map <Integer,Map<Shapelet,Double>> shapeDiff = new HashMap<Integer, Map<Shapelet, Double>>();
        for (int clas : classValues) {
            Map<Shapelet,Double> temp = new HashMap<Shapelet, Double>();
            for (Shapelet s : shapeletBucket.get(clas).getShapeletSet()) {
                //temp.put(s,0.4);
                double val = clasNprob.get(clas);
                temp.put(s, val - classValProbs.get(clas));
                //differences[clas][s.seriesId] = Math.abs(/*Here the prob(class Val) of shapelt has to be included. */-classValProbs.get(clas));
                // this has to be changed. The above is wrong.
            }
            shapeDiff.put(clas,temp);

            // Done. Now Test the values.
            Shapelet newShape = GetMinDifShape(shapeDiff.get(clas));
            // Have to change this heavily.
            shapeletsArr.add(newShape);
        }


        return shapeletsArr;

    }

    private Shapelet GetMinDifShape(Map<Shapelet,Double> shapeDiffs) {
        Shapelet shapelet = null; // = new Shapelet(minDiffs);
        Double minVal = Double.MAX_VALUE;
        for(Map.Entry<Shapelet,Double> sd : shapeDiffs.entrySet()){
            if(minVal > sd.getValue()){
                minVal = sd.getValue();
                shapelet = sd.getKey();
            }
        }

        return shapelet;
    }


    private Map<Integer,Double> MaxProbClassVal(Shapelet shaplet) {
       ArrayList<ArrayList<Double>> shapeContent = shaplet.contentInMergedShapelets;
        ArrayList<Double> tempArr = new ArrayList<Double>();
        int count = 0;
        for(ArrayList<Double> val : shapeContent){
            tempArr.add(count,val.get(val.size()-1));
            count ++;
        }

        Map <Integer,Double> retValue = new HashMap<Integer, Double>();
        //Arrays.sort(tempArr);
        Map <Double,Integer> classValCount = new HashMap<Double, Integer>();

        for(int i=0;i<tempArr.size();i++){
           if(classValCount.containsKey(tempArr.get(i))){
               Integer val = classValCount.get(tempArr.get(i));
               val ++;
               classValCount.put(tempArr.get(i),val);
           }
            else {
               classValCount.put(tempArr.get(i),1);
           }
        }
        int MaxVal = 0;
        Double MaxKey = 0.0; // classValues
       for (Map.Entry<Double,Integer> val : classValCount.entrySet()){
            if(val.getValue()>MaxVal){
                MaxVal = val.getValue();
                MaxKey = val.getKey();
            }
       }
        Double maxProb = MaxVal*1.0/tempArr.size();
        retValue.put(MaxKey.intValue(),maxProb);
        return retValue;
    }

    private double findProb(Instances data, int classVal) {
        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            if (Integer.parseInt(data.get(i).attribute(data.get(i).numAttributes()-3).toString()) == classVal) {
                count++;
            }
        }

        double var =  count*1.0/data.size();
        System.out.println("Probability of the classvalue "+classVal+" is "+ var );
        return var;

    }



}
