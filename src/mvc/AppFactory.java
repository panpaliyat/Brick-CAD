package mvc;

import java.util.List;

public interface AppFactory {
	public Model makeModel();
	public View makeView(String type);
	public Command makeCommand(String type);
	public String[] getViews();
	public List<String> getCommands();
	public String getTitle();
	public String getHelp();
	public String about();
}
