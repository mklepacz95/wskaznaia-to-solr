import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class ExcelProcessor {

    private String pathToFile;
    private XmlProcessor xmlProcessor;

    public  ExcelProcessor(String pathToFile, XmlProcessor xmlProcessor) {
        this.pathToFile = pathToFile;
        this.xmlProcessor = xmlProcessor;
    }

    public void readFromExcel() {
        try {
            FileInputStream file = new FileInputStream(new File(pathToFile));

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            String tytul = null;
            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                String ean = null;
                String rodzjaWskazania = null;
                String wskazanie = null;
                String poziomOdplatnosci = null;
                String wiek = null;
                String poziomWskazania = null;
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 1:
                            ean = cell.getStringCellValue();
                            if(ean.length() == 13) ean = "0" + ean;
                            //System.out.println(ean + " " + ean.length());
                            break;
                        case 2:
                            rodzjaWskazania = cell.getStringCellValue();
                            break;
                        case 3:
                            poziomWskazania = cell.getStringCellValue();
                            break;
                        case 4:
                            wskazanie = cell.getStringCellValue();
                            if(wskazanie.contains("\n")) {
                                wskazanie = wskazanie.replace("\n"," ");
                            }
                            if(poziomWskazania != null ) {
                                if(poziomWskazania.contains(".")) {
                                    if(tytul != null) wskazanie = tytul + " " + wskazanie;
                                }
                                else {
                                    tytul = wskazanie;
                                }
                            }
                            break;
                        case 5:
                            poziomOdplatnosci = cell.getStringCellValue();
                            if(poziomOdplatnosci.equals("R")) poziomOdplatnosci = "RYCZAŁT";
                            break;
                        case 6:
                            wiek = cell.getStringCellValue();
                            break;
                    }
                }
                System.out.println("EAN: " + ean
                        + " poziomOdplatnosci: " + poziomOdplatnosci
                        + " wskaznie: " + wskazanie
                        + " rodzaj wskazania: " + rodzjaWskazania
                        + " poziom wskazania: " + poziomWskazania
                );
                if(ean != null && poziomOdplatnosci != null && wskazanie != null && rodzjaWskazania != null) {
                    xmlProcessor.dodajWskazanieDoXml(ean, poziomOdplatnosci, wskazanie, rodzjaWskazania, wiek);
                }
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku " + pathToFile);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(pathToFile);
    }

}