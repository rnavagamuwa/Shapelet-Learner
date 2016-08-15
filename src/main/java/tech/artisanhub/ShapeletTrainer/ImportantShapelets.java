package tech.artisanhub.ShapeletTrainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by jawadhsr on 8/14/16.
 */
public class ImportantShapelets {

    public ArrayList<Shapelet> GetImportantShapelets(ArrayList<Shapelet> shapelets, Double[] dataSet, int[] classValues) {
        ArrayList<Shapelet> shapeletsArr = new ArrayList<Shapelet>();
        ArrayList<Double> classValProbs = new ArrayList<Double>();
        Map<Integer, ShapeletBucket> shapeletBucket = null;

        for (int i = 0; i < classValues.length; i++) {
            ShapeletBucket temp = new ShapeletBucket(classValues[i]);
            classValProbs.add(findProb(dataSet, classValues[i]));
            shapeletBucket.put(classValues[i], temp);
            //remember Above can be optimized.
        }
        Map<Integer,Double> clasNprob = null;
        for (Shapelet s : shapelets) {
            clasNprob = MaxProbClassVal(s);
            // above method has to be changed and for that attributes
            // of shapelets also has to be changed.
            Integer clas = Integer.parseInt(clasNprob.keySet().toArray()[0].toString());
            shapeletBucket.get(clas).put(s);
        }
        Double[][] differences = new Double[classValues.length][10];
        Double[] minDifs = new Double[classValues.length];
        Map <Integer,Map<Shapelet,Double>> shapeDiff = null;
        for (int clas : classValues) {
            Map<Shapelet,Double> temp = null;
            for (Shapelet s : shapeletBucket.get(clas).getShapeletSet()) {
                temp.put(s,clasNprob.get(clas) - classValProbs.get(clas));
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

    private Double GetMinDif(Double[] diff, int classVal) {
        Double Mindif = Double.MAX_VALUE;

        for (int i = 0; i < diff.length; i++) {
            if (Mindif > diff[i]) {
                Mindif = diff[i];
            }
        }

        return Mindif;
    }

    private Map<Integer,Double> MaxProbClassVal(Shapelet shaplet) {
        double[][] shapeContent = shaplet.contentInMergedShapelets;
        double [] tempArr = shapeContent[shapeContent.length];
        Map <Integer,Double> retValue = null;
        Arrays.sort(tempArr);
        Map <Double,Integer> classValCount = null;

        for(int i=0;i<tempArr.length;i++){
           if(classValCount.containsKey(tempArr[i])){
               Integer val = classValCount.get(tempArr[i]);
               val ++;
               classValCount.put(tempArr[i],val);
           }
            else {
               classValCount.put(tempArr[i],1);
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
        Double maxProb = MaxVal*1.0/tempArr.length;
        retValue.put(MaxKey.intValue(),maxProb);
        return retValue;
    }

    private double findProb(Double[] data, int classVal) {
        int count = 0;
        for (int i = 0; i < data.length; i++) {
            if (Integer.parseInt(data[i].toString()) == classVal) {
                count++;
            }
        }

        return count / data.length;

    }



}
