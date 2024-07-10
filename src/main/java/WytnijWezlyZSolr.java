import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class WytnijWezlyZSolr {

    String pathToXml;
    String pathToExcel;

    public WytnijWezlyZSolr(String pathToXml, String pathToExcel) {
        this.pathToXml = pathToXml;
        this.pathToExcel = pathToExcel;
    }

    public void wytnij(String ean) {
        try {
            File file = new File(pathToXml);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File output = new File("files/07_2023/bezIdWA1.xml");
            OutputStream os = new FileOutputStream(output);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));

            Document doc = builder.parse(file);

            NodeList nodeList = doc.getElementsByTagName("EAN");

            int size = nodeList.getLength();
            for(int i = 0; i< size;i++) {
                Node n = nodeList.item(i);
                Element e = (Element) n;
                System.out.println(e.getNodeName());
                e.getParentNode().removeChild(e);
            }
            doc.normalize();
            Writer out = new StringWriter();
            try {
                Transformer tf = TransformerFactory.newInstance().newTransformer();
                tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tf.setOutputProperty(OutputKeys.INDENT, "yes");
                try {
                    tf.transform(new DOMSource(doc), new StreamResult(out));
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                //pw.println(out.toString());
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }
            pw.println(out.toString());
            pw.close();
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void odczytEanZExecla() {
        String ean;
        try {
            FileInputStream file = new FileInputStream(new File(pathToExcel));

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            Sheet sheet = null;
            while(sheetIterator.hasNext()) {
                sheet = sheetIterator.next();
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getColumnIndex()) {
                            case 0:
                                ean = cell.getStringCellValue();
                                if (ean.length() == 13) ean = "0" + ean;
                                wytnij(ean);
                                break;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wytnijWskazaniaZA1() {
        try {
            File file = new File(pathToXml);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();



            String pattern = "dd_MM_yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String data = simpleDateFormat.format(new Date());

            Files.createDirectories(Paths.get(data));
            File output = new File(data + "/bezIdWA1.xml");
            PrintWriter pw = new PrintWriter(output);
            Document doc = builder.parse(file);

            NodeList nodeList = doc.getElementsByTagName("listaRefundacyjnaEnum");

            int size = nodeList.getLength();
            for(int i = 0; i< size;i++) {
                Node n = nodeList.item(i);
                Element e = (Element) n;
                if(e.getTextContent().equals("A1")) {
                    Node pe = e.getParentNode().getChildNodes().item(17);
                    System.out.printf("pe = " + pe.getNodeName());// + " " + pe.getTextContent());

                    Element parentWskazania = (Element) pe.getParentNode();

                    parentWskazania.removeChild(pe);

                    Element wskazania = doc.createElement("wskazania");
                    parentWskazania.appendChild(wskazania);

                }
                System.out.println(e.getTextContent());
                //e.getParentNode().removeChild(e);
            }
            doc.normalize();
            Writer out = new StringWriter();
            try {
                Transformer tf = TransformerFactory.newInstance().newTransformer();
                tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tf.setOutputProperty(OutputKeys.INDENT, "yes");
                try {
                    tf.transform(new DOMSource(doc), new StreamResult(out));
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                //pw.println(out.toString());
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }
            pw.println(out.toString());
            pw.close();
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
