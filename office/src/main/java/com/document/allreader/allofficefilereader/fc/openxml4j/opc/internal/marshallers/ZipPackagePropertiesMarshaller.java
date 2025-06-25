

package com.document.allreader.allofficefilereader.fc.openxml4j.opc.internal.marshallers;


/**
 * Package core properties marshaller specialized for zipped package.
 *
 * @author Julien Chable
 */
public final class ZipPackagePropertiesMarshaller extends PackagePropertiesMarshaller
{

    /*@ Override
    public boolean marshall(PackagePart part, OutputStream out) throws OpenXML4JException
    {
        if (!(out instanceof ZipOutputStream))
        {
            throw new IllegalArgumentException("ZipOutputStream expected!");
        }
        ZipOutputStream zos = (ZipOutputStream)out;

        // Saving the part in the zip file
        ZipEntry ctEntry = new ZipEntry(ZipHelper.getZipItemNameFromOPCName(part.getPartName()
            .getURI().toString()));
        try
        {
            // Save in ZIP
            zos.putNextEntry(ctEntry); // Add entry in ZIP
            super.marshall(part, out); // Marshall the properties inside a XML
            // Document
            if (!StreamHelper.saveXmlInStream(xmlDoc, out))
            {
                return false;
            }
            zos.closeEntry();
        }
        catch(IOException e)
        {
            throw new OpenXML4JException(e.getLocalizedMessage());
        }
        return true;
    }*/
}
