package assignment5;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/**
 * A5Driver.java
 * @author Allen Wang (aw28928)
 * Top level java code for the drawable car class
 */
public class A5Driver extends JApplet {
	
	/*		// use this in order to test it without a browser
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame mainFrame = new JFrame();
				mainFrame.setTitle("EE422C Assignment 5");
				mainFrame.setResizable(false);
				mainFrame.add(new CarInterface());
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.pack();
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setVisible(true);
			}
		});
	}
	*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					JPanel app = new CarInterface();
					getContentPane().add(app, BorderLayout.CENTER);
					setSize(app.getPreferredSize());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Hello");
	}
	
	
}