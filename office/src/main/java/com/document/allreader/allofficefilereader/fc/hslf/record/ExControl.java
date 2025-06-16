

package com.document.allreader.allofficefilereader.fc.hslf.record;


public final class ExControl extends ExEmbed {


    protected ExControl(byte[] source, int start, int len) {
        super(source, start, len);
    }


    public ExControl() {
        super();

        _children[0] = embedAtom = new ExControlAtom();
    }


    public ExControlAtom getExControlAtom()
    {
        return (ExControlAtom)_children[0];
    }


    public long getRecordType() {
        return RecordTypes.ExControl.typeID;
    }
}
