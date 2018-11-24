package bc;

import mvc.Command;
import mvc.Utilities;

public class SetLength extends Command {
private Double newLength = null;
	
	public SetLength() {
		this.undoable = true;
	}
	
	public SetLength(Double length) {
		this.undoable = true;
		this.newLength = length; 
	}
	
	public void execute() {

		if (this.newLength == null) {
			try {
				this.newLength = Double.valueOf(Utilities.askUser("Enter the new Length"));
				if (this.newLength <= 0) {
					Utilities.error("Length must be a positive number");
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
			((Brick)model).setLength(this.newLength);
		}
	}
}
