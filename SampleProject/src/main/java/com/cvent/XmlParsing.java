package com.cvent;

import com.cvent.respondent.answers.SurveyResponse;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import com.cvent.respondent.answers.ANSWERS;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;


/**
 * Created by a.srivastava on 3/16/16.
 */
public class XmlParsing {
    private static String xmlString = "<ANSWERS xmlns=\"http://schema.cvent.com/surveys/XMLSchema.xsd\"><Answer answ_text=\"Website\"/></ANSWERS>";
    public static final XmlMapper MAPPER = new XmlMapper();
    public static void main (String[] args) {
        parseFasterXml();
        //parseFasterXml1();
        //parseFasterXml2();
    }

    private static void parseFasterXml2() {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(toInputStream(xmlString));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    private static void parseFasterXml1() {
        try {
            JAXBContext jaxbContext =  JAXBContext.newInstance(ANSWERS.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ANSWERS ans = (ANSWERS) jaxbUnmarshaller.unmarshal(toInputStream(xmlString));

            System.out.printf(ans.getAnswer().get(0).getAnswText());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static InputStream toInputStream(String xmlString) {
        return  new ByteArrayInputStream(xmlString.toString().trim().getBytes(Charset.defaultCharset()));
    }

    private static void parseFasterXml() {
        XmlMapper mapper = new XmlMapper();
        SurveyResponse ans = null;
        try {
            ans = mapper.readValue(xmlString, SurveyResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
