

package com.document.allreader.allofficefilereader.fc.hwpf.model;

import java.io.IOException;

import com.document.allreader.allofficefilereader.fc.hwpf.model.io.HWPFOutputStream;
import com.document.allreader.allofficefilereader.fc.hwpf.sprm.CharacterSprmUncompressor;
import com.document.allreader.allofficefilereader.fc.hwpf.sprm.ParagraphSprmUncompressor;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.CharacterProperties;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.ParagraphProperties;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;

@Internal
public final class StyleSheet implements HDFType {

  public static final int NIL_STYLE = 4095;
  private static final int PAP_TYPE = 1;
  private static final int CHP_TYPE = 2;
  private static final int SEP_TYPE = 4;
  private static final int TAP_TYPE = 5;


  private final static ParagraphProperties NIL_PAP = new ParagraphProperties();
  private final static CharacterProperties NIL_CHP = new CharacterProperties();

    private int _cbStshi;


    private int _cbSTDBaseInFile;

    private int _flags;


    private int _stiMaxWhenSaved;


    private int _istdMaxFixedWhenSaved;

    private int nVerBuiltInNamesWhenSaved;

    private int[] _rgftcStandardChpStsh;

  StyleDescription[] _styleDescriptions;


  public StyleSheet(byte[] tableStream, int offset)
  {
        int startOffset = offset;
        _cbStshi = LittleEndian.getShort( tableStream, offset );
        offset += LittleEndian.SHORT_SIZE;


        int cstd = LittleEndian.getUShort( tableStream, offset );
        offset += LittleEndian.SHORT_SIZE;

        _cbSTDBaseInFile = LittleEndian.getUShort( tableStream, offset );
        offset += LittleEndian.SHORT_SIZE;

        _flags = LittleEndian.getShort( tableStream, offset );
        offset += LittleEndian.SHORT_SIZE;

        _stiMaxWhenSaved = LittleEndian.getUShort( tableStream, offset );
        offset += LittleEndian.SHORT_SIZE;

        _istdMaxFixedWhenSaved = LittleEndian.getUShort( tableStream, offset );
        offset += LittleEndian.SHORT_SIZE;

        nVerBuiltInNamesWhenSaved = LittleEndian.getUShort( tableStream, offset );
        offset += LittleEndian.SHORT_SIZE;

        _rgftcStandardChpStsh = new int[3];
        _rgftcStandardChpStsh[0] = LittleEndian.getShort( tableStream, offset );
        offset += LittleEndian.SHORT_SIZE;
        _rgftcStandardChpStsh[1] = LittleEndian.getShort( tableStream, offset );
        offset += LittleEndian.SHORT_SIZE;
        _rgftcStandardChpStsh[2] = LittleEndian.getShort( tableStream, offset );
        offset += LittleEndian.SHORT_SIZE;

        
      offset = startOffset + LittleEndian.SHORT_SIZE + _cbStshi;
      _styleDescriptions = new StyleDescription[cstd];
      for(int x = 0; x < cstd; x++)
      {
          int stdSize = LittleEndian.getShort(tableStream, offset);

          offset += 2;
          if(stdSize > 0)
          {


              StyleDescription aStyle = new StyleDescription(tableStream,
                _cbSTDBaseInFile, offset, true);

              _styleDescriptions[x] = aStyle;
          }

          offset += stdSize;

      }
      for(int x = 0; x < _styleDescriptions.length; x++)
      {
          if(_styleDescriptions[x] != null)
          {
              createPap(x);
              createChp(x);
          }
      }
  }

  public void writeTo(HWPFOutputStream out)
    throws IOException
  {

    int offset = 0;

        this._cbStshi = 18;


    byte[] buf = new byte[_cbStshi + 2];

    LittleEndian.putUShort(buf, offset, (short)_cbStshi);
    offset += LittleEndian.SHORT_SIZE;
    LittleEndian.putUShort(buf, offset, (short)_styleDescriptions.length);
    offset += LittleEndian.SHORT_SIZE;
    LittleEndian.putUShort(buf, offset, (short)_cbSTDBaseInFile);
    offset += LittleEndian.SHORT_SIZE;
    LittleEndian.putShort(buf, offset, (short)_flags);
    offset += LittleEndian.SHORT_SIZE;
    LittleEndian.putUShort(buf, offset, (short)_stiMaxWhenSaved);
    offset += LittleEndian.SHORT_SIZE;
    LittleEndian.putUShort(buf, offset, (short)_istdMaxFixedWhenSaved);
    offset += LittleEndian.SHORT_SIZE;
    LittleEndian.putUShort(buf, offset, (short)nVerBuiltInNamesWhenSaved);
    offset += LittleEndian.SHORT_SIZE;

    LittleEndian.putShort(buf, offset, (short)_rgftcStandardChpStsh[0]);
    offset += LittleEndian.SHORT_SIZE;
    LittleEndian.putShort(buf, offset, (short)_rgftcStandardChpStsh[1]);
    offset += LittleEndian.SHORT_SIZE;
    LittleEndian.putShort(buf, offset, (short)_rgftcStandardChpStsh[2]);

    out.write(buf);

    byte[] sizeHolder = new byte[2];
    for (int x = 0; x < _styleDescriptions.length; x++)
    {
      if(_styleDescriptions[x] != null)
      {
          byte[] std = _styleDescriptions[x].toByteArray();

          LittleEndian.putShort(sizeHolder, (short)((std.length) + (std.length % 2)));
          out.write(sizeHolder);
          out.write(std);

          if (std.length % 2 == 1)
          {
            out.write('\0');
          }
      }
      else
      {
        sizeHolder[0] = 0;
        sizeHolder[1] = 0;
        out.write(sizeHolder);
      }
    }
  }
  public boolean equals(Object o)
  {
    StyleSheet ss = (StyleSheet)o;

    if (ss._cbSTDBaseInFile == _cbSTDBaseInFile && ss._flags == _flags &&
        ss._istdMaxFixedWhenSaved ==_istdMaxFixedWhenSaved && ss._stiMaxWhenSaved == _stiMaxWhenSaved &&
        ss._rgftcStandardChpStsh[0] == _rgftcStandardChpStsh[0] && ss._rgftcStandardChpStsh[1] == _rgftcStandardChpStsh[1] &&
        ss._rgftcStandardChpStsh[2] == _rgftcStandardChpStsh[2] && ss._cbStshi == _cbStshi &&
        ss.nVerBuiltInNamesWhenSaved == nVerBuiltInNamesWhenSaved)
    {
      if (ss._styleDescriptions.length == _styleDescriptions.length)
      {
        for (int x = 0; x < _styleDescriptions.length; x++)
        {
          // check for null
          if (ss._styleDescriptions[x] != _styleDescriptions[x])
          {
            // check for equality
            if (!ss._styleDescriptions[x].equals(_styleDescriptions[x]))
            {
              return false;
            }
          }
        }
        return true;
      }
    }
    return false;
  }

  private void createPap(int istd)
  {
      StyleDescription sd = _styleDescriptions[istd];
      ParagraphProperties pap = sd.getPAP();
      byte[] papx = sd.getPAPX();
      int baseIndex = sd.getBaseStyle();
      if(pap == null && papx != null)
      {
          ParagraphProperties parentPAP = new ParagraphProperties();
          if(baseIndex != NIL_STYLE)
          {

              parentPAP = _styleDescriptions[baseIndex].getPAP();
              if(parentPAP == null) {
                  if(baseIndex == istd) {
                      // Oh dear, style claims that it is its own parent
                      throw new IllegalStateException("Pap style " + istd + " claimed to have itself as its parent, which isn't allowed");
                  }
                  // Create the parent style
                  createPap(baseIndex);
                  parentPAP = _styleDescriptions[baseIndex].getPAP();
              }

          }

          if (parentPAP == null) {
              parentPAP = new ParagraphProperties();
          }

          pap = ParagraphSprmUncompressor.uncompressPAP(parentPAP, papx, 2);
          sd.setPAP(pap);
      }
  }

  private void createChp(int istd)
  {
      StyleDescription sd = _styleDescriptions[istd];
      CharacterProperties chp = sd.getCHP();
      byte[] chpx = sd.getCHPX();
      int baseIndex = sd.getBaseStyle();
      
      if(baseIndex == istd) {

         baseIndex = NIL_STYLE;
      }

      if(chp == null && chpx != null)
      {
          CharacterProperties parentCHP = new CharacterProperties();
          if(baseIndex != NIL_STYLE)
          {

              parentCHP = _styleDescriptions[baseIndex].getCHP();
              if(parentCHP == null)
              {
                  createChp(baseIndex);
                  parentCHP = _styleDescriptions[baseIndex].getCHP();
              }

          }

          chp = CharacterSprmUncompressor.uncompressCHP(parentCHP, chpx, 0);
          sd.setCHP(chp);
      }
  }

  public int numStyles() {
      return _styleDescriptions.length;
  }


  public StyleDescription getStyleDescription(int x)
  {
      return _styleDescriptions[x];
  }

  public CharacterProperties getCharacterStyle(int x)
  {
    if (x == NIL_STYLE)
    {
      return NIL_CHP;
    }

    if (x>=_styleDescriptions.length) {
        return NIL_CHP;
    }

    return (_styleDescriptions[x] != null ? _styleDescriptions[x].getCHP() : NIL_CHP);
  }

  public ParagraphProperties getParagraphStyle(int x)
  {
    if (x == NIL_STYLE) {
        return NIL_PAP;
    }

    if (x >= _styleDescriptions.length) {
      return NIL_PAP;
    }

    if (_styleDescriptions[x]==null) {
      return NIL_PAP;
    }

    if (_styleDescriptions[x].getPAP()==null) {
      return NIL_PAP;
    }

    return _styleDescriptions[x].getPAP();
  }
}
