

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.Hashtable;


public abstract class PositionDependentRecordContainer extends RecordContainer implements
    PositionDependentRecord
{
    private int sheetId; // Found from PersistPtrHolder


    public int getSheetId()
    {
        return sheetId;
    }


    public void setSheetId(int id)
    {
        sheetId = id;
    }

    protected int myLastOnDiskOffset;

    public int getLastOnDiskOffset()
    {
        return myLastOnDiskOffset;
    }


    public void setLastOnDiskOffset(int offset)
    {
        myLastOnDiskOffset = offset;
    }


    public void updateOtherRecordReferences(Hashtable<Integer, Integer> oldToNewReferencesLookup)
    {
        return;
    }
}
