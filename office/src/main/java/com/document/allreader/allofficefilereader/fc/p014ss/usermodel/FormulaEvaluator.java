package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.FormulaEvaluator */

public interface FormulaEvaluator {
    void clearAllCachedResultValues();

    CellValue evaluate(ICell iCell);

    void evaluateAll();

    int evaluateFormulaCell(ICell iCell);

    ICell evaluateInCell(ICell iCell);

    void notifyDeleteCell(ICell iCell);

    void notifySetFormula(ICell iCell);

    void notifyUpdateCell(ICell iCell);
}
