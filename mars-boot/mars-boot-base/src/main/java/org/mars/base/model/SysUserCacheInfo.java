package org.mars.base.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mars.base.util.DateUtils;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserCacheInfo {

    private String sysUserCode;

    private String sysUserName;

    private String sysOrgCode;

    private List<String> sysMultiOrgCode;

    private String provinceCode;


    private boolean oneDepart;

    public boolean isOneDepart() {
        return oneDepart;
    }

    public void setOneDepart(boolean oneDepart) {
        this.oneDepart = oneDepart;
    }

    public String getSysDate() {
        return DateUtils.formatDate();
    }

    public String getSysTime() {
        return DateUtils.now();
    }


}
