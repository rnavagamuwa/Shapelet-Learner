package tech.artisanhub.ARFFGenerator;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import java.io.File;

public class CSVtoARFF {

    private static final String TRAINING_DATA = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/DEBS-2013-SoccerField/result_1st_goal.txt";
    private static final String OUTPUT_DATA = "/home/rnavagamuwa/Documents/CSE/FYP/Datasets/DEBS-2013-SoccerField/ARFF_result_1st_goal.arff";




    public static void main(String[] args) throws Exception {

        String salihPath = "/home/jawadhsr/Desktop/FYP/FIles/iris_replacedNamedWithInts.csv";
        // load CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(salihPath));
        Instances data = loader.getDataSet();
        System.out.println("Source CSV has been successfully loaded");

        String salihOutput = "/home/jawadhsr/Desktop/FYP/FIles/output.arff";
        // save ARFF
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(salihOutput));
        saver.setDestination(new File(salihOutput));
        saver.writeBatch();
        System.out.println("ARFF has been successfully generated");

    }
}
