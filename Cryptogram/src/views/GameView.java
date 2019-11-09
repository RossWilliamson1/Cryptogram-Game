package views;

import java.awt.Dimension;

import javax.swing.JFrame;


@SuppressWarnings("serial")
public class GameView extends JFrame {

	public GameView() {
		super("Cryptogram");
		
		this.setSize(new Dimension(500, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
