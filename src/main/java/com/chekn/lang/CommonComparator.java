package com.chekn.lang;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class CommonComparator<T, N>
{
  private List<CommComparStruct> storePushVal = new ArrayList<CommComparStruct>();

  public void addComparVal(T unit, N comparVal)
  {
    this.storePushVal.add(new CommComparStruct(unit, comparVal));
  }

  public List<T> sort(boolean isAsc) {
    List<T> storeAfterSortData = new ArrayList<T>();

    if (this.storePushVal.size() != 0) {
      do {
        int indexTmp = 0;
        CommComparStruct ccsTmp = null;

        for (int i = 0; i < this.storePushVal.size(); i++) {
          CommComparStruct ccsUnit = (CommComparStruct)this.storePushVal.get(i);
          if (i == 0) {
            ccsTmp = ccsUnit;
          } else {
            if (compare(ccsTmp.getComparVal(), ccsUnit.getComparVal()))
              ccsTmp = isAsc ? ccsUnit : ccsTmp;
            else {
              ccsTmp = isAsc ? ccsTmp : ccsUnit;
            }
            if (ccsTmp == ccsUnit) {
              indexTmp = i;
            }
          }
        }

        storeAfterSortData.add(ccsTmp.getUnit());
        this.storePushVal.remove(indexTmp);
      }while (this.storePushVal.size() != 0);
    }
    return storeAfterSortData;
  }

  private boolean compare(N max, N min)
  {
    if ((max instanceof String))
      return strCompare((String)max, (String)min);
    if ((max instanceof Double))
      return ((Double)max).doubleValue() > ((Double)min).doubleValue();
    if ((max instanceof Float))
      return ((Float)max).floatValue() > ((Float)min).floatValue();
    if ((max instanceof Long))
      return ((Long)max).longValue() > ((Long)min).longValue();
    if ((max instanceof Integer)) {
      return ((Integer)max).intValue() > ((Integer)min).intValue();
    }
    throw new ClassCastException(String.format("current run instance's class %s can not force cast to number type or string type", max.getClass().getName()));
  }

  private boolean strCompare(String max, String min)
  {
    if ((StringUtils.isEmpty(max)) || (StringUtils.isEmpty(min)) || (!min.matches("(\\w|[\u4E00-\u9FA5])*")) || (!max.matches("(\\w|[\u4E00-\u9FA5])*"))) {
      throw new RuntimeException("请保持字符串非空 和只以 字母,数据,汉字 组成");
    }

    boolean isTrue = false;
    if (max.length() > min.length()) {
      isTrue = true;
    } else if (max.length() == min.length()) {
      char[] maxLetters = max.toCharArray();
      char[] minLetters = min.toCharArray();

      for (int i = 0; i < maxLetters.length; i++)
      {
        if (maxLetters[i] == minLetters[i])
          continue;
        if (maxLetters[i] <= minLetters[i]) break;
        isTrue = true;
        break;
      }
    }
    return isTrue;
  }

  public static void main(String[] args)
  {
    CommonComparator<String, Integer> cctor = new CommonComparator<String, Integer>();
    cctor.addComparVal("1", Integer.valueOf(1));
    cctor.addComparVal("4", Integer.valueOf(4));
    cctor.addComparVal("9", Integer.valueOf(9));
    cctor.addComparVal("8", Integer.valueOf(8));
    cctor.addComparVal("6", Integer.valueOf(6));
    cctor.addComparVal("5", Integer.valueOf(5));

    List<String> comparRes = cctor.sort(true);
    System.out.println(comparRes);
  }

  public class CommComparStruct
  {
    private T unit;
    private N comparVal;

    public T getUnit()
    {
      return this.unit;
    }
    public void setUnit(T unit) {
      this.unit = unit;
    }

    public N getComparVal() {
      return this.comparVal;
    }
    public void setComparVal(N comparVal) {
      this.comparVal = comparVal;
    }

    public CommComparStruct(T unit, N comparVal) {
      this.unit = unit;
      this.comparVal = comparVal;
    }
  }
}