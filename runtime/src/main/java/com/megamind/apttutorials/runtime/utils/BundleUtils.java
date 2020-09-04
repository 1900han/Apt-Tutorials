package com.megamind.apttutorials.runtime.utils;

import android.os.Bundle;

/**
 * @author HanZhengYa
 * @description
 * @date 22:42 9/4/20
 * @since 1.0
 **/
public class BundleUtils {
    public static <T> T get(Bundle bundle, String key) {
        return (T)bundle.get(key);
    }

    public static <T> T get(Bundle bundle, String key,Object defaultValue) {
        Object obj = bundle.get(key);
        if (obj == null) {
            obj = defaultValue;
        }
        return (T)obj;
    }
}
