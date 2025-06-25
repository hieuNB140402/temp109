package com.document.allreader.allofficefilereader.fc.p014ss.usermodel;

import com.document.allreader.allofficefilereader.fc.openxml4j.opc.PackagingURIHelper;
import com.document.allreader.allofficefilereader.ss.util.DateUtil;
import java.lang.reflect.InvocationTargetException;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.DataFormatter */

public class DataFormatter {
    private static final Pattern amPmPattern = Pattern.compile("((A|P)[M/P]*)", 2);
    private static final Pattern colorPattern = Pattern.compile("(\\[BLACK\\])|(\\[BLUE\\])|(\\[CYAN\\])|(\\[GREEN\\])|(\\[MAGENTA\\])|(\\[RED\\])|(\\[WHITE\\])|(\\[YELLOW\\])|(\\[COLOR\\s*\\d\\])|(\\[COLOR\\s*[0-5]\\d\\])", 2);
    private static final Pattern daysAsText = Pattern.compile("([d]{3,})", 2);
    private static final String invalidDateTimeString;
    private static final Pattern numPattern = Pattern.compile("[0#]+");
    private static final Pattern specialPatternGroup = Pattern.compile("(\\[\\$[^-\\]]*-[0-9A-Z]+\\])");
    private final DateFormatSymbols dateSymbols;
    private final DecimalFormatSymbols decimalSymbols;
    private Format defaultNumFormat;
    private boolean emulateCsv;
    private final Map<String, Format> formats;
    private final Format generalDecimalNumFormat;
    private final Format generalWholeNumFormat;

    static {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 255; i++) {
            sb.append('#');
        }
        invalidDateTimeString = sb.toString();
    }

    public DataFormatter() {
        this(false);
    }

    public DataFormatter(boolean z) {
        this(Locale.getDefault());
        this.emulateCsv = z;
    }

    public DataFormatter(Locale locale, boolean z) {
        this(locale);
        this.emulateCsv = z;
    }

    public DataFormatter(Locale locale) {
        this.emulateCsv = false;
        this.dateSymbols = new DateFormatSymbols(locale);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(locale);
        this.decimalSymbols = decimalFormatSymbols;
        this.generalWholeNumFormat = new DecimalFormat("#", decimalFormatSymbols);
        this.generalDecimalNumFormat = new DecimalFormat("#.##########", decimalFormatSymbols);
        this.formats = new HashMap();
        Format format = ZipPlusFourFormat.instance;
        addFormat("00000\\-0000", format);
        addFormat("00000-0000", format);
        Format format2 = PhoneFormat.instance;
        addFormat("[<=9999999]###\\-####;\\(###\\)\\ ###\\-####", format2);
        addFormat("[<=9999999]###-####;(###) ###-####", format2);
        addFormat("###\\-####;\\(###\\)\\ ###\\-####", format2);
        addFormat("###-####;(###) ###-####", format2);
        Format format3 = SSNFormat.instance;
        addFormat("000\\-00\\-0000", format3);
        addFormat("000-00-0000", format3);
    }

    private Format getFormat(ICell iCell) {
        if (iCell.getCellStyle() == null) {
            return null;
        }
        short dataFormat = iCell.getCellStyle().getDataFormat();
        String dataFormatString = iCell.getCellStyle().getDataFormatString();
        if (dataFormatString == null || dataFormatString.trim().length() == 0) {
            return null;
        }
        return getFormat(iCell.getNumericCellValue(), dataFormat, dataFormatString);
    }

    private Format getFormat(double d, int i, String str) {
        int indexOf = str.indexOf(59);
        int lastIndexOf = str.lastIndexOf(59);
        if (!(indexOf == -1 || indexOf == lastIndexOf)) {
            int indexOf2 = str.indexOf(59, indexOf + 1);
            if (indexOf2 == lastIndexOf) {
                if (d == 0.0d) {
                    str = str.substring(lastIndexOf + 1);
                } else {
                    str = str.substring(0, lastIndexOf);
                }
            } else if (d == 0.0d) {
                str = str.substring(indexOf2 + 1, lastIndexOf);
            } else {
                str = str.substring(0, indexOf2);
            }
        }
        if (this.emulateCsv && d == 0.0d && str.contains("#") && !str.contains("0")) {
            str = str.replaceAll("#", "");
        }
        Format format = this.formats.get(str);
        if (format != null) {
            return format;
        }
        if (!"General".equalsIgnoreCase(str) && !"@".equals(str)) {
            Format createFormat = createFormat(d, i, str);
            this.formats.put(str, createFormat);
            return createFormat;
        } else if (isWholeNumber(d)) {
            return this.generalWholeNumFormat;
        } else {
            return this.generalDecimalNumFormat;
        }
    }

    public Format createFormat(ICell iCell) {
        return createFormat(iCell.getNumericCellValue(), iCell.getCellStyle().getDataFormat(), iCell.getCellStyle().getDataFormatString());
    }

    private Format createFormat(double d, int i, String str) {
        String group;
        int indexOf;
        Matcher matcher = colorPattern.matcher(str);
        while (matcher.find() && (indexOf = str.indexOf((group = matcher.group()))) != -1) {
            String str2 = str.substring(0, indexOf) + str.substring(indexOf + group.length());
            if (str2.equals(str)) {
                break;
            }
            matcher = colorPattern.matcher(str2);
            str = str2;
        }
        Matcher matcher2 = specialPatternGroup.matcher(str);
        while (matcher2.find()) {
            String group2 = matcher2.group();
            String substring = group2.substring(group2.indexOf(36) + 1, group2.indexOf(45));
            if (substring.indexOf(36) > -1) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(substring.substring(0, substring.indexOf(36)));
                stringBuffer.append('\\');
                stringBuffer.append(substring.substring(substring.indexOf(36), substring.length()));
                substring = stringBuffer.toString();
            }
            str = matcher2.replaceAll(substring);
            matcher2 = specialPatternGroup.matcher(str);
        }
        if (str == null || str.trim().length() == 0) {
            return getDefaultFormat(d);
        }
        if (DateUtil.isADateFormat(i, str) && DateUtil.isValidExcelDate(d)) {
            return createDateFormat(str, d);
        }
        if (numPattern.matcher(str).find()) {
            return createNumberFormat(str, d);
        }
        if (this.emulateCsv) {
            return new ConstantStringFormat(cleanFormatForNumber(str));
        }
        return null;
    }

    private Format createDateFormat(String str, double d) {
        String replaceAll = str.replaceAll("\\\\-", "-").replaceAll("\\\\,", ",").replaceAll("\\\\\\.", ".").replaceAll("\\\\ ", " ").replaceAll("\\\\/", PackagingURIHelper.FORWARD_SLASH_STRING).replaceAll(";@", "").replaceAll("\"/\"", PackagingURIHelper.FORWARD_SLASH_STRING);
        Matcher matcher = amPmPattern.matcher(replaceAll);
        boolean z = false;
        while (matcher.find()) {
            replaceAll = matcher.replaceAll("@");
            matcher = amPmPattern.matcher(replaceAll);
            z = true;
        }
        String replaceAll2 = replaceAll.replaceAll("@", "a");
        Matcher matcher2 = daysAsText.matcher(replaceAll2);
        if (matcher2.find()) {
            replaceAll2 = matcher2.replaceAll(matcher2.group(0).toUpperCase().replaceAll("D", "E"));
        }
        StringBuffer stringBuffer = new StringBuffer();
        char[] charArray = replaceAll2.toCharArray();
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        boolean z3 = true;
        for (char c : charArray) {
            if (c == '[' && !z2) {
                stringBuffer.append(c);
                z2 = true;
            } else if (c != ']' || !z2) {
                if (z2) {
                    if (c == 'h' || c == 'H') {
                        stringBuffer.append('H');
                    } else if (c == 'm' || c == 'M') {
                        stringBuffer.append('m');
                    } else if (c == 's' || c == 'S') {
                        stringBuffer.append('s');
                    } else {
                        stringBuffer.append(c);
                    }
                } else if (c == 'h' || c == 'H') {
                    if (z) {
                        stringBuffer.append('h');
                    } else {
                        stringBuffer.append('H');
                    }
                } else if (c != 'm' && c != 'M') {
                    if (c == 's' || c == 'S') {
                        stringBuffer.append('s');
                        for (int i = 0; i < arrayList.size(); i++) {
                            int intValue = ((Integer) arrayList.get(i)).intValue();
                            if (stringBuffer.charAt(intValue) == 'M') {
                                stringBuffer.replace(intValue, intValue + 1, "m");
                            }
                        }
                        arrayList.clear();
                    } else if (Character.isLetter(c)) {
                        arrayList.clear();
                        if (c == 'y' || c == 'Y') {
                            stringBuffer.append('y');
                        } else if (c == 'd' || c == 'D') {
                            stringBuffer.append('d');
                        } else {
                            stringBuffer.append(c);
                        }
                    } else {
                        stringBuffer.append(c);
                    }
                    z3 = true;
                } else if (z3) {
                    stringBuffer.append('M');
                    arrayList.add(Integer.valueOf(stringBuffer.length() - 1));
                } else {
                    stringBuffer.append('m');
                }
            } else {
                stringBuffer.append(c);
                z2 = false;
            }
            z3 = false;
        }
        try {
            return new ExcelStyleDateFormatter(stringBuffer.toString(), this.dateSymbols);
        } catch (IllegalArgumentException unused) {
            return getDefaultFormat(d);
        }
    }

    private String cleanFormatForNumber(String str) {
        StringBuffer stringBuffer = new StringBuffer(str);
        int i = 0;
        if (this.emulateCsv) {
            for (int i2 = 0; i2 < stringBuffer.length(); i2++) {
                char charAt = stringBuffer.charAt(i2);
                if ((charAt == '_' || charAt == '*' || charAt == '?') && (i2 <= 0 || stringBuffer.charAt(i2 - 1) != '\\')) {
                    if (charAt == '?') {
                        stringBuffer.setCharAt(i2, ' ');
                    } else if (i2 < stringBuffer.length() - 1) {
                        if (charAt == '_') {
                            stringBuffer.setCharAt(i2 + 1, ' ');
                        } else {
                            stringBuffer.deleteCharAt(i2 + 1);
                        }
                        stringBuffer.deleteCharAt(i2);
                    }
                }
            }
        } else {
            for (int i3 = 0; i3 < stringBuffer.length(); i3++) {
                char charAt2 = stringBuffer.charAt(i3);
                if ((charAt2 == '_' || charAt2 == '*') && (i3 <= 0 || stringBuffer.charAt(i3 - 1) != '\\')) {
                    if (i3 < stringBuffer.length() - 1) {
                        stringBuffer.deleteCharAt(i3 + 1);
                    }
                    stringBuffer.deleteCharAt(i3);
                }
            }
        }
        while (i < stringBuffer.length()) {
            char charAt3 = stringBuffer.charAt(i);
            if (charAt3 == '\\' || charAt3 == '\"') {
                stringBuffer.deleteCharAt(i);
            } else {
                if (charAt3 == '+' && i > 0 && stringBuffer.charAt(i - 1) == 'E') {
                    stringBuffer.deleteCharAt(i);
                }
                i++;
            }
            i--;
            i++;
        }
        return stringBuffer.toString();
    }

    private Format createNumberFormat(String str, double d) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat(cleanFormatForNumber(str), this.decimalSymbols);
            setExcelStyleRoundingMode(decimalFormat);
            return decimalFormat;
        } catch (IllegalArgumentException unused) {
            return getDefaultFormat(d);
        }
    }

    private static boolean isWholeNumber(double d) {
        return d == Math.floor(d);
    }

    public Format getDefaultFormat(ICell iCell) {
        return getDefaultFormat(iCell.getNumericCellValue());
    }

    private Format getDefaultFormat(double d) {
        Format format = this.defaultNumFormat;
        if (format != null) {
            return format;
        }
        if (isWholeNumber(d)) {
            return this.generalWholeNumFormat;
        }
        return this.generalDecimalNumFormat;
    }

    private String performDateFormatting(Date date, Format format) {
        if (format != null) {
            return format.format(date);
        }
        return date.toString();
    }

    private String getFormattedDateString(ICell iCell) {
        Format format = getFormat(iCell);
        if (format instanceof ExcelStyleDateFormatter) {
            ((ExcelStyleDateFormatter) format).setDateToBeFormatted(iCell.getNumericCellValue());
        }
        return performDateFormatting(iCell.getDateCellValue(), format);
    }

    private String getFormattedNumberString(ICell iCell) {
        Format format = getFormat(iCell);
        double numericCellValue = iCell.getNumericCellValue();
        if (format == null) {
            return String.valueOf(numericCellValue);
        }
        return format.format(new Double(numericCellValue));
    }

    public String formatRawCellContents(double d, int i, String str) {
        return formatRawCellContents(d, i, str, false);
    }

    public String formatRawCellContents(double d, int i, String str, boolean z) {
        if (DateUtil.isADateFormat(i, str)) {
            if (DateUtil.isValidExcelDate(d)) {
                Format format = getFormat(d, i, str);
                if (format instanceof ExcelStyleDateFormatter) {
                    ((ExcelStyleDateFormatter) format).setDateToBeFormatted(d);
                }
                return performDateFormatting(DateUtil.getJavaDate(d, z), format);
            } else if (this.emulateCsv) {
                return invalidDateTimeString;
            }
        }
        Format format2 = getFormat(d, i, str);
        if (format2 == null) {
            return String.valueOf(d);
        }
        String format3 = format2.format(new Double(d));
        return (!format3.contains("E") || format3.contains("E-")) ? format3 : format3.replaceFirst("E", "E+");
    }

    public String formatCellValue(ICell iCell) {
        return formatCellValue(iCell, null);
    }

    public String formatCellValue(ICell iCell, FormulaEvaluator formulaEvaluator) {
        if (iCell == null) {
            return "";
        }
        int cellType = iCell.getCellType();
        if (cellType == 2) {
            if (formulaEvaluator == null) {
                return iCell.getCellFormula();
            }
            cellType = formulaEvaluator.evaluateFormulaCell(iCell);
        }
        if (cellType == 0) {
            return getFormattedNumberString(iCell);
        }
        if (cellType == 1) {
            return iCell.getRichStringCellValue().getString();
        }
        if (cellType == 3) {
            return "";
        }
        if (cellType == 4) {
            return String.valueOf(iCell.getBooleanCellValue());
        }
        throw new RuntimeException("Unexpected celltype (" + cellType + ")");
    }

    public void setDefaultNumberFormat(Format format) {
        for (Map.Entry<String, Format> entry : this.formats.entrySet()) {
            if (entry.getValue() == this.generalDecimalNumFormat || entry.getValue() == this.generalWholeNumFormat) {
                entry.setValue(format);
            }
        }
        this.defaultNumFormat = format;
    }

    public void addFormat(String str, Format format) {
        this.formats.put(str, format);
    }

    static DecimalFormat createIntegerOnlyFormat(String str) {
        DecimalFormat decimalFormat = new DecimalFormat(str);
        decimalFormat.setParseIntegerOnly(true);
        return decimalFormat;
    }

    public static void setExcelStyleRoundingMode(DecimalFormat decimalFormat) {
        setExcelStyleRoundingMode(decimalFormat, RoundingMode.HALF_UP);
    }

    public static void setExcelStyleRoundingMode(DecimalFormat decimalFormat, RoundingMode roundingMode) {
        try {
            decimalFormat.getClass().getMethod("setRoundingMode", RoundingMode.class).invoke(decimalFormat, roundingMode);
        } catch (NoSuchMethodException | SecurityException unused) {
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to set rounding mode", e);
        } catch (InvocationTargetException e2) {
            throw new RuntimeException("Unable to set rounding mode", e2);
        }
    }

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.DataFormatter$SSNFormat */

    private static final class SSNFormat extends Format {

        /* renamed from: df */
        private static final DecimalFormat f176df = DataFormatter.createIntegerOnlyFormat("000000000");
        public static final Format instance = new SSNFormat();

        private SSNFormat() {
        }

        public static String format(Number number) {
            String format = f176df.format(number);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(format.substring(0, 3));
            stringBuffer.append('-');
            stringBuffer.append(format.substring(3, 5));
            stringBuffer.append('-');
            stringBuffer.append(format.substring(5, 9));
            return stringBuffer.toString();
        }

        @Override // java.text.Format
        public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            stringBuffer.append(format((Number) obj));
            return stringBuffer;
        }

        @Override // java.text.Format
        public Object parseObject(String str, ParsePosition parsePosition) {
            return f176df.parseObject(str, parsePosition);
        }
    }

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.DataFormatter$ZipPlusFourFormat */

    private static final class ZipPlusFourFormat extends Format {

        /* renamed from: df */
        private static final DecimalFormat f177df = DataFormatter.createIntegerOnlyFormat("000000000");
        public static final Format instance = new ZipPlusFourFormat();

        private ZipPlusFourFormat() {
        }

        public static String format(Number number) {
            String format = f177df.format(number);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(format.substring(0, 5));
            stringBuffer.append('-');
            stringBuffer.append(format.substring(5, 9));
            return stringBuffer.toString();
        }

        @Override // java.text.Format
        public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            stringBuffer.append(format((Number) obj));
            return stringBuffer;
        }

        @Override // java.text.Format
        public Object parseObject(String str, ParsePosition parsePosition) {
            return f177df.parseObject(str, parsePosition);
        }
    }

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.DataFormatter$PhoneFormat */

    private static final class PhoneFormat extends Format {

        /* renamed from: df */
        private static final DecimalFormat f175df = DataFormatter.createIntegerOnlyFormat("##########");
        public static final Format instance = new PhoneFormat();

        private PhoneFormat() {
        }

        public static String format(Number number) {
            String format = f175df.format(number);
            StringBuffer stringBuffer = new StringBuffer();
            int length = format.length();
            if (length <= 4) {
                return format;
            }
            int i = length - 4;
            String substring = format.substring(i, length);
            int i2 = length - 7;
            String substring2 = format.substring(Math.max(0, i2), i);
            String substring3 = format.substring(Math.max(0, length - 10), Math.max(0, i2));
            if (substring3 != null && substring3.trim().length() > 0) {
                stringBuffer.append('(');
                stringBuffer.append(substring3);
                stringBuffer.append(") ");
            }
            if (substring2 != null && substring2.trim().length() > 0) {
                stringBuffer.append(substring2);
                stringBuffer.append('-');
            }
            stringBuffer.append(substring);
            return stringBuffer.toString();
        }

        @Override // java.text.Format
        public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            stringBuffer.append(format((Number) obj));
            return stringBuffer;
        }

        @Override // java.text.Format
        public Object parseObject(String str, ParsePosition parsePosition) {
            return f175df.parseObject(str, parsePosition);
        }
    }

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.usermodel.DataFormatter$ConstantStringFormat */

    private static final class ConstantStringFormat extends Format {

        /* renamed from: df */
        private static final DecimalFormat f174df = DataFormatter.createIntegerOnlyFormat("##########");
        private final String str;

        public ConstantStringFormat(String str) {
            this.str = str;
        }

        @Override // java.text.Format
        public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            stringBuffer.append(this.str);
            return stringBuffer;
        }

        @Override // java.text.Format
        public Object parseObject(String str, ParsePosition parsePosition) {
            return f174df.parseObject(str, parsePosition);
        }
    }
}
