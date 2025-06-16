package com.document.allreader.allofficefilereader.fc.p014ss.format;

import java.util.HashMap;
import java.util.Map;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.format.CellFormatCondition */

public abstract class CellFormatCondition {

    /* renamed from: EQ */
    private static final int f162EQ = 4;

    /* renamed from: GE */
    private static final int f163GE = 3;

    /* renamed from: GT */
    private static final int f164GT = 2;

    /* renamed from: LE */
    private static final int f165LE = 1;

    /* renamed from: LT */
    private static final int f166LT = 0;

    /* renamed from: NE */
    private static final int f167NE = 5;
    private static final Map<String, Integer> TESTS;

    public abstract boolean pass(double d);

    static {
        HashMap hashMap = new HashMap();
        TESTS = hashMap;
        hashMap.put("<", 0);
        hashMap.put("<=", 1);
        hashMap.put(">", 2);
        hashMap.put(">=", 3);
        hashMap.put("=", 4);
        hashMap.put("==", 4);
        hashMap.put("!=", 5);
        hashMap.put("<>", 5);
    }

    public static CellFormatCondition getInstance(String str, String str2) {
        Map<String, Integer> map = TESTS;
        if (map.containsKey(str)) {
            int intValue = map.get(str).intValue();
            final double parseDouble = Double.parseDouble(str2);
            if (intValue == 0) {
                return new CellFormatCondition() { // from class: com.allreader.office.allofficefilereader.fc.ss.format.CellFormatCondition.1
                    @Override // fc4ss.format.CellFormatCondition
                    public boolean pass(double d) {
                        return d < parseDouble;
                    }
                };
            }
            if (intValue == 1) {
                return new CellFormatCondition() { // from class: com.allreader.office.allofficefilereader.fc.ss.format.CellFormatCondition.2
                    @Override // fc4ss.format.CellFormatCondition
                    public boolean pass(double d) {
                        return d <= parseDouble;
                    }
                };
            }
            if (intValue == 2) {
                return new CellFormatCondition() { // from class: com.allreader.office.allofficefilereader.fc.ss.format.CellFormatCondition.3
                    @Override // fc4ss.format.CellFormatCondition
                    public boolean pass(double d) {
                        return d > parseDouble;
                    }
                };
            }
            if (intValue == 3) {
                return new CellFormatCondition() { // from class: com.allreader.office.allofficefilereader.fc.ss.format.CellFormatCondition.4
                    @Override // fc4ss.format.CellFormatCondition
                    public boolean pass(double d) {
                        return d >= parseDouble;
                    }
                };
            }
            if (intValue == 4) {
                return new CellFormatCondition() { // from class: com.allreader.office.allofficefilereader.fc.ss.format.CellFormatCondition.5
                    @Override // fc4ss.format.CellFormatCondition
                    public boolean pass(double d) {
                        return d == parseDouble;
                    }
                };
            }
            if (intValue == 5) {
                return new CellFormatCondition() { // from class: com.allreader.office.allofficefilereader.fc.ss.format.CellFormatCondition.6
                    @Override // fc4ss.format.CellFormatCondition
                    public boolean pass(double d) {
                        return d != parseDouble;
                    }
                };
            }
            throw new IllegalArgumentException("Cannot create for test number " + intValue + "(\"" + str + "\")");
        }
        throw new IllegalArgumentException("Unknown test: " + str);
    }
}
