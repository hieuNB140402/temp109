

package com.document.allreader.allofficefilereader.fc.hslf.record;

import com.document.allreader.allofficefilereader.fc.fs.filesystem.CFBFileSystem;
import com.document.allreader.allofficefilereader.fc.hslf.exceptions.CorruptPowerPointFileException;
import com.document.allreader.allofficefilereader.fc.hslf.exceptions.EncryptedPowerPointFileException;
import com.document.allreader.allofficefilereader.fc.hslf.exceptions.OldPowerPointFormatException;
import com.document.allreader.allofficefilereader.fc.hwpf.OldWordFileFormatException;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;
import com.document.allreader.allofficefilereader.fc.util.StringUtil;

import java.io.IOException;


public class CurrentUserAtom {

    public static final byte[] atomHeader = new byte[]{0, 0, -10, 15};
    public static final byte[] headerToken = new byte[]{95, -64, -111, -29};
    public static final byte[] encHeaderToken = new byte[]{-33, -60, -47, -13};
    public static final byte[] ppt97FileVer = new byte[]{8, 00, -13, 03, 03, 00};


    private int docFinalVersion;
    private byte docMajorNo;
    private byte docMinorNo;

    private long currentEditOffset;
    private String lastEditUser;
    private long releaseVersion;

    private byte[] _contents;


    public int getDocFinalVersion() {
        return docFinalVersion;
    }

    public byte getDocMajorNo() {
        return docMajorNo;
    }

    public byte getDocMinorNo() {
        return docMinorNo;
    }

    public long getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(long rv) {
        releaseVersion = rv;
    }

    public long getCurrentEditOffset() {
        return currentEditOffset;
    }

    public void setCurrentEditOffset(long id) {
        currentEditOffset = id;
    }

    public String getLastEditUsername() {
        return lastEditUser;
    }

    public void setLastEditUsername(String u) {
        lastEditUser = u;
    }


    public CurrentUserAtom() {
        _contents = new byte[0];


        docFinalVersion = 0x03f4;
        docMajorNo = 3;
        docMinorNo = 0;
        releaseVersion = 8;
        currentEditOffset = 0;
        lastEditUser = "Apache POI";
    }


    public CurrentUserAtom(CFBFileSystem cfbFS) throws IOException {

        _contents = cfbFS.getPropertyRawData("Current User");

        if (_contents == null || _contents.length > 131072) {
            throw new CorruptPowerPointFileException(
                    "The Current User stream is implausably long. It's normally 28-200 bytes long, but was "
                            + _contents.length + " bytes");
        }


        if (_contents.length < 28) {
            if (_contents.length >= 4) {

                int size = LittleEndian.getInt(_contents);
                System.err.println(size);
                if (size + 4 == _contents.length) {
                    throw new OldPowerPointFormatException(
                            "Based on the Current User stream, you seem to have supplied a PowerPoint95 file, which isn't supported");
                }
            }
            throw new CorruptPowerPointFileException(
                    "The Current User stream must be at least 28 bytes long, but was only "
                            + _contents.length);
        }


        init();
    }


    private void init() {

        if (_contents[12] == encHeaderToken[0] && _contents[13] == encHeaderToken[1]
                && _contents[14] == encHeaderToken[2] && _contents[15] == encHeaderToken[3]) {
            throw new EncryptedPowerPointFileException(
                    "Cannot process encrypted allofficefilereader files!");
        }

        currentEditOffset = LittleEndian.getUInt(_contents, 16);

        docFinalVersion = LittleEndian.getUShort(_contents, 22);
        docMajorNo = _contents[24];
        docMinorNo = _contents[25];

        long usernameLen = LittleEndian.getUShort(_contents, 20);
        if (usernameLen > 512) {
            System.err.println("Warning - invalid username length " + usernameLen
                    + " found, treating as if there was no username set");
            usernameLen = 0;
        }

        if (_contents.length >= 28 + (int) usernameLen + 4) {
            releaseVersion = LittleEndian.getUInt(_contents, 28 + (int) usernameLen);
            if (releaseVersion == 0) {
                throw new OldWordFileFormatException(
                        "The document is too old - Word 95 or older. Try HWPFOldDocument instead?");
            }
        } else {

            releaseVersion = 0;
        }

        int start = 28 + (int) usernameLen + 4;
        int len = 2 * (int) usernameLen;

        if (_contents.length >= start + len) {
            byte[] textBytes = new byte[len];
            System.arraycopy(_contents, start, textBytes, 0, len);
            lastEditUser = StringUtil.getFromUnicodeLE(textBytes);
        } else {
            byte[] textBytes = new byte[(int) usernameLen];
            System.arraycopy(_contents, 28, textBytes, 0, (int) usernameLen);
            lastEditUser = StringUtil.getFromCompressedUnicode(textBytes, 0, (int) usernameLen);
        }
    }


    public void dispose() {
        _contents = null;
        lastEditUser = null;
    }


}
