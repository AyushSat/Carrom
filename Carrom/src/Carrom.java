import java.awt.Dimension;
import javax.swing.JFrame;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**Carrom is the main class of the project. When run, it will launch the actual game
 * in a window. 
 * 
 * @author Calix Tang, Akshat Jain, and Ayush Satyavarpu
 *
 */
public class Carrom {
	public static void main(String[] args) {
		
		Menu menu = new Menu(1000, 1000);
		PApplet.runSketch(new String[]{"Menu"}, menu);
		
		PSurfaceAWT menusurf = (PSurfaceAWT) menu.getSurface();
		PSurfaceAWT.SmoothCanvas menucanvas = (PSurfaceAWT.SmoothCanvas) menusurf.getNative();
		JFrame menuwindow = (JFrame)menucanvas.getFrame();
		
		//window is 1000x1000 permanently
		menuwindow.setSize(1000,1000);
		menuwindow.setMinimumSize(new Dimension(1000,1000));
		menuwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuwindow.setResizable(false);//a stretch is to change this
				
		//make window visible
		menuwindow.setVisible(true);
		menucanvas.requestFocus();
	}
}
