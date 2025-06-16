

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import com.document.allreader.allofficefilereader.fc.hwpf.model.PAPX;
import com.document.allreader.allofficefilereader.fc.hwpf.sprm.SprmBuffer;
import com.document.allreader.allofficefilereader.fc.hwpf.sprm.TableSprmCompressor;


public class Paragraph extends Range implements Cloneable
{
  public final static short SPRM_JC = 0x2403;
  public final static short SPRM_FSIDEBYSIDE = 0x2404;
  public final static short SPRM_FKEEP = 0x2405;
  public final static short SPRM_FKEEPFOLLOW = 0x2406;
  public final static short SPRM_FPAGEBREAKBEFORE = 0x2407;
  public final static short SPRM_FNOLINENUMB = 0x240C;
  public final static short SPRM_DXARIGHT = (short)0x840E;
  public final static short SPRM_DXALEFT = (short)0x840F;
  public final static short SPRM_DXALEFT1 = (short)0x8411;
  public final static short SPRM_DYALINE = 0x6412;
  public final static short SPRM_DYABEFORE = (short)0xA413;
  public final static short SPRM_DYAAFTER = (short)0xA414;

  public final static short SPRM_FTTP = 0x2417;

  public final static short SPRM_BRCTOP = 0x6424;
  public final static short SPRM_BRCLEFT = 0x6425;
  public final static short SPRM_BRCBOTTOM = 0x6426;
  public final static short SPRM_BRCRIGHT = 0x6427;
  public final static short SPRM_BRCBAR = 0x6629;
  public final static short SPRM_FNOAUTOHYPH = 0x242A;
  public final static short SPRM_DCS = 0x442C;
  public final static short SPRM_SHD = 0x442D;
  public final static short SPRM_FWIDOWCONTROL = 0x2431;
  public final static short SPRM_FKINSOKU = 0x2433;
  public final static short SPRM_FWORDWRAP = 0x2434;
  public final static short SPRM_WALIGNFONT = 0x4439;
  public final static short SPRM_FRAMETEXTFLOW = 0x443A;


  protected short _istd;
  protected ParagraphProperties _props;
  protected SprmBuffer _papx;

    protected Paragraph( int startIdxInclusive, int endIdxExclusive,
            Table parent )
    {
        super( startIdxInclusive, endIdxExclusive, parent );

        initAll();
        PAPX papx = _paragraphs.get( _parEnd - 1 );
        _props = papx.getParagraphProperties( _doc.getStyleSheet() );
        _papx = papx.getSprmBuf();
        _istd = papx.getIstd();
    }

  protected Paragraph(PAPX papx, Range parent)
  {
    super(Math.max(parent._start, papx.getStart()), Math.min(parent._end, papx.getEnd()), parent);
    _props = papx.getParagraphProperties(_doc.getStyleSheet());
    _papx = papx.getSprmBuf();
    _istd = papx.getIstd();
  }

  protected Paragraph(PAPX papx, Range parent, int start)
  {
    super(Math.max(parent._start, start), Math.min(parent._end, papx.getEnd()), parent);
    _props = papx.getParagraphProperties(_doc.getStyleSheet());
    _papx = papx.getSprmBuf();
    _istd = papx.getIstd();
  }

  public short getStyleIndex()
  {
    return _istd;
  }

  public int type()
  {
    return TYPE_PARAGRAPH;
  }

  public boolean isInTable()
  {
    return _props.getFInTable();
  }


  public boolean isTableRowEnd()
  {
    return _props.getFTtp() || _props.getFTtpEmbedded();
  }

  public int getTableLevel()
  {
    return _props.getItap();
  }


    public boolean isEmbeddedCellMark()
    {
        return _props.getFInnerTableCell();
    }

  public int getJustification()
  {
    return _props.getJc();
  }

  public void setJustification(byte jc)
  {
    _props.setJc(jc);
    _papx.updateSprm(SPRM_JC, jc);
  }

  public boolean keepOnPage()
  {
    return _props.getFKeep();
  }

  public void setKeepOnPage(boolean fKeep)
  {
    _props.setFKeep(fKeep);
    _papx.updateSprm(SPRM_FKEEP, fKeep);
  }

  public boolean keepWithNext()
  {
    return _props.getFKeepFollow();
  }

  public void setKeepWithNext(boolean fKeepFollow)
  {
    _props.setFKeepFollow(fKeepFollow);
    _papx.updateSprm(SPRM_FKEEPFOLLOW, fKeepFollow);
  }

  public boolean pageBreakBefore()
  {
    return _props.getFPageBreakBefore();
  }

  public void setPageBreakBefore(boolean fPageBreak)
  {
    _props.setFPageBreakBefore(fPageBreak);
    _papx.updateSprm(SPRM_FPAGEBREAKBEFORE, fPageBreak);
  }

  public boolean isLineNotNumbered()
  {
    return _props.getFNoLnn();
  }

  public void setLineNotNumbered(boolean fNoLnn)
  {
    _props.setFNoLnn(fNoLnn);
    _papx.updateSprm(SPRM_FNOLINENUMB, fNoLnn);
  }

  public boolean isSideBySide()
  {
    return _props.getFSideBySide();
  }

  public void setSideBySide(boolean fSideBySide)
  {
    _props.setFSideBySide(fSideBySide);
    _papx.updateSprm(SPRM_FSIDEBYSIDE, fSideBySide);
  }

  public boolean isAutoHyphenated()
  {
    return !_props.getFNoAutoHyph();
  }

  public void setAutoHyphenated(boolean autoHyph)
  {
    _props.setFNoAutoHyph(!autoHyph);
    _papx.updateSprm(SPRM_FNOAUTOHYPH, !autoHyph);
  }

  public boolean isWidowControlled()
  {
    return _props.getFWidowControl();
  }

  public void setWidowControl(boolean widowControl)
  {
    _props.setFWidowControl(widowControl);
    _papx.updateSprm(SPRM_FWIDOWCONTROL, widowControl);
  }

  public int getIndentFromRight()
  {
    return _props.getDxaRight();
  }

  public void setIndentFromRight(int dxaRight)
  {
    _props.setDxaRight(dxaRight);
    _papx.updateSprm(SPRM_DXARIGHT, (short)dxaRight);
  }

  public int getIndentFromLeft()
  {
    return _props.getDxaLeft();
  }

  public void setIndentFromLeft(int dxaLeft)
  {
    _props.setDxaLeft(dxaLeft);
    _papx.updateSprm(SPRM_DXALEFT, (short)dxaLeft);
  }

  public int getFirstLineIndent()
  {
    return _props.getDxaLeft1();
  }

  public void setFirstLineIndent(int first)
  {
    _props.setDxaLeft1(first);
    _papx.updateSprm(SPRM_DXALEFT1, (short)first);
  }

  public LineSpacingDescriptor getLineSpacing()
  {
    return _props.getLspd();
  }

  public void setLineSpacing(LineSpacingDescriptor lspd)
  {
    _props.setLspd(lspd);
    _papx.updateSprm(SPRM_DYALINE, lspd.toInt());
  }

  public int getSpacingBefore()
  {
    return _props.getDyaBefore();
  }

  public void setSpacingBefore(int before)
  {
    _props.setDyaBefore(before);
    _papx.updateSprm(SPRM_DYABEFORE, (short)before);
  }

  public int getSpacingAfter()
  {
    return _props.getDyaAfter();
  }

  public void setSpacingAfter(int after)
  {
    _props.setDyaAfter(after);
    _papx.updateSprm(SPRM_DYAAFTER, (short)after);
  }

  public boolean isKinsoku()
  {
    return _props.getFKinsoku();
  }

  public void setKinsoku(boolean kinsoku)
  {
    _props.setFKinsoku(kinsoku);
    _papx.updateSprm(SPRM_FKINSOKU, kinsoku);
  }

  public boolean isWordWrapped()
  {
    return _props.getFWordWrap();
  }

  public void setWordWrapped(boolean wrap)
  {
    _props.setFWordWrap(wrap);
    _papx.updateSprm(SPRM_FWORDWRAP, wrap);
  }

  public int getFontAlignment()
  {
    return _props.getWAlignFont();
  }

  public void setFontAlignment(int align)
  {
    _props.setWAlignFont(align);
    _papx.updateSprm(SPRM_WALIGNFONT, (short)align);
  }

  public boolean isVertical()
  {
    return _props.isFVertical();
  }

  public void setVertical(boolean vertical)
  {
    _props.setFVertical(vertical);
    _papx.updateSprm(SPRM_FRAMETEXTFLOW, getFrameTextFlow());
  }

  public boolean isBackward()
  {
    return _props.isFBackward();
  }

  public void setBackward(boolean bward)
  {
    _props.setFBackward(bward);
    _papx.updateSprm(SPRM_FRAMETEXTFLOW, getFrameTextFlow());
  }

  public BorderCode getTopBorder()
  {
    return _props.getBrcTop();
  }

  public void setTopBorder(BorderCode top)
  {
    _props.setBrcTop(top);
    _papx.updateSprm(SPRM_BRCTOP, top.toInt());
  }

  public BorderCode getLeftBorder()
  {
    return _props.getBrcLeft();
  }

  public void setLeftBorder(BorderCode left)
  {
    _props.setBrcLeft(left);
    _papx.updateSprm(SPRM_BRCLEFT, left.toInt());
  }

  public BorderCode getBottomBorder()
  {
    return _props.getBrcBottom();
  }

  public void setBottomBorder(BorderCode bottom)
  {
    _props.setBrcBottom(bottom);
    _papx.updateSprm(SPRM_BRCBOTTOM, bottom.toInt());
  }

  public BorderCode getRightBorder()
  {
    return _props.getBrcRight();
  }

  public void setRightBorder(BorderCode right)
  {
    _props.setBrcRight(right);
    _papx.updateSprm(SPRM_BRCRIGHT, right.toInt());
  }

  public BorderCode getBarBorder()
  {
    return _props.getBrcBar();
  }

  public void setBarBorder(BorderCode bar)
  {
    _props.setBrcBar(bar);
    _papx.updateSprm(SPRM_BRCBAR, bar.toInt());
  }

  public ShadingDescriptor getShading()
  {
    return _props.getShd();
  }

  public void setShading(ShadingDescriptor shd)
  {
    _props.setShd(shd);
    _papx.updateSprm(SPRM_SHD, shd.toShort());
  }

  public DropCapSpecifier getDropCap()
  {
    return _props.getDcs();
  }

  public void setDropCap(DropCapSpecifier dcs)
  {
    _props.setDcs(dcs);
    _papx.updateSprm(SPRM_DCS, dcs.toShort());
  }


  public int getIlfo()
  {
     return _props.getIlfo();
  }



  public int getIlvl()
  {
     return _props.getIlvl();
  }


  public int getLvl()
  {
     return _props.getLvl();
  }

  void setTableRowEnd(TableProperties props)
  {
    setTableRowEnd(true);
    byte[] grpprl = TableSprmCompressor.compressTableProperty(props);
    _papx.append(grpprl);
  }

  private void setTableRowEnd(boolean val)
  {
    _props.setFTtp(val);
    _papx.updateSprm(SPRM_FTTP, val);
  }

  public short getTabClearPosition()
  {
      return _props.getTabClearPosition();
  }

  public ParagraphProperties cloneProperties() {
    try {
       return (ParagraphProperties)_props.clone();
    } catch (Exception e) {
       throw new RuntimeException(e);
    }
  }

  public Object clone()
    throws CloneNotSupportedException
  {
    Paragraph p = (Paragraph)super.clone();
    p._props = (ParagraphProperties)_props.clone();
    //p._baseStyle = _baseStyle;
    p._papx = new SprmBuffer(0);
    return p;
  }

  private short getFrameTextFlow()
  {
    short retVal = 0;
    if (_props.isFVertical())
    {
      retVal |= 1;
    }
    if (_props.isFBackward())
    {
      retVal |= 2;
    }
    if (_props.isFRotateFont())
    {
      retVal |= 4;
    }
    return retVal;
  }

    @Override
    public String toString()
    {
        return "Paragraph [" + getStartOffset() + "; " + getEndOffset() + ")";
    }
}
