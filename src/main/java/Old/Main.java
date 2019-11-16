package Old;

import java.lang.*;
import java.util.Scanner;

public class Main {

	public static void calculatePacing(float users, float transactionsPerHour){

		float pacing = transactionsPerHour / users;
		float tpm = 60/pacing;
		float minTime = Math.round(0.98 * tpm * 60);
		float maxTime = Math.round(1.02 * tpm * 60);
		System.out.println("Pacing: " + pacing + " = transactionsPerHour: " + transactionsPerHour + " divided by users: " + users);

		System.out.printf("Minimum overall pacing: %.2f seconds.\n", minTime);
		System.out.printf("Maximum overall pacing: %.2f seconds.\n", maxTime);
	}


	public static void main(String[] args){
		/*
		 * By James
		 * This script is the old console-based version of the Calculator
		 */

		Scanner input = new Scanner(System.in);
		double oneExecution = 0.0, pacingTime = 0.0, finalTps = 0.0, tpsHour = 0.0, tpsMin = 0.0, tpsSec = 0.0, numOfUsers = 0.0;
		String testCaseName = null, strOneExecution = null, temp_tps = null, strInput = null, tpsChoiceName = null, prompt = null;
		char tpsChoice;

		System.out.println("\n\n");
		System.out.println("Throughput Calculator - Concurrent Users");
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

			//set the next prompt to reflect choice
			if(tpsChoice == 'h' || tpsChoice == 'H'){
				prompt = "Please enter your Transactions Per Hour: ";
			}
			else if(tpsChoice == 'm' || tpsChoice == 'M'){
				prompt = "Please enter your Transactions Per Minute: ";
			}
			else if(tpsChoice == 's' || tpsChoice == 'S'){
				prompt = "Please enter your Transactions Per Second: ";
			}

			if(tpsChoice != 'h' && tpsChoice != 'm' && tpsChoice != 's' && tpsChoice != 'H' && tpsChoice != 'M' && tpsChoice != 'S'){
				System.out.println("Error: Please choose either h, m or s!");
			}

		}
		while(tpsChoice != 'h' && tpsChoice != 'm' && tpsChoice != 's' && tpsChoice != 'H' && tpsChoice != 'M' && tpsChoice != 'S');

		//get around scanner bug
		input.nextLine();

		do {

			System.out.print(prompt);

			temp_tps = input.nextLine();

			if(temp_tps.isEmpty()){
				System.out.println("Error: Input cannot be empty!");
			}

			else if(temp_tps.matches("[A-Za-z]+")){
				System.out.println("Error: Please only input numbers!");
			}
			else {

				System.out.println("Temp TPS: " + temp_tps);

				switch(tpsChoice){

					case 'h':
						//tpsHour = input.nextDouble();
						tpsHour = Double.parseDouble(temp_tps);
						//for hours, divide by 60 to get mins, and then 60 again to get seconds (final calc has to be in secs)
						finalTps = tpsHour / 3600;
						tpsChoiceName = "Hours";
						break;
					case 'H':
						//tpsHour = input.nextDouble();
						tpsHour = Double.parseDouble(temp_tps);
						finalTps = tpsHour / 3600;
						tpsChoiceName = "Hours";
						break;
					case 'm':
						//tpsMin = input.nextDouble();
						tpsMin = Double.parseDouble(temp_tps);
						finalTps = tpsMin / 60;
						tpsChoiceName = "Minutes";
						break;
					case 'M':
						//tpsMin = input.nextDouble();
						tpsMin = Double.parseDouble(temp_tps);
						finalTps = tpsMin / 60;
						tpsChoiceName = "Minutes";
						break;
					case 's':
						//tpsSec = input.nextDouble();
						tpsSec = Double.parseDouble(temp_tps);
						finalTps = tpsSec;
						tpsChoiceName = "Seconds";
						break;
					case 'S':
						//tpsSec = input.nextDouble();
						tpsSec = Double.parseDouble(temp_tps);
						finalTps = tpsSec;
						tpsChoiceName = "Seconds";
						break;

				}


			}

		}
		while(finalTps == 0.0);

		System.out.println("Final TPS: " + finalTps);
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

		if(tpsChoiceName.equals("Hours")){
			calculatePacing((float)numOfUsers, (float)tpsHour);
		}
		else if(tpsChoiceName.equals("Minutes")){
			//convert to hours again
			tpsHour = tpsMin * 60;
			calculatePacing((float)numOfUsers, (float)tpsHour);

		}
		else if(tpsChoiceName.equals("Seconds")){
			tpsHour = tpsSec * 3600;
			calculatePacing((float)numOfUsers, (float)tpsHour);

		}


		System.out.println("TPS Format: " + tpsChoiceName);
		System.out.printf("Transactions Per Second: %.2f seconds\n", finalTps);
		System.out.printf("Your number of concurrent users is: %.2f users\n", numOfUsers);
		System.out.println("==================================================");


	}




}