
package com.document.allreader.allofficefilereader.fc.openxml4j.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

/**
 * An Interface to make getting the different bits
 *  of a Zip File easy.
 * Allows you to get at the ZipEntries, without
 *  needing to worry about ZipFile vs ZipInputStream
 *  being annoyingly very different.
 */
public interface ZipEntrySource {
	/**
	 * Returns an Enumeration of all the Entries
	 */
	public Enumeration<? extends ZipEntry> getEntries();
	
	/**
	 * Returns an InputStream of the decompressed 
	 *  data that makes up the entry
	 */
	public InputStream getInputStream(ZipEntry entry) throws IOException;
	
	/**
	 * Indicates we are done with reading, and 
	 *  resources may be freed
	 */
	public void close() throws IOException;
}
