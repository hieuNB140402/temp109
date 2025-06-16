

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import com.document.allreader.allofficefilereader.fc.hwpf.model.Colorref;
import com.document.allreader.allofficefilereader.fc.hwpf.model.types.CHPAbstractType;


public final class CharacterProperties
        extends CHPAbstractType implements Cloneable {

    public final static short SPRM_CCV = 0x6870;

    public CharacterProperties() {
        setFUsePgsuSettings(true);
        setXstDispFldRMark(new byte[36]);
    }

    public boolean isMarkedDeleted() {
        return isFRMarkDel();
    }

    public void markDeleted(boolean mark) {
        super.setFRMarkDel(mark);
    }

    public boolean isBold() {
        return isFBold();
    }

    public void setBold(boolean bold) {
        super.setFBold(bold);
    }

    public boolean isItalic() {
        return isFItalic();
    }

    public void setItalic(boolean italic) {
        super.setFItalic(italic);
    }

    public boolean isOutlined() {
        return isFOutline();
    }

    public void setOutline(boolean outlined) {
        super.setFOutline(outlined);
    }

    public boolean isFldVanished() {
        return isFFldVanish();
    }

    public void setFldVanish(boolean fldVanish) {
        super.setFFldVanish(fldVanish);
    }

    public boolean isSmallCaps() {
        return isFSmallCaps();
    }

    public void setSmallCaps(boolean smallCaps) {
        super.setFSmallCaps(smallCaps);
    }

    public boolean isCapitalized() {
        return isFCaps();
    }

    public void setCapitalized(boolean caps) {
        super.setFCaps(caps);
    }

    public boolean isVanished() {
        return isFVanish();
    }

    public void setVanished(boolean vanish) {
        super.setFVanish(vanish);

    }

    public boolean isMarkedInserted() {
        return isFRMark();
    }

    public void markInserted(boolean mark) {
        super.setFRMark(mark);
    }

    public boolean isStrikeThrough() {
        return isFStrike();
    }

    public void strikeThrough(boolean strike) {
        super.setFStrike(strike);
    }

    public boolean isShadowed() {
        return isFShadow();
    }

    public void setShadow(boolean shadow) {
        super.setFShadow(shadow);

    }

    public boolean isEmbossed() {
        return isFEmboss();
    }

    public void setEmbossed(boolean emboss) {
        super.setFEmboss(emboss);
    }

    public boolean isImprinted() {
        return isFImprint();
    }

    public void setImprinted(boolean imprint) {
        super.setFImprint(imprint);
    }

    public boolean isDoubleStrikeThrough() {
        return isFDStrike();
    }

    public void setDoubleStrikeThrough(boolean dstrike) {
        super.setFDStrike(dstrike);
    }

    public int getFontSize() {
        return getHps();
    }

    public void setFontSize(int halfPoints) {
        super.setHps(halfPoints);
    }

    public int getCharacterSpacing() {
        return getDxaSpace();
    }

    public void setCharacterSpacing(int twips) {
        super.setDxaSpace(twips);
    }

    public short getSubSuperScriptIndex() {
        return getIss();
    }

    public void setSubSuperScriptIndex(short iss) {
        super.setDxaSpace(iss);
    }

    public int getUnderlineCode() {
        return super.getKul();
    }

    public void setUnderlineCode(int kul) {
        super.setKul((byte) kul);
    }

    public int getColor() {
        return super.getIco();
    }

    public void setColor(int color) {
        super.setIco((byte) color);
    }

    public int getVerticalOffset() {
        return super.getHpsPos();
    }

    public void setVerticalOffset(int hpsPos) {
        super.setHpsPos((short) hpsPos);
    }

    public int getKerning() {
        return super.getHpsKern();
    }

    public void setKerning(int kern) {
        super.setHpsKern(kern);
    }

    public boolean isHighlighted() {
        return super.isFHighlight();
    }

    public void setHighlighted(byte color) {
        super.setIcoHighlight(color);
    }

    /**
     * Get the ico24 field for the CHP record.
     */
    public int getIco24() {
        if (!getCv().isEmpty())
            return getCv().getValue();

        // convert word 97 colour numbers to 0xBBRRGGRR value
        switch (getIco()) {
            case 0: // auto
                return -1;
            case 1: // black
                return 0x00000000;
            case 2: // blue
                return 0x00FF0000;
            case 3: // cyan
                return 0x00FFFF00;
            case 4: // green
                return 0x0000FF00;
            case 5: // magenta
                return 0x00FF00FF;
            case 6: // red
                return 0x000000FF;
            case 7: // yellow
                return 0x0000FFFF;
            case 8: // white
                return 0x00FFFFFF;
            case 9: // dark blue
                return 0x00800000;
            case 10: // dark cyan
                return 0x00808000;
            case 11: // dark green
                return 0x00008000;
            case 12: // dark magenta
                return 0x00800080;
            case 13: // dark red
                return 0x00000080;
            case 14: // dark yellow
                return 0x00008080;
            case 15: // dark grey
                return 0x00808080;
            case 16: // light grey
                return 0x00C0C0C0;
        }

        return -1;
    }


    public void setIco24(int colour24) {
        setCv(new Colorref(colour24 & 0xFFFFFF));
    }

    public Object clone() throws CloneNotSupportedException {
        CharacterProperties cp = (CharacterProperties) super.clone();

        cp.setCv(getCv().clone());
        cp.setDttmRMark((DateAndTime) getDttmRMark().clone());
        cp.setDttmRMarkDel((DateAndTime) getDttmRMarkDel().clone());
        cp.setDttmPropRMark((DateAndTime) getDttmPropRMark().clone());
        cp.setDttmDispFldRMark((DateAndTime) getDttmDispFldRMark().clone());
        cp.setXstDispFldRMark(getXstDispFldRMark().clone());
        cp.setShd((ShadingDescriptor) getShd().clone());
        cp.setBrc((BorderCode) getBrc().clone());

        return cp;
    }
}
