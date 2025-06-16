

package com.document.allreader.allofficefilereader.fc.hssf.eventmodel;

import java.io.InputStream;
import java.util.Arrays;

import com.document.allreader.allofficefilereader.fc.hssf.record.Record;
import com.document.allreader.allofficefilereader.fc.hssf.record.RecordFactory;
import com.document.allreader.allofficefilereader.fc.hssf.record.RecordFormatException;
import com.document.allreader.allofficefilereader.fc.hssf.record.RecordInputStream;



public final class EventRecordFactory {

	private final ERFListener _listener;
	private final short[] _sids;


	public EventRecordFactory(ERFListener listener, short[] sids) {
		_listener = listener;
		if (sids == null) {
			_sids = null;
		} else {
			_sids = sids.clone();
			Arrays.sort(_sids); // for faster binary search
		}
	}
	private boolean isSidIncluded(short sid) {
		if (_sids == null) {
			return true;
		}
		return Arrays.binarySearch(_sids, sid) >= 0;
	}


	private boolean processRecord(Record record) {
		if (!isSidIncluded(record.getSid())) {
			return true;
		}
		return _listener.processRecord(record);
	}


	public void processRecords(InputStream in) throws RecordFormatException {
		Record last_record = null;

		RecordInputStream recStream = new RecordInputStream(in);

		while (recStream.hasNextRecord()) {
			recStream.nextRecord();
			Record[] recs = RecordFactory.createRecord(recStream);
			if (recs.length > 1) {
				for (int k = 0; k < recs.length; k++) {
					if ( last_record != null ) {
						if (!processRecord(last_record)) {
							return;
						}
					}
					last_record = recs[ k ];
				}
			} else {
				Record record = recs[ 0 ];

				if (record != null) {
					if (last_record != null) {
						if (!processRecord(last_record)) {
							return;
						}
					}
					 last_record = record;
				}
			}
		}

		if (last_record != null) {
			processRecord(last_record);
		}
	}
}
