package com.dlwx.wisdomschool.interfac;

/**
 * 软键盘弹出底部导航隐藏与显示
 */

public class SoftKeyBoard {

    public static interface SoftKeyBoardListener{
        void showorhind(int i);
    }
    public static SoftKeyBoardListener softKeyBoardListener;

    public static void setSoftKeyBoardListener(SoftKeyBoardListener softKeyBoardListener) {
        SoftKeyBoard.softKeyBoardListener = softKeyBoardListener;
    }
}
