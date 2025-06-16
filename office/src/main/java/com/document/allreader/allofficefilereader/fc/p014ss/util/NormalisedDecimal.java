package com.document.allreader.allofficefilereader.fc.p014ss.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.NormalisedDecimal */

final class NormalisedDecimal {
    private static final BigDecimal BD_2_POW_24 = new BigDecimal(BigInteger.ONE.shiftLeft(24));
    private static final int C_2_POW_19 = 524288;
    private static final int EXPONENT_OFFSET = 14;
    private static final int FRAC_HALF = 8388608;
    private static final int LOG_BASE_10_OF_2_TIMES_2_POW_20 = 315653;
    private static final long MAX_REP_WHOLE_PART = 1000000000000000L;
    private final int _fractionalPart;
    private final int _relativeDecimalExponent;
    private final long _wholePart;

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x004f, code lost:
        if (r1.isBelowMaxRep() != false) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005d, code lost:
        if (r1.isAboveMinRep() != false) goto L_0x0066;
     */
    public static NormalisedDecimal create(BigInteger bigInteger, int i) {
        int i2 = (i > 49 || i < 46) ? -(((15204352 - (LOG_BASE_10_OF_2_TIMES_2_POW_20 * i)) + 524288) >> 20) : 0;
        MutableFPNumber mutableFPNumber = new MutableFPNumber(bigInteger, i);
        if (i2 != 0) {
            mutableFPNumber.multiplyByPowerOfTen(-i2);
        }
        switch (mutableFPNumber.get64BitNormalisedExponent()) {
            case 46:
                break;
            case 44:
            case 45:
                mutableFPNumber.multiplyByPowerOfTen(1);
                i2--;
                break;
            case 47:
            case 48:
                break;
            case 49:
                break;
            case 50:
                mutableFPNumber.multiplyByPowerOfTen(-1);
                i2++;
                break;
            default:
                throw new IllegalStateException("Bad binary exp " + mutableFPNumber.get64BitNormalisedExponent() + ".");
        }
        mutableFPNumber.normalise64bit();
        return mutableFPNumber.createNormalisedDecimal(i2);
    }

    public NormalisedDecimal roundUnits() {
        long j = this._wholePart;
        if (this._fractionalPart >= 8388608) {
            j++;
        }
        int i = this._relativeDecimalExponent;
        if (j < MAX_REP_WHOLE_PART) {
            return new NormalisedDecimal(j, 0, i);
        }
        return new NormalisedDecimal(j / 10, 0, i + 1);
    }

    NormalisedDecimal(long j, int i, int i2) {
        this._wholePart = j;
        this._fractionalPart = i;
        this._relativeDecimalExponent = i2;
    }

    public ExpandedDouble normaliseBaseTwo() {
        MutableFPNumber mutableFPNumber = new MutableFPNumber(composeFrac(), 39);
        mutableFPNumber.multiplyByPowerOfTen(this._relativeDecimalExponent);
        mutableFPNumber.normalise64bit();
        return mutableFPNumber.createExpandedDouble();
    }

    BigInteger composeFrac() {
        long j = this._wholePart;
        int i = this._fractionalPart;
        return new BigInteger(new byte[]{(byte) ((int) (j >> 56)), (byte) ((int) (j >> 48)), (byte) ((int) (j >> 40)), (byte) ((int) (j >> 32)), (byte) ((int) (j >> 24)), (byte) ((int) (j >> 16)), (byte) ((int) (j >> 8)), (byte) ((int) (j >> 0)), (byte) (i >> 16), (byte) (i >> 8), (byte) (i >> 0)});
    }

    public String getSignificantDecimalDigits() {
        return Long.toString(this._wholePart);
    }

    public String getSignificantDecimalDigitsLastDigitRounded() {
        StringBuilder sb = new StringBuilder(24);
        sb.append(this._wholePart + 5);
        sb.setCharAt(sb.length() - 1, '0');
        return sb.toString();
    }

    public int getDecimalExponent() {
        return this._relativeDecimalExponent + 14;
    }

    public int compareNormalised(NormalisedDecimal normalisedDecimal) {
        int i = this._relativeDecimalExponent - normalisedDecimal._relativeDecimalExponent;
        if (i != 0) {
            return i;
        }
        long j = this._wholePart;
        long j2 = normalisedDecimal._wholePart;
        if (j > j2) {
            return 1;
        }
        if (j < j2) {
            return -1;
        }
        return this._fractionalPart - normalisedDecimal._fractionalPart;
    }

    public BigDecimal getFractionalPart() {
        return new BigDecimal(this._fractionalPart).divide(BD_2_POW_24);
    }

    private String getFractionalDigits() {
        if (this._fractionalPart == 0) {
            return "0";
        }
        return getFractionalPart().toString().substring(2);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(" [");
        String valueOf = String.valueOf(this._wholePart);
        sb.append(valueOf.charAt(0));
        sb.append('.');
        sb.append(valueOf.substring(1));
        sb.append(' ');
        sb.append(getFractionalDigits());
        sb.append("E");
        sb.append(getDecimalExponent());
        sb.append("]");
        return sb.toString();
    }
}
