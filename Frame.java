
import javax.swing.*;
import java.awt.*;

public class Frame {

	public static void main(String[] args)
    {

    		JFrame myFrame = new JFrame();

    		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    		myFrame.getContentPane().add(new Panel());

    		myFrame.pack();

				myFrame.setSize(new Dimension(500,500));

				myFrame.setResizable(false);

    		myFrame.setVisible(true);
    }
}
