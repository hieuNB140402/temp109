package com.document.allreader.allofficefilereader.fc.p014ss.format;

/* renamed from: com.allreader.office.allofficefilereader.fc.ss.format.CellFormatType */

public enum CellFormatType {
    GENERAL {
        @Override // fc4ss.format.CellFormatType
        boolean isSpecial(char c) {
            return false;
        }

        @Override // fc4ss.format.CellFormatType
        CellFormatter formatter(String str) {
            return new CellGeneralFormatter();
        }
    },
    NUMBER {
        @Override // fc4ss.format.CellFormatType
        boolean isSpecial(char c) {
            return false;
        }

        @Override // fc4ss.format.CellFormatType
        CellFormatter formatter(String str) {
            return new CellNumberFormatter(str);
        }
    },
    DATE {
        @Override // fc4ss.format.CellFormatType
        boolean isSpecial(char c) {
            return c == '\'' || (c <= '\u007f' && Character.isLetter(c));
        }

        @Override // fc4ss.format.CellFormatType
        CellFormatter formatter(String str) {
            return new CellDateFormatter(str);
        }
    },
    ELAPSED {
        @Override // fc4ss.format.CellFormatType
        boolean isSpecial(char c) {
            return false;
        }

        @Override // fc4ss.format.CellFormatType
        CellFormatter formatter(String str) {
            return new CellElapsedFormatter(str);
        }
    },
    TEXT {
        @Override // fc4ss.format.CellFormatType
        boolean isSpecial(char c) {
            return false;
        }

        @Override // fc4ss.format.CellFormatType
        CellFormatter formatter(String str) {
            return new CellTextFormatter(str);
        }
    };

    abstract CellFormatter formatter(String str);

    abstract boolean isSpecial(char c);
}
