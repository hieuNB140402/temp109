
package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.Hashtable;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public class TimeNodeAtom extends PositionDependentRecordAtom
{

    public static final int TNT_Parallel = 0;

    public static final int TNT_Sequential = 1;

    public static final int TNT_Behavior = 2;
    public static final int TNT__Media = 3;    
    
    
    private byte[] _header;
    private static long _type = 0xF127;
    
    private int reserved1;

    private int restart;
    

    private int timeNodeType;

    private int fill;
    
    private int reserved2;
    private byte reserved3;
    private int unused;
    

    private int duration;

    private boolean fFillProperty;
    private boolean fRestartProperty;
    private boolean reserved4;
    private boolean fGroupingTypeProperty;
    private boolean fDurationProperty;
    
    private byte[] reserved5;

    public long getRecordType()
    {
        return _type;
    }
    

    protected TimeNodeAtom(byte[] source, int start, int len)
    {
        if(len < 40)
        {
            len = 40;
        }

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);
        
        reserved1 = LittleEndian.getInt(source, start + 8);
        restart = LittleEndian.getInt(source, start + 12);
        
        timeNodeType = LittleEndian.getInt(source, start + 16);
        fill = LittleEndian.getInt(source, start + 20);
        
        duration = LittleEndian.getInt(source, start + 32);        
       
        byte b = source[start + 36];
        fDurationProperty = ((b & 0x10)) >> 4 > 0;
        fGroupingTypeProperty = ((b & 0x8) >> 3) > 0;

        fRestartProperty = ((b & 0x2) >> 1) > 0;
        fFillProperty = ((b & 0x1)) > 0;
    }
    

    public void updateOtherRecordReferences(Hashtable<Integer,Integer> oldToNewReferencesLookup)
    {
       
    }
    

    public void dispose()
    {
        _header = null;
    }
}
