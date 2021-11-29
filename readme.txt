RUNNING DIRECTIONS FOR LINUX

Note: the .java file is named "Scheduler.java", it should be fairly obvious as this is the only .java file in the zip folder.

mkdir scheduler
// ---> upload Scheduler.java into scheduler
// ---> upload jobs.txt into scheduler

// Make sure that the path settings in Scheduler.java are correct:
// That means that File text within readInputs() of Scheduler.java should be appropriately set to the path of jobs.txt
//File text = new File("scheduler\\jobs.txt");

cd scheduler		//make sure you are in the scheduler directory
javac Scheduler.java 	//compile

cd			//make sure you are one level above semaEle

java scheduler/Scheduler RR
java scheduler/Scheduler SRT
java scheduler/Scheduler FB
java scheduler/Scheduler ALL
