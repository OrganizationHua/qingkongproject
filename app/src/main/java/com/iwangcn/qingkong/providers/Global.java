package com.iwangcn.qingkong.providers;

/**
 * Created by czh on 2017/4/18.
 */

public class Global {
    public static boolean isNewVersion;//是否是最新版本

    public static boolean isNewVersion() {
        return isNewVersion;
    }

    public static void setIsNewVersion(boolean isNewVersion) {
        Global.isNewVersion = isNewVersion;
    }
}
