import java.util.ArrayList;

public class Plan {
	
	public String run_sequence;
	public String run;
	public String description;
	private int different_locations;
	private int different_compositeTasks;
	
	public Plan(Allocate[] a, int num_locations, int num_capababilities, int num_robots, int num_tasks, int num_ct, int num_MedRobot,String d) throws Exception {
			
		//Run sequence
		run_sequence = "run TaskAllocation for exactly "+ num_locations +" Location, exactly "+ num_capababilities+ " Capability, exactly "+ num_robots+" Robot, "+ num_tasks +" Task, exactly "+ num_ct +" CompositeTask, exactly "+ num_MedRobot +" MedRobot";
		
		this.run = run_sequence;
		this.description = d;
	}
	
	public void Plan2(Allocate[] a, int num_capababilities, int num_robots, int num_tasks, int num_ct, int num_MedRobot,String d) throws Exception {
		//This method is under development to automatically estimate:
		// 1) number of different rooms
		// 2) number of composite tasks
		// 3) number of capabilities
		//...
		
		//Run as: Plan2(allocate,5,4,9,6,2,"Plan1");
		
		
		//1) Get number of different rooms
		//(rooms must match the number of DIFFERENT rooms assigned in "Allocate", as rooms can repeat)
		ArrayList<String> all_rooms = new ArrayList<String>();
		for (Allocate allocate: a) {
			//System.out.print(allocate.room.getName());
			boolean ans = all_rooms.contains(allocate.room.getName()); //check if already added
			if (ans==false) {//if not added
				all_rooms.add(allocate.room.getName()); // add
			}
			different_locations = all_rooms.size();
		}
		
		//2) Get number of different compositeTasks
		//(compositeTasks must match the number of Allocate, as they are separate tasks, even when the task is the same in multiple rooms)
		different_compositeTasks = a.length;
		
		//3) Get number of capabilities
		
		
		//Run sequence
		run_sequence = "run TaskAllocation for exactly "+ different_locations +" Location, exactly "+ num_capababilities+ " Capability, exactly "+ num_robots+" Robot, "+ num_tasks +" Task, exactly "+ different_compositeTasks +" CompositeTask, exactly "+ num_MedRobot +" MedRobot";
		
		this.run = run_sequence;
		this.description = d;
	}

}

