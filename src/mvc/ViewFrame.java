package mvc;

import javax.swing.JInternalFrame;

public class ViewFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private static int openFrameCount = 0;
	public ViewFrame(View view) {

		super("",
              true, //resizable
              true, //closable
              true, //maximizable
              true);//iconifiable
		
        setContentPane(view);
        ++openFrameCount;
        setLocation(30*openFrameCount, 30*openFrameCount);
	}
}
