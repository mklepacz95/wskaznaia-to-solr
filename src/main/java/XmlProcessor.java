import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Random;

public class XmlProcessor {

    private String pathTofile;

    public XmlProcessor(String pathTofile) {
        this.pathTofile = pathTofile;
    }

    public XmlProcessor(){}

    public void dodajWskazanieDoXml(String ean, String poziomOdplatnosci, String wskazanie, String rodzajWskazania, String wiekOd, String wiekDo, String idWskazania) { //}, String numerGrupy, String nazwaGrupy) {

        System.out.println(idWskazania);

        try {
            File file = new File(pathTofile);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();


            Document doc = builder.parse(file);

            NodeList nodeList = doc.getElementsByTagName("OpakowanieLeku");

            for(int i = 0; i< nodeList.getLength();i++) {
                Node n = nodeList.item(i);
                Element e = (Element) n;

                Node eanNode = e.getElementsByTagName("EAN").item(0);

                if (eanNode.getTextContent().trim().equals(ean)) {
                    Element opakowanieLekuElement = (Element) eanNode.getParentNode();
                    NodeList refundacje = opakowanieLekuElement.getElementsByTagName("Refundacja");
                    for (int j = 0; j < refundacje.getLength(); j++) {
                        Node refundacja = refundacje.item(j);
                        Element refdacjaElement = (Element) refundacja;
                        NodeList refundacjaNode = refdacjaElement.getElementsByTagName("poziomOdplatnosciEnum");
                        if (refundacjaNode.getLength() != 0) {
                            if (refundacjaNode.item(0).getTextContent().equals(poziomOdplatnosci)) {

/*
                                NodeList grupaNodeList = refdacjaElement.getElementsByTagName("grupaLimitowa");
                                Element grupaLimitowaElemet = (Element) grupaNodeList.item(0);

                                NodeList numer = grupaLimitowaElemet.getElementsByTagName("numer");




                                if(numer.getLength() == 0) {
                                    Element numerGrupyLimitowej = doc.createElement("numer");
                                    numerGrupyLimitowej.appendChild(doc.createTextNode(numerGrupy));
                                    grupaLimitowaElemet.appendChild(numerGrupyLimitowej);

                                    Element nazwaGrupyLimitowej = doc.createElement("nazwa");
                                    nazwaGrupyLimitowej.appendChild(doc.createTextNode(nazwaGrupy));
                                    grupaLimitowaElemet.appendChild(nazwaGrupyLimitowej);

                                }



                                //refdacjaElement.appendChild(grupaLimitowaElement);



 */



                                NodeList wskazania = ((Element) refundacja).getElementsByTagName("wskazania");
                                Node wskazniaNode = wskazania.item(0);
//
                                Element wskazanieElementToAdd = doc.createElement("wskazanie");
                                wskazanieElementToAdd.appendChild(doc.createTextNode(wskazanie));
                                wskazanieElementToAdd.setAttribute("czyWszystkie", "false");

                                if (rodzajWskazania.equals("RYCZAŁT") || rodzajWskazania.equals("R")) {
                                    wskazanieElementToAdd.setAttribute("rodzajWskazania", "P");
                                } else {
                                    wskazanieElementToAdd.setAttribute("rodzajWskazania", rodzajWskazania);
                                }

                                if (!wiekOd.equals("")) {
                                    wskazanieElementToAdd.setAttribute("wiekOd", wiekOd);
                                }
                                if (!wiekDo.equals("")) {
                                    wskazanieElementToAdd.setAttribute("wiekDo", wiekDo);
                                }
                                wskazanieElementToAdd.setAttribute("idWskazania",idWskazania);

                                wskazniaNode.appendChild(wskazanieElementToAdd);

                                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                Transformer transformer = transformerFactory.newTransformer();
                                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                                DOMSource domSource = new DOMSource(doc);
                                StreamResult streamResult = new StreamResult(new File(pathTofile));

                                transformer.transform(domSource, streamResult);
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void dodajIdWskazaniaA2A3(String ean, String poziomOdplatnosci, String idWskazania, String wiekOd, String wiekDo) { //, String numerGrupy, String nazwaGrupy) {
        try {
            File file = new File(pathTofile);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();


            Document doc = builder.parse(file);

            NodeList nodeList = doc.getElementsByTagName("OpakowanieLeku");

            for(int i = 0; i< nodeList.getLength();i++) {
                Node n = nodeList.item(i);
                Element e = (Element) n;

                Node eanNode = e.getElementsByTagName("EAN").item(0);

                if (eanNode.getTextContent().trim().equals(ean)) {
                    Element opakowanieLekuElement = (Element) eanNode.getParentNode();
                    NodeList refundacje = opakowanieLekuElement.getElementsByTagName("Refundacja");
                    for (int j = 0; j < refundacje.getLength(); j++) {
                        Node refundacja = refundacje.item(j);
                        Element refdacjaElement = (Element) refundacja;
                        NodeList refundacjaNode = refdacjaElement.getElementsByTagName("poziomOdplatnosciEnum");
                        if (refundacjaNode.getLength() != 0) {
                            for(int k=0; k < refundacjaNode.getLength(); k++) {
                                if (refundacjaNode.item(0).getTextContent().equals(poziomOdplatnosci)) {


                                    //Element ref = (Element) refundacjaNode.item(0);
/*
                                    Element grupaLimitowaElement = doc.createElement("grupaLimitowa");

                                    Element numerGrupyLimitowej = doc.createElement("numer");
                                    numerGrupyLimitowej.appendChild(doc.createTextNode(numerGrupy));
                                    grupaLimitowaElement.appendChild(numerGrupyLimitowej);

                                    Element nazwaGrupyLimitowej = doc.createElement("nazwa");
                                    nazwaGrupyLimitowej.appendChild(doc.createTextNode(nazwaGrupy));
                                    grupaLimitowaElement.appendChild(nazwaGrupyLimitowej);

                                    //ref.appendChild(grupaLimitowaElement);
                                    */

                                    Element ref = (Element) refundacja;


                                    /*
                                    NodeList grupaNodeList = ref.getElementsByTagName("grupaLimitowa");
                                    Element grupaLimitowaElemet = (Element) grupaNodeList.item(0);

                                    NodeList numer = grupaLimitowaElemet.getElementsByTagName("numer");

                                    if(numer.getLength() == 0) {
                                        Element numerGrupyLimitowej = doc.createElement("numer");
                                        numerGrupyLimitowej.appendChild(doc.createTextNode(numerGrupy));
                                        grupaLimitowaElemet.appendChild(numerGrupyLimitowej);

                                        Element nazwaGrupyLimitowej = doc.createElement("nazwa");
                                        nazwaGrupyLimitowej.appendChild(doc.createTextNode(nazwaGrupy));
                                        grupaLimitowaElemet.appendChild(nazwaGrupyLimitowej);

                                    }

                                     */


                                    NodeList wskazania = ref.getElementsByTagName("wskazanie");
                                    Element wskaznieNode = (Element) wskazania.item(0);

                                    wskaznieNode.setAttribute("idWskazania",idWskazania);
                                    wskaznieNode.setAttribute("wiekOd", wiekOd);
                                    wskaznieNode.setAttribute("wiekDo", wiekDo);


                                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                    Transformer transformer = transformerFactory.newTransformer();
                                    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                                    DOMSource domSource = new DOMSource(doc);
                                    StreamResult streamResult = new StreamResult(new File(pathTofile));

                                    transformer.transform(domSource, streamResult);
                                }
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void deleteNodefromXml() {
        try {
            File file = new File("file/tmp.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File output = new File("file/bezNip.xml");
            PrintWriter pw = new PrintWriter(output);

            Document doc = builder.parse(file);

            doc.getElementsByTagName("ks:wpisy").item(0).getAttributes().removeNamedItem("stanNaDzien");

            NodeList nodeList = doc.getElementsByTagName("oso:NIP");
            NodeList nodeListOp = doc.getElementsByTagName("typ:OswiadczeniePierwsze");
            NodeList nodeListDr = doc.getElementsByTagName("typ:OswiadczenieDrugie");
            NodeList nodeListTr = doc.getElementsByTagName("typ:OswiadczenieTrzecie");
            NodeList nodeListCz = doc.getElementsByTagName("typ:OswiadczenieCzwarte");
            NodeList nodeListOsT = doc.getElementsByTagName("typ:OswiadczenieTresc");
            NodeList nodeListOs = doc.getElementsByTagName("typ:OswiadczenieData");
            NodeList nodeListOsD = doc.getElementsByTagName("typ:Oswiadczenie");
            int size = nodeList.getLength();
            for(int i = 0; i< size;i++) {
                Node n = nodeList.item(0);
                Element e = (Element) n;
                e.getParentNode().removeChild(e);
            }
            size = nodeListOp.getLength();
            for(int i = 0; i< size;i++) {
                Node n = nodeListOp.item(0);
                Element e = (Element) n;
                e.getParentNode().removeChild(e);
            }
            size = nodeListDr.getLength();
            for(int i = 0; i< size;i++) {
                Node n = nodeListDr.item(0);
                Element e = (Element) n;
                e.getParentNode().removeChild(e);
            }
            size = nodeListTr.getLength();
            for(int i = 0; i< size;i++) {
                Node n = nodeListTr.item(0);
                Element e = (Element) n;
                e.getParentNode().removeChild(e);
            }
            size = nodeListCz.getLength();
            for(int i = 0; i< size;i++) {
                Node n = nodeListCz.item(0);
                Element e = (Element) n;
                e.getParentNode().removeChild(e);
            }
            size = nodeListOsD.getLength();
            for(int i = 0; i< size;i++) {
                Node n = nodeListOsD.item(0);
                Element e = (Element) n;
                e.getParentNode().removeChild(e);
            }
            size = nodeListOsT.getLength();
            for(int i = 0; i< size;i++) {
                Node n = nodeListOsT.item(0);
                Element e = (Element) n;
                e.getParentNode().removeChild(e);
            }
            size = nodeListTr.getLength();
            for(int i = 0; i< size;i++) {
                Node n = nodeListTr.item(0);
                Element e = (Element) n;
                e.getParentNode().removeChild(e);
            }
            size = nodeListOs.getLength();
            for(int i = 0; i< size;i++) {
                Node n = nodeListOs.item(0);
                Element e = (Element) n;
                e.getParentNode().removeChild(e);
            }
            System.out.println(doc.getElementsByTagName("oso:NIP").getLength());
            doc.normalize();
            try {
                Transformer tf = TransformerFactory.newInstance().newTransformer();
                tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tf.setOutputProperty(OutputKeys.INDENT, "yes");
                Writer out = new StringWriter();
                try {
                    tf.transform(new DOMSource(doc), new StreamResult(out));
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                //System.out.println(out.toString());
                pw.println(out.toString());
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }
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

    public void deleteNodeFromSolrXml() {
        try {
            File file = new File("file/tmpSolr.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File output = new File("file/bezWskazan.xml");
            PrintWriter pw = new PrintWriter(output);

            Document doc = builder.parse(file);

            //doc.getElementsByTagName("ks:wpisy").item(0).getAttributes().removeNamedItem("stanNaDzien");

            NodeList nodeList = doc.getElementsByTagName("wskazania");
            int size = nodeList.getLength();
            for(int i = 0; i< size;i++) {
                Node n = nodeList.item(0);
                Element e = (Element) n;
                e.getParentNode().removeChild(e);
            }
            doc.normalize();
            try {
                Transformer tf = TransformerFactory.newInstance().newTransformer();
                tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tf.setOutputProperty(OutputKeys.INDENT, "yes");
                Writer out = new StringWriter();
                try {
                    tf.transform(new DOMSource(doc), new StreamResult(out));
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                System.out.println(out.toString());
                pw.println(out.toString());
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }
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

    public void wygenerujPlikXmlDlaRpm() {

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("rejestr");
            root.setAttribute("xmlns", "http://rpm.rejestrymedyczne.csioz.gov.pl/export/rejestr/2017/11/14/");
            root.setAttribute("stanNaDzien", "2019-08-31T09:57:26Z");
            root.setAttribute("xmlns:xs","http://www.w3.org/2001/XMLSchema-instance");
            //root.setAttributeNS("xmlns","xs","http://www.w3.org/2001/XMLSchema-instance");
            document.appendChild(root);


            Integer npwz = 1111111;


            for(int i = 0; i <= 500000;i++) {
                String pesel= "";
                Element pracownik = document.createElement("pracownik");
                root.appendChild(pracownik);

                Element imiona = document.createElement("imiona");
                imiona.appendChild(document.createTextNode("Imie" + i));
                pracownik.appendChild(imiona);

                Element nazwisko = document.createElement("nazwisko");
                nazwisko.appendChild(document.createTextNode("Nazwisko" + i));
                pracownik.appendChild(nazwisko);

                Element peselEle = document.createElement("pesel");
                Random random = new Random();
                for(int j = 0 ; j<= 10; j++) {
                    String cyfra = Integer.valueOf(random.nextInt(10)).toString();
                    System.out.println(cyfra);
                    pesel = pesel + cyfra;
                }
                System.out.println(pesel);
                peselEle.appendChild(document.createTextNode(pesel));
                pracownik.appendChild(peselEle);

                Element numerPwz = document.createElement("numerPwz");
                npwz = npwz +1;
                numerPwz.appendChild(document.createTextNode(npwz.toString()));
                pracownik.appendChild(numerPwz);

                Element typPracownikaMedycznego = document.createElement("typPracownikaMedycznego");
                typPracownikaMedycznego.appendChild(document.createTextNode("Lekarz"));
                pracownik.appendChild(typPracownikaMedycznego);

                Element dataUzyskaniaPwz = document.createElement("dataUzyskaniaPwz");
                dataUzyskaniaPwz.appendChild(document.createTextNode("1974-09-03"));
                pracownik.appendChild(dataUzyskaniaPwz);

                Element dataUtratyPwz = document.createElement("dataUtratyPwz");
                dataUtratyPwz.setAttribute("xs:nil","true");
                pracownik.appendChild(dataUtratyPwz);

                Element dataPoczatkuZawieszeniaPwz = document.createElement("dataPoczatkuZawieszeniaPwz");
                dataPoczatkuZawieszeniaPwz.setAttribute("xs:nil","true");
                pracownik.appendChild(dataPoczatkuZawieszeniaPwz);

                Element dataKoncaZawieszeniaPwz = document.createElement("dataKoncaZawieszeniaPwz");
                dataKoncaZawieszeniaPwz.setAttribute("xs:nil","true");
                pracownik.appendChild(dataKoncaZawieszeniaPwz);

                Element specjalizacje = document.createElement("specjalizacje");
                pracownik.appendChild(specjalizacje);

                Element specjalizacja = document.createElement("specjalizacja");
                specjalizacje.appendChild(specjalizacja);

                Element kodSpecjalizacjiMedycznej = document.createElement("kodSpecjalizacjiMedycznej");
                kodSpecjalizacjiMedycznej.appendChild(document.createTextNode("LD001-22-0158"));
                specjalizacja.appendChild(kodSpecjalizacjiMedycznej);

                Element nazwaSpecjalizacjiMedycznej = document.createElement("nazwaSpecjalizacjiMedycznej");
                nazwaSpecjalizacjiMedycznej.appendChild(document.createTextNode("Anestezjologia"));
                specjalizacja.appendChild(nazwaSpecjalizacjiMedycznej);

                Element dataUzyskaniaSpecjalizacjiMedycznej = document.createElement("dataUzyskaniaSpecjalizacjiMedycznej");
                dataUzyskaniaSpecjalizacjiMedycznej.appendChild(document.createTextNode("2019-01-01"));
                specjalizacja.appendChild(dataUzyskaniaSpecjalizacjiMedycznej);

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("src/file/prac.xml"));

            transformer.transform(domSource, streamResult);

            System.out.println("Wygenerowane");


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }


    }

}
