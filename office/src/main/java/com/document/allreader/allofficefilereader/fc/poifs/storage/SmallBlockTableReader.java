

package com.document.allreader.allofficefilereader.fc.poifs.storage;

import java.io.IOException;

import com.document.allreader.allofficefilereader.fc.poifs.common.POIFSBigBlockSize;
import com.document.allreader.allofficefilereader.fc.poifs.property.RootProperty;



/**
 * This class implements reading the small document block list from an
 * existing file
 *
 * @author Marc Johnson (mjohnson at apache dot org)
 */
public final class SmallBlockTableReader {

    /**
     * fetch the small document block list from an existing file
     *
     * @param blockList the raw data from which the small block table
     *                  will be extracted
     * @param root the root property (which contains the start block
     *             and small block table size)
     * @param sbatStart the start block of the SBAT
     *
     * @return the small document block list
     *
     * @exception IOException
     */
    public static BlockList getSmallDocumentBlocks(
            final POIFSBigBlockSize bigBlockSize,
            final RawDataBlockList blockList, final RootProperty root,
            final int sbatStart)
        throws IOException
    {
       // Fetch the blocks which hold the Small Blocks stream
       ListManagedBlock [] smallBlockBlocks = 
          blockList.fetchBlocks(root.getStartBlock(), -1);
        
       // Turn that into a list
       BlockList list =new SmallDocumentBlockList(
             SmallDocumentBlock.extract(bigBlockSize, smallBlockBlocks));

       // Process
        new BlockAllocationTableReader(bigBlockSize,
                                       blockList.fetchBlocks(sbatStart, -1),
                                       list);
        return list;
    }
}
