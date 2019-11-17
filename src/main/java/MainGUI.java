
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import Helper.Helper;
import Helper.HintTextField;
import Helper.ExcelHelper;


public class MainGUI extends JPanel {
    private JButton helpButton;
    private JLabel informationLabel;
    private JLabel testCaseLabel;
    private JLabel testCaseNameLabel;
    private JTextField testCaseNameField;
    private JTextField totalExecutionTimeField;
    private JLabel pacingLabel;
    private JTextField pacingField;
    private JComboBox tpsChoice;
    private JLabel tpsLabel;
    private JButton calculateButton;
    private JLabel processLabel;
    private JLabel transactionsLabel;
    private JTextField transactionsField;
    private JButton resetButton;

    private String tpsChoiceString = "Hours";
    private String testCaseNameString = null;
    private String pacingString;
    private String oneExecutionString = null;
    private String temp_tps = null;
    String fileName;

    //Number values
    double oneExecution = 0.0;
    double pacingTime = 0.0;
    double finalTps = 0.0;
    double tpsHour = 0.0;
    double tpsMin = 0.0;
    double tpsSec = 0.0;
    double numOfUsers = 0.0;

    boolean allOptionsSetCorrectly = false;

    public double calculateTransactionsPerHour(String temp_tps, String tpsChoice){
        /*
         * By James
         * Checks the transactions per hour, minute or second input is valid
         * Returns String input as Double
         */
        double finalTps = 0.0;
        if(temp_tps.isEmpty()){
            System.out.println("Error: Input cannot be empty!");
            JOptionPane.showMessageDialog(null, "Error: Input cannot be empty for Transactions Per " +
                    tpsChoice.substring(0, tpsChoice.length() - 1) + "!");
            return -1;
        }

        else if(temp_tps.matches("[A-Za-z]+")){
            System.out.println("Error: Please only input numbers!");
            JOptionPane.showMessageDialog(null, "Error: Please only input numbers for Transactions per " +
                    tpsChoice.substring(0, tpsChoice.length() - 1) + "!");
            return -1;
        }
        else {
            switch(tpsChoice){
                case "Hours":
                    tpsHour = Double.parseDouble(temp_tps);
                    //for hours, divide by 60 to get mins, and then 60 again to get seconds (final calc has to be in secs)
                    finalTps = tpsHour / 3600;
                    break;
                case "Minutes":
                    tpsMin = Double.parseDouble(temp_tps);
                    finalTps = tpsMin / 60;
                    break;
                case "Seconds":
                    tpsSec = Double.parseDouble(temp_tps);
                    finalTps = tpsSec;
                    break;
            }
        }
        return finalTps;
    }


    public MainGUI() {
        //Constructor for GUI
        //construct preComponents
        String[] tpsChoiceItems = {"Hours", "Minutes", "Seconds"};

        //construct components
        helpButton = new JButton ("Help Me!");
        final String desc = "Throughput Calculator - Concurrent Users\n" +
                "Using Little's Law, This program will calculate the number of\n" +
                "concurrent users to use in a Test Case scenario/Thread group\n" +
                "for JMeter. Please note that generally Think Time is for the\n" +
                "overall scenario and Pacing is per test case step, but you will\n" +
                "need to input the pacing for the overall scenario because you\n" +
                "may want to have different pacings for different steps.\n\n";
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, desc);
            }
        });
        informationLabel = new JLabel ("Please fill in the options below:");
        testCaseLabel = new JLabel ("Total execution time of test case:");
        testCaseNameLabel = new JLabel ("Test Case Name:");
        testCaseNameField = new JTextField (5);
        totalExecutionTimeField = new HintTextField("(Enter in seconds)");
        pacingLabel = new JLabel ("Please enter the overall pacing you wish to add:");
        pacingField = new HintTextField("(In seconds, enter 0 for none)");
        tpsChoice = new JComboBox (tpsChoiceItems);
        tpsChoice.setEditable(false);
        tpsChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tpsChoiceString = (String)tpsChoice.getSelectedItem();
                transactionsLabel.setText("Please enter your Transactions Per " +
                        tpsChoiceString.substring(0, tpsChoiceString.length() - 1) + ":");
            }
        });
        tpsLabel = new JLabel ("Is your Transactions Per Second (TPS) in hours, minutes or seconds?");
        calculateButton = new JButton ("Calculate TPS!");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testCaseNameString = testCaseNameField.getText();
                //Check fields are in correct format
                oneExecutionString = totalExecutionTimeField.getText();
                oneExecution = Helper.checkOneExecutionField(oneExecutionString);

                //Pacing
                pacingString = pacingField.getText();
                pacingTime = Helper.checkPacingField(pacingString);

                temp_tps = transactionsField.getText();

                //Temporary check to make sure all options set correctly
                tpsHour = Helper.checkTransactionsField(temp_tps, tpsChoiceString);

                if(oneExecution != -1 && finalTps != -1 && tpsHour != -1){
                    allOptionsSetCorrectly = true;
                }

                if(allOptionsSetCorrectly){
                    processLabel.setText("Calculating number of users required...");
                    processLabel.setVisible(true);
                    finalTps = calculateTransactionsPerHour(temp_tps, tpsChoiceString);
                    //do Littles Law calculation
                    numOfUsers = (oneExecution + pacingTime) * finalTps;

                    //Transactions Per H/M/S
                    float[] minMaxValue = new float[2];
                    if(tpsChoiceString.equals("Hours")){
                        tpsHour = Helper.checkTransactionsField(temp_tps, tpsChoiceString);
                        minMaxValue = Helper.calculatePacing((float)numOfUsers, (float)tpsHour);
                    }
                    else if(tpsChoiceString.equals("Minutes")){
                        tpsMin = Helper.checkTransactionsField(temp_tps, tpsChoiceString);
                        tpsHour = tpsMin * 60;
                        minMaxValue = Helper.calculatePacing((float)numOfUsers, (float)tpsHour);
                    }
                    else if(tpsChoiceString.equals("Seconds")){
                        tpsSec = Helper.checkTransactionsField(temp_tps, tpsChoiceString);
                        tpsHour = tpsSec * 3600;
                        minMaxValue = Helper.calculatePacing((float)numOfUsers, (float)tpsHour);
                    }

                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
                    String strDate = sdf.format(date);
                    fileName = "TPSCalculation_" + testCaseNameField.getText() + "_" + strDate + ".xlsx";
                    //double[] minMaxValue = Helper.Helper.calculatePacing(numOfUsers, finalTps);
                    String pacingString = "";
                    if(pacingTime > 0){
                        pacingString = String.format("Pacing chosen: %.2f seconds.\n", pacingTime);
                    }
                    else if(pacingTime == 0){
                        pacingString = "Illegal characters detected, pacing set to 0.\n";
                    }
                    String output = String.format("Test Case: " + testCaseNameString + "\n" +
                                    "Time taken for one execution: %.2f seconds.\n" +
                                    pacingString +
                                    "Minimum overall pacing: %.2f seconds.\n" +
                                    "Maximum overall pacing: %.2f seconds.\n" +
                                    "TPS Format: " + tpsChoiceString + "\n"+
                                    "Transactions Per Second: %.2f seconds\n" +
                                    "Your number of concurrent users is: %.2f users\n",
                            oneExecution, minMaxValue[0], minMaxValue[1], finalTps, numOfUsers);
                    ArrayList<String> fields = new ArrayList<String>();
                    fields.add(testCaseNameString);
                    fields.add(oneExecutionString);
                    fields.add(String.valueOf(pacingTime));
                    fields.add(String.valueOf(minMaxValue[0]));
                    fields.add(String.valueOf(minMaxValue[1]));
                    fields.add(tpsChoiceString);
                    fields.add(String.valueOf(finalTps));
                    fields.add(String.valueOf(numOfUsers));
                    try {
                        ExcelHelper.writeToExcelFile(fields, fileName);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    processLabel.setText("Written to Excel File: " + fileName);
                    JOptionPane.showMessageDialog(null, output);

                }
            }
        });
        processLabel = new JLabel ("Calculating number of users required...");
        processLabel.setVisible(false);


        transactionsLabel = new JLabel ("Please enter your Transactions Per " +
                tpsChoiceString.substring(0, tpsChoiceString.length() - 1) + ":");
        transactionsField = new JTextField (5);
        resetButton = new JButton ("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Clear all fields
                testCaseNameField.setText("");
                testCaseNameString = "";
                totalExecutionTimeField.setText("");
                pacingField.setText("");
                pacingString = "";
                transactionsField.setText("");
                finalTps = 0.0;
                processLabel.setText("");
            }
        });

        //adjust size and set layout
        setPreferredSize (new Dimension (527, 314));
        setLayout (null);

        //add components
        add (helpButton);
        add (informationLabel);
        add (testCaseLabel);
        add (testCaseNameLabel);
        add (testCaseNameField);
        add (totalExecutionTimeField);
        add (pacingLabel);
        add (pacingField);
        add (tpsChoice);
        add (tpsLabel);
        add (calculateButton);
        add (processLabel);
        add (transactionsLabel);
        add (transactionsField);
        add (resetButton);

        //set component bounds (only needed by Absolute Positioning)
        helpButton.setBounds (5, 10, 100, 25);
        informationLabel.setBounds (115, 10, 465, 25);
        testCaseLabel.setBounds (5, 80, 195, 30);
        testCaseNameLabel.setBounds (5, 50, 120, 25);
        testCaseNameField.setBounds (110, 50, 400, 25);
        totalExecutionTimeField.setBounds (200, 85, 310, 25);
        pacingLabel.setBounds (5, 120, 280, 25);
        pacingField.setBounds (280, 120, 230, 25);
        tpsChoice.setBounds (410, 155, 100, 25);
        tpsLabel.setBounds (5, 155, 400, 25);
        calculateButton.setBounds (185, 235, 155, 25);
        processLabel.setBounds (40, 290, 500, 20);
        transactionsLabel.setBounds (5, 190, 305, 20);
        transactionsField.setBounds (300, 190, 210, 25);
        resetButton.setBounds (410, 10, 100, 25);
    }


    public static void main (String[] args) {
        /*
         * By James
         * This is the GUI version of the TPS Calculator
         * For the old console based version, refer to the Main class in src/main/java/Old
         */
        JFrame frame = new JFrame ("TPS Calculator | By James Snee");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dimension.width/2-frame.getSize().width/2, dimension.height/2-frame.getSize().height/2);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(new MainGUI());
        frame.pack();
        frame.setVisible(true);
    }
}
