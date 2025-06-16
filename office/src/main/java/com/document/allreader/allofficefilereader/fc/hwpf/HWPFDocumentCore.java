

package com.document.allreader.allofficefilereader.fc.hwpf;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import com.document.allreader.allofficefilereader.fc.EncryptedDocumentException;
import com.document.allreader.allofficefilereader.fc.fs.filesystem.CFBFileSystem;
import com.document.allreader.allofficefilereader.fc.hwpf.model.CHPBinTable;
import com.document.allreader.allofficefilereader.fc.hwpf.model.FileInformationBlock;
import com.document.allreader.allofficefilereader.fc.hwpf.model.FontTable;
import com.document.allreader.allofficefilereader.fc.hwpf.model.ListTables;
import com.document.allreader.allofficefilereader.fc.hwpf.model.PAPBinTable;
import com.document.allreader.allofficefilereader.fc.hwpf.model.SectionTable;
import com.document.allreader.allofficefilereader.fc.hwpf.model.StyleSheet;
import com.document.allreader.allofficefilereader.fc.hwpf.model.TextPieceTable;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.ObjectPoolImpl;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.ObjectsPool;
import com.document.allreader.allofficefilereader.fc.hwpf.usermodel.Range;
import com.document.allreader.allofficefilereader.fc.util.Internal;

public abstract class HWPFDocumentCore// extends POIDocument
{
    protected static final String STREAM_OBJECT_POOL = "ObjectPool";
    protected static final String STREAM_WORD_DOCUMENT = "WordDocument";

    protected ObjectPoolImpl _objectPool;

    protected FileInformationBlock _fib;

    protected StyleSheet _ss;

    protected CHPBinTable _cbt;
    protected PAPBinTable _pbt;

    protected SectionTable _st;

    protected FontTable _ft;

    protected ListTables _lt;

    protected byte[] _mainStream;

    protected CFBFileSystem cfbFS;



    public static CFBFileSystem verifyAndBuildPOIFS(InputStream istream) throws IOException
    {
        PushbackInputStream pis = new PushbackInputStream(istream, 6);
        byte[] first6 = new byte[6];
        pis.read(first6);

       if (first6[0] == '{' && first6[1] == '\\' && first6[2] == 'r' && first6[3] == 't'
            && first6[4] == 'f')
        {
            throw new IllegalArgumentException("The document is really a RTF file");
        }


        pis.unread(first6);
        return new CFBFileSystem(pis);
    }


    public HWPFDocumentCore(InputStream istream) throws IOException
    {

        cfbFS = verifyAndBuildPOIFS(istream);
        _mainStream = cfbFS.getPropertyRawData("WordDocument"); 
        

        _fib = new FileInformationBlock(_mainStream);
        if (_fib.isFEncrypted())
        {
            throw new EncryptedDocumentException("Cannot process encrypted allofficefilereader files!");
        }


    }


    public abstract Range getRange();


    public abstract Range getOverallRange();


    public String getDocumentText()
    {
        return getText().toString();
    }


    @ Internal
    public abstract StringBuilder getText();

    public CHPBinTable getCharacterTable()
    {
        return _cbt;
    }

    public PAPBinTable getParagraphTable()
    {
        return _pbt;
    }

    public SectionTable getSectionTable()
    {
        return _st;
    }

    public StyleSheet getStyleSheet()
    {
        return _ss;
    }

    public ListTables getListTables()
    {
        return _lt;
    }

    public FontTable getFontTable()
    {
        return _ft;
    }

    public FileInformationBlock getFileInformationBlock()
    {
        return _fib;
    }

    public ObjectsPool getObjectsPool()
    {
        return _objectPool;
    }

    public abstract TextPieceTable getTextTable();
}
