

package com.document.allreader.allofficefilereader.fc.hslf.usermodel;

import com.document.allreader.allofficefilereader.constant.MainConstant;
import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.hslf.model.MasterSheet;
import com.document.allreader.allofficefilereader.fc.hslf.model.Sheet;
import com.document.allreader.allofficefilereader.fc.hslf.model.TextRun;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.BitMaskTextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.CharFlagsTextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.ParagraphFlagsTextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.TextProp;
import com.document.allreader.allofficefilereader.fc.hslf.model.textproperties.TextPropCollection;
import com.document.allreader.allofficefilereader.fc.hslf.record.ColorSchemeAtom;
import com.document.allreader.allofficefilereader.java.awt.Color;


public final class RichTextRun
{

    public RichTextRun(TextRun parent, int startAt, int len)
    {
        this(parent, startAt, len, null, null, false, false);
    }


    public RichTextRun(TextRun parent, int startAt, int len, TextPropCollection pStyle,
        TextPropCollection cStyle, boolean pShared, boolean cShared)
    {
        parentRun = parent;
        startPos = startAt;
        length = len;
        paragraphStyle = pStyle;
        characterStyle = cStyle;
        sharingParagraphStyle = pShared;
        sharingCharacterStyle = cShared;
    }


    public void supplyTextProps(TextPropCollection pStyle, TextPropCollection cStyle,
        boolean pShared, boolean cShared)
    {
        if (paragraphStyle != null || characterStyle != null)
        {
            throw new IllegalStateException("Can't call supplyTextProps if run already has some");
        }
        paragraphStyle = pStyle;
        characterStyle = cStyle;
        sharingParagraphStyle = pShared;
        sharingCharacterStyle = cShared;
    }


    public void supplySlideShow(SlideShow ss)
    {
        slideShow = ss;
        if (_fontname != null)
        {
            setFontName(_fontname);
            _fontname = null;
        }
    }


    public int getLength()
    {
        return length;
    }


    public int getStartIndex()
    {
        return startPos;
    }


    public int getEndIndex()
    {
        return startPos + length;
    }

    public String getText()
    {
        String text = parentRun.getText();
        int end = Math.min(text.length(), startPos + length);
        return text.substring(startPos, end);
    }

    public String getRawText()
    {
        return parentRun.getRawText().substring(startPos, startPos + length);
    }


    public void setText(String text)
    {
        String s = parentRun.normalize(text);
        setRawText(s);
    }

    public void setRawText(String text)
    {
        length = text.length();
        parentRun.changeTextInRichTextRun(this, text);
    }


    public void updateStartPosition(int startAt)
    {
        startPos = startAt;
    }


    private boolean isCharFlagsTextPropVal(int index)
    {
        return getFlag(true, index);
    }

    private boolean getFlag(boolean isCharacter, int index)
    {
        TextPropCollection props;
        String propname;
        if (isCharacter)
        {
            props = characterStyle;
            propname = CharFlagsTextProp.NAME;
        }
        else
        {
            props = paragraphStyle;
            propname = ParagraphFlagsTextProp.NAME;
        }

        BitMaskTextProp prop = null;
        if (props != null)
        {
            prop = (BitMaskTextProp)props.findByName(propname);
        }
        if (prop == null)
        {
            Sheet sheet = parentRun.getSheet();
            if (sheet != null)
            {
                int txtype = parentRun.getRunType();
                MasterSheet master = sheet.getMasterSheet();
                if (master != null)
                {
                    prop = (BitMaskTextProp)master.getStyleAttribute(txtype, getIndentLevel(),
                        propname, isCharacter);
                }
            }
            else
            {
            }
        }

        return prop == null ? false : prop.getSubValue(index);
    }


    private void setCharFlagsTextPropVal(int index, boolean value)
    {
        if (getFlag(true, index) != value)
            setFlag(true, index, value);
    }

    public void setFlag(boolean isCharacter, int index, boolean value)
    {
        TextPropCollection props;
        String propname;
        if (isCharacter)
        {
            props = characterStyle;
            propname = CharFlagsTextProp.NAME;
        }
        else
        {
            props = paragraphStyle;
            propname = ParagraphFlagsTextProp.NAME;
        }

        // Ensure we have the StyleTextProp atom we're going to need
        if (props == null)
        {
            parentRun.ensureStyleAtomPresent();
            props = isCharacter ? characterStyle : paragraphStyle;
        }

        BitMaskTextProp prop = (BitMaskTextProp)fetchOrAddTextProp(props, propname);
        prop.setSubValue(value, index);
    }


    private TextProp fetchOrAddTextProp(TextPropCollection textPropCol, String textPropName)
    {

        TextProp tp = textPropCol.findByName(textPropName);
        if (tp == null)
        {
            tp = textPropCol.addWithName(textPropName);
        }
        return tp;
    }


    private int getCharTextPropVal(String propName)
    {
        TextProp prop = null;
        if (characterStyle != null)
        {
            prop = characterStyle.findByName(propName);
        }

        if (prop == null)
        {
            Sheet sheet = parentRun.getSheet();
            int txtype = parentRun.getRunType();
            MasterSheet master = sheet.getMasterSheet();
            if (master != null)
            {
                prop = master.getStyleAttribute(txtype, getIndentLevel(), propName, true);
            }
        }
        if (prop == null && propName.equalsIgnoreCase("font.color"))
        {
            return Color.BLACK.getRGB();
        }
        return prop == null ? -1 : prop.getValue();
    }


    private int getParaTextPropVal(String propName)
    {
        TextProp prop = null;
        boolean hardAttribute = false;
        if (paragraphStyle != null)
        {
            prop = paragraphStyle.findByName(propName);

        }
        if (prop == null && !hardAttribute)
        {
            Sheet sheet = parentRun.getSheet();
            int txtype = parentRun.getRunType();
            MasterSheet master = sheet.getMasterSheet();
            if (master != null)
                prop = master.getStyleAttribute(txtype, getIndentLevel(), propName, false);
        }

        return prop == null ? -1 : prop.getValue();
    }

    public void setParaTextPropVal(String propName, int val)
    {

        if (paragraphStyle == null)
        {
            parentRun.ensureStyleAtomPresent();
        }

        TextProp tp = fetchOrAddTextProp(paragraphStyle, propName);
        tp.setValue(val);
    }


    public void setCharTextPropVal(String propName, int val)
    {

        if (characterStyle == null)
        {
            parentRun.ensureStyleAtomPresent();
        }

        TextProp tp = fetchOrAddTextProp(characterStyle, propName);
        tp.setValue(val);
    }


    public boolean isBold()
    {
        return isCharFlagsTextPropVal(CharFlagsTextProp.BOLD_IDX);
    }

    public void setBold(boolean bold)
    {
        setCharFlagsTextPropVal(CharFlagsTextProp.BOLD_IDX, bold);
    }


    public boolean isItalic()
    {
        return isCharFlagsTextPropVal(CharFlagsTextProp.ITALIC_IDX);
    }

    public void setItalic(boolean italic)
    {
        setCharFlagsTextPropVal(CharFlagsTextProp.ITALIC_IDX, italic);
    }

    public boolean isUnderlined()
    {
        return isCharFlagsTextPropVal(CharFlagsTextProp.UNDERLINE_IDX);
    }


    public void setUnderlined(boolean underlined)
    {
        setCharFlagsTextPropVal(CharFlagsTextProp.UNDERLINE_IDX, underlined);
    }


    public boolean isShadowed()
    {
        return isCharFlagsTextPropVal(CharFlagsTextProp.SHADOW_IDX);
    }


    public void setShadowed(boolean flag)
    {
        setCharFlagsTextPropVal(CharFlagsTextProp.SHADOW_IDX, flag);
    }

    public boolean isEmbossed()
    {
        return isCharFlagsTextPropVal(CharFlagsTextProp.RELIEF_IDX);
    }

    public void setEmbossed(boolean flag)
    {
        setCharFlagsTextPropVal(CharFlagsTextProp.RELIEF_IDX, flag);
    }


    public boolean isStrikethrough()
    {
        return isCharFlagsTextPropVal(CharFlagsTextProp.STRIKETHROUGH_IDX);
    }


    public void setStrikethrough(boolean flag)
    {
        setCharFlagsTextPropVal(CharFlagsTextProp.STRIKETHROUGH_IDX, flag);
    }

    public int getSuperscript()
    {
        int val = getCharTextPropVal("superscript");
        return val == -1 ? 0 : val;
    }

    public void setSuperscript(int val)
    {
        setCharTextPropVal("superscript", val);
    }


    public int getFontSize()
    {
        return getCharTextPropVal("font.size");
    }


    public void setFontSize(int fontSize)
    {
        setCharTextPropVal("font.size", fontSize);
    }


    public int getFontIndex()
    {
        return getCharTextPropVal("font.index");
    }


    public void setFontIndex(int idx)
    {
        setCharTextPropVal("font.index", idx);
    }


    public void setFontName(String fontName)
    {
        if (slideShow == null)
        {
            _fontname = fontName;
        }
        else
        {
            int fontIdx = slideShow.getFontCollection().addFont(fontName);
            setCharTextPropVal("font.index", fontIdx);
        }
    }


    public String getFontName()
    {
        if (slideShow == null)
        {
            return _fontname;
        }
        int fontIdx = getCharTextPropVal("font.index");
        if (fontIdx == -1)
        {
            return null;
        }
        return slideShow.getFontCollection().getFontWithId(fontIdx);
    }


    public Color getFontColor()
    {
        int rgb = getCharTextPropVal("font.color");

        int cidx = rgb >> 24;
        if (rgb % 0x1000000 == 0)
        {
            ColorSchemeAtom ca = parentRun.getSheet().getColorScheme();
            if (cidx >= 0 && cidx <= 7)
                rgb = ca.getColor(cidx);
        }
        Color tmp = new Color(rgb, true);
        return new Color(tmp.getBlue(), tmp.getGreen(), tmp.getRed());
    }


    public void setFontColor(int bgr)
    {
        setCharTextPropVal("font.color", bgr);
    }


    public void setFontColor(Color color)
    {
        int rgb = new Color(color.getBlue(), color.getGreen(), color.getRed(), 254).getRGB();
        setFontColor(rgb);
    }


    public void setAlignment(int align)
    {
        setParaTextPropVal("alignment", align);
    }

    public int getAlignment()
    {
        return getParaTextPropVal("alignment");
    }


    public int getIndentLevel()
    {
        return paragraphStyle == null ? 0 : paragraphStyle.getReservedField();
    }


    public void setIndentLevel(int level)
    {
        if (paragraphStyle != null)
            paragraphStyle.setReservedField((short)level);
    }


    public void setBullet(boolean flag)
    {
        setFlag(false, ParagraphFlagsTextProp.BULLET_IDX, flag);
    }


    public boolean isBullet()
    {
        return getFlag(false, ParagraphFlagsTextProp.BULLET_IDX);
    }


    public boolean isBulletHard()
    {
        return getFlag(false, ParagraphFlagsTextProp.BULLET_IDX);
    }

    public void setBulletChar(char c)
    {
        setParaTextPropVal("bullet.char", c);
    }


    public char getBulletChar()
    {
        return (char)getParaTextPropVal("bullet.char");
    }


    public void setBulletOffset(int offset)
    {
        setParaTextPropVal("bullet.offset", (int)(offset * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
    }


    public int getBulletOffset()
    {
        return (int)(getParaTextPropVal("bullet.offset") * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI);
    }


    public void setTextOffset(int offset)
    {
        setParaTextPropVal("text.offset", (int)(offset * ShapeKit.MASTER_DPI / MainConstant.POINT_DPI));
    }


    public int getTextOffset()
    {
        return (int)(getParaTextPropVal("text.offset") * MainConstant.POINT_DPI / ShapeKit.MASTER_DPI);
    }

    public void setBulletSize(int size)
    {
        setParaTextPropVal("bullet.size", size);
    }


    public int getBulletSize()
    {
        return getParaTextPropVal("bullet.size");
    }


    public void setBulletColor(Color color)
    {
        int rgb = new Color(color.getBlue(), color.getGreen(), color.getRed(), 254).getRGB();
        setParaTextPropVal("bullet.color", rgb);
    }


    public Color getBulletColor()
    {
        int rgb = getParaTextPropVal("bullet.color");
        if (rgb == -1)
            return getFontColor();

        int cidx = rgb >> 24;
        if (rgb % 0x1000000 == 0)
        {
            ColorSchemeAtom ca = parentRun.getSheet().getColorScheme();
            if (cidx >= 0 && cidx <= 7)
                rgb = ca.getColor(cidx);
        }
        Color tmp = new Color(rgb, true);
        return new Color(tmp.getBlue(), tmp.getGreen(), tmp.getRed());
    }


    public void setBulletFont(int idx)
    {
        setParaTextPropVal("bullet.font", idx);
        setFlag(false, ParagraphFlagsTextProp.BULLET_HARDFONT_IDX, true);
    }

    public int getBulletFont()
    {
        return getParaTextPropVal("bullet.font");
    }


    public void setLineSpacing(int val)
    {
        setParaTextPropVal("linespacing", val);
    }


    public int getLineSpacing()
    {
        int val = getParaTextPropVal("linespacing");
        return val == -1 ? 0 : val;
    }


    public void setSpaceBefore(int val)
    {
        setParaTextPropVal("spacebefore", val);
    }


    public int getSpaceBefore()
    {
        int val = getParaTextPropVal("spacebefore");
        return val == -1 ? 0 : val;
    }

    public void setSpaceAfter(int val)
    {
        setParaTextPropVal("spaceafter", val);
    }


    public int getSpaceAfter()
    {
        int val = getParaTextPropVal("spaceafter");
        return val == -1 ? 0 : val;
    }


    public TextPropCollection _getRawParagraphStyle()
    {
        return paragraphStyle;
    }


    public TextPropCollection _getRawCharacterStyle()
    {
        return characterStyle;
    }


    public boolean _isParagraphStyleShared()
    {
        return sharingParagraphStyle;
    }


    public boolean _isCharacterStyleShared()
    {
        return sharingCharacterStyle;
    }

    public void dispose()
    {
        parentRun = null;
        slideShow = null;
        _fontname = null;
        if (paragraphStyle != null)
        {
            paragraphStyle.dispose();
            paragraphStyle = null;
        }
        if (characterStyle != null)
        {
            characterStyle.dispose();
            characterStyle = null;
        }
    }
    


    private TextRun parentRun;
    private SlideShow slideShow;

    private int startPos;

    private int length;

    private String _fontname;
    private TextPropCollection paragraphStyle;
    private TextPropCollection characterStyle;
    private boolean sharingParagraphStyle;
    private boolean sharingCharacterStyle;
}
