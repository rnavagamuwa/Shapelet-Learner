package tech.artisanhub.ShapeletTrainer;


import org.jfree.util.ArrayUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MergeShapelets {

   public ArrayList<Shapelet> mergeShapelets(ArrayList<Shapelet> shapelets,int requiredClassSize){


       ArrayList<Shapelet> mergedShapeletsOut = new ArrayList<Shapelet>();

       Shapelet currentShapelet = null;
       Iterator<Shapelet> iterator = shapelets.iterator();

       int count = shapelets.size()/requiredClassSize;

       ArrayList<ArrayList<Double>> temp = new ArrayList<ArrayList<Double>>();

       while (iterator.hasNext()){
           currentShapelet = iterator.next();
           if (count>0){
               temp.add(shapelets.size()/requiredClassSize - count, new ArrayList<Double>(Arrays.asList(currentShapelet.rawContent)));
               count --;
           }else {
               count = shapelets.size()/requiredClassSize;
               mergedShapeletsOut.add(new Shapelet(temp));
               temp = new ArrayList<ArrayList<Double>>();
           }

       }

       return mergedShapeletsOut;
   }
}
