package com.document.allreader.allofficefilereader.fc.p014ss.util;

import java.math.BigInteger;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.MutableFPNumber */

final class MutableFPNumber {
    private static final BigInteger BI_MAX_BASE = new BigInteger("0E35FA9319FFFE000", 16);
    private static final BigInteger BI_MIN_BASE = new BigInteger("0B5E620F47FFFE666", 16);
    private static final int C_64 = 64;
    private static final int MIN_PRECISION = 72;
    private int _binaryExponent;
    private BigInteger _significand;

    public MutableFPNumber(BigInteger bigInteger, int i) {
        this._significand = bigInteger;
        this._binaryExponent = i;
    }

    public MutableFPNumber copy() {
        return new MutableFPNumber(this._significand, this._binaryExponent);
    }

    public void normalise64bit() {
        int bitLength = this._significand.bitLength();
        int i = bitLength - 64;
        if (i != 0) {
            if (i >= 0) {
                this._binaryExponent += i;
                if (i > 32) {
                    int i2 = (i - 1) & 16777184;
                    this._significand = this._significand.shiftRight(i2);
                    i -= i2;
                    bitLength -= i2;
                }
                if (i >= 1) {
                    BigInteger round = Rounder.round(this._significand, i);
                    this._significand = round;
                    if (round.bitLength() > bitLength) {
                        i++;
                        this._binaryExponent++;
                    }
                    this._significand = this._significand.shiftRight(i);
                    return;
                }
                throw new IllegalStateException();
            }
            throw new IllegalStateException("Not enough precision");
        }
    }

    public int get64BitNormalisedExponent() {
        return (this._binaryExponent + this._significand.bitLength()) - 64;
    }

    public boolean isBelowMaxRep() {
        return this._significand.compareTo(BI_MAX_BASE.shiftLeft(this._significand.bitLength() + -64)) < 0;
    }

    public boolean isAboveMinRep() {
        return this._significand.compareTo(BI_MIN_BASE.shiftLeft(this._significand.bitLength() + -64)) > 0;
    }

    public NormalisedDecimal createNormalisedDecimal(int i) {
        int intValue = this._significand.intValue();
        return new NormalisedDecimal(this._significand.shiftRight((64 - this._binaryExponent) - 1).longValue(), (intValue << (this._binaryExponent - 39)) & 16777088, i);
    }

    public void multiplyByPowerOfTen(int i) {
        TenPower instance = TenPower.getInstance(Math.abs(i));
        if (i < 0) {
            mulShift(instance._divisor, instance._divisorShift);
        } else {
            mulShift(instance._multiplicand, instance._multiplierShift);
        }
    }

    private void mulShift(BigInteger bigInteger, int i) {
        BigInteger multiply = this._significand.multiply(bigInteger);
        this._significand = multiply;
        this._binaryExponent += i;
        int bitLength = (multiply.bitLength() - 72) & -32;
        if (bitLength > 0) {
            this._significand = this._significand.shiftRight(bitLength);
            this._binaryExponent += bitLength;
        }
    }

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.MutableFPNumber$Rounder */

    private static final class Rounder {
        private static final BigInteger[] HALF_BITS;

        private Rounder() {
        }

        static {
            BigInteger[] bigIntegerArr = new BigInteger[33];
            long j = 1;
            for (int i = 1; i < 33; i++) {
                bigIntegerArr[i] = BigInteger.valueOf(j);
                j <<= 1;
            }
            HALF_BITS = bigIntegerArr;
        }

        public static BigInteger round(BigInteger bigInteger, int i) {
            return i < 1 ? bigInteger : bigInteger.add(HALF_BITS[i]);
        }
    }

    /* renamed from: com.allreader.office.allofficefilereader.fc.ss.util.MutableFPNumber$TenPower */

    private static final class TenPower {
        private static final BigInteger FIVE = new BigInteger("5");
        private static final TenPower[] _cache = new TenPower[350];
        public final BigInteger _divisor;
        public final int _divisorShift;
        public final BigInteger _multiplicand;
        public final int _multiplierShift;

        private TenPower(int i) {
            BigInteger pow = FIVE.pow(i);
            int bitLength = pow.bitLength();
            BigInteger divide = BigInteger.ONE.shiftLeft(bitLength + 80).divide(pow);
            int bitLength2 = divide.bitLength() - 80;
            this._divisor = divide.shiftRight(bitLength2);
            this._divisorShift = -((bitLength - bitLength2) + i + 80);
            int bitLength3 = pow.bitLength() - 68;
            if (bitLength3 > 0) {
                this._multiplierShift = i + bitLength3;
                this._multiplicand = pow.shiftRight(bitLength3);
                return;
            }
            this._multiplierShift = i;
            this._multiplicand = pow;
        }

        static TenPower getInstance(int i) {
            TenPower[] tenPowerArr = _cache;
            TenPower tenPower = tenPowerArr[i];
            if (tenPower != null) {
                return tenPower;
            }
            TenPower tenPower2 = new TenPower(i);
            tenPowerArr[i] = tenPower2;
            return tenPower2;
        }
    }

    public ExpandedDouble createExpandedDouble() {
        return new ExpandedDouble(this._significand, this._binaryExponent);
    }
}
