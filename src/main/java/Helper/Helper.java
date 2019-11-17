package Helper;

import javax.swing.*;
import java.text.DecimalFormat;

public class Helper {

    public static float[] calculatePacing(float users, float transactionsPerHour){
        /*
         * By James
         * Calculates Overall Pacing
         * Returns as float array for printing
         */
        float pacing = transactionsPerHour / users;
        float tpm = 60/pacing;
        float minTime = Math.round(0.98 * tpm * 60);
        float maxTime = Math.round(1.02 * tpm * 60);

        float[] returns = new float[2];
        returns[0] = minTime;
        returns[1] = maxTime;

        return returns;
    }


    public static double checkOneExecutionField(String oneExecutionString){
        /*
         * By James
         * Checks the input to the Execution for one testcase field is valid
         * Returns String input as Double
         */
        if(!oneExecutionString.matches("[0-9.]+")){
            JOptionPane.showMessageDialog(null, "Error: Please only input numbers for total execution time!");
            return -1;
        }
        else {
            oneExecutionString = oneExecutionString.replaceAll("\\D+W+", "");
            return Double.parseDouble(oneExecutionString);
        }
    }

    public static double checkPacingField(String strInput){
        /*
         * By James
         * Checks the input to the pacing field is valid
         * Returns String input as Double
         */
        double pacingTime;
        if(strInput.isEmpty()){
            pacingTime = 0.0;
        }
        else {
            if(strInput.matches("[A-Za-z]+")){
                System.out.println("Illegal characters detected, pacing set to 0.0");
                pacingTime = 0.0;
            }
            else if(!strInput.matches("[0-9.]+")){
                pacingTime = 0.0;
            }
            else{
                pacingTime = Double.parseDouble(strInput);
            }

        }
        return pacingTime;
    }

    public static double checkTransactionsField(String temp_tps, String tpsChoice){
        /*
         * By James
         * Checks the input to the transactions field is valid
         * Returns String input as Double
         */
        double finalTps = 0;
        double tpsHour = 0;
        double tpsMin = 0;
        double tpsSec = 0;
        if(temp_tps.isEmpty()){
            JOptionPane.showMessageDialog(null, "Error: Input cannot be empty for Transactions!");
            return -1;
        }

        else if(temp_tps.matches("[A-Za-z]+")){
            JOptionPane.showMessageDialog(null, "Error: Please only input numbers for Transactions!");
        }
        else {

            switch(tpsChoice){
                case "Hours":
                    tpsHour = Double.parseDouble(temp_tps);
                    finalTps = tpsHour;
                    break;
                case "Minutes":
                    tpsMin = Double.parseDouble(temp_tps);
                    finalTps = tpsMin * 60;
                    break;
                case "Seconds":
                    tpsSec = Double.parseDouble(temp_tps);
                    finalTps = tpsSec * 3600;
                    break;

            }


        }
        return finalTps;
    }

}
