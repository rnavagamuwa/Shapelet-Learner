package tech.artisanhub.ShapeletTrainer;


import java.util.ArrayList;
import java.util.Iterator;

public class MergeShapelets {


   /*class value isn't considering here. That should be added also. Without the class value salih can't do his work */

   public ArrayList<Shapelet> mergeShapelets(ArrayList<Shapelet> shapelets,int requiredClassSize){

       int noOfShapeletsToMerge = shapelets.size();
       double minInfoGain = shapelets.get(noOfShapeletsToMerge-1).informationGain;
       double maxInfoGain = shapelets.get(0).informationGain;

       double threshold = (maxInfoGain - minInfoGain) / requiredClassSize;
       minInfoGain += threshold;

       Iterator<Shapelet> iterator = shapelets.iterator();
       int shapeletSize = shapelets.get(0).content.length;

       Shapelet currentShapelet = null;
       ArrayList<Shapelet> mergedShapeletsOut = new ArrayList<Shapelet>();
       Shapelet mergedShapelet = null;
       int count =0;
       double currentInformationGain = 0;
       double[][] content = null;

       while (iterator.hasNext()){
           currentShapelet = iterator.next();
           if (minInfoGain<currentShapelet.informationGain){
               content[count] = currentShapelet.rawContent;
               currentInformationGain += currentShapelet.informationGain;
               count ++;
           }else if (minInfoGain>=currentShapelet.informationGain){
               minInfoGain +=threshold;
               mergedShapeletsOut.add(new Shapelet(content,currentInformationGain/count));
               currentInformationGain = 0;
               count = 0;
           }

           if (minInfoGain>maxInfoGain){
               break;
           }
       }

       return mergedShapeletsOut;
   }
}
