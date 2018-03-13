import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Saleem Wadood
 * @Date: 03/12/2018
 * display GUI which would interact with the sort algorithm
 */
public class GuiComponents extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	static int[] intArray;
	JProgressBar[] progressBars = null;
	AtomicBoolean inProgress = new AtomicBoolean(false);

	JButton shuffle = null;
	JButton bubbleSort = null;
	JButton selectionSort = null;
	JButton quickSort = null;
	JButton mergeSort = null;
	JButton ins_sort = null;
	JButton settings = null;
	Random randomGenerator = null;

	final int MAX_VALUE = 1000000;
	final int LIST_SIZE = 8000;
	/****************************/

	
	public GuiComponents(){
		randomGenerator = new Random();
		randomGenerator.setSeed(System.currentTimeMillis());
		shuffle = new JButton("Shuffle");
		shuffle.addActionListener(this);		
		bubbleSort = new JButton("Bubble Sort");
		bubbleSort.addActionListener(this);
		selectionSort = new JButton("Selection Sort");
		selectionSort.addActionListener(this);
		quickSort = new JButton("Quick Sort");
		quickSort.addActionListener(this);
		mergeSort = new JButton("Merge Sort");
		mergeSort.addActionListener(this);
		ins_sort = new JButton("Insertion Sort");
		ins_sort.addActionListener(this);
		settings = new JButton("Info?");
		settings.addActionListener(this);

		BorderLayout MainComLayout = new BorderLayout();
		this.setLayout(MainComLayout);
		
		FlowLayout buttonLayout = new FlowLayout();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(buttonLayout);
		buttonPanel.add(settings);
		buttonPanel.add(shuffle);
		buttonPanel.add(bubbleSort);
		buttonPanel.add(selectionSort);
		buttonPanel.add(ins_sort);
		buttonPanel.add(quickSort);
		buttonPanel.add(mergeSort);
		
		this.add(buttonPanel, BorderLayout.NORTH);
		
		intArray = new int[LIST_SIZE];
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for(int i = 0; i < intArray.length; i++){
			intArray[i] = randomGenerator.nextInt(MAX_VALUE);
			if(max < intArray[i])
				max = intArray[i];
			if(min > intArray[i])
				min = intArray[i];
		}		
		
		JPanel barPanel = new JPanel();
		progressBars = new JProgressBar[100];
		for(int i = 0; i < progressBars.length; i++){
			progressBars[i] = new JProgressBar();
			progressBars[i].setPreferredSize(new Dimension(6,400));
			progressBars[i].setOrientation(JProgressBar.VERTICAL);
			progressBars[i].setMaximum(max + 1000);
			progressBars[i].setMinimum(min);
			progressBars[i].setBackground(Color.white);
			progressBars[i].setForeground(Color.blue);
			barPanel.add(progressBars[i]);
		}
		this.add(barPanel, BorderLayout.CENTER);			
		updateBars();
	}

	/**
	 * updates the vertical progress bars placement within the horizontal frame
	 */
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


	/**
	 * all button clicks are sent here
	 * @param e ActionEvent containing source of action
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		if(inProgress.get()) {
			JOptionPane.showMessageDialog(null, "Sort Job Is Still In Progress.");
			return;
		}
		if(e.getActionCommand().equalsIgnoreCase("Shuffle")){
			intArray = new int[LIST_SIZE];
			for(int i = 0; i < LIST_SIZE; i++){
				intArray[i] = randomGenerator.nextInt(MAX_VALUE);
			}
			updateBars();
		}else if (e.getActionCommand().equalsIgnoreCase("Bubble Sort")){
			final SortAlgorithmImpl sort = new SortAlgorithmImpl(intArray, progressBars);
			sort.bubbleSort(inProgress);
		}else if (e.getActionCommand().equals("Selection Sort")){
			final SortAlgorithmImpl sort = new SortAlgorithmImpl(intArray, progressBars);
			sort.selectionSort(inProgress);
	}else if(e.getActionCommand().equalsIgnoreCase("Insertion Sort")) {
			SortAlgorithmImpl sort = new SortAlgorithmImpl(intArray, progressBars);
			sort.insertionSort(inProgress);
		} else if (e.getActionCommand().equals("Quick Sort")){
			final SortAlgorithmImpl sort = new SortAlgorithmImpl(intArray, progressBars);
			sort.quickSort(inProgress);
		}else if(e.getActionCommand().equalsIgnoreCase("Merge Sort")){
			SortAlgorithmImpl sort = new SortAlgorithmImpl(intArray, progressBars);
			sort.mergeSort(inProgress);
		}else if(e.getActionCommand().contains("?")){
			JOptionPane.showMessageDialog(null,
							" * @author Saleem Wadood\n" +
							" * @Date: 03/12/2018\n" +
							" * This program implements the following sorting algorithms:\n" +
							" * N*Log(N) sort:   Quick Sort and Merge Sort\n" +
							" * N^2 sort:   bubble sort, selection sort and insertion sort\n" +
							" * The main purpose of the writeup is to help users visualize\n" +
							" * how each algorithm manipulate data while sorting it.");
		}
	}
}

