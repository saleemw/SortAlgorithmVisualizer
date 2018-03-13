import javax.swing.JProgressBar;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author Saleem Wadood
 * @Date: 03/12/2018
 * This class implements the following sorting algorithms:
 * N*Log(N) sort:   Quick Sort and Merge Sort
 * N^2 sort:   bubble sort, selection sort and insertion sort
 * The main purpose of the writeup is to help users visualize
 * how each algorithm manipulate data while sorting it.
 */
public class SortAlgorithmImpl implements SortAlgorithms{
    private final int[] intArray;
    private final JProgressBar[] progressBars;

    final ArrayList<String> callStack = new ArrayList<String>();
    int callCount;
    int[] helper = null;


    /**
     *
     * @param array this is the array that would be sorted.
     * @param bars  bars that collectively would indicate the progress of sort
     */
    public SortAlgorithmImpl(final int[] array, final JProgressBar[] bars) {
        intArray = array;
        progressBars = bars;
    }

    /**
     * performs bubble sort on a specified array
     * @param inProgress sort progress indicator
     */
    @Override
    public void bubbleSort(final AtomicBoolean inProgress) {
        inProgress.set(true);
        final Timer timer = new Timer();
        TimerTask task = new TimerTask(){
            public void run(){
                if(!isSorted()){
                    for (int i = 1; i < intArray.length; i++){
                        if(intArray[i-1] >= intArray[i]){
                            int tmp = intArray[i-1];
                            intArray[i-1] = intArray[i];
                            intArray[i] = tmp;
                        }
                    }
                    updateBars();
                }else{
                    updateBars();
                    inProgress.set(false);
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, 0, 2);
        return;
    }

    /**
     * performs selection sort on a specified array
     * @param inProgress sort progress indicator
     */
    @Override
    public void selectionSort(AtomicBoolean inProgress) {
        inProgress.set(true);
        Timer timer = new Timer();
        final TimerTask task = new TimerTask(){
            int right_index_lim = intArray.length;
            public void run(){
                if(right_index_lim >= 1){
                    int maxIdx = find_max_index(0, right_index_lim);
                    int temp = intArray[maxIdx];
                    intArray[maxIdx] = intArray[right_index_lim - 1];
                    intArray[right_index_lim - 1] = temp;
                    right_index_lim--;
                    updateBars();
                }else{
                    isSorted();
                    updateBars();
                    timer.cancel();
                    inProgress.set(false);
                }
            }
        };
        timer.schedule(task, 0, 2);
        return;
    }

    /**
     * performs insertion sort on a specified array
     * @param inProgress sort progress indicator
     */
    @Override
    public void insertionSort(AtomicBoolean inProgress) {
        inProgress.set(true);
        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
            int jdx = 1;
            @Override
            public void run() {
                updateBars();

                if(jdx < intArray.length){
                    int key = intArray[jdx];
                    int i;
                    for(i = jdx - 1; (i >= 0) && (intArray[ i ] > key); i--)
                    {
                        intArray[ i+1 ] = intArray[ i ];
                    }
                    intArray[ i+1 ] = key;
                    jdx++;
                }
                if(isSorted()){
                    updateBars();
                    timer.cancel();
                    inProgress.set(false);
                }
            }

        };
        timer.schedule(task, 0, 2);
        return;
    }

    /**
     * performs quick sort on a specified array
     * @param inProgress sort progress indicator
     */
    @Override
    public void quickSort(AtomicBoolean inProgress) {
        inProgress.set(true);
        Timer timer = new Timer();
        callCount = 0;
        callStack.clear();
        TimerTask task = new TimerTask(){
            @Override
            public void run() {

                if(isSorted() == false){
                    if(callStack.size() > 0){
                        String call = new String(callStack.remove(0));
                        //make call
                        String[] hl = call.split(",");
                        if(hl.length == 2){
                            int low = Integer.parseInt(hl[0]);
                            int high = Integer.parseInt(hl[1]);
                            quickSort(low, high);
                        }
                    }
                    updateBars();
                }else{
                    updateBars();
                    timer.cancel();
                    inProgress.set(false);
                }

            }
        };
        quickSort(0, intArray.length - 1);
        timer.schedule(task, 0, 120);
        return;
    }

    /**
     * performs merge sort on a specified array
     * @param inProgress sort progress indicator
     */
    @Override
    public void mergeSort(AtomicBoolean inProgress) {
        inProgress.set(true);
        Timer timer = new Timer();
        helper = new int[intArray.length];
        callStack.clear();
        final TimerTask task = new TimerTask(){
            @Override
            public synchronized void run() {
                if(isSorted() == false){
                    if (callStack.size() > 10){
                        while(callStack.size() > 10){
                            String call = new String(callStack.get(0));
                            callStack.remove(0);
                            String[] params = call.split(",");
                            if(params.length == 3){
                                int low = Integer.parseInt(params[0]);
                                int mid = Integer.parseInt(params[1]);
                                int hi = Integer.parseInt(params[2]);
                                merge(low,mid,hi);
                            }
                        }
                    }else{
                        String call = new String(callStack.get(0));
                        callStack.remove(0);
                        String[] params = call.split(",");
                        if(params.length == 3){
                            int low = Integer.parseInt(params[0]);
                            int mid = Integer.parseInt(params[1]);
                            int hi = Integer.parseInt(params[2]);
                            merge(low,mid,hi);
                        }
                    }
                    updateBars();
                }else{
                    updateBars();
                    timer.cancel();
                    inProgress.set(false);
                }
            }
        };
        mergeSort(0, intArray.length - 1);
        timer.schedule(task, 1000, 1000);
        return;
    }

    private void mergeSort(int low, int high) {
        if (low < high) {
            int middle = low + (high - low) / 2;
            mergeSort(low, middle);
            mergeSort(middle + 1, high);
            callStack.add(low + "," + middle + "," + high);
        }
    }

    private void merge(int begin, int mid, int end) {

        for (int i = begin; i <= end; i++) {
            helper[i] = intArray[i];
        }

        int i = begin;
        int j = mid + 1;
        int k = begin;

        while (i <= mid && j <= end) {
            if (helper[i] <= helper[j]) {
                intArray[k] = helper[i];
                i++;
            } else {
                intArray[k] = helper[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            intArray[k] = helper[i];
            k++;
            i++;
        }

    }

    private void quickSort(int low, int high) {
        callCount++;
        int i = low, j = high;
        int pivot = intArray[low + (high-low)/2];

        while (i <= j) {
            while (intArray[i] < pivot) {
                i++;
            }
            while (intArray[j] > pivot) {
                j--;
            }

            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }

        if(callCount < 50){
            if (low < j){
                callStack.add(low + "," + j);
            }
            if (i < high){
                callStack.add(i + "," + high);
            }
        }else{
            if (low < j){
                quickSort(low, j);
            }
            if (i < high){
                quickSort(i, high);
            }
        }
    }


    private void updateBars(){
        int segment = intArray.length / 100;
        for(int d = 0; d < progressBars.length; d++){

            if(d == 0){
                progressBars[d].setValue(intArray[0]);}
            else if(d == (progressBars.length - 1)){
                progressBars[d].setValue(intArray[intArray.length -1]);

            }else{
                int idx = d*(segment);
                if(idx == 0)
                    progressBars[d].setValue(intArray[idx]);
                else
                    progressBars[d].setValue(intArray[idx - 1]);
            }
        }
    }

    private boolean isSorted(){
        for(int i = 1; i < intArray.length; i++){
            if(intArray[i - 1] > intArray[i])
                return false;
        }
        JOptionPane.showMessageDialog(null, "Sort Job Completed");
        return true;
    }

    private int find_max_index(int beg, int end){
        int idx = 0;
        int max = Integer.MIN_VALUE;
        for(int i = beg; i < end; i++){
            if(max <= intArray[i]){
                idx = i;
                max = intArray[i];
            }
        }
        return idx;
    }

    private void swap(final int i, final int j){
        int temp = intArray[i];
        intArray[i] = intArray[j];
        intArray[j] = temp;
    }
}
