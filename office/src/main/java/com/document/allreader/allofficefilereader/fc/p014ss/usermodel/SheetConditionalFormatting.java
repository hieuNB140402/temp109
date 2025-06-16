package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

import com.document.allreader.allofficefilereader.fc.p014ss.util.HSSFCellRangeAddress;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.SheetConditionalFormatting */

public interface SheetConditionalFormatting {
    int addConditionalFormatting(ConditionalFormatting conditionalFormatting);

    int addConditionalFormatting(HSSFCellRangeAddress[] hSSFCellRangeAddressArr, ConditionalFormattingRule conditionalFormattingRule);

    int addConditionalFormatting(HSSFCellRangeAddress[] hSSFCellRangeAddressArr, ConditionalFormattingRule conditionalFormattingRule, ConditionalFormattingRule conditionalFormattingRule2);

    int addConditionalFormatting(HSSFCellRangeAddress[] hSSFCellRangeAddressArr, ConditionalFormattingRule[] conditionalFormattingRuleArr);

    ConditionalFormattingRule createConditionalFormattingRule(byte b, String str);

    ConditionalFormattingRule createConditionalFormattingRule(byte b, String str, String str2);

    ConditionalFormattingRule createConditionalFormattingRule(String str);

    ConditionalFormatting getConditionalFormattingAt(int i);

    int getNumConditionalFormattings();

    void removeConditionalFormatting(int i);
}
