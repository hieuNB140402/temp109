package com.document.allreader.allofficefilereader.fc.hssf.record.p011cf;

import com.document.allreader.allofficefilereader.fc.p014ss.util.HSSFCellRangeAddress;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.allreader.office.allofficefilereader.fc.hssf.record.cf.CellRangeUtil */

public final class CellRangeUtil {
    public static final int ENCLOSES = 4;
    public static final int INSIDE = 3;
    public static final int NO_INTERSECTION = 1;
    public static final int OVERLAP = 2;

    /* renamed from: lt */
    private static boolean m68lt(int i, int i2) {
        return i != -1 && (i2 == -1 || i < i2);
    }

    private CellRangeUtil() {
    }

    public static int intersect(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        int firstRow = hSSFCellRangeAddress2.getFirstRow();
        int lastRow = hSSFCellRangeAddress2.getLastRow();
        int firstColumn = hSSFCellRangeAddress2.getFirstColumn();
        int lastColumn = hSSFCellRangeAddress2.getLastColumn();
        if (m70gt(hSSFCellRangeAddress.getFirstRow(), lastRow) || m68lt(hSSFCellRangeAddress.getLastRow(), firstRow) || m70gt(hSSFCellRangeAddress.getFirstColumn(), lastColumn) || m68lt(hSSFCellRangeAddress.getLastColumn(), firstColumn)) {
            return 1;
        }
        if (contains(hSSFCellRangeAddress, hSSFCellRangeAddress2)) {
            return 3;
        }
        return contains(hSSFCellRangeAddress2, hSSFCellRangeAddress) ? 4 : 2;
    }

    public static HSSFCellRangeAddress[] mergeCellRanges(HSSFCellRangeAddress[] hSSFCellRangeAddressArr) {
        if (hSSFCellRangeAddressArr.length < 1) {
            return hSSFCellRangeAddressArr;
        }
        ArrayList arrayList = new ArrayList();
        for (HSSFCellRangeAddress hSSFCellRangeAddress : hSSFCellRangeAddressArr) {
            arrayList.add(hSSFCellRangeAddress);
        }
        return toArray(mergeCellRanges(arrayList));
    }

    private static List mergeCellRanges(List list) {
        while (list.size() > 1) {
            int i = 0;
            boolean z = false;
            while (i < list.size()) {
                HSSFCellRangeAddress hSSFCellRangeAddress = (HSSFCellRangeAddress) list.get(i);
                int i2 = i + 1;
                int i3 = i2;
                while (i3 < list.size()) {
                    HSSFCellRangeAddress[] mergeRanges = mergeRanges(hSSFCellRangeAddress, (HSSFCellRangeAddress) list.get(i3));
                    if (mergeRanges != null) {
                        list.set(i, mergeRanges[0]);
                        list.remove(i3);
                        i3--;
                        for (int i4 = 1; i4 < mergeRanges.length; i4++) {
                            i3++;
                            list.add(i3, mergeRanges[i4]);
                        }
                        z = true;
                    }
                    i3++;
                }
                i = i2;
            }
            if (!z) {
                break;
            }
        }
        return list;
    }

    private static HSSFCellRangeAddress[] mergeRanges(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        int intersect = intersect(hSSFCellRangeAddress, hSSFCellRangeAddress2);
        if (intersect != 1) {
            if (intersect == 2) {
                return resolveRangeOverlap(hSSFCellRangeAddress, hSSFCellRangeAddress2);
            }
            if (intersect == 3) {
                return new HSSFCellRangeAddress[]{hSSFCellRangeAddress};
            }
            if (intersect == 4) {
                return new HSSFCellRangeAddress[]{hSSFCellRangeAddress2};
            }
            throw new RuntimeException("unexpected intersection result (" + intersect + ")");
        } else if (hasExactSharedBorder(hSSFCellRangeAddress, hSSFCellRangeAddress2)) {
            return new HSSFCellRangeAddress[]{createEnclosingCellRange(hSSFCellRangeAddress, hSSFCellRangeAddress2)};
        } else {
            return null;
        }
    }

    static HSSFCellRangeAddress[] resolveRangeOverlap(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        if (hSSFCellRangeAddress.isFullColumnRange()) {
            if (hSSFCellRangeAddress.isFullRowRange()) {
                return null;
            }
            return sliceUp(hSSFCellRangeAddress, hSSFCellRangeAddress2);
        } else if (hSSFCellRangeAddress.isFullRowRange()) {
            if (hSSFCellRangeAddress2.isFullColumnRange()) {
                return null;
            }
            return sliceUp(hSSFCellRangeAddress, hSSFCellRangeAddress2);
        } else if (hSSFCellRangeAddress2.isFullColumnRange()) {
            return sliceUp(hSSFCellRangeAddress2, hSSFCellRangeAddress);
        } else {
            if (hSSFCellRangeAddress2.isFullRowRange()) {
                return sliceUp(hSSFCellRangeAddress2, hSSFCellRangeAddress);
            }
            return sliceUp(hSSFCellRangeAddress, hSSFCellRangeAddress2);
        }
    }

    private static HSSFCellRangeAddress[] sliceUp(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        List arrayList = new ArrayList();
        arrayList.add(hSSFCellRangeAddress2);
        if (!hSSFCellRangeAddress.isFullColumnRange()) {
            arrayList = cutHorizontally(hSSFCellRangeAddress.getLastRow() + 1, cutHorizontally(hSSFCellRangeAddress.getFirstRow(), arrayList));
        }
        if (!hSSFCellRangeAddress.isFullRowRange()) {
            arrayList = cutVertically(hSSFCellRangeAddress.getLastColumn() + 1, cutVertically(hSSFCellRangeAddress.getFirstColumn(), arrayList));
        }
        HSSFCellRangeAddress[] array = toArray(arrayList);
        arrayList.clear();
        arrayList.add(hSSFCellRangeAddress);
        for (HSSFCellRangeAddress hSSFCellRangeAddress3 : array) {
            if (intersect(hSSFCellRangeAddress, hSSFCellRangeAddress3) != 4) {
                arrayList.add(hSSFCellRangeAddress3);
            }
        }
        return toArray(arrayList);
    }

    private static List cutHorizontally(int i, List list) {
        ArrayList arrayList = new ArrayList();
        HSSFCellRangeAddress[] array = toArray(list);
        for (HSSFCellRangeAddress hSSFCellRangeAddress : array) {
            if (hSSFCellRangeAddress.getFirstRow() >= i || i >= hSSFCellRangeAddress.getLastRow()) {
                arrayList.add(hSSFCellRangeAddress);
            } else {
                arrayList.add(new HSSFCellRangeAddress(hSSFCellRangeAddress.getFirstRow(), i, hSSFCellRangeAddress.getFirstColumn(), hSSFCellRangeAddress.getLastColumn()));
                arrayList.add(new HSSFCellRangeAddress(i + 1, hSSFCellRangeAddress.getLastRow(), hSSFCellRangeAddress.getFirstColumn(), hSSFCellRangeAddress.getLastColumn()));
            }
        }
        return arrayList;
    }

    private static List cutVertically(int i, List list) {
        ArrayList arrayList = new ArrayList();
        HSSFCellRangeAddress[] array = toArray(list);
        for (HSSFCellRangeAddress hSSFCellRangeAddress : array) {
            if (hSSFCellRangeAddress.getFirstColumn() >= i || i >= hSSFCellRangeAddress.getLastColumn()) {
                arrayList.add(hSSFCellRangeAddress);
            } else {
                arrayList.add(new HSSFCellRangeAddress(hSSFCellRangeAddress.getFirstRow(), hSSFCellRangeAddress.getLastRow(), hSSFCellRangeAddress.getFirstColumn(), i));
                arrayList.add(new HSSFCellRangeAddress(hSSFCellRangeAddress.getFirstRow(), hSSFCellRangeAddress.getLastRow(), i + 1, hSSFCellRangeAddress.getLastColumn()));
            }
        }
        return arrayList;
    }

    private static HSSFCellRangeAddress[] toArray(List list) {
        HSSFCellRangeAddress[] hSSFCellRangeAddressArr = new HSSFCellRangeAddress[list.size()];
        list.toArray(hSSFCellRangeAddressArr);
        return hSSFCellRangeAddressArr;
    }

    public static boolean contains(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        return m69le(hSSFCellRangeAddress.getFirstRow(), hSSFCellRangeAddress2.getFirstRow()) && m71ge(hSSFCellRangeAddress.getLastRow(), hSSFCellRangeAddress2.getLastRow()) && m69le(hSSFCellRangeAddress.getFirstColumn(), hSSFCellRangeAddress2.getFirstColumn()) && m71ge(hSSFCellRangeAddress.getLastColumn(), hSSFCellRangeAddress2.getLastColumn());
    }

    public static boolean hasExactSharedBorder(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        int firstRow = hSSFCellRangeAddress2.getFirstRow();
        int lastRow = hSSFCellRangeAddress2.getLastRow();
        int firstColumn = hSSFCellRangeAddress2.getFirstColumn();
        int lastColumn = hSSFCellRangeAddress2.getLastColumn();
        if ((hSSFCellRangeAddress.getFirstRow() <= 0 || hSSFCellRangeAddress.getFirstRow() - 1 != lastRow) && (firstRow <= 0 || firstRow - 1 != hSSFCellRangeAddress.getLastRow())) {
            if (((hSSFCellRangeAddress.getFirstColumn() > 0 && hSSFCellRangeAddress.getFirstColumn() - 1 == lastColumn) || (firstColumn > 0 && hSSFCellRangeAddress.getLastColumn() == firstColumn - 1)) && hSSFCellRangeAddress.getFirstRow() == firstRow && hSSFCellRangeAddress.getLastRow() == lastRow) {
                return true;
            }
            return false;
        } else if (hSSFCellRangeAddress.getFirstColumn() == firstColumn && hSSFCellRangeAddress.getLastColumn() == lastColumn) {
            return true;
        } else {
            return false;
        }
    }

    public static HSSFCellRangeAddress createEnclosingCellRange(HSSFCellRangeAddress hSSFCellRangeAddress, HSSFCellRangeAddress hSSFCellRangeAddress2) {
        if (hSSFCellRangeAddress2 == null) {
            return hSSFCellRangeAddress.copy();
        }
        return new HSSFCellRangeAddress(m68lt(hSSFCellRangeAddress2.getFirstRow(), hSSFCellRangeAddress.getFirstRow()) ? hSSFCellRangeAddress2.getFirstRow() : hSSFCellRangeAddress.getFirstRow(), m70gt(hSSFCellRangeAddress2.getLastRow(), hSSFCellRangeAddress.getLastRow()) ? hSSFCellRangeAddress2.getLastRow() : hSSFCellRangeAddress.getLastRow(), m68lt(hSSFCellRangeAddress2.getFirstColumn(), hSSFCellRangeAddress.getFirstColumn()) ? hSSFCellRangeAddress2.getFirstColumn() : hSSFCellRangeAddress.getFirstColumn(), m70gt(hSSFCellRangeAddress2.getLastColumn(), hSSFCellRangeAddress.getLastColumn()) ? hSSFCellRangeAddress2.getLastColumn() : hSSFCellRangeAddress.getLastColumn());
    }

    /* renamed from: le */
    private static boolean m69le(int i, int i2) {
        return i == i2 || m68lt(i, i2);
    }

    /* renamed from: gt */
    private static boolean m70gt(int i, int i2) {
        return m68lt(i2, i);
    }

    /* renamed from: ge */
    private static boolean m71ge(int i, int i2) {
        return !m68lt(i, i2);
    }
}
