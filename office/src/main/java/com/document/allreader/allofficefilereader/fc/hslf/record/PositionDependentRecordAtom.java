

package com.document.allreader.allofficefilereader.fc.hslf.record;
import java.util.Hashtable;


public abstract class PositionDependentRecordAtom extends RecordAtom implements PositionDependentRecord
{

	protected int myLastOnDiskOffset;

	public int getLastOnDiskOffset() { return myLastOnDiskOffset; }

	public void setLastOnDiskOffset(int offset) {
		myLastOnDiskOffset = offset;
	}


	public abstract void updateOtherRecordReferences(Hashtable<Integer,Integer> oldToNewReferencesLookup);
}
