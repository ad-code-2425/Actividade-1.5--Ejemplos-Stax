/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stax.escritura.iterator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * Exemplos obtidos da URL modificados mínimamente (rutas de arquivos)
 * @author https://mkyong.com/java/how-to-write-xml-file-in-java-stax-writer/
 */
public class WriteXmlStAXIterator {

    public static void main(String[] args) throws XMLStreamException {

        // send the output to a xml file
        try(FileOutputStream out = new FileOutputStream("testIterator.xml")){
            writeXml2(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // send the output to System.out
        // writeXml2(System.out);

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

}
