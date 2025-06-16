package com.document.allreader.allofficefilereader.fc.p014ss.format;

import android.graphics.Color;
import com.document.allreader.allofficefilereader.fc.hssf.util.HSSFColor;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.format.CellFormatPart */

public class CellFormatPart {
    public static final int COLOR_GROUP;
    public static final Pattern COLOR_PAT = Pattern.compile("\\[(black|blue|cyan|green|magenta|red|white|yellow|color [0-9]+)\\]", 6);
    public static final int CONDITION_OPERATOR_GROUP;
    public static final Pattern CONDITION_PAT = Pattern.compile("([<>=]=?|!=|<>)    # The operator\n  \\s*([0-9]+(?:\\.[0-9]*)?)\\s*  # The constant to test against\n", 6);
    public static final int CONDITION_VALUE_GROUP;
    public static final Pattern FORMAT_PAT;
    private static final Map<String, Integer> NAMED_COLORS = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    public static final int SPECIFICATION_GROUP;
    public static final Pattern SPECIFICATION_PAT = Pattern.compile("\\\\.                 # Quoted single character\n|\"([^\\\\\"]|\\\\.)*\"         # Quoted string of characters (handles escaped quotes like \\\") \n|_.                             # Space as wide as a given character\n|\\*.                           # Repeating fill character\n|@                              # Text: cell text\n|([0?\\#](?:[0?\\#,]*))         # Number: digit + other digits and commas\n|e[-+]                          # Number: Scientific: Exponent\n|m{1,5}                         # Date: month or minute spec\n|d{1,4}                         # Date: day/date spec\n|y{2,4}                         # Date: year spec\n|h{1,2}                         # Date: hour spec\n|s{1,2}                         # Date: second spec\n|am?/pm?                        # Date: am/pm spec\n|\\[h{1,2}\\]                   # Elapsed time: hour spec\n|\\[m{1,2}\\]                   # Elapsed time: minute spec\n|\\[s{1,2}\\]                   # Elapsed time: second spec\n|[^;]                           # A character\n", 6);
    private final int color;
    private CellFormatCondition condition;
    private final CellFormatter format;

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.format.CellFormatPart$PartHandler */
    
    interface PartHandler {
        String handlePart(Matcher matcher, String str, CellFormatType cellFormatType, StringBuffer stringBuffer);
    }

    static {
        for (HSSFColor hSSFColor : HSSFColor.getIndexHash().values()) {
            String simpleName = hSSFColor.getClass().getSimpleName();
            if (simpleName.equals(simpleName.toUpperCase())) {
                short[] triplet = hSSFColor.getTriplet();
                int rgb = Color.rgb((int) triplet[0], (int) triplet[1], (int) triplet[2]);
                Map<String, Integer> map = NAMED_COLORS;
                map.put(simpleName, Integer.valueOf(rgb));
                if (simpleName.indexOf(95) > 0) {
                    map.put(simpleName.replace('_', ' '), Integer.valueOf(rgb));
                }
                if (simpleName.indexOf("_PERCENT") > 0) {
                    map.put(simpleName.replace("_PERCENT", "%").replace('_', ' '), Integer.valueOf(rgb));
                }
            }
        }
        Pattern compile = Pattern.compile("(?:\\[(black|blue|cyan|green|magenta|red|white|yellow|color [0-9]+)\\])?                  # Text color\n(?:\\[([<>=]=?|!=|<>)    # The operator\n  \\s*([0-9]+(?:\\.[0-9]*)?)\\s*  # The constant to test against\n\\])?                # Condition\n((?:\\\\.                 # Quoted single character\n|\"([^\\\\\"]|\\\\.)*\"         # Quoted string of characters (handles escaped quotes like \\\") \n|_.                             # Space as wide as a given character\n|\\*.                           # Repeating fill character\n|@                              # Text: cell text\n|([0?\\#](?:[0?\\#,]*))         # Number: digit + other digits and commas\n|e[-+]                          # Number: Scientific: Exponent\n|m{1,5}                         # Date: month or minute spec\n|d{1,4}                         # Date: day/date spec\n|y{2,4}                         # Date: year spec\n|h{1,2}                         # Date: hour spec\n|s{1,2}                         # Date: second spec\n|am?/pm?                        # Date: am/pm spec\n|\\[h{1,2}\\]                   # Elapsed time: hour spec\n|\\[m{1,2}\\]                   # Elapsed time: minute spec\n|\\[s{1,2}\\]                   # Elapsed time: second spec\n|[^;]                           # A character\n)+)                        # Format spec\n", 6);
        FORMAT_PAT = compile;
        COLOR_GROUP = findGroup(compile, "[Blue]@", "Blue");
        CONDITION_OPERATOR_GROUP = findGroup(compile, "[>=1]@", ">=");
        CONDITION_VALUE_GROUP = findGroup(compile, "[>=1]@", "1");
        SPECIFICATION_GROUP = findGroup(compile, "[Blue][>1]\\a ?", "\\a ?");
    }

    public CellFormatPart(String str) {
        Matcher matcher = FORMAT_PAT.matcher(str);
        if (matcher.matches()) {
            this.color = getColor(matcher);
            this.condition = getCondition(matcher);
            this.format = getFormatter(matcher);
            return;
        }
        throw new IllegalArgumentException("Unrecognized format: " + CellFormatter.quote(str));
    }

    public boolean applies(Object obj) {
        CellFormatCondition cellFormatCondition = this.condition;
        if (cellFormatCondition != null && (obj instanceof Number)) {
            return cellFormatCondition.pass(((Number) obj).doubleValue());
        }
        Objects.requireNonNull(obj, "valueObject");
        return true;
    }

    private static int findGroup(Pattern pattern, String str, String str2) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                String group = matcher.group(i);
                if (group != null && group.equals(str2)) {
                    return i;
                }
            }
            throw new IllegalArgumentException("\"" + str2 + "\" not found in \"" + pattern.pattern() + "\"");
        }
        throw new IllegalArgumentException("Pattern \"" + pattern.pattern() + "\" doesn't match \"" + str + "\"");
    }

    private static int getColor(Matcher matcher) {
        String group = matcher.group(COLOR_GROUP);
        if (group == null || group.length() == 0) {
            return -1;
        }
        Integer num = NAMED_COLORS.get(group);
        if (num == null) {
            Logger logger = CellFormatter.logger;
            logger.warning("Unknown color: " + CellFormatter.quote(group));
        }
        return num.intValue();
    }

    private CellFormatCondition getCondition(Matcher matcher) {
        int i = CONDITION_OPERATOR_GROUP;
        String group = matcher.group(i);
        if (group == null || group.length() == 0) {
            return null;
        }
        return CellFormatCondition.getInstance(matcher.group(i), matcher.group(CONDITION_VALUE_GROUP));
    }

    private CellFormatter getFormatter(Matcher matcher) {
        String group = matcher.group(SPECIFICATION_GROUP);
        return formatType(group).formatter(group);
    }

    private CellFormatType formatType(String str) {
        String trim = str.trim();
        if (trim.equals("") || trim.equalsIgnoreCase("General")) {
            return CellFormatType.GENERAL;
        }
        Matcher matcher = SPECIFICATION_PAT.matcher(trim);
        boolean z = false;
        boolean z2 = false;
        while (matcher.find()) {
            String group = matcher.group(0);
            if (group.length() > 0) {
                switch (group.charAt(0)) {
                    case '#':
                    case '?':
                        return CellFormatType.NUMBER;
                    case '0':
                        z2 = true;
                        continue;
                    case '@':
                        return CellFormatType.TEXT;
                    case 'D':
                    case 'Y':
                    case 'd':
                    case 'y':
                        return CellFormatType.DATE;
                    case 'H':
                    case 'M':
                    case 'S':
                    case 'h':
                    case 'm':
                    case 's':
                        z = true;
                        continue;
                    case '[':
                        return CellFormatType.ELAPSED;
                }
            }
        }
        if (z) {
            return CellFormatType.DATE;
        }
        if (z2) {
            return CellFormatType.NUMBER;
        }
        return CellFormatType.TEXT;
    }

    static String quoteSpecial(String str, CellFormatType cellFormatType) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt != '\'' || !cellFormatType.isSpecial('\'')) {
                boolean isSpecial = cellFormatType.isSpecial(charAt);
                if (isSpecial) {
                    sb.append("'");
                }
                sb.append(charAt);
                if (isSpecial) {
                    sb.append("'");
                }
            } else {
                sb.append((char) 0);
            }
        }
        return sb.toString();
    }

    public CellFormatResult apply(Object obj) {
        String str;
        int i;
        boolean applies = applies(obj);
        if (applies) {
            str = this.format.format(obj);
            i = this.color;
        } else {
            str = this.format.simpleFormat(obj);
            i = -1;
        }
        return new CellFormatResult(applies, str, i);
    }

    public static StringBuffer parseFormat(String str, CellFormatType cellFormatType, PartHandler partHandler) {
        int i;
        Matcher matcher = SPECIFICATION_PAT.matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            i = 0;
            if (!matcher.find()) {
                break;
            }
            String group = group(matcher, 0);
            if (group.length() > 0) {
                String handlePart = partHandler.handlePart(matcher, group, cellFormatType, stringBuffer);
                if (handlePart == null) {
                    char charAt = group.charAt(0);
                    if (charAt == '\"') {
                        group = quoteSpecial(group.substring(1, group.length() - 1), cellFormatType);
                    } else if (charAt == '*') {
                        group = expandChar(group);
                    } else if (charAt == '\\') {
                        group = quoteSpecial(group.substring(1), cellFormatType);
                    } else if (charAt == '_') {
                        group = " ";
                    }
                } else {
                    group = handlePart;
                }
                matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(group));
            }
        }
        matcher.appendTail(stringBuffer);
        if (cellFormatType.isSpecial('\'')) {
            int i2 = 0;
            while (true) {
                i2 = stringBuffer.indexOf("''", i2);
                if (i2 < 0) {
                    break;
                }
                stringBuffer.delete(i2, i2 + 2);
            }
            while (true) {
                i = stringBuffer.indexOf("\u0000", i);
                if (i < 0) {
                    break;
                }
                stringBuffer.replace(i, i + 1, "''");
            }
        }
        return stringBuffer;
    }

    static String expandChar(String str) {
        char charAt = str.charAt(1);
        return "" + charAt + charAt + charAt;
    }

    public static String group(Matcher matcher, int i) {
        String group = matcher.group(i);
        return group == null ? "" : group;
    }
}
