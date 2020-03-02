package com.dengyun.splashmodule.beans;

/**
 * Created by seven on 2016/8/5.
 */

public class MainConfig {

    private String message;
    private String result;
    private DataBean data;
    private AdvertisingBean advertising;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public AdvertisingBean getAdvertising() {
        return advertising;
    }

    public void setAdvertising(AdvertisingBean advertising) {
        this.advertising = advertising;
    }

    public static class AdvertisingBean {
        public int ads_id;
        public String ads_name;
        public String ad_url;
        public String ad_pic;

        public int getAds_id() {
            return ads_id;
        }

        public void setAds_id(int ads_id) {
            this.ads_id = ads_id;
        }

        public String getAds_name() {
            return ads_name;
        }

        public void setAds_name(String ads_name) {
            this.ads_name = ads_name;
        }

        public String getAd_url() {
            return ad_url;
        }

        public void setAd_url(String ad_url) {
            this.ad_url = ad_url;
        }

        public String getAd_pic() {
            return ad_pic;
        }

        public void setAd_pic(String ad_pic) {
            this.ad_pic = ad_pic;
        }
    }

    public static class DataBean {
        public String queryquestion;//常见问题
        public String getCommission;//佣金常见问题
        public String addSpeciliaty;
        public String askDetail;
        public String articleCommParise;
        public String categoryUserMappingInsert; //修改考试状态
        public String queryShopScoreSort; //店铺学分排行
        public String sendVerifyCode;
        public String praiseAskComment;
        public String askCommentInsert;
        public String lessonUserMappingInsert;
        public String lessonList;
        public String querySubscribedDayList;
        public String askInsert;
        public String mygold; //如何获得云币
        public String articleCommList;
        public String favoriteLesson;
        public String cidInsert;
        public String articleList;
        public String lessonCommentList;
        public String insertScoreLog;
        public String updateStatusByUid;
        public String exerciseList;
        public String addUserInfo;
        public String feedBack;
        public String queryOneDaySubscribe;
        public String searchUserOffOffice;
        public String myAskList;
        public String queryUserGold;
        public String queryMyScore;
        public String queryShopOfBeautyWS;
        public String searchMyColleagues;
        public String lessonCommentInsert;
        public String seachArticleOfSubscribe;
        public String lessonScoreList;
        public String querySysSprcility;
        public String updatePwd;
        public String addUserInfoContent;
        public String queryBeautyScoreSort;
        public String subscribeInsert; //关注
        public String redEnvelopes;
        public String queryUserMoney;
        public String askList;
        public String index;
        public String articleContent;
        public String articleContents;
        public String login;
        public String offLineSubTimeList;
        public String notSubscribeList;
        public String uploadFile;
        public String updateUser;
        public String tradingLog;
        public String myscore; //我的学分下面的页面
        public String lessonCategoryList;
        public String askCommentList;
        public String lessonDetails;
        public String subscribeList;
        public String articleCommInsert;
        public String queryShopSortDetail;
        public String version;
        public String versionCode;
        public String invite;//邀请
        public String inviteUrl;//团队邀请
        public String teamhelp;//团队帮助
        public String relationslist;//我的团队
        public String applyrelieverelations;//删除团队好友
        public String createinvitationcode;//删除团队好友

        public String EntrySopCast;//申请直播
        public String getLivebyid;//查看申请
        public String getLivenow;//直播列表
        public String getattention;//查看是否被关注
        public String LiveEnd;//直播结束
        public String addordelCollect;//收藏
        public String BackSee;//回看
        public String Discuss;//评论
        public String getDiscuss;//得到评论
        public String Search;//搜索
        public String getCollectlist;//收藏列表
        public String getCollectcourse;//收藏课程列表
        public String PersonalDetails;//个人信息
        public String article;//文章
        public String fansList;//粉丝
        public String articleCollect;//文章收藏
        public String queryMyarticle;//文章列表
        public String Mycollectarticle;//收藏的文章列表
        public String mymessage;//我的
        public String accessapartment;//直播审核
        public String updatelivestatus;//直播状态\
        public String secret;//加密字段
        public String PayAgreement;//充值协议

        public String praisepaper;
        public String livesubscribe;
        public String queryconcernaskmycomment;
        public String queryaskmycomment;
        //        public String queryarticlemycomment;
        public String queryconcernarticlemycomment;
        public String upSubscribestatus; //将预约的状态修改成完成
        public String querylivepurchaselist; //获取购买列表
        public String onlinepayliveverify; //直播详情
        public String addOrder;//添加订单
        public String queryCharge;//查询charge
        public String queryCharges;//查询charge(商学院课程支付)
        public String lessonSearch;//搜索课程
        public String askListSearch;//搜索问答
        public String lessonSeriesList;//获取课程系列
        public String querylesson; //课程内容
        public String querycontribution; // 云币贡献榜接口
        public String querydtInvitationCode;//请求二维码
        public String querydtmonthrecord;//获取地推月份记录
        public String querydtstaffrecord;//获取地推指定月份人员名单

        public String queryintegralsrule;//云币充值/佣金兑换的12个规格
        public String querybalance;//直播观看端进入界面请求余额
        public String livegiftto;//直播送礼
        public String queryrecord;//获取云币充值记录 || 兑换记录
        public String addcloudcoinOrder;//创建云币订单

        public String addJournal;//添加预约日志
        public String queryJournal;//获取预约日志接口
        public String queryundonejournalrecord;//获取美容师所有没写日志记录
        public String queryCosmetologist;//获取店铺下美容师
        public String queryUserList;//客户管理查询用户列表
        public String modifyUserRemarkByUserId;//修改用户的备注信息
        public String modifyMultipleUserBeauticianId;//通过每天美耶用户id和妃子校用户id绑定服务
        public String queryGoodsListByUserId;//通过每天美耶用户id获取已购买的服务
        public String queryUserByCondition;//根据name/nickname/mobile模糊查询用户
        public String queryCourseList;//线下课程列表（商学院）
        public String queryCourseContent;//线下课程内容（商学院）
        public String addTrainOrder;//线下课程下单（商学院）
        public String queryPurchaseCourse;//已购买课程（商学院）
        public String updateServiceManifesto;//修改服务宣言
        public String queryServiceRecordList;//通过记录id获取已购买的服务记录（临时）
        public String querylivesection;//获取回看列表
        public String queryAnchordata;//在直播间内获取主播的信息

        public int QQServiceFlag;//客服开关：0：QQ;  1：IM
        public String QQService;//im客服
        public String contactusByQQ;//qq客服

        public String auth;  //报货身份验证
        public String queryAccount;  //转款余额
        public String queryAccountDetail;  //账户明细
        public String queryChildCategory;  //商品分类
        public String queryGoods;  //查询商品
        public String queryGoodsOfCate;  //分类下商品
        public String addInCar;  //加入购物车
        public String queryCar;  //查询购物车
        public String modifyCountFromCar;  //修改购物车中商品数量
        public String delFromCar;  //商品从购物车中删除
        public String queryBillList;  //报货单列表
        public String queryBillDetailList;  //报货单详情
        public String queryBillDetailForProxy;  //付款人报货单详情
        public String queryCargoIndex;  //最近一次报货列表
        public String queryGoodsDetail;  //商品详情
        public String queryCarLen;  //购物车商品数量
        public String cs_secret;  //报货加密关键字
        public String cs_ver_num;  //报货版本号
        public String makeSureCarForPersonal;  //个人报货确认订单
        public String makeSureCarForOffice;  //企业报货确认订单
        public String modifyIsSelected;  //购物车选中接口
        public String saveBillForOffice;  //企业下单
        public String saveBillForPersonal;  //个人下单
        public String queryWarehouseLogOfBill;  //报货单入库日志
        public String addInCarAgain;  //再次加入购物车
        public String beauticianperformance;  //美容师信息，总的&&昨天
        public String clientperformance;  //客户消费信息
        public String clientperformances;  //通过条件获取客户消费信息
        public String clientsubscribe;  //客户预约总数
        public String clientsubscribes;  //通过条件获取客户预约次数
        public String shoptopten;  //店铺top10
        public String reportmarket;  //销售报表（销售额&&数量）
        public String Performancereport;  //业绩报表
        public String Restsreport;  //其它报表
        public String Shop;  //市场列表
        public String Shops;  //底层店铺查询
        public String bazaartopten;  //市场top10
        public String bazaarperformance;  //市场信息，总的&&昨天
        public String blocperformance;  //集团信息，总的&&昨天
        public String searchshop;  //店铺搜索
        public String Toshopfrequency;  //顾客来店频率
        public String frozenanalyze;  //顾客冰冻分析
        public String querytodayworkdata;  //今日工作数据
        public String queryFeizixiaoData;  //会员消费
        public String querynewcustomerlist;  //获取新客列表
        public String querytimelist;  //获取服务次数列表
        public String querymantimelist;  //获取服务人次列表
        public String queryconsumecardlist;  //获取耗卡列表
        public String synthesizeanalyzeboss;//综合分析-登云
        public String synthesizeanalyzelist;//工作台综合分析列表
        public String queryitems;//顾客项目列表
        public String querycommodity;//顾客商品列表
        public String queryset;//套卡数据
        public String querycommoncard;//通用卡数据
        public String stampcardrecord;//划卡记录
        public String memberdebt;//顾客欠款
        public String consumeperformance;//消耗业绩
        public String queryperformancedetail;//业绩明细
        public String queryperformancedetaillist;//业绩明细列表
        public String querydebtdetaillist;//顾客欠款记录
        public String clientanalyze;//客户分析
        public String addcolleague;//添加同事
        public String queryAppIndex;  //美货首页
        public String queryAllGoodsOfCate;  //获取子分类下所有商品
        public String queryFirstLevelCate;  //获取一级分类
        public String queryUserGoods;  //查询用户收藏的商品
        public String addOrDelUserGoods;  //收藏商品
        public String querySearchTag;  //查询热搜词汇
        public String queryAllGoods;  //查询全部商品
        public String queryItemsOfBanner;  //首页banner图下查询活动以及专题
        public String searchbossColleagues;//老板的同事详情
        public String topten;//项目top10
        public String queryordermoneyparticulars;//订单金额详情
        public String querymantimeparticulars;//服务人次详情列表
        public String queryfrequencynumberparticulars;//客频次详情
        public String querymember;//查询会员列表
        public String addServiceTime;//预约详情---实际服务开始、结束时间
        public String searchshopdownbeautician;//组织架构-店铺下美容师
        public String queryOneDaySubscribes;//当天待服务预约个数
        public String clientService;//客服配置接口
        public String getLivecategorylist;//首页直播分类
        public String queryOrderArrearageList;//获取顾客欠款列表
        public String queryAccountBalanceLog;//获取顾客余额明细
        public String verificationuserBymobile;//创建会员---验证手机号码是否被注册
        public String addmtmyuser;//创建会员
        public String addmtmyuseridentifyingcode;//验证码接口验证
        public String recordbrowsenumber;//记录直播浏览人数及浏览量
        public String earningsmain;//收益管理-主页面
        public String mybalance;//收益管理-我的佣金
        public String myintegral;//收益管理-我的云币
        public String integralhelp;//收益管理-我的云币详情
        public String balancehelp;//收益管理-我的佣金详
        public String commissionIncome;//收益管理-我的已结算佣金收益详情
        public String commissionIncomenot;//收益管理-我的结算中佣金收益详情
        public String commissionIncomenotpersonne;//收益管理-我的结算中的佣金收益个人详情
        public String integralDetails;//收益管理-我的已结算云币收益详情
        public String integralDetailsnot;//收益管理-我的结算中云币收益详情
        public String integralDetailsnotpersonne;//收益管理-我的结算中的金币收益个人详情
        public String elseDetailsnot;//收益管理-我的其它的金币收益个人详情
        public String alreadybalance;//收益管理-我的佣金已结算收益个人详情
        public String alreadyintegral;//收益管理-我的云币已结算收益个人详情
        public String help;//我的团队帮助界面
        public String mySelfCustomList;         //查询定制列表
        public String queryUserDetectionList;   //妃子校顾客检测记录
        public String userCustom;                   //用户定制详情
        public String queryCustomTemplet;           //定制列表模板
        public String fzxSearchGoods;                   //商品搜索
        public String screenCondition;                  //用户筛选
        public String addUserCustom;                  //保存定制
        public String detectionResults;               //定制详情
        public String carePlan;                 //护肤方案
        public String userCustomList;               //顾客检测记录
        public String queryMyScoreLog;               //我的学分-学分详情
        public String queryAccountDetailByOfficeId;               //账户详情账户详情
        public String morelessonList;               //更多课程
        public String sysUserConsumeperformanceTopTen;               //消耗top10
        public String queryTurnovertopten;               //销售top10
        public String appKeyAndroid;               //客服的key
        public String appKeyIos;                   //客服的key
        public String audituserinfo;               //手艺人申请
        public String franchiseeinfo;               //企业申请
        public String registeruser;                //注册用户
        public String authenticationmsg;               //获取认证信息
        public String livelike;                     //直播点赞
        public String queryRecommendGoods;               //购物车推荐商品
        public String queryMoreItems;               //查询更多项目
        public String queryGoodsFromItems;               //查询项目下商品
        public String queryUsertAddress;               //查询用户收货地址
        public String addOrUpdateAddress;               //编辑添加用户收货地址
        public String delUserAddress;               //删除用户收货地址
        public String updateDefaultAddress;               //修改为默认选中地址
        public String invitemendmsgs;           //非美容师邀请
        public String invitemendmsg;           //美容师邀请
        public String queryBillForReceipt;           //发票管理查询订单
        public String inviterefuses;            //美容师拒绝邀请
        public String delusercid;            //删除设备标识（token过期后调用）
        public String querynotifylist;            //获取通知消息
        public String readed;            //修改通知状态
        public String querynotifymsg;            //获取推送首页展示信息
        public String makeSureReceive;           //确认收货
        public String cancelBill;           //取消订单
        public String addReceipt;               //开具发票接口
        public String delBill;               //删除订单
        public String queryAfterSaleList;     //查询售后列表
        public String queryAfterSaleForBill;     //查询售后列表(订单)
        public String addAfterSaleAddress;     //补充售后信息
        public String saveAfterSale;     //申请售后
        public String delAfterSale;     //删除售后单
        public String authenticationstatus;     //认证状态获取
        public String enterprise;     //企业权限
        public String craftsman;     //手艺人权限
        //新版本普通会员、手艺人、企业会员权限
        public String getptmember;
        public String getCraftsman;
        public String getCompany;
        public String ServiceProtocol;      //服务协议
        public String LawAndPrivacy;        //法律及隐私
        public String VideoBuyProtocol;     //视频协议
        public String queryAfterSale;     //售后单详情
        public String queryCourier;     //查询物流信息
        public String queryInvoiceList;     //查询物流信息列表
        public String ms_secret;     //动态的加密字符
        public String myArticleCommList;     //我的  评论
        public String queryDefaultAddressByUserId;     //开具发票 默认地址接口
        public String queryMyCollectArticle;     //我的--收藏--文章
        public String querybanner;     //动态轮播图接口
        public String queryBillAuditList;     //查询订单审核列表
        public String updateStatusForBillAudit;     //订单审核
        public String queryBillProxyList;     //查询订单代付列表
        public String updateStatusForProxyPayAudit;     //订单代付（此接口为线下支付流程，线上支付调用在线支付接口）
        public String updateUsers;     //非企业用户：1 企业员工：2 企业技师信息：3
        public String modifyBillPayed;     //支付金额为零时支付接口
        public String calculatefreight;     //查询运费
        public String querycreditline;     //获取信用额度
        public String querybill;     //获取账单列表
        public String querybillparticulars;     //获取账单详情
        public String queryrefundparticulars;     //获取还款详情
        public String queryBillForUsedLimit;     //订单额度账单详情
        public String queryAfterSaleForUsedLimit;     //售后额度账单详情
        public String queryRefundPayCharge;     //还款获取charge
        public String queryPaytInfoByOfficeId;     //获取银行卡
        public String transferpay;     //转账支付
        public String getSupplylist;     //获取合同名称列表
        public String queryprotocolbyid;     //获取合同详情
        public String querystatus;     //获取供应链协议状态
        public String is_exist;//是否含有未签协议  0.已签、 1.未签
        public String querynoapply;//获取未签协议列表
        public String saveprotocol;//保存确认未签协议
        public String faq;//还款首页的常见问题url
        public String queryliveCharge;//直播间购买
        public String queryMyInformation;//我的界面信息
        public String adduserlooklog;//保存用户浏览记录
        public String queryuserlookinfo;//获取用户浏览记录列表
        public String deluserlookbyids;//删除用户浏览记录
        public String queryLiveSectionByHistory;//历史浏览获取直播回放片段
        public String queryArticleDetails;//文章详情
        public String queryArticleDetail;//文章详情wap
        public String savefavorites;//商学院收藏
        public String delfavorites;//商学院取消收藏
        public String querymycollectcourse;//商学院收藏列表
        public String cancelorder;//取消报名
        public String home;//地推活动主页
        public String registerLog;//注册记录分月
        public String registerLogInfo;//注册记录单月详情
        public String queryAcInfo;//地推详情
        public String marketService;//地推加密关键字

        private String mypraiselist;//获赞数
        //        private String askCommentLists;//问答列表接口
        private String updateinterest;//着陆页---兴趣点选择

        private String queryLivecategorylist; // 申请直播的分类
        private String studySearch; // 搜索
        private String querynews; // 妃子快讯
        private String queryMenu; // 查询菜单
        private String querynum; // 今日数据
        private String queryAdvertShareList; // 学习界面的轮播
        private String getQuestion; // 如何发布一个好问答
        private String getTitle; // 如何取好一个标题
        private String queryidcard; // //商学院报名验证身份
        private String dellivenum; // //离开直播间

        private String savestudytime; // //记录学习时长
        private String queryerrorbank; // //查询用户错题分类
        private String saveerror; // //保存错题信息
        private String delerror; // //删除错题库信息
        private String queryGraph; // //学习记录曲线图数据
        private String queryrank; // //学习排行榜
        private String queryerrorbankinfo; // //查询错题列表
        private String queryunitlist; // //单元测试试题列表
        private String findKind; // //课程分类列表
        private String findExamKind; // //学习测试分类列表--3.4.2增加
        private String queryunitinfos; // //单元测试首页
        private String courselist; // //课程列表
        private String insertGoldLog; // //添加云币
        private String queryCourseCharge; // 课程付费下单
        private String queryCourseOrderByPage; // 课程订单列表

        public  String gold_type; //云币类型（登陆送云币）
        public int gold_num; //云币数量

        private String queryapptsum; //预约列表（管理员、店长、美容师）
        private String queryapptlist;//全部预约
        private String queryapptinfo;//预约详情
        private String querynursesum;//护理日志汇总（管理员、店长、美容师）
        private String querynurse;//护理日志接口
        private String querynotfinishnurse;//未完成的护理日志
        private String querymemberlabel; //标签列表
        private String nursingDetail; //护理日志范例
        private String queryvisitsum; //回访列表（管理员、店长、美容师）
        private String queryvisitlist;//全部回访列表
        private String queryvisitcategory;//回访模板分类列表
        private String querytagmember; //子标签汇总列表
        private String savecustomerlabel; //保存会员标签
        private String savecustomerlabelbyid;//标签列表添加用户
        private String saveVisitLog;//新增回访记录接口
        private String queryUserRedEnvelope;//查询红包
        private String chooseRedEnvelope;//付款人选择红包计算订单金额
        private String saveOrderFigure;//付款人支付金额保存订单
        private String queryPayForOrder;//查询订单支付状态
        private String queryVipByOfficeId;//查询会员首页
        private String  queryVipOrderRecord;//查询会员购买记录
        private String  saveVipOrderToCache;//购买会员缓存保存订单
        private String  proxyPayForVip;//购买会员线下付款
        private String  queryVipCharge;//购买会员在线支付查询付款信息


        //        ————————————IM接口——————————————
        public String queryintegralindex;//我的云币首页
        public String querynearmonthintegral;//我的云币列表
        public String queryintegralincome;//我的云币收入、支出列表
        public String queryintegraltype;//获取云币类型
        public String queryintegralbytype;//云币类型筛选

        public String balanceshome;//佣金首页
        public String balanceshomelist;//佣金列表（刷新用）
        public String spendingdetails;//佣金支出
        public String totaldetails;//佣金累计收入、支出、结算
        public String querybalancestype;//佣金类型
        public String praiseComments;//我的界面---点赞数量统计
        public String adduserpraise;//动态文章评论点赞：方圆
        public String addFzxArticleLike;//动态文章评论点赞:小黑
        public String createrefundorder;//创建还款订单
        public String lessonDetail;//商学院分享
        public String shareArticleDetail;//文章分享
        public String shareGoodsDetail;//商品分享
        public String cancelrefundorder;//取消订单
        public String queryReimbursementMeans;//获取还款方式
        public String validationlinepayment;//验证线上支付账号
        public String validatibelowlinepayment;//验证线下支付账号
        public String queryBillGoodsForAfterSale;//售后查询可申请售后的订单商品
        public String queryproxy;//代付人协议变更
        public String updateContractInfoAuditForAgree;//代付人协议变更,是否同意
        public String queryAdvertShare;//供应链查询分享banner图
        public String querycustomerlist;//美容师服务客户
        public String queryMemberList;
        public String vipprotocol;

        //        ————————————IM接口——————————————
        public String registerToken;
        public String updateUserMessage;
        public String createGroup;
        public String joinGroup;
        public String quitGroup;
        public String dismissGroup;
        public String refreshGroup;
        public String getGroupUserList;
        public String getUserGroupByMap;
        public String kickedGroup;
        public String getGroupMessage;
        public String sendPrivateMessage;
        public String sendPrivateTemplate;
        public String sendGroupMessage;
        public String sendSystemMessage;
        public String sendSystemTemplate;
        public String organization;
        public String sendFriendRequest;
        public String getNewFriendList;
        public String acceptFriendRequest;
        public String getFriendList;
        public String setFriendAlias;
        public String getUserInfo;
        public String searchUserByTerms;
        public String queryBookOfficeList;
        public String queryBookUserList;
        public String updateGroupNotice;
        public String updateGroupLogo;
        public String getMemberList;//获取专属会员列表
        public String checkUserGroupRight;//校验创建群组的权限
        public String getMemberInfo;//获取专属会员信息详情接口
        public String assumeRole;//获取专属会员信息详情接口
        public String queryliveusernum;//获取直播间人数
        public String batchSendMessage;//好友群发消息
        public String updateGroupOwner;//群主转让
        public String queryAgreeContractOfficeList;//代付人查询代付机构等待同意机构列表
        public String screenWlanData;//客情分析
        public String saveComplain;//投诉

        public String getRongyunToken;//获取融云token
    }
}