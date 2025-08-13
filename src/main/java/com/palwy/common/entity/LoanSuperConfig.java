package com.palwy.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class LoanSuperConfig {
    private Long id;

    private String prdCode;

    private String prdName;

    private String prdTag;

    private String prdLogo;

    private String prdStatus;

    private BigDecimal amtUpper;

    private String showTimeStart;

    private String showTimeEnd;

    private String showHoliday;

    private String showOrder;

    private String linkUrl;

    private String isDeleted;

    private String creator;

    private Date gmtCreated;

    private String modifier;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrdCode() {
        return prdCode;
    }

    public void setPrdCode(String prdCode) {
        this.prdCode = prdCode == null ? null : prdCode.trim();
    }

    public String getPrdName() {
        return prdName;
    }

    public void setPrdName(String prdName) {
        this.prdName = prdName == null ? null : prdName.trim();
    }

    public String getPrdTag() {
        return prdTag;
    }

    public void setPrdTag(String prdTag) {
        this.prdTag = prdTag == null ? null : prdTag.trim();
    }

    public String getPrdLogo() {
        return prdLogo;
    }

    public void setPrdLogo(String prdLogo) {
        this.prdLogo = prdLogo == null ? null : prdLogo.trim();
    }

    public String getPrdStatus() {
        return prdStatus;
    }

    public void setPrdStatus(String prdStatus) {
        this.prdStatus = prdStatus == null ? null : prdStatus.trim();
    }

    public BigDecimal getAmtUpper() {
        return amtUpper;
    }

    public void setAmtUpper(BigDecimal amtUpper) {
        this.amtUpper = amtUpper;
    }

    public String getShowTimeStart() {
        return showTimeStart;
    }

    public void setShowTimeStart(String showTimeStart) {
        this.showTimeStart = showTimeStart == null ? null : showTimeStart.trim();
    }

    public String getShowTimeEnd() {
        return showTimeEnd;
    }

    public void setShowTimeEnd(String showTimeEnd) {
        this.showTimeEnd = showTimeEnd == null ? null : showTimeEnd.trim();
    }

    public String getShowHoliday() {
        return showHoliday;
    }

    public void setShowHoliday(String showHoliday) {
        this.showHoliday = showHoliday == null ? null : showHoliday.trim();
    }

    public String getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(String showOrder) {
        this.showOrder = showOrder == null ? null : showOrder.trim();
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl == null ? null : linkUrl.trim();
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}