
public class Test {

	public static void main(String[] args) throws Exception {
		
		// Create a few tasks
		Task t1 = new Task("TEMP","MeasuringTemp");
		Task t2 = new Task("BLOOD_PRESSURE","MeasuringPressure");
		Task t3 = new Task("CLEAN","Cleaning");
		
		Task[] tasks = new Task[] {t1,t2,t3};
		
		// Create a few composite tasks
		CompositeTask ct_1 = new CompositeTask("vitalParamGoal","CT1", new Task[] {t1,t2});
		CompositeTask ct_2 = new CompositeTask("cleanRoom","CT2", new Task[] {t3});
		
		CompositeTask[] compositeTasks = new CompositeTask[] {ct_1,ct_2};
		
		// Add rooms
		Room r_A = new Room("RoomA");
		Room r_B = new Room("RoomB");
		Room r_C = new Room("RoomC");
		Room r_D = new Room("RoomD");
		Room r_E = new Room("RoomE");
		Room r_F = new Room("RoomF");
		
		Room[] rooms = new Room[] {r_A,r_B,r_C,r_D,r_E};
		
		// World model
		World hospital = new World("HospitalWorld",rooms);
		
		// Robot Capabilities
		Capability c1 = new Capability("Cleaning",t3);
		Capability c2 = new Capability("MeasuringTemp",t1);
		Capability c3 = new Capability("MeasuringPressure",t2);
		
		Capability[] capabilities = new Capability[]{c1,c2,c3};
		
		// Robots
		Robot r1 = new Robot("CleanerRobot", new Capability[] {c1});
		Robot r2 = new Robot("MedRobot", new Capability[] {c2,c3});
		
		Robot[] robots = new Robot[] {r1,r2};
		
		// TaskAllocation
		Allocate a_1 = new Allocate(ct_2,r_A);
		Allocate a_2 = new Allocate(ct_1,r_B);
		Allocate a_3 = new Allocate(ct_2,r_B);
		Allocate a_4 = new Allocate(ct_2,r_C);
		Allocate a_5 = new Allocate(ct_2,r_D);
		Allocate a_6 = new Allocate(ct_2,r_E);
		Allocate a_7 = new Allocate(ct_2,r_F);
		
		Allocate[] allocate = new Allocate[] {a_1,a_2,a_3,a_4,a_5,a_6};
		
		//Task planning: (allocate,num_locations, num_capababilities, num_robots, num_tasks, num_ct, num_MedRobot)  
		Plan plan = new Plan(allocate,5,5,4,9,6,2,"Plan1");
		
		//Create Alloy Model¨
		AlloyModel.createAlloyFile(tasks, compositeTasks, rooms, capabilities,robots,allocate,plan);
		
	}

}
