

package com.document.allreader.allofficefilereader.fc.ddf;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class DefaultEscherRecordFactory implements EscherRecordFactory
{
    private static Class< ? >[] escherRecordClasses = {EscherBSERecord.class,
        EscherOptRecord.class, EscherTertiaryOptRecord.class, EscherClientAnchorRecord.class,
        EscherDgRecord.class, EscherSpgrRecord.class, EscherSpRecord.class,
        EscherClientDataRecord.class, EscherDggRecord.class, EscherSplitMenuColorsRecord.class,
        EscherChildAnchorRecord.class, EscherTextboxRecord.class, EscherBinaryTagRecord.class};
    private static Map<Short, Constructor< ? extends EscherRecord>> recordsMap = recordsToMap(escherRecordClasses);


    public DefaultEscherRecordFactory()
    {

    }


    public EscherRecord createRecord(byte[] data, int offset)
    {
        EscherRecord.EscherRecordHeader header = EscherRecord.EscherRecordHeader.readHeader(data,
            offset);


        if ((header.getOptions() & (short)0x000F) == (short)0x000F
            && header.getRecordId() != EscherTextboxRecord.RECORD_ID)
        {
            EscherContainerRecord r = new EscherContainerRecord();
            r.setRecordId(header.getRecordId());
            r.setOptions(header.getOptions());
            return r;
        }

        if (header.getRecordId() >= EscherBlipRecord.RECORD_ID_START
            && header.getRecordId() <= EscherBlipRecord.RECORD_ID_END)
        {
            EscherBlipRecord r;
            if (header.getRecordId() == EscherBitmapBlip.RECORD_ID_DIB
                || header.getRecordId() == EscherBitmapBlip.RECORD_ID_JPEG
                || header.getRecordId() == EscherBitmapBlip.RECORD_ID_PNG)
            {
                r = new EscherBitmapBlip();
            }
            else if (header.getRecordId() == EscherMetafileBlip.RECORD_ID_EMF
                || header.getRecordId() == EscherMetafileBlip.RECORD_ID_WMF
                || header.getRecordId() == EscherMetafileBlip.RECORD_ID_PICT)
            {
                r = new EscherMetafileBlip();
            }
            else
            {
                r = new EscherBlipRecord();
            }
            r.setRecordId(header.getRecordId());
            r.setOptions(header.getOptions());
            return r;
        }

        Constructor< ? extends EscherRecord> recordConstructor = recordsMap.get(Short
            .valueOf(header.getRecordId()));
        EscherRecord escherRecord = null;
        if (recordConstructor == null)
        {
            return new UnknownEscherRecord();
        }
        try
        {
            escherRecord = recordConstructor.newInstance(new Object[]{});
        }
        catch(Exception e)
        {
            return new UnknownEscherRecord();
        }
        escherRecord.setRecordId(header.getRecordId());
        escherRecord.setOptions(header.getOptions());
        return escherRecord;
    }

    private static Map<Short, Constructor< ? extends EscherRecord>> recordsToMap(
        Class< ? >[] recClasses)
    {
        Map<Short, Constructor< ? extends EscherRecord>> result = new HashMap<Short, Constructor< ? extends EscherRecord>>();
        final Class< ? >[] EMPTY_CLASS_ARRAY = new Class[0];

        for (int i = 0; i < recClasses.length; i++)
        {
            @ SuppressWarnings("unchecked")
            Class< ? extends EscherRecord> recCls = (Class< ? extends EscherRecord>)recClasses[i];
            short sid;
            try
            {
                sid = recCls.getField("RECORD_ID").getShort(null);
            }
            catch(IllegalArgumentException e)
            {
                throw new RuntimeException(e);
            }
            catch(IllegalAccessException e)
            {
                throw new RuntimeException(e);
            }
            catch(NoSuchFieldException e)
            {
                throw new RuntimeException(e);
            }
            Constructor< ? extends EscherRecord> constructor;
            try
            {
                constructor = recCls.getConstructor(EMPTY_CLASS_ARRAY);
            }
            catch(NoSuchMethodException e)
            {
                throw new RuntimeException(e);
            }
            result.put(Short.valueOf(sid), constructor);
        }
        return result;
    }
}
