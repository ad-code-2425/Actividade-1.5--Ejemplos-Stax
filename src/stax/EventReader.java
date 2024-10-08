/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author maria
 */
public class EventReader {

    public static void main(String[] args) {
        // primero crea un nuevo XMLInputFactory
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        // Configura un nuevo eventReader a partir del fichero XML
        InputStream in = null;
        try {

          //He modificado la ruta para que funcione
          //in = new FileInputStream("books.xml");
            in = new FileInputStream(Paths.get("src", "stax", "books.xml").toString());
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        try {

            XMLEventReader eventReader = inputFactory.createXMLEventReader(in); //input con i minúscula
            // repetitiva que recorre todos los eventos
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                // si el evento es el inicio del nodo titulo
                // avanzo un evento para obtener el titulo del libro 
                //Faltaba X al comienzo del código
                if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    StartElement startElement = event.asStartElement();
                    //Cambiamos == por equals
                    if ("title".equals(startElement.getName().getLocalPart())) {
                        // if (startElement.getName().getLocalPart() == "title") {
                        Iterator iterator = ((StartElement) event).getAttributes();
                        while (iterator.hasNext()) {
                            Attribute attribute = (Attribute) iterator.next();
                            QName name = attribute.getName();
                            String value = attribute.getValue();
                            //Faltaba name
                            System.out.println("Atributo name/valor: " + name +"/" + value
                            );
                        }
                        event = eventReader.nextEvent();
                        System.out.println((String) event.asCharacters().getData());
                    }
                } else if (event.getEventType() == XMLStreamConstants.END_DOCUMENT) {
                    System.out.println("fin del documento");
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
