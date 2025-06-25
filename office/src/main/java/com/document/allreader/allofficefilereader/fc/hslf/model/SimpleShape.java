

package com.document.allreader.allofficefilereader.fc.hslf.model;

import com.document.allreader.allofficefilereader.constant.AutoShapeConstant;
import com.document.allreader.allofficefilereader.fc.ShapeKit;
import com.document.allreader.allofficefilereader.fc.ddf.DefaultEscherRecordFactory;
import com.document.allreader.allofficefilereader.fc.ddf.EscherChildAnchorRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherClientAnchorRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherClientDataRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherContainerRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherOptRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherProperties;
import com.document.allreader.allofficefilereader.fc.ddf.EscherRecord;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSimpleProperty;
import com.document.allreader.allofficefilereader.fc.ddf.EscherSpRecord;
import com.document.allreader.allofficefilereader.fc.hslf.exceptions.HSLFException;
import com.document.allreader.allofficefilereader.fc.hslf.record.InteractiveInfo;
import com.document.allreader.allofficefilereader.fc.hslf.record.InteractiveInfoAtom;
import com.document.allreader.allofficefilereader.fc.hslf.record.Record;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;
import com.document.allreader.allofficefilereader.java.awt.Color;
import com.document.allreader.allofficefilereader.java.awt.geom.AffineTransform;
import com.document.allreader.allofficefilereader.java.awt.geom.Rectangle2D;

import java.io.ByteArrayOutputStream;


public abstract class SimpleShape extends Shape {

    protected SimpleShape(EscherContainerRecord escherRecord, Shape parent) {
        super(escherRecord, parent);
    }

    public Rectangle2D getLogicalAnchor2D() {
        Rectangle2D anchor = getAnchor2D();

        if (_parent != null) {

            Rectangle2D clientAnchor = ((ShapeGroup) _parent).getClientAnchor2D(_parent);

            Rectangle2D spgrAnchor = ((ShapeGroup) _parent).getCoordinates();


            double scalex = spgrAnchor.getWidth() / clientAnchor.getWidth();
            double scaley = spgrAnchor.getHeight() / clientAnchor.getHeight();

            double x = clientAnchor.getX() + (anchor.getX() - spgrAnchor.getX()) / scalex;
            double y = clientAnchor.getY() + (anchor.getY() - spgrAnchor.getY()) / scaley;
            double width = anchor.getWidth() / scalex;
            double height = anchor.getHeight() / scaley;


            anchor = new Rectangle2D.Double(x, y, width, height);

        }

        int angle = getRotation();
        if (angle != 0) {
            double centerX = anchor.getX() + anchor.getWidth() / 2;
            double centerY = anchor.getY() + anchor.getHeight() / 2;

            AffineTransform trans = new AffineTransform();
            trans.translate(centerX, centerY);
            trans.rotate(Math.toRadians(angle));
            trans.translate(-centerX, -centerY);

            Rectangle2D rect = trans.createTransformedShape(anchor).getBounds2D();
            if ((anchor.getWidth() < anchor.getHeight() && rect.getWidth() > rect.getHeight())
                    || (anchor.getWidth() > anchor.getHeight() && rect.getWidth() < rect.getHeight())) {
                trans = new AffineTransform();
                trans.translate(centerX, centerY);
                trans.rotate(Math.PI / 2);
                trans.translate(-centerX, -centerY);
                anchor = trans.createTransformedShape(anchor).getBounds2D();
            }
        }
        return anchor;
    }

    /**
     * Find a record in the underlying EscherClientDataRecord
     *
     * @param recordType type of the record to search
     */
    protected Record getClientDataRecord(int recordType) {
        Record[] records = getClientRecords();
        if (records != null) {
            for (int i = 0; i < records.length; i++) {
                if (records[i].getRecordType() == recordType) {
                    return records[i];
                }
            }
        }
        return null;
    }

    /**
     * Search for EscherClientDataRecord, if found, convert its contents into an array of HSLF records
     *
     * @return an array of HSLF records contained in the shape's EscherClientDataRecord or <code>null</code>
     */
    protected Record[] getClientRecords() {
        if (_clientData == null) {
            EscherRecord r = ShapeKit.getEscherChild(getSpContainer(),
                    EscherClientDataRecord.RECORD_ID);
            //ddf can return EscherContainerRecord with recordId=EscherClientDataRecord.RECORD_ID
            //convert in to EscherClientDataRecord on the fly
            if (r != null && !(r instanceof EscherClientDataRecord)) {
                byte[] data = r.serialize();
                r = new EscherClientDataRecord();
                r.fillFields(data, 0, new DefaultEscherRecordFactory());
            }
            _clientData = (EscherClientDataRecord) r;
        }
        if (_clientData != null && _clientRecords == null) {
            byte[] data = _clientData.getRemainingData();
            _clientRecords = Record.findChildRecords(data, 0, data.length);
        }
        return _clientRecords;
    }


    /***********************************************************/
    /**
     * Create a new Shape
     *
     * @param isChild <code>true</code> if the Line is inside a group, <code>false</code> otherwise
     * @return the record container which holds this shape
     */
    protected EscherContainerRecord createSpContainer(boolean isChild) {
        _escherContainer = new EscherContainerRecord();
        _escherContainer.setRecordId(EscherContainerRecord.SP_CONTAINER);
        _escherContainer.setOptions((short) 15);

        EscherSpRecord sp = new EscherSpRecord();
        int flags = EscherSpRecord.FLAG_HAVEANCHOR | EscherSpRecord.FLAG_HASSHAPETYPE;
        if (isChild)
            flags |= EscherSpRecord.FLAG_CHILD;
        sp.setFlags(flags);
        _escherContainer.addChildRecord(sp);

        EscherOptRecord opt = new EscherOptRecord();
        opt.setRecordId(EscherOptRecord.RECORD_ID);
        _escherContainer.addChildRecord(opt);

        EscherRecord anchor;
        if (isChild)
            anchor = new EscherChildAnchorRecord();
        else {
            anchor = new EscherClientAnchorRecord();


            byte[] header = new byte[16];
            LittleEndian.putUShort(header, 0, 0);
            LittleEndian.putUShort(header, 2, 0);
            LittleEndian.putInt(header, 4, 8);
            anchor.fillFields(header, 0, null);
        }
        _escherContainer.addChildRecord(anchor);

        return _escherContainer;
    }


    public void setLineWidth(double width) {
        EscherOptRecord opt = (EscherOptRecord) ShapeKit.getEscherChild(_escherContainer,
                EscherOptRecord.RECORD_ID);
        setEscherProperty(opt, EscherProperties.LINESTYLE__LINEWIDTH, (int) (width * ShapeKit.EMU_PER_POINT));
    }


    public void setLineColor(Color color) {
        EscherOptRecord opt = (EscherOptRecord) ShapeKit.getEscherChild(_escherContainer,
                EscherOptRecord.RECORD_ID);
        if (color == null) {
            setEscherProperty(opt, EscherProperties.LINESTYLE__NOLINEDRAWDASH, 0x80000);
        } else {
            int rgb = new Color(color.getBlue(), color.getGreen(), color.getRed(), 0).getRGB();
            setEscherProperty(opt, EscherProperties.LINESTYLE__COLOR, rgb);
            setEscherProperty(opt, EscherProperties.LINESTYLE__NOLINEDRAWDASH, color == null
                    ? 0x180010 : 0x180018);
        }
    }


    public int getLineDashing() {
        EscherOptRecord opt = (EscherOptRecord) ShapeKit.getEscherChild(_escherContainer,
                EscherOptRecord.RECORD_ID);

        EscherSimpleProperty prop = (EscherSimpleProperty) ShapeKit.getEscherProperty(opt,
                EscherProperties.LINESTYLE__LINEDASHING);
        return prop == null ? AutoShapeConstant.LINESTYLE_SOLID : prop.getPropertyValue();
    }


    public void setLineDashing(int pen) {
        EscherOptRecord opt = (EscherOptRecord) ShapeKit.getEscherChild(_escherContainer,
                EscherOptRecord.RECORD_ID);

        setEscherProperty(opt, EscherProperties.LINESTYLE__LINEDASHING, pen);
    }


    public void setLineStyle(int style) {
        EscherOptRecord opt = (EscherOptRecord) ShapeKit.getEscherChild(_escherContainer,
                EscherOptRecord.RECORD_ID);
        setEscherProperty(opt, EscherProperties.LINESTYLE__LINESTYLE, style == AutoShapeConstant.LINE_SIMPLE
                ? -1 : style);
    }

    public int getLineStyle() {
        EscherOptRecord opt = (EscherOptRecord) ShapeKit.getEscherChild(_escherContainer,
                EscherOptRecord.RECORD_ID);
        EscherSimpleProperty prop = (EscherSimpleProperty) ShapeKit.getEscherProperty(opt,
                EscherProperties.LINESTYLE__LINESTYLE);
        return prop == null ? AutoShapeConstant.LINE_SIMPLE : prop.getPropertyValue();
    }

    public void setFillColor(Color color) {
        getFill().setForegroundColor(color);
    }


    public void setRotation(int theta) {
        setEscherProperty(EscherProperties.TRANSFORM__ROTATION, (theta << 16));
    }



    protected void updateClientData() {
        if (_clientData != null && _clientRecords != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                for (int i = 0; i < _clientRecords.length; i++) {

                }
            } catch (Exception e) {
                throw new HSLFException(e);
            }
            _clientData.setRemainingData(out.toByteArray());
        }
    }

    public void setHyperlink(Hyperlink link) {
        if (link.getId() == -1) {
            throw new HSLFException("You must call SlideShow.addHyperlink(Hyperlink link) first");
        }

        EscherClientDataRecord cldata = new EscherClientDataRecord();
        cldata.setOptions((short) 0xF);
        getSpContainer().addChildRecord(cldata); // TODO - junit to prove getChildRecords().add is wrong

        InteractiveInfo info = new InteractiveInfo();
        InteractiveInfoAtom infoAtom = info.getInteractiveInfoAtom();

        switch (link.getType()) {
            case Hyperlink.LINK_FIRSTSLIDE:
                infoAtom.setAction(InteractiveInfoAtom.ACTION_JUMP);
                infoAtom.setJump(InteractiveInfoAtom.JUMP_FIRSTSLIDE);
                infoAtom.setHyperlinkType(InteractiveInfoAtom.LINK_FirstSlide);
                break;
            case Hyperlink.LINK_LASTSLIDE:
                infoAtom.setAction(InteractiveInfoAtom.ACTION_JUMP);
                infoAtom.setJump(InteractiveInfoAtom.JUMP_LASTSLIDE);
                infoAtom.setHyperlinkType(InteractiveInfoAtom.LINK_LastSlide);
                break;
            case Hyperlink.LINK_NEXTSLIDE:
                infoAtom.setAction(InteractiveInfoAtom.ACTION_JUMP);
                infoAtom.setJump(InteractiveInfoAtom.JUMP_NEXTSLIDE);
                infoAtom.setHyperlinkType(InteractiveInfoAtom.LINK_NextSlide);
                break;
            case Hyperlink.LINK_PREVIOUSSLIDE:
                infoAtom.setAction(InteractiveInfoAtom.ACTION_JUMP);
                infoAtom.setJump(InteractiveInfoAtom.JUMP_PREVIOUSSLIDE);
                infoAtom.setHyperlinkType(InteractiveInfoAtom.LINK_PreviousSlide);
                break;
            case Hyperlink.LINK_URL:
                infoAtom.setAction(InteractiveInfoAtom.ACTION_HYPERLINK);
                infoAtom.setJump(InteractiveInfoAtom.JUMP_NONE);
                infoAtom.setHyperlinkType(InteractiveInfoAtom.LINK_Url);
                break;
        }

        infoAtom.setHyperlinkID(link.getId());


    }


    public void dispose() {
        super.dispose();
        if (_clientRecords != null) {
            for (Record rec : _clientRecords) {
                rec.dispose();
            }
            _clientRecords = null;
        }
        if (_clientData != null) {
            _clientData.dispose();
            _clientData = null;
        }
    }


    protected Record[] _clientRecords;
    protected EscherClientDataRecord _clientData;

}
