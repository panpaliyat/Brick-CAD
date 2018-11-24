package bc;

import mvc.Command;
import mvc.Utilities;

public class SetWidth extends Command {
private Double newWidth = null;
	
	public SetWidth() {
		this.undoable = true;
	}
	
	public SetWidth(Double width) {
		this.undoable = true;
		this.newWidth = width;
	}
	
	public void execute() {

		if (newWidth == null) {
			try {
				newWidth = Double.valueOf(Utilities.askUser("Enter the new Width"));
				if (newWidth <= 0) {
					Utilities.error("Width must be a positive number");
					return;
				}
			} catch(NumberFormatException e) {
				Utilities.error("Please enter a valid number");
				throw(e);
			} catch (NullPointerException e) {
				throw(e);
			}
		}
		
		if (!(model instanceof Brick)) {
			Utilities.error("Brick not found !!");
			return;
		} else {
			super.execute();
			((Brick)model).setWidth(newWidth);
		}
	}
}
