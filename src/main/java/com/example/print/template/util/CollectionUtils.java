package com.example.print.template.util;

import cn.hutool.core.collection.CollectionUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
 *@Author BieFeNg
 *@Date 2019/8/19 14:05
 *@DESC
 */
public class CollectionUtils {

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        if (null == map || map.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotNullOrEmpty(Map<?, ?> map) {
        return !isNullOrEmpty(map);
    }

    public static boolean isNullOrEmpty(Collection<?> coll) {
        if (null == coll || coll.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotNullOrEmpty(Collection<?> coll) {
        CollectionUtil.isNotEmpty(new HashMap<>());
        return !isNullOrEmpty(coll);
    }

    public static boolean isNullOrEmpty(Iterator<?> i) {
        if (null == i || !i.hasNext()) {
            return true;
        }
        return false;
    }

    public static boolean isNotNullOrEmpty(Iterator<?> i) {
        return !isNotNullOrEmpty(i);
    }


}
