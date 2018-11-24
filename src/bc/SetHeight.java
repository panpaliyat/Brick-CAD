package bc;

import mvc.Command;
import mvc.Utilities;

public class SetHeight extends Command {
	private Double newHeight = null;
	
	public SetHeight() {
		this.undoable = true;
	}
	
	public SetHeight(Double height) {
		this.undoable = true;
		this.newHeight = height;
	}
	
	public void execute() {

		if (newHeight == null) {
			try {
				newHeight = Double.valueOf(Utilities.askUser("Enter the new Height"));
				if (newHeight <= 0) {
					Utilities.error("Height must be a positive number");
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
			((Brick)model).setHeight(newHeight);
		}
	}
}
