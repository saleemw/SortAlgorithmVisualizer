import javax.swing.*;
import java.awt.*;

/**
 * @author Saleem Wadood
 * @version 1.0
 * @Date 03/12/2018
 * Class that is responsible for displaying application window and Gui components
 * for visual sort
 */
public class GuiFrame {
	static GuiComponents cc;
	static JFrame frame = null;
	public GuiFrame(){
	}

	public static void createAndShowGUI(){
		cc = new GuiComponents();
		frame = new JFrame("Visual Sort");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setContentPane(cc);
		frame.setSize(new Dimension(1200,500));
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public static void main(String[] arg0){
		final Runnable rn = () -> createAndShowGUI();
		javax.swing.SwingUtilities.invokeLater(rn);
	}
}
