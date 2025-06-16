package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.ConditionalFormattingRule */

public interface ConditionalFormattingRule {
    public static final byte CONDITION_TYPE_CELL_VALUE_IS = 1;
    public static final byte CONDITION_TYPE_FORMULA = 2;

    BorderFormatting createBorderFormatting();

    FontFormatting createFontFormatting();

    PatternFormatting createPatternFormatting();

    BorderFormatting getBorderFormatting();

    byte getComparisonOperation();

    byte getConditionType();

    FontFormatting getFontFormatting();

    String getFormula1();

    String getFormula2();

    PatternFormatting getPatternFormatting();
}
