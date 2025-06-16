

        

package com.document.allreader.allofficefilereader.fc.poifs.property;

import java.io.IOException;

import java.util.*;

import com.document.allreader.allofficefilereader.fc.poifs.common.POIFSConstants;
import com.document.allreader.allofficefilereader.fc.poifs.storage.ListManagedBlock;



/**
 * Factory for turning an array of RawDataBlock instances containing
 * Property data into an array of proper Property objects.
 *
 * The array produced may be sparse, in that any portion of data that
 * should correspond to a Property, but which does not map to a proper
 * Property (i.e., a DirectoryProperty, DocumentProperty, or
 * RootProperty) will get mapped to a null Property in the array.
 *
 * @author Marc Johnson (mjohnson at apache dot org)
 */

class PropertyFactory
{
    private PropertyFactory()
    {
    }

    /**
     * Convert raw data blocks to an array of Property's
     *
     * @param blocks to be converted
     *
     * @return the converted List of Property objects. May contain
     *         nulls, but will not be null
     *
     * @exception IOException if any of the blocks are empty
     */
    static List<Property> convertToProperties(ListManagedBlock [] blocks)
        throws IOException
    {
        List<Property> properties = new ArrayList<Property>();

        for (int j = 0; j < blocks.length; j++) {
            byte[] data = blocks[ j ].getData();
            convertToProperties(data, properties);
        }
        return properties;
    }
    
    static void convertToProperties(byte[] data, List<Property> properties)
        throws IOException
    {
       int property_count = data.length / POIFSConstants.PROPERTY_SIZE;
       int offset         = 0;

       for (int k = 0; k < property_count; k++) {
          switch (data[ offset + PropertyConstants.PROPERTY_TYPE_OFFSET ]) {
          case PropertyConstants.DIRECTORY_TYPE :
             properties.add(
                   new DirectoryProperty(properties.size(), data, offset)
             );
             break;

          case PropertyConstants.DOCUMENT_TYPE :
             properties.add(
                   new DocumentProperty(properties.size(), data, offset)
             );
             break;

          case PropertyConstants.ROOT_TYPE :
             properties.add(
                   new RootProperty(properties.size(), data, offset)
             );
             break;

          default :
             properties.add(null);
             break;
          }
          
          offset += POIFSConstants.PROPERTY_SIZE;
       }
    }
    
}

