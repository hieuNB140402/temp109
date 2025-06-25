

package com.document.allreader.allofficefilereader.fc.hslf.record;


public final class SoundCollection extends RecordContainer {

    private byte[] _header;


    protected SoundCollection(byte[] source, int start, int len) {

        _header = new byte[8];
        System.arraycopy(source,start,_header,0,8);


        _children = Record.findChildRecords(source,start+8,len-8);
    }


    public long getRecordType() {
        return RecordTypes.SoundCollection.typeID;
    }
    

    public void dispose()
    {
        super.dispose();
        _header = null;
    }

}
