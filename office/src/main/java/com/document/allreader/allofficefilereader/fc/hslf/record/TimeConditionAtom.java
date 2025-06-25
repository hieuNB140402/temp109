

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.Hashtable;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public class TimeConditionAtom extends PositionDependentRecordAtom
{

    public static final int TOT_None = 0;               //None.
    public static final int TOT_VisualElement = 1;      //An animatable object.
    public static final int TOT_TimeNode = 2;           //A time node.
    public static final int TOT_RuntimeNodeRef = 3;     //Runtime child time nodes.
    
    private byte[] _header;
    private static long _type = 0xF128;
    

    private int triggerObject;


    private int triggerEvent;
    


    private int id;


    private int delay;
    


    public long getRecordType()
    {
        return _type;
    }
    


    protected TimeConditionAtom(byte[] source, int start, int len)
    {
        if(len < 40)
        {
            len = 40;
        }

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);
        
        triggerObject = LittleEndian.getInt(source, start + 8);
        triggerEvent = LittleEndian.getInt(source, start + 12);
        id = LittleEndian.getInt(source, start + 16);
        delay =LittleEndian.getInt(source, start + 20);
    }
    


    public void updateOtherRecordReferences(Hashtable<Integer,Integer> oldToNewReferencesLookup)
    {
       
    }
    

    public void dispose()
    {
        _header = null;
    }
}
