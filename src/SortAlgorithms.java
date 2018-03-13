import java.util.concurrent.atomic.AtomicBoolean;

public interface SortAlgorithms {

    /**
     * performs bubble sort on a specified array
     * @param inProgress sort progress indicator
     */
    public void bubbleSort(final AtomicBoolean inProgress);

    /**
     * performs selection sort on a specified array
     * @param inProgress sort progress indicator
     */
    public void selectionSort(final AtomicBoolean inProgress);

    /**
     * performs insertion sort on a specified array
     * @param inProgress sort progress indicator
     */
    public void insertionSort(final AtomicBoolean inProgress);

    /**
     * performs quick sort on a specified array
     * @param inProgress sort progress indicator
     */
    public void quickSort(final AtomicBoolean inProgress);

    /**
     * performs merge sort on a specified array
     * @param inProgress sort progress indicator
     */
    public void mergeSort(final AtomicBoolean inProgress);

}
