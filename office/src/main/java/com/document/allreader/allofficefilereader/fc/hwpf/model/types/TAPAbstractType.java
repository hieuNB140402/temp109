

package com.document.allreader.allofficefilereader.fc.hwpf.model.types;

import com.document.allreader.allofficefilereader.fc.hwpf.model.HDFType;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.BorderCode;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.ShadingDescriptor;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.TableAutoformatLookSpecifier;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.TableCellDescriptor;
import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.Internal;


@Internal
public abstract class TAPAbstractType implements HDFType
{

    protected short field_1_istd;
    protected short field_2_jc;
    protected int field_3_dxaGapHalf;
    protected int field_4_dyaRowHeight;
    protected boolean field_5_fCantSplit;
    protected boolean field_6_fCantSplit90;
    protected boolean field_7_fTableHeader;
    protected TableAutoformatLookSpecifier field_8_tlp;
    protected short field_9_wWidth;
    protected short field_10_wWidthIndent;
    protected short field_11_wWidthBefore;
    protected short field_12_wWidthAfter;
    protected int field_13_widthAndFitsFlags;
    private static BitField fAutofit = new BitField( 0x00000001 );
    private static BitField fKeepFollow = new BitField( 0x00000002 );
    private static BitField ftsWidth = new BitField( 0x0000001c );
    private static BitField ftsWidthIndent = new BitField( 0x000000e0 );
    private static BitField ftsWidthBefore = new BitField( 0x00000700 );
    private static BitField ftsWidthAfter = new BitField( 0x00003800 );
    private static BitField fNeverBeenAutofit = new BitField( 0x00004000 );
    private static BitField fInvalAutofit = new BitField( 0x00008000 );
    private static BitField widthAndFitsFlags_empty1 = new BitField( 0x00070000 );
    private static BitField fVert = new BitField( 0x00080000 );
    private static BitField pcVert = new BitField( 0x00300000 );
    private static BitField pcHorz = new BitField( 0x00c00000 );
    private static BitField widthAndFitsFlags_empty2 = new BitField( 0xff000000 );
    protected int field_14_dxaAbs;
    protected int field_15_dyaAbs;
    protected int field_16_dxaFromText;
    protected int field_17_dyaFromText;
    protected int field_18_dxaFromTextRight;
    protected int field_19_dyaFromTextBottom;
    protected byte field_20_fBiDi;
    protected byte field_21_fRTL;
    protected byte field_22_fNoAllowOverlap;
    protected byte field_23_fSpare;
    protected int field_24_grpfTap;
    protected int field_25_internalFlags;
    private static BitField fFirstRow = new BitField( 0x0001 );
    private static BitField fLastRow = new BitField( 0x0002 );
    private static BitField fOutline = new BitField( 0x0004 );
    private static BitField fOrigWordTableRules = new BitField( 0x0008 );
    private static BitField fCellSpacing = new BitField( 0x0010 );
    private static BitField grpfTap_unused = new BitField( 0xffe0 );
    protected short field_26_itcMac;
    protected int field_27_dxaAdjust;
    protected int field_28_dxaWebView;
    protected int field_29_dxaRTEWrapWidth;
    protected int field_30_dxaColWidthWwd;
    protected short field_31_pctWwd;
    protected int field_32_viewFlags;
    private static BitField fWrapToWwd = new BitField( 0x0001 );
    private static BitField fNotPageView = new BitField( 0x0002 );
    private static BitField viewFlags_unused1 = new BitField( 0x0004 );
    private static BitField fWebView = new BitField( 0x0008 );
    private static BitField fAdjusted = new BitField( 0x0010 );
    private static BitField viewFlags_unused2 = new BitField( 0xffe0 );
    protected short[] field_33_rgdxaCenter;
    protected short[] field_34_rgdxaCenterPrint;
    protected ShadingDescriptor field_35_shdTable;
    protected BorderCode field_36_brcBottom;
    protected BorderCode field_37_brcTop;
    protected BorderCode field_38_brcLeft;
    protected BorderCode field_39_brcRight;
    protected BorderCode field_40_brcVertical;
    protected BorderCode field_41_brcHorizontal;
    protected short field_42_wCellPaddingDefaultTop;
    protected short field_43_wCellPaddingDefaultLeft;
    protected short field_44_wCellPaddingDefaultBottom;
    protected short field_45_wCellPaddingDefaultRight;
    protected byte field_46_ftsCellPaddingDefaultTop;
    protected byte field_47_ftsCellPaddingDefaultLeft;
    protected byte field_48_ftsCellPaddingDefaultBottom;
    protected byte field_49_ftsCellPaddingDefaultRight;
    protected short field_50_wCellSpacingDefaultTop;
    protected short field_51_wCellSpacingDefaultLeft;
    protected short field_52_wCellSpacingDefaultBottom;
    protected short field_53_wCellSpacingDefaultRight;
    protected byte field_54_ftsCellSpacingDefaultTop;
    protected byte field_55_ftsCellSpacingDefaultLeft;
    protected byte field_56_ftsCellSpacingDefaultBottom;
    protected byte field_57_ftsCellSpacingDefaultRight;
    protected short field_58_wCellPaddingOuterTop;
    protected short field_59_wCellPaddingOuterLeft;
    protected short field_60_wCellPaddingOuterBottom;
    protected short field_61_wCellPaddingOuterRight;
    protected byte field_62_ftsCellPaddingOuterTop;
    protected byte field_63_ftsCellPaddingOuterLeft;
    protected byte field_64_ftsCellPaddingOuterBottom;
    protected byte field_65_ftsCellPaddingOuterRight;
    protected short field_66_wCellSpacingOuterTop;
    protected short field_67_wCellSpacingOuterLeft;
    protected short field_68_wCellSpacingOuterBottom;
    protected short field_69_wCellSpacingOuterRight;
    protected byte field_70_ftsCellSpacingOuterTop;
    protected byte field_71_ftsCellSpacingOuterLeft;
    protected byte field_72_ftsCellSpacingOuterBottom;
    protected byte field_73_ftsCellSpacingOuterRight;
    protected TableCellDescriptor[] field_74_rgtc;
    protected ShadingDescriptor[] field_75_rgshd;
    protected byte field_76_fPropRMark;
    protected byte field_77_fHasOldProps;
    protected short field_78_cHorzBands;
    protected short field_79_cVertBands;
    protected BorderCode field_80_rgbrcInsideDefault_0;
    protected BorderCode field_81_rgbrcInsideDefault_1;
    protected short field_82_tableIndent;
    protected short field_83_tCellSpacingDefault;

    public TAPAbstractType()
    {

    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append( "[TAP]\n" );

        buffer.append( "    .istd                 = " );
        buffer.append( " (" ).append( getIstd() ).append( " )\n" );

        buffer.append( "    .jc                   = " );
        buffer.append( " (" ).append( getJc() ).append( " )\n" );

        buffer.append( "    .dxaGapHalf           = " );
        buffer.append( " (" ).append( getDxaGapHalf() ).append( " )\n" );

        buffer.append( "    .dyaRowHeight         = " );
        buffer.append( " (" ).append( getDyaRowHeight() ).append( " )\n" );

        buffer.append( "    .fCantSplit           = " );
        buffer.append( " (" ).append( getFCantSplit() ).append( " )\n" );

        buffer.append( "    .fCantSplit90         = " );
        buffer.append( " (" ).append( getFCantSplit90() ).append( " )\n" );

        buffer.append( "    .fTableHeader         = " );
        buffer.append( " (" ).append( getFTableHeader() ).append( " )\n" );

        buffer.append( "    .tlp                  = " );
        buffer.append( " (" ).append( getTlp() ).append( " )\n" );

        buffer.append( "    .wWidth               = " );
        buffer.append( " (" ).append( getWWidth() ).append( " )\n" );

        buffer.append( "    .wWidthIndent         = " );
        buffer.append( " (" ).append( getWWidthIndent() ).append( " )\n" );

        buffer.append( "    .wWidthBefore         = " );
        buffer.append( " (" ).append( getWWidthBefore() ).append( " )\n" );

        buffer.append( "    .wWidthAfter          = " );
        buffer.append( " (" ).append( getWWidthAfter() ).append( " )\n" );

        buffer.append( "    .widthAndFitsFlags    = " );
        buffer.append( " (" ).append( getWidthAndFitsFlags() ).append( " )\n" );
        buffer.append( "         .fAutofit                 = " )
                .append( isFAutofit() ).append( '\n' );
        buffer.append( "         .fKeepFollow              = " )
                .append( isFKeepFollow() ).append( '\n' );
        buffer.append( "         .ftsWidth                 = " )
                .append( getFtsWidth() ).append( '\n' );
        buffer.append( "         .ftsWidthIndent           = " )
                .append( getFtsWidthIndent() ).append( '\n' );
        buffer.append( "         .ftsWidthBefore           = " )
                .append( getFtsWidthBefore() ).append( '\n' );
        buffer.append( "         .ftsWidthAfter            = " )
                .append( getFtsWidthAfter() ).append( '\n' );
        buffer.append( "         .fNeverBeenAutofit        = " )
                .append( isFNeverBeenAutofit() ).append( '\n' );
        buffer.append( "         .fInvalAutofit            = " )
                .append( isFInvalAutofit() ).append( '\n' );
        buffer.append( "         .widthAndFitsFlags_empty1     = " )
                .append( getWidthAndFitsFlags_empty1() ).append( '\n' );
        buffer.append( "         .fVert                    = " )
                .append( isFVert() ).append( '\n' );
        buffer.append( "         .pcVert                   = " )
                .append( getPcVert() ).append( '\n' );
        buffer.append( "         .pcHorz                   = " )
                .append( getPcHorz() ).append( '\n' );
        buffer.append( "         .widthAndFitsFlags_empty2     = " )
                .append( getWidthAndFitsFlags_empty2() ).append( '\n' );

        buffer.append( "    .dxaAbs               = " );
        buffer.append( " (" ).append( getDxaAbs() ).append( " )\n" );

        buffer.append( "    .dyaAbs               = " );
        buffer.append( " (" ).append( getDyaAbs() ).append( " )\n" );

        buffer.append( "    .dxaFromText          = " );
        buffer.append( " (" ).append( getDxaFromText() ).append( " )\n" );

        buffer.append( "    .dyaFromText          = " );
        buffer.append( " (" ).append( getDyaFromText() ).append( " )\n" );

        buffer.append( "    .dxaFromTextRight     = " );
        buffer.append( " (" ).append( getDxaFromTextRight() ).append( " )\n" );

        buffer.append( "    .dyaFromTextBottom    = " );
        buffer.append( " (" ).append( getDyaFromTextBottom() ).append( " )\n" );

        buffer.append( "    .fBiDi                = " );
        buffer.append( " (" ).append( getFBiDi() ).append( " )\n" );

        buffer.append( "    .fRTL                 = " );
        buffer.append( " (" ).append( getFRTL() ).append( " )\n" );

        buffer.append( "    .fNoAllowOverlap      = " );
        buffer.append( " (" ).append( getFNoAllowOverlap() ).append( " )\n" );

        buffer.append( "    .fSpare               = " );
        buffer.append( " (" ).append( getFSpare() ).append( " )\n" );

        buffer.append( "    .grpfTap              = " );
        buffer.append( " (" ).append( getGrpfTap() ).append( " )\n" );

        buffer.append( "    .internalFlags        = " );
        buffer.append( " (" ).append( getInternalFlags() ).append( " )\n" );
        buffer.append( "         .fFirstRow                = " )
                .append( isFFirstRow() ).append( '\n' );
        buffer.append( "         .fLastRow                 = " )
                .append( isFLastRow() ).append( '\n' );
        buffer.append( "         .fOutline                 = " )
                .append( isFOutline() ).append( '\n' );
        buffer.append( "         .fOrigWordTableRules      = " )
                .append( isFOrigWordTableRules() ).append( '\n' );
        buffer.append( "         .fCellSpacing             = " )
                .append( isFCellSpacing() ).append( '\n' );
        buffer.append( "         .grpfTap_unused           = " )
                .append( getGrpfTap_unused() ).append( '\n' );

        buffer.append( "    .itcMac               = " );
        buffer.append( " (" ).append( getItcMac() ).append( " )\n" );

        buffer.append( "    .dxaAdjust            = " );
        buffer.append( " (" ).append( getDxaAdjust() ).append( " )\n" );

        buffer.append( "    .dxaWebView           = " );
        buffer.append( " (" ).append( getDxaWebView() ).append( " )\n" );

        buffer.append( "    .dxaRTEWrapWidth      = " );
        buffer.append( " (" ).append( getDxaRTEWrapWidth() ).append( " )\n" );

        buffer.append( "    .dxaColWidthWwd       = " );
        buffer.append( " (" ).append( getDxaColWidthWwd() ).append( " )\n" );

        buffer.append( "    .pctWwd               = " );
        buffer.append( " (" ).append( getPctWwd() ).append( " )\n" );

        buffer.append( "    .viewFlags            = " );
        buffer.append( " (" ).append( getViewFlags() ).append( " )\n" );
        buffer.append( "         .fWrapToWwd               = " )
                .append( isFWrapToWwd() ).append( '\n' );
        buffer.append( "         .fNotPageView             = " )
                .append( isFNotPageView() ).append( '\n' );
        buffer.append( "         .viewFlags_unused1        = " )
                .append( isViewFlags_unused1() ).append( '\n' );
        buffer.append( "         .fWebView                 = " )
                .append( isFWebView() ).append( '\n' );
        buffer.append( "         .fAdjusted                = " )
                .append( isFAdjusted() ).append( '\n' );
        buffer.append( "         .viewFlags_unused2        = " )
                .append( getViewFlags_unused2() ).append( '\n' );

        buffer.append( "    .rgdxaCenter          = " );
        buffer.append( " (" ).append( getRgdxaCenter() ).append( " )\n" );

        buffer.append( "    .rgdxaCenterPrint     = " );
        buffer.append( " (" ).append( getRgdxaCenterPrint() ).append( " )\n" );

        buffer.append( "    .shdTable             = " );
        buffer.append( " (" ).append( getShdTable() ).append( " )\n" );

        buffer.append( "    .brcBottom            = " );
        buffer.append( " (" ).append( getBrcBottom() ).append( " )\n" );

        buffer.append( "    .brcTop               = " );
        buffer.append( " (" ).append( getBrcTop() ).append( " )\n" );

        buffer.append( "    .brcLeft              = " );
        buffer.append( " (" ).append( getBrcLeft() ).append( " )\n" );

        buffer.append( "    .brcRight             = " );
        buffer.append( " (" ).append( getBrcRight() ).append( " )\n" );

        buffer.append( "    .brcVertical          = " );
        buffer.append( " (" ).append( getBrcVertical() ).append( " )\n" );

        buffer.append( "    .brcHorizontal        = " );
        buffer.append( " (" ).append( getBrcHorizontal() ).append( " )\n" );

        buffer.append( "    .wCellPaddingDefaultTop = " );
        buffer.append( " (" ).append( getWCellPaddingDefaultTop() )
                .append( " )\n" );

        buffer.append( "    .wCellPaddingDefaultLeft = " );
        buffer.append( " (" ).append( getWCellPaddingDefaultLeft() )
                .append( " )\n" );

        buffer.append( "    .wCellPaddingDefaultBottom = " );
        buffer.append( " (" ).append( getWCellPaddingDefaultBottom() )
                .append( " )\n" );

        buffer.append( "    .wCellPaddingDefaultRight = " );
        buffer.append( " (" ).append( getWCellPaddingDefaultRight() )
                .append( " )\n" );

        buffer.append( "    .ftsCellPaddingDefaultTop = " );
        buffer.append( " (" ).append( getFtsCellPaddingDefaultTop() )
                .append( " )\n" );

        buffer.append( "    .ftsCellPaddingDefaultLeft = " );
        buffer.append( " (" ).append( getFtsCellPaddingDefaultLeft() )
                .append( " )\n" );

        buffer.append( "    .ftsCellPaddingDefaultBottom = " );
        buffer.append( " (" ).append( getFtsCellPaddingDefaultBottom() )
                .append( " )\n" );

        buffer.append( "    .ftsCellPaddingDefaultRight = " );
        buffer.append( " (" ).append( getFtsCellPaddingDefaultRight() )
                .append( " )\n" );

        buffer.append( "    .wCellSpacingDefaultTop = " );
        buffer.append( " (" ).append( getWCellSpacingDefaultTop() )
                .append( " )\n" );

        buffer.append( "    .wCellSpacingDefaultLeft = " );
        buffer.append( " (" ).append( getWCellSpacingDefaultLeft() )
                .append( " )\n" );

        buffer.append( "    .wCellSpacingDefaultBottom = " );
        buffer.append( " (" ).append( getWCellSpacingDefaultBottom() )
                .append( " )\n" );

        buffer.append( "    .wCellSpacingDefaultRight = " );
        buffer.append( " (" ).append( getWCellSpacingDefaultRight() )
                .append( " )\n" );

        buffer.append( "    .ftsCellSpacingDefaultTop = " );
        buffer.append( " (" ).append( getFtsCellSpacingDefaultTop() )
                .append( " )\n" );

        buffer.append( "    .ftsCellSpacingDefaultLeft = " );
        buffer.append( " (" ).append( getFtsCellSpacingDefaultLeft() )
                .append( " )\n" );

        buffer.append( "    .ftsCellSpacingDefaultBottom = " );
        buffer.append( " (" ).append( getFtsCellSpacingDefaultBottom() )
                .append( " )\n" );

        buffer.append( "    .ftsCellSpacingDefaultRight = " );
        buffer.append( " (" ).append( getFtsCellSpacingDefaultRight() )
                .append( " )\n" );

        buffer.append( "    .wCellPaddingOuterTop = " );
        buffer.append( " (" ).append( getWCellPaddingOuterTop() )
                .append( " )\n" );

        buffer.append( "    .wCellPaddingOuterLeft = " );
        buffer.append( " (" ).append( getWCellPaddingOuterLeft() )
                .append( " )\n" );

        buffer.append( "    .wCellPaddingOuterBottom = " );
        buffer.append( " (" ).append( getWCellPaddingOuterBottom() )
                .append( " )\n" );

        buffer.append( "    .wCellPaddingOuterRight = " );
        buffer.append( " (" ).append( getWCellPaddingOuterRight() )
                .append( " )\n" );

        buffer.append( "    .ftsCellPaddingOuterTop = " );
        buffer.append( " (" ).append( getFtsCellPaddingOuterTop() )
                .append( " )\n" );

        buffer.append( "    .ftsCellPaddingOuterLeft = " );
        buffer.append( " (" ).append( getFtsCellPaddingOuterLeft() )
                .append( " )\n" );

        buffer.append( "    .ftsCellPaddingOuterBottom = " );
        buffer.append( " (" ).append( getFtsCellPaddingOuterBottom() )
                .append( " )\n" );

        buffer.append( "    .ftsCellPaddingOuterRight = " );
        buffer.append( " (" ).append( getFtsCellPaddingOuterRight() )
                .append( " )\n" );

        buffer.append( "    .wCellSpacingOuterTop = " );
        buffer.append( " (" ).append( getWCellSpacingOuterTop() )
                .append( " )\n" );

        buffer.append( "    .wCellSpacingOuterLeft = " );
        buffer.append( " (" ).append( getWCellSpacingOuterLeft() )
                .append( " )\n" );

        buffer.append( "    .wCellSpacingOuterBottom = " );
        buffer.append( " (" ).append( getWCellSpacingOuterBottom() )
                .append( " )\n" );

        buffer.append( "    .wCellSpacingOuterRight = " );
        buffer.append( " (" ).append( getWCellSpacingOuterRight() )
                .append( " )\n" );

        buffer.append( "    .ftsCellSpacingOuterTop = " );
        buffer.append( " (" ).append( getFtsCellSpacingOuterTop() )
                .append( " )\n" );

        buffer.append( "    .ftsCellSpacingOuterLeft = " );
        buffer.append( " (" ).append( getFtsCellSpacingOuterLeft() )
                .append( " )\n" );

        buffer.append( "    .ftsCellSpacingOuterBottom = " );
        buffer.append( " (" ).append( getFtsCellSpacingOuterBottom() )
                .append( " )\n" );

        buffer.append( "    .ftsCellSpacingOuterRight = " );
        buffer.append( " (" ).append( getFtsCellSpacingOuterRight() )
                .append( " )\n" );

        buffer.append( "    .rgtc                 = " );
        buffer.append( " (" ).append( getRgtc() ).append( " )\n" );

        buffer.append( "    .rgshd                = " );
        buffer.append( " (" ).append( getRgshd() ).append( " )\n" );

        buffer.append( "    .fPropRMark           = " );
        buffer.append( " (" ).append( getFPropRMark() ).append( " )\n" );

        buffer.append( "    .fHasOldProps         = " );
        buffer.append( " (" ).append( getFHasOldProps() ).append( " )\n" );

        buffer.append( "    .cHorzBands           = " );
        buffer.append( " (" ).append( getCHorzBands() ).append( " )\n" );

        buffer.append( "    .cVertBands           = " );
        buffer.append( " (" ).append( getCVertBands() ).append( " )\n" );

        buffer.append( "    .rgbrcInsideDefault_0 = " );
        buffer.append( " (" ).append( getRgbrcInsideDefault_0() )
                .append( " )\n" );

        buffer.append( "    .rgbrcInsideDefault_1 = " );
        buffer.append( " (" ).append( getRgbrcInsideDefault_1() )
                .append( " )\n" );

        buffer.append( "[/TAP]\n" );
        return buffer.toString();
    }

    public int getSize()
    {
        return 4 + +2 + 2 + 4 + 4 + 0 + 0 + 0 + 4 + 2 + 2 + 2 + 2 + 4 + 4 + 4
                + 4 + 4 + 4 + 4 + 1 + 1 + 1 + 1 + 2 + 2 + 2 + 4 + 4 + 4 + 4 + 2
                + 2 + 130 + 130 + 10 + 4 + 4 + 4 + 4 + 4 + 4 + 2 + 2 + 2 + 2
                + 1 + 1 + 1 + 1 + 2 + 2 + 2 + 2 + 1 + 1 + 1 + 1 + 2 + 2 + 2 + 2
                + 1 + 1 + 1 + 1 + 2 + 2 + 2 + 2 + 1 + 1 + 1 + 1 + 0 + 0 + 1 + 1
                + 1 + 1 + 8 + 8;
    }

    public short getIstd()
    {
        return field_1_istd;
    }

    public void setIstd( short field_1_istd )
    {
        this.field_1_istd = field_1_istd;
    }


    public short getJc()
    {
        return field_2_jc;
    }

    public void setJc( short field_2_jc )
    {
        this.field_2_jc = field_2_jc;
    }
   

    public short getTableInIndent()
    {
        return this.field_82_tableIndent;
    }
    

    public void setTableIndent(short field_82_tableIndent)
    {
        this.field_82_tableIndent = field_82_tableIndent;
    }
    
    public void setTCellSpacingDefault(short field_83_tCellSpacingDefault)
    {
        this.field_83_tCellSpacingDefault = field_83_tCellSpacingDefault;
    }
    
    public short getTCellSpacingDefault()
    {
        return this.field_83_tCellSpacingDefault;
    }
   


    public int getDxaGapHalf()
    {
        return field_3_dxaGapHalf;
    }


    public void setDxaGapHalf( int field_3_dxaGapHalf )
    {
        this.field_3_dxaGapHalf = field_3_dxaGapHalf;
    }


    public int getDyaRowHeight()
    {
        return field_4_dyaRowHeight;
    }

    public void setDyaRowHeight( int field_4_dyaRowHeight )
    {
        this.field_4_dyaRowHeight = field_4_dyaRowHeight;
    }


    public boolean getFCantSplit()
    {
        return field_5_fCantSplit;
    }


    public void setFCantSplit( boolean field_5_fCantSplit )
    {
        this.field_5_fCantSplit = field_5_fCantSplit;
    }


    public boolean getFCantSplit90()
    {
        return field_6_fCantSplit90;
    }


    public void setFCantSplit90( boolean field_6_fCantSplit90 )
    {
        this.field_6_fCantSplit90 = field_6_fCantSplit90;
    }


    public boolean getFTableHeader()
    {
        return field_7_fTableHeader;
    }


    public void setFTableHeader( boolean field_7_fTableHeader )
    {
        this.field_7_fTableHeader = field_7_fTableHeader;
    }


    public TableAutoformatLookSpecifier getTlp()
    {
        return field_8_tlp;
    }


    public void setTlp( TableAutoformatLookSpecifier field_8_tlp )
    {
        this.field_8_tlp = field_8_tlp;
    }


    public short getWWidth()
    {
        return field_9_wWidth;
    }


    public void setWWidth( short field_9_wWidth )
    {
        this.field_9_wWidth = field_9_wWidth;
    }


    public short getWWidthIndent()
    {
        return field_10_wWidthIndent;
    }


    public void setWWidthIndent( short field_10_wWidthIndent )
    {
        this.field_10_wWidthIndent = field_10_wWidthIndent;
    }


    public short getWWidthBefore()
    {
        return field_11_wWidthBefore;
    }


    public void setWWidthBefore( short field_11_wWidthBefore )
    {
        this.field_11_wWidthBefore = field_11_wWidthBefore;
    }


    public short getWWidthAfter()
    {
        return field_12_wWidthAfter;
    }


    public void setWWidthAfter( short field_12_wWidthAfter )
    {
        this.field_12_wWidthAfter = field_12_wWidthAfter;
    }

    public int getWidthAndFitsFlags()
    {
        return field_13_widthAndFitsFlags;
    }


    public void setWidthAndFitsFlags( int field_13_widthAndFitsFlags )
    {
        this.field_13_widthAndFitsFlags = field_13_widthAndFitsFlags;
    }


    public int getDxaAbs()
    {
        return field_14_dxaAbs;
    }


    public void setDxaAbs( int field_14_dxaAbs )
    {
        this.field_14_dxaAbs = field_14_dxaAbs;
    }

    public int getDyaAbs()
    {
        return field_15_dyaAbs;
    }


    public void setDyaAbs( int field_15_dyaAbs )
    {
        this.field_15_dyaAbs = field_15_dyaAbs;
    }


    public int getDxaFromText()
    {
        return field_16_dxaFromText;
    }

    public void setDxaFromText( int field_16_dxaFromText )
    {
        this.field_16_dxaFromText = field_16_dxaFromText;
    }


    public int getDyaFromText()
    {
        return field_17_dyaFromText;
    }


    public void setDyaFromText( int field_17_dyaFromText )
    {
        this.field_17_dyaFromText = field_17_dyaFromText;
    }


    public int getDxaFromTextRight()
    {
        return field_18_dxaFromTextRight;
    }


    public void setDxaFromTextRight( int field_18_dxaFromTextRight )
    {
        this.field_18_dxaFromTextRight = field_18_dxaFromTextRight;
    }


    public int getDyaFromTextBottom()
    {
        return field_19_dyaFromTextBottom;
    }


    public void setDyaFromTextBottom( int field_19_dyaFromTextBottom )
    {
        this.field_19_dyaFromTextBottom = field_19_dyaFromTextBottom;
    }


    public byte getFBiDi()
    {
        return field_20_fBiDi;
    }


    public void setFBiDi( byte field_20_fBiDi )
    {
        this.field_20_fBiDi = field_20_fBiDi;
    }


    public byte getFRTL()
    {
        return field_21_fRTL;
    }

    public void setFRTL( byte field_21_fRTL )
    {
        this.field_21_fRTL = field_21_fRTL;
    }

    public byte getFNoAllowOverlap()
    {
        return field_22_fNoAllowOverlap;
    }

    public void setFNoAllowOverlap( byte field_22_fNoAllowOverlap )
    {
        this.field_22_fNoAllowOverlap = field_22_fNoAllowOverlap;
    }


    public byte getFSpare()
    {
        return field_23_fSpare;
    }


    public void setFSpare( byte field_23_fSpare )
    {
        this.field_23_fSpare = field_23_fSpare;
    }


    public int getGrpfTap()
    {
        return field_24_grpfTap;
    }

    public void setGrpfTap( int field_24_grpfTap )
    {
        this.field_24_grpfTap = field_24_grpfTap;
    }


    public int getInternalFlags()
    {
        return field_25_internalFlags;
    }


    public void setInternalFlags( int field_25_internalFlags )
    {
        this.field_25_internalFlags = field_25_internalFlags;
    }


    public short getItcMac()
    {
        return field_26_itcMac;
    }


    public void setItcMac( short field_26_itcMac )
    {
        this.field_26_itcMac = field_26_itcMac;
    }


    public int getDxaAdjust()
    {
        return field_27_dxaAdjust;
    }


    public void setDxaAdjust( int field_27_dxaAdjust )
    {
        this.field_27_dxaAdjust = field_27_dxaAdjust;
    }


    public int getDxaWebView()
    {
        return field_28_dxaWebView;
    }


    public void setDxaWebView( int field_28_dxaWebView )
    {
        this.field_28_dxaWebView = field_28_dxaWebView;
    }


    public int getDxaRTEWrapWidth()
    {
        return field_29_dxaRTEWrapWidth;
    }


    public void setDxaRTEWrapWidth( int field_29_dxaRTEWrapWidth )
    {
        this.field_29_dxaRTEWrapWidth = field_29_dxaRTEWrapWidth;
    }


    public int getDxaColWidthWwd()
    {
        return field_30_dxaColWidthWwd;
    }

    public void setDxaColWidthWwd( int field_30_dxaColWidthWwd )
    {
        this.field_30_dxaColWidthWwd = field_30_dxaColWidthWwd;
    }

    /**
     * Get the pctWwd field for the TAP record.
     */
    public short getPctWwd()
    {
        return field_31_pctWwd;
    }

    public void setPctWwd( short field_31_pctWwd )
    {
        this.field_31_pctWwd = field_31_pctWwd;
    }


    public int getViewFlags()
    {
        return field_32_viewFlags;
    }

    public void setViewFlags( int field_32_viewFlags )
    {
        this.field_32_viewFlags = field_32_viewFlags;
    }


    public short[] getRgdxaCenter()
    {
        return field_33_rgdxaCenter;
    }


    public void setRgdxaCenter( short[] field_33_rgdxaCenter )
    {
        this.field_33_rgdxaCenter = field_33_rgdxaCenter;
    }


    public short[] getRgdxaCenterPrint()
    {
        return field_34_rgdxaCenterPrint;
    }


    public void setRgdxaCenterPrint( short[] field_34_rgdxaCenterPrint )
    {
        this.field_34_rgdxaCenterPrint = field_34_rgdxaCenterPrint;
    }


    public ShadingDescriptor getShdTable()
    {
        return field_35_shdTable;
    }


    public void setShdTable( ShadingDescriptor field_35_shdTable )
    {
        this.field_35_shdTable = field_35_shdTable;
    }


    public BorderCode getBrcBottom()
    {
        return field_36_brcBottom;
    }


    public void setBrcBottom( BorderCode field_36_brcBottom )
    {
        this.field_36_brcBottom = field_36_brcBottom;
    }


    public BorderCode getBrcTop()
    {
        return field_37_brcTop;
    }

    public void setBrcTop( BorderCode field_37_brcTop )
    {
        this.field_37_brcTop = field_37_brcTop;
    }


    public BorderCode getBrcLeft()
    {
        return field_38_brcLeft;
    }

    public void setBrcLeft( BorderCode field_38_brcLeft )
    {
        this.field_38_brcLeft = field_38_brcLeft;
    }


    public BorderCode getBrcRight()
    {
        return field_39_brcRight;
    }


    public void setBrcRight( BorderCode field_39_brcRight )
    {
        this.field_39_brcRight = field_39_brcRight;
    }


    public BorderCode getBrcVertical()
    {
        return field_40_brcVertical;
    }

    public void setBrcVertical( BorderCode field_40_brcVertical )
    {
        this.field_40_brcVertical = field_40_brcVertical;
    }


    public BorderCode getBrcHorizontal()
    {
        return field_41_brcHorizontal;
    }


    public void setBrcHorizontal( BorderCode field_41_brcHorizontal )
    {
        this.field_41_brcHorizontal = field_41_brcHorizontal;
    }


    public short getWCellPaddingDefaultTop()
    {
        return field_42_wCellPaddingDefaultTop;
    }

    public void setWCellPaddingDefaultTop( short field_42_wCellPaddingDefaultTop )
    {
        this.field_42_wCellPaddingDefaultTop = field_42_wCellPaddingDefaultTop;
    }


    public short getWCellPaddingDefaultLeft()
    {
        return field_43_wCellPaddingDefaultLeft;
    }

    public void setWCellPaddingDefaultLeft(
            short field_43_wCellPaddingDefaultLeft )
    {
        this.field_43_wCellPaddingDefaultLeft = field_43_wCellPaddingDefaultLeft;
    }

    public short getWCellPaddingDefaultBottom()
    {
        return field_44_wCellPaddingDefaultBottom;
    }


    public void setWCellPaddingDefaultBottom(
            short field_44_wCellPaddingDefaultBottom )
    {
        this.field_44_wCellPaddingDefaultBottom = field_44_wCellPaddingDefaultBottom;
    }


    public short getWCellPaddingDefaultRight()
    {
        return field_45_wCellPaddingDefaultRight;
    }


    public void setWCellPaddingDefaultRight(
            short field_45_wCellPaddingDefaultRight )
    {
        this.field_45_wCellPaddingDefaultRight = field_45_wCellPaddingDefaultRight;
    }


    public byte getFtsCellPaddingDefaultTop()
    {
        return field_46_ftsCellPaddingDefaultTop;
    }


    public void setFtsCellPaddingDefaultTop(
            byte field_46_ftsCellPaddingDefaultTop )
    {
        this.field_46_ftsCellPaddingDefaultTop = field_46_ftsCellPaddingDefaultTop;
    }


    public byte getFtsCellPaddingDefaultLeft()
    {
        return field_47_ftsCellPaddingDefaultLeft;
    }


    public void setFtsCellPaddingDefaultLeft(
            byte field_47_ftsCellPaddingDefaultLeft )
    {
        this.field_47_ftsCellPaddingDefaultLeft = field_47_ftsCellPaddingDefaultLeft;
    }


    public byte getFtsCellPaddingDefaultBottom()
    {
        return field_48_ftsCellPaddingDefaultBottom;
    }


    public void setFtsCellPaddingDefaultBottom(
            byte field_48_ftsCellPaddingDefaultBottom )
    {
        this.field_48_ftsCellPaddingDefaultBottom = field_48_ftsCellPaddingDefaultBottom;
    }


    public byte getFtsCellPaddingDefaultRight()
    {
        return field_49_ftsCellPaddingDefaultRight;
    }


    public void setFtsCellPaddingDefaultRight(
            byte field_49_ftsCellPaddingDefaultRight )
    {
        this.field_49_ftsCellPaddingDefaultRight = field_49_ftsCellPaddingDefaultRight;
    }


    public short getWCellSpacingDefaultTop()
    {
        return field_50_wCellSpacingDefaultTop;
    }


    public void setWCellSpacingDefaultTop( short field_50_wCellSpacingDefaultTop )
    {
        this.field_50_wCellSpacingDefaultTop = field_50_wCellSpacingDefaultTop;
    }


    public short getWCellSpacingDefaultLeft()
    {
        return field_51_wCellSpacingDefaultLeft;
    }


    public void setWCellSpacingDefaultLeft(
            short field_51_wCellSpacingDefaultLeft )
    {
        this.field_51_wCellSpacingDefaultLeft = field_51_wCellSpacingDefaultLeft;
    }


    public short getWCellSpacingDefaultBottom()
    {
        return field_52_wCellSpacingDefaultBottom;
    }



    public void setWCellSpacingDefaultBottom(
            short field_52_wCellSpacingDefaultBottom )
    {
        this.field_52_wCellSpacingDefaultBottom = field_52_wCellSpacingDefaultBottom;
    }


    public short getWCellSpacingDefaultRight()
    {
        return field_53_wCellSpacingDefaultRight;
    }

    public void setWCellSpacingDefaultRight(
            short field_53_wCellSpacingDefaultRight )
    {
        this.field_53_wCellSpacingDefaultRight = field_53_wCellSpacingDefaultRight;
    }

    public byte getFtsCellSpacingDefaultTop()
    {
        return field_54_ftsCellSpacingDefaultTop;
    }


    public void setFtsCellSpacingDefaultTop(
            byte field_54_ftsCellSpacingDefaultTop )
    {
        this.field_54_ftsCellSpacingDefaultTop = field_54_ftsCellSpacingDefaultTop;
    }


    public byte getFtsCellSpacingDefaultLeft()
    {
        return field_55_ftsCellSpacingDefaultLeft;
    }


    public void setFtsCellSpacingDefaultLeft(
            byte field_55_ftsCellSpacingDefaultLeft )
    {
        this.field_55_ftsCellSpacingDefaultLeft = field_55_ftsCellSpacingDefaultLeft;
    }


    public byte getFtsCellSpacingDefaultBottom()
    {
        return field_56_ftsCellSpacingDefaultBottom;
    }


    public void setFtsCellSpacingDefaultBottom(
            byte field_56_ftsCellSpacingDefaultBottom )
    {
        this.field_56_ftsCellSpacingDefaultBottom = field_56_ftsCellSpacingDefaultBottom;
    }


    public byte getFtsCellSpacingDefaultRight()
    {
        return field_57_ftsCellSpacingDefaultRight;
    }


    public void setFtsCellSpacingDefaultRight(
            byte field_57_ftsCellSpacingDefaultRight )
    {
        this.field_57_ftsCellSpacingDefaultRight = field_57_ftsCellSpacingDefaultRight;
    }


    public short getWCellPaddingOuterTop()
    {
        return field_58_wCellPaddingOuterTop;
    }

    public void setWCellPaddingOuterTop( short field_58_wCellPaddingOuterTop )
    {
        this.field_58_wCellPaddingOuterTop = field_58_wCellPaddingOuterTop;
    }


    public short getWCellPaddingOuterLeft()
    {
        return field_59_wCellPaddingOuterLeft;
    }


    public void setWCellPaddingOuterLeft( short field_59_wCellPaddingOuterLeft )
    {
        this.field_59_wCellPaddingOuterLeft = field_59_wCellPaddingOuterLeft;
    }


    public short getWCellPaddingOuterBottom()
    {
        return field_60_wCellPaddingOuterBottom;
    }


    public void setWCellPaddingOuterBottom(
            short field_60_wCellPaddingOuterBottom )
    {
        this.field_60_wCellPaddingOuterBottom = field_60_wCellPaddingOuterBottom;
    }


    public short getWCellPaddingOuterRight()
    {
        return field_61_wCellPaddingOuterRight;
    }


    public void setWCellPaddingOuterRight( short field_61_wCellPaddingOuterRight )
    {
        this.field_61_wCellPaddingOuterRight = field_61_wCellPaddingOuterRight;
    }


    public byte getFtsCellPaddingOuterTop()
    {
        return field_62_ftsCellPaddingOuterTop;
    }

    public void setFtsCellPaddingOuterTop( byte field_62_ftsCellPaddingOuterTop )
    {
        this.field_62_ftsCellPaddingOuterTop = field_62_ftsCellPaddingOuterTop;
    }


    public byte getFtsCellPaddingOuterLeft()
    {
        return field_63_ftsCellPaddingOuterLeft;
    }

    /**
     * Set the ftsCellPaddingOuterLeft field for the TAP record.
     */
    public void setFtsCellPaddingOuterLeft(
            byte field_63_ftsCellPaddingOuterLeft )
    {
        this.field_63_ftsCellPaddingOuterLeft = field_63_ftsCellPaddingOuterLeft;
    }

    public byte getFtsCellPaddingOuterBottom()
    {
        return field_64_ftsCellPaddingOuterBottom;
    }


    public void setFtsCellPaddingOuterBottom(
            byte field_64_ftsCellPaddingOuterBottom )
    {
        this.field_64_ftsCellPaddingOuterBottom = field_64_ftsCellPaddingOuterBottom;
    }


    public byte getFtsCellPaddingOuterRight()
    {
        return field_65_ftsCellPaddingOuterRight;
    }

    public void setFtsCellPaddingOuterRight(
            byte field_65_ftsCellPaddingOuterRight )
    {
        this.field_65_ftsCellPaddingOuterRight = field_65_ftsCellPaddingOuterRight;
    }



    public short getWCellSpacingOuterTop()
    {
        return field_66_wCellSpacingOuterTop;
    }

    public void setWCellSpacingOuterTop( short field_66_wCellSpacingOuterTop )
    {
        this.field_66_wCellSpacingOuterTop = field_66_wCellSpacingOuterTop;
    }


    public short getWCellSpacingOuterLeft()
    {
        return field_67_wCellSpacingOuterLeft;
    }


    public void setWCellSpacingOuterLeft( short field_67_wCellSpacingOuterLeft )
    {
        this.field_67_wCellSpacingOuterLeft = field_67_wCellSpacingOuterLeft;
    }



    public short getWCellSpacingOuterBottom()
    {
        return field_68_wCellSpacingOuterBottom;
    }


    public void setWCellSpacingOuterBottom(
            short field_68_wCellSpacingOuterBottom )
    {
        this.field_68_wCellSpacingOuterBottom = field_68_wCellSpacingOuterBottom;
    }

    public short getWCellSpacingOuterRight()
    {
        return field_69_wCellSpacingOuterRight;
    }


    public void setWCellSpacingOuterRight( short field_69_wCellSpacingOuterRight )
    {
        this.field_69_wCellSpacingOuterRight = field_69_wCellSpacingOuterRight;
    }


    public byte getFtsCellSpacingOuterTop()
    {
        return field_70_ftsCellSpacingOuterTop;
    }

    public void setFtsCellSpacingOuterTop( byte field_70_ftsCellSpacingOuterTop )
    {
        this.field_70_ftsCellSpacingOuterTop = field_70_ftsCellSpacingOuterTop;
    }

    public byte getFtsCellSpacingOuterLeft()
    {
        return field_71_ftsCellSpacingOuterLeft;
    }


    public void setFtsCellSpacingOuterLeft(
            byte field_71_ftsCellSpacingOuterLeft )
    {
        this.field_71_ftsCellSpacingOuterLeft = field_71_ftsCellSpacingOuterLeft;
    }


    public byte getFtsCellSpacingOuterBottom()
    {
        return field_72_ftsCellSpacingOuterBottom;
    }


    public void setFtsCellSpacingOuterBottom(
            byte field_72_ftsCellSpacingOuterBottom )
    {
        this.field_72_ftsCellSpacingOuterBottom = field_72_ftsCellSpacingOuterBottom;
    }


    public byte getFtsCellSpacingOuterRight()
    {
        return field_73_ftsCellSpacingOuterRight;
    }



    public void setFtsCellSpacingOuterRight(
            byte field_73_ftsCellSpacingOuterRight )
    {
        this.field_73_ftsCellSpacingOuterRight = field_73_ftsCellSpacingOuterRight;
    }


    public TableCellDescriptor[] getRgtc()
    {
        return field_74_rgtc;
    }


    public void setRgtc( TableCellDescriptor[] field_74_rgtc )
    {
        this.field_74_rgtc = field_74_rgtc;
    }


    public ShadingDescriptor[] getRgshd()
    {
        return field_75_rgshd;
    }

    public void setRgshd( ShadingDescriptor[] field_75_rgshd )
    {
        this.field_75_rgshd = field_75_rgshd;
    }


    public byte getFPropRMark()
    {
        return field_76_fPropRMark;
    }

    public void setFPropRMark( byte field_76_fPropRMark )
    {
        this.field_76_fPropRMark = field_76_fPropRMark;
    }

    public byte getFHasOldProps()
    {
        return field_77_fHasOldProps;
    }



    public void setFHasOldProps( byte field_77_fHasOldProps )
    {
        this.field_77_fHasOldProps = field_77_fHasOldProps;
    }


    public short getCHorzBands()
    {
        return field_78_cHorzBands;
    }


    public void setCHorzBands( short field_78_cHorzBands )
    {
        this.field_78_cHorzBands = field_78_cHorzBands;
    }

    public short getCVertBands()
    {
        return field_79_cVertBands;
    }


    public void setCVertBands( short field_79_cVertBands )
    {
        this.field_79_cVertBands = field_79_cVertBands;
    }


    public BorderCode getRgbrcInsideDefault_0()
    {
        return field_80_rgbrcInsideDefault_0;
    }


    public void setRgbrcInsideDefault_0(
            BorderCode field_80_rgbrcInsideDefault_0 )
    {
        this.field_80_rgbrcInsideDefault_0 = field_80_rgbrcInsideDefault_0;
    }


    public BorderCode getRgbrcInsideDefault_1()
    {
        return field_81_rgbrcInsideDefault_1;
    }


    public void setRgbrcInsideDefault_1(
            BorderCode field_81_rgbrcInsideDefault_1 )
    {
        this.field_81_rgbrcInsideDefault_1 = field_81_rgbrcInsideDefault_1;
    }


    public void setFAutofit( boolean value )
    {
        field_13_widthAndFitsFlags = fAutofit.setBoolean(
                field_13_widthAndFitsFlags, value );

    }


    public boolean isFAutofit()
    {
        return fAutofit.isSet( field_13_widthAndFitsFlags );

    }


    public void setFKeepFollow( boolean value )
    {
        field_13_widthAndFitsFlags = fKeepFollow.setBoolean(
                field_13_widthAndFitsFlags, value );

    }


    public boolean isFKeepFollow()
    {
        return fKeepFollow.isSet( field_13_widthAndFitsFlags );

    }


    public void setFtsWidth( byte value )
    {
        field_13_widthAndFitsFlags = ftsWidth.setValue(
                field_13_widthAndFitsFlags, value );

    }


    public byte getFtsWidth()
    {
        return (byte) ftsWidth.getValue( field_13_widthAndFitsFlags );

    }

    public void setFtsWidthIndent( byte value )
    {
        field_13_widthAndFitsFlags = ftsWidthIndent.setValue(
                field_13_widthAndFitsFlags, value );

    }


    public byte getFtsWidthIndent()
    {
        return (byte) ftsWidthIndent.getValue( field_13_widthAndFitsFlags );

    }


    public void setFtsWidthBefore( byte value )
    {
        field_13_widthAndFitsFlags = ftsWidthBefore.setValue(
                field_13_widthAndFitsFlags, value );

    }


    public byte getFtsWidthBefore()
    {
        return (byte) ftsWidthBefore.getValue( field_13_widthAndFitsFlags );

    }


    public void setFtsWidthAfter( byte value )
    {
        field_13_widthAndFitsFlags = ftsWidthAfter.setValue(
                field_13_widthAndFitsFlags, value );

    }


    public byte getFtsWidthAfter()
    {
        return (byte) ftsWidthAfter.getValue( field_13_widthAndFitsFlags );

    }

    public void setFNeverBeenAutofit( boolean value )
    {
        field_13_widthAndFitsFlags = fNeverBeenAutofit.setBoolean(
                field_13_widthAndFitsFlags, value );

    }

    public boolean isFNeverBeenAutofit()
    {
        return fNeverBeenAutofit.isSet( field_13_widthAndFitsFlags );

    }


    public void setFInvalAutofit( boolean value )
    {
        field_13_widthAndFitsFlags = fInvalAutofit.setBoolean(
                field_13_widthAndFitsFlags, value );

    }


    public boolean isFInvalAutofit()
    {
        return fInvalAutofit.isSet( field_13_widthAndFitsFlags );

    }

    public void setWidthAndFitsFlags_empty1( byte value )
    {
        field_13_widthAndFitsFlags = widthAndFitsFlags_empty1.setValue(
                field_13_widthAndFitsFlags, value );

    }


    public byte getWidthAndFitsFlags_empty1()
    {
        return (byte) widthAndFitsFlags_empty1
                .getValue( field_13_widthAndFitsFlags );

    }


    public void setFVert( boolean value )
    {
        field_13_widthAndFitsFlags = fVert.setBoolean(
                field_13_widthAndFitsFlags, value );

    }

    public boolean isFVert()
    {
        return fVert.isSet( field_13_widthAndFitsFlags );

    }


    public void setPcVert( byte value )
    {
        field_13_widthAndFitsFlags = pcVert.setValue(
                field_13_widthAndFitsFlags, value );

    }


    public byte getPcVert()
    {
        return (byte) pcVert.getValue( field_13_widthAndFitsFlags );

    }


    public void setPcHorz( byte value )
    {
        field_13_widthAndFitsFlags = pcHorz.setValue(
                field_13_widthAndFitsFlags, value );

    }


    public byte getPcHorz()
    {
        return (byte) pcHorz.getValue( field_13_widthAndFitsFlags );

    }


    public void setWidthAndFitsFlags_empty2( short value )
    {
        field_13_widthAndFitsFlags = widthAndFitsFlags_empty2.setValue(
                field_13_widthAndFitsFlags, value );

    }

    public short getWidthAndFitsFlags_empty2()
    {
        return (short) widthAndFitsFlags_empty2
                .getValue( field_13_widthAndFitsFlags );

    }

    public void setFFirstRow( boolean value )
    {
        field_25_internalFlags = fFirstRow.setBoolean( field_25_internalFlags,
                value );

    }


    public boolean isFFirstRow()
    {
        return fFirstRow.isSet( field_25_internalFlags );

    }

    public void setFLastRow( boolean value )
    {
        field_25_internalFlags = fLastRow.setBoolean( field_25_internalFlags,
                value );

    }

    public boolean isFLastRow()
    {
        return fLastRow.isSet( field_25_internalFlags );

    }


    public void setFOutline( boolean value )
    {
        field_25_internalFlags = fOutline.setBoolean( field_25_internalFlags,
                value );

    }


    public boolean isFOutline()
    {
        return fOutline.isSet( field_25_internalFlags );

    }


    public void setFOrigWordTableRules( boolean value )
    {
        field_25_internalFlags = fOrigWordTableRules.setBoolean(
                field_25_internalFlags, value );

    }


    public boolean isFOrigWordTableRules()
    {
        return fOrigWordTableRules.isSet( field_25_internalFlags );

    }

    public void setFCellSpacing( boolean value )
    {
        field_25_internalFlags = fCellSpacing.setBoolean(
                field_25_internalFlags, value );

    }


    public boolean isFCellSpacing()
    {
        return fCellSpacing.isSet( field_25_internalFlags );

    }

    public void setGrpfTap_unused( short value )
    {
        field_25_internalFlags = grpfTap_unused.setValue(
                field_25_internalFlags, value );

    }

    public short getGrpfTap_unused()
    {
        return (short) grpfTap_unused.getValue( field_25_internalFlags );

    }


    public void setFWrapToWwd( boolean value )
    {
        field_32_viewFlags = fWrapToWwd.setBoolean( field_32_viewFlags, value );

    }

    public boolean isFWrapToWwd()
    {
        return fWrapToWwd.isSet( field_32_viewFlags );

    }

    public void setFNotPageView( boolean value )
    {
        field_32_viewFlags = fNotPageView
                .setBoolean( field_32_viewFlags, value );

    }

    public boolean isFNotPageView()
    {
        return fNotPageView.isSet( field_32_viewFlags );

    }


    public void setViewFlags_unused1( boolean value )
    {
        field_32_viewFlags = viewFlags_unused1.setBoolean( field_32_viewFlags,
                value );

    }


    public boolean isViewFlags_unused1()
    {
        return viewFlags_unused1.isSet( field_32_viewFlags );

    }



    public void setFWebView( boolean value )
    {
        field_32_viewFlags = fWebView.setBoolean( field_32_viewFlags, value );

    }


    public boolean isFWebView()
    {
        return fWebView.isSet( field_32_viewFlags );

    }


    public void setFAdjusted( boolean value )
    {
        field_32_viewFlags = fAdjusted.setBoolean( field_32_viewFlags, value );

    }

    public boolean isFAdjusted()
    {
        return fAdjusted.isSet( field_32_viewFlags );

    }

    public void setViewFlags_unused2( short value )
    {
        field_32_viewFlags = viewFlags_unused2.setValue( field_32_viewFlags,
                value );

    }

    public short getViewFlags_unused2()
    {
        return (short) viewFlags_unused2.getValue( field_32_viewFlags );

    }

}
