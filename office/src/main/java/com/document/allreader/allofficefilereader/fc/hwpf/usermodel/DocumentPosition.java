

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import com.document.allreader.allofficefilereader.fc.hwpf.HWPFDocument;

public final class DocumentPosition extends Range
{
    public DocumentPosition(HWPFDocument doc, int pos)
    {
        super(pos, pos, doc);
    }

}
