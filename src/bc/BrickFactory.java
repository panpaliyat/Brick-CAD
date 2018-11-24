package bc;

import java.util.ArrayList;
import java.util.List;

import mvc.AppFactory;
import mvc.Command;
import mvc.Model;
import mvc.View;

public class BrickFactory implements AppFactory {

	private String title;
	
	public BrickFactory() {
		this.title = "Brick CAD";
	}
	
	@Override
	public Model makeModel() {
		return new Brick();
	}

	@Override
	public Command makeCommand(String type) {
		if (type.equals("Set Height"))
			return new SetHeight();
		else if (type.equals("Set Width"))
			return new SetWidth();
		else if (type.equals("Set Length"))
			return new SetLength();
		else	return null;
	}

	@Override
	public View makeView(String type) {
		// This creates the view based on the type of view
		if (type.equals("Side View"))
			return new SideView();
		else if (type.equals("Front View"))
			return new FrontView();
		else if (type.equals("Top View"))
			return new TopView();
		else if (type.equals("Dimension View"))
			return new DimensionView();
		else	return null;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String[] getViews() {
		return new String[] {"Front View", "Top View", "Side View", "Dimension View"};
	}

	@Override
	public List<String> getCommands() {
		List<String> commands = new ArrayList<String>();
		commands.add("Set Height");
		commands.add("Set Width");
		commands.add("Set Length");
		return commands;
	}

	@Override
	public String getHelp() {
		String help;
		help = "Brick Dimensions can be set by using :\n";
		help += "Set Height, Set Length, Set Width or Dimension View\n";
		help += "Brick can be viewed in :\n";
		help += "Front View, Side View, Top View, Dimension View";
		return help;
	}

	@Override
	public String about() {
		String about = "Brick CAD by Tushar Panpaliya";
		return about;
	}
}
