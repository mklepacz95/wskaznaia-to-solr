import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Pattern;

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
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            Sheet sheet = null;
            while(sheetIterator.hasNext()) {
                sheet = sheetIterator.next();
                System.out.println(sheet.getSheetName());
                Iterator<Row> rowIterator = sheet.iterator();
                String tytul = null;
                String poziomPoprzedni = "";
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    String ean = null;
                    String rodzjaWskazania = null;
                    String wskazanie = null;
                    String poziomOdplatnosci = null;
                    String wiek = "";
                    String wiekOd = "";
                    String wiekDo = "";
                    String poziomWskazania = null;
                    String eanPoprzedni = null;
                    String idWskazania = null;
                    if (sheet.getSheetName().equals("A1")) {
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            //if(sheet.getSheetName().equalsIgnoreCase("a1")) {
                            switch (cell.getColumnIndex()) {
                                case 0:
                                    idWskazania = String.valueOf(cell.getNumericCellValue());
                                    idWskazania = idWskazania.trim();
                                    idWskazania = idWskazania.substring(0,idWskazania.indexOf("."));
                                    break;
                                case 1:
                                    if (cell.getCellType() == CellType.STRING) {
                                        ean = cell.getStringCellValue();
                                    } else ean = String.valueOf(cell.getNumericCellValue());
                                    if (ean.length() == 13) ean = "0" + ean;
                                    if (ean.equals(eanPoprzedni)) tytul = null;
                                    //System.out.println(ean + " " + ean.length());
                                    break;
                                case 2:
                                    rodzjaWskazania = cell.getStringCellValue();
                                    rodzjaWskazania = rodzjaWskazania.trim();
                                    if (rodzjaWskazania.equals("CHPL") || rodzjaWskazania.equals("ChPl"))
                                        rodzjaWskazania = "ChPL";
                                    break;
                                case 3:
                                    if (cell.getCellType() == CellType.STRING) {
                                        poziomWskazania = cell.getStringCellValue();
                                    } else poziomWskazania = String.valueOf(cell.getNumericCellValue());
                                    poziomWskazania.trim();
                                    System.out.println(poziomWskazania);
                                    if (poziomWskazania.contains(".")) {
                                        if (poziomWskazania.substring(poziomWskazania.indexOf(".") + 1, poziomWskazania.indexOf(".") + 2).equals("0")) {
                                            //System.out.println("index: " + poziomWskazania.indexOf("."));
                                            poziomWskazania = poziomWskazania.substring(0, poziomWskazania.indexOf("."));
                                        }
                                    }
//                            System.out.println(poziomWskazania);
//                            System.out.println("test " + poziomWskazania.contains(".") + " " + poziomWskazania);
//                            //System.out.println(poziomWskazania.substring(0,1));
                                    break;
                                case 4:
                                    wskazanie = cell.getStringCellValue();
                                    if (wskazanie.contains("\n")) {
                                        wskazanie = wskazanie.replace("\n", " ");
                                    }
                                    if (!poziomWskazania.contains(poziomPoprzedni)) {
                                        tytul = "";
                                    }
                                    wskazanie = wskazanie.trim();
                                    wskazanie = wskazanie.replaceFirst(wskazanie.substring(0, 1), wskazanie.substring(0, 1).toUpperCase());
                                    if (poziomWskazania != null) {
                                        if (Pattern.matches("\\d{1,2}\\.0", poziomWskazania) || poziomWskazania.length() == 1) {
                                            //System.out.println(Pattern.matches("\\d{1}.0",poziomWskazania));
                                            //if(tytul != null) wskazanie = tytul + " " + wskazanie;
                                            wskazanie = wskazanie.replaceFirst(wskazanie.substring(0, 1), wskazanie.substring(0, 1).toUpperCase());
                                            tytul = wskazanie;
                                            //System.out.println("Poziom: " + poziomWskazania + " Tytul: " + tytul + " wskazanie: " + wskazanie);
                                            System.out.println("Pattern");
                                        } else {
                                            if (!tytul.equals(wskazanie)) {
                                                //System.out.println(Pattern.matches("\\d{1}.0",poziomWskazania));
                                                //System.out.println("Poziom: " + poziomWskazania + " Tytul: " + tytul + " wskazanie: " + wskazanie);
                                                wskazanie = wskazanie.replaceFirst(wskazanie.substring(0, 1), wskazanie.substring(0, 1).toLowerCase());
                                                if (!tytul.equals("")) {
                                                    tytul = tytul.replaceFirst(tytul.substring(0, 1), tytul.substring(0, 1).toUpperCase());
                                                    if(tytul.trim().endsWith(":") || tytul.trim().endsWith(".") || tytul.trim().endsWith("-")) {
                                                        wskazanie = tytul + " " + wskazanie;
                                                    }
                                                    else {
                                                        wskazanie = tytul + " - " + wskazanie;
                                                    }
                                                }
                                                tytul = wskazanie;
                                                System.out.println("no pattern");
                                            }
                                        }
                                    }
                                    //System.out.println("Poziom: " + poziomWskazania + " Tytul: " + tytul + " wskazanie: " + wskazanie);
                                    break;
                                case 5:
                                    if (cell.getCellType() == CellType.STRING)
                                        poziomOdplatnosci = cell.getStringCellValue();
                                    else poziomOdplatnosci = String.valueOf(cell.getNumericCellValue());
                                    poziomOdplatnosci = poziomOdplatnosci.trim().toUpperCase();
                                    if (poziomOdplatnosci.trim().equals("R")) poziomOdplatnosci = "RYCZAŁT";
                                    else if (poziomOdplatnosci.equals("0.5") || poziomOdplatnosci.equals("0,5")) poziomOdplatnosci = "50%";
                                    else if (poziomOdplatnosci.equals("0.3") || poziomOdplatnosci.equals("0,3")) poziomOdplatnosci = "30%";
                                    else if (poziomOdplatnosci.equals("B") || poziomOdplatnosci.equals("BEZPŁATNY DO LIMITU"))
                                        poziomOdplatnosci = "BEZPŁATNY_DO_LIMITU";
                                    break;
                                case 6:
                                    wiek = cell.getStringCellValue();
                                    if (wiek == null) wiek = "";

                                    wiek = wiek.trim().toUpperCase();
                                    if (wiek.contains("DO") && wiek.contains("OD")) {
                                        wiekDo = wiek.substring(wiek.indexOf("DO ") + 3);
                                        wiekOd = wiek.substring(wiek.indexOf("OD ") + 3, wiek.indexOf(" DO"));
                                        System.out.println("wiek od: " + wiekOd + " wiek do: " + wiekDo);
                                    } else if (wiek.contains("DO")) {
                                        wiekDo = wiek.substring(wiek.indexOf("DO ") + 3).trim();
                                        System.out.println(" wiek do: " + wiekDo);
                                    } else if (wiek.contains("OD")) {
                                        wiekOd = wiek.substring(wiek.indexOf("OD ") + 3).trim();
                                        System.out.println("wiek od: " + wiekOd);
                                    }
                                    break;
                            }
                        }

                        eanPoprzedni = ean;
                        poziomPoprzedni = poziomWskazania;
                        String next = "";
                        //System.out.println(sheet.getRow(row.getRowNum() + 1).getCell(3).getStringCellValue());
                        if (sheet.getRow(row.getRowNum() + 1).getCell(3).getCellType() == CellType.STRING) {
                            next = sheet.getRow(row.getRowNum() + 1).getCell(3).getStringCellValue();
                        } else {
                            next = String.valueOf(sheet.getRow(row.getRowNum() + 1).getCell(3).getNumericCellValue());
                        }
                        if(!poziomWskazania.contains(".")) {
                            if (ean != null && poziomOdplatnosci != null && wskazanie != null && rodzjaWskazania != null) {
                                xmlProcessor.dodajWskazanieDoXml(ean, poziomOdplatnosci, wskazanie, rodzjaWskazania, wiekOd, wiekDo, idWskazania);
                            }
                        } else if (!next.contains(poziomWskazania)) {
                            if (ean != null && poziomOdplatnosci != null && wskazanie != null && rodzjaWskazania != null) {
                                xmlProcessor.dodajWskazanieDoXml(ean, poziomOdplatnosci, wskazanie, rodzjaWskazania, wiekOd, wiekDo, idWskazania);
                            }
                        }
                    } else {
                        System.out.println("po else" + sheet.getSheetName());
                        //TO-DO dla if(nazwa_arkusza) != a1
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cell.getColumnIndex()) {
                                case 0:
                                    idWskazania = String.valueOf(cell.getNumericCellValue());
                                    idWskazania = idWskazania.trim();
                                    idWskazania = idWskazania.substring(0,idWskazania.indexOf("."));
                                    break;
                                case 1:
                                    ean = cell.getStringCellValue().trim();
                                    //if (ean.length() == 13) ean = "0" + ean;
                                    //if (ean.equals(eanPoprzedni)) tytul = null;
                                    //System.out.println(ean + " " + ean.length());
                                    break;
                                case 2:
                                    if (cell.getCellType() == CellType.STRING)
                                        poziomOdplatnosci = cell.getStringCellValue();
                                    else poziomOdplatnosci = String.valueOf(cell.getNumericCellValue());
                                    poziomOdplatnosci = poziomOdplatnosci.trim().toUpperCase();
                                    if (poziomOdplatnosci.trim().equals("R")) poziomOdplatnosci = "RYCZAŁT";
                                    else if (poziomOdplatnosci.equals("0.5") || poziomOdplatnosci.equals("0,5")) poziomOdplatnosci = "50%";
                                    else if (poziomOdplatnosci.equals("0.3") || poziomOdplatnosci.equals("0,3")) poziomOdplatnosci = "30%";
                                    else if (poziomOdplatnosci.equals("B") || poziomOdplatnosci.equals("BEZPŁATNY DO LIMITU"))
                                        poziomOdplatnosci = "BEZPŁATNY_DO_LIMITU";
                                    break;
                            }
                            if(ean != null && poziomOdplatnosci != null  && idWskazania != null)
                            xmlProcessor.dodajIdWskazaniaA2A3(ean, poziomOdplatnosci, idWskazania);
                        }
                    }
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