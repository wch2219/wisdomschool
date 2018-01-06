package com.dlwx.wisdomschool.interfac;

/**
 * Created by Administrator on 2018/1/5/005.
 */

public class Picseltete {
    public static SletePicInterface sletePicInterf;

    public interface SletePicInterface {
        void checkback(int pos, boolean check);
    }

    public static void setSletePicInterface(SletePicInterface sletePicInterface) {
       sletePicInterf = sletePicInterface;
    }
}
