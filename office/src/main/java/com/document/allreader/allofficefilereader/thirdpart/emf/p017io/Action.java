package com.document.allreader.allofficefilereader.thirdpart.emf.p017io;

import java.io.IOException;

/* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.Action */

public abstract class Action {
    private int code;
    private String name;

    public abstract Action read(int i, TaggedInputStream taggedInputStream, int i2) throws IOException;

    protected Action(int i) {
        this.code = i;
        String name = getClass().getName();
        this.name = name;
        int lastIndexOf = name.lastIndexOf(".");
        this.name = lastIndexOf >= 0 ? this.name.substring(lastIndexOf + 1) : this.name;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "Action " + getName() + " (" + getCode() + ")";
    }

    /* renamed from: com.allreader.office.allofficefilereader.thirdpart.emf.io.Action$Unknown */

    public static class Unknown extends Action {
        private int[] data;

        public Unknown() {
            super(0);
        }

        public Unknown(int i) {
            super(i);
        }

        @Override // com.allreader.office.allofficefilereader.thirdpart.emf.p017io.Action
        public Action read(int i, TaggedInputStream taggedInputStream, int i2) throws IOException {
            Unknown unknown = new Unknown(i);
            unknown.data = taggedInputStream.readUnsignedByte(i2);
            return unknown;
        }

        @Override // com.allreader.office.allofficefilereader.thirdpart.emf.p017io.Action
        public String toString() {
            return  " UNKNOWN!, length " + this.data.length;
        }
    }
}
