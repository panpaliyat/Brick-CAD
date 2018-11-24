package mvc;

import java.util.Observer;
import javax.swing.JPanel;

abstract public class View extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	protected Model model;
	private Boolean packable;
	
	public View(Model model) {
		super();
		this.model = model;
	}

	public View() {
		this.packable = false;
	}
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
		model.addObserver(this);
		update(model, null);
	}

	public Boolean getPackable() {
		return packable;
	}

	public void setPackable(Boolean packable) {
		this.packable = packable;
	}
}