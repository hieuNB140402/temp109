

package com.document.allreader.allofficefilereader.fc.openxml4j.opc.internal;

import java.io.IOException;
import java.io.InputStream;

import com.document.allreader.allofficefilereader.fc.openxml4j.exceptions.InvalidFormatException;
import com.document.allreader.allofficefilereader.fc.openxml4j.exceptions.OpenXML4JException;
import com.document.allreader.allofficefilereader.fc.openxml4j.opc.PackagePart;
import com.document.allreader.allofficefilereader.fc.openxml4j.opc.internal.unmarshallers.UnmarshallContext;


/**
 * Object implemented this interface are considered as part unmarshaller. A part
 * unmarshaller is responsible to unmarshall a part in order to load it from a
 * package.
 *
 * @author Julien Chable
 * @version 0.1
 */
public interface PartUnmarshaller
{

    /**
     * Save the content of the package in the stream
     *
     * @param in
     *            The input stream from which the part will be unmarshall.
     * @return The part freshly unmarshall from the input stream.
     * @throws OpenXML4JException
     *             Throws only if any other exceptions are thrown by inner
     *             methods.
     */
    public PackagePart unmarshall(UnmarshallContext context, InputStream in)
        throws InvalidFormatException, IOException;
}
