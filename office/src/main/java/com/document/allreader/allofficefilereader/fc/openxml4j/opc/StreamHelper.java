

package com.document.allreader.allofficefilereader.fc.openxml4j.opc;


public final class StreamHelper
{/*

    private StreamHelper()
    {
        // Do nothing
    }

    *//**
     * Turning the DOM4j object in the specified output stream.
     *
     * @param xmlContent
     *            The XML document.
     * @param outStream
     *            The OutputStream in which the XML document will be written.
     * @return <b>true</b> if the xml is successfully written in the stream,
     *         else <b>false</b>.
     *//*
    public static boolean saveXmlInStream(Document xmlContent, OutputStream outStream)
    {
        try
        {
            OutputFormat outformat = OutputFormat.createPrettyPrint();
            outformat.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(outStream, outformat);
            writer.write(xmlContent);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    *//**
     * Copy the input stream into the output stream.
     *
     * @param inStream
     *            The source stream.
     * @param outStream
     *            The destination stream.
     * @return <b>true</b> if the operation succeed, else return <b>false</b>.
     *//*
    public static boolean copyStream(InputStream inStream, OutputStream outStream)
    {
        try
        {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) >= 0)
            {
                outStream.write(buffer, 0, bytesRead);
            }
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }
*/}
