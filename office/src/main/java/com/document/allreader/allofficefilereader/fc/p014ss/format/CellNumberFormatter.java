package com.document.allreader.allofficefilereader.fc.p014ss.format;

import com.document.allreader.allofficefilereader.fc.openxml4j.opc.PackagingURIHelper;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.BitSet;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.format.CellNumberFormatter */

public class CellNumberFormatter extends CellFormatter {
    private static final CellFormatter SIMPLE_FLOAT = new CellNumberFormatter("#.#");
    private static final CellFormatter SIMPLE_INT = new CellNumberFormatter("#");
    static final CellFormatter SIMPLE_NUMBER = new CellFormatter("General") { // from class: com.allreader.office.allofficefilereader.fc.ss.format.CellNumberFormatter.1
        @Override // fc4ss.format.CellFormatter
        public void formatValue(StringBuffer stringBuffer, Object obj) {
            if (obj != null) {
                if (!(obj instanceof Number)) {
                    CellTextFormatter.SIMPLE_TEXT.formatValue(stringBuffer, obj);
                } else if (((Number) obj).doubleValue() % 1.0d == 0.0d) {
                    CellNumberFormatter.SIMPLE_INT.formatValue(stringBuffer, obj);
                } else {
                    CellNumberFormatter.SIMPLE_FLOAT.formatValue(stringBuffer, obj);
                }
            }
        }

        @Override // fc4ss.format.CellFormatter
        public void simpleValue(StringBuffer stringBuffer, Object obj) {
            formatValue(stringBuffer, obj);
        }
    };
    private Special afterFractional;
    private Special afterInteger;
    private DecimalFormat decimalFmt;
    private Special decimalPoint;
    private String denominatorFmt;
    private List<Special> denominatorSpecials;
    private final String desc;
    private Special exponent;
    private List<Special> exponentDigitSpecials;
    private List<Special> exponentSpecials;
    private List<Special> fractionalSpecials;
    private boolean improperFraction;
    private boolean integerCommas;
    private List<Special> integerSpecials;
    private int maxDenominator;
    private Special numerator;
    private String numeratorFmt;
    private List<Special> numeratorSpecials;
    private String printfFmt;
    private double scale = 1.0d;
    private Special slash;
    private final List<Special> specials;

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.format.CellNumberFormatter$Special */

    static class Special {

        /* renamed from: ch */
        final char f168ch;
        int pos;

        Special(char c, int i) {
            this.f168ch = c;
            this.pos = i;
        }

        public String toString() {
            return "'" + this.f168ch + "' @ " + this.pos;
        }
    }

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.format.CellNumberFormatter$StringMod */

    static class StringMod implements Comparable<StringMod> {
        public static final int AFTER = 2;
        public static final int BEFORE = 1;
        public static final int REPLACE = 3;
        Special end;
        boolean endInclusive;

        /* renamed from: op */
        final int f169op;
        final Special special;
        boolean startInclusive;
        CharSequence toAdd;

        private StringMod(Special special, CharSequence charSequence, int i) {
            this.special = special;
            this.toAdd = charSequence;
            this.f169op = i;
        }

        public StringMod(Special special, boolean z, Special special2, boolean z2, char c) {
            this(special, z, special2, z2);
            this.toAdd = c + "";
        }

        public StringMod(Special special, boolean z, Special special2, boolean z2) {
            this.special = special;
            this.startInclusive = z;
            this.end = special2;
            this.endInclusive = z2;
            this.f169op = 3;
            this.toAdd = "";
        }

        public int compareTo(StringMod stringMod) {
            int i = this.special.pos - stringMod.special.pos;
            if (i != 0) {
                return i;
            }
            return this.f169op - stringMod.f169op;
        }

        @Override 
        public boolean equals(Object obj) {
            try {
                return compareTo((StringMod) obj) == 0;
            } catch (RuntimeException unused) {
                return false;
            }
        }

        @Override 
        public int hashCode() {
            return this.special.hashCode() + this.f169op;
        }
    }

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.format.CellNumberFormatter$NumPartHandler */

    private class NumPartHandler implements CellFormatPart.PartHandler {
        private char insertSignForExponent;

        private NumPartHandler() {
        }

        @Override // fc4ss.format.CellFormatPart.PartHandler
        public String handlePart(Matcher matcher, String str, CellFormatType cellFormatType, StringBuffer stringBuffer) {
            int length = stringBuffer.length();
            char charAt = str.charAt(0);
            if (charAt != '#') {
                if (charAt == '%') {
                    CellNumberFormatter.this.scale *= 100.0d;
                } else if (charAt != '?') {
                    if (charAt != 'E' && charAt != 'e') {
                        switch (charAt) {
                            case '.':
                                if (CellNumberFormatter.this.decimalPoint == null && CellNumberFormatter.this.specials.size() > 0) {
                                    CellNumberFormatter.this.specials.add(CellNumberFormatter.this.decimalPoint = new Special('.', length));
                                    break;
                                }
                            case '/':
                                if (CellNumberFormatter.this.slash == null && CellNumberFormatter.this.specials.size() > 0) {
                                    CellNumberFormatter cellNumberFormatter = CellNumberFormatter.this;
                                    cellNumberFormatter.numerator = cellNumberFormatter.previousNumber();
                                    if (CellNumberFormatter.this.numerator == CellNumberFormatter.firstDigit(CellNumberFormatter.this.specials)) {
                                        CellNumberFormatter.this.improperFraction = true;
                                    }
                                    CellNumberFormatter.this.specials.add(CellNumberFormatter.this.slash = new Special('.', length));
                                    break;
                                }
                            case '0':
                                break;
                            default:
                                return null;
                        }
                    } else if (CellNumberFormatter.this.exponent == null && CellNumberFormatter.this.specials.size() > 0) {
                        CellNumberFormatter.this.specials.add(CellNumberFormatter.this.exponent = new Special('.', length));
                        this.insertSignForExponent = str.charAt(1);
                        return str.substring(0, 1);
                    }
                }
                return str;
            }
            if (this.insertSignForExponent != 0) {
                CellNumberFormatter.this.specials.add(new Special(this.insertSignForExponent, length));
                stringBuffer.append(this.insertSignForExponent);
                this.insertSignForExponent = 0;
                length++;
            }
            for (int i = 0; i < str.length(); i++) {
                CellNumberFormatter.this.specials.add(new Special(str.charAt(i), length + i));
            }
            return str;
        }
    }

    public CellNumberFormatter(String str) {
        super(str);
        int i;
        int i2;
        LinkedList linkedList = new LinkedList();
        this.specials = linkedList;
        StringBuffer parseFormat = CellFormatPart.parseFormat(str, CellFormatType.NUMBER, new NumPartHandler());
        if (!((this.decimalPoint == null && this.exponent == null) || this.slash == null)) {
            this.slash = null;
            this.numerator = null;
        }
        interpretCommas(parseFormat);
        if (this.decimalPoint == null) {
            i2 = 0;
            i = 0;
        } else {
            i2 = interpretPrecision();
            i = i2 + 1;
            if (i2 == 0) {
                linkedList.remove(this.decimalPoint);
                this.decimalPoint = null;
            }
        }
        boolean z = true;
        if (i2 == 0) {
            this.fractionalSpecials = Collections.emptyList();
        } else {
            this.fractionalSpecials = linkedList.subList(linkedList.indexOf(this.decimalPoint) + 1, fractionalEnd());
        }
        Special special = this.exponent;
        if (special == null) {
            this.exponentSpecials = Collections.emptyList();
        } else {
            int indexOf = linkedList.indexOf(special);
            this.exponentSpecials = specialsFor(indexOf, 2);
            this.exponentDigitSpecials = specialsFor(indexOf + 2);
        }
        if (this.slash == null) {
            this.numeratorSpecials = Collections.emptyList();
            this.denominatorSpecials = Collections.emptyList();
        } else {
            Special special2 = this.numerator;
            if (special2 == null) {
                this.numeratorSpecials = Collections.emptyList();
            } else {
                this.numeratorSpecials = specialsFor(linkedList.indexOf(special2));
            }
            List<Special> specialsFor = specialsFor(linkedList.indexOf(this.slash) + 1);
            this.denominatorSpecials = specialsFor;
            if (specialsFor.isEmpty()) {
                this.numeratorSpecials = Collections.emptyList();
            } else {
                this.maxDenominator = maxValue(this.denominatorSpecials);
                this.numeratorFmt = singleNumberFormat(this.numeratorSpecials);
                this.denominatorFmt = singleNumberFormat(this.denominatorSpecials);
            }
        }
        this.integerSpecials = linkedList.subList(0, integerEnd());
        if (this.exponent == null) {
            StringBuffer stringBuffer = new StringBuffer("%");
            stringBuffer.append('0');
            stringBuffer.append(calculateIntegerPartWidth() + i);
            stringBuffer.append('.');
            stringBuffer.append(i2);
            stringBuffer.append("f");
            this.printfFmt = stringBuffer.toString();
        } else {
            StringBuffer stringBuffer2 = new StringBuffer();
            List<Special> list = this.integerSpecials;
            if (list.size() == 1) {
                stringBuffer2.append("0");
                z = false;
            } else {
                for (Special special3 : list) {
                    if (isDigitFmt(special3)) {
                        stringBuffer2.append(z ? '#' : '0');
                        z = false;
                    }
                }
            }
            if (this.fractionalSpecials.size() > 0) {
                stringBuffer2.append('.');
                for (Special special4 : this.fractionalSpecials) {
                    if (isDigitFmt(special4)) {
                        if (!z) {
                            stringBuffer2.append('0');
                        }
                        z = false;
                    }
                }
            }
            stringBuffer2.append('E');
            List<Special> list2 = this.exponentSpecials;
            placeZeros(stringBuffer2, list2.subList(2, list2.size()));
            this.decimalFmt = new DecimalFormat(stringBuffer2.toString());
        }
        if (this.exponent != null) {
            this.scale = 1.0d;
        }
        this.desc = parseFormat.toString();
    }

    private static void placeZeros(StringBuffer stringBuffer, List<Special> list) {
        for (Special special : list) {
            if (isDigitFmt(special)) {
                stringBuffer.append('0');
            }
        }
    }

    
    public static Special firstDigit(List<Special> list) {
        for (Special special : list) {
            if (isDigitFmt(special)) {
                return special;
            }
        }
        return null;
    }

    static StringMod insertMod(Special special, CharSequence charSequence, int i) {
        return new StringMod(special, charSequence, i);
    }

    static StringMod deleteMod(Special special, boolean z, Special special2, boolean z2) {
        return new StringMod(special, z, special2, z2);
    }

    static StringMod replaceMod(Special special, boolean z, Special special2, boolean z2, char c) {
        return new StringMod(special, z, special2, z2, c);
    }

    private static String singleNumberFormat(List<Special> list) {
        return "%0" + list.size() + "d";
    }

    private static int maxValue(List<Special> list) {
        return (int) Math.round(Math.pow(10.0d, (double) list.size()) - 1.0d);
    }

    private List<Special> specialsFor(int i, int i2) {
        if (i >= this.specials.size()) {
            return Collections.emptyList();
        }
        int i3 = i2 + i;
        ListIterator<Special> listIterator = this.specials.listIterator(i3);
        Special next = listIterator.next();
        while (listIterator.hasNext()) {
            Special next2 = listIterator.next();
            if (!isDigitFmt(next2) || next2.pos - next.pos > 1) {
                break;
            }
            i3++;
            next = next2;
        }
        return this.specials.subList(i, i3 + 1);
    }

    private List<Special> specialsFor(int i) {
        return specialsFor(i, 0);
    }

    private static boolean isDigitFmt(Special special) {
        return special.f168ch == '0' || special.f168ch == '?' || special.f168ch == '#';
    }

    
    public Special previousNumber() {
        List<Special> list = this.specials;
        ListIterator<Special> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            Special previous = listIterator.previous();
            if (isDigitFmt(previous)) {
                while (listIterator.hasPrevious()) {
                    Special previous2 = listIterator.previous();
                    if (previous.pos - previous2.pos > 1 || !isDigitFmt(previous2)) {
                        break;
                    }
                    previous = previous2;
                }
                return previous;
            }
        }
        return null;
    }

    private int calculateIntegerPartWidth() {
        Special next;
        ListIterator<Special> listIterator = this.specials.listIterator();
        int i = 0;
        while (listIterator.hasNext() && (next = listIterator.next()) != this.afterInteger) {
            if (isDigitFmt(next)) {
                i++;
            }
        }
        return i;
    }

    private int interpretPrecision() {
        Special special = this.decimalPoint;
        if (special == null) {
            return -1;
        }
        int i = 0;
        List<Special> list = this.specials;
        ListIterator<Special> listIterator = list.listIterator(list.indexOf(special));
        if (listIterator.hasNext()) {
            listIterator.next();
        }
        while (listIterator.hasNext() && isDigitFmt(listIterator.next())) {
            i++;
        }
        return i;
    }

    private void interpretCommas(StringBuffer stringBuffer) {
        ListIterator<Special> listIterator = this.specials.listIterator(integerEnd());
        int i = 0;
        this.integerCommas = false;
        boolean z = true;
        while (listIterator.hasPrevious()) {
            if (listIterator.previous().f168ch != ',') {
                z = false;
            } else if (z) {
                this.scale /= 1000.0d;
            } else {
                this.integerCommas = true;
            }
        }
        if (this.decimalPoint != null) {
            ListIterator<Special> listIterator2 = this.specials.listIterator(fractionalEnd());
            while (listIterator2.hasPrevious() && listIterator2.previous().f168ch == ',') {
                this.scale /= 1000.0d;
            }
        }
        ListIterator<Special> listIterator3 = this.specials.listIterator();
        while (listIterator3.hasNext()) {
            Special next = listIterator3.next();
            next.pos -= i;
            if (next.f168ch == ',') {
                i++;
                listIterator3.remove();
                stringBuffer.deleteCharAt(next.pos);
            }
        }
    }

    private int integerEnd() {
        Special special = this.decimalPoint;
        if (special != null) {
            this.afterInteger = special;
        } else {
            Special special2 = this.exponent;
            if (special2 != null) {
                this.afterInteger = special2;
            } else {
                Special special3 = this.numerator;
                if (special3 != null) {
                    this.afterInteger = special3;
                } else {
                    this.afterInteger = null;
                }
            }
        }
        Special special4 = this.afterInteger;
        return special4 == null ? this.specials.size() : this.specials.indexOf(special4);
    }

    private int fractionalEnd() {
        Special special = this.exponent;
        if (special != null) {
            this.afterFractional = special;
        } else {
            Special special2 = this.numerator;
            if (special2 != null) {
                this.afterInteger = special2;
            } else {
                this.afterFractional = null;
            }
        }
        Special special3 = this.afterFractional;
        return special3 == null ? this.specials.size() : this.specials.indexOf(special3);
    }


    @Override


    public void formatValue(StringBuffer stringBuffer, Object obj) {
        double d;
        double d2;
        int i = 0;
        double doubleValue = ((Number) obj).doubleValue() * this.scale;
        double d3 = 0.0d;
        int i2 = 1;
        boolean z = doubleValue < 0.0d;
        if (z) {
            doubleValue = -doubleValue;
        }
        ListIterator<Special> listIterator;
        if (this.slash != null) {
            if (this.improperFraction) {
                d = doubleValue;
                d2 = 0.0d;
                TreeSet treeSet = new TreeSet();
                StringBuffer stringBuffer2 = new StringBuffer(this.desc);
                if (this.exponent == null) {
                    writeScientific(d2, stringBuffer2, treeSet);
                } else if (this.improperFraction) {
                    writeFraction(d2, null, d, stringBuffer2, treeSet);
                } else {
                    StringBuffer stringBuffer3 = new StringBuffer();
                    new Formatter(stringBuffer3).format(LOCALE, this.printfFmt, Double.valueOf(d2));
                    if (this.numerator == null) {
                        writeFractional(stringBuffer3, stringBuffer2);
                        writeInteger(stringBuffer3, stringBuffer2, this.integerSpecials, treeSet, this.integerCommas);
                    } else {
                        writeFraction(d2, stringBuffer3, d, stringBuffer2, treeSet);
                    }
                }
                listIterator = this.specials.listIterator();
                Iterator<StringMod> it = treeSet.iterator();
                StringMod next = !it.hasNext() ? it.next() : null;
                BitSet bitSet = new BitSet();
                int i3 = 0;
                while (listIterator.hasNext()) {
                    Special next2 = listIterator.next();
                    int i4 = next2.pos + i3;
                    if (!bitSet.get(next2.pos) && stringBuffer2.charAt(i4) == '#') {
                        stringBuffer2.deleteCharAt(i4);
                        i3--;
                        bitSet.set(next2.pos);
                    }
                    while (next != null && next2 == next.special) {
                        int length = stringBuffer2.length();
                        int i5 = next2.pos + i3;
                        int i6 = next.f169op;
                        ListIterator<Special> listIterator2 = null;
                        if (i6 != i2) {
                            if (i6 == 2) {
                                listIterator2 = listIterator;
                                if (!next.toAdd.equals(",") || !bitSet.get(next2.pos)) {
                                    i = 1;
                                }
                            } else if (i6 == 3) {
                                int i7 = next2.pos;
                            } else {
                                throw new IllegalStateException("Unknown op: " + next.f169op);
                            }
                            i3 += stringBuffer2.length() - length;
                            next = !it.hasNext() ? it.next() : null;

                        } else {
                            listIterator2 = listIterator;
                            i = 0;
                        }
                        stringBuffer2.insert(i5 + i, next.toAdd);
                        i3 += stringBuffer2.length() - length;
                        if (!it.hasNext()) {
                        }
                        listIterator = listIterator2;
                        i2 = 1;
                    }
                    listIterator = listIterator;
                    i2 = 1;
                }
                if (z) {
                    stringBuffer.append('-');
                }
                stringBuffer.append(stringBuffer2);
            }
            d3 = doubleValue % 1.0d;
            doubleValue = (double) ((long) doubleValue);
        }
        d = d3;
        d2 = doubleValue;
        TreeSet treeSet = new TreeSet();
        StringBuffer stringBuffer2 = new StringBuffer(this.desc);
        if (this.exponent == null) {
        }
        listIterator = this.specials.listIterator();
        Iterator<StringMod> it = treeSet.iterator();
        if (!it.hasNext()) {
        }
        BitSet bitSet = new BitSet();
        int i3 = 0;
        while (listIterator.hasNext()) {
        }
        if (z) {
        }
        stringBuffer.append(stringBuffer2);
    }

    private void writeScientific(double d, StringBuffer stringBuffer, Set<StringMod> set) {
        StringBuffer stringBuffer2 = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(1);
        this.decimalFmt.format(d, stringBuffer2, fieldPosition);
        writeInteger(stringBuffer2, stringBuffer, this.integerSpecials, set, this.integerCommas);
        writeFractional(stringBuffer2, stringBuffer);
        int endIndex = fieldPosition.getEndIndex() + 1;
        char charAt = stringBuffer2.charAt(endIndex);
        if (charAt != '-') {
            stringBuffer2.insert(endIndex, '+');
            charAt = '+';
        }
        Special next = this.exponentSpecials.listIterator(1).next();
        char c = next.f168ch;
        if (charAt == '-' || c == '+') {
            set.add(replaceMod(next, true, next, true, charAt));
        } else {
            set.add(deleteMod(next, true, next, true));
        }
        writeInteger(new StringBuffer(stringBuffer2.substring(endIndex + 1)), stringBuffer, this.exponentDigitSpecials, set, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x0137 A[Catch:{ RuntimeException -> 0x015e }] */
    private void writeFraction(double d, StringBuffer stringBuffer, double d2, StringBuffer stringBuffer2, Set<StringMod> set) {
        double d3;
        int i;
        int i2 = 1;
        if (!this.improperFraction) {
            int i3 = (d2 > 0.0d ? 1 : (d2 == 0.0d ? 0 : -1));
            if (i3 != 0 || hasChar('0', this.numeratorSpecials)) {
                int i4 = (d > 0.0d ? 1 : (d == 0.0d ? 0 : -1));
                boolean z = i4 == 0 && i3 == 0;
                boolean z2 = i3 != 0 || hasChar('0', new List[]{this.numeratorSpecials});
                boolean z3 = z && (hasOnly('#', new List[]{this.integerSpecials}) || !hasChar('0', new List[]{this.numeratorSpecials}));
                boolean z4 = !z && i4 == 0 && z2 && !hasChar('0', new List[]{this.integerSpecials});
                if (z3 || z4) {
                    List<Special> list = this.integerSpecials;
                    Special special = list.get(list.size() - 1);
                    if (hasChar('?', this.integerSpecials, this.numeratorSpecials)) {
                        set.add(replaceMod(special, true, this.numerator, false, ' '));
                    } else {
                        set.add(deleteMod(special, true, this.numerator, false));
                    }
                } else {
                    writeInteger(stringBuffer, stringBuffer2, this.integerSpecials, set, false);
                }
                d3 = 0.0d;
            } else {
                writeInteger(stringBuffer, stringBuffer2, this.integerSpecials, set, false);
                List<Special> list2 = this.integerSpecials;
                Special special2 = list2.get(list2.size() - 1);
                List<Special> list3 = this.denominatorSpecials;
                Special special3 = list3.get(list3.size() - 1);
                if (hasChar('?', this.integerSpecials, this.numeratorSpecials, this.denominatorSpecials)) {
                    set.add(replaceMod(special2, false, special3, true, ' '));
                    return;
                } else {
                    set.add(deleteMod(special2, false, special3, true));
                    return;
                }
            }
        } else {
            d3 = 0.0d;
        }
        if (d2 != d3) {
            try {
                if (!this.improperFraction || d2 % 1.0d != d3) {
                    Fraction fraction = new Fraction(d2, this.maxDenominator);
                    i = fraction.getNumerator();
                    i2 = fraction.getDenominator();
                    if (this.improperFraction) {
                        i = (int) (((long) i) + Math.round(((double) i2) * d));
                    }
                    writeSingleInteger(this.numeratorFmt, i, stringBuffer2, this.numeratorSpecials, set);
                    writeSingleInteger(this.denominatorFmt, i2, stringBuffer2, this.denominatorSpecials, set);
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                return;
            }
        }
        i = (int) Math.round(d2);
        if (this.improperFraction) {
        }
        writeSingleInteger(this.numeratorFmt, i, stringBuffer2, this.numeratorSpecials, set);
        writeSingleInteger(this.denominatorFmt, i2, stringBuffer2, this.denominatorSpecials, set);
    }

    private static boolean hasChar(char c, List<Special>... listArr) {
        for (List<Special> list : listArr) {
            for (Special special : list) {
                if (special.f168ch == c) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasOnly(char c, List<Special>... listArr) {
        for (List<Special> list : listArr) {
            for (Special special : list) {
                if (special.f168ch != c) {
                    return false;
                }
            }
        }
        return true;
    }

    private void writeSingleInteger(String str, int i, StringBuffer stringBuffer, List<Special> list, Set<StringMod> set) {
        StringBuffer stringBuffer2 = new StringBuffer();
        new Formatter(stringBuffer2).format(LOCALE, str, Integer.valueOf(i));
        writeInteger(stringBuffer2, stringBuffer, list, set, false);
    }

    private void writeInteger(StringBuffer stringBuffer, StringBuffer stringBuffer2, List<Special> list, Set<StringMod> set, boolean z) {
        boolean z2;
        int i;
        int indexOf = stringBuffer.indexOf(".") - 1;
        if (indexOf < 0) {
            if (this.exponent == null || list != this.integerSpecials) {
                i = stringBuffer.length();
            } else {
                i = stringBuffer.indexOf("E");
            }
            indexOf = i - 1;
        }
        int i2 = 0;
        while (i2 < indexOf &&  stringBuffer.charAt(i2) == '0') {
            i2++;
        }
        ListIterator<Special> listIterator = list.listIterator(list.size());
        Special special = null;
        int i3 = 0;
        while (listIterator.hasPrevious()) {
            char charAt = indexOf >= 0 ? stringBuffer.charAt(indexOf) : '0';
            Special previous = listIterator.previous();
            boolean z3 = z && i3 > 0 && i3 % 3 == 0;
            if (charAt != '0' || previous.f168ch == '0' || previous.f168ch == '?' || indexOf >= i2) {
                z2 = previous.f168ch == '?' && indexOf < i2;
                int i4 = previous.pos;
                if (z2) {
                    charAt = ' ';
                }
                stringBuffer2.setCharAt(i4, charAt);
                special = previous;
            } else {
                z2 = false;
            }
            if (z3) {
                set.add(insertMod(previous, z2 ? " " : ",", 2));
            }
            i3++;
            indexOf--;
        }
        if (indexOf >= 0) {
            int i5 = indexOf + 1;
            StringBuffer stringBuffer3 = new StringBuffer(stringBuffer.substring(0, i5));
            if (z) {
                while (i5 > 0) {
                    if (i3 > 0 && i3 % 3 == 0) {
                        stringBuffer3.insert(i5, ',');
                    }
                    i3++;
                    i5--;
                }
            }
            set.add(insertMod(special, stringBuffer3, 1));
        }
    }

    private void writeFractional(StringBuffer stringBuffer, StringBuffer stringBuffer2) {
        int i;
        if (this.fractionalSpecials.size() > 0) {
            int indexOf = stringBuffer.indexOf(".") + 1;
            if (this.exponent != null) {
                i = stringBuffer.indexOf("e");
            } else {
                i = stringBuffer.length();
            }
            while (true) {
                i--;
                if (i <= indexOf || stringBuffer.charAt(i) != '0') {
                    break;
                }
            }
            ListIterator<Special> listIterator = this.fractionalSpecials.listIterator();
            while (listIterator.hasNext()) {
                Special next = listIterator.next();
                char charAt = stringBuffer.charAt(indexOf);
                if (charAt != '0' || next.f168ch == '0' || indexOf < i) {
                    stringBuffer2.setCharAt(next.pos, charAt);
                } else if (next.f168ch == '?') {
                    stringBuffer2.setCharAt(next.pos, ' ');
                }
                indexOf++;
            }
        }
    }

    @Override // fc4ss.format.CellFormatter
    public void simpleValue(StringBuffer stringBuffer, Object obj) {
        SIMPLE_NUMBER.formatValue(stringBuffer, obj);
    }

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.format.CellNumberFormatter$Fraction */

    private static class Fraction {
        private final int denominator;
        private final int numerator;

        private Fraction(double d, double d2, int i, int i2) {
            long j;
            long j2;
            long j3;
            long j4;
            long floor = (long) Math.floor(d);
            String str = ")";
            String str2 = PackagingURIHelper.FORWARD_SLASH_STRING;
            if (floor > 2147483647L) {
                throw new IllegalArgumentException("Overflow trying to convert " + d + " to fraction (" + floor + str2 + 1L + str);
            } else if (Math.abs(((double) floor) - d) < d2) {
                this.numerator = (int) floor;
                this.denominator = 1;
            } else {
                int i3 = 0;
                double d3 = d;
                long j5 = 0;
                long j6 = 1;
                long j7 = 1;
                boolean z = false;
                long j8 = floor;
                while (true) {
                    int i4 = i3 + 1;
                    double d4 = 1.0d / (d3 - ((double) floor));
                    long floor2 = (long) Math.floor(d4);
                    j = (floor2 * j8) + j6;
                    j2 = (floor2 * j7) + j5;
                    if (j > 2147483647L || j2 > 2147483647L) {
                        break;
                    }
                    long j9 = floor2;
                    double d5 = ((double) j) / ((double) j2);
                    if (i4 >= i2 || Math.abs(d5 - d) <= d2 || j2 >= ((long) i)) {
                        j3 = j7;
                        j9 = floor;
                        j4 = j8;
                        z = true;
                    } else {
                        j4 = j;
                        j3 = j2;
                        j5 = j7;
                        d3 = d4;
                        j6 = j8;
                    }
                    if (!z) {
                        j8 = j4;
                        i3 = i4;
                        j7 = j3;
                        floor = j9;
                        str = str;
                        str2 = str2;
                    } else if (i4 >= i2) {
                        throw new RuntimeException("Unable to convert " + d + " to fraction after " + i2 + " iterations");
                    } else if (j2 < ((long) i)) {
                        this.numerator = (int) j;
                        this.denominator = (int) j2;
                        return;
                    } else {
                        this.numerator = (int) j4;
                        this.denominator = (int) j3;
                        return;
                    }
                }
                throw new RuntimeException("Overflow trying to convert " + d + " to fraction (" + j + str2 + j2 + str);
            }
        }

        public Fraction(double d, int i) {
            this(d, 0.0d, i, 100);
        }

        public int getDenominator() {
            return this.denominator;
        }

        public int getNumerator() {
            return this.numerator;
        }
    }
}
