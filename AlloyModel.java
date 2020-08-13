import java.io.*;
import java.util.List;

public class AlloyModel {
	private String sigDeclaration;
	private static Task[] task;
	private static CompositeTask[] compositeTask;
	private static Room[] room;
	private static Capability[] capability;
	private static Robot[] robot;
	private static Allocate[] allocate;
	private static Plan plan;
	
	private static int i;
	private static int count;
	
	
	private static String returnValue; 
	
	public static void createAlloyFile(Task[] t, CompositeTask[] ct, Room[] r_i, Capability[] cap, Robot[] r, Allocate[] a, Plan p) throws Exception {
		task = t;
		compositeTask = ct;
		room = r_i;
		capability = cap;
		robot = r;
		allocate = a;
		plan = p;
		
		System.out.println("STARTING ALLOY FILE");
		//Import sig Declaration from .txt file
		
		//Delete prior file
		File f1 = new File("src/models/model.als");
		boolean b = f1.delete(); // if b is true, then the file has been deleted successfully
		
		//Read declaration of signatures directly from sigDeclaration.txt
		
		returnValue = readTextFile("src/sigDeclaration.txt");
		writeTextFile("src/models/model.als" , returnValue);
		
		
		//Create file
		BufferedWriter WriteToFile = new BufferedWriter(new FileWriter("src/models/model.als", true));
		
		//Write description
		WriteToFile.newLine();WriteToFile.newLine();
		WriteToFile.write("// Example:");
		WriteToFile.newLine();WriteToFile.newLine();
				
		
		//Add Tasks  e.g.: some sig CLEAN extends Task{} {disj[runby, Capability-Cleaning]}
		WriteToFile.write("// Tasks:");WriteToFile.newLine();
		for (Task task : t) {
			WriteToFile.write("some sig " + task.name + " extends Task{}"); WriteToFile.newLine();
			WriteToFile.write("{"); WriteToFile.newLine();
			WriteToFile.write("    disj[runby, Capability-"+ task.capability + "]  // "+ task.name +" tasks only run by capability "+ task.capability); WriteToFile.newLine();
			WriteToFile.write("}"); WriteToFile.newLine();
		}
		
		
		//Add CompositeTasks
		for (CompositeTask compositeTask : ct) {
			WriteToFile.write("some sig " + compositeTask.name + " extends CompositeTask{}"); WriteToFile.newLine();
			WriteToFile.write("fact { all c:" + compositeTask.name + " | #c.tasks = " + compositeTask.getNumberTasks());
			
			//Iterate over tasks 
			for (Task ts: compositeTask.tasks) {
				WriteToFile.write(" and not disj["+ ts.name +", c.tasks]");
			}
			WriteToFile.write("} // Composite task "+ compositeTask.name);WriteToFile.newLine();
			WriteToFile.newLine();
		}
		WriteToFile.newLine();
		
		//Add Rooms
		WriteToFile.write("// Rooms:");WriteToFile.newLine();
		i = 1;
		count = room.length; //num of rooms to add commas
		WriteToFile.write("one sig ");
		
		for (Room room : r_i) {
			WriteToFile.write(room.getName());
			if (i<count) {
				WriteToFile.write(", "); //num of rooms to add commas
				i = i+1;
			}
		}
		WriteToFile.write(" extends Location{}");WriteToFile.newLine();
		WriteToFile.newLine();
		
		//Add Capabilities
		WriteToFile.write("// Capabilities:");WriteToFile.newLine();
		for (Capability capability: cap) {
			WriteToFile.write("some sig "+ capability.getName() +" extends Capability {}");WriteToFile.newLine();
			WriteToFile.write("{"); WriteToFile.newLine();
			WriteToFile.write("    disj[canrun, Task-"+ capability.getCanRunTask() +"]"); WriteToFile.write("// Capability able to run only " + capability.getCanRunTask() +" tasks");
			WriteToFile.newLine();
			WriteToFile.write("}"); WriteToFile.newLine();
		}
		WriteToFile.newLine();
		
		//Add Robots
		WriteToFile.write("// Robots:");WriteToFile.newLine();
		for (Robot robot: r) {
			WriteToFile.write("some sig "+ robot.getName() +" extends Robot {}");WriteToFile.newLine();
			WriteToFile.write("{");WriteToFile.newLine();
			WriteToFile.write("    disj[contributes, Capability-(");
			
			i=1;
			count = robot.getNumCapab();
			
			for (Capability capab: robot.capabilities) {
				//WriteToFile.write("MeasuringTemp+MeasuringPressure");
				WriteToFile.write(capab.name);
				if (i<count) {
					WriteToFile.write("+");
					i = i+1;
				}
			}
			
			WriteToFile.write(")] // "+ robot.getName() +" robot");WriteToFile.newLine();
			WriteToFile.write("}");WriteToFile.newLine();
		}
		WriteToFile.newLine();
		
		//Add Task specification
		WriteToFile.write("// Task specification (Task allocation in relation to task.runby):");WriteToFile.newLine();
		WriteToFile.write("pred TaskAllocation{");WriteToFile.newLine();
		for (Allocate allocate: a) {
			WriteToFile.write("    one ct:"+allocate.compositeTask.name+" | #(ct.location&"+allocate.room.getName()+")=1  // Do "+allocate.compositeTask.name+" in room "+allocate.room.getName());WriteToFile.newLine();
		}
		WriteToFile.write("}");
		WriteToFile.newLine();
		
		//Add Task planning
		WriteToFile.write("// ):");WriteToFile.newLine();
		WriteToFile.write(p.run);
		
		
		//Close file
		WriteToFile.close();
		
		System.out.println("ALLOY FILE COMPLITED");
	}
	
	
	
	private static String readTextFile(String fileName) {
	    String returnValue = "";
	    FileReader file;
	    String line = "";
	    try {
	        file = new FileReader(fileName);
	        BufferedReader reader = new BufferedReader(file);
	                    try {
	            while ((line = reader.readLine()) != null) {
	            returnValue += line + "\n";
	            }
	                    } finally {
	                        reader.close();
	                    }
	    } catch (FileNotFoundException e) {
	        throw new RuntimeException("File not found");
	    } catch (IOException e) {
	        throw new RuntimeException("IO Error occured");
	    }
	    return returnValue;

	}
	
	

	private static void writeTextFile(String fileName, String s) {
	    FileWriter output;
	    try {
	        output = new FileWriter(fileName);
	        BufferedWriter writer = new BufferedWriter(output);
	        writer.write(s);
	                    writer.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}
	
	
		
	
}