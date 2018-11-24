package bc;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;

import mvc.View;

public class TopView extends View {

	private static final long serialVersionUID = 1L;

	public TopView(Brick b) {
		super(b);
	}

	public TopView() {
		super();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!(model instanceof Brick)) {
			//throw new Exception("Model expected to be a brick");
		} else {
			Brick b = (Brick) model;
			g.setColor(Color.red);
			g.fillRect(5, 5, b.getLength().intValue(), b.getWidth().intValue());
		}
	}
}
