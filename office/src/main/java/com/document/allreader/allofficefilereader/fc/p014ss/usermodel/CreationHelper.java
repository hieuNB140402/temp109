package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

import com.document.allreader.allofficefilereader.fc.hssf.usermodel.IClientAnchor;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.CreationHelper */

public interface CreationHelper {
    IClientAnchor createClientAnchor();

    DataFormat createDataFormat();

    FormulaEvaluator createFormulaEvaluator();

    IHyperlink createHyperlink(int i);

    RichTextString createRichTextString(String str);
}
