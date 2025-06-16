
package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;

public final class ExtendedParagraphHeaderAtom extends RecordAtom
{ 

    private static long _type = 4015;
    

    public ExtendedParagraphHeaderAtom(byte[] source, int start, int len)
    {

        if(len < 8) 
        {
            len = 8;
        }

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);
        
        if(len >= 16)
        {
            refSlideID = LittleEndian.getInt(source, start + 8);
            textType = LittleEndian.getInt(source, start + 12);
        }
    }
    

    public int getRefSlideID()
    {
        return refSlideID;
    }
    

    public int getTextType()
    {
        return textType;
    }
    

    public long getRecordType()
    {
        return _type;
    }
    
    
    public void dispose()
    {
        _header = null;
    }
   

    private byte[] _header;

    private int refSlideID;

    private int textType;
}
