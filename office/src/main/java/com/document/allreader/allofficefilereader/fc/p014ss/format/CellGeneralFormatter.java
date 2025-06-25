package com.document.allreader.allofficefilereader.fc.p014ss.format;

import java.util.Formatter;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.format.CellGeneralFormatter */

public class CellGeneralFormatter extends CellFormatter {
    public CellGeneralFormatter() {
        super("General");
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    @Override // fc4ss.format.CellFormatter
    public void formatValue(StringBuffer stringBuffer, Object obj) {
        boolean z;
        String str;
        int i;
        if (obj instanceof Number) {
            double doubleValue = ((Number) obj).doubleValue();
            if (doubleValue == 0.0d) {
                stringBuffer.append('0');
                return;
            }
            double log10 = Math.log10(Math.abs(doubleValue));
            if (log10 > 10.0d || log10 < -9.0d) {
                str = "%1.5E";
            } else if (((double) ((long) doubleValue)) != doubleValue) {
                str = "%1.9f";
            } else {
                str = "%1.0f";
                z = false;
                new Formatter(stringBuffer).format(LOCALE, str, obj);
                if (!z) {
                    if (str.endsWith("E")) {
                        i = stringBuffer.lastIndexOf("E");
                    } else {
                        i = stringBuffer.length();
                    }
                    int i2 = i - 1;
                    while (stringBuffer.charAt(i2) == '0') {
                        stringBuffer.deleteCharAt(i2);
                        i2--;
                    }
                    if (stringBuffer.charAt(i2) == '.') {
                        stringBuffer.deleteCharAt(i2);
                        return;
                    }
                    return;
                }
                return;
            }
            z = true;
            new Formatter(stringBuffer).format(LOCALE, str, obj);
            if (!z) {
            }
        } else {
            stringBuffer.append(obj.toString());
        }
    }

    @Override // fc4ss.format.CellFormatter
    public void simpleValue(StringBuffer stringBuffer, Object obj) {
        formatValue(stringBuffer, obj);
    }
}
