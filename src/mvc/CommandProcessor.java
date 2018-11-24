package mvc;
import java.util.Stack;

public class CommandProcessor {
	
	private Stack<Command> undoStack = new Stack<Command>();
	private Stack<Command> redoStack = new Stack<Command>();
	
	private static CommandProcessor theCommandProcessor = null;
	
	public static CommandProcessor makeCommandProcessor() {
		if(theCommandProcessor == null) theCommandProcessor = new CommandProcessor();
		return theCommandProcessor;
	}
	
	public void execute(Command cmmd) {
		try {
			cmmd.execute();
		} catch (Exception e) {
			return;
		}
		if (cmmd.isUndoable()) {
			redoStack.clear();
			undoStack.push(cmmd);
		}
	}
	
	public void redo() {
		
		if(!redoStack.isEmpty()) {
			Command cmmd = redoStack.pop();
			cmmd.execute();
			undoStack.push(cmmd);
		} else {
		}
	}
	
	public void undo() {
		
		if(!undoStack.isEmpty()) {
			Command cmmd = undoStack.pop();
			cmmd.undo();
			redoStack.push(cmmd);
		} else {
		}
	}
}
