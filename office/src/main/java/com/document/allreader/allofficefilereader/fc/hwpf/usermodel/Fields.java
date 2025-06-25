
package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import java.util.Collection;

import com.document.allreader.allofficefilereader.fc.hwpf.model.FieldsDocumentPart;


/**
 * User-friendly interface to access document {@link Field}s
 * 
 * @author Sergey Vladimirov (vlsergey {at} gmail {dot} com)
 */
public interface Fields
{
    Field getFieldByStartOffset( FieldsDocumentPart documentPart, int offset );

    Collection<Field> getFields( FieldsDocumentPart part );
}
