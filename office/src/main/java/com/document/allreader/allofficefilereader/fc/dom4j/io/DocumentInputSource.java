

package com.document.allreader.allofficefilereader.fc.dom4j.io;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.xml.sax.InputSource;

import com.document.allreader.allofficefilereader.fc.dom4j.Document;



class DocumentInputSource extends InputSource
{
    private Document document;

    public DocumentInputSource()
    {
    }

    public DocumentInputSource(Document document)
    {
        this.document = document;
        setSystemId(document.getName());
    }



    public Document getDocument()
    {
        return document;
    }


    public void setDocument(Document document)
    {
        this.document = document;
        setSystemId(document.getName());
    }



    public void setCharacterStream(Reader characterStream) throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }


    public Reader getCharacterStream()
    {
        try
        {
            StringWriter out = new StringWriter();
            XMLWriter writer = new XMLWriter(out);
            writer.write(document);
            writer.flush();

            return new StringReader(out.toString());
        }
        catch(final IOException e)
        {

            return new Reader()
            {
                public int read(char[] ch, int offset, int length) throws IOException
                {
                    throw e;
                }

                public void close() throws IOException
                {
                }
            };
        }
    }
}

