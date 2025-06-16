
package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.Hashtable;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;

public class TimeSequenceDataAtom extends PositionDependentRecordAtom
{
    private byte[] _header;
    private static long _type = 0xF141;


    private int concurrency;
    

    private int nextAction;

    private int previousAction;
    
    private int reserved1;
    
    private boolean fConcurrencyPropertyUsed;
    private boolean fNextActionPropertyUsed;
    private boolean fPreviousActionPropertyUsed;
    
    private byte[] reserved2;
    

    public long getRecordType()
    {
        return _type;
    }
    

    protected TimeSequenceDataAtom(byte[] source, int start, int len)
    {
        if(len < 28)
        {
            len = 28;
        }

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);
        
        concurrency = LittleEndian.getInt(source, start + 8);
        nextAction = LittleEndian.getInt(source, start + 12);
        previousAction = LittleEndian.getInt(source, start + 16);
        reserved1 = LittleEndian.getInt(source, start + 20);
        
    }

    public void updateOtherRecordReferences(Hashtable<Integer,Integer> oldToNewReferencesLookup)
    {
       
    }
    

    public void dispose()
    {
        _header = null;
    }
}
