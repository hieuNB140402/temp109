
package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.Hashtable;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public class SlideTimeAtom extends PositionDependentRecordAtom
{
    private byte[] _header;
    private static long _type = 12011;

    private long fileTime;
    

    protected SlideTimeAtom(byte[] source, int start, int len)
    {

        if(len < 16) { len = 16; }

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);
        
        fileTime = LittleEndian.getLong(source, start + 8);
    }

    public long getSlideCreateTime()
    {
        return fileTime;
    }
    

    public long getRecordType()
    { 
        return _type; 
    }

    public void updateOtherRecordReferences(Hashtable<Integer,Integer> oldToNewReferencesLookup)
    {
       
    }
    

    public void dispose()
    {
        _header = null;
    }
}
