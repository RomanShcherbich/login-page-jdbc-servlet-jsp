package utils;

import javax.xml.bind.*;
import java.io.StringWriter;

public class JaxbUtils {

    public static String xmlToString(Object xml) throws JAXBException {
        StringWriter writer = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(xml.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.marshal(xml, writer);
        return writer.toString();
    }

}
