

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.hslf.exceptions.CorruptPowerPointFileException;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;
import com.document.allreader.allofficefilereader.fc.util.POILogFactory;
import com.document.allreader.allofficefilereader.fc.util.POILogger;




public abstract class Record
{
    protected POILogger logger = POILogFactory.getLogger(this.getClass());


	public abstract boolean isAnAtom();


	public abstract long getRecordType();


	public abstract Record[] getChildRecords();


	public abstract void dispose();

	public static void writeLittleEndian(int i,OutputStream o) throws IOException {
		byte[] bi = new byte[4];
		LittleEndian.putInt(bi,i);
		o.write(bi);
	}

	public static void writeLittleEndian(short s,OutputStream o) throws IOException {
		byte[] bs = new byte[2];
		LittleEndian.putShort(bs,s);
		o.write(bs);
	}


	public static Record buildRecordAtOffset(byte[] b, int offset) {
		long type = LittleEndian.getUShort(b,offset+2);
		long rlen = LittleEndian.getUInt(b,offset+4);


		int rleni = (int)rlen;
		if(rleni < 0) { rleni = 0; }

		return createRecordForType(type,b,offset,8+rleni);
	}
	
	public static Record buildRecordAtOffset(byte[] b, int offset, int myLastOnDiskOffset) {
        long type = LittleEndian.getUShort(b,offset+2);
        long rlen = LittleEndian.getUInt(b,offset+4);


        int rleni = (int)rlen;
        if(rleni < 0) { rleni = 0; }

        return createRecordForType(type,b,offset,8+rleni, myLastOnDiskOffset);
    }

	public static Record[] findChildRecords(byte[] b, int start, int len) {
		List<Record> children = new ArrayList<Record>(5);

		int pos = start;
		while(pos <= (start+len-8)) {
			long type = LittleEndian.getUShort(b,pos+2);
			long rlen = LittleEndian.getUInt(b,pos+4);

			int rleni = (int)rlen;
			if(rleni < 0) { rleni = 0; }

			if(pos == 0 && type == 0l && rleni == 0xffff) {
				throw new CorruptPowerPointFileException("Corrupt document - starts with record of type 0000 and length 0xFFFF");
			}

			Record r = createRecordForType(type,b,pos,8+rleni);
			if(r != null) {
				children.add(r);
			} else {

			}
			pos += 8;
			pos += rleni;
		}

		// Turn the vector into an array, and return
		Record[] cRecords = children.toArray( new Record[children.size()] );
		return cRecords;
	}


	public static Record createRecordForType(long type, byte[] b, int start, int len) {
		Record toReturn = null;


		if(start + len > b.length) {
			System.err.println("Warning: Skipping record of type " + type + " at position " + start + " which claims to be longer than the file! (" + len + " vs " + (b.length-start) + ")");
			return null;
		}


		Class<? extends Record> c = null;
		try {
			c = RecordTypes.recordHandlingClass((int)type);
			if(c == null) {

				c = RecordTypes.recordHandlingClass( RecordTypes.Unknown.typeID );
			}

			java.lang.reflect.Constructor<? extends Record> con = c.getDeclaredConstructor(new Class[] { byte[].class, Integer.TYPE, Integer.TYPE });

			toReturn = con.newInstance(new Object[] { b, Integer.valueOf(start), Integer.valueOf(len) });
		} catch(InstantiationException ie) {
			throw new RuntimeException("Couldn't instantiate the class for type with id " + type + " on class " + c + " : " + ie, ie);
		} catch(java.lang.reflect.InvocationTargetException ite) {
			throw new RuntimeException("Couldn't instantiate the class for type with id " + type + " on class " + c + " : " + ite + "\nCause was : " + ite.getCause(), ite);
		} catch(IllegalAccessException iae) {
			throw new RuntimeException("Couldn't access the constructor for type with id " + type + " on class " + c + " : " + iae, iae);
		} catch(NoSuchMethodException nsme) {
			throw new RuntimeException("Couldn't access the constructor for type with id " + type + " on class " + c + " : " + nsme, nsme);
		}

		if(toReturn instanceof PositionDependentRecord) {
			PositionDependentRecord pdr = (PositionDependentRecord)toReturn;
			pdr.setLastOnDiskOffset(start);
		}
		return toReturn;
	}
	
	public static Record createRecordForType(long type, byte[] b, int start, int len, int myLastOnDiskOffset) {
        Record toReturn = null;


        if(start + len > b.length) {
            System.err.println("Warning: Skipping record of type " + type + " at position " + start + " which claims to be longer than the file! (" + len + " vs " + (b.length-start) + ")");
            return null;
        }


        Class<? extends Record> c = null;
        try {
            c = RecordTypes.recordHandlingClass((int)type);
            if(c == null) {

                c = RecordTypes.recordHandlingClass( RecordTypes.Unknown.typeID );
            }


            java.lang.reflect.Constructor<? extends Record> con = c.getDeclaredConstructor(new Class[] { byte[].class, Integer.TYPE, Integer.TYPE });

            toReturn = con.newInstance(new Object[] { b, Integer.valueOf(start), Integer.valueOf(len) });
        } catch(InstantiationException ie) {
            throw new RuntimeException("Couldn't instantiate the class for type with id " + type + " on class " + c + " : " + ie, ie);
        } catch(java.lang.reflect.InvocationTargetException ite) {
            throw new RuntimeException("Couldn't instantiate the class for type with id " + type + " on class " + c + " : " + ite + "\nCause was : " + ite.getCause(), ite);
        } catch(IllegalAccessException iae) {
            throw new RuntimeException("Couldn't access the constructor for type with id " + type + " on class " + c + " : " + iae, iae);
        } catch(NoSuchMethodException nsme) {
            throw new RuntimeException("Couldn't access the constructor for type with id " + type + " on class " + c + " : " + nsme, nsme);
        }

        if(toReturn instanceof PositionDependentRecord) {
            PositionDependentRecord pdr = (PositionDependentRecord)toReturn;
            pdr.setLastOnDiskOffset(myLastOnDiskOffset);
        }
        return toReturn;
    }
}
