

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import com.document.allreader.allofficefilereader.fc.hwpf.model.ListFormatOverride;
import com.document.allreader.allofficefilereader.fc.hwpf.model.ListFormatOverrideLevel;
import com.document.allreader.allofficefilereader.fc.hwpf.model.ListTables;
import com.document.allreader.allofficefilereader.fc.hwpf.model.PAPX;
import com.document.allreader.allofficefilereader.fc.hwpf.model.POIListLevel;
import com.document.allreader.allofficefilereader.fc.util.POILogFactory;
import com.document.allreader.allofficefilereader.fc.util.POILogger;


public final class ListEntry extends Paragraph
{
    private static POILogger log = POILogFactory.getLogger(ListEntry.class);

    POIListLevel _level;
    ListFormatOverrideLevel _overrideLevel;

    ListEntry(PAPX papx, Range parent, ListTables tables)
    {
        super(papx, parent);

        if (tables != null && _props.getIlfo() < tables.getOverrideCount())
        {
            ListFormatOverride override = tables.getOverride(_props.getIlfo());
            _overrideLevel = override.getOverrideLevel(_props.getIlvl());
            _level = tables.getLevel(override.getLsid(), _props.getIlvl());
        }
        else
        {
            log.log(
                POILogger.WARN,
                "No ListTables found for ListEntry - document probably partly corrupt, and you may experience problems");
        }
    }

    public int type()
    {
        return TYPE_LISTENTRY;
    }
}
