
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class Frame {

	public static void main(String[] args) throws FileNotFoundException
    {

    		JFrame myFrame = new JFrame();

    		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            System.out.println(System.getProperty("os.name"));
            String osName = System.getProperty("os.name");
    		//if(osName.equalsIgnoreCase("Windows 10")) {
    	  //  myFrame.setIconImage(new ImageIcon(ClassLoader.getSystemResource("ewpLogo.png")).getImage());
    		//}

    		myFrame.setUndecorated(true);

    		myFrame.getContentPane().add(new Panel());

    		myFrame.pack();

			myFrame.setSize(new Dimension(1200,720));

			myFrame.setResizable(false);

			myFrame.setLocationRelativeTo(null);

    		myFrame.setVisible(true);


    }
}
