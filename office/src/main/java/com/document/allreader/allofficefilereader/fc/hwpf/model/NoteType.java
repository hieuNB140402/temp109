



package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.util.Internal;

@Internal
public enum NoteType {

    ENDNOTE( FIBFieldHandler.PLCFENDREF, FIBFieldHandler.PLCFENDTXT ),


    FOOTNOTE( FIBFieldHandler.PLCFFNDREF, FIBFieldHandler.PLCFFNDTXT );

    private final int fibDescriptorsFieldIndex;
    private final int fibTextPositionsFieldIndex;

    private NoteType( int fibDescriptorsFieldIndex,
            int fibTextPositionsFieldIndex )
    {
        this.fibDescriptorsFieldIndex = fibDescriptorsFieldIndex;
        this.fibTextPositionsFieldIndex = fibTextPositionsFieldIndex;
    }

    public int getFibDescriptorsFieldIndex()
    {
        return fibDescriptorsFieldIndex;
    }

    public int getFibTextPositionsFieldIndex()
    {
        return fibTextPositionsFieldIndex;
    }
}
