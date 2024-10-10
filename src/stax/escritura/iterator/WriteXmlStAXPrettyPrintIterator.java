/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stax.escritura.iterator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Exemplos obtidos da URL modificados mínimamente (rutas de arquivos)
 * @author https://mkyong.com/java/how-to-write-xml-file-in-java-stax-writer/
 */
public class WriteXmlStAXPrettyPrintIterator {

    public static void main(String[] args) {

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // write XML to ByteArrayOutputStream
            writeXml2(out);

            // Java 10
            //String xml = out.toString(StandardCharsets.UTF_8);
            // standard way to convert byte array to String
            String xml = new String(out.toByteArray(), StandardCharsets.UTF_8);

            // System.out.println(formatXML(xml));
            String prettyPrintXML = formatXML(xml);

            // print to console
            // System.out.println(prettyPrintXML);
            // Java 11 - write to file
            Files.writeString(Paths.get("testIteratorPrettyPrint.xml"),
                    prettyPrintXML, StandardCharsets.UTF_8);

            // Java 7 - write to file
            //Files.write(Paths.get("/home/mkyong/test.xml"),
            //    prettyPrintXML.getBytes(StandardCharsets.UTF_8));
            // BufferedWriter - write to file
            /*try (FileWriter writer = new FileWriter("/home/mkyong/test.xml");
                 BufferedWriter bw = new BufferedWriter(writer)) {
                bw.write(xml);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        } catch (TransformerException | XMLStreamException | IOException e) {
            e.printStackTrace();
        }

    }

    
    // StAX Iterator API
    private static void writeXml2(OutputStream out) throws XMLStreamException {

        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();

        // StAX Iterator API
        XMLEventWriter writer = output.createXMLEventWriter(out);

        XMLEvent event = eventFactory.createStartDocument();
        // default
        //event = eventFactory.createStartDocument("utf-8", "1.0");
        writer.add(event);

        writer.add(eventFactory.createStartElement("", "", "company"));

        // write XML comment
        writer.add(eventFactory.createComment("This is staff 1001"));

        writer.add(eventFactory.createStartElement("", "", "staff"));
        // write XML attribute
        writer.add(eventFactory.createAttribute("id", "1001"));

        writer.add(eventFactory.createStartElement("", "", "name"));
        writer.add(eventFactory.createCharacters("mkyong"));
        writer.add(eventFactory.createEndElement("", "", "name"));

        writer.add(eventFactory.createStartElement("", "", "salary"));
        writer.add(eventFactory.createAttribute("currency", "USD"));
        writer.add(eventFactory.createCharacters("5000"));
        writer.add(eventFactory.createEndElement("", "", "salary"));

        writer.add(eventFactory.createStartElement("", "", "bio"));
        // write XML CData
        writer.add(eventFactory.createCData("HTML tag <code>testing</code>"));
        writer.add(eventFactory.createEndElement("", "", "bio"));

        // </staff>
        writer.add(eventFactory.createEndElement("", "", "staff"));

        // next staff, tired to write more
        // writer.add(eventFactory.createStartElement("", "", "staff"));
        // writer.add(eventFactory.createAttribute("id", "1002"));
        // writer.add(eventFactory.createEndElement("", "", "staff"));

        // end here.
        writer.add(eventFactory.createEndDocument());

        writer.flush();

        writer.close();

    }


    private static String formatXML(String xml) throws TransformerException {

        // write data to xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print by indention
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // add standalone="yes", add line break before the root element
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

        StreamSource source = new StreamSource(new StringReader(xml));
        StringWriter output = new StringWriter();
        transformer.transform(source, new StreamResult(output));

        return output.toString();

    }

}
