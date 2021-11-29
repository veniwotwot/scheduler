package scheduler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.net.*;

public class Scheduler {

	public static void main(String[] args) {
		Scheduler s = new Scheduler();

		try {
			s.execute(args[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// System.out.println();
		// s.execute2();
	}

	private void execute(String arg) throws FileNotFoundException {
		readInputs(arg);
	}

	// Reads inputs and converts them to an array of processes
	private void readInputs(String arg) throws FileNotFoundException {
		URL url = getClass().getResource("jobs.txt");
		File text = new File(url.getPath());
		Scanner scnr = new Scanner(text);

		//System.out.println("Entered readInputs.");

		// Reading each line of the file using Scanner class
		String[] ray = new String[3];
		List<Process> input = new ArrayList<Process>();
		int i;

		while (scnr.hasNextLine()) {
			i = 0;
			// System.out.println("i = " + i);
			String line = scnr.nextLine();
			StringTokenizer st = new StringTokenizer(line, "\t");
			while (st.hasMoreTokens()) {
				ray[i] = st.nextToken();
				i++;
			}
			input.add(new Process(ray[0], Integer.parseInt(ray[1]), Integer.parseInt(ray[2])));
		}
		scnr.close();

		//System.out.println("Finished scanning.");

		Process[] input2 = new Process[input.size()];

		for (int l = 0; l < input.size(); l++) {
			input2[l] = input.get(l);
		}

		for (int k = 0; k < input.size(); k++) {
			//System.out.println(input2[k].toPrint());
		}

		//System.out.println("Finished scanning. v2");
		//System.out.println("Arg is: " + arg);

		interpretInputs(input2, arg);

	}

	private void interpretInputs(Process[] inputs, String arg) {
		//System.out.println("Entered interpretInputs");
		//System.out.println();

		if (arg.equals("RR")) {
			String rr = RoundRobin(inputs);
			System.out.println("The output of RR  is: " + rr);
			drawGraph(rr);
		} else if (arg.equals("SRT")) {
			String srt = ShortestRemaining(inputs);
			System.out.println("The output of SRT is: " + srt);
			drawGraph(srt);
		} else if (arg.equals("FB")) {
			String fb = Feedback(inputs);
			System.out.println("The output of FB  is: " + fb);
			drawGraph(fb);
		} else if (arg.equals("ALL")) {
			//System.out.println("Entered Else If Cond for ALL");
			
			Process[] input1 = deepClone(inputs);
			Process[] input2 = deepClone(inputs);
			Process[] input3 = deepClone(inputs);
/*
			System.out.println("Test Pos 1: input1 array");
			for (int k = 0; k < length; k++) {
				System.out.println(input1[k].toPrint());
			}
			System.out.println("Test Pos 1: input2 array");
			for (int k = 0; k < length; k++) {
				System.out.println(input2[k].toPrint());
			}
			System.out.println("Test Pos 1: input3 array");
			for (int k = 0; k < length; k++) {
				System.out.println(input3[k].toPrint());
			}
*/
			String rr = RoundRobin(input1);
			//System.out.println("Test Pos 2: " + rr);
/*
			System.out.println("Test Pos 2: input1 array");
			for (int k = 0; k < length; k++) {
				System.out.println(input1[k].toPrint());
			}
			System.out.println("Test Pos 2: input2 array");
			for (int k = 0; k < length; k++) {
				System.out.println(input2[k].toPrint());
			}
			System.out.println("Test Pos 2: input3 array");
			for (int k = 0; k < length; k++) {
				System.out.println(input3[k].toPrint());
			}
*/
			String srt = ShortestRemaining(input2);
			//System.out.println("Test Pos 3: " + srt);
			String fb = Feedback(input3);
			//System.out.println("Test Pos 4: " + fb);

			System.out.println("The output of RR  is: " + rr);
			drawGraph(rr);
			System.out.println();

			System.out.println("The output of SRT is: " + srt);
			drawGraph(srt);
			System.out.println();

			System.out.println("The output of FB  is: " + fb);
			drawGraph(fb);
			System.out.println();
		} else {
			System.out.println("Invalid input arg.");
		}

	}

	private Process[] deepClone(Process[] object) {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			ObjectInputStream objectInputStream = new ObjectInputStream(bais);
			return (Process[]) objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//Original testing processes code
	private void execute2() {
		// Create Processes
		Process[] input1 = setup2();
		Process[] input2 = setup2();
		Process[] input3 = setup2();

		// Run
		String rr = RoundRobin(input1);
		String srt = ShortestRemaining(input2);
		String fb = Feedback(input3);

		// Print Output
		System.out.println("The output of RR  is: " + rr);
		drawGraph(rr);
		System.out.println();

		System.out.println("The output of SRT is: " + srt);
		drawGraph(srt);
		System.out.println();

		System.out.println("The output of FB  is: " + fb);
		drawGraph(fb);
		System.out.println();
	}

	//Used for testing
	private Process[] setup() {
		Process a = new Process("A", 0, 3);
		Process b = new Process("B", 2, 6);
		Process c = new Process("C", 4, 4);
		Process d = new Process("D", 6, 5);
		Process e = new Process("E", 8, 2);

		Process[] output = { a, b, c, d, e };
		return output;
	}

	private Process[] setup2() {
		Process a = new Process("A", 0, 3);
		Process b = new Process("B", 1, 4);
		Process c = new Process("C", 4, 6);
		Process d = new Process("D", 7, 5);
		Process e = new Process("E", 8, 2);

		Process[] output = { a, b, c, d, e };
		return output;
	}

	// Scheduling algorithm RR, accepts Array of Process and returns String
	private String RoundRobin(Process[] input) {
		String output = "";
		Queue<Process> RRQ = new LinkedList<>();
		boolean running = true;
		int time = 0;
		Process curr = null;

		// Initial check for starting processes when time == 0
		for (int i = 0; i < input.length; i++) {
			// if (input[i] != null)
			if (input[i].getStart() == time)
				RRQ.add(input[i]);
		}

		// System.out.println(RRQ.peek().getName());

		while (running) {
			// Run the Process in line for q = 1
			curr = RRQ.peek();
			output += curr.run();
			RRQ.remove();
			time++;

			// Add to Queue if starting now
			for (int i = 0; i < input.length; i++) {
				if (input[i].getStart() == time)
					RRQ.add(input[i]);
			}

			// If curr isnt finished, place back in queue
			if (curr.getDuration() > 0)
				RRQ.add(curr);

			// If queue is empty now, stop
			if (RRQ.isEmpty())
				running = false;
		}
		return output;
	}

	// Scheduling algorithm SRT, accepts Array of Processes and returns String
	private String ShortestRemaining(Process[] input) {
		String output = "";
		int time = 0;
		int position = 0;
		int shortest;
		Process SRT = null;
		boolean running = true;

		while (running) {
			shortest = Integer.MAX_VALUE;
			SRT = null;

			// Scan to find valid process with with the shortest remaining time
			for (int i = 0; i < input.length; i++) {
				if (input[i] != null && time >= input[i].getStart())
					if (input[i].getDuration() < shortest && input[i].getDuration() > 0) {
						shortest = input[i].getDuration();
						SRT = input[i];
						position = i;
					}
			}

			// if there is no valid SRT, stop running
			if (SRT == null)
				running = false;
			else {
				// Run for q = 1
				output += SRT.run();
				time++;

				// If the SRT Process is finished, set it to null in the input array
				if (SRT.getDuration() == 0)
					input[position] = null;
			}

		}

		return output;
	}

	// Scheduling algorithm FB, accepts array of processes and returns String
	private String Feedback(Process[] input) {
		String output = "";
		int time = 0;
		boolean running = true;
		Process curr = null;
		Queue<Process> top = new LinkedList<>();
		Queue<Process> mid = new LinkedList<>();
		Queue<Process> low = new LinkedList<>();

		while (running) {
			// Add to Queue if starting now
			for (int i = 0; i < input.length; i++) {
				if (input[i].getStart() == time)
					top.add(input[i]);
			}

			if (top.isEmpty() != true) {
				curr = top.peek();
				output += curr.run();

				// System.out.print("top queue: ");
				// System.out.println(curr.toPrint());

				if (curr.getDuration() == 0)
					top.remove();
				else {
					top.remove();
					mid.add(curr);
				}
			} else if (mid.isEmpty() != true) {
				curr = mid.peek();
				output += curr.run();

				// System.out.print("mid queue: ");
				// System.out.println(curr.toPrint());

				if (curr.getDuration() == 0)
					mid.remove();
				else {
					mid.remove();
					low.add(curr);
				}
			} else if (low.isEmpty() != true) {
				curr = low.peek();
				output += curr.run();

				// System.out.print("low queue: ");
				// System.out.println(curr.toPrint());

				if (curr.getDuration() == 0)
					low.remove();
				else {
					low.remove();
					low.add(curr);
				}
			} else {
				running = false;
			}

			time++;
			// System.out.println(output);
			curr = null;
		}
		return output;
	}

	private String[] toArray(String input) {
		String[] output = new String[input.length()];

		for (int i = 0; i < input.length(); i++) {
			output[i] = input.substring(i, i + 1);
		}
		return output;
	}

	private void drawGraph(String input) {
		String[] ray = toArray(input);
		String[] alpha = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };
		boolean running = true;
		int count = 0;

		int i = 0;
		while (running) {
			System.out.print(alpha[i] + "  ");

			for (int j = 0; j < input.length(); j++) {
				if (ray[j].equals(alpha[i])) {
					System.out.print("X");
					count++;
				} else
					System.out.print(" ");
			}
			i++;
			System.out.println();
			if (count == ray.length)
				running = false;
		}

	}
}

class Process implements java.io.Serializable{
	String name;
	int startTime;
	int duration;

	Process() {
		name = "";
		startTime = duration = 0;
	}

	Process(String n, int s, int d) {
		name = n;
		startTime = s;
		duration = d;
	}

	public String run() {
		duration--;
		return name;
	}

	public String getName() {
		return name;
	}

	public int getStart() {
		return startTime;
	}

	public int getDuration() {
		return duration;
	}
	

	// Method for testing
	public String toPrint() {
		return name + " " + startTime + " " + duration;
	}
}
