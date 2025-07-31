package com.palwy.common.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoanSuperConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LoanSuperConfigExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPrdCodeIsNull() {
            addCriterion("prd_code is null");
            return (Criteria) this;
        }

        public Criteria andPrdCodeIsNotNull() {
            addCriterion("prd_code is not null");
            return (Criteria) this;
        }

        public Criteria andPrdCodeEqualTo(String value) {
            addCriterion("prd_code =", value, "prdCode");
            return (Criteria) this;
        }

        public Criteria andPrdCodeNotEqualTo(String value) {
            addCriterion("prd_code <>", value, "prdCode");
            return (Criteria) this;
        }

        public Criteria andPrdCodeGreaterThan(String value) {
            addCriterion("prd_code >", value, "prdCode");
            return (Criteria) this;
        }

        public Criteria andPrdCodeGreaterThanOrEqualTo(String value) {
            addCriterion("prd_code >=", value, "prdCode");
            return (Criteria) this;
        }

        public Criteria andPrdCodeLessThan(String value) {
            addCriterion("prd_code <", value, "prdCode");
            return (Criteria) this;
        }

        public Criteria andPrdCodeLessThanOrEqualTo(String value) {
            addCriterion("prd_code <=", value, "prdCode");
            return (Criteria) this;
        }

        public Criteria andPrdCodeLike(String value) {
            addCriterion("prd_code like", value, "prdCode");
            return (Criteria) this;
        }

        public Criteria andPrdCodeNotLike(String value) {
            addCriterion("prd_code not like", value, "prdCode");
            return (Criteria) this;
        }

        public Criteria andPrdCodeIn(List<String> values) {
            addCriterion("prd_code in", values, "prdCode");
            return (Criteria) this;
        }

        public Criteria andPrdCodeNotIn(List<String> values) {
            addCriterion("prd_code not in", values, "prdCode");
            return (Criteria) this;
        }

        public Criteria andPrdCodeBetween(String value1, String value2) {
            addCriterion("prd_code between", value1, value2, "prdCode");
            return (Criteria) this;
        }

        public Criteria andPrdCodeNotBetween(String value1, String value2) {
            addCriterion("prd_code not between", value1, value2, "prdCode");
            return (Criteria) this;
        }

        public Criteria andPrdNameIsNull() {
            addCriterion("prd_name is null");
            return (Criteria) this;
        }

        public Criteria andPrdNameIsNotNull() {
            addCriterion("prd_name is not null");
            return (Criteria) this;
        }

        public Criteria andPrdNameEqualTo(String value) {
            addCriterion("prd_name =", value, "prdName");
            return (Criteria) this;
        }

        public Criteria andPrdNameNotEqualTo(String value) {
            addCriterion("prd_name <>", value, "prdName");
            return (Criteria) this;
        }

        public Criteria andPrdNameGreaterThan(String value) {
            addCriterion("prd_name >", value, "prdName");
            return (Criteria) this;
        }

        public Criteria andPrdNameGreaterThanOrEqualTo(String value) {
            addCriterion("prd_name >=", value, "prdName");
            return (Criteria) this;
        }

        public Criteria andPrdNameLessThan(String value) {
            addCriterion("prd_name <", value, "prdName");
            return (Criteria) this;
        }

        public Criteria andPrdNameLessThanOrEqualTo(String value) {
            addCriterion("prd_name <=", value, "prdName");
            return (Criteria) this;
        }

        public Criteria andPrdNameLike(String value) {
            addCriterion("prd_name like", value, "prdName");
            return (Criteria) this;
        }

        public Criteria andPrdNameNotLike(String value) {
            addCriterion("prd_name not like", value, "prdName");
            return (Criteria) this;
        }

        public Criteria andPrdNameIn(List<String> values) {
            addCriterion("prd_name in", values, "prdName");
            return (Criteria) this;
        }

        public Criteria andPrdNameNotIn(List<String> values) {
            addCriterion("prd_name not in", values, "prdName");
            return (Criteria) this;
        }

        public Criteria andPrdNameBetween(String value1, String value2) {
            addCriterion("prd_name between", value1, value2, "prdName");
            return (Criteria) this;
        }

        public Criteria andPrdNameNotBetween(String value1, String value2) {
            addCriterion("prd_name not between", value1, value2, "prdName");
            return (Criteria) this;
        }

        public Criteria andPrdTagIsNull() {
            addCriterion("prd_tag is null");
            return (Criteria) this;
        }

        public Criteria andPrdTagIsNotNull() {
            addCriterion("prd_tag is not null");
            return (Criteria) this;
        }

        public Criteria andPrdTagEqualTo(String value) {
            addCriterion("prd_tag =", value, "prdTag");
            return (Criteria) this;
        }

        public Criteria andPrdTagNotEqualTo(String value) {
            addCriterion("prd_tag <>", value, "prdTag");
            return (Criteria) this;
        }

        public Criteria andPrdTagGreaterThan(String value) {
            addCriterion("prd_tag >", value, "prdTag");
            return (Criteria) this;
        }

        public Criteria andPrdTagGreaterThanOrEqualTo(String value) {
            addCriterion("prd_tag >=", value, "prdTag");
            return (Criteria) this;
        }

        public Criteria andPrdTagLessThan(String value) {
            addCriterion("prd_tag <", value, "prdTag");
            return (Criteria) this;
        }

        public Criteria andPrdTagLessThanOrEqualTo(String value) {
            addCriterion("prd_tag <=", value, "prdTag");
            return (Criteria) this;
        }

        public Criteria andPrdTagLike(String value) {
            addCriterion("prd_tag like", value, "prdTag");
            return (Criteria) this;
        }

        public Criteria andPrdTagNotLike(String value) {
            addCriterion("prd_tag not like", value, "prdTag");
            return (Criteria) this;
        }

        public Criteria andPrdTagIn(List<String> values) {
            addCriterion("prd_tag in", values, "prdTag");
            return (Criteria) this;
        }

        public Criteria andPrdTagNotIn(List<String> values) {
            addCriterion("prd_tag not in", values, "prdTag");
            return (Criteria) this;
        }

        public Criteria andPrdTagBetween(String value1, String value2) {
            addCriterion("prd_tag between", value1, value2, "prdTag");
            return (Criteria) this;
        }

        public Criteria andPrdTagNotBetween(String value1, String value2) {
            addCriterion("prd_tag not between", value1, value2, "prdTag");
            return (Criteria) this;
        }

        public Criteria andPrdLogoIsNull() {
            addCriterion("prd_logo is null");
            return (Criteria) this;
        }

        public Criteria andPrdLogoIsNotNull() {
            addCriterion("prd_logo is not null");
            return (Criteria) this;
        }

        public Criteria andPrdLogoEqualTo(String value) {
            addCriterion("prd_logo =", value, "prdLogo");
            return (Criteria) this;
        }

        public Criteria andPrdLogoNotEqualTo(String value) {
            addCriterion("prd_logo <>", value, "prdLogo");
            return (Criteria) this;
        }

        public Criteria andPrdLogoGreaterThan(String value) {
            addCriterion("prd_logo >", value, "prdLogo");
            return (Criteria) this;
        }

        public Criteria andPrdLogoGreaterThanOrEqualTo(String value) {
            addCriterion("prd_logo >=", value, "prdLogo");
            return (Criteria) this;
        }

        public Criteria andPrdLogoLessThan(String value) {
            addCriterion("prd_logo <", value, "prdLogo");
            return (Criteria) this;
        }

        public Criteria andPrdLogoLessThanOrEqualTo(String value) {
            addCriterion("prd_logo <=", value, "prdLogo");
            return (Criteria) this;
        }

        public Criteria andPrdLogoLike(String value) {
            addCriterion("prd_logo like", value, "prdLogo");
            return (Criteria) this;
        }

        public Criteria andPrdLogoNotLike(String value) {
            addCriterion("prd_logo not like", value, "prdLogo");
            return (Criteria) this;
        }

        public Criteria andPrdLogoIn(List<String> values) {
            addCriterion("prd_logo in", values, "prdLogo");
            return (Criteria) this;
        }

        public Criteria andPrdLogoNotIn(List<String> values) {
            addCriterion("prd_logo not in", values, "prdLogo");
            return (Criteria) this;
        }

        public Criteria andPrdLogoBetween(String value1, String value2) {
            addCriterion("prd_logo between", value1, value2, "prdLogo");
            return (Criteria) this;
        }

        public Criteria andPrdLogoNotBetween(String value1, String value2) {
            addCriterion("prd_logo not between", value1, value2, "prdLogo");
            return (Criteria) this;
        }

        public Criteria andPrdStatusIsNull() {
            addCriterion("prd_status is null");
            return (Criteria) this;
        }

        public Criteria andPrdStatusIsNotNull() {
            addCriterion("prd_status is not null");
            return (Criteria) this;
        }

        public Criteria andPrdStatusEqualTo(String value) {
            addCriterion("prd_status =", value, "prdStatus");
            return (Criteria) this;
        }

        public Criteria andPrdStatusNotEqualTo(String value) {
            addCriterion("prd_status <>", value, "prdStatus");
            return (Criteria) this;
        }

        public Criteria andPrdStatusGreaterThan(String value) {
            addCriterion("prd_status >", value, "prdStatus");
            return (Criteria) this;
        }

        public Criteria andPrdStatusGreaterThanOrEqualTo(String value) {
            addCriterion("prd_status >=", value, "prdStatus");
            return (Criteria) this;
        }

        public Criteria andPrdStatusLessThan(String value) {
            addCriterion("prd_status <", value, "prdStatus");
            return (Criteria) this;
        }

        public Criteria andPrdStatusLessThanOrEqualTo(String value) {
            addCriterion("prd_status <=", value, "prdStatus");
            return (Criteria) this;
        }

        public Criteria andPrdStatusLike(String value) {
            addCriterion("prd_status like", value, "prdStatus");
            return (Criteria) this;
        }

        public Criteria andPrdStatusNotLike(String value) {
            addCriterion("prd_status not like", value, "prdStatus");
            return (Criteria) this;
        }

        public Criteria andPrdStatusIn(List<String> values) {
            addCriterion("prd_status in", values, "prdStatus");
            return (Criteria) this;
        }

        public Criteria andPrdStatusNotIn(List<String> values) {
            addCriterion("prd_status not in", values, "prdStatus");
            return (Criteria) this;
        }

        public Criteria andPrdStatusBetween(String value1, String value2) {
            addCriterion("prd_status between", value1, value2, "prdStatus");
            return (Criteria) this;
        }

        public Criteria andPrdStatusNotBetween(String value1, String value2) {
            addCriterion("prd_status not between", value1, value2, "prdStatus");
            return (Criteria) this;
        }

        public Criteria andAmtUpperIsNull() {
            addCriterion("amt_upper is null");
            return (Criteria) this;
        }

        public Criteria andAmtUpperIsNotNull() {
            addCriterion("amt_upper is not null");
            return (Criteria) this;
        }

        public Criteria andAmtUpperEqualTo(BigDecimal value) {
            addCriterion("amt_upper =", value, "amtUpper");
            return (Criteria) this;
        }

        public Criteria andAmtUpperNotEqualTo(BigDecimal value) {
            addCriterion("amt_upper <>", value, "amtUpper");
            return (Criteria) this;
        }

        public Criteria andAmtUpperGreaterThan(BigDecimal value) {
            addCriterion("amt_upper >", value, "amtUpper");
            return (Criteria) this;
        }

        public Criteria andAmtUpperGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amt_upper >=", value, "amtUpper");
            return (Criteria) this;
        }

        public Criteria andAmtUpperLessThan(BigDecimal value) {
            addCriterion("amt_upper <", value, "amtUpper");
            return (Criteria) this;
        }

        public Criteria andAmtUpperLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amt_upper <=", value, "amtUpper");
            return (Criteria) this;
        }

        public Criteria andAmtUpperIn(List<BigDecimal> values) {
            addCriterion("amt_upper in", values, "amtUpper");
            return (Criteria) this;
        }

        public Criteria andAmtUpperNotIn(List<BigDecimal> values) {
            addCriterion("amt_upper not in", values, "amtUpper");
            return (Criteria) this;
        }

        public Criteria andAmtUpperBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amt_upper between", value1, value2, "amtUpper");
            return (Criteria) this;
        }

        public Criteria andAmtUpperNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amt_upper not between", value1, value2, "amtUpper");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartIsNull() {
            addCriterion("show_time_start is null");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartIsNotNull() {
            addCriterion("show_time_start is not null");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartEqualTo(String value) {
            addCriterion("show_time_start =", value, "showTimeStart");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartNotEqualTo(String value) {
            addCriterion("show_time_start <>", value, "showTimeStart");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartGreaterThan(String value) {
            addCriterion("show_time_start >", value, "showTimeStart");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartGreaterThanOrEqualTo(String value) {
            addCriterion("show_time_start >=", value, "showTimeStart");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartLessThan(String value) {
            addCriterion("show_time_start <", value, "showTimeStart");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartLessThanOrEqualTo(String value) {
            addCriterion("show_time_start <=", value, "showTimeStart");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartLike(String value) {
            addCriterion("show_time_start like", value, "showTimeStart");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartNotLike(String value) {
            addCriterion("show_time_start not like", value, "showTimeStart");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartIn(List<String> values) {
            addCriterion("show_time_start in", values, "showTimeStart");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartNotIn(List<String> values) {
            addCriterion("show_time_start not in", values, "showTimeStart");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartBetween(String value1, String value2) {
            addCriterion("show_time_start between", value1, value2, "showTimeStart");
            return (Criteria) this;
        }

        public Criteria andShowTimeStartNotBetween(String value1, String value2) {
            addCriterion("show_time_start not between", value1, value2, "showTimeStart");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndIsNull() {
            addCriterion("show_time_end is null");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndIsNotNull() {
            addCriterion("show_time_end is not null");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndEqualTo(String value) {
            addCriterion("show_time_end =", value, "showTimeEnd");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndNotEqualTo(String value) {
            addCriterion("show_time_end <>", value, "showTimeEnd");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndGreaterThan(String value) {
            addCriterion("show_time_end >", value, "showTimeEnd");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndGreaterThanOrEqualTo(String value) {
            addCriterion("show_time_end >=", value, "showTimeEnd");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndLessThan(String value) {
            addCriterion("show_time_end <", value, "showTimeEnd");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndLessThanOrEqualTo(String value) {
            addCriterion("show_time_end <=", value, "showTimeEnd");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndLike(String value) {
            addCriterion("show_time_end like", value, "showTimeEnd");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndNotLike(String value) {
            addCriterion("show_time_end not like", value, "showTimeEnd");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndIn(List<String> values) {
            addCriterion("show_time_end in", values, "showTimeEnd");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndNotIn(List<String> values) {
            addCriterion("show_time_end not in", values, "showTimeEnd");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndBetween(String value1, String value2) {
            addCriterion("show_time_end between", value1, value2, "showTimeEnd");
            return (Criteria) this;
        }

        public Criteria andShowTimeEndNotBetween(String value1, String value2) {
            addCriterion("show_time_end not between", value1, value2, "showTimeEnd");
            return (Criteria) this;
        }

        public Criteria andShowHolidayIsNull() {
            addCriterion("show_holiday is null");
            return (Criteria) this;
        }

        public Criteria andShowHolidayIsNotNull() {
            addCriterion("show_holiday is not null");
            return (Criteria) this;
        }

        public Criteria andShowHolidayEqualTo(String value) {
            addCriterion("show_holiday =", value, "showHoliday");
            return (Criteria) this;
        }

        public Criteria andShowHolidayNotEqualTo(String value) {
            addCriterion("show_holiday <>", value, "showHoliday");
            return (Criteria) this;
        }

        public Criteria andShowHolidayGreaterThan(String value) {
            addCriterion("show_holiday >", value, "showHoliday");
            return (Criteria) this;
        }

        public Criteria andShowHolidayGreaterThanOrEqualTo(String value) {
            addCriterion("show_holiday >=", value, "showHoliday");
            return (Criteria) this;
        }

        public Criteria andShowHolidayLessThan(String value) {
            addCriterion("show_holiday <", value, "showHoliday");
            return (Criteria) this;
        }

        public Criteria andShowHolidayLessThanOrEqualTo(String value) {
            addCriterion("show_holiday <=", value, "showHoliday");
            return (Criteria) this;
        }

        public Criteria andShowHolidayLike(String value) {
            addCriterion("show_holiday like", value, "showHoliday");
            return (Criteria) this;
        }

        public Criteria andShowHolidayNotLike(String value) {
            addCriterion("show_holiday not like", value, "showHoliday");
            return (Criteria) this;
        }

        public Criteria andShowHolidayIn(List<String> values) {
            addCriterion("show_holiday in", values, "showHoliday");
            return (Criteria) this;
        }

        public Criteria andShowHolidayNotIn(List<String> values) {
            addCriterion("show_holiday not in", values, "showHoliday");
            return (Criteria) this;
        }

        public Criteria andShowHolidayBetween(String value1, String value2) {
            addCriterion("show_holiday between", value1, value2, "showHoliday");
            return (Criteria) this;
        }

        public Criteria andShowHolidayNotBetween(String value1, String value2) {
            addCriterion("show_holiday not between", value1, value2, "showHoliday");
            return (Criteria) this;
        }

        public Criteria andShowOrderIsNull() {
            addCriterion("show_order is null");
            return (Criteria) this;
        }

        public Criteria andShowOrderIsNotNull() {
            addCriterion("show_order is not null");
            return (Criteria) this;
        }

        public Criteria andShowOrderEqualTo(String value) {
            addCriterion("show_order =", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderNotEqualTo(String value) {
            addCriterion("show_order <>", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderGreaterThan(String value) {
            addCriterion("show_order >", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderGreaterThanOrEqualTo(String value) {
            addCriterion("show_order >=", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderLessThan(String value) {
            addCriterion("show_order <", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderLessThanOrEqualTo(String value) {
            addCriterion("show_order <=", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderLike(String value) {
            addCriterion("show_order like", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderNotLike(String value) {
            addCriterion("show_order not like", value, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderIn(List<String> values) {
            addCriterion("show_order in", values, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderNotIn(List<String> values) {
            addCriterion("show_order not in", values, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderBetween(String value1, String value2) {
            addCriterion("show_order between", value1, value2, "showOrder");
            return (Criteria) this;
        }

        public Criteria andShowOrderNotBetween(String value1, String value2) {
            addCriterion("show_order not between", value1, value2, "showOrder");
            return (Criteria) this;
        }

        public Criteria andLinkUrlIsNull() {
            addCriterion("link_url is null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlIsNotNull() {
            addCriterion("link_url is not null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlEqualTo(String value) {
            addCriterion("link_url =", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlNotEqualTo(String value) {
            addCriterion("link_url <>", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlGreaterThan(String value) {
            addCriterion("link_url >", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlGreaterThanOrEqualTo(String value) {
            addCriterion("link_url >=", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlLessThan(String value) {
            addCriterion("link_url <", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlLessThanOrEqualTo(String value) {
            addCriterion("link_url <=", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlLike(String value) {
            addCriterion("link_url like", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlNotLike(String value) {
            addCriterion("link_url not like", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlIn(List<String> values) {
            addCriterion("link_url in", values, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlNotIn(List<String> values) {
            addCriterion("link_url not in", values, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlBetween(String value1, String value2) {
            addCriterion("link_url between", value1, value2, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlNotBetween(String value1, String value2) {
            addCriterion("link_url not between", value1, value2, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(String value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(String value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(String value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(String value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(String value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(String value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLike(String value) {
            addCriterion("is_deleted like", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotLike(String value) {
            addCriterion("is_deleted not like", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<String> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<String> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(String value1, String value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(String value1, String value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIsNull() {
            addCriterion("gmt_created is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIsNotNull() {
            addCriterion("gmt_created is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedEqualTo(Date value) {
            addCriterion("gmt_created =", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotEqualTo(Date value) {
            addCriterion("gmt_created <>", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedGreaterThan(Date value) {
            addCriterion("gmt_created >", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_created >=", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedLessThan(Date value) {
            addCriterion("gmt_created <", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_created <=", value, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedIn(List<Date> values) {
            addCriterion("gmt_created in", values, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotIn(List<Date> values) {
            addCriterion("gmt_created not in", values, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedBetween(Date value1, Date value2) {
            addCriterion("gmt_created between", value1, value2, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andGmtCreatedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_created not between", value1, value2, "gmtCreated");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("modifier like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("modifier not like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}