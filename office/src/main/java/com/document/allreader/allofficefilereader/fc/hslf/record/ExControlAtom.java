

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class ExControlAtom extends RecordAtom {



    private byte[] _header;


    private int _id;


    protected ExControlAtom() {
        _header = new byte[8];

        LittleEndian.putShort(_header, 2, (short) getRecordType());
        LittleEndian.putInt(_header, 4, 4);

    }



    public int getSlideId() {
        return _id;
    }


    public void setSlideId(int id) {
        _id = id;
    }


    public long getRecordType() {
        return RecordTypes.ExControlAtom.typeID;
    }


    public void writeOut(OutputStream out) throws IOException {
        out.write(_header);
        byte[] data = new byte[4];
        LittleEndian.putInt(data, _id);
        out.write(data);
    }
    

   public void dispose()
   {
       _header = null;
   }

}
