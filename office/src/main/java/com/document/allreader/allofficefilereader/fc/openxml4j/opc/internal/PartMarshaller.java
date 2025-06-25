

package com.document.allreader.allofficefilereader.fc.openxml4j.opc.internal;

import java.io.OutputStream;

import com.document.allreader.allofficefilereader.fc.openxml4j.exceptions.OpenXML4JException;
import com.document.allreader.allofficefilereader.fc.openxml4j.opc.PackagePart;


/**
 * Object implemented this interface are considered as part marshaller. A part
 * marshaller is responsible to marshall a part in order to be save in a
 * package.
 *
 * @author Julien Chable
 * @version 0.1
 */
public interface PartMarshaller
{

    /**
     * Save the content of the package in the stream
     *
     * @param part
     *            Part to marshall.
     * @param out
     *            The output stream into which the part will be marshall.
     * @return <b>false</b> if any marshall error occurs, else <b>true</b>
     * @throws OpenXML4JException
     *             Throws only if any other exceptions are thrown by inner
     *             methods.
     */
    public boolean marshall(PackagePart part, OutputStream out) throws OpenXML4JException;
}
