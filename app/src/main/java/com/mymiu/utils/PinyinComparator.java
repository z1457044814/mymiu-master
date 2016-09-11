package com.mymiu.utils;

import com.mymiu.model.MusicEntity;

import java.util.Comparator;

/**
 * 字母排序类
 * Created by Yang on 2016/9/4.
 */
public class PinyinComparator implements Comparator<MusicEntity> {
    @Override
    public int compare(MusicEntity lhs, MusicEntity rhs) {
        return sort(lhs, rhs);
    }
    private int sort(MusicEntity lhs, MusicEntity rhs) {
        // 获取ascii值
        int lhs_ascii = lhs.getFirstPinYin().toUpperCase().charAt(0);
        int rhs_ascii = rhs.getFirstPinYin().toUpperCase().charAt(0);
        // 判断若不是字母，则排在字母之后
        if (lhs_ascii < 65 || lhs_ascii > 90)
            return 1;
        else if (rhs_ascii < 65 || rhs_ascii > 90)
            return -1;
        else
            return lhs.getPinYin().compareTo(rhs.getPinYin());
    }
}
