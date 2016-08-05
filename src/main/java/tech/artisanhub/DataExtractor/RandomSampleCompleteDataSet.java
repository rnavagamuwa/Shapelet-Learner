package tech.artisanhub.DataExtractor;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;

public class RandomSampleCompleteDataSet {
    public static void main(String args[]){
        BigDecimal val1 =    new BigDecimal("10629342490369879");
        int thresholdVal = 5;

        //Enter the value sampling value. To remove evey pico second values enter 1000000000000
        BigDecimal resolutionInPicoSeconds = new BigDecimal("1000000000000");

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = new FileInputStream("/home/rnavagamuwa/Documents/CSE/FYP/Datasets/DEBS-2013-SoccerField/full-game.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            outputStream = new FileOutputStream("/home/rnavagamuwa/Documents/CSE/FYP/Datasets/DEBS-2013-SoccerField/random_sample_complete_result_1st_goal.txt");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

            String line = null;
            BigDecimal counter = resolutionInPicoSeconds.add(val1);
            BigDecimal currentTime;
            int thresholdTemp = thresholdVal;
            while ((line = reader.readLine())!= null){
                currentTime = new BigDecimal(line.split(",")[1]);

                if (counter.compareTo(currentTime)>0){
                    continue;
                }

                if (thresholdTemp>0){
                    writer.write(line.concat("\n"));
                    thresholdTemp --;
                    continue;
                }
                thresholdTemp = 5;
                counter = counter.add(resolutionInPicoSeconds);
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Extracted file has been successfully created");
    }
}
