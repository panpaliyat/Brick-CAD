package mvc;

import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class Utilities {

	public static JMenu makeMenu(String name, String[] items, ActionListener handler) {

		JMenu result;
		int j = name.indexOf('&');
		if (j != -1) {
			char c = name.charAt(j + 1);
			String s = name.substring(0, j) + name.substring(j + 1);
			result = new JMenu(s);
			result.setMnemonic(c);
		} else {
			result = new JMenu(name);
		}

		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				result.addSeparator();
			} else {
				j = items[i].indexOf('&');
				JMenuItem item;
				if (j != -1) {
					char c = items[i].charAt(j + 1);
					String s = items[i].substring(0, j) + items[i].substring(j + 2);
					item = new JMenuItem(s, items[i].charAt(j + 1));
					item.setAccelerator(KeyStroke.getKeyStroke(c, InputEvent.CTRL_MASK));
				} else {
					item = new JMenuItem(items[i]);
				}
				item.addActionListener(handler);
				result.add(item);
			}
			// result.addMenuListener(this);
		}
		return result;
	}

	public static String askUser(String query) {
		return JOptionPane.showInputDialog(query);
	}

	public static boolean confirm(String query) {
		int result = JOptionPane.showConfirmDialog(null, query, "choose one", JOptionPane.YES_NO_OPTION);
		return result == 0; // or 1?
	}

	public static void error(String gripe) {
		JOptionPane.showMessageDialog(null, gripe, "OOPS!", JOptionPane.ERROR_MESSAGE);
	}

	public static void error(Exception gripe) {
		gripe.printStackTrace();
		JOptionPane.showMessageDialog(null, gripe.toString(), "OOPS!", JOptionPane.ERROR_MESSAGE);
	}

	public static void informUser(String info) {
		JOptionPane.showMessageDialog(null, info, "information", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void saveChanges(Model model) {
		if (model.hasUnsavedChanges() && Utilities.confirm("current model has unsaved changes, continue?"))
			Utilities.save(model);
	}

	public static void save(Model model) {
		String fName = model.getFileName();
		ObjectOutputStream os = null;

		//JFileChooser automatically handles the case, when the save button is clicked without entering the file name
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		if (fName == null) {
			// File name is null, means we have not saved this file yet
			if (0 == fileChooser.showSaveDialog(null)) {
				fName = fileChooser.getSelectedFile().getPath();
			} else {
				//clicked on cancel button, so we return
				return;
			}
		} else {
		}
		try {
			os = new ObjectOutputStream(new FileOutputStream(fName));
			model.setFileName(fName);
			model.setUnsavedChanges(false);
			os.writeObject(model);
			Utilities.informUser("File Saved");
		} catch (Exception err) {
			err.printStackTrace();
			Utilities.error(err.getMessage());
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveAs(Model model) {

		JFileChooser fileChooser = new JFileChooser();
		
		if (model.getFileName()!= null)
			fileChooser.setCurrentDirectory(new File(model.getFileName()));
		else
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		if (0 == fileChooser.showSaveDialog(null)) {
			model.setFileName(fileChooser.getSelectedFile().getPath());
			save(model);
		}
		else
			return;
	}

	public static Model open(Model model) {

		ObjectInputStream is = null;

		String fileName = model.getFileName();
		
		JFileChooser fileChooser = new JFileChooser();
		if (fileName != null)
			fileChooser.setCurrentDirectory(new File(fileName));
		else
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		if ( 0 == fileChooser.showOpenDialog(null)) {
			try {
				is = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile().getPath()));
				model = (Model) is.readObject();
			} catch (Exception e){
				Utilities.error("Can not open file");
			}	finally {
				try {
					if (is != null)
						is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return model;
		} else {
			return null;
		}
	}
}
