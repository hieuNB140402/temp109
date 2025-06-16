

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.ArrayList;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


/**
 * This class holds the links to exernal objects referenced
 *  from the document.
 * @author Nick Burch
 */
public class ExObjList extends RecordContainer
{
    private byte[] _header;
    private static long _type = 1033;

    // Links to our more interesting children
    private ExObjListAtom exObjListAtom;

    /** 
     * Returns the ExObjListAtom of this list
     */
    public ExObjListAtom getExObjListAtom()
    {
        return exObjListAtom;
    }

    /**
     * Returns all the ExHyperlinks
     */
    public ExHyperlink[] getExHyperlinks()
    {
        ArrayList<ExHyperlink> links = new ArrayList<ExHyperlink>();
        for (int i = 0; i < _children.length; i++)
        {
            if (_children[i] instanceof ExHyperlink)
            {
                links.add((ExHyperlink)_children[i]);
            }
        }

        return links.toArray(new ExHyperlink[links.size()]);
    }

    /** 
     * Set things up, and find our more interesting children
     */
    protected ExObjList(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);


        _children = Record.findChildRecords(source, start + 8, len - 8);
        findInterestingChildren();
    }

    /**
     * Go through our child records, picking out the ones that are
     *  interesting, and saving those for use by the easy helper
     *  methods.
     */
    private void findInterestingChildren()
    {
        // First child should be the atom
        if (_children[0] instanceof ExObjListAtom)
        {
            exObjListAtom = (ExObjListAtom)_children[0];
        }
        else
        {
            throw new IllegalStateException(
                "First child record wasn't a ExObjListAtom, was of type "
                    + _children[0].getRecordType());
        }
    }

    /**
     * Create a new ExObjList, with blank fields
     */
    public ExObjList()
    {
        _header = new byte[8];
        _children = new Record[1];

        // Setup our header block
        _header[0] = 0x0f; // We are a container record
        LittleEndian.putShort(_header, 2, (short)_type);

        // Setup our child records
        _children[0] = new ExObjListAtom();
        findInterestingChildren();
    }

    /**
     * We are of type 1033
     */
    public long getRecordType()
    {
        return _type;
    }


    /**
     * Lookup a hyperlink by its unique id
     *
     * @param id hyperlink id
     * @return found <code>ExHyperlink</code> or <code>null</code>
     */
    public ExHyperlink get(int id)
    {
        for (int i = 0; i < _children.length; i++)
        {
            if (_children[i] instanceof ExHyperlink)
            {
                ExHyperlink rec = (ExHyperlink)_children[i];
                if (rec.getExHyperlinkAtom().getNumber() == id)
                {
                    return rec;
                }
            }
        }
        return null;
    }
    
    
    public void dispose()
    {
        super.dispose();
        _header = null;
        if (exObjListAtom != null)
        {
            exObjListAtom.dispose();
            exObjListAtom = null;
        }
    }
}
