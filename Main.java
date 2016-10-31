import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args){

		Scanner input = new Scanner(System.in);
		double oneExecution = 0.0, pacingTime = 0.0, finalTps = 0.0, tpsHour = 0.0, tpsMin = 0.0, tpsSec = 0.0, numOfUsers = 0.0;
		String testCaseName = null;
		char tpsChoice;

		System.out.println("\n\n");
		System.out.println("Throughput Calculator");
		System.out.println("By James Snee");
		System.out.println("---------------------\n\n");
		System.out.println("Using Little's Law, This program will calculate the number of");
		System.out.println("concurrent users to use in a Test Case scenario/Thread group");
		System.out.println("for JMeter. Please note that Think Time is for the overall");
		System.out.println("scenario, and Pacing is per test case step, which we use here.\n\n");

		System.out.print("Please enter a test case name: ");
		testCaseName = input.nextLine();

			
		System.out.println("Please enter the time it takes for one Test Case to execute: ");
		System.out.print("(Enter in seconds): ");
		oneExecution = input.nextDouble();


		System.out.print("Please enter any pacing you wish to add (press enter for none): ");
		pacingTime = input.nextDouble();

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
				break;
			case 'H':
				tpsHour = input.nextDouble();
				finalTps = tpsHour / 3600;
				break;
			case 'm':
				tpsMin = input.nextDouble();
				finalTps = tpsMin / 60;
				break;
			case 'M':
				tpsMin = input.nextDouble();
				finalTps = tpsMin / 60;
				break;
			case 's':
				tpsSec = input.nextDouble();
				finalTps = tpsSec;
				break;
			case 'S':
				tpsSec = input.nextDouble();
				finalTps = tpsSec;
				break;				

		}

		System.out.println("\n\nCalculating number of users required....");
		

		//do Littles Law calculation
		numOfUsers = (oneExecution + pacingTime) * finalTps;
		System.out.println("Test Case: " + testCaseName);
		System.out.printf("Time taken for one execution: %.2f\n", oneExecution);

		if(pacingTime > 0){
			System.out.printf("Pacing chosen: %.2f\n", pacingTime);
		}
		System.out.println("TPS Format: " + tpsChoice);
		System.out.printf("Transactions Per Second: %.2f\n", finalTps);
		System.out.printf("Your number of concurrent users is: %.2f\n", numOfUsers);


	}




}