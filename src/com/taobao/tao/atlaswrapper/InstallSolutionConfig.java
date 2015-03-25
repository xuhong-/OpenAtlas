package com.taobao.tao.atlaswrapper;

public class InstallSolutionConfig {
    public static boolean install_when_findclass;
    public static boolean install_when_oncreate;
    public static boolean install_when_onreceive;

    static {
        install_when_oncreate = true;
        install_when_onreceive = true;
        install_when_findclass = true;
    }
}