package bc;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mvc.Command;
import mvc.CommandProcessor;
import mvc.Utilities;
import mvc.View;

public class DimensionView extends View implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JTextField lengthTextArea;
	private JTextField widthTextArea;
	private JTextField heightTextArea;
	
	public DimensionView() {
		
		lengthTextArea = new JTextField(7);
		widthTextArea = new JTextField(7);
		heightTextArea = new JTextField(7);
		
		lengthTextArea.addActionListener(this);
		widthTextArea.addActionListener(this);
		heightTextArea.addActionListener(this);
		
		GridLayout layout = new GridLayout();
		layout.setRows(4);
		layout.setColumns(2);
		setLayout(layout);
		
		JPanel p = new JPanel();
	    p.add(new JLabel("Height"));
	    add(p);
	    p = new JPanel();
	    p.add(heightTextArea);
	    add(p);
	    
	    p = new JPanel();
	    p.add(new JLabel("Width"));
	    add(p);
	    p = new JPanel();
	    p.add(widthTextArea);
	    add(p);
	    
	    p = new JPanel();
	    p.add(new JLabel("Length"));
	    add(p);
	    p = new JPanel();
	    p.add(lengthTextArea);
	    add(p);
	    
	    p = new JPanel();
	    JLabel  note = new JLabel();
	    note.setText("Type text in a filed and press enter");
	    note.setHorizontalAlignment(SwingConstants.RIGHT);
	    p.add(note);
	    add(p);
	    
	    setPackable(true);
	    setFields();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		setFields();
	}

	private void setFields() {
		if (model != null && model instanceof Brick) {
			Brick b = (Brick) model;
			lengthTextArea.setText(b.getLength().toString());
			heightTextArea.setText(b.getHeight().toString());
			widthTextArea.setText(b.getWidth().toString());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Brick b = (Brick) model;
		Command c;
		
		if (validateFields()) {
			
			Double length = Double.valueOf(lengthTextArea.getText());
			Double height  = Double.valueOf(heightTextArea.getText());
			Double width = Double.valueOf(widthTextArea.getText());
			
			if (length<=0 || height<=0 || width<=0) {
				Utilities.error("Dimension must be a postive number");
				return;
			}
			
			if (!b.getLength().equals(length)) {
				// Length is changed so update
				c = new SetLength(Double.valueOf(length));
				c.setModel(model);
				CommandProcessor.makeCommandProcessor().execute(c);
			}
			if (!b.getHeight().equals(height)) {
				// Height is changed so update
				c = new SetHeight(Double.valueOf(height));
				c.setModel(model);
				CommandProcessor.makeCommandProcessor().execute(c);
			}
			if (!b.getWidth().equals(width)) {
				// Width is changed so update
				c = new SetWidth(Double.valueOf(width));
				c.setModel(model);
	            CommandProcessor.makeCommandProcessor().execute(c);
			}
		}
	}
	
	private boolean validateFields() {
		try {
			Double.valueOf(lengthTextArea.getText());
			Double.valueOf(heightTextArea.getText());
			Double.valueOf(widthTextArea.getText());
		} catch (Exception e) {
			Utilities.error("Provide correct dimensions");
			return false;
		}
		return true;
	}
}
