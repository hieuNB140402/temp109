

        


package com.document.allreader.allofficefilereader.fc.poifs.filesystem;

/**
 * This exception is thrown when we try to open a file that's actually
 *  an Office 2007+ XML file, rather than an OLE2 file (which is what
 *  POI works with)
 *
 * @author Nick Burch
 */

public class OfficeXmlFileException extends IllegalArgumentException
{
	public OfficeXmlFileException(String s) {
		super(s);
	}
}
