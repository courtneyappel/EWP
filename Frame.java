
import javax.swing.*;

public class Frame {

	public static void main(String[] args)
    {

    		JFrame myFrame = new JFrame();

    		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    		myFrame.getContentPane().add(new Panel());

    		myFrame.pack();

    		myFrame.setVisible(true);
    }
}
