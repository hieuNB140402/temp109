

package com.document.allreader.allofficefilereader.fc.hwpf.model.types;

import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.BorderCode;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.DateAndTime;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.DropCapSpecifier;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.LineSpacingDescriptor;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.ShadingDescriptor;
import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.Internal;


@ Internal
public abstract class PAPAbstractType
{

    protected int field_1_istd;
    protected boolean field_2_fSideBySide;
    protected boolean field_3_fKeep;
    protected boolean field_4_fKeepFollow;
    protected boolean field_5_fPageBreakBefore;
    protected byte field_6_brcl;
    public final static byte BRCL_SINGLE = 0;
    public final static byte BRCL_THICK = 1;
    public final static byte BRCL_DOUBLE = 2;
    public final static byte BRCL_SHADOW = 3;
    protected byte field_7_brcp;
    public final static byte BRCP_NONE = 0;
    public final static byte BRCP_BORDER_ABOVE = 1;
    public final static byte BRCP_BORDER_BELOW = 2;
    public final static byte BRCP_BOX_AROUND = 15;
    public final static byte BRCP_BAR_TO_LEFT_OF_PARAGRAPH = 16;
    protected byte field_8_ilvl;
    protected int field_9_ilfo;
    protected boolean field_10_fNoLnn;
    protected LineSpacingDescriptor field_11_lspd;
    protected int field_12_dyaBefore;
    protected int field_13_dyaAfter;
    protected boolean field_14_fInTable;
    protected boolean field_15_finTableW97;
    protected boolean field_16_fTtp;
    protected int field_17_dxaAbs;
    protected int field_18_dyaAbs;
    protected int field_19_dxaWidth;
    protected boolean field_20_fBrLnAbove;
    protected boolean field_21_fBrLnBelow;
    protected byte field_22_pcVert;
    protected byte field_23_pcHorz;
    protected byte field_24_wr;
    protected boolean field_25_fNoAutoHyph;
    protected int field_26_dyaHeight;
    protected boolean field_27_fMinHeight;
    public final static boolean FMINHEIGHT_EXACT = false;
    public final static boolean FMINHEIGHT_AT_LEAST = true;
    protected DropCapSpecifier field_28_dcs;
    protected int field_29_dyaFromText;
    protected int field_30_dxaFromText;
    protected boolean field_31_fLocked;
    protected boolean field_32_fWidowControl;
    protected boolean field_33_fKinsoku;
    protected boolean field_34_fWordWrap;
    protected boolean field_35_fOverflowPunct;
    protected boolean field_36_fTopLinePunct;
    protected boolean field_37_fAutoSpaceDE;
    protected boolean field_38_fAutoSpaceDN;
    protected int field_39_wAlignFont;
    public final static byte WALIGNFONT_HANGING = 0;
    public final static byte WALIGNFONT_CENTERED = 1;
    public final static byte WALIGNFONT_ROMAN = 2;
    public final static byte WALIGNFONT_VARIABLE = 3;
    public final static byte WALIGNFONT_AUTO = 4;
    protected short field_40_fontAlign;
    private static BitField fVertical = new BitField(0x0001);
    private static BitField fBackward = new BitField(0x0002);
    private static BitField fRotateFont = new BitField(0x0004);
    protected byte field_41_lvl;
    protected boolean field_42_fBiDi;
    protected boolean field_43_fNumRMIns;
    protected boolean field_44_fCrLf;
    protected boolean field_45_fUsePgsuSettings;
    protected boolean field_46_fAdjustRight;
    protected int field_47_itap;
    protected boolean field_48_fInnerTableCell;
    protected boolean field_49_fOpenTch;
    protected boolean field_50_fTtpEmbedded;
    protected short field_51_dxcRight;
    protected short field_52_dxcLeft;
    protected short field_53_dxcLeft1;
    protected boolean field_54_fDyaBeforeAuto;
    protected boolean field_55_fDyaAfterAuto;
    protected int field_56_dxaRight;
    protected int field_57_dxaLeft;
    protected int field_58_dxaLeft1;
    protected byte field_59_jc;
    protected boolean field_60_fNoAllowOverlap;
    protected BorderCode field_61_brcTop;
    protected BorderCode field_62_brcLeft;
    protected BorderCode field_63_brcBottom;
    protected BorderCode field_64_brcRight;
    protected BorderCode field_65_brcBetween;
    protected BorderCode field_66_brcBar;
    protected ShadingDescriptor field_67_shd;
    protected byte[] field_68_anld;
    protected byte[] field_69_phe;
    protected boolean field_70_fPropRMark;
    protected int field_71_ibstPropRMark;
    protected DateAndTime field_72_dttmPropRMark;
    protected int field_73_itbdMac;
    protected int[] field_74_rgdxaTab;
    protected byte[] field_75_rgtbd;
    protected byte[] field_76_numrm;
    protected byte[] field_77_ptap;

    protected PAPAbstractType()
    {
        this.field_11_lspd = new LineSpacingDescriptor();
        this.field_11_lspd = new LineSpacingDescriptor();
        this.field_28_dcs = new DropCapSpecifier();
        this.field_32_fWidowControl = true;
        this.field_41_lvl = 9;
        this.field_61_brcTop = new BorderCode();
        this.field_62_brcLeft = new BorderCode();
        this.field_63_brcBottom = new BorderCode();
        this.field_64_brcRight = new BorderCode();
        this.field_65_brcBetween = new BorderCode();
        this.field_66_brcBar = new BorderCode();
        this.field_67_shd = new ShadingDescriptor();
        this.field_68_anld = new byte[0];
        this.field_69_phe = new byte[0];
        this.field_72_dttmPropRMark = new DateAndTime();
        this.field_74_rgdxaTab = new int[0];
        this.field_75_rgtbd = new byte[0];
        this.field_76_numrm = new byte[0];
        this.field_77_ptap = new byte[0];
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[PAP]\n");
        builder.append("    .istd                 = ");
        builder.append(" (").append(getIstd()).append(" )\n");
        builder.append("    .fSideBySide          = ");
        builder.append(" (").append(getFSideBySide()).append(" )\n");
        builder.append("    .fKeep                = ");
        builder.append(" (").append(getFKeep()).append(" )\n");
        builder.append("    .fKeepFollow          = ");
        builder.append(" (").append(getFKeepFollow()).append(" )\n");
        builder.append("    .fPageBreakBefore     = ");
        builder.append(" (").append(getFPageBreakBefore()).append(" )\n");
        builder.append("    .brcl                 = ");
        builder.append(" (").append(getBrcl()).append(" )\n");
        builder.append("    .brcp                 = ");
        builder.append(" (").append(getBrcp()).append(" )\n");
        builder.append("    .ilvl                 = ");
        builder.append(" (").append(getIlvl()).append(" )\n");
        builder.append("    .ilfo                 = ");
        builder.append(" (").append(getIlfo()).append(" )\n");
        builder.append("    .fNoLnn               = ");
        builder.append(" (").append(getFNoLnn()).append(" )\n");
        builder.append("    .lspd                 = ");
        builder.append(" (").append(getLspd()).append(" )\n");
        builder.append("    .dyaBefore            = ");
        builder.append(" (").append(getDyaBefore()).append(" )\n");
        builder.append("    .dyaAfter             = ");
        builder.append(" (").append(getDyaAfter()).append(" )\n");
        builder.append("    .fInTable             = ");
        builder.append(" (").append(getFInTable()).append(" )\n");
        builder.append("    .finTableW97          = ");
        builder.append(" (").append(getFinTableW97()).append(" )\n");
        builder.append("    .fTtp                 = ");
        builder.append(" (").append(getFTtp()).append(" )\n");
        builder.append("    .dxaAbs               = ");
        builder.append(" (").append(getDxaAbs()).append(" )\n");
        builder.append("    .dyaAbs               = ");
        builder.append(" (").append(getDyaAbs()).append(" )\n");
        builder.append("    .dxaWidth             = ");
        builder.append(" (").append(getDxaWidth()).append(" )\n");
        builder.append("    .fBrLnAbove           = ");
        builder.append(" (").append(getFBrLnAbove()).append(" )\n");
        builder.append("    .fBrLnBelow           = ");
        builder.append(" (").append(getFBrLnBelow()).append(" )\n");
        builder.append("    .pcVert               = ");
        builder.append(" (").append(getPcVert()).append(" )\n");
        builder.append("    .pcHorz               = ");
        builder.append(" (").append(getPcHorz()).append(" )\n");
        builder.append("    .wr                   = ");
        builder.append(" (").append(getWr()).append(" )\n");
        builder.append("    .fNoAutoHyph          = ");
        builder.append(" (").append(getFNoAutoHyph()).append(" )\n");
        builder.append("    .dyaHeight            = ");
        builder.append(" (").append(getDyaHeight()).append(" )\n");
        builder.append("    .fMinHeight           = ");
        builder.append(" (").append(getFMinHeight()).append(" )\n");
        builder.append("    .dcs                  = ");
        builder.append(" (").append(getDcs()).append(" )\n");
        builder.append("    .dyaFromText          = ");
        builder.append(" (").append(getDyaFromText()).append(" )\n");
        builder.append("    .dxaFromText          = ");
        builder.append(" (").append(getDxaFromText()).append(" )\n");
        builder.append("    .fLocked              = ");
        builder.append(" (").append(getFLocked()).append(" )\n");
        builder.append("    .fWidowControl        = ");
        builder.append(" (").append(getFWidowControl()).append(" )\n");
        builder.append("    .fKinsoku             = ");
        builder.append(" (").append(getFKinsoku()).append(" )\n");
        builder.append("    .fWordWrap            = ");
        builder.append(" (").append(getFWordWrap()).append(" )\n");
        builder.append("    .fOverflowPunct       = ");
        builder.append(" (").append(getFOverflowPunct()).append(" )\n");
        builder.append("    .fTopLinePunct        = ");
        builder.append(" (").append(getFTopLinePunct()).append(" )\n");
        builder.append("    .fAutoSpaceDE         = ");
        builder.append(" (").append(getFAutoSpaceDE()).append(" )\n");
        builder.append("    .fAutoSpaceDN         = ");
        builder.append(" (").append(getFAutoSpaceDN()).append(" )\n");
        builder.append("    .wAlignFont           = ");
        builder.append(" (").append(getWAlignFont()).append(" )\n");
        builder.append("    .fontAlign            = ");
        builder.append(" (").append(getFontAlign()).append(" )\n");
        builder.append("         .fVertical                = ").append(isFVertical()).append('\n');
        builder.append("         .fBackward                = ").append(isFBackward()).append('\n');
        builder.append("         .fRotateFont              = ").append(isFRotateFont())
            .append('\n');
        builder.append("    .lvl                  = ");
        builder.append(" (").append(getLvl()).append(" )\n");
        builder.append("    .fBiDi                = ");
        builder.append(" (").append(getFBiDi()).append(" )\n");
        builder.append("    .fNumRMIns            = ");
        builder.append(" (").append(getFNumRMIns()).append(" )\n");
        builder.append("    .fCrLf                = ");
        builder.append(" (").append(getFCrLf()).append(" )\n");
        builder.append("    .fUsePgsuSettings     = ");
        builder.append(" (").append(getFUsePgsuSettings()).append(" )\n");
        builder.append("    .fAdjustRight         = ");
        builder.append(" (").append(getFAdjustRight()).append(" )\n");
        builder.append("    .itap                 = ");
        builder.append(" (").append(getItap()).append(" )\n");
        builder.append("    .fInnerTableCell      = ");
        builder.append(" (").append(getFInnerTableCell()).append(" )\n");
        builder.append("    .fOpenTch             = ");
        builder.append(" (").append(getFOpenTch()).append(" )\n");
        builder.append("    .fTtpEmbedded         = ");
        builder.append(" (").append(getFTtpEmbedded()).append(" )\n");
        builder.append("    .dxcRight             = ");
        builder.append(" (").append(getDxcRight()).append(" )\n");
        builder.append("    .dxcLeft              = ");
        builder.append(" (").append(getDxcLeft()).append(" )\n");
        builder.append("    .dxcLeft1             = ");
        builder.append(" (").append(getDxcLeft1()).append(" )\n");
        builder.append("    .fDyaBeforeAuto       = ");
        builder.append(" (").append(getFDyaBeforeAuto()).append(" )\n");
        builder.append("    .fDyaAfterAuto        = ");
        builder.append(" (").append(getFDyaAfterAuto()).append(" )\n");
        builder.append("    .dxaRight             = ");
        builder.append(" (").append(getDxaRight()).append(" )\n");
        builder.append("    .dxaLeft              = ");
        builder.append(" (").append(getDxaLeft()).append(" )\n");
        builder.append("    .dxaLeft1             = ");
        builder.append(" (").append(getDxaLeft1()).append(" )\n");
        builder.append("    .jc                   = ");
        builder.append(" (").append(getJc()).append(" )\n");
        builder.append("    .fNoAllowOverlap      = ");
        builder.append(" (").append(getFNoAllowOverlap()).append(" )\n");
        builder.append("    .brcTop               = ");
        builder.append(" (").append(getBrcTop()).append(" )\n");
        builder.append("    .brcLeft              = ");
        builder.append(" (").append(getBrcLeft()).append(" )\n");
        builder.append("    .brcBottom            = ");
        builder.append(" (").append(getBrcBottom()).append(" )\n");
        builder.append("    .brcRight             = ");
        builder.append(" (").append(getBrcRight()).append(" )\n");
        builder.append("    .brcBetween           = ");
        builder.append(" (").append(getBrcBetween()).append(" )\n");
        builder.append("    .brcBar               = ");
        builder.append(" (").append(getBrcBar()).append(" )\n");
        builder.append("    .shd                  = ");
        builder.append(" (").append(getShd()).append(" )\n");
        builder.append("    .anld                 = ");
        builder.append(" (").append(getAnld()).append(" )\n");
        builder.append("    .phe                  = ");
        builder.append(" (").append(getPhe()).append(" )\n");
        builder.append("    .fPropRMark           = ");
        builder.append(" (").append(getFPropRMark()).append(" )\n");
        builder.append("    .ibstPropRMark        = ");
        builder.append(" (").append(getIbstPropRMark()).append(" )\n");
        builder.append("    .dttmPropRMark        = ");
        builder.append(" (").append(getDttmPropRMark()).append(" )\n");
        builder.append("    .itbdMac              = ");
        builder.append(" (").append(getItbdMac()).append(" )\n");
        builder.append("    .rgdxaTab             = ");
        builder.append(" (").append(getRgdxaTab()).append(" )\n");
        builder.append("    .rgtbd                = ");
        builder.append(" (").append(getRgtbd()).append(" )\n");
        builder.append("    .numrm                = ");
        builder.append(" (").append(getNumrm()).append(" )\n");
        builder.append("    .ptap                 = ");
        builder.append(" (").append(getPtap()).append(" )\n");

        builder.append("[/PAP]\n");
        return builder.toString();
    }


    public int getIstd()
    {
        return field_1_istd;
    }


    public void setIstd(int field_1_istd)
    {
        this.field_1_istd = field_1_istd;
    }


    public boolean getFSideBySide()
    {
        return field_2_fSideBySide;
    }


    public void setFSideBySide(boolean field_2_fSideBySide)
    {
        this.field_2_fSideBySide = field_2_fSideBySide;
    }

    public boolean getFKeep()
    {
        return field_3_fKeep;
    }


    public void setFKeep(boolean field_3_fKeep)
    {
        this.field_3_fKeep = field_3_fKeep;
    }


    public boolean getFKeepFollow()
    {
        return field_4_fKeepFollow;
    }


    public void setFKeepFollow(boolean field_4_fKeepFollow)
    {
        this.field_4_fKeepFollow = field_4_fKeepFollow;
    }


    public boolean getFPageBreakBefore()
    {
        return field_5_fPageBreakBefore;
    }


    public void setFPageBreakBefore(boolean field_5_fPageBreakBefore)
    {
        this.field_5_fPageBreakBefore = field_5_fPageBreakBefore;
    }


    public byte getBrcl()
    {
        return field_6_brcl;
    }


    public void setBrcl(byte field_6_brcl)
    {
        this.field_6_brcl = field_6_brcl;
    }


    public byte getBrcp()
    {
        return field_7_brcp;
    }


    public void setBrcp(byte field_7_brcp)
    {
        this.field_7_brcp = field_7_brcp;
    }


    public byte getIlvl()
    {
        return field_8_ilvl;
    }

    public void setIlvl(byte field_8_ilvl)
    {
        this.field_8_ilvl = field_8_ilvl;
    }



    public int getIlfo()
    {
        return field_9_ilfo;
    }


    public void setIlfo(int field_9_ilfo)
    {
        this.field_9_ilfo = field_9_ilfo;
    }


    public boolean getFNoLnn()
    {
        return field_10_fNoLnn;
    }


    public void setFNoLnn(boolean field_10_fNoLnn)
    {
        this.field_10_fNoLnn = field_10_fNoLnn;
    }

    public LineSpacingDescriptor getLspd()
    {
        return field_11_lspd;
    }

    public void setLspd(LineSpacingDescriptor field_11_lspd)
    {
        this.field_11_lspd = field_11_lspd;
    }

    public int getDyaBefore()
    {
        return field_12_dyaBefore;
    }


    public void setDyaBefore(int field_12_dyaBefore)
    {
        this.field_12_dyaBefore = field_12_dyaBefore;
    }


    public int getDyaAfter()
    {
        return field_13_dyaAfter;
    }


    public void setDyaAfter(int field_13_dyaAfter)
    {
        this.field_13_dyaAfter = field_13_dyaAfter;
    }

    public boolean getFInTable()
    {
        return field_14_fInTable;
    }


    public void setFInTable(boolean field_14_fInTable)
    {
        this.field_14_fInTable = field_14_fInTable;
    }

    public boolean getFinTableW97()
    {
        return field_15_finTableW97;
    }

    public void setFinTableW97(boolean field_15_finTableW97)
    {
        this.field_15_finTableW97 = field_15_finTableW97;
    }

    public boolean getFTtp()
    {
        return field_16_fTtp;
    }


    public void setFTtp(boolean field_16_fTtp)
    {
        this.field_16_fTtp = field_16_fTtp;
    }


    public int getDxaAbs()
    {
        return field_17_dxaAbs;
    }


    public void setDxaAbs(int field_17_dxaAbs)
    {
        this.field_17_dxaAbs = field_17_dxaAbs;
    }


    public int getDyaAbs()
    {
        return field_18_dyaAbs;
    }

    public void setDyaAbs(int field_18_dyaAbs)
    {
        this.field_18_dyaAbs = field_18_dyaAbs;
    }


    public int getDxaWidth()
    {
        return field_19_dxaWidth;
    }

    public void setDxaWidth(int field_19_dxaWidth)
    {
        this.field_19_dxaWidth = field_19_dxaWidth;
    }


    public boolean getFBrLnAbove()
    {
        return field_20_fBrLnAbove;
    }


    public void setFBrLnAbove(boolean field_20_fBrLnAbove)
    {
        this.field_20_fBrLnAbove = field_20_fBrLnAbove;
    }


    public boolean getFBrLnBelow()
    {
        return field_21_fBrLnBelow;
    }


    public void setFBrLnBelow(boolean field_21_fBrLnBelow)
    {
        this.field_21_fBrLnBelow = field_21_fBrLnBelow;
    }


    public byte getPcVert()
    {
        return field_22_pcVert;
    }


    public void setPcVert(byte field_22_pcVert)
    {
        this.field_22_pcVert = field_22_pcVert;
    }


    public byte getPcHorz()
    {
        return field_23_pcHorz;
    }


    public void setPcHorz(byte field_23_pcHorz)
    {
        this.field_23_pcHorz = field_23_pcHorz;
    }

    public byte getWr()
    {
        return field_24_wr;
    }


    public void setWr(byte field_24_wr)
    {
        this.field_24_wr = field_24_wr;
    }

    public boolean getFNoAutoHyph()
    {
        return field_25_fNoAutoHyph;
    }

    public void setFNoAutoHyph(boolean field_25_fNoAutoHyph)
    {
        this.field_25_fNoAutoHyph = field_25_fNoAutoHyph;
    }

    public int getDyaHeight()
    {
        return field_26_dyaHeight;
    }


    public void setDyaHeight(int field_26_dyaHeight)
    {
        this.field_26_dyaHeight = field_26_dyaHeight;
    }

    public boolean getFMinHeight()
    {
        return field_27_fMinHeight;
    }


    public void setFMinHeight(boolean field_27_fMinHeight)
    {
        this.field_27_fMinHeight = field_27_fMinHeight;
    }


    public DropCapSpecifier getDcs()
    {
        return field_28_dcs;
    }


    public void setDcs(DropCapSpecifier field_28_dcs)
    {
        this.field_28_dcs = field_28_dcs;
    }

    public int getDyaFromText()
    {
        return field_29_dyaFromText;
    }


    public void setDyaFromText(int field_29_dyaFromText)
    {
        this.field_29_dyaFromText = field_29_dyaFromText;
    }


    public int getDxaFromText()
    {
        return field_30_dxaFromText;
    }


    public void setDxaFromText(int field_30_dxaFromText)
    {
        this.field_30_dxaFromText = field_30_dxaFromText;
    }

    public boolean getFLocked()
    {
        return field_31_fLocked;
    }


    public void setFLocked(boolean field_31_fLocked)
    {
        this.field_31_fLocked = field_31_fLocked;
    }


    public boolean getFWidowControl()
    {
        return field_32_fWidowControl;
    }


    public void setFWidowControl(boolean field_32_fWidowControl)
    {
        this.field_32_fWidowControl = field_32_fWidowControl;
    }

    public boolean getFKinsoku()
    {
        return field_33_fKinsoku;
    }


    public void setFKinsoku(boolean field_33_fKinsoku)
    {
        this.field_33_fKinsoku = field_33_fKinsoku;
    }

    public boolean getFWordWrap()
    {
        return field_34_fWordWrap;
    }

    public void setFWordWrap(boolean field_34_fWordWrap)
    {
        this.field_34_fWordWrap = field_34_fWordWrap;
    }

    public boolean getFOverflowPunct()
    {
        return field_35_fOverflowPunct;
    }


    public void setFOverflowPunct(boolean field_35_fOverflowPunct)
    {
        this.field_35_fOverflowPunct = field_35_fOverflowPunct;
    }

    public boolean getFTopLinePunct()
    {
        return field_36_fTopLinePunct;
    }


    public void setFTopLinePunct(boolean field_36_fTopLinePunct)
    {
        this.field_36_fTopLinePunct = field_36_fTopLinePunct;
    }


    public boolean getFAutoSpaceDE()
    {
        return field_37_fAutoSpaceDE;
    }


    public void setFAutoSpaceDE(boolean field_37_fAutoSpaceDE)
    {
        this.field_37_fAutoSpaceDE = field_37_fAutoSpaceDE;
    }


    public boolean getFAutoSpaceDN()
    {
        return field_38_fAutoSpaceDN;
    }

    public void setFAutoSpaceDN(boolean field_38_fAutoSpaceDN)
    {
        this.field_38_fAutoSpaceDN = field_38_fAutoSpaceDN;
    }


    public int getWAlignFont()
    {
        return field_39_wAlignFont;
    }


    public void setWAlignFont(int field_39_wAlignFont)
    {
        this.field_39_wAlignFont = field_39_wAlignFont;
    }


    public short getFontAlign()
    {
        return field_40_fontAlign;
    }


    public void setFontAlign(short field_40_fontAlign)
    {
        this.field_40_fontAlign = field_40_fontAlign;
    }


    public byte getLvl()
    {
        return field_41_lvl;
    }

    public void setLvl(byte field_41_lvl)
    {
        this.field_41_lvl = field_41_lvl;
    }


    public boolean getFBiDi()
    {
        return field_42_fBiDi;
    }


    public void setFBiDi(boolean field_42_fBiDi)
    {
        this.field_42_fBiDi = field_42_fBiDi;
    }


    public boolean getFNumRMIns()
    {
        return field_43_fNumRMIns;
    }


    public void setFNumRMIns(boolean field_43_fNumRMIns)
    {
        this.field_43_fNumRMIns = field_43_fNumRMIns;
    }


    public boolean getFCrLf()
    {
        return field_44_fCrLf;
    }


    public void setFCrLf(boolean field_44_fCrLf)
    {
        this.field_44_fCrLf = field_44_fCrLf;
    }


    public boolean getFUsePgsuSettings()
    {
        return field_45_fUsePgsuSettings;
    }


    public void setFUsePgsuSettings(boolean field_45_fUsePgsuSettings)
    {
        this.field_45_fUsePgsuSettings = field_45_fUsePgsuSettings;
    }


    public boolean getFAdjustRight()
    {
        return field_46_fAdjustRight;
    }

    public void setFAdjustRight(boolean field_46_fAdjustRight)
    {
        this.field_46_fAdjustRight = field_46_fAdjustRight;
    }

    public int getItap()
    {
        return field_47_itap;
    }


    public void setItap(int field_47_itap)
    {
        this.field_47_itap = field_47_itap;
    }

    public boolean getFInnerTableCell()
    {
        return field_48_fInnerTableCell;
    }


    public void setFInnerTableCell(boolean field_48_fInnerTableCell)
    {
        this.field_48_fInnerTableCell = field_48_fInnerTableCell;
    }


    public boolean getFOpenTch()
    {
        return field_49_fOpenTch;
    }


    public void setFOpenTch(boolean field_49_fOpenTch)
    {
        this.field_49_fOpenTch = field_49_fOpenTch;
    }


    public boolean getFTtpEmbedded()
    {
        return field_50_fTtpEmbedded;
    }


    public void setFTtpEmbedded(boolean field_50_fTtpEmbedded)
    {
        this.field_50_fTtpEmbedded = field_50_fTtpEmbedded;
    }


    public short getDxcRight()
    {
        return field_51_dxcRight;
    }


    public void setDxcRight(short field_51_dxcRight)
    {
        this.field_51_dxcRight = field_51_dxcRight;
    }


    public short getDxcLeft()
    {
        return field_52_dxcLeft;
    }


    public void setDxcLeft(short field_52_dxcLeft)
    {
        this.field_52_dxcLeft = field_52_dxcLeft;
    }


    public short getDxcLeft1()
    {
        return field_53_dxcLeft1;
    }


    public void setDxcLeft1(short field_53_dxcLeft1)
    {
        this.field_53_dxcLeft1 = field_53_dxcLeft1;
    }


    public boolean getFDyaBeforeAuto()
    {
        return field_54_fDyaBeforeAuto;
    }


    public void setFDyaBeforeAuto(boolean field_54_fDyaBeforeAuto)
    {
        this.field_54_fDyaBeforeAuto = field_54_fDyaBeforeAuto;
    }


    public boolean getFDyaAfterAuto()
    {
        return field_55_fDyaAfterAuto;
    }


    public void setFDyaAfterAuto(boolean field_55_fDyaAfterAuto)
    {
        this.field_55_fDyaAfterAuto = field_55_fDyaAfterAuto;
    }


    public int getDxaRight()
    {
        return field_56_dxaRight;
    }

    public void setDxaRight(int field_56_dxaRight)
    {
        this.field_56_dxaRight = field_56_dxaRight;
    }


    public int getDxaLeft()
    {
        return field_57_dxaLeft;
    }


    public void setDxaLeft(int field_57_dxaLeft)
    {
        this.field_57_dxaLeft = field_57_dxaLeft;
    }


    public int getDxaLeft1()
    {
        return field_58_dxaLeft1;
    }


    public void setDxaLeft1(int field_58_dxaLeft1)
    {
        this.field_58_dxaLeft1 = field_58_dxaLeft1;
    }


    public byte getJc()
    {
        return field_59_jc;
    }


    public void setJc(byte field_59_jc)
    {
        this.field_59_jc = field_59_jc;
    }


    public boolean getFNoAllowOverlap()
    {
        return field_60_fNoAllowOverlap;
    }


    public void setFNoAllowOverlap(boolean field_60_fNoAllowOverlap)
    {
        this.field_60_fNoAllowOverlap = field_60_fNoAllowOverlap;
    }


    public BorderCode getBrcTop()
    {
        return field_61_brcTop;
    }


    public void setBrcTop(BorderCode field_61_brcTop)
    {
        this.field_61_brcTop = field_61_brcTop;
    }


    public BorderCode getBrcLeft()
    {
        return field_62_brcLeft;
    }


    public void setBrcLeft(BorderCode field_62_brcLeft)
    {
        this.field_62_brcLeft = field_62_brcLeft;
    }


    public BorderCode getBrcBottom()
    {
        return field_63_brcBottom;
    }

    public void setBrcBottom(BorderCode field_63_brcBottom)
    {
        this.field_63_brcBottom = field_63_brcBottom;
    }


    public BorderCode getBrcRight()
    {
        return field_64_brcRight;
    }


    public void setBrcRight(BorderCode field_64_brcRight)
    {
        this.field_64_brcRight = field_64_brcRight;
    }


    public BorderCode getBrcBetween()
    {
        return field_65_brcBetween;
    }


    public void setBrcBetween(BorderCode field_65_brcBetween)
    {
        this.field_65_brcBetween = field_65_brcBetween;
    }

    public BorderCode getBrcBar()
    {
        return field_66_brcBar;
    }


    public void setBrcBar(BorderCode field_66_brcBar)
    {
        this.field_66_brcBar = field_66_brcBar;
    }

    public ShadingDescriptor getShd()
    {
        return field_67_shd;
    }


    public void setShd(ShadingDescriptor field_67_shd)
    {
        this.field_67_shd = field_67_shd;
    }


    public byte[] getAnld()
    {
        return field_68_anld;
    }


    public void setAnld(byte[] field_68_anld)
    {
        this.field_68_anld = field_68_anld;
    }


    public byte[] getPhe()
    {
        return field_69_phe;
    }


    public void setPhe(byte[] field_69_phe)
    {
        this.field_69_phe = field_69_phe;
    }


    public boolean getFPropRMark()
    {
        return field_70_fPropRMark;
    }


    public void setFPropRMark(boolean field_70_fPropRMark)
    {
        this.field_70_fPropRMark = field_70_fPropRMark;
    }


    public int getIbstPropRMark()
    {
        return field_71_ibstPropRMark;
    }


    public void setIbstPropRMark(int field_71_ibstPropRMark)
    {
        this.field_71_ibstPropRMark = field_71_ibstPropRMark;
    }


    public DateAndTime getDttmPropRMark()
    {
        return field_72_dttmPropRMark;
    }


    public void setDttmPropRMark(DateAndTime field_72_dttmPropRMark)
    {
        this.field_72_dttmPropRMark = field_72_dttmPropRMark;
    }


    public int getItbdMac()
    {
        return field_73_itbdMac;
    }


    public void setItbdMac(int field_73_itbdMac)
    {
        this.field_73_itbdMac = field_73_itbdMac;
    }


    public int[] getRgdxaTab()
    {
        return field_74_rgdxaTab;
    }


    public void setRgdxaTab(int[] field_74_rgdxaTab)
    {
        this.field_74_rgdxaTab = field_74_rgdxaTab;
    }

    public byte[] getRgtbd()
    {
        return field_75_rgtbd;
    }


    public void setRgtbd(byte[] field_75_rgtbd)
    {
        this.field_75_rgtbd = field_75_rgtbd;
    }


    public byte[] getNumrm()
    {
        return field_76_numrm;
    }


    public void setNumrm(byte[] field_76_numrm)
    {
        this.field_76_numrm = field_76_numrm;
    }


    public byte[] getPtap()
    {
        return field_77_ptap;
    }


    public void setPtap(byte[] field_77_ptap)
    {
        this.field_77_ptap = field_77_ptap;
    }


    public void setFVertical(boolean value)
    {
        field_40_fontAlign = (short)fVertical.setBoolean(field_40_fontAlign, value);

    }


    public boolean isFVertical()
    {
        return fVertical.isSet(field_40_fontAlign);

    }


    public void setFBackward(boolean value)
    {
        field_40_fontAlign = (short)fBackward.setBoolean(field_40_fontAlign, value);

    }

    public boolean isFBackward()
    {
        return fBackward.isSet(field_40_fontAlign);

    }


    public void setFRotateFont(boolean value)
    {
        field_40_fontAlign = (short)fRotateFont.setBoolean(field_40_fontAlign, value);

    }


    public boolean isFRotateFont()
    {
        return fRotateFont.isSet(field_40_fontAlign);

    }

}
