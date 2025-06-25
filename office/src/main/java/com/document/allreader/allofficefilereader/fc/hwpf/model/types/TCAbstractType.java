

package com.document.allreader.allofficefilereader.fc.hwpf.model.types;



import com.document.allreader.allofficefilereader.fc.hwpf.model.HDFType;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.BorderCode;
import com.document.allreader.allofficefilereader.fc.util.BitField;
import com.document.allreader.allofficefilereader.fc.util.Internal;



@Internal
public abstract class TCAbstractType
    implements HDFType
{

    protected  short field_1_rgf;
        private static BitField  fFirstMerged = new BitField(0x0001);
        private static BitField  fMerged = new BitField(0x0002);
        private static BitField  fVertical = new BitField(0x0004);
        private static BitField  fBackward = new BitField(0x0008);
        private static BitField  fRotateFont = new BitField(0x0010);
        private static BitField  fVertMerge = new BitField(0x0020);
        private static BitField  fVertRestart = new BitField(0x0040);
        private static BitField  vertAlign = new BitField(0x0180);
        private static BitField  ftsWidth = new BitField(0x0E00);
        private static BitField  fFitText = new BitField(0x1000);
        private static BitField  fNoWrap = new BitField(0x2000);
        private static BitField  fUnused = new BitField(0xC000);
    protected  short field_2_wWidth;
    protected  short field_3_wCellPaddingLeft;
    protected  short field_4_wCellPaddingTop;
    protected  short field_5_wCellPaddingBottom;
    protected  short field_6_wCellPaddingRight;
    protected  byte field_7_ftsCellPaddingLeft;
    protected  byte field_8_ftsCellPaddingTop;
    protected  byte field_9_ftsCellPaddingBottom;
    protected  byte field_10_ftsCellPaddingRight;
    protected  short field_11_wCellSpacingLeft;
    protected  short field_12_wCellSpacingTop;
    protected  short field_13_wCellSpacingBottom;
    protected  short field_14_wCellSpacingRight;
    protected  byte field_15_ftsCellSpacingLeft;
    protected  byte field_16_ftsCellSpacingTop;
    protected  byte field_17_ftsCellSpacingBottom;
    protected  byte field_18_ftsCellSpacingRight;
    protected  BorderCode field_19_brcTop;
    protected  BorderCode field_20_brcLeft;
    protected  BorderCode field_21_brcBottom;
    protected  BorderCode field_22_brcRight;


    public TCAbstractType()
    {

    }


    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[TC]\n");

        buffer.append("    .rgf                  = ");
        buffer.append(" (").append(getRgf()).append(" )\n");
        buffer.append("         .fFirstMerged             = ").append(isFFirstMerged()).append('\n');
        buffer.append("         .fMerged                  = ").append(isFMerged()).append('\n');
        buffer.append("         .fVertical                = ").append(isFVertical()).append('\n');
        buffer.append("         .fBackward                = ").append(isFBackward()).append('\n');
        buffer.append("         .fRotateFont              = ").append(isFRotateFont()).append('\n');
        buffer.append("         .fVertMerge               = ").append(isFVertMerge()).append('\n');
        buffer.append("         .fVertRestart             = ").append(isFVertRestart()).append('\n');
        buffer.append("         .vertAlign                = ").append(getVertAlign()).append('\n');
        buffer.append("         .ftsWidth                 = ").append(getFtsWidth()).append('\n');
        buffer.append("         .fFitText                 = ").append(isFFitText()).append('\n');
        buffer.append("         .fNoWrap                  = ").append(isFNoWrap()).append('\n');
        buffer.append("         .fUnused                  = ").append(getFUnused()).append('\n');

        buffer.append("    .wWidth               = ");
        buffer.append(" (").append(getWWidth()).append(" )\n");

        buffer.append("    .wCellPaddingLeft     = ");
        buffer.append(" (").append(getWCellPaddingLeft()).append(" )\n");

        buffer.append("    .wCellPaddingTop      = ");
        buffer.append(" (").append(getWCellPaddingTop()).append(" )\n");

        buffer.append("    .wCellPaddingBottom   = ");
        buffer.append(" (").append(getWCellPaddingBottom()).append(" )\n");

        buffer.append("    .wCellPaddingRight    = ");
        buffer.append(" (").append(getWCellPaddingRight()).append(" )\n");

        buffer.append("    .ftsCellPaddingLeft   = ");
        buffer.append(" (").append(getFtsCellPaddingLeft()).append(" )\n");

        buffer.append("    .ftsCellPaddingTop    = ");
        buffer.append(" (").append(getFtsCellPaddingTop()).append(" )\n");

        buffer.append("    .ftsCellPaddingBottom = ");
        buffer.append(" (").append(getFtsCellPaddingBottom()).append(" )\n");

        buffer.append("    .ftsCellPaddingRight  = ");
        buffer.append(" (").append(getFtsCellPaddingRight()).append(" )\n");

        buffer.append("    .wCellSpacingLeft     = ");
        buffer.append(" (").append(getWCellSpacingLeft()).append(" )\n");

        buffer.append("    .wCellSpacingTop      = ");
        buffer.append(" (").append(getWCellSpacingTop()).append(" )\n");

        buffer.append("    .wCellSpacingBottom   = ");
        buffer.append(" (").append(getWCellSpacingBottom()).append(" )\n");

        buffer.append("    .wCellSpacingRight    = ");
        buffer.append(" (").append(getWCellSpacingRight()).append(" )\n");

        buffer.append("    .ftsCellSpacingLeft   = ");
        buffer.append(" (").append(getFtsCellSpacingLeft()).append(" )\n");

        buffer.append("    .ftsCellSpacingTop    = ");
        buffer.append(" (").append(getFtsCellSpacingTop()).append(" )\n");

        buffer.append("    .ftsCellSpacingBottom = ");
        buffer.append(" (").append(getFtsCellSpacingBottom()).append(" )\n");

        buffer.append("    .ftsCellSpacingRight  = ");
        buffer.append(" (").append(getFtsCellSpacingRight()).append(" )\n");

        buffer.append("    .brcTop               = ");
        buffer.append(" (").append(getBrcTop()).append(" )\n");

        buffer.append("    .brcLeft              = ");
        buffer.append(" (").append(getBrcLeft()).append(" )\n");

        buffer.append("    .brcBottom            = ");
        buffer.append(" (").append(getBrcBottom()).append(" )\n");

        buffer.append("    .brcRight             = ");
        buffer.append(" (").append(getBrcRight()).append(" )\n");

        buffer.append("[/TC]\n");
        return buffer.toString();
    }


    public int getSize()
    {
        return 4 +  + 2 + 2 + 2 + 2 + 2 + 2 + 1 + 1 + 1 + 1 + 2 + 2 + 2 + 2 + 1 + 1 + 1 + 1 + 4 + 4 + 4 + 4;
    }




    public short getRgf()
    {
        return field_1_rgf;
    }


    public void setRgf(short field_1_rgf)
    {
        this.field_1_rgf = field_1_rgf;
    }


    public short getWWidth()
    {
        return field_2_wWidth;
    }


    public void setWWidth(short field_2_wWidth)
    {
        this.field_2_wWidth = field_2_wWidth;
    }

    public short getWCellPaddingLeft()
    {
        return field_3_wCellPaddingLeft;
    }


    public void setWCellPaddingLeft(short field_3_wCellPaddingLeft)
    {
        this.field_3_wCellPaddingLeft = field_3_wCellPaddingLeft;
    }

    public short getWCellPaddingTop()
    {
        return field_4_wCellPaddingTop;
    }


    public void setWCellPaddingTop(short field_4_wCellPaddingTop)
    {
        this.field_4_wCellPaddingTop = field_4_wCellPaddingTop;
    }

    public short getWCellPaddingBottom()
    {
        return field_5_wCellPaddingBottom;
    }


    public void setWCellPaddingBottom(short field_5_wCellPaddingBottom)
    {
        this.field_5_wCellPaddingBottom = field_5_wCellPaddingBottom;
    }


    public short getWCellPaddingRight()
    {
        return field_6_wCellPaddingRight;
    }


    public void setWCellPaddingRight(short field_6_wCellPaddingRight)
    {
        this.field_6_wCellPaddingRight = field_6_wCellPaddingRight;
    }


    public byte getFtsCellPaddingLeft()
    {
        return field_7_ftsCellPaddingLeft;
    }


    public void setFtsCellPaddingLeft(byte field_7_ftsCellPaddingLeft)
    {
        this.field_7_ftsCellPaddingLeft = field_7_ftsCellPaddingLeft;
    }


    public byte getFtsCellPaddingTop()
    {
        return field_8_ftsCellPaddingTop;
    }


    public void setFtsCellPaddingTop(byte field_8_ftsCellPaddingTop)
    {
        this.field_8_ftsCellPaddingTop = field_8_ftsCellPaddingTop;
    }


    public byte getFtsCellPaddingBottom()
    {
        return field_9_ftsCellPaddingBottom;
    }


    public void setFtsCellPaddingBottom(byte field_9_ftsCellPaddingBottom)
    {
        this.field_9_ftsCellPaddingBottom = field_9_ftsCellPaddingBottom;
    }

    public byte getFtsCellPaddingRight()
    {
        return field_10_ftsCellPaddingRight;
    }


    public void setFtsCellPaddingRight(byte field_10_ftsCellPaddingRight)
    {
        this.field_10_ftsCellPaddingRight = field_10_ftsCellPaddingRight;
    }


    public short getWCellSpacingLeft()
    {
        return field_11_wCellSpacingLeft;
    }


    public void setWCellSpacingLeft(short field_11_wCellSpacingLeft)
    {
        this.field_11_wCellSpacingLeft = field_11_wCellSpacingLeft;
    }



    public short getWCellSpacingTop()
    {
        return field_12_wCellSpacingTop;
    }


    public void setWCellSpacingTop(short field_12_wCellSpacingTop)
    {
        this.field_12_wCellSpacingTop = field_12_wCellSpacingTop;
    }


    public short getWCellSpacingBottom()
    {
        return field_13_wCellSpacingBottom;
    }


    public void setWCellSpacingBottom(short field_13_wCellSpacingBottom)
    {
        this.field_13_wCellSpacingBottom = field_13_wCellSpacingBottom;
    }


    public short getWCellSpacingRight()
    {
        return field_14_wCellSpacingRight;
    }


    public void setWCellSpacingRight(short field_14_wCellSpacingRight)
    {
        this.field_14_wCellSpacingRight = field_14_wCellSpacingRight;
    }


    public byte getFtsCellSpacingLeft()
    {
        return field_15_ftsCellSpacingLeft;
    }


    public void setFtsCellSpacingLeft(byte field_15_ftsCellSpacingLeft)
    {
        this.field_15_ftsCellSpacingLeft = field_15_ftsCellSpacingLeft;
    }

    /**
     * Get the ftsCellSpacingTop field for the TC record.
     */
    public byte getFtsCellSpacingTop()
    {
        return field_16_ftsCellSpacingTop;
    }


    public void setFtsCellSpacingTop(byte field_16_ftsCellSpacingTop)
    {
        this.field_16_ftsCellSpacingTop = field_16_ftsCellSpacingTop;
    }

    public byte getFtsCellSpacingBottom()
    {
        return field_17_ftsCellSpacingBottom;
    }


    public void setFtsCellSpacingBottom(byte field_17_ftsCellSpacingBottom)
    {
        this.field_17_ftsCellSpacingBottom = field_17_ftsCellSpacingBottom;
    }


    public byte getFtsCellSpacingRight()
    {
        return field_18_ftsCellSpacingRight;
    }

    public void setFtsCellSpacingRight(byte field_18_ftsCellSpacingRight)
    {
        this.field_18_ftsCellSpacingRight = field_18_ftsCellSpacingRight;
    }


    public BorderCode getBrcTop()
    {
        return field_19_brcTop;
    }


    public void setBrcTop(BorderCode field_19_brcTop)
    {
        this.field_19_brcTop = field_19_brcTop;
    }

    public BorderCode getBrcLeft()
    {
        return field_20_brcLeft;
    }


    public void setBrcLeft(BorderCode field_20_brcLeft)
    {
        this.field_20_brcLeft = field_20_brcLeft;
    }

    public BorderCode getBrcBottom()
    {
        return field_21_brcBottom;
    }


    public void setBrcBottom(BorderCode field_21_brcBottom)
    {
        this.field_21_brcBottom = field_21_brcBottom;
    }


    public BorderCode getBrcRight()
    {
        return field_22_brcRight;
    }


    public void setBrcRight(BorderCode field_22_brcRight)
    {
        this.field_22_brcRight = field_22_brcRight;
    }


    public void setFFirstMerged(boolean value)
    {
        field_1_rgf = (short)fFirstMerged.setBoolean(field_1_rgf, value);

        
    }


    public boolean isFFirstMerged()
    {
        return fFirstMerged.isSet(field_1_rgf);
        
    }


    public void setFMerged(boolean value)
    {
        field_1_rgf = (short)fMerged.setBoolean(field_1_rgf, value);

        
    }


    public boolean isFMerged()
    {
        return fMerged.isSet(field_1_rgf);
        
    }


    public void setFVertical(boolean value)
    {
        field_1_rgf = (short)fVertical.setBoolean(field_1_rgf, value);

        
    }



    public boolean isFVertical()
    {
        return fVertical.isSet(field_1_rgf);
        
    }


    public void setFBackward(boolean value)
    {
        field_1_rgf = (short)fBackward.setBoolean(field_1_rgf, value);

        
    }


    public boolean isFBackward()
    {
        return fBackward.isSet(field_1_rgf);
        
    }


    public void setFRotateFont(boolean value)
    {
        field_1_rgf = (short)fRotateFont.setBoolean(field_1_rgf, value);

        
    }

    public boolean isFRotateFont()
    {
        return fRotateFont.isSet(field_1_rgf);
        
    }

    public void setFVertMerge(boolean value)
    {
        field_1_rgf = (short)fVertMerge.setBoolean(field_1_rgf, value);

        
    }


    public boolean isFVertMerge()
    {
        return fVertMerge.isSet(field_1_rgf);
        
    }


    public void setFVertRestart(boolean value)
    {
        field_1_rgf = (short)fVertRestart.setBoolean(field_1_rgf, value);

        
    }


    public boolean isFVertRestart()
    {
        return fVertRestart.isSet(field_1_rgf);
        
    }


    public void setVertAlign(byte value)
    {
        field_1_rgf = (short)vertAlign.setValue(field_1_rgf, value);

        
    }


    public byte getVertAlign()
    {
        return ( byte )vertAlign.getValue(field_1_rgf);
        
    }


    public void setFtsWidth(byte value)
    {
        field_1_rgf = (short)ftsWidth.setValue(field_1_rgf, value);

        
    }


    public byte getFtsWidth()
    {
        return ( byte )ftsWidth.getValue(field_1_rgf);
        
    }


    public void setFFitText(boolean value)
    {
        field_1_rgf = (short)fFitText.setBoolean(field_1_rgf, value);

        
    }

    public boolean isFFitText()
    {
        return fFitText.isSet(field_1_rgf);
        
    }


    public void setFNoWrap(boolean value)
    {
        field_1_rgf = (short)fNoWrap.setBoolean(field_1_rgf, value);

        
    }


    public boolean isFNoWrap()
    {
        return fNoWrap.isSet(field_1_rgf);
        
    }


    public void setFUnused(byte value)
    {
        field_1_rgf = (short)fUnused.setValue(field_1_rgf, value);

        
    }


    public byte getFUnused()
    {
        return ( byte )fUnused.getValue(field_1_rgf);
        
    }


}




