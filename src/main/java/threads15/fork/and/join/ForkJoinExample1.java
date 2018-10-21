package threads15.fork.and.join;
/*
  Fork join is the concrete implementation for parallel execution.
  
  Used for Divide and Conquer algorithms.Example: Merge Sort.
  
  IMPORTANT:-> subtasks have to be independent and preferably pure functions to be executed in parallel.
  
  RecursiveTask<T> is analogous to Callable
  RecursiveAction is analogous to Executable
  
  ForkJoinPool is a threadPool for fork-join tasks
  
  Stream API in java-8 use fork-join pool for parallel streams but for normal stream it uses main thread only.
  
  See: https://stackoverflow.com/questions/52915530/fork-join-pool-in-streams
       https://stackoverflow.com/questions/42486428/when-should-i-use-streams?rq=1
  
  
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

class SimpleRecursiveAction extends RecursiveAction {

	private int numberOfTasks;
	
	public  SimpleRecursiveAction(int numberOfTasks) {
		this.numberOfTasks = numberOfTasks;
	}
	
	@Override
	protected void compute() {
		if(numberOfTasks>50) {
			//split into two tasks
			SimpleRecursiveAction simpleRecursiveAction1 = new SimpleRecursiveAction(numberOfTasks/2);
			SimpleRecursiveAction simpleRecursiveAction2 = new SimpleRecursiveAction(numberOfTasks - numberOfTasks/2);
			//start both threads
			invokeAll(simpleRecursiveAction1,simpleRecursiveAction2);
		} else {
			System.out.println("Task Executed for "+numberOfTasks+" number of tasks");
		}
	}
	
}


public class ForkJoinExample1 {
	
	public static void main(String[] args) {
		
		ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
		//use common pool for better efficiency
		
		SimpleRecursiveAction simpleRecursiveAction = new SimpleRecursiveAction(11);
		forkJoinPool.invoke(simpleRecursiveAction);
		//use invoke method to start the task
		
		SimpleRecursiveAction simpleRecursiveAction2 = new SimpleRecursiveAction(101);
		forkJoinPool.invoke(simpleRecursiveAction2);
	}

}
