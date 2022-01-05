package com.cumt.bigdata.util;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @Author: ahui
 * @date: 2021/12/24 - 17:34
 */
@Component
public class SortUtil {

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> st = map.entrySet().stream();

        st.sorted(Comparator.comparing(Map.Entry<K, V>::getValue).reversed()).forEach(e -> result.put(e.getKey(), e.getValue()));

        return result;
    }
}
