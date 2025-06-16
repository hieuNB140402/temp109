

package com.document.allreader.allofficefilereader.fc.hslf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import com.document.allreader.allofficefilereader.constant.EventConstant;
import com.document.allreader.allofficefilereader.fc.fs.filesystem.CFBFileSystem;
import com.document.allreader.allofficefilereader.fc.fs.filesystem.Property;
import com.document.allreader.allofficefilereader.fc.hslf.blip.Metafile;
import com.document.allreader.allofficefilereader.fc.hslf.exceptions.CorruptPowerPointFileException;
import com.document.allreader.allofficefilereader.fc.hslf.exceptions.EncryptedPowerPointFileException;
import com.document.allreader.allofficefilereader.fc.hslf.model.Picture;
import com.document.allreader.allofficefilereader.fc.hslf.record.CurrentUserAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.ExOleObjStg;
import com.document.allreader.allofficefilereader.fc.hslf.record.PersistPtrHolder;
import com.document.allreader.allofficefilereader.fc.hslf.record.PersistRecord;
import com.document.allreader.allofficefilereader.fc.hslf.record.Record;
import com.document.allreader.allofficefilereader.fc.hslf.record.UserEditAtom;
import com.document.allreader.allofficefilereader.fc.hslf.usermodel.ObjectData;
import com.document.allreader.allofficefilereader.fc.hslf.usermodel.PictureData;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;
import com.document.allreader.allofficefilereader.system.IControl;


public final class HSLFSlideShow/* extends POIDocument*/
{
    protected static final int CHECKSUM_SIZE = 16;

    private CurrentUserAtom currentUser;


    private byte[] _docstream;
    private Property _docProp;

    private Record[] _records;

    private List<PictureData> _pictures;
   private ObjectData[] _objects;

    private CFBFileSystem cfbFS;

    private IControl control;


    public HSLFSlideShow(IControl control, String fileName) throws IOException
    {
        this.control = control;
        cfbFS = new CFBFileSystem(new FileInputStream(fileName));

        readCurrentUserStream();

        readPowerPointStream();

        boolean encrypted = cfbFS.getPropertyRawData("EncryptedSummary") != null;
        if (encrypted)
        {
            throw new EncryptedPowerPointFileException(
                "Cannot process encrypted allofficefilereader files!");
        }

        buildRecords();

        readOtherStreams();
    }



    private void readPowerPointStream() throws IOException
    {
        // Get the main document stream
        //DocumentEntry docProps = (DocumentEntry)directory.getEntry("PowerPoint Document");

        // Grab the document stream
        _docstream = cfbFS.getPropertyRawData("PowerPoint Document");//new byte[docProps.getSize()];
        //directory.createDocumentInputStream("PowerPoint Document").read(_docstream);
        
        _docProp = cfbFS.getProperty("PowerPoint Document");
    }

    /**
     * Builds the list of records, based on the contents
     *  of the PowerPoint stream
     */
    private void buildRecords()
    {

        _records = read(/*_docstream,*/ (int)currentUser.getCurrentEditOffset());
    }
    
    private Record[] read(/*byte[] docstream, */int usrOffset)
    {
        ArrayList<Integer> lst = new ArrayList<Integer>();
        HashMap<Integer, Integer> offset2id = new HashMap<Integer, Integer>();
        while (usrOffset != 0)
        {
               byte[] data = _docProp.getRecordData(usrOffset);
            UserEditAtom usr = (UserEditAtom)Record.buildRecordAtOffset(data, 0, usrOffset);
            
            lst.add(Integer.valueOf(usrOffset));
            int psrOffset = usr.getPersistPointersOffset();

            data = _docProp.getRecordData(psrOffset);
            PersistPtrHolder ptr = (PersistPtrHolder)Record.buildRecordAtOffset(data, 0, psrOffset);
            
            lst.add(Integer.valueOf(psrOffset));
            Hashtable<Integer, Integer> entries = ptr.getSlideLocationsLookup();
            for (Integer id : entries.keySet())
            {
                Integer offset = entries.get(id);
                lst.add(offset);
                offset2id.put(offset, id);
            }

            usrOffset = usr.getLastUserEditAtomOffset();
        }
         Integer a[] = lst.toArray(new Integer[lst.size()]);
        Arrays.sort(a);
        Record[] rec = new Record[lst.size()];
        for (int i = 0; i < a.length; i++)
        {
            Integer offset = a[i];  byte[] data = _docProp.getRecordData(offset.intValue());
            rec[i] = Record.buildRecordAtOffset(data, 0, offset.intValue());
            
            if (rec[i] instanceof PersistRecord)
            {
                PersistRecord psr = (PersistRecord)rec[i];
                Integer id = offset2id.get(offset);
                psr.setPersistId(id.intValue());
            }
        }

        return rec;
    }


    private void readCurrentUserStream()
    {
        try
        {
            currentUser = new CurrentUserAtom(cfbFS);
        }
        catch(IOException ie)
        {
            currentUser = new CurrentUserAtom();
        }
    }


    private void readOtherStreams()
    {

    }

    private void readPictures() throws IOException
    {
        if (control == null)
        {
            return;
        }

        Property property = cfbFS.getProperty("Pictures");
        if (property == null)
        {
            return;
        }
        
        _pictures = new ArrayList<PictureData>();
        long rawDataSize = property.getPropertyRawDataSize();
        
        int pos = 0;
        while (pos <= (rawDataSize - 8))
        {
            int offset = pos;


            int signature = property.getUShort(pos);//LittleEndian.getUShort(pictstream, pos);
            pos += LittleEndian.SHORT_SIZE;
            int type = property.getUShort(pos);//LittleEndian.getUShort(pictstream, pos);
            pos += LittleEndian.SHORT_SIZE;
            int imgsize = property.getInt(pos);//LittleEndian.getInt(pictstream, pos);
            pos += LittleEndian.INT_SIZE;


            if (imgsize < 0)
            {
            	break;   }


            if (type == 0)
            {

            }
            else
            {

                try
                {
                    PictureData pict = PictureData.create(type - 0xF018);
                    pict.setOffset(offset);
                    

                    
                    if (pict.getType() == Picture.JPEG
                        || pict.getType() == Picture.PNG
                        || pict.getType() == Picture.DIB
                        || pict.getType() == Picture.WMF
                        || pict.getType() == Picture.EMF)
                    {
                        String name = String.valueOf(System.currentTimeMillis()) +  ".tmp";
                        File file = new File(control.getSysKit().getPictureManage().getPicTempPath() + File.separator + name);
                        try
                        {
                            file.createNewFile();
                            FileOutputStream out = new FileOutputStream(file);
                            if(pict.getType() == Picture.WMF || pict.getType() == Picture.EMF)
                            {
                                byte[] rawdata = property.getRecordData(pict.getOffset());
                                
                                pict.setRawData(rawdata);
                                ((Metafile)pict).writeByte_WMFAndEMF(out);
                            	
                            }
                            else
                            {
                                if (pict.getType() == Picture.PNG)
                                {
                                    long a = property.getLong(pos + 17);
                                    if (a == 0x0A1A0A0D474E5089L)
                                    {
                                        property.writeByte(out, pos + 17, imgsize - 17);
                                    }
                                    else
                                    {       
                                        // create PNG base on Mac/OS
                                        a = property.getLong(pos + 33);
                                        if (a == 0x0A1A0A0D474E5089L)
                                        {
                                            property.writeByte(out, pos + 33, imgsize - 33);
                                        }                                            
                                    }                                    
                                }
                                else
                                {
                                    property.writeByte(out, pos + 17, imgsize - 17);
                                }                               
                            }
                            
                            out.close();
                        }
                        catch (Exception e)
                        {
                            control.getSysKit().getErrorKit().writerLog(e);
                        }
                        pict.setTempFilePath(file.getAbsolutePath());
                    
                    }
                    _pictures.add(pict);
                }
                catch(IllegalArgumentException e)
                {

                	control.getSysKit().getErrorKit().writerLog(e);
                }
            }

            pos += imgsize;
        }
    }


    public synchronized int appendRootLevelRecord(Record newRecord)
    {
        int addedAt = -1;
        Record[] r = new Record[_records.length + 1];
        boolean added = false;
        for (int i = (_records.length - 1); i >= 0; i--)
        {
            if (added)
            {
                // Just copy over
                r[i] = _records[i];
            }
            else
            {
                r[(i + 1)] = _records[i];
                if (_records[i] instanceof PersistPtrHolder)
                {
                    r[i] = newRecord;
                    added = true;
                    addedAt = i;
                }
            }
        }
        _records = r;
        return addedAt;
    }


    public int addPicture(PictureData img)
    {
        // Process any existing pictures if we haven't yet
        if (_pictures == null)
        {
            try
            {
                readPictures();
            }
            catch(IOException e)
            {
                throw new CorruptPowerPointFileException(e.getMessage());
            }
        }

        // Add the new picture in
        int offset = 0;
        if (_pictures.size() > 0)
        {
            PictureData prev = _pictures.get(_pictures.size() - 1);
            offset = prev.getOffset() + prev.getRawData().length + 8;
        }
        img.setOffset(offset);
        _pictures.add(img);
        return offset;
    }


    public Record[] getRecords()
    {
        return _records;
    }


    public byte[] getUnderlyingBytes()
    {
        return _docstream;
    }


    public CurrentUserAtom getCurrentUserAtom()
    {
        return currentUser;
    }


    public PictureData[] getPictures()
    {
        if (_pictures == null)
        {
            try
            {
                readPictures();
            }
            catch(IOException e)
            {
                throw new CorruptPowerPointFileException(e.getMessage());
            }
            catch(OutOfMemoryError e)
            {
                control.getSysKit().getErrorKit().writerLog(e, true);
                control.actionEvent(EventConstant.SYS_READER_FINSH_ID, true);
                control = null;
            }
        }

        if (_pictures != null)
        {
            return _pictures.toArray(new PictureData[_pictures.size()]);
        }
        return null;
    }


    public ObjectData[] getEmbeddedObjects()
    {
        if (_objects == null)
        {
            List<ObjectData> objects = new ArrayList<ObjectData>();
            for (int i = 0; i < _records.length; i++)
            {
                if (_records[i] instanceof ExOleObjStg)
                {
                    objects.add(new ObjectData((ExOleObjStg)_records[i]));
                }
            }
            _objects = objects.toArray(new ObjectData[objects.size()]);
        }
        return _objects;
    }
    
    

    public void dispose()
    {
        if (currentUser != null)
        {
            currentUser.dispose();
            currentUser = null;
        }
        if (_records != null)
        {
            for (Record rec: _records)
            {
                rec.dispose();
            }
            _records = null;
        }
        if (_pictures != null)
        {
            for (PictureData pd : _pictures)
            {
                pd.dispose();
            }
            _pictures.clear();
            _pictures = null;
        }
        if (_objects != null)
        {
            for (ObjectData od : _objects)
            {
                od.dispose();
            }
            _objects = null;
        }
        if (cfbFS != null)
        {
            cfbFS.dispose();
            cfbFS = null;
        }
        control = null;
        _docstream = null;
    }
}
