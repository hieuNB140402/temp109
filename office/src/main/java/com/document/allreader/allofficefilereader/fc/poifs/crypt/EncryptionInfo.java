
package com.document.allreader.allofficefilereader.fc.poifs.crypt;


import java.io.IOException;

import com.document.allreader.allofficefilereader.fc.poifs.filesystem.DirectoryNode;
import com.document.allreader.allofficefilereader.fc.poifs.filesystem.DocumentInputStream;
import com.document.allreader.allofficefilereader.fc.poifs.filesystem.NPOIFSFileSystem;
import com.document.allreader.allofficefilereader.fc.poifs.filesystem.POIFSFileSystem;

/**
 *  @author Maxim Valyanskiy
 *  @author Gary King
 */
public class EncryptionInfo {
    private final int versionMajor;
    private final int versionMinor;
    private final int encryptionFlags;

    private final EncryptionHeader header;
    private final EncryptionVerifier verifier;

    public EncryptionInfo(POIFSFileSystem fs) throws IOException {
       this(fs.getRoot());
    }
    public EncryptionInfo(NPOIFSFileSystem fs) throws IOException {
       this(fs.getRoot());
    }
    public EncryptionInfo(DirectoryNode dir) throws IOException {
        DocumentInputStream dis = dir.createDocumentInputStream("EncryptionInfo");
        versionMajor = dis.readShort();
        versionMinor = dis.readShort();

        encryptionFlags = dis.readInt();

        if (versionMajor == 4 && versionMinor == 4 && encryptionFlags == 0x40) {
            StringBuilder builder = new StringBuilder();
            byte[] xmlDescriptor = new byte[dis.available()];
            dis.read(xmlDescriptor);
            for (byte b : xmlDescriptor)
                builder.append((char)b);
            String descriptor = builder.toString();
            header = new EncryptionHeader(descriptor);
            verifier = new EncryptionVerifier(descriptor);
        } else {
            int hSize = dis.readInt();
            header = new EncryptionHeader(dis);
            if (header.getAlgorithm()==EncryptionHeader.ALGORITHM_RC4) {
                verifier = new EncryptionVerifier(dis, 20);
            } else {
                verifier = new EncryptionVerifier(dis, 32);
            }
        }
    }

    public int getVersionMajor() {
        return versionMajor;
    }

    public int getVersionMinor() {
        return versionMinor;
    }

    public int getEncryptionFlags() {
        return encryptionFlags;
    }

    public EncryptionHeader getHeader() {
        return header;
    }

    public EncryptionVerifier getVerifier() {
        return verifier;
    }
}
