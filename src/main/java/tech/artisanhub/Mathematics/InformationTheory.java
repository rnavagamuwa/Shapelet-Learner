package tech.artisanhub.Mathematics;

import weka.core.Instances;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jawadhsr on 8/18/16.
 */
public class InformationTheory {

    /**
     *
     * @param D1 : is any subset of D2 where it represents a Dataset table with one or more coulmns with a class vlaue
     * @param D2 : is the main dataset.
     * @return : Information gain when we split D2 into D1.
     */
    public double InformationGain(Instances D1,Instances D2){

        double inforGain = 0.0;
        if (D1.size() > D2.size()){
            inforGain = Integer.MIN_VALUE;
        }

        else {
            inforGain = Entropy(D2) - Entropy(D1);
        }

        return  inforGain;
    }

    /**
     *
     * @param data : is any dataset
     * @return entropy of the dataset.
     */
    public double Entropy (Instances data){
        double entropy = 0.0;
        Map<Double,Integer> classValueDist = new HashMap<Double, Integer>();

        for (int i=0;i<data.size();i++){
            double tempClassVal = data.get(i).classValue();
            if(classValueDist.containsKey(tempClassVal)){
                int currentCount = classValueDist.get(tempClassVal);
                classValueDist.put(tempClassVal,++currentCount);

            }
            else {
                classValueDist.put(tempClassVal,1);
            }
        }

        for (Map.Entry<Double,Integer> mapVal : classValueDist.entrySet()){
            int temp = mapVal.getValue();
            double probability = temp*1.0/data.size();
            entropy += probability*Math.log10(probability)/Math.log10(2);
        }
        return  -1.0*entropy;
    }
}
