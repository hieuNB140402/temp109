

package com.document.allreader.allofficefilereader.fc.poifs.property;

import java.io.IOException;
import java.io.OutputStream;

import com.document.allreader.allofficefilereader.fc.poifs.common.POIFSBigBlockSize;
import com.document.allreader.allofficefilereader.fc.poifs.filesystem.POIFSFileSystem;
import com.document.allreader.allofficefilereader.fc.poifs.storage.BlockWritable;
import com.document.allreader.allofficefilereader.fc.poifs.storage.HeaderBlock;
import com.document.allreader.allofficefilereader.fc.poifs.storage.PropertyBlock;
import com.document.allreader.allofficefilereader.fc.poifs.storage.RawDataBlockList;



/**
 * This class embodies the Property Table for the {@link POIFSFileSystem}; 
 *  this is basically the directory for all of the documents in the
 * filesystem.
 *
 * @author Marc Johnson (mjohnson at apache dot org)
 */
public final class PropertyTable extends PropertyTableBase implements BlockWritable {
    private POIFSBigBlockSize _bigBigBlockSize;
    private BlockWritable[]   _blocks;

    public PropertyTable(HeaderBlock headerBlock)
    {
        super(headerBlock);
        _bigBigBlockSize = headerBlock.getBigBlockSize();
        _blocks = null;
    }

    /**
     * reading constructor (used when we've read in a file and we want
     * to extract the property table from it). Populates the
     * properties thoroughly
     *
     * @param headerBlock the header block of the file
     * @param blockList the list of blocks
     *
     * @exception IOException if anything goes wrong (which should be
     *            a result of the input being NFG)
     */
    public PropertyTable(final HeaderBlock headerBlock,
                         final RawDataBlockList blockList)
        throws IOException
    {
        super(
              headerBlock,
              PropertyFactory.convertToProperties(
                    blockList.fetchBlocks(headerBlock.getPropertyStart(), -1)
              )
        );
        _bigBigBlockSize = headerBlock.getBigBlockSize();
        _blocks      = null;
    }

    /**
     * Prepare to be written
     */
    public void preWrite()
    {
        Property[] properties = _properties.toArray(new Property[_properties.size()]);

        // give each property its index
        for (int k = 0; k < properties.length; k++)
        {
            properties[ k ].setIndex(k);
        }

        // allocate the blocks for the property table
        _blocks = PropertyBlock.createPropertyBlockArray(_bigBigBlockSize, _properties);

        // prepare each property for writing
        for (int k = 0; k < properties.length; k++)
        {
            properties[ k ].preWrite();
        }
    }
    
    /**
     * Return the number of BigBlock's this instance uses
     *
     * @return count of BigBlock instances
     */
    public int countBlocks()
    {
        return (_blocks == null) ? 0
                                 : _blocks.length;
    }

    /**
     * Write the storage to an OutputStream
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
        if (_blocks != null)
        {
            for (int j = 0; j < _blocks.length; j++)
            {
                _blocks[ j ].writeBlocks(stream);
            }
        }
    }
}
