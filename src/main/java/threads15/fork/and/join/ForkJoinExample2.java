package threads15.fork.and.join;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

class MergeSort extends RecursiveAction {

	private final int[] array;
	private final int low;
	private final int high;


	MergeSort(int[] array, int low, int high) {
		this.array = array;
		this.low = low;
		this.high = high;
	}

	@Override
	protected void compute() {
		int size = high - low ;
		
		//Threshold value
		if(size < 3) {
			Arrays.sort(array, low, high);
			return;
		}
		
		// else use fork join
		int middle = (high + low) / 2;
		invokeAll(new MergeSort(array, low, middle), new MergeSort(array, middle, high));
		merge(middle);
	}
	
	// low and high are already present as data members
	private void merge(int middle) {
		//if last element of first part is smaller than first of 2nd then array is sorted
		if( array[middle -1] < array[middle]  ) {
			return;
		}
		// normal merging
//		int[] temp = new int[middle - low];
//		System.arraycopy(array, low, temp, 0, middle - low);
//		int ptr1 = low;
//		int ptr2 = middle;
//		int index = low ;
//		while(index < high && ptr1 < middle && ptr2 < high) {
//			if(array[ptr1] < array[ptr2]) {
//				array[index++] = ptr1++;
//			} else {
//				array[index++] = ptr2++;
//			}
//		}
//		while(index < high && ptr1 < middle) {
//			array[index++] = ptr1++;
//		}
//		while(index < high && ptr2 < middle) {
//			array[index++] = ptr2++;
//		}
		int copySize = high - low;
		int copyMiddle = middle - low;
		int[] copy = new int[copySize];
		System.arraycopy(array, low, copy, 0, copy.length);
		int p = 0;
		int q = copyMiddle;
		for (int i = low; i < high; ++i) {
		    if (q >= copySize || (p < copyMiddle && copy[p] < copy[q])) {
			array[i] = copy[p++];
		    }
		    else {
			array[i] = copy[q++];
		    }
		}
	}

}

public class ForkJoinExample2 {
	
	public static void main(String[] args) {
		
		int array[] = { 7, 8, 2, 3, 1, 5, 4, 9, 0, 6}; 
		MergeSort mergeSort = new MergeSort(array, 0 , array.length);
		ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
		forkJoinPool.invoke(mergeSort);
		System.out.println(""+ Arrays.toString(array));
	}
}
