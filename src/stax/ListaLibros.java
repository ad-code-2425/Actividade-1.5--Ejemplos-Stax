/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package stax;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
//El programa java que recorre el documento para extraer los titulos y el atributo lang es el siguiente

public class ListaLibros {

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
// Creamos el flujo
        XMLInputFactory xmlif = XMLInputFactory.newInstance();
        XMLStreamReader xmlsr = xmlif.createXMLStreamReader(new FileReader(Paths.get("src", "stax", "books.xml").toString()));
        String tag;
        int eventType;
        
        
        System.out.println("Lista de libros");
// iteramos con el cursor a lo largo del documento
        while (xmlsr.hasNext()) {
            eventType = xmlsr.next();
            switch (eventType) {
                case XMLEvent.START_ELEMENT:
                    tag = xmlsr.getLocalName();
                    if (tag.equals("title")) {
                        //Hubo que obtener primero el atributo y luego llamar a getElementText()

                        String atLang = xmlsr.getAttributeValue(0);
                        String texto = xmlsr.getElementText();
                        System.out.println(texto + " lang " + atLang); //cambio de idioma por lang, pero aún con 
                        //excepción:
//                        xception in thread "main" java.lang.IllegalStateException: Current state is not among the states START_ELEMENT , ATTRIBUTEvalid for getAttributeValue()
//	at com.sun.org.apache.xerces.internal.impl.XMLStreamReaderImpl.getAttributeValue(XMLStreamReaderImpl.java:802)
//	at stax.ListaLibros.main(ListaLibros.java:32)

//Según la documentación (https://download.java.net/java/early_access/genzgc/docs/api/java.xml/javax/xml/stream/XMLStreamReader.html#getElementText()--)
//, después de llamar al método getElementText() el evento toma el valor END_ELEMENT
// y si os fijáis en la tabla de la documentación https://download.java.net/java/early_access/genzgc/docs/api/java.xml/javax/xml/stream/XMLStreamReader.html, no está permitido llamar a getAttributeValue() en END_ELEMENT
                    }
                    
                    break;

                case XMLEvent.ATTRIBUTE:

                    if (xmlsr.getAttributeName(0).toString().equals("lang")) {
                        String lang = xmlsr.getAttributeValue(0);
                        System.out.println("Atributo lang: " + lang);
                    }
                  
                    System.out.println(xmlsr.getAttributeName(0));
                    break;
                case XMLEvent.END_DOCUMENT:
                    System.out.println("Fin del documento");
                    break;
            }
        }
    }
}
