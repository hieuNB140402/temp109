

package com.document.allreader.allofficefilereader.fc.ddf;

public interface EscherSerializationListener
{

    void beforeRecordSerialize(int offset, short recordId, EscherRecord record);


    void afterRecordSerialize(int offset, short recordId, int size, EscherRecord record);
}
