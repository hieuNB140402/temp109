

package com.document.allreader.allofficefilereader.fc.hwpf.sprm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.ParagraphProperties;
import com.document.allreader.allofficefilereader.fc.util.Internal;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


@ Internal
public final class ParagraphSprmCompressor
{
    public ParagraphSprmCompressor()
    {
    }

    public static byte[] compressParagraphProperty(ParagraphProperties newPAP,
        ParagraphProperties oldPAP)
    {

        List<byte[]> sprmList = new ArrayList<byte[]>();
        int size = 0;

        if (newPAP.getIstd() != oldPAP.getIstd())
        {
            size += SprmUtils.addSprm((short)0x4600, newPAP.getIstd(), null, sprmList);
        }
        if (newPAP.getJc() != oldPAP.getJc())
        {

            size += SprmUtils.addSprm((short)0x2403, newPAP.getJc(), null, sprmList);
        }
        if (newPAP.getFSideBySide() != oldPAP.getFSideBySide())
        {

            size += SprmUtils.addSprm((short)0x2404, newPAP.getFSideBySide(), sprmList);
        }
        if (newPAP.getFKeep() != oldPAP.getFKeep())
        {
            size += SprmUtils.addSprm((short)0x2405, newPAP.getFKeep(), sprmList);
        }
        if (newPAP.getFKeepFollow() != oldPAP.getFKeepFollow())
        {
            size += SprmUtils.addSprm((short)0x2406, newPAP.getFKeepFollow(), sprmList);
        }
        if (newPAP.getFPageBreakBefore() != oldPAP.getFPageBreakBefore())
        {
            size += SprmUtils.addSprm((short)0x2407, newPAP.getFPageBreakBefore(), sprmList);
        }
        if (newPAP.getBrcl() != oldPAP.getBrcl())
        {
            size += SprmUtils.addSprm((short)0x2408, newPAP.getBrcl(), null, sprmList);
        }
        if (newPAP.getBrcp() != oldPAP.getBrcp())
        {
            size += SprmUtils.addSprm((short)0x2409, newPAP.getBrcp(), null, sprmList);
        }
        if (newPAP.getIlvl() != oldPAP.getIlvl())
        {
            size += SprmUtils.addSprm((short)0x260A, newPAP.getIlvl(), null, sprmList);
        }
        if (newPAP.getIlfo() != oldPAP.getIlfo())
        {
            size += SprmUtils.addSprm((short)0x460b, newPAP.getIlfo(), null, sprmList);
        }
        if (newPAP.getFNoLnn() != oldPAP.getFNoLnn())
        {
            size += SprmUtils.addSprm((short)0x240C, newPAP.getFNoLnn(), sprmList);
        }
        if (newPAP.getItbdMac() != oldPAP.getItbdMac()
            || !Arrays.equals(newPAP.getRgdxaTab(), oldPAP.getRgdxaTab())
            || !Arrays.equals(newPAP.getRgtbd(), oldPAP.getRgtbd()))
        {
                  }
        if (newPAP.getDxaLeft() != oldPAP.getDxaLeft())
        {
            size += SprmUtils.addSprm((short)0x840F, newPAP.getDxaLeft(), null, sprmList);
        }


        if (newPAP.getDxaLeft1() != oldPAP.getDxaLeft1())
        {

            size += SprmUtils.addSprm((short)0x8411, newPAP.getDxaLeft1(), null, sprmList);
        }
        if (newPAP.getDxaRight() != oldPAP.getDxaRight())
        {

            size += SprmUtils.addSprm((short)0x840E, newPAP.getDxaRight(), null, sprmList);
        }
        if (newPAP.getDxcLeft() != oldPAP.getDxcLeft())
        {

            size += SprmUtils.addSprm((short)0x4456, newPAP.getDxcLeft(), null, sprmList);
        }
        if (newPAP.getDxcLeft1() != oldPAP.getDxcLeft1())
        {
            size += SprmUtils.addSprm((short)0x4457, newPAP.getDxcLeft1(), null, sprmList);
        }
        if (newPAP.getDxcRight() != oldPAP.getDxcRight())
        {
            size += SprmUtils.addSprm((short)0x4455, newPAP.getDxcRight(), null, sprmList);
        }
        if (!newPAP.getLspd().equals(oldPAP.getLspd()))
        {
            byte[] buf = new byte[4];
            newPAP.getLspd().serialize(buf, 0);

            size += SprmUtils.addSprm((short)0x6412, LittleEndian.getInt(buf), null, sprmList);
        }
        if (newPAP.getDyaBefore() != oldPAP.getDyaBefore())
        {
            size += SprmUtils.addSprm((short)0xA413, newPAP.getDyaBefore(), null, sprmList);
        }
        if (newPAP.getDyaAfter() != oldPAP.getDyaAfter())
        {
            size += SprmUtils.addSprm((short)0xA414, newPAP.getDyaAfter(), null, sprmList);
        }
        if (newPAP.getFDyaBeforeAuto() != oldPAP.getFDyaBeforeAuto())
        {
            size += SprmUtils.addSprm((short)0x245B, newPAP.getFDyaBeforeAuto(), sprmList);
        }
        if (newPAP.getFDyaAfterAuto() != oldPAP.getFDyaAfterAuto())
        {
            size += SprmUtils.addSprm((short)0x245C, newPAP.getFDyaAfterAuto(), sprmList);
        }
        if (newPAP.getFInTable() != oldPAP.getFInTable())
        {
            size += SprmUtils.addSprm((short)0x2416, newPAP.getFInTable(), sprmList);
        }
        if (newPAP.getFTtp() != oldPAP.getFTtp())
        {
            size += SprmUtils.addSprm((short)0x2417, newPAP.getFTtp(), sprmList);
        }
        if (newPAP.getDxaAbs() != oldPAP.getDxaAbs())
        {
            size += SprmUtils.addSprm((short)0x8418, newPAP.getDxaAbs(), null, sprmList);
        }
        if (newPAP.getDyaAbs() != oldPAP.getDyaAbs())
        {
            size += SprmUtils.addSprm((short)0x8419, newPAP.getDyaAbs(), null, sprmList);
        }
        if (newPAP.getDxaWidth() != oldPAP.getDxaWidth())
        {
            size += SprmUtils.addSprm((short)0x841A, newPAP.getDxaWidth(), null, sprmList);
        }

        if (newPAP.getWr() != oldPAP.getWr())
        {
            size += SprmUtils.addSprm((short)0x2423, newPAP.getWr(), null, sprmList);
        }

        if (newPAP.getBrcBar().equals(oldPAP.getBrcBar()))
        {
           int brc = newPAP.getBrcBar().toInt();
            size += SprmUtils.addSprm((short)0x6428, brc, null, sprmList);
        }
        if (!newPAP.getBrcBottom().equals(oldPAP.getBrcBottom()))
        {

            int brc = newPAP.getBrcBottom().toInt();
            size += SprmUtils.addSprm((short)0x6426, brc, null, sprmList);
        }
        if (!newPAP.getBrcLeft().equals(oldPAP.getBrcLeft()))
        {

            int brc = newPAP.getBrcLeft().toInt();
            size += SprmUtils.addSprm((short)0x6425, brc, null, sprmList);
        }

        if (!newPAP.getBrcRight().equals(oldPAP.getBrcRight()))
        {
            int brc = newPAP.getBrcRight().toInt();
            size += SprmUtils.addSprm((short)0x6427, brc, null, sprmList);
        }
        if (!newPAP.getBrcTop().equals(oldPAP.getBrcTop()))
        {
            int brc = newPAP.getBrcTop().toInt();
            size += SprmUtils.addSprm((short)0x6424, brc, null, sprmList);
        }
        if (newPAP.getFNoAutoHyph() != oldPAP.getFNoAutoHyph())
        {
            size += SprmUtils.addSprm((short)0x242A, newPAP.getFNoAutoHyph(), sprmList);
        }
        if (newPAP.getDyaHeight() != oldPAP.getDyaHeight()
            || newPAP.getFMinHeight() != oldPAP.getFMinHeight())
        {
            short val = (short)newPAP.getDyaHeight();
            if (newPAP.getFMinHeight())
            {
                val |= 0x8000;
            }
            size += SprmUtils.addSprm((short)0x442B, val, null, sprmList);
        }
        if (newPAP.getDcs() != null && !newPAP.getDcs().equals(oldPAP.getDcs()))
        {
            size += SprmUtils.addSprm((short)0x442C, newPAP.getDcs().toShort(), null, sprmList);
        }
        if (newPAP.getShd() != null && !newPAP.getShd().equals(oldPAP.getShd()))
        {

            size += SprmUtils.addSprm((short)0x442D, newPAP.getShd().toShort(), null, sprmList);
        }
        if (newPAP.getDyaFromText() != oldPAP.getDyaFromText())
        {

            size += SprmUtils.addSprm((short)0x842E, newPAP.getDyaFromText(), null, sprmList);
        }
        if (newPAP.getDxaFromText() != oldPAP.getDxaFromText())
        {

            size += SprmUtils.addSprm((short)0x842F, newPAP.getDxaFromText(), null, sprmList);
        }
        if (newPAP.getFLocked() != oldPAP.getFLocked())
        {

            size += SprmUtils.addSprm((short)0x2430, newPAP.getFLocked(), sprmList);
        }
        if (newPAP.getFWidowControl() != oldPAP.getFWidowControl())
        {

            size += SprmUtils.addSprm((short)0x2431, newPAP.getFWidowControl(), sprmList);
        }
        if (newPAP.getFKinsoku() != oldPAP.getFKinsoku())
        {
            size += SprmUtils.addSprm((short)0x2433, newPAP.getDyaBefore(), null, sprmList);
        }
        if (newPAP.getFWordWrap() != oldPAP.getFWordWrap())
        {
            size += SprmUtils.addSprm((short)0x2434, newPAP.getFWordWrap(), sprmList);
        }
        if (newPAP.getFOverflowPunct() != oldPAP.getFOverflowPunct())
        {
            size += SprmUtils.addSprm((short)0x2435, newPAP.getFOverflowPunct(), sprmList);
        }
        if (newPAP.getFTopLinePunct() != oldPAP.getFTopLinePunct())
        {
            size += SprmUtils.addSprm((short)0x2436, newPAP.getFTopLinePunct(), sprmList);
        }
        if (newPAP.getFAutoSpaceDE() != oldPAP.getFAutoSpaceDE())
        {
            size += SprmUtils.addSprm((short)0x2437, newPAP.getFAutoSpaceDE(), sprmList);
        }
        if (newPAP.getFAutoSpaceDN() != oldPAP.getFAutoSpaceDN())
        {
            size += SprmUtils.addSprm((short)0x2438, newPAP.getFAutoSpaceDN(), sprmList);
        }
        if (newPAP.getWAlignFont() != oldPAP.getWAlignFont())
        {
            size += SprmUtils.addSprm((short)0x4439, newPAP.getWAlignFont(), null, sprmList);
        }


        if (newPAP.isFBackward() != oldPAP.isFBackward()
            || newPAP.isFVertical() != oldPAP.isFVertical()
            || newPAP.isFRotateFont() != oldPAP.isFRotateFont())
        {
            int val = 0;
            if (newPAP.isFBackward())
            {
                val |= 0x2;
            }
            if (newPAP.isFVertical())
            {
                val |= 0x1;
            }
            if (newPAP.isFRotateFont())
            {
                val |= 0x4;
            }
            size += SprmUtils.addSprm((short)0x443A, val, null, sprmList);
        }
        if (!Arrays.equals(newPAP.getAnld(), oldPAP.getAnld()))
        {
            size += SprmUtils.addSprm((short)0xC63E, 0, newPAP.getAnld(), sprmList);
        }
        if (newPAP.getFPropRMark() != oldPAP.getFPropRMark()
            || newPAP.getIbstPropRMark() != oldPAP.getIbstPropRMark()
            || !newPAP.getDttmPropRMark().equals(oldPAP.getDttmPropRMark()))
        {
            byte[] buf = new byte[7];
            buf[0] = (byte)(newPAP.getFPropRMark() ? 1 : 0);
            LittleEndian.putShort(buf, 1, (short)newPAP.getIbstPropRMark());
            newPAP.getDttmPropRMark().serialize(buf, 3);
            size += SprmUtils.addSprm((short)0xC63F, 0, buf, sprmList);
        }
        if (newPAP.getLvl() != oldPAP.getLvl())
        {
            size += SprmUtils.addSprm((short)0x2640, newPAP.getLvl(), null, sprmList);
        }
        if (newPAP.getFBiDi() != oldPAP.getFBiDi())
        {
            size += SprmUtils.addSprm((short)0x2441, newPAP.getFBiDi(), sprmList);
        }
        if (newPAP.getFNumRMIns() != oldPAP.getFNumRMIns())
        {
            size += SprmUtils.addSprm((short)0x2443, newPAP.getFNumRMIns(), sprmList);
        }
        if (!Arrays.equals(newPAP.getNumrm(), oldPAP.getNumrm()))
        {
            size += SprmUtils.addSprm((short)0xC645, 0, newPAP.getNumrm(), sprmList);
        }
        if (newPAP.getFInnerTableCell() != oldPAP.getFInnerTableCell())
        {

            size += SprmUtils.addSprm((short)0x244b, newPAP.getFInnerTableCell(), sprmList);
        }
        if (newPAP.getFTtpEmbedded() != oldPAP.getFTtpEmbedded())
        {

            size += SprmUtils.addSprm((short)0x244c, newPAP.getFTtpEmbedded(), sprmList);
        }


        if (newPAP.getItap() != oldPAP.getItap())
        {
            size += SprmUtils.addSprm((short)0x6649, newPAP.getItap(), null, sprmList);
        }

        return SprmUtils.getGrpprl(sprmList, size);

    }
}
