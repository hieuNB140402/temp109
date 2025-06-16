

package com.document.allreader.allofficefilereader.fc.poifs.storage;

import java.io.IOException;

/**
 * A simple implementation of BlockList
 *
 * @author Marc Johnson (mjohnson at apache dot org
 */
abstract class BlockListImpl implements BlockList
{
    private ListManagedBlock[] _blocks;
    private BlockAllocationTableReader _bat;

    protected BlockListImpl()
    {
        _blocks = new ListManagedBlock[0];
        _bat = null;
    }

    /**
     * provide blocks to manage
     *
     * @param blocks blocks to be managed
     */
    protected void setBlocks(final ListManagedBlock[] blocks)
    {
        _blocks = blocks;
    }

    /**
     * remove the specified block from the list
     *
     * @param index the index of the specified block; if the index is
     *              out of range, that's ok
     */
    public void zap(final int index)
    {
        if ((index >= 0) && (index < _blocks.length))
        {
            _blocks[index] = null;
        }
    }

    /**
     * Unit testing method. Gets, without sanity checks or
     *  removing.
     */
    protected ListManagedBlock get(final int index)
    {
        return _blocks[index];
    }

    /**
     * remove and return the specified block from the list
     *
     * @param index the index of the specified block
     *
     * @return the specified block
     *
     * @exception IOException if the index is out of range or has
     *            already been removed
     */
    public ListManagedBlock remove(final int index) throws IOException
    {
        ListManagedBlock result = null;

        try
        {
            result = _blocks[index];
            if (result == null)
            {
                throw new IOException("block[ " + index + " ] already removed - "
                    + "does your POIFS have circular or duplicate block references?");
            }
            _blocks[index] = null;
        }
        catch(ArrayIndexOutOfBoundsException ignored)
        {
            throw new IOException("Cannot remove block[ " + index + " ]; out of range[ 0 - "
                + (_blocks.length - 1) + " ]");
        }
        return result;
    }

    /**
     * get the blocks making up a particular stream in the list. The
     * blocks are removed from the list.
     *
     * @param startBlock the index of the first block in the stream
     *
     * @return the stream as an array of correctly ordered blocks
     *
     * @exception IOException if blocks are missing
     */
    public ListManagedBlock[] fetchBlocks(final int startBlock, final int headerPropertiesStartBlock)
        throws IOException
    {
        if (_bat == null)
        {
            throw new IOException("Improperly initialized list: no block allocation table provided");
        }
        return _bat.fetchBlocks(startBlock, headerPropertiesStartBlock, this);
    }

    /**
     * set the associated BlockAllocationTable
     *
     * @param bat the associated BlockAllocationTable
     */
    public void setBAT(final BlockAllocationTableReader bat) throws IOException
    {
        if (_bat != null)
        {
            throw new IOException("Attempt to replace existing BlockAllocationTable");
        }
        _bat = bat;
    }

    /**
     * Returns the count of the number of blocks
     */
    public int blockCount()
    {
        return _blocks.length;
    }

    /**
     * Returns the number of remaining blocks
     */
    protected int remainingBlocks()
    {
        int c = 0;
        for (int i = 0; i < _blocks.length; i++)
        {
            if (_blocks[i] != null)
                c++;
        }
        return c;
    }
}
