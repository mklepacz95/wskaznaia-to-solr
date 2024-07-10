import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class App {

    public static void main(String[] args) {
        JFrame frame = new JFrame("");
        Gui panel = new Gui();
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        frame.getContentPane().add(panel,"Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
        frame.setResizable(false);

        String pattern = "dd_MM_yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String test = simpleDateFormat.format(new Date());
        System.out.println(test);
    }

}
