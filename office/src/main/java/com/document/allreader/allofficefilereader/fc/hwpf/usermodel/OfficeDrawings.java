
package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import java.util.Collection;



public interface OfficeDrawings
{
    OfficeDrawing getOfficeDrawingAt( int characterPosition );

    Collection<OfficeDrawing> getOfficeDrawings();
}
