

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.Hashtable;


public class TimeColorBehaviorAtom extends PositionDependentRecordAtom
{
    private byte[] _header;
    private static long _type = 0xF135;
    
    //A TimeColorBehaviorPropertyUsedFlag structure that specifies which fields are valid.
    private int flag;
    /**
     * We are of type 0x2AFB
     */
    public long getRecordType()
    {
        return _type;
    }
    
    /**
     * Set things up, and find our more interesting children
     */
    protected TimeColorBehaviorAtom(byte[] source, int start, int len)
    {
        if(len < 60)
        {
            len = 60;
        }

        _header = new byte[8];
        System.arraycopy(source, start, _header, 0, 8);
        
    }
    
    /**
     * At write-out time, update the references to PersistPtrs and
     *  other UserEditAtoms to point to their new positions
     */
    public void updateOtherRecordReferences(Hashtable<Integer,Integer> oldToNewReferencesLookup)
    {
       
    }
    
    
    public void dispose()
    {
        _header = null;
    }
}
