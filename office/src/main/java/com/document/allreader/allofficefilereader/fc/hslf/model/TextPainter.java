

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.fc.hslf.usermodel.RichTextRun;
import com.document.allreader.allofficefilereader.fc.util.POILogFactory;
import com.document.allreader.allofficefilereader.fc.util.POILogger;

import java.awt.font.TextAttribute;
import java.text.AttributedString;


public final class TextPainter {
    protected POILogger logger = POILogFactory.getLogger(this.getClass());


    protected static final char DEFAULT_BULLET_CHAR = '\u25a0';

    protected TextShape _shape;

    public TextPainter(TextShape shape) {
        _shape = shape;
    }

    public AttributedString getAttributedString(TextRun txrun) {
        String text = txrun.getText();

        text = text.replace('\t', ' ');
        text = text.replace((char) 160, ' ');

        AttributedString at = new AttributedString(text);
        RichTextRun[] rt = txrun.getRichTextRuns();
        for (int i = 0; i < rt.length; i++) {
            int start = rt[i].getStartIndex();
            int end = rt[i].getEndIndex();
            if (start == end) {
                logger.log(POILogger.INFO, "Skipping RichTextRun with zero length");
                continue;
            }

            at.addAttribute(TextAttribute.FAMILY, rt[i].getFontName(), start, end);
            at.addAttribute(TextAttribute.SIZE, new Float(rt[i].getFontSize()), start, end);
            at.addAttribute(TextAttribute.FOREGROUND, rt[i].getFontColor(), start, end);
            if (rt[i].isBold())
                at.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD, start, end);
            if (rt[i].isItalic())
                at.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE, start, end);
            if (rt[i].isUnderlined()) {
                at.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, start, end);
                at.addAttribute(TextAttribute.INPUT_METHOD_UNDERLINE,
                        TextAttribute.UNDERLINE_LOW_TWO_PIXEL, start, end);
            }
            if (rt[i].isStrikethrough())
                at.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON, start,
                        end);
            int superScript = rt[i].getSuperscript();
            if (superScript != 0)
                at.addAttribute(TextAttribute.SUPERSCRIPT, superScript > 0
                        ? TextAttribute.SUPERSCRIPT_SUPER : TextAttribute.SUPERSCRIPT_SUB, start, end);

        }
        return at;
    }

    public static class TextElement {
        public AttributedString _text;
        public int _textOffset;
        public AttributedString _bullet;
        public int _bulletOffset;
        public int _align;
        public float ascent, descent;
        public float advance;
        public int textStartIndex, textEndIndex;
    }
}
