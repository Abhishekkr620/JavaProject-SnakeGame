package snakegame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener{
	
	private Image apple;
	private Image dot;
	private Image head;
	
	private final int ALL_DOTS = 1600;
	private final int DOT_SIZE = 10;
	private final int RANDOM_POSITION = 27;
	
	private int apple_x;
	private int apple_y;
	
	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];
	
	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	
	private boolean inGame = true;
	
	private int dots;
	private int score;
	private Timer timer;
	
	Board(){
		addKeyListener(new TAdapter());
		
		
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(400,400));
		setFocusable(true);
		
		loadImages();
		initGame();
	}
	
	public void loadImages() {
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
		apple = i1.getImage();
		
		ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
		dot = i2.getImage();
		
		ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
		head = i3.getImage();
	}
	
	public void initGame() {
		dots = 3;
		score = 0;
		
		for(int i=0;i<dots;i++) {
			y[i] = 50;
			x[i] = 50 - i*DOT_SIZE;
		}
		
		locateApple();
		
		timer = new Timer(100,this);
		timer.start();
	}
	
	public void locateApple() {
		int r = (int)(Math.random() *RANDOM_POSITION);
		apple_x = r*DOT_SIZE;
		
		r = (int)(Math.random()*RANDOM_POSITION);
		apple_y = r*DOT_SIZE;
	}
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if(inGame) {
			g.drawImage(apple, apple_x, apple_y, this);
			
			for(int i=0;i<dots;i++) {
				if(i==0) {
					g.drawImage(head, x[i], y[i],this);
				}else {
					g.drawImage(dot, x[i], y[i],this);
				}
			}
			
			Toolkit.getDefaultToolkit().sync();
		}else {
			gameOver(g);
		}
		
	}
	
	public void gameOver(Graphics g) {
		String msg = "Game Over !";
		String scoreMsg = "Your score "+score;
		
		Font font = new Font("SAN_SERIF",Font.BOLD,14);
		FontMetrics metrices = getFontMetrics(font);
		
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(msg,((400- metrices.stringWidth(msg)) /2), 200);
		g.drawString(scoreMsg,((400- metrices.stringWidth(scoreMsg)) /2), 220);
	}
	
	
	public void move() {
		for(int i=dots;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		if (leftDirection) {
			x[0] = x[0] - DOT_SIZE;
		}
		if (rightDirection) {
			x[0] = x[0] + DOT_SIZE;
		}
		if (upDirection) {
			y[0] = y[0] - DOT_SIZE;
		}
		if (downDirection) {
			y[0] = y[0] + DOT_SIZE;
		}
		
	}
	
	
	public void checkApple() {
		if ((x[0]==apple_x) && (y[0]==apple_y)){
			dots++;
			score += 10;
			locateApple();
		}
		
	}
	
	public void checkCollision() {
		for(int i=dots;i>0;i--) {
			if((i>4) && (x[0]==x[i]) && (y[0]==y[i])) {
				inGame = false;
			}
		}
		if (x[0]>=400) {
			inGame = false;
		}
		
		
		if (y[0]>=400) {
			inGame = false;
		}
		
		
		if (x[0]<0) {
			inGame = false;
		}
		
		
		if (y[0]<0) {
			inGame = false;
		}
		
		if(!inGame) {
			timer.stop();
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (inGame) {
			
			checkApple();
			checkCollision();
			move();
		}
		
		repaint();
	}
	
	
	public class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed (KeyEvent e) {
			int key = e.getKeyCode();
			
			if (key == KeyEvent.VK_LEFT && (!rightDirection)) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}
			
			if (key == KeyEvent.VK_RIGHT && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}
			
			if (key == KeyEvent.VK_UP && (!downDirection)) {
				upDirection = true;
				leftDirection = false;
				rightDirection = false;
			}
			
			if (key == KeyEvent.VK_DOWN && (!upDirection)) {
				downDirection = true;
				leftDirection = false;
				rightDirection = false;
			}
		}
	}

} 