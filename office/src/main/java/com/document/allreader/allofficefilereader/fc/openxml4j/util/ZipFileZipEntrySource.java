

package com.document.allreader.allofficefilereader.fc.openxml4j.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * A ZipEntrySource wrapper around a ZipFile.
 * Should be as low in terms of memory as a
 *  normal ZipFile implementation is.
 */
public class ZipFileZipEntrySource implements ZipEntrySource
{
    private ZipFile zipArchive;

    public ZipFileZipEntrySource(ZipFile zipFile)
    {
        this.zipArchive = zipFile;
    }

    public void close() throws IOException
    {
        zipArchive.close();
        zipArchive = null;
    }

    public Enumeration< ? extends ZipEntry> getEntries()
    {
        return zipArchive.entries();
    }

    public InputStream getInputStream(ZipEntry entry) throws IOException
    {
        return zipArchive.getInputStream(entry);
    }
}
