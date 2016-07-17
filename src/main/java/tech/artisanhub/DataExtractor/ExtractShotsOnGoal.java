package tech.artisanhub.DataExtractor;

import java.io.*;
import java.math.BigDecimal;

public class ExtractShotsOnGoal {
    public static void main( String[] args ){
        BigDecimal val1 =    new BigDecimal("10629342490369879");
        BigDecimal picoVal = new BigDecimal("1000000000000");

   //Extracting the data relevant to first goal
        BigDecimal startTimeInPicoSec = new BigDecimal("426").multiply(picoVal).add(val1);
        BigDecimal endTimeInPicoSec = new BigDecimal("450").multiply(picoVal).add(val1);
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;


        try {
            inputStream = new FileInputStream("/home/rnavagamuwa/Documents/CSE/FYP/Datasets/DEBS-2013-SoccerField/full-game.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            outputStream = new FileOutputStream("/home/rnavagamuwa/Documents/CSE/FYP/Datasets/DEBS-2013-SoccerField/result_1st_goal.txt");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

            String line = null;
            BigDecimal currentTime;
            int count = 0;
            while ((line = reader.readLine())!= null){
                currentTime = new BigDecimal(line.split(",")[1]);
                if (currentTime.compareTo(startTimeInPicoSec)>0){
                    writer.write(line.concat("\n"));
                    count ++;
                }
                if (currentTime.compareTo(endTimeInPicoSec)>0){
                    break;
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Extracted file has been successfully created");
    }

}
