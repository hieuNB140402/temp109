

package com.document.allreader.allofficefilereader.fc.codec;

import java.util.Comparator;


public class StringEncoderComparator implements Comparator {


    private final StringEncoder stringEncoder;


    public StringEncoderComparator() {
        this.stringEncoder = null; // Trying to use this will cause things to break
    }


    public StringEncoderComparator(StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
    }


    public int compare(Object o1, Object o2) {

        int compareCode = 0;

        try {
            Comparable s1 = (Comparable) this.stringEncoder.encode(o1);
            Comparable s2 = (Comparable) this.stringEncoder.encode(o2);
            compareCode = s1.compareTo(s2);
        } catch (EncoderException ee) {
            compareCode = 0;
        }
        return compareCode;
    }

}
