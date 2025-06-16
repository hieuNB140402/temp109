

package com.document.allreader.allofficefilereader.fc.ddf;


public interface EscherRecordFactory {

    EscherRecord createRecord( byte[] data, int offset );
}
