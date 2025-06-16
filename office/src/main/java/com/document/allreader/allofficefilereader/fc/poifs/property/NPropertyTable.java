

package com.document.allreader.allofficefilereader.fc.poifs.property;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.poifs.common.POIFSBigBlockSize;
import com.document.allreader.allofficefilereader.fc.poifs.common.POIFSConstants;
import com.document.allreader.allofficefilereader.fc.poifs.filesystem.NPOIFSFileSystem;
import com.document.allreader.allofficefilereader.fc.poifs.filesystem.NPOIFSStream;
import com.document.allreader.allofficefilereader.fc.poifs.storage.HeaderBlock;



/**
 * This class embodies the Property Table for a {@link NPOIFSFileSystem}; 
 *  this is basically the directory for all of the documents in the
 * filesystem.
 */
public final class NPropertyTable extends PropertyTableBase {
    private POIFSBigBlockSize _bigBigBlockSize;

    public NPropertyTable(HeaderBlock headerBlock)
    {
        super(headerBlock);
        _bigBigBlockSize = headerBlock.getBigBlockSize();
    }

    /**
     * reading constructor (used when we've read in a file and we want
     * to extract the property table from it). Populates the
     * properties thoroughly
     *
     * @param headerBlock the header block of the file
     * @param filesystem the filesystem to read from
     *
     * @exception IOException if anything goes wrong (which should be
     *            a result of the input being NFG)
     */
    public NPropertyTable(final HeaderBlock headerBlock,
                          final NPOIFSFileSystem filesystem)
        throws IOException
    {
        super(
              headerBlock,
              buildProperties(
                    (new NPOIFSStream(filesystem, headerBlock.getPropertyStart())).iterator(),
                    headerBlock.getBigBlockSize()
              )
        );
        _bigBigBlockSize = headerBlock.getBigBlockSize();
    }
    
    /**
     * Builds
     * @param startAt
     * @param filesystem
     * @return
     * @throws IOException
     */
    private static List<Property> buildProperties(final Iterator<ByteBuffer> dataSource,
          final POIFSBigBlockSize bigBlockSize) throws IOException
    {
       List<Property> properties = new ArrayList<Property>();
       while(dataSource.hasNext()) {
          ByteBuffer bb = dataSource.next();
          
          // Turn it into an array
          byte[] data;
          if(bb.hasArray() && bb.arrayOffset() == 0 && 
                bb.array().length == bigBlockSize.getBigBlockSize()) {
             data = bb.array();
          } else {
             data = new byte[bigBlockSize.getBigBlockSize()];
             bb.get(data, 0, data.length);
          }
          
          PropertyFactory.convertToProperties(data, properties);
       }
       return properties;
    }

    /**
     * Return the number of BigBlock's this instance uses
     *
     * @return count of BigBlock instances
     */
    public int countBlocks()
    {
       int size = _properties.size() * POIFSConstants.PROPERTY_SIZE;
       return (int)Math.ceil(size / _bigBigBlockSize.getBigBlockSize());
    }
 
    /**
     * Writes the properties out into the given low-level stream
     */
    public void write(NPOIFSStream stream) throws IOException {
       // TODO - Use a streaming write
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       for(Property property : _properties) {
          if(property != null) {
             property.writeData(baos);
          }
       }
       stream.updateContents(baos.toByteArray());
       
       // Update the start position if needed
       if(getStartBlock() != stream.getStartBlock()) {
          setStartBlock(stream.getStartBlock());
       }
    }
}
