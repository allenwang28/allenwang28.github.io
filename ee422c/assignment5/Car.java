package assignment5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
* Car.java
* @author Allen Wang (aw28928)
* Car class used to model a single car
*/
class Car {
	private Rectangle body;
	private Ellipse2D.Double frontTire;
	private Ellipse2D.Double innerFrontTire;
	private Ellipse2D.Double rearTire;
	private Ellipse2D.Double innerRearTire;
	private Color thisColor;
	private Point2D.Double r1;
	private Point2D.Double r2;
	private Point2D.Double r3;
	private Point2D.Double r4;
	private Line2D.Double frontWindShield;
	private Line2D.Double roofTop;
	private Line2D.Double rearWindShield;
	private int x;
	private int y;
	private int id;
	private int speed;
	private final int carHeight = 10;
	private final int carWidth = 60;
	private final int tireHeight = 10;
	private final int tireWidth = 10;
	private boolean dead;
	
	/**
	 * Default constructor for the car
	 * @param x
	 * @param y
	 */
	public Car(int x, int y, int id, Color c, int speed) {
		this.id = id;
		updatePosition(x, y);
		this.thisColor = c;
		this.speed = speed;
		this.dead = false;
	}
	
	/** updatePosition
	 * 
	 * @param x
	 * @param y
	 */
	public void updatePosition(int x, int y) {
		this.x = x;
		this.y = y;
		this.body = new Rectangle(x, y, carWidth, carHeight);
		this.frontTire = new Ellipse2D.Double(x + 10, y + 10, tireWidth, tireHeight);
		this.rearTire = new Ellipse2D.Double(x + 40, y + 10, tireWidth, tireHeight);
		this.innerFrontTire = new Ellipse2D.Double(x + 12,  y + 12, tireWidth / 2, tireHeight / 2);
		this.innerRearTire = new Ellipse2D.Double(x + 42,  y + 12, tireWidth / 2, tireHeight / 2);
		this.r1 = new Point2D.Double(x + 10, y);
		this.r2 = new Point2D.Double(x + 20, y - 10);
		this.r3 = new Point2D.Double(x + 40, y - 10);
		this.r4 = new Point2D.Double(x + 50, y);
		
		this.frontWindShield = new Line2D.Double(r1, r2);
		this.roofTop = new Line2D.Double(r2, r3);
		this.rearWindShield = new Line2D.Double(r3, r4);
	}
	
	/** setSpeed()
	 * 
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/** speedUp
	 * 
	 * @param speed
	 */
	public void speedUp(int speed) {
	    if (this.speed + speed < CarInterface.GAME_X_UPPER_BOUND - CarInterface.GAME_X_LOWER_BOUND) {
			  this.speed += speed;
	    }
	}
	
	/** dead
	 * 
	 * @return dead
	 */
	public boolean dead() {
		return this.dead;
	}
	
	/** revive
	 * 
	 */
	public void revive() {
		this.dead = false;
	}
	
	/** moveLeft
	 * 
	 */
	public void moveLeft() {
		if (x - speed < CarInterface.GAME_X_LOWER_BOUND) {
			this.dead = true;
		} else {
			updatePosition(x - speed, y);
		}
	}
	
	/** moveRight
	 * 
	 */
	public void moveRight() {
		if (x + speed < CarInterface.GAME_X_UPPER_BOUND) {
			updatePosition(x + speed, y);
		}
	}
	
	/** moveUp
	 * 
	 */
	public void moveUp() {
		if (y - speed >= CarInterface.GAME_Y_LOWER_BOUND) {
			updatePosition(x, y - speed);
		}
	}

	/** moveDown
	 * 
	 */
	public void moveDown() {
		if (y + speed <= CarInterface.GAME_Y_UPPER_BOUND) {
			updatePosition(x, y + speed);
		}
	}

	/** getX
	 * 
	 * @return x position
	 */
	public int getX() {
		return x;
	}
	
	/** getY
	 * 
	 * @return y position
	 */
	public int getY() {
		return y;
	}
	
	/** getID
	 * 
	 * @return id
	 */
	public int getID() {
		return id;
	}

	/** drawCar
	 * 
	 * @param g
	 */
	public void drawCar(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.draw(body);
		g2.draw(frontTire);
		g2.draw(rearTire);
		g2.draw(innerFrontTire);
		g2.draw(innerRearTire);
		g2.draw(frontWindShield);
		g2.draw(roofTop);
		g2.draw(rearWindShield);
		g2.setColor(this.thisColor);
		g2.fill(body);
		g2.setColor(Color.BLACK);
		g2.fill(frontTire);
		g2.fill(rearTire);
		g2.setColor(Color.WHITE);
		g2.fill(innerRearTire);
		g2.fill(innerFrontTire);
		g2.drawString(Integer.toString(id), this.x + 25, this.y + 10);
	}
	
}
