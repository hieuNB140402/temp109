

package com.document.allreader.allofficefilereader.fc.hssf.model;

import java.util.List;

import com.document.allreader.allofficefilereader.fc.hssf.record.Record;

/**
 * Simplifies iteration over a sequence of <tt>Record</tt> objects.
 *
 * @author Josh Micich
 */
public final class RecordStream {

	private final List<Record> _list;
	private int _nextIndex;
	private int _countRead;
	private final int _endIx;

	/**
	 * Creates a RecordStream bounded by startIndex and endIndex
	 */
	public RecordStream(List<Record> inputList, int startIndex, int endIx) {
		_list = inputList;
		_nextIndex = startIndex;
		_endIx = endIx;
		_countRead = 0;
	}

	public RecordStream(List<Record> records, int startIx) {
		this(records, startIx, records.size());
	}

	public boolean hasNext() {
		return _nextIndex < _endIx;
	}

	public Record getNext() {
		if(!hasNext()) {
			throw new RuntimeException("Attempt to read past end of record stream");
		}
		_countRead ++;
		return (Record) _list.get(_nextIndex++);
	}

	/**
	 * @return the {@link Class} of the next Record. <code>null</code> if this stream is exhausted.
	 */
	public Class<? extends Record> peekNextClass() {
		if(!hasNext()) {
			return null;
		}
		return _list.get(_nextIndex).getClass();
	}

	/**
	 * @return -1 if at end of records
	 */
	public int peekNextSid() {
		if(!hasNext()) {
			return -1;
		}
		return ((Record)_list.get(_nextIndex)).getSid();
	}

	public int getCountRead() {
		return _countRead;
	}
}
