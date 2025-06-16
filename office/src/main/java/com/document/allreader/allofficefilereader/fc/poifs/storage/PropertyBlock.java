

package com.document.allreader.allofficefilereader.fc.poifs.storage;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.poifs.common.POIFSBigBlockSize;
import com.document.allreader.allofficefilereader.fc.poifs.property.Property;



/**
 * A block of Property instances
 *
 * @author Marc Johnson (mjohnson at apache dot org)
 */
public final class PropertyBlock extends BigBlock {
    private Property[]       _properties;

    /**
     * Create a single instance initialized with default values
     *
     * @param properties the properties to be inserted
     * @param offset the offset into the properties array
     */

    private PropertyBlock(final POIFSBigBlockSize bigBlockSize, final Property [] properties, final int offset)
    {
        super(bigBlockSize);
        
        _properties = new Property[ bigBlockSize.getPropertiesPerBlock() ]; 
        for (int j = 0; j < _properties.length; j++)
        {
            _properties[ j ] = properties[ j + offset ];
        }
    }

    /**
     * Create an array of PropertyBlocks from an array of Property
     * instances, creating empty Property instances to make up any
     * shortfall
     *
     * @param properties the Property instances to be converted into
     *                   PropertyBlocks, in a java List
     *
     * @return the array of newly created PropertyBlock instances
     */

    public static BlockWritable [] createPropertyBlockArray(
            final POIFSBigBlockSize bigBlockSize, final List<Property> properties)
    {
        int _properties_per_block = bigBlockSize.getPropertiesPerBlock();
        int        block_count   =
            (properties.size() + _properties_per_block - 1)
            / _properties_per_block;
        Property[] to_be_written =
            new Property[ block_count * _properties_per_block ];

        System.arraycopy(properties.toArray(new Property[ 0 ]), 0,
                         to_be_written, 0, properties.size());
        for (int j = properties.size(); j < to_be_written.length; j++)
        {

            // create an instance of an anonymous inner class that
            // extends Property
            to_be_written[ j ] = new Property()
            {
                protected void preWrite()
                {
                }

                public boolean isDirectory()
                {
                    return false;
                }
            };
        }
        BlockWritable[] rvalue = new BlockWritable[ block_count ];

        for (int j = 0; j < block_count; j++)
        {
            rvalue[ j ] = new PropertyBlock(bigBlockSize, to_be_written,
                                            j * _properties_per_block);
        }
        return rvalue;
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

    void writeData(final OutputStream stream)
        throws IOException
    {
        int _properties_per_block = bigBlockSize.getPropertiesPerBlock();
        for (int j = 0; j < _properties_per_block; j++)
        {
            _properties[ j ].writeData(stream);
        }
    }


}

