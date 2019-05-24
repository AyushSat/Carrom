import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 * PApplet for testing purposes
 * 
 * @author Akshat and Calix
 * @version 5/6/19
 */
public class Menu extends PApplet {
	private Button start;
	private Button credits;
	private Button m;
	private Button tut;
	private Button twoPlayer;
	private Button threePlayer;
	private Button fourPlayer;
	private Button back;
	private Button oneComputer;
	private Button networked;
	private int level;
	private double w;
	private double h;
	private PImage background;
	private Music music;
	private Thread t;
	private static final File song = new File("data" + File.separator + "music.wav");
	private static final Color def = new Color(1, 8, 173);
	private static final Color hov = new Color(0, 150, 203);
	private PFont font;

	public Menu(double width, double height) {
		music = new Music(song);
		t = new Thread(music);
		t.start();
		level = 0;
		start = new Button(width*.5, height*.49, width*.2, height*.04, hov, def, "Start", 50);
		tut = new Button(width*.5, height*.55, width*.2, height*.04, hov, def, "Tutorial",
				50);
		credits = new Button(width*.5, height*.61, width*.2, height*.04, hov, def, "Credits",
				50);
		m = new Button(width*.5, height*.73, width*.2, height*.04, hov, def, "Music",
				50);
		twoPlayer = new Button(width*.5, height*.49, width*.2, height*.04, hov, def, "2 players", 50);
		threePlayer = new Button(width*.5, height*.55, width*.2, height*.04, hov, def, "3 players",
				50);
		fourPlayer = new Button(width*.5, height*.61, width*.2, height*.04, hov, def, "4 players",
				50);
		back = new Button(width*.5,height*.67,width*.2,height*.04, hov, def, "Back", 50);
		oneComputer = new Button(width*.5, height*.49, width*.2, height*.04, hov, def, "1 Computer", 50);
		networked = new Button(width*.5, height*.55, width*.2, height*.04, hov, def, "Networked",
				50);
		w = width;
		h = height;
	}

	public void settings() {
		size((int) w, (int) h);
	}

	public void setup() {
		background = loadImage("data" + File.separator + "backgroundMenu.png");
		this.frameRate(60);
		image(background, 0, 0, 1000, 980);
		font = createFont("AbhayaLibre-Bold.ttf", 20);
		start.setFont(font);
		tut.setFont(font);
		credits.setFont(font);
		m.setFont(font);
		twoPlayer.setFont(font);
		threePlayer.setFont(font);
		fourPlayer.setFont(font);
		back.setFont(font);
		oneComputer.setFont(font);
		networked.setFont(font);
		
	}

	public void draw() {
		if(level==2) {
			twoPlayer.draw(this);
			threePlayer.draw(this);
			fourPlayer.draw(this);
			back.draw(this);
		}else if(level==0){
			start.draw(this);
			tut.draw(this);
			credits.draw(this);
			m.draw(this);
		}else if(level==1) {
			//networked.draw(this);
			oneComputer.draw(this);
			back.draw(this);
		}
	}

	public void mouseDragged() {

	}

	public void mousePressed() {
		if(level==2) {
			if(twoPlayer.getBoundingRectangle().contains(mouseX,mouseY)) {
				startGame(2);
				level = 0;
			}
			if(threePlayer.getBoundingRectangle().contains(mouseX,mouseY)) {
				startGame(3);
				level = 0;
			}
			if(fourPlayer.getBoundingRectangle().contains(mouseX,mouseY)) {
				startGame(4);
				level = 0;
			}
			if(back.getBoundingRectangle().contains(mouseX,mouseY)) {
				level--;
			}
			image(background, 0, 0, 1000, 980);
		}else if(level==0){
			if (start.getBoundingRectangle().contains(mouseX, mouseY))
				level++;
			if (tut.getBoundingRectangle().contains(mouseX, mouseY))
				tutorial();
			if (credits.getBoundingRectangle().contains(mouseX, mouseY)) {
				credits();
			}
			if(m.getBoundingRectangle().contains(mouseX, mouseY)) {
				m.toggleST();
				if(m.getST())
					music.pause();
				else
					music.play();
			}
			image(background, 0, 0, 1000, 980);
		}else if(level==1) {
			if(back.getBoundingRectangle().contains(mouseX,mouseY)) {
				level--;
			}
			if(oneComputer.getBoundingRectangle().contains(mouseX,mouseY)) {
				level++;
			}
//			if(networked.getBoundingRectangle().contains(mouseX,mouseY)) {
//				level++;
//			}
			image(background, 0, 0, 1000, 980);
		}
		
	}

	public void mouseMoved() {
		start.setHover(start.getBoundingRectangle().contains(mouseX, mouseY));
		tut.setHover(tut.getBoundingRectangle().contains(mouseX, mouseY));
		credits.setHover(credits.getBoundingRectangle().contains(mouseX, mouseY));
		twoPlayer.setHover(twoPlayer.getBoundingRectangle().contains(mouseX,mouseY));
		threePlayer.setHover(threePlayer.getBoundingRectangle().contains(mouseX,mouseY));
		fourPlayer.setHover(fourPlayer.getBoundingRectangle().contains(mouseX,mouseY));
		back.setHover(back.getBoundingRectangle().contains(mouseX,mouseY));
		oneComputer.setHover(oneComputer.getBoundingRectangle().contains(mouseX,mouseY));
		networked.setHover(networked.getBoundingRectangle().contains(mouseX,mouseY));
		m.setHover(m.getBoundingRectangle().contains(mouseX,mouseY));
	}

	public void keyPressed() {

	}

	private void startGame(int players) {
		GameBoard board = new GameBoard(9, 9, players);
		PApplet.runSketch(new String[] { "Carrom" }, board);

		PSurfaceAWT surf = (PSurfaceAWT) board.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();

		// window is 1000x1000 permanently
		window.setSize(1000, 1000);
		window.setMinimumSize(new Dimension(1000, 1000));
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setResizable(false);// a stretch is to change this

		// make window visible
		window.setVisible(true);
		canvas.requestFocus();
	}

	public void tutorial() {
		Tutorial tutorial = new Tutorial(width, height);
		PApplet.runSketch(new String[] { "Tutorial" }, tutorial);

		PSurfaceAWT surf = (PSurfaceAWT) tutorial.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();

		// window is 1000x1000 permanently
		window.setSize(1000, 1000);
		window.setMinimumSize(new Dimension(1000, 1000));
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setResizable(false);// a stretch is to change this

		// make window visible
		window.setVisible(true);
		canvas.requestFocus();
	}
	
	public void credits() {
		Credits credits = new Credits(width, height);
		PApplet.runSketch(new String[] { "Credits" }, credits);

		PSurfaceAWT surf = (PSurfaceAWT) credits.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();

		// window is 1000x1000 permanently
		window.setSize(1000, 1000);
		window.setMinimumSize(new Dimension(1000, 1000));
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setResizable(false);// a stretch is to change this

		// make window visible
		window.setVisible(true);
		canvas.requestFocus();
	}

}