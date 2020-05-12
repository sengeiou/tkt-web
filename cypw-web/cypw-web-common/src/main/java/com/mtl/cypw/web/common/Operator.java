package com.mtl.cypw.web.common;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tang.
 * @date 2019/11/28.
 */
@Slf4j
public class Operator {
    static transient ThreadLocal<Map<String, Integer>> threadLocal = new ThreadLocal<>();

    private static void setContextMap(Map<String, Integer> contextMap) {
        threadLocal.set(contextMap);
    }

    private static Map<String, Integer> getContextMap() {
        Map<String, Integer> map = threadLocal.get();
        return map;
    }

    private static Integer get(String key) {
        Map<String, Integer> contextMap = getContextMap();
        return contextMap == null ? null : contextMap.get(key);
    }

    private static void put(String key, Integer value) {
        Map<String, Integer> contextMap = getContextMap();
        if (contextMap == null) {
            contextMap = new HashMap();
            setContextMap(contextMap);
        }
        contextMap.put(key, value);
    }

    private static String MEMBER_ID_KEY = "member_id";

    private static String USER_ID_KEY = "user_id";

    private static String ENTERPRISE_ID_KEY = "enterprise_id";

    public static Integer getMemberId() {
        return get(MEMBER_ID_KEY);
    }

    public static void setMemberId(Integer memberId) {
        if (memberId == null) {
            return;
        }
        put(MEMBER_ID_KEY, memberId);
    }

    public static Integer getUserId() {
        return get(USER_ID_KEY);
    }

    public static void setUserId(Integer userId) {
        if (userId == null) {
            return;
        }
        put(USER_ID_KEY, userId);
    }

    public static Integer getEnterpriseId() {
        return get(ENTERPRISE_ID_KEY);
    }

    public static void setEnterpriseId(Integer enterpriseId) {
        if (enterpriseId == null) {
            return;
        }
        put(ENTERPRISE_ID_KEY, enterpriseId);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
