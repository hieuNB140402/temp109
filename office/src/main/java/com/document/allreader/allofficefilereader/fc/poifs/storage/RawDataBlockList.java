

        

package com.document.allreader.allofficefilereader.fc.poifs.storage;

import java.io.*;

import java.util.*;

import com.document.allreader.allofficefilereader.fc.poifs.common.POIFSBigBlockSize;


/**
 * A list of RawDataBlocks instances, and methods to manage the list
 *
 * @author Marc Johnson (mjohnson at apache dot org
 */

public class RawDataBlockList
    extends BlockListImpl
{

    /**
     * Constructor RawDataBlockList
     *
     * @param stream the InputStream from which the data will be read
     * @param bigBlockSize The big block size, either 512 bytes or 4096 bytes
     *
     * @exception IOException on I/O errors, and if an incomplete
     *            block is read
     */

    public RawDataBlockList(final InputStream stream, POIFSBigBlockSize bigBlockSize)
        throws IOException
    {
        List<RawDataBlock> blocks = new ArrayList<RawDataBlock>();

        while (true)
        {
            RawDataBlock block = new RawDataBlock(stream, bigBlockSize.getBigBlockSize());
            

            if(block.hasData()) {
            	blocks.add(block);
            }

            if (block.eof()) {
                break;
            }
        }
        setBlocks( blocks.toArray(new RawDataBlock[ blocks.size() ]) );
    }
}   // end public class RawDataBlockList

