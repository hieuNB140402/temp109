


package com.document.allreader.allofficefilereader.fc.hssf.util;

/**
 * Represents a from/to row/col square.  This is a object primitive
 * that can be used to represent row,col - row,col just as one would use String
 * to represent a string of characters.  Its really only useful for HSSF though.
 *
 * @author  Andrew C. Oliver acoliver at apache dot org
 */

public class Region extends com.document.allreader.allofficefilereader.fc.ss.util.Region
{
    /**
     * Creates a new instance of Region (0,0 - 0,0)
     */
    public Region()
    {
		super();
    }

    public Region(int rowFrom, short colFrom, int rowTo, short colTo)
    {
		super(rowFrom, colFrom, rowTo, colTo);
    }

    public Region(String ref) {
		super(ref);
	}
}
