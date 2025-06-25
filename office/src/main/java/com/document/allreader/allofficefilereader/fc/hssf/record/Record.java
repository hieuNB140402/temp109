

package com.document.allreader.allofficefilereader.fc.hssf.record;

import java.io.ByteArrayInputStream;

/**
 * Title: Record
 * Description: All HSSF Records inherit from this class.
 * @author Andrew C. Oliver
 * @author Marc Johnson (mjohnson at apache dot org)
 * @author Jason Height (jheight at chariot dot net dot au)
 */
public abstract class Record extends RecordBase {

	protected Record() {
		// no fields to initialise
	}

	/**
	 * called by the class that is responsible for writing this sucker.
	 * Subclasses should implement this so that their data is passed back in a
	 * byte array.
	 *
	 * @return byte array containing instance data
	 */
	public final byte[] serialize() {
		byte[] retval = new byte[ getRecordSize() ];

		serialize(0, retval);
		return retval;
	}

	/**
	 * get a string representation of the record (for biffview/debugging)
	 */
	public String toString() {
		return super.toString();
	}

	/**
	 * return the non static version of the id for this record.
	 */

	public abstract short getSid();

	public Object clone() {
		if (false) {
			// TODO - implement clone in a more standardised way
			try {
				return super.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
		}
		throw new RuntimeException("The class "+getClass().getName()+" needs to define a clone method");
	}

	/**
	 * Clone the current record, via a call to serialize
	 *  it, and another to create a new record from the
	 *  bytes.
	 * May only be used for classes which don't have
	 *  internal counts / ids in them. For those which
	 *  do, a full model-aware cloning is needed, which
	 *  allocates new ids / counts as needed.
	 */
	public Record cloneViaReserialise() {
		// Do it via a re-serialization
		// It's a cheat, but it works...
		byte[] b = serialize();
		RecordInputStream rinp = new RecordInputStream(new ByteArrayInputStream(b));
		rinp.nextRecord();

		Record[] r = RecordFactory.createRecord(rinp);
		if(r.length != 1) {
			throw new IllegalStateException("Re-serialised a record to clone it, but got " + r.length + " records back!");
		}
		return r[0];
	}
}
