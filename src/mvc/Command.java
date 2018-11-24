package mvc;

abstract public class Command {
	protected Model model;
	private Memento memento;
	protected boolean undoable;
	
	public void execute() {
		if (undoable) {
			memento = model.makeMemento();
		}
	}
	
	public void undo() {
		if (undoable) {
			model.accept(memento);
			model.changed();
		}
	}
	
	public boolean isUndoable() {
		return undoable;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
}
