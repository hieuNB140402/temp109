
package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

public interface Field {
    //field type
    public static final byte REF = 0x03;
    public static final byte SET = 0x06;
    public static final byte IF = 0x07;
    public static final byte INDEX = 0x08;
    public static final byte TOC = 0x0D;
    public static final byte INFO = 0x0E;
    public static final byte TITLE = 0x0F;
    public static final byte SUBJECT = 0x10;
    public static final byte COMMENTS = 0x13;
    public static final byte CREATEDATE = 0x15;
    public static final byte EDITTIME = 0x19;
    public static final byte NUMPAGES = 0x1A;
    public static final byte NUMWORDS = 0x1B;
    public static final byte NUMCHARS = 0x1C;
    public static final byte FILENAME = 0x1D;
    public static final byte TEMPLATE = 0x1E;
    public static final byte DATE = 0x1F;
    public static final byte TIME = 0x20;
    public static final byte PAGE = 0x21;
    public static final byte Equals = 0x22;
    public static final byte QUOTE = 0x23;
    public static final byte INCLUDE = 0x24;
    public static final byte PAGEREF = 0x25;
    public static final byte ASK = 0x26;
    public static final byte FILLIN = 0x27;
    public static final byte DATA = 0x28;
    public static final byte NEXT = 0x29;
    public static final byte DDE = 0x2D;
    public static final byte DDEAUTO = 0x2E;
    public static final byte GLOSSARY = 0x2F;
    public static final byte PRINT = 0x30;
    public static final byte GOTOBUTTON = 0x32;
    public static final byte MACROBUTTON = 0x33;
    public static final byte AUTONUMOUT = 0x34;
    public static final byte AUTONUMLGL = 0x35;
    public static final byte AUTONUM = 0x36;
    public static final byte IMPORT = 0x37;
    public static final byte LINK = 0x38;
    public static final byte SYMBOL = 0x39;
    public static final byte EMBED = 0x3A;
    public static final byte MERGEFIELD = 0x3B;
    public static final byte USERNAME = 0x3C;
    public static final byte USERINITIALS = 0x3D;
    public static final byte USERADDRESS = 0x3E;
    public static final byte BARCODE = 0x3F;
    public static final byte MERGESEQ = 0x4B;
    public static final byte COMPARE = 0x50;
    public static final byte CONTROL = 0x57;
    public static final byte HYPERLINK = 0x58;
    public static final byte GREETINGLINE = 0x5E;
    public static final byte SHAPE = 0x5F;

    Range firstSubrange(Range parent);


    int getFieldEndOffset();


    int getFieldStartOffset();

    CharacterRun getMarkEndCharacterRun(Range parent);


    int getMarkEndOffset();

    CharacterRun getMarkSeparatorCharacterRun(Range parent);


    int getMarkSeparatorOffset();

    CharacterRun getMarkStartCharacterRun(Range parent);

    int getMarkStartOffset();

    int getType();

    boolean hasSeparator();

    boolean isHasSep();

    boolean isLocked();

    boolean isNested();

    boolean isPrivateResult();

    boolean isResultDirty();

    boolean isResultEdited();

    boolean isZombieEmbed();

    Range secondSubrange(Range parent);
}
