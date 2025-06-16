

package com.document.allreader.allofficefilereader.fc.hslf.record;


public final class RoundTripHFPlaceholder12 extends RecordAtom
{

    private byte[] _header;


    private byte _placeholderId;


    protected RoundTripHFPlaceholder12(byte[] source, int start, int len)
    {
        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);


        _placeholderId = source[start + 8];
    }


    public int getPlaceholderId()
    {
        return _placeholderId;
    }


    public void setPlaceholderId(int number)
    {
        _placeholderId = (byte)number;
    }

    public long getRecordType()
    {
        return RecordTypes.RoundTripHFPlaceholder12.typeID;
    }
    

    public void dispose()
    {
        _header = null;
    }
}
