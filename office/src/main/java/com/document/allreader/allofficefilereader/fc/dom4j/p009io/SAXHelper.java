package com.document.allreader.allofficefilereader.fc.dom4j.p009io;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/* renamed from: com.allreader.office.allofficefilereader.fc.dom4j.io.SAXHelper */

class SAXHelper {
    private static boolean loggedWarning = true;

    protected SAXHelper() {
    }

    public static boolean setParserProperty(XMLReader xMLReader, String str, Object obj) {
        try {
            xMLReader.setProperty(str, obj);
            return true;
        } catch (SAXNotRecognizedException | SAXNotSupportedException unused) {
            return false;
        }
    }

    public static boolean setParserFeature(XMLReader xMLReader, String str, boolean z) {
        try {
            xMLReader.setFeature(str, z);
            return true;
        } catch (SAXNotRecognizedException | SAXNotSupportedException unused) {
            return false;
        }
    }

    public static XMLReader createXMLReader(boolean z) throws SAXException {
        XMLReader createXMLReaderViaJAXP = createXMLReaderViaJAXP(z, true);
        if (createXMLReaderViaJAXP == null) {
            try {
                createXMLReaderViaJAXP = XMLReaderFactory.createXMLReader();
            } catch (Exception e) {
                if (isVerboseErrorReporting()) {
                    e.printStackTrace();
                }
                throw new SAXException(e);
            }
        }
        if (createXMLReaderViaJAXP != null) {
            return createXMLReaderViaJAXP;
        }
        throw new SAXException("Couldn't create SAX reader");
    }

    protected static XMLReader createXMLReaderViaJAXP(boolean z, boolean z2) {
        try {
            return JAXPHelper.createXMLReader(z, z2);
        } catch (Throwable th) {
            if (loggedWarning) {
                return null;
            }
            loggedWarning = true;
            if (!isVerboseErrorReporting()) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    protected static boolean isVerboseErrorReporting() {
        try {
            String property = System.getProperty("org.dom4j.verbose");
            if (property == null || property.equalsIgnoreCase("true")) {
            }
        } catch (Exception unused) {
        }
        return true;
    }
}
