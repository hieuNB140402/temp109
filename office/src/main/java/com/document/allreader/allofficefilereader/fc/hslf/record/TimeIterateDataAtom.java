

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.Hashtable;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public class TimeIterateDataAtom extends PositionDependentRecordAtom
{
    private byte[] _header;
    private static long _type = 0xF140;
    

    private int iterateInterval;


    private int iterateType;
    


    private int iterateDirection;


    private int iterateIntervalType;
    
    private boolean fIterateDirectionPropertyUsed;
    private boolean fIterateTypePropertyUsed;
    private boolean fIterateIntervalPropertyUsed;
    private boolean fIterateIntervalTypePropertyUsed;
    
    private byte[] reserved;


    public long getRecordType()
    {
        return _type;
    }


    protected TimeIterateDataAtom(byte[] source, int start, int len)
    {
        if(len < 28)
        {
            len = 28;
        }

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);
        
        iterateInterval = LittleEndian.getInt(source, start + 8);
        iterateType = LittleEndian.getInt(source, start + 12);
        iterateDirection = LittleEndian.getInt(source, start + 16);
        iterateIntervalType = LittleEndian.getInt(source, start + 20);
        
    }


    public void updateOtherRecordReferences(Hashtable<Integer,Integer> oldToNewReferencesLookup)
    {
       
    }
    

    public void dispose()
    {
        _header = null;
    }
}
