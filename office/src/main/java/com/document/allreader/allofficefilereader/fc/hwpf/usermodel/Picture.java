

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.InflaterInputStream;

import com.document.allreader.allofficefilereader.fc.hwpf.model.PictureDescriptor;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public final class Picture extends PictureDescriptor {
    private static final int PRELOADDATA_LENGTH = 128;


    static final int PICF_OFFSET = 0x0;
    static final int PICT_HEADER_OFFSET = 0x4;
    static final int MFPMM_OFFSET = 0x6;
    static final int PICF_SHAPE_OFFSET = 0xE;
    static final int UNKNOWN_HEADER_SIZE = 0x49;

    @Deprecated
    public static final byte[] GIF = PictureType.GIF.getSignatures()[0];
    @Deprecated
    public static final byte[] PNG = PictureType.PNG.getSignatures()[0];
    @Deprecated
    public static final byte[] JPG = PictureType.JPEG.getSignatures()[0];
    @Deprecated
    public static final byte[] BMP = PictureType.BMP.getSignatures()[0];
    @Deprecated
    public static final byte[] TIFF = PictureType.TIFF.getSignatures()[0];
    @Deprecated
    public static final byte[] TIFF1 = PictureType.TIFF.getSignatures()[1];

    @Deprecated
    public static final byte[] EMF = PictureType.EMF.getSignatures()[0];
    @Deprecated
    public static final byte[] WMF1 = PictureType.WMF.getSignatures()[0];
    // Windows 3.x
    @Deprecated
    public static final byte[] WMF2 = PictureType.WMF.getSignatures()[1];
    // TODO: DIB, PICT

    public static final byte[] IHDR = new byte[]{'I', 'H', 'D', 'R'};

    public static final byte[] COMPRESSED1 = {(byte) 0xFE, 0x78, (byte) 0xDA};
    public static final byte[] COMPRESSED2 = {(byte) 0xFE, 0x78, (byte) 0x9C};

    private int dataBlockStartOfsset;
    private int pictureBytesStartOffset;
    private int dataBlockSize;
    private int size;
    private byte[] preloadRawContent;
    private byte[] rawContent;
    private byte[] content;
    private byte[] _dataStream;
    private int height = -1;
    private int width = -1;


    private String tempPath;

    public Picture(String tempPath, int dataBlockStartOfsset, byte[] _dataStream, boolean fillBytes) throws Exception {
        super(_dataStream, dataBlockStartOfsset);

        this._dataStream = _dataStream;
        this.dataBlockStartOfsset = dataBlockStartOfsset;
        this.dataBlockSize = LittleEndian.getInt(_dataStream, dataBlockStartOfsset);
        this.pictureBytesStartOffset = getPictureBytesStartOffset(dataBlockStartOfsset,
                _dataStream, dataBlockSize);
        this.size = dataBlockSize - (pictureBytesStartOffset - dataBlockStartOfsset);

        if (size < 0) {
            throw new Exception("picture size is wrong");
        }

        if (fillBytes) {
            fillImageContent();
        }
        this.tempPath = tempPath;
    }

    public Picture(byte[] _dataStream) {
        super();

        this._dataStream = _dataStream;
        this.dataBlockStartOfsset = 0;
        this.dataBlockSize = _dataStream.length;
        this.pictureBytesStartOffset = 0;
        this.size = _dataStream.length;
    }

    private void fillWidthHeight() {
        PictureType pictureType = suggestPictureType();
        // trying to extract width and height from pictures content:
        switch (pictureType) {
            case JPEG:
                fillJPGWidthHeight();
                break;
            case PNG:
                fillPNGWidthHeight();
                break;
            default:
                // unsupported;
                break;
        }
    }


    public String suggestFullFileName() {
        String fileExt = suggestFileExtension();
        return Integer.toHexString(dataBlockStartOfsset)
                + (fileExt.length() > 0 ? "." + fileExt : "");
    }


    public int getStartOffset() {
        return dataBlockStartOfsset;
    }


    public byte[] getContent() {
        fillImageContent();
        return content;
    }


    public byte[] getPreLoadRawContent() {
        fillPreloadRawImageContent();
        return preloadRawContent;
    }


    public byte[] getRawContent() {
        fillRawImageContent();
        return rawContent;
    }


    public int getSize() {
        return size;
    }


    @Deprecated
    public int getAspectRatioX() {
        return mx / 10;
    }


    public int getHorizontalScalingFactor() {
        return mx;
    }


    @Deprecated
    public int getAspectRatioY() {
        return my / 10;
    }


    public int getVerticalScalingFactor() {
        return my;
    }


    public int getDxaGoal() {
        return dxaGoal;
    }


    public int getDyaGoal() {
        return dyaGoal;
    }


    public float getDxaCropLeft() {
        return dxaCropLeft;
    }


    public float getDyaCropTop() {
        return dyaCropTop;
    }

    public float getDxaCropRight() {
        return dxaCropRight;
    }

    public float getDyaCropBottom() {
        return dyaCropBottom;
    }

    public String suggestFileExtension() {
        return suggestPictureType().getExtension();
    }


    public String getMimeType() {
        return suggestPictureType().getMime();
    }

    public PictureType suggestPictureType() {
        return PictureType.findMatchingType(getContent());
    }


    private void fillPreloadRawImageContent() {
        if (preloadRawContent != null && preloadRawContent.length > 0)
            return;

        this.preloadRawContent = new byte[Math.min(size, PRELOADDATA_LENGTH)];
        try {
            System.arraycopy(_dataStream, pictureBytesStartOffset, preloadRawContent, 0, preloadRawContent.length);
        } catch (Exception e) {

        }
    }

    private void fillRawImageContent() {
        if (rawContent != null && rawContent.length > 0)
            return;
        this.rawContent = new byte[size];
        try {
            System.arraycopy(_dataStream, pictureBytesStartOffset, rawContent, 0,
                    size);
        } catch (Exception e) {

        }
    }

    private void fillImageContent() {
        if (content != null && content.length > 0)
            return;

        byte[] rawContent = getPreLoadRawContent();
        content = rawContent;

        int offset = pictureBytesStartOffset;
        int len = size;
        if (matchSignature(rawContent, COMPRESSED1, 32)
                || matchSignature(rawContent, COMPRESSED2, 32)) {
            try {
                InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(
                        _dataStream, pictureBytesStartOffset + 33, size - 33));

                this.tempFilePath = tempPath + File.separator + String.valueOf(System.currentTimeMillis()) + ".tmp";
                File file = new File(tempFilePath);
                file.createNewFile();

                FileOutputStream out = new FileOutputStream(file);
                byte[] buf = new byte[4096];
                int readBytes;
                boolean flag = false;
                while ((readBytes = in.read(buf)) > 0) {
                    if (!flag) {
                        flag = true;
                        content = new byte[readBytes];
                        System.arraycopy(buf, 0, content, 0, readBytes);
                    }
                    out.write(buf, 0, readBytes);
                }
                out.close();

            } catch (Exception e) {
            }
        } else {

            try {
                InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(
                        _dataStream, pictureBytesStartOffset + 33, size - 33));
                byte[] b = new byte[128];
                in.read(b);
                String str = PictureType.findMatchingType(b).getExtension();
                if ("wmf".equalsIgnoreCase(str)
                        || "emf".equalsIgnoreCase(str)) {
                    content = b;
                    File file = new File(tempPath + File.separator + System.currentTimeMillis() + ".tmp");
                    file.createNewFile();

                    FileOutputStream out = new FileOutputStream(file);
                    out.write(b);
                    byte[] buf = new byte[4096];
                    int readBytes;
                    while ((readBytes = in.read(buf)) > 0) {
                        out.write(buf, 0, readBytes);
                    }
                    out.close();
                    this.tempFilePath = file.getAbsolutePath();
                } else {
                    this.tempFilePath = writeTempFile(_dataStream, offset, len);
                }
                in.close();
            } catch (Exception e) {
                this.tempFilePath = writeTempFile(_dataStream, offset, len);
            }

        }
    }

    private String writeTempFile(byte[] b, int offset, int len) {
        String name = String.valueOf(System.currentTimeMillis()) + ".tmp";
        File file = new File(tempPath + File.separator + name);
        try {
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            out.write(b, offset, len);
            out.close();
        } catch (Exception e) {

        }
        return file.getAbsolutePath();
    }

    private static boolean matchSignature(byte[] pictureData, byte[] signature, int offset) {
        boolean matched = offset < pictureData.length;
        for (int i = 0; (i + offset) < pictureData.length && i < signature.length; i++) {
            if (pictureData[i + offset] != signature[i]) {
                matched = false;
                break;
            }
        }
        return matched;
    }

    private static int getPictureBytesStartOffset(int dataBlockStartOffset, byte[] _dataStream,
                                                  int dataBlockSize) {
        int realPicoffset = dataBlockStartOffset;
        final int dataBlockEndOffset = dataBlockSize + dataBlockStartOffset;


        int PICTFBlockSize = LittleEndian.getShort(_dataStream, dataBlockStartOffset
                + PICT_HEADER_OFFSET);

        int PICTF1BlockOffset = PICTFBlockSize + PICT_HEADER_OFFSET;
        short MM_TYPE = LittleEndian.getShort(_dataStream, dataBlockStartOffset
                + PICT_HEADER_OFFSET + 2);
        if (MM_TYPE == 0x66) {

            int cchPicName = LittleEndian.getUnsignedByte(_dataStream, PICTF1BlockOffset);
            PICTF1BlockOffset += 1 + cchPicName;
        }
        int PICTF1BlockSize = LittleEndian.getInt(_dataStream, dataBlockStartOffset
                + PICTF1BlockOffset);
        int unknownHeaderOffset = (PICTF1BlockSize + PICTF1BlockOffset) < dataBlockEndOffset
                ? (PICTF1BlockSize + PICTF1BlockOffset) : PICTF1BlockOffset;
        realPicoffset += (unknownHeaderOffset + UNKNOWN_HEADER_SIZE);
        if (realPicoffset >= dataBlockEndOffset) {
            realPicoffset -= UNKNOWN_HEADER_SIZE;
        }
        return realPicoffset;
    }

    private void fillJPGWidthHeight() {

        int pointer = pictureBytesStartOffset + 2;
        int firstByte = _dataStream[pointer];
        int secondByte = _dataStream[pointer + 1];

        int endOfPicture = pictureBytesStartOffset + size;
        while (pointer < endOfPicture - 1) {
            do {
                firstByte = _dataStream[pointer];
                secondByte = _dataStream[pointer + 1];
                pointer += 2;
            }
            while (!(firstByte == (byte) 0xFF) && pointer < endOfPicture - 1);

            if (firstByte == ((byte) 0xFF) && pointer < endOfPicture - 1) {
                if (secondByte == (byte) 0xD9 || secondByte == (byte) 0xDA) {
                    break;
                } else if ((secondByte & 0xF0) == 0xC0 && secondByte != (byte) 0xC4
                        && secondByte != (byte) 0xC8 && secondByte != (byte) 0xCC) {
                    pointer += 5;
                    this.height = getBigEndianShort(_dataStream, pointer);
                    this.width = getBigEndianShort(_dataStream, pointer + 2);
                    break;
                } else {
                    pointer++;
                    pointer++;
                    int length = getBigEndianShort(_dataStream, pointer);
                    pointer += length;
                }
            } else {
                pointer++;
            }
        }
    }

    private void fillPNGWidthHeight() {

        int HEADER_START = pictureBytesStartOffset + PNG.length + 4;
        if (matchSignature(_dataStream, IHDR, HEADER_START)) {
            int IHDR_CHUNK_WIDTH = HEADER_START + 4;
            this.width = getBigEndianInt(_dataStream, IHDR_CHUNK_WIDTH);
            this.height = getBigEndianInt(_dataStream, IHDR_CHUNK_WIDTH + 4);
        }
    }


    public int getWidth() {
        if (width == -1) {
            fillWidthHeight();
        }
        return width;
    }


    public int getHeight() {
        if (height == -1) {
            fillWidthHeight();
        }
        return height;
    }

    private static int getBigEndianInt(byte[] data, int offset) {
        return (((data[offset] & 0xFF) << 24) + ((data[offset + 1] & 0xFF) << 16)
                + ((data[offset + 2] & 0xFF) << 8) + (data[offset + 3] & 0xFF));
    }

    private static int getBigEndianShort(byte[] data, int offset) {
        return (((data[offset] & 0xFF) << 8) + (data[offset + 1] & 0xFF));
    }

    public String getTempFilePath() {
        return tempFilePath;
    }


    public void setTempFilePath(String tempFilePath) {
        this.tempFilePath = tempFilePath;
    }


    private String tempFilePath;

}
