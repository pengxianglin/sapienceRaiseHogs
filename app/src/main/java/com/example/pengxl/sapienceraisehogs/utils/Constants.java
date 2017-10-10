package com.example.pengxl.sapienceraisehogs.utils;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by newhope on 2016/4/7.
 */
public class Constants {
    public static final Class[] ALL_DB_ENTITIES = {
//            CacheEntity.class,
//            MedicalBasicData.class,
//            PigBasicData.class, BatchBasicData.class,
//            LoginInfo.class, OrganizeModel.class,
//            AreaData.class, DataDefineSourceData.class, DataDefineData.class,
//            FarmerInfoExData.class, HouseInfoData.class,
//            BatchInfo.class, DrugInfo.class, FeedInfo.class, FeedingHistoryInfo.class,
//            ImmuneInfo.class, FarmerEventsInfo.class, RolesModel.class, OrgRolesModel.class,
    };
    public static final Set<String> EXCLUDE_LOGIN = new HashSet<>();

    static {
        EXCLUDE_LOGIN.add("SplashActivity");
        EXCLUDE_LOGIN.add("LoginActivity");
        EXCLUDE_LOGIN.add("GuideActivity");
    }

    public static final String APIURL_UAT = "http://aptest.pigkeeping.cn:19005";
    public static final String APIURL_XIAN = "http://bksapi.pigkeeping.cn:80/";
    public static final String NAME_APK = "pigmanager.apk";
    public static final String SEPORATOR_PATH = "\\*";
    public static final String DB_NAME_BASIC = "databases.db";
    public static final String VERSION_URL = "http://pigboss.oak.net.cn:18000/files/down/version.xml";
    public static final String PKG_NAME = "com.newhope.pig.manage";
    public static final String API_BASE_PATH = "api/com.newhope.bps.v1.";
    public static final String DIR_BASE = "/sdcard/pigmanager/";
    public static final String DIR_COMPRESSED = DIR_BASE + "compress/";
    public static HashMap<String, Long> timeMap = new HashMap<String, Long>();//用来存放倒计时的时间
    public static String BAIDU_MAP_ADDRESS = "";
    public static final String userId = "-1";
    public static final int TYPE_CACHE_CHECKDATA = 1;
    public static final int TYPE_CACHE_DEATHDATA = 2;
    public static final int TYPE_CACHE_DISEASEDATA = 3;
    public static final int TYPE_CACHE_EVERYDAYDATA = 4;
    public static final int TYPE_CACHE_SELLPIGDATA = 5;
    public static final int TYPE_CACHE_YOUNGPIGDATA = 6;
    public static final int TYPE_CACHE_FARMINFODATA = 7;
    public static final int TYPE_CACHE_PACTDATA = 8;
    public static final int TYPE_CACHE_FEEDDATA = 9;
    public static final int TYPE_CACHE_CHECKDATA_PIG = 11;
    public static final int TYPE_CACHE_CHECKDATA_FEED = 12;
    //新增合同的缓存操作类别
    public static final int TYPE_CACHE_CONTRACT = 100;


    public static final int TYPE_CACHE_PARTNERDATA = 20;

    public static final int ERROR_CODE_FILE_NOT_FOUND = -100;
    public static final int ERROR_CODE_FAILED = -101;
    public static final String ERROR_STRING_FILE_NOT_FOUND = "File not found";

    public static final String FARMER_CONTRACT_STATUS_WAIT = "contract";
    public static final String FARMER_CONTRACT_STATUS_INTOPIG = "wait_piglet";
    public static final String FARMER_CONTRACT_STATUS_FEEDING = "feed";
    public static final String FARMER_CONTRACT_STATUS_SELLPIG = "slaughter";
    public static final String FARMER_CONTRACT_STATUS_SETTLEMENT = "settlement";

    public static final String INTENT_PARAM_FARMER = "farmer";
    public static final String INTENT_PACTINFO = "pactInfo";
    public static final String INTENT_HISTORY = "history";
    public static final String INTENT_EDIT_DETAIL = "BATCHID";
    //指标正常范围code
    public static final String INDEX_RANGE_CODE_SSZPL = "production_quota_SSZPL";
    public static final String INDEX_RANGE_CODE_CHL = "production_quota_CHL";
    public static final String INDEX_RANGE_CODE_LRB = "production_quota_LRB";
    public static final String INDEX_RANGE_CODE_RJZZ = "production_quota_RJZZ";

    public static final String UserType = "服务部负责人";
    public static final String UserType_LEAD = "分公司领导";
    public static final String UserType_AREA_LEAD = "区域领导";
    public static final String UserType_Admin = "放养管理员";

    public static int UserTypeName = 1;  //用户的角色信息，1为放养管理员。 2为公司领导

}
