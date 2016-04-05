package assignment5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * CarInterface.java
 * @author Allen Wang (aw28928)
 * Class used to model the car drawer.
 */

public class CarInterface extends JPanel implements KeyListener {
	// tells the compiler to shut up
	private static final long serialVersionUID = 3902651753080457855L;
	
	// Game logic constants
	private final int INITIAL_CAR_SPEED = 10;
	private final int MAIN_CAR_SPEED = 15;
	static final int PANEL_WIDTH = 640;
	static final int PANEL_HEIGHT = 480;
	static final int GAME_Y_LOWER_BOUND = 70;
	static final int GAME_Y_UPPER_BOUND = 400;
	static final int GAME_X_LOWER_BOUND = 0;
	static final int GAME_X_UPPER_BOUND = 640;
	private final int INITIAL_NUMBER_OF_ENEMIES = 5;
	private final int GAME_SPEED = 50;
	// Car info
	private ArrayList<Car> badCars;
	private ArrayList<Car> allCars;
	private final Color BURNTORANGE = new Color(0xBF5700);
	private final Color MAROON = new Color(0x500000);
	private Car mainCar;
	
	// Game logic variables
	private Random rnd;
	private Timer timer;
	private double score;
	private double highScore;
	private int numberOfEnemies;
	private int difficulty;
	private boolean running;
	private StopWatch stopWatch;

	/**
	 * CarInterface
	 * Default Constructor
	 */
	public CarInterface() {
		reinitializeCars();
		running = true;
		numberOfEnemies = 5;
		difficulty = 1;
		highScore = 0;
		stopWatch = new StopWatch();
		timer = new Timer(GAME_SPEED, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Car bc : badCars) {
					if (bc.dead()) {	// revive, update score
						bc.updatePosition(genX(), genY());
						bc.speedUp(rnd.nextInt(3) + 2);
						bc.revive();
						score += 10 * (1 + ((double)difficulty / 20));
						if (score > highScore) {		// should keep updating on the high score
							highScore = score;
						}
					}
					bc.moveLeft();
					if (Math.abs(bc.getX() - mainCar.getX()) < 60 &&
							Math.abs(bc.getY() - mainCar.getY()) < 25) {
						running = false;
						repaint();
					}
				}
				for (Car c : allCars) {
					repaint();
				}
				if (!running) {
					timer.stop();
				}
				
			}
		});
		timer.start();
		addKeyListener(this);
		setFocusable(true);
	}
	
	/** genX()
	 * @return generated X value
	 */
	private int genX() {
		final int[] possibleX = { 700, 760, 800, 820, 860, 900};
		return possibleX[rnd.nextInt(possibleX.length)];
	}
	
	/** genY()
	 * @return generated Y value
	 */
	private int genY() {
		return rnd.nextInt(GAME_Y_UPPER_BOUND - GAME_Y_LOWER_BOUND) + GAME_Y_LOWER_BOUND;
	}
	
	/** reinitializeCars
	 * 
	 */
	private void reinitializeCars() {
		allCars = new ArrayList<Car>();
		badCars = new ArrayList<Car>();
		mainCar = new Car(50, 80, 1, BURNTORANGE, MAIN_CAR_SPEED);
		allCars.add(mainCar);
		
		rnd = new Random(Double.doubleToLongBits(Math.random()));	// very random seed
		
		numberOfEnemies = INITIAL_NUMBER_OF_ENEMIES + difficulty * 2;
		for (int i = 0; i < numberOfEnemies; ++i) {
			Car badCar = new Car(genX(), genY(), i + 2, MAROON, INITIAL_CAR_SPEED); 
			allCars.add(badCar); 
			badCars.add(badCar);
		}
		score = 0;
	}
	
	/** restart
	 * 
	 */
	private void restart() {
		reinitializeCars();
		if (!running) {
			running = true;
			timer.start();
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		for (Car c : allCars) {
			c.drawCar(g);
		}
		g2.setColor(Color.BLACK);
		
		String scoreString = String.format("Score: %1$,.0f", score);
		String highScoreString = String.format("High Score: %1$.0f", highScore);
		g2.drawString(scoreString, 500, 30);
		g2.drawString("Difficulty: " + difficulty, 50, 30);
		g2.drawString("Arrows change directions", 50, 430);
		g2.drawString("Numbers (0-9) change difficulty", 50, 450);
		g2.drawString("R resets the game", 400, 430);
		g2.drawString(highScoreString, 400, 450);
	}

	/** handleKeyPress
	 * 
	 * @param e
	 */
	private void handleKeyPress(KeyEvent e) {
		if (running) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				mainCar.moveDown();
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				mainCar.moveUp();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				mainCar.moveLeft();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				mainCar.moveRight();
			}
			repaint();
 		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		handleKeyPress(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		handleKeyPress(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == 'r') {
			restart();
		} else if (e.getKeyChar() == '1') {
			difficulty = 1;
			restart();
		} else if (e.getKeyChar() == '2') {
			difficulty = 2;
			restart();
		} else if (e.getKeyChar() == '3') {
			difficulty = 3;
			restart();
		} else if (e.getKeyChar() == '4') {
			difficulty = 4;
			restart();
		} else if (e.getKeyChar() == '5') {
			difficulty = 5;
			restart();
		} else if (e.getKeyChar() == '6') {
			difficulty = 6;
			restart();
		} else if (e.getKeyChar() == '7') {
			difficulty = 7;
			restart();
		} else if (e.getKeyChar() == '8') {
			difficulty = 8;
			restart();
		} else if (e.getKeyChar() == '9') {
			difficulty = 9;
			restart();
		} else if (e.getKeyChar() == '0') {
			difficulty = 10;
			restart();
		}
	}	// do nothing
}
