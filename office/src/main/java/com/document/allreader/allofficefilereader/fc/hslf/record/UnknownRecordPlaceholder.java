

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class UnknownRecordPlaceholder extends RecordAtom
{
    private byte[] _contents;
    private long _type;

    protected UnknownRecordPlaceholder(byte[] source, int start, int len)
    {

        if (len < 0)
        {
            len = 0;
        }

        _contents = new byte[len];
        System.arraycopy(source, start, _contents, 0, len);
        _type = LittleEndian.getUShort(_contents, 2);
    }


    public long getRecordType()
    {
        return _type;
    }
    
    
    public void dispose()
    {
        _contents = null;
    }
}
