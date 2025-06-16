

package com.document.allreader.allofficefilereader.fc.hslf.record;


public final class Environment extends PositionDependentRecordContainer
{


    public FontCollection getFontCollection()
    {
        return fontCollection;
    }

    protected Environment(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);

        _children = Record.findChildRecords(source, start + 8, len - 8);

        for (int i = 0; i < _children.length; i++)
        {
            if (_children[i] instanceof FontCollection)
            {
                fontCollection = (FontCollection)_children[i];
            }
            else if (_children[i] instanceof TxMasterStyleAtom)
            {
                txmaster = (TxMasterStyleAtom)_children[i];
            }
        }

        if (fontCollection == null)
        {
            throw new IllegalStateException("Environment didn't contain a FontCollection record!");
        }
    }

    public TxMasterStyleAtom getTxMasterStyleAtom()
    {
        return txmaster;
    }


    public long getRecordType()
    {
        return _type;
    }

    

    public void dispose()
    {
        _header = null;
        if (fontCollection != null)
        {
            fontCollection.dispose();
            fontCollection = null;
        }
        if (txmaster != null)
        {
            txmaster.dispose();
            txmaster = null;
        }
    }
    
    private byte[] _header;
    private static long _type = 1010;

    private FontCollection fontCollection;
    private TxMasterStyleAtom txmaster;
}
