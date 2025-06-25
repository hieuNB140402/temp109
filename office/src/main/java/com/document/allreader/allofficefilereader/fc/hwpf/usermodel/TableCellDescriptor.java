

package com.document.allreader.allofficefilereader.fc.hwpf.usermodel;

import com.document.allreader.allofficefilereader.fc.hwpf.model.types.TCAbstractType;
import com.document.allreader.allofficefilereader.fc.util.LittleEndian;


public final class TableCellDescriptor extends TCAbstractType implements
        Cloneable
{
  public static final int SIZE = 20;

  protected  short field_x_unused;

  public TableCellDescriptor()
  {
    setBrcTop(new BorderCode());
    setBrcLeft(new BorderCode());
    setBrcBottom(new BorderCode());
    setBrcRight(new BorderCode());

  }

  protected void fillFields(byte[] data, int offset)
  {
    field_1_rgf = LittleEndian.getShort(data, 0x0 + offset);
    field_x_unused = LittleEndian.getShort(data, 0x2 + offset);
    setBrcTop(new BorderCode(data, 0x4 + offset));
    setBrcLeft(new BorderCode(data, 0x8 + offset));
    setBrcBottom(new BorderCode(data, 0xc + offset));
    setBrcRight(new BorderCode(data, 0x10 + offset));
  }

  public void serialize(byte[] data, int offset)
  {
      LittleEndian.putShort(data, 0x0 + offset, field_1_rgf);
      LittleEndian.putShort(data, 0x2 + offset, field_x_unused);
      getBrcTop().serialize(data, 0x4 + offset);
      getBrcLeft().serialize(data, 0x8 + offset);
      getBrcBottom().serialize(data, 0xc + offset);
      getBrcRight().serialize(data, 0x10 + offset);
  }

  public Object clone()
    throws CloneNotSupportedException
  {
    TableCellDescriptor tc = (TableCellDescriptor)super.clone();
    tc.setBrcTop((BorderCode)getBrcTop().clone());
    tc.setBrcLeft((BorderCode)getBrcLeft().clone());
    tc.setBrcBottom((BorderCode)getBrcBottom().clone());
    tc.setBrcRight((BorderCode)getBrcRight().clone());
    return tc;
  }

  public static TableCellDescriptor convertBytesToTC(byte[] buf, int offset)
  {
    TableCellDescriptor tc = new TableCellDescriptor();
    tc.fillFields(buf, offset);
    return tc;
  }

}
