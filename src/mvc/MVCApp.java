package mvc;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;


public class MVCApp extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JDesktopPane desktop;
	private AppFactory factory;
	private Model model;
	private CommandProcessor commandProcessor;

	public MVCApp(AppFactory factory) {

		this.factory = factory;
		this.model = factory.makeModel();
		this.commandProcessor = CommandProcessor.makeCommandProcessor();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultLookAndFeelDecorated(true);
		desktop = new JDesktopPane();

		int inset = 50;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(inset, inset,screenSize.width  - inset*2,screenSize.height - inset*2);

		showDefaultView();
		
		setJMenuBar(createMenuBar());
		//Make dragging a little faster but perhaps uglier.
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

	}

	protected JMenuBar createMenuBar() {
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;

		menu = Utilities.makeMenu("&File", new String[] {"&NNew", "&OOpen", "&SSave", "SaveAs", "Quit"}, this);
		menuBar.add(menu);
		
		List<String> factoryCommands = factory.getCommands();
		factoryCommands.add("&ZUndo");
		factoryCommands.add("&YRedo");
		
		String[] editCommands = factoryCommands.toArray(new String[factoryCommands.size()]);
		
		menu = Utilities.makeMenu("&Edit", editCommands, new EditHandler());
		menuBar.add(menu);
		menu = Utilities.makeMenu("&View", factory.getViews(), new ViewHandler());
		menuBar.add(menu);
		menu = Utilities.makeMenu("Help", new String[] {"Help", "About"}, this);
		menuBar.add(menu);

		return menuBar;
	}

	public void actionPerformed(ActionEvent e){
    	String cmmd = e.getActionCommand();
    	
    	if (cmmd.equals("Save")) {
    		if (model.hasUnsavedChanges())	{
    			Utilities.save(model);
    		}
    		return;
    	
    	} else if (cmmd.equals("SaveAs")) { 
    		
    		Utilities.saveAs(model);
    		
    	} else if (cmmd.equals("Open")) {
    		
    		Utilities.saveChanges(model);
    		Model m = Utilities.open(model);
    		if (m != null) {
    			this.model = m;
    			showDefaultView();
    		}
    		
    	} else if (cmmd.equals("New")) {
    		
    		if (model != null)	Utilities.saveChanges(model);
    		model = factory.makeModel();
    		showDefaultView();
    		
    	} else if (cmmd.equals("Quit")) {
    		
    		Utilities.saveChanges(model);
    		System.exit(1);
    		
    	} else if (cmmd.equals("Help")) {
    		Utilities.informUser(factory.getHelp());
    	} else if (cmmd.equals("About")) {
    		Utilities.informUser(factory.about());
    	} else {
    		Utilities.error("Unrecognized command: " + cmmd);
    	}
    }

	// This class will handle all the view related functionalities
	class ViewHandler implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String type = e.getActionCommand();
			View view = factory.makeView(type);
			view.setModel(model);
			ViewFrame vf = new ViewFrame(view);
			
			vf.setSize(200, 100);
			vf.setTitle(type);
			
			if(view.getPackable())
				vf.pack();
			
			
			vf.setVisible(true);
			desktop.add(vf);
			try {
				vf.setSelected(true);
			} catch (java.beans.PropertyVetoException ex) {}
		}
	}

    class EditHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e){
        	// make a command and ask command processor to execute it
    		String cmmd = e.getActionCommand();
    		if (cmmd.equals("Undo")) {
        		commandProcessor.undo();
        	} else if (cmmd.equals("Redo")) {
        		commandProcessor.redo();
        	} else {
        		Command c = factory.makeCommand(cmmd);
        		c.setModel(model);
        		commandProcessor.execute(c);
        	}
    	}
    }

    public static void run(AppFactory factory) {
    	try {
    		MVCApp app = new MVCApp(factory);
    		app.setSize(800,600);
			app.setTitle(factory.getTitle());
			app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			app.setVisible(true);
    	} catch(Exception e) {
    		Utilities.error("" + e);
    	}

    }
    
    private void showDefaultView() {
    	
    	desktop.removeAll();
    	View DefaultView  = factory.makeView(factory.getViews()[0]);
		DefaultView.setModel(model);
		ViewFrame vf = new ViewFrame(DefaultView);
		
		vf.setSize(200, 100);
		vf.setTitle(factory.getViews()[0]);
		
		if(DefaultView.getPackable())
			vf.pack();
		vf.setVisible(true);
		desktop.add(vf);
		
		try {
			vf.setSelected(true);
		} catch (java.beans.PropertyVetoException ex) {}
		setContentPane(desktop);
    }
}
