
package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.Hashtable;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;
import com.document.allreader.allofficefilereader.fc.util.StringUtil;

public class TimeVariant extends PositionDependentRecordAtom
{
    public static final byte TPID_Display = 2;
    public static final byte TPID_MasterPos = 5;
    public static final byte TPID_SlaveType = 6;
    public static final byte TPID__EffectID = 9;
    public static final byte TPID_EffectDir = 10;
    public static final byte TPID_EffectType = 0x0B;
    public static final byte TPID_AfterEffect = 0x0D;
    public static final byte TPID_SlideCount = 0x0F;
    public static final byte TPID__TimeFilter = 0x10;
    public static final byte TPID__EventFilter = 0x11;
    public static final byte TPID_HideWhenStopped = 0x12;
    public static final byte TPID__GroupID = 0x13;
    public static final byte TPID__EffectNodeType = 0x14;
    public static final byte TPID_PlaceholderNode = 0x15;
    public static final byte TPID__MediaVolume = 0x16;
    public static final byte TPID_MediaMute = 0x17;
    public static final byte TPID__ZoomToFullScreen = 0x1A;

    public static final byte TimeEffectType__Entrance = 1;
    public static final byte TimeEffectType__Exit = 2;
    public static final byte TimeEffectType__Emphasis = 3;
    public static final byte TimeEffectType__MotionPath = 4;
    public static final byte TimeEffectType__ActionVerb = 5;
    public static final byte TimeEffectType__MediaCommand = 6;
    
    
    private static final byte TVT_Bool = 0;
    private static final byte TVT_Int = 1;
    private static final byte TVT_TVT_Float = 2;
    private static final byte TVT_String = 3;
    
    private byte[] _header;
    private static long _type = 0xF142;    

    private int tpID;
    
    private Object value;
    

    public long getRecordType()
    {
        return _type;
    }
    

    public TimeVariant(byte[] source, int start, int len)
    {

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);        
        tpID = (LittleEndian.getShort(_header, 0) & 0xFFF0) >> 4;
        
        int t = source[start + 8];
        switch(t)
        {
            case TVT_Bool:
                value = (source[start + 9] == 1);
                break;
            case TVT_Int:
                value = LittleEndian.getInt(source, start + 9);
                break;
            case TVT_TVT_Float:
                value = LittleEndian.getFloat(source, start + 9);
                break;
            case TVT_String:
                int strLen = LittleEndian.getInt(_header, 4) - 1;
                byte[] textBytes = new byte[strLen];
                System.arraycopy(source, start + 9, textBytes, 0, strLen);
                value = StringUtil.getFromUnicodeLE(textBytes);
                break;
        }
    }
    

    public int getAttributeType()
    {
        return tpID;
    }
    
    public Object getValue()
    {
        return value;
    }

    public void updateOtherRecordReferences(Hashtable<Integer,Integer> oldToNewReferencesLookup)
    {
       
    }
    
    
    public void dispose()
    {
        _header = null;
    }
}
