import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args){

		Scanner input = new Scanner(System.in);
		double oneExecution = 0.0, pacingTime = 0.0, finalTps = 0.0, tpsHour = 0.0, tpsMin = 0.0, tpsSec = 0.0, numOfUsers = 0.0;
		String testCaseName = null, strOneExecution = null, strInput = null, tpsChoiceName = null;
		char tpsChoice;

		System.out.println("\n\n");
		System.out.println("Throughput Calculator");
		System.out.println("By James Snee");
		System.out.println("---------------------\n\n");
		System.out.println("Using Little's Law, This program will calculate the number of");
		System.out.println("concurrent users to use in a Test Case scenario/Thread group");
		System.out.println("for JMeter. Please note that generally Think Time is for the overall");
		System.out.println("scenario and Pacing is per test case step, but you will");
		System.out.println("need to input the pacing for the overall scenario because you may");
		System.out.println("want to have different pacings for different steps.\n\n");

		System.out.print("Please enter a test case name (press enter for none): ");
		testCaseName = input.nextLine();

		do{
			if(testCaseName.isEmpty()){
				System.out.println("Please enter the time it takes for test case to execute: ");
			}
			else{
				System.out.println("Please enter the time it takes for " + testCaseName + " to execute: ");
			}
			
			System.out.print("(Enter in seconds): ");
			strOneExecution = input.nextLine();

			if(!strOneExecution.matches("[0-9.]+")){
				System.out.println("Error: Please only input numbers!");
			}
			else {
				strOneExecution = strOneExecution.replaceAll("\\D+W+", "");
				oneExecution = Double.parseDouble(strOneExecution);
				//System.out.printf("Execution time is %.2f\n", oneExecution);

			}

		}
		while(oneExecution == 0.0);
		


		System.out.print("Please enter the overall pacing you wish to add (press enter for none): ");
		strInput = input.nextLine();
		
		if(strInput.isEmpty()){
			pacingTime = 0.0;
		}
		else {

			if(strInput.matches("[A-Za-z]+")){
				System.out.println("Illegal characters detected, pacing set to 0.0");
				pacingTime = 0.0;
			}
			else{
				pacingTime = Double.parseDouble(strInput);
			}

		}
		


		do {
			System.out.println("Is your Transactions Per Second(TPS) in hours, minutes or seconds?");
			System.out.print("(Enter h, m or s): ");
			tpsChoice = input.next().charAt(0);

			if(tpsChoice != 'h' && tpsChoice != 'm' && tpsChoice != 's' && tpsChoice != 'H' && tpsChoice != 'M' && tpsChoice != 'S'){
				System.out.println("Error: Please choose either h, m or s!");
			}

		}
		while(tpsChoice != 'h' && tpsChoice != 'm' && tpsChoice != 's' && tpsChoice != 'H' && tpsChoice != 'M' && tpsChoice != 'S');

		System.out.print("Please enter your Transactions Per Second(TPS): ");

		switch(tpsChoice){

			case 'h':
				tpsHour = input.nextDouble();
				//for hours, divide by 60 to get mins, and then 60 again to get seconds (final calc has to be in secs)
				finalTps = tpsHour / 3600;
				tpsChoiceName = "Hours";
				break;
			case 'H':
				tpsHour = input.nextDouble();
				finalTps = tpsHour / 3600;
				tpsChoiceName = "Hours";
				break;
			case 'm':
				tpsMin = input.nextDouble();
				finalTps = tpsMin / 60;
				tpsChoiceName = "Minutes";
				break;
			case 'M':
				tpsMin = input.nextDouble();
				finalTps = tpsMin / 60;
				tpsChoiceName = "Minutes";
				break;
			case 's':
				tpsSec = input.nextDouble();
				finalTps = tpsSec;
				tpsChoiceName = "Seconds";
				break;
			case 'S':
				tpsSec = input.nextDouble();
				finalTps = tpsSec;
				tpsChoiceName = "Seconds";
				break;				

		}

		System.out.println("\n\nCalculating number of users required....\n\n");
		

		//do Littles Law calculation
		numOfUsers = (oneExecution + pacingTime) * finalTps;
		System.out.println("==================================================");

		if(testCaseName.isEmpty()){
			System.out.println("Test Case");
		}
		else{
			System.out.println("Test Case: " + testCaseName);
		}

		System.out.printf("Time taken for one execution: %.2f seconds\n", oneExecution);

		if(pacingTime > 0){
			System.out.printf("Pacing chosen: %.2f seconds\n", pacingTime);
		}

		System.out.println("TPS Format: " + tpsChoiceName);
		System.out.printf("Transactions Per Second: %.2f seconds\n", finalTps);
		System.out.printf("Your number of concurrent users is: %.2f users\n", numOfUsers);
		System.out.println("==================================================");


	}




}