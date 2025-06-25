

package com.document.allreader.allofficefilereader.fc.poifs.common;

import com.document.allreader.allofficefilereader.fc.util.LittleEndianConsts;

/**
 * <p>A class describing attributes of the Big Block Size</p>
 */
public final class POIFSBigBlockSize
{
    private int bigBlockSize;
    private short headerValue;

    protected POIFSBigBlockSize(int bigBlockSize, short headerValue)
    {
        this.bigBlockSize = bigBlockSize;
        this.headerValue = headerValue;
    }

    public int getBigBlockSize()
    {
        return bigBlockSize;
    }

    /**
     * Returns the value that gets written into the 
     *  header.
     * Is the power of two that corresponds to the
     *  size of the block, eg 512 => 9
     */
    public short getHeaderValue()
    {
        return headerValue;
    }

    public int getPropertiesPerBlock()
    {
        return bigBlockSize / POIFSConstants.PROPERTY_SIZE;
    }

    public int getBATEntriesPerBlock()
    {
        return bigBlockSize / LittleEndianConsts.INT_SIZE;
    }

    public int getXBATEntriesPerBlock()
    {
        return getBATEntriesPerBlock() - 1;
    }

    public int getNextXBATChainOffset()
    {
        return getXBATEntriesPerBlock() * LittleEndianConsts.INT_SIZE;
    }
}
