
package com.document.allreader.allofficefilereader.fc.hwpf.model;

import com.document.allreader.allofficefilereader.fc.util.Internal;

@Internal
public enum FieldsDocumentPart {


    ANNOTATIONS( FIBFieldHandler.PLCFFLDATN ),


    ENDNOTES( FIBFieldHandler.PLCFFLDEDN ),


    FOOTNOTES( FIBFieldHandler.PLCFFLDFTN ),


    HEADER( FIBFieldHandler.PLCFFLDHDR ),


    HEADER_TEXTBOX( FIBFieldHandler.PLCFFLDHDRTXBX ),


    MAIN( FIBFieldHandler.PLCFFLDMOM ),


    TEXTBOX( FIBFieldHandler.PLCFFLDTXBX );

    private final int fibFieldsField;

    private FieldsDocumentPart( final int fibHandlerField )
    {
        this.fibFieldsField = fibHandlerField;
    }

    public int getFibFieldsField()
    {
        return fibFieldsField;
    }

}
