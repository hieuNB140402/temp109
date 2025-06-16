package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

import com.document.allreader.allofficefilereader.fc.p014ss.util.HSSFCellRangeAddress;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.ConditionalFormatting */

public interface ConditionalFormatting {
    void addRule(ConditionalFormattingRule conditionalFormattingRule);

    HSSFCellRangeAddress[] getFormattingRanges();

    int getNumberOfRules();

    ConditionalFormattingRule getRule(int i);

    void setRule(int i, ConditionalFormattingRule conditionalFormattingRule);
}
