

package com.document.allreader.allofficefilereader.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.document.allreader.allofficefilereader.fc.util.LittleEndian;



public final class TextSpecInfoAtom extends RecordAtom {

    private byte[] _header;


    private byte[] _data;


    protected TextSpecInfoAtom(byte[] source, int start, int len) {

        _header = new byte[8];
        System.arraycopy(source,start,_header,0,8);

        _data = new byte[len-8];
        System.arraycopy(source,start+8,_data,0,len-8);

    }

    public long getRecordType() { return RecordTypes.TextSpecInfoAtom.typeID; }


    public void writeOut(OutputStream out) throws IOException {
        out.write(_header);
        out.write(_data);
    }


    public void setTextSize(int size){
        LittleEndian.putInt(_data, 0, size);
    }


    public void reset(int size){
        _data = new byte[10];

        LittleEndian.putInt(_data, 0, size);

        LittleEndian.putInt(_data, 4, 1); //mask

        LittleEndian.putShort(_data, 8, (short)0); //langId

        LittleEndian.putInt(_header, 4, _data.length);
    }


    public int getCharactersCovered(){
        int covered = 0;
        TextSpecInfoRun[] runs = getTextSpecInfoRuns();
        for (int i = 0; i < runs.length; i++) covered += runs[i].len;
        return covered;
    }

    public TextSpecInfoRun[] getTextSpecInfoRuns(){
        ArrayList<TextSpecInfoRun> lst = new ArrayList<TextSpecInfoRun>();
        int pos = 0;
        int[] bits = {1, 0, 2};
        while(pos < _data.length) {
            TextSpecInfoRun run = new TextSpecInfoRun();
            run.len = LittleEndian.getInt(_data, pos); pos += 4;
            run.mask = LittleEndian.getInt(_data, pos); pos += 4;
            for (int i = 0; i < bits.length; i++) {
                if((run.mask & 1 << bits[i]) != 0){
                    switch (bits[i]){
                        case 0:
                            run.spellInfo = LittleEndian.getShort(_data, pos); pos += 2;
                            break;
                        case 1:
                            run.langId = LittleEndian.getShort(_data, pos); pos += 2;
                            break;
                        case 2:
                            run.altLangId = LittleEndian.getShort(_data, pos); pos += 2;
                            break;
                    }
                }
            }
            lst.add(run);
        }
        return lst.toArray(new TextSpecInfoRun[lst.size()]);
    }

    public static class TextSpecInfoRun {

        protected int len;

        protected int mask;

        protected short spellInfo = -1;
        protected short langId = -1;
        protected short altLangId = -1;


        public short getSpellInfo(){
            return spellInfo;
        }


        public short getLangId(){
            return spellInfo;
        }


        public short getAltLangId(){
            return altLangId;
        }


        public int length(){
            return len;
        }
    }
    

    public void dispose()
    {
        _header = null;
        _data = null;
    }
}
