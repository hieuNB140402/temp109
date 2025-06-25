

package com.document.allreader.allofficefilereader.fc.dom4j.jaxb;


/**
 * DOCUMENT ME!
 * 
 * @author Wonne Keysers (Realsoftware.be)
 */
abstract class JAXBSupport
{
    private String contextPath;

    private ClassLoader classloader;

    //private JAXBContext jaxbContext;

    //private Marshaller marshaller;

    //private Unmarshaller unmarshaller;

    public JAXBSupport(String contextPath)
    {
        this.contextPath = contextPath;
    }

    public JAXBSupport(String contextPath, ClassLoader classloader)
    {
        this.contextPath = contextPath;
        this.classloader = classloader;
    }

    /**
     * Marshals the given {@link javax.xml.bind.Element}in to its DOM4J
     * counterpart.
     * 
     * @param element
     *            JAXB Element to be marshalled
     * 
     * @return the marshalled DOM4J {@link org.dom4j.Element}
     * 
     * @throws JAXBException
     *             when an error occurs
     */
    /*protected Element marshal(javax.xml.bind.Element element) throws JAXBException
    {
        DOMDocument doc = new DOMDocument();
        getMarshaller().marshal(element, doc);

        return doc.getRootElement();
    }

    *//**
     * Unmarshalls the specified DOM4J {@link org.dom4j.Element}into a {@link
     * javax.xml.bind.Element}
     * 
     * @param element
     *            the DOM4J element to unmarshall
     * 
     * @return the unmarshalled JAXB object
     * 
     * @throws JAXBException
     *             when an error occurs
     *//*
    protected javax.xml.bind.Element unmarshal(org.dom4j.Element element) throws JAXBException
    {
        Source source = new StreamSource(new StringReader(element.asXML()));

        return (javax.xml.bind.Element)getUnmarshaller().unmarshal(source);
    }

    private Marshaller getMarshaller() throws JAXBException
    {
        if (marshaller == null)
        {
            marshaller = getContext().createMarshaller();
        }

        return marshaller;
    }

    private Unmarshaller getUnmarshaller() throws JAXBException
    {
        if (unmarshaller == null)
        {
            unmarshaller = getContext().createUnmarshaller();
        }

        return unmarshaller;
    }

    private JAXBContext getContext() throws JAXBException
    {
        if (jaxbContext == null)
        {
            if (classloader == null)
            {
                jaxbContext = JAXBContext.newInstance(contextPath);
            }
            else
            {
                jaxbContext = JAXBContext.newInstance(contextPath, classloader);
            }
        }

        return jaxbContext;
    }*/
}

