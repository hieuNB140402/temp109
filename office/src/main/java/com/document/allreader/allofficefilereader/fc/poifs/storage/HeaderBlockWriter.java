

        

package com.document.allreader.allofficefilereader.fc.poifs.storage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.document.allreader.allofficefilereader.fc.poifs.common.POIFSBigBlockSize;
import com.document.allreader.allofficefilereader.fc.poifs.common.POIFSConstants;


/**
 * The block containing the archive header
 *
 * @author Marc Johnson (mjohnson at apache dot org)
 */
public class HeaderBlockWriter implements HeaderBlockConstants, BlockWritable
{
   private final HeaderBlock _header_block;

    /**
     * Create a single instance initialized with default values
     */
    public HeaderBlockWriter(POIFSBigBlockSize bigBlockSize)
    {
       _header_block = new HeaderBlock(bigBlockSize);
    }

    /**
     * Create a single instance initialized with the specified 
     *  existing values
     */
    public HeaderBlockWriter(HeaderBlock headerBlock)
    {
       _header_block = headerBlock;
    }

    /**
     * Set BAT block parameters. Assumes that all BAT blocks are
     * contiguous. Will construct XBAT blocks if necessary and return
     * the array of newly constructed XBAT blocks.
     *
     * @param blockCount count of BAT blocks
     * @param startBlock index of first BAT block
     *
     * @return array of XBAT blocks; may be zero length, will not be
     *         null
     */

    public BATBlock [] setBATBlocks(final int blockCount,
                                    final int startBlock)
    {
        BATBlock[] rvalue;
        POIFSBigBlockSize bigBlockSize = _header_block.getBigBlockSize();

        _header_block.setBATCount(blockCount);

        // Set the BAT locations
        int limit  = Math.min(blockCount, _max_bats_in_header);
        int[] bat_blocks = new int[limit];
        for (int j = 0; j < limit; j++) {
           bat_blocks[j] = startBlock + j;
        }
        _header_block.setBATArray(bat_blocks);
        
        // Now do the XBATs
        if (blockCount > _max_bats_in_header)
        {
            int   excess_blocks      = blockCount - _max_bats_in_header;
            int[] excess_block_array = new int[ excess_blocks ];

            for (int j = 0; j < excess_blocks; j++)
            {
                excess_block_array[ j ] = startBlock + j
                                          + _max_bats_in_header;
            }
            rvalue = BATBlock.createXBATBlocks(bigBlockSize, excess_block_array,
                                               startBlock + blockCount);
            _header_block.setXBATStart(startBlock + blockCount);
        }
        else
        {
            rvalue = BATBlock.createXBATBlocks(bigBlockSize, new int[ 0 ], 0);
            _header_block.setXBATStart(POIFSConstants.END_OF_CHAIN);
        }
        _header_block.setXBATCount(rvalue.length);
        return rvalue;
    }

    /**
     * Set start of Property Table
     *
     * @param startBlock the index of the first block of the Property
     *                   Table
     */
    public void setPropertyStart(final int startBlock)
    {
       _header_block.setPropertyStart(startBlock);
    }

    /**
     * Set start of small block allocation table
     *
     * @param startBlock the index of the first big block of the small
     *                   block allocation table
     */
    public void setSBATStart(final int startBlock)
    {
        _header_block.setSBATStart(startBlock);
    }

    /**
     * Set count of SBAT blocks
     *
     * @param count the number of SBAT blocks
     */
    public void setSBATBlockCount(final int count)
    {
       _header_block.setSBATBlockCount(count);
    }

    /**
     * For a given number of BAT blocks, calculate how many XBAT
     * blocks will be needed
     *
     * @param blockCount number of BAT blocks
     *
     * @return number of XBAT blocks needed
     */

    static int calculateXBATStorageRequirements(POIFSBigBlockSize bigBlockSize, final int blockCount)
    {
        return (blockCount > _max_bats_in_header)
               ? BATBlock.calculateXBATStorageRequirements(
                     bigBlockSize, blockCount - _max_bats_in_header)
               : 0;
    }

    /* ********** START extension of BigBlock ********** */

    /**
     * Write the block's data to an OutputStream
     *
     * @param stream the OutputStream to which the stored data should
     *               be written
     *
     * @exception IOException on problems writing to the specified
     *            stream
     */
    public void writeBlocks(final OutputStream stream)
        throws IOException
    {
        _header_block.writeData(stream);
    }
    
    /**
     * Write the block's data to an existing block
     *
     * @param block the ByteBuffer of the block to which the 
     *               stored data should be written
     *
     * @exception IOException on problems writing to the block
     */
    public void writeBlock(ByteBuffer block)
        throws IOException
    {
       ByteArrayOutputStream baos = new ByteArrayOutputStream(
             _header_block.getBigBlockSize().getBigBlockSize()
       );
       _header_block.writeData(baos);
       
       block.put(baos.toByteArray());
    }

    /* **********  END  extension of BigBlock ********** */
}   // end public class HeaderBlockWriter

