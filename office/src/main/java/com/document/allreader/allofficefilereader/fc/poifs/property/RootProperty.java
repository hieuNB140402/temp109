

package com.document.allreader.allofficefilereader.fc.poifs.property;


import com.document.allreader.allofficefilereader.fc.poifs.common.POIFSConstants;
import com.document.allreader.allofficefilereader.fc.poifs.storage.SmallDocumentBlock;


/**
 * Root property
 *
 * @author Marc Johnson (mjohnson at apache dot org)
 */
public final class RootProperty extends DirectoryProperty {
   private static final String NAME = "Root Entry";

    RootProperty()
    {
        super(NAME);

        // overrides
        setNodeColor(_NODE_BLACK);
        setPropertyType(PropertyConstants.ROOT_TYPE);
        setStartBlock(POIFSConstants.END_OF_CHAIN);
    }

    /**
     * reader constructor
     *
     * @param index index number
     * @param array byte data
     * @param offset offset into byte data
     */
    protected RootProperty(final int index, final byte [] array,
                           final int offset)
    {
        super(index, array, offset);
    }

    /**
     * set size
     *
     * @param size size in terms of small blocks
     */
    public void setSize(int size)
    {
        super.setSize(SmallDocumentBlock.calcSize(size));
    }

    /**
     * Returns the fixed name "Root Entry", as the
     *  raw property doesn't have a real name set
     */
    @Override
    public String getName() {
        return NAME;
    }
}
