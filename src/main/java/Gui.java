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
    JButton wytynijZSolr;

    JFileChooser chooser;
    String choosertitle;

    XmlProcessor xmlProcessor;
    ExcelProcessor excelProcessor;
    WytnijWezlyZSolr wytnijWezlyZSolr;

    public Gui() {

        lblSolr = new JLabel();
        lblSolr.setPreferredSize(new Dimension(400,20));

        lblWsk = new JLabel();
        lblWsk.setPreferredSize(new Dimension(400,20));

        JLabel lblText = new JLabel();
        lblText.setText("<html>Najpierw trzeba usunąć ID wskazania z A1. Do tego wskaż plik i kliknij usun wskazania. <br/> Po wykonaniu restart aplikacji</html>");

        add(lblText);

        solr = new JButton("Wybierz plik do ktorego dodac wskazania");
        solr.setPreferredSize(new Dimension(300,20));
        solr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("files"));
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
        wytynijZSolr = new JButton("Usuwanie z SOLR");
        wytynijZSolr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                wytnijWezlyZSolr = new WytnijWezlyZSolr(lblSolr.getText(), lblWsk.getText());
                wytnijWezlyZSolr.wytnijWskazaniaZA1();
            }
        });
        add(wytynijZSolr);


        JLabel lblText2 = new JLabel();
        lblText2.setText("<html>Tutaj po restarcie apki, wskazujemy plik wynikowy o nazwie bezIdWA1<br/>Dodajemy plik excel wskazan<br/>Klikamy dodaj wskazania<br/>Czekamy aż się wygeneruje<br/>Powstanie folder z nazwa daty gdzie powinno wszystko trafic<br/> szczegolnie ten wygenerwoany plik bez id w a1</html>");

        add(lblText2);

        files = new JButton("Wybierz plik wskazan");
        files.setPreferredSize(new Dimension(300,20));
        files.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("files"));
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

        merge = new JButton("Dodaj wskaznaia cos cos ocs");
        merge.setPreferredSize(new Dimension(300,20));
        merge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xmlProcessor = new XmlProcessor(lblSolr.getText());
                excelProcessor = new ExcelProcessor(lblWsk.getText(), xmlProcessor);
                excelProcessor.readFromExcel();
            }
        });
        add(merge);



        /*
        wygenerujRPM = new JButton("Wygenruj RPM");
        merge.setPreferredSize(new Dimension(300,20));
        wygenerujRPM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xmlProcessor = new XmlProcessor();
                xmlProcessor.wygenerujPlikXmlDlaRpm();
            }
        });
        add(wygenerujRPM);

         */

    }

    public Dimension getPreferredSize(){
        return new Dimension(600, 320);
    }

}