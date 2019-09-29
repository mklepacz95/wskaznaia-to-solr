import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JPanel {
    JButton files;
    JButton solr;
    JLabel lblSolr;
    JLabel lblWsk;
    JButton merge;
    JButton wygenerujRPM;

    JFileChooser chooser;
    String choosertitle;

    XmlProcessor xmlProcessor;
    ExcelProcessor excelProcessor;

    public Gui() {

        lblSolr = new JLabel();
        lblSolr.setPreferredSize(new Dimension(300,20));

        lblWsk = new JLabel();
        lblWsk.setPreferredSize(new Dimension(300,20));

        solr = new JButton("Wybierz plik do ktorego dodac wskazania");
        solr.setPreferredSize(new Dimension(300,20));
        solr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("C:\\Users\\m.klepacz\\Desktop\\przyrosty\\!2019"));
                chooser.setDialogTitle(choosertitle);
                if (chooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getSelectedFile() : "
                            +  chooser.getSelectedFile());
                    lblSolr.setText(chooser.getSelectedFile().toString());
                }
                else {
                    System.out.println("No Selection ");
                }
            }
        });
        add(solr);
        add(lblSolr);


        files = new JButton("Wybierz plik wskazan");
        files.setPreferredSize(new Dimension(300,20));
        files.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("C:\\Users\\m.klepacz\\Documents"));
                chooser.setDialogTitle(choosertitle);
                if (chooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
                    System.out.println("getSelectedFile() : "
                            +  chooser.getSelectedFile());
                    lblWsk.setText(chooser.getSelectedFile().toString());
                }
                else {
                    System.out.println("No Selection ");
                }
            }
        });
        add(files);
        add(lblWsk);

        merge = new JButton("Dodaj wskaznaia");
        merge.setPreferredSize(new Dimension(300,20));
        merge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xmlProcessor = new XmlProcessor(lblSolr.getText());
                excelProcessor = new ExcelProcessor(lblWsk.getText(), xmlProcessor);
                excelProcessor.readFromExcel();
            }
        });
        add(merge);

        wygenerujRPM = new JButton("Wygenruj RPM");
        merge.setPreferredSize(new Dimension(300,20));
        wygenerujRPM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xmlProcessor = new XmlProcessor();
                xmlProcessor.wygenerujPlikXmlDlaRpm();
            }
        });
        add(wygenerujRPM);


    }

    public Dimension getPreferredSize(){
        return new Dimension(310, 220);
    }

}