some abstract sig Robot {
	contributes : some Capability
} 
fact { all r:Robot | disj[r.contributes, (Robot-r).contributes]} // Robot capabilities do not overlap

some abstract sig Capability {
	canrun: some Task,
	belongsto: one Robot
}
fact { all c:Capability, r:Robot | r in c.belongsto <=> c in r.contributes } 

some abstract sig Location {}

some abstract sig Task {
	runby: one Capability
} 
fact { all t:Task, c:Capability | t.runby = c => t in c.canrun} // All tasks are run by a capability able to run it

some abstract sig CompositeTask {
	tasks: some Task,
	location: one Location
}
fact { 
	all ct:CompositeTask | disj[ct.tasks, (CompositeTask-ct).tasks] // Tasks belong only to a single composite task
}

fact {
	Location in CompositeTask.location // All locations have some composite task running in it
	Task in Capability.canrun // All tasks are covered by some robot capability
	Task in CompositeTask.tasks // All tasks are part of a composite task
	Capability in Robot.contributes // All capabilities belong to some robot
}


// Example:

// Tasks:
some sig TEMP extends Task{}
{
    disj[runby, Capability-MeasuringTemp]  // TEMP tasks only run by capability MeasuringTemp
}
some sig BLOOD_PRESSURE extends Task{}
{
    disj[runby, Capability-MeasuringPressure]  // BLOOD_PRESSURE tasks only run by capability MeasuringPressure
}
some sig CLEAN extends Task{}
{
    disj[runby, Capability-Cleaning]  // CLEAN tasks only run by capability Cleaning
}
some sig vitalParamGoal extends CompositeTask{}
fact { all c:vitalParamGoal | #c.tasks = 2 and not disj[TEMP, c.tasks] and not disj[BLOOD_PRESSURE, c.tasks]} // Composite task vitalParamGoal

some sig cleanRoom extends CompositeTask{}
fact { all c:cleanRoom | #c.tasks = 1 and not disj[CLEAN, c.tasks]} // Composite task cleanRoom


// Rooms:
one sig RoomA, RoomB, RoomC, RoomD, RoomE extends Location{}

// Capabilities:
some sig Cleaning extends Capability {}
{
    disj[canrun, Task-CLEAN]// Capability able to run only CLEAN tasks
}
some sig MeasuringTemp extends Capability {}
{
    disj[canrun, Task-TEMP]// Capability able to run only TEMP tasks
}
some sig MeasuringPressure extends Capability {}
{
    disj[canrun, Task-BLOOD_PRESSURE]// Capability able to run only BLOOD_PRESSURE tasks
}

// Robots:
some sig CleanerRobot extends Robot {}
{
    disj[contributes, Capability-(Cleaning)] // CleanerRobot robot
}
some sig MedRobot extends Robot {}
{
    disj[contributes, Capability-(MeasuringTemp+MeasuringPressure)] // MedRobot robot
}

// Task specification (Task allocation in relation to task.runby):
pred TaskAllocation{
    one ct:cleanRoom | #(ct.location&RoomA)=1  // Do cleanRoom in room RoomA
    one ct:vitalParamGoal | #(ct.location&RoomB)=1  // Do vitalParamGoal in room RoomB
    one ct:cleanRoom | #(ct.location&RoomB)=1  // Do cleanRoom in room RoomB
    one ct:cleanRoom | #(ct.location&RoomC)=1  // Do cleanRoom in room RoomC
    one ct:cleanRoom | #(ct.location&RoomD)=1  // Do cleanRoom in room RoomD
    one ct:cleanRoom | #(ct.location&RoomE)=1  // Do cleanRoom in room RoomE
}
// ):
run TaskAllocation for exactly 5 Location, exactly 5 Capability, exactly 4 Robot, 9 Task, exactly 6 CompositeTask, exactly 2 MedRobot
