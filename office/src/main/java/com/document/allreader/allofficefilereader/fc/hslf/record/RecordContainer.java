

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.util.ArrayList;

import com.document.allreader.allofficefilereader.fc.util.ArrayUtil;


public abstract class RecordContainer extends Record
{
	protected Record[] _children;
	private Boolean changingChildRecordsLock = Boolean.TRUE;


    public Record[] getChildRecords() { return _children; }


	public boolean isAnAtom() { return false; }



	private int findChildLocation(Record child) {

		synchronized(changingChildRecordsLock) {
			for(int i=0; i<_children.length; i++) {
				if(_children[i].equals(child)) {
					return i;
				}
			}
		}
		return -1;
	}


	private void appendChild(Record newChild) {
		synchronized(changingChildRecordsLock) {

			Record[] nc = new Record[(_children.length + 1)];
			System.arraycopy(_children, 0, nc, 0, _children.length);

			nc[_children.length] = newChild;
			_children = nc;
		}
	}

	private void addChildAt(Record newChild, int position) {
		synchronized(changingChildRecordsLock) {
			// Firstly, have the child added in at the end
			appendChild(newChild);


			moveChildRecords( (_children.length-1), position, 1 );
		}
	}

	private void moveChildRecords(int oldLoc, int newLoc, int number) {
		if(oldLoc == newLoc) { return; }
		if(number == 0) { return; }


		if(oldLoc+number > _children.length) {
			throw new IllegalArgumentException("Asked to move more records than there are!");
		}

		ArrayUtil.arrayMoveWithin(_children, oldLoc, newLoc, number);
	}



	public Record findFirstOfType(long type) {
		for(int i=0; i<_children.length; i++) {
			if(_children[i].getRecordType() == type) {
				return _children[i];
			}
		}
		return null;
	}


    public Record removeChild(Record ch) {
        Record rm = null;
        ArrayList<Record> lst = new ArrayList<Record>();
        for(Record r : _children) {
            if(r != ch) lst.add(r);
            else rm = r;
        }
        _children = lst.toArray(new Record[lst.size()]);
        return rm;
    }


	public void appendChildRecord(Record newChild) {
		synchronized(changingChildRecordsLock) {
			appendChild(newChild);
		}
	}


	public void addChildAfter(Record newChild, Record after) {
		synchronized(changingChildRecordsLock) {

			int loc = findChildLocation(after);
			if(loc == -1) {
				throw new IllegalArgumentException("Asked to add a new child after another record, but that record wasn't one of our children!");
			}

			// Add one place after the supplied record
			addChildAt(newChild, loc+1);
		}
	}


    public void setChildRecord(Record[] records) {
        this._children = records;
    }


    public static void handleParentAwareRecords(RecordContainer br) {

        for (Record record : br.getChildRecords()) {
            if (record instanceof ParentAwareRecord) {
                ((ParentAwareRecord) record).setParentRecord(br);
            }
            if (record instanceof RecordContainer) {
                handleParentAwareRecords((RecordContainer)record);
            }
        }
    }
    
    
    public void dispose()
    {
        if (_children != null)
        {
            for (Record rec : _children)
            {
                if (rec != null)
                {
                    rec.dispose();
                }
            }
            _children = null;
        }
    }


}
