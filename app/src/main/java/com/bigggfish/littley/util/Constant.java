package com.bigggfish.littley.util;

import android.os.Environment;

import com.bigggfish.littley.R;

import java.io.File;

/**
 * Created by android on 2016/7/26.
 */
public class Constant {

    //支出类别图片id
    //一般；餐饮；交通；衣服鞋包；日用品；通讯话费；
    // 水果零食；酒水饮料；房租；人情；药品；
    // 娱乐；家居；数码；其他

    //收入类别图片Id
    //报销；工资；红包；兼职；奖金；投资；

    public static final int[] TYPE_IMAGES_ID = new int[]{
            R.drawable.icon_zhichu_type_canyin,
            R.drawable.icon_zhichu_type_jiaotong,
            R.drawable.icon_zhichu_type_yifu,
            R.drawable.richangyongpin,
            R.drawable.icon_zhichu_type_shoujitongxun,
            R.drawable.icon_zhichu_type_shuiguolingshi,
            R.drawable.jiushui,
            R.drawable.fangzu,
            R.drawable.icon_zhichu_type_renqingsongli,
            R.drawable.yaopinfei,
            R.drawable.icon_zhichu_type_yule,
            R.drawable.icon_zhichu_type_jujia,
            R.drawable.shumachanpin,
            R.drawable.icon_shouru_type_qita,
            //收入
            R.drawable.icon_shouru_type_gongzi,
            R.drawable.baoxiao,
            R.drawable.icon_shouru_type_hongbao,
            R.drawable.icon_shouru_type_jianzhiwaikuai,
            R.drawable.icon_shouru_type_jiangjin,
            R.drawable.icon_shouru_type_touzishouru,
            R.drawable.icon_shouru_type_qita
    };


    /**
     * 应用文件目录
     */
    public static String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "littley";
    public static String SOFT_PATH = BASE_PATH + File.separator + "littley";
    public static String LOG_PATH = SOFT_PATH + File.separator + "log";
    public static String PORTRAIT_PATH = SOFT_PATH + File.separator + "portrait";
    public static String IMAGE_PATH = SOFT_PATH + File.separator + "images";
    public static String CACHE_PATH = SOFT_PATH + File.separator + "cache";
    public static String DOWNLOAD_PATH = SOFT_PATH + File.separator + "download";
    public static String CRASH_PATH = LOG_PATH + File.separator + "crash" + File.separator;


}
