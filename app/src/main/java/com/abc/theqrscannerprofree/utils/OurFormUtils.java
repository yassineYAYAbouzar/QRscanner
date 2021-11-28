package com.abc.theqrscannerprofree.utils;

import net.sourceforge.zbar.Symbol;

import java.util.List;
import java.util.ArrayList;

public class OurFormUtils {
    private int mId;
    private String mName;

    public static final OurFormUtils NONE = new OurFormUtils(Symbol.NONE, "NONE");
    public static final OurFormUtils PARTIAL = new OurFormUtils(Symbol.PARTIAL, "PARTIAL");
    public static final OurFormUtils EAN8 = new OurFormUtils(Symbol.EAN8, "EAN8");
    public static final OurFormUtils UPCE = new OurFormUtils(Symbol.UPCE, "UPCE");
    public static final OurFormUtils ISBN10 = new OurFormUtils(Symbol.ISBN10, "ISBN10");
    public static final OurFormUtils UPCA = new OurFormUtils(Symbol.UPCA, "UPCA");
    public static final OurFormUtils EAN13 = new OurFormUtils(Symbol.EAN13, "EAN13");
    public static final OurFormUtils ISBN13 = new OurFormUtils(Symbol.ISBN13, "ISBN13");
    public static final OurFormUtils I25 = new OurFormUtils(Symbol.I25, "I25");
    public static final OurFormUtils DATABAR = new OurFormUtils(Symbol.DATABAR, "DATABAR");
    public static final OurFormUtils DATABAR_EXP = new OurFormUtils(Symbol.DATABAR_EXP, "DATABAR_EXP");
    public static final OurFormUtils CODABAR = new OurFormUtils(Symbol.CODABAR, "CODABAR");
    public static final OurFormUtils CODE39 = new OurFormUtils(Symbol.CODE39, "CODE39");
    public static final OurFormUtils PDF417 = new OurFormUtils(Symbol.PDF417, "PDF417");
    public static final OurFormUtils QRCODE = new OurFormUtils(Symbol.QRCODE, "QRCODE");
    public static final OurFormUtils CODE93 = new OurFormUtils(Symbol.CODE93, "CODE93");
    public static final OurFormUtils CODE128 = new OurFormUtils(Symbol.CODE128, "CODE128");

    public static final List<OurFormUtils> ALL_FORMATS = new ArrayList<OurFormUtils>();

    static {
        ALL_FORMATS.add(OurFormUtils.PARTIAL);
        ALL_FORMATS.add(OurFormUtils.EAN8);
        ALL_FORMATS.add(OurFormUtils.UPCE);
        ALL_FORMATS.add(OurFormUtils.ISBN10);
        ALL_FORMATS.add(OurFormUtils.UPCA);
        ALL_FORMATS.add(OurFormUtils.EAN13);
        ALL_FORMATS.add(OurFormUtils.ISBN13);
        ALL_FORMATS.add(OurFormUtils.I25);
        ALL_FORMATS.add(OurFormUtils.DATABAR);
        ALL_FORMATS.add(OurFormUtils.DATABAR_EXP);
        ALL_FORMATS.add(OurFormUtils.CODABAR);
        ALL_FORMATS.add(OurFormUtils.CODE39);
        ALL_FORMATS.add(OurFormUtils.PDF417);
        ALL_FORMATS.add(OurFormUtils.QRCODE);
        ALL_FORMATS.add(OurFormUtils.CODE93);
        ALL_FORMATS.add(OurFormUtils.CODE128);
    }

    public OurFormUtils(int id, String name) {
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public static OurFormUtils getFormatById(int id) {
        for(OurFormUtils format : ALL_FORMATS) {
            if(format.getId() == id) {
                return format;
            }
        }
        return OurFormUtils.NONE;
    }
}