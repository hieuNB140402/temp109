

package com.document.allreader.allofficefilereader.fc.hssf.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility for delaying the concatenation of multiple byte arrays.  Doing this up-front
 * causes significantly more copying, which for a large number of byte arrays can cost
 * a large amount of time.
 */
public class LazilyConcatenatedByteArray {
    private final List<byte[]> arrays = new ArrayList<byte[]>(1);

    /**
     * Clears the array (sets the concatenated length back to zero.
     */
    public void clear() {
        arrays.clear();
    }

    /**
     * Concatenates an array onto the end of our array.
     * This is a relatively fast operation.
     *
     * @param array the array to concatenate.
     * @throws IllegalArgumentException if {@code array} is {@code null}.
     */
    public void concatenate(byte[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array cannot be null");
        }
        arrays.add(array);
    }

    /**
     * Gets the concatenated contents as a single byte array.
     *
     * This is a slower operation, but the concatenated array is stored off as a single
     * array again so that subsequent calls will not perform additional copying.
     *
     * @return the byte array.  Returns {@code null} if no data has been placed into it.
     */
    public byte[] toArray() {
        if (arrays.isEmpty()) {
            return null;
        } else if (arrays.size() > 1) {
            int totalLength = 0;
            for (byte[] array : arrays) {
                totalLength += array.length;
            }

            byte[] concatenated = new byte[totalLength];
            int destPos = 0;
            for (byte[] array : arrays) {
                System.arraycopy(array, 0, concatenated, destPos, array.length);
                destPos += array.length;
            }

            arrays.clear();
            arrays.add(concatenated);
        }

        return arrays.get(0);
    }
}

