package com.dengyun.baselibrary.net.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Title 项目类型枚举
 * @Author: zhoubo
 * @CreateDate: 2019-05-15 16:58
 */
@IntDef({ProjectType.NONE,ProjectType.IDENGYUN_MTMY,ProjectType.IDENGYUN_FZX,ProjectType.IDENGYUN_IM,ProjectType.IDENGYUN_SOBOT,
        ProjectType.FZX_CS,ProjectType.FZX_MS,ProjectType.FZX_DT,ProjectType.IDENGYUN_HR})
@Retention(RetentionPolicy.SOURCE)
public @interface ProjectType {
    int NONE = -1;
    //每天美耶
    int IDENGYUN_MTMY = 0;
    //妃子校
    int IDENGYUN_FZX = 1;
    //IM
    int IDENGYUN_IM = 2;
    //智齿客服
    int IDENGYUN_SOBOT = 3;

    //美货
    int FZX_CS = 4;
    //自媒体
    int FZX_MS = 5;
    //地推
    int FZX_DT = 6;
    //心零售
    int IDENGYUN_HR = 7;
}
