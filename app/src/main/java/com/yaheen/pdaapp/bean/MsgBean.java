package com.yaheen.pdaapp.bean;

import java.io.Serializable;
import java.util.List;

public class MsgBean implements Serializable {

    private static final long serialVersionUID = 5602235794418727193L;

    /**
     * result : true
     * msg : save success
     * entity : {"modifiedUserName":"","createUserId":"","groupPropertyNameList":[],"deleteUnitId":"","operatedDate":"","createUserName":"","operatedUnitCode":"","operatedUnitName":"","operateRelationMap":{},"operateRelation":{},"criterionAlias":"","modifiedUserCode":"","deleteUserCode":"","id":"00000000606e477101606e7c9d99005d","longModifiedDate":0,"operateValue":{},"longDeleteDate":0,"operatedName":"","defCriterion":null,"houseNumberId":"000000006069cae601606e261d17013e","telephone":"11110000001","community":"A","createUnitCode":"","modifiedUnitCode":"","filterMap":{},"excludeCopyValueFieldNames":["operatedUnitCode","operatedUnitName","operatedUnitId","createUnitCode","createUnitName","createUnitId","modifiedUnitCode","modifiedUnitName","modifiedUnitId","deleteUnitCode","deleteUnitName","deleteUnitId","longOperatedDate","longCreateDate","longModifiedDate","longDeleteDate","operatedName","operatedUserCode","createName","createUserCode","modifiedName","modifiedUserCode","deleteName","deleteUserCode","operatedUserName","operatedUserId","operatedDate","createUserName","createUserId","modifiedUserName","modifiedUserId","deleteUserName","deleteUserId","createDate","modifiedDate","deleteDate","delete","criteriaLogicExpression","operateRelationMap","operateValue","defCriterion","onlyCopyValueFieldNames","excludeCopyValueFieldNames","criterionAlias","criterionOrders","filterMap","groupPropertyNameList","modelDTOSearchOnField","dealRemaind","criteriaConfig","interrupt"],"modifiedDate":"","criteriaLogicExpression":"","createName":"","modifiedUnitId":"","status":"","createUnitId":"","modifiedName":"","createUnitName":"","flag":"","operatedUserName":"","longCreateDate":0,"criterionOrders":[],"criteriaConfig":null,"remark":"","delete":false,"modelDTOSearchOnField":false,"operatedUserId":"","modifiedUnitName":"","deleteUnitName":"","deleteUserId":"","createDate":"","deleteDate":"","address":"万东南村1号","createUserCode":"","sex":"M","mobile":"","userName":"陈职东","operatedUnitId":"","userId":"00000000606e477101606e559fa8000c","dealRemaind":true,"longOperatedDate":0,"deleteUnitCode":"","modifiedUserId":"","operatedUserCode":"","peopleNumber":"12","onlyCopyValueFieldNames":[],"category":"S","deleteName":"","deleteUserName":"","partyMember":"F"}
     */

    private boolean result;
    private String msg;
    private EntityBean entity;

    //实体类变量的个数
    public static int num = 20;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public EntityBean getEntity() {
        return entity;
    }

    public void setEntity(EntityBean entity) {
        this.entity = entity;
    }

    public static class EntityBean {
        /**
         * modifiedUserName :
         * createUserId :
         * groupPropertyNameList : []
         * deleteUnitId :
         * operatedDate :
         * createUserName :
         * operatedUnitCode :
         * operatedUnitName :
         * operateRelationMap : {}
         * operateRelation : {}
         * criterionAlias :
         * modifiedUserCode :
         * deleteUserCode :
         * id : 00000000606e477101606e7c9d99005d
         * longModifiedDate : 0
         * operateValue : {}
         * longDeleteDate : 0
         * operatedName :
         * defCriterion : null
         * houseNumberId : 000000006069cae601606e261d17013e
         * telephone : 11110000001
         * community : A
         * createUnitCode :
         * modifiedUnitCode :
         * filterMap : {}
         * excludeCopyValueFieldNames : ["operatedUnitCode","operatedUnitName","operatedUnitId","createUnitCode","createUnitName","createUnitId","modifiedUnitCode","modifiedUnitName","modifiedUnitId","deleteUnitCode","deleteUnitName","deleteUnitId","longOperatedDate","longCreateDate","longModifiedDate","longDeleteDate","operatedName","operatedUserCode","createName","createUserCode","modifiedName","modifiedUserCode","deleteName","deleteUserCode","operatedUserName","operatedUserId","operatedDate","createUserName","createUserId","modifiedUserName","modifiedUserId","deleteUserName","deleteUserId","createDate","modifiedDate","deleteDate","delete","criteriaLogicExpression","operateRelationMap","operateValue","defCriterion","onlyCopyValueFieldNames","excludeCopyValueFieldNames","criterionAlias","criterionOrders","filterMap","groupPropertyNameList","modelDTOSearchOnField","dealRemaind","criteriaConfig","interrupt"]
         * modifiedDate :
         * criteriaLogicExpression :
         * createName :
         * modifiedUnitId :
         * status :
         * createUnitId :
         * modifiedName :
         * createUnitName :
         * flag :
         * operatedUserName :
         * longCreateDate : 0
         * criterionOrders : []
         * criteriaConfig : null
         * remark :
         * delete : false
         * modelDTOSearchOnField : false
         * operatedUserId :
         * modifiedUnitName :
         * deleteUnitName :
         * deleteUserId :
         * createDate :
         * deleteDate :
         * address : 万东南村1号
         * createUserCode :
         * sex : M
         * mobile :
         * userName : 陈职东
         * operatedUnitId :
         * userId : 00000000606e477101606e559fa8000c
         * dealRemaind : true
         * longOperatedDate : 0
         * deleteUnitCode :
         * modifiedUserId :
         * operatedUserCode :
         * peopleNumber : 12
         * onlyCopyValueFieldNames : []
         * category : S
         * deleteName :
         * deleteUserName :
         * partyMember : F
         */

        private String modifiedUserName;
        private String createUserId;
        private String deleteUnitId;
        private String operatedDate;
        private String createUserName;
        private String operatedUnitCode;
        private String operatedUnitName;
        private OperateRelationMapBean operateRelationMap;
        private OperateRelationBean operateRelation;
        private String criterionAlias;
        private String modifiedUserCode;
        private String deleteUserCode;
        private String id;
        private int longModifiedDate;
        private OperateValueBean operateValue;
        private int longDeleteDate;
        private String operatedName;
        private Object defCriterion;
        private String houseNumberId;
        private String telephone;
        private String community;
        private String createUnitCode;
        private String modifiedUnitCode;
        private FilterMapBean filterMap;
        private String modifiedDate;
        private String criteriaLogicExpression;
        private String createName;
        private String modifiedUnitId;
        private String status;
        private String createUnitId;
        private String modifiedName;
        private String createUnitName;
        private String flag;
        private String operatedUserName;
        private int longCreateDate;
        private Object criteriaConfig;
        private String remark;
        private boolean delete;
        private boolean modelDTOSearchOnField;
        private String operatedUserId;
        private String modifiedUnitName;
        private String deleteUnitName;
        private String deleteUserId;
        private String createDate;
        private String deleteDate;
        private String address;
        private String createUserCode;
        private String sex;
        private String mobile;
        private String userName;
        private String operatedUnitId;
        private String userId;
        private boolean dealRemaind;
        private int longOperatedDate;
        private String deleteUnitCode;
        private String modifiedUserId;
        private String operatedUserCode;
        private String peopleNumber;
        private String category;
        private String deleteName;
        private String deleteUserName;
        private String partyMember;
        private List<?> groupPropertyNameList;
        private List<String> excludeCopyValueFieldNames;
        private List<?> criterionOrders;
        private List<?> onlyCopyValueFieldNames;

        public String getModifiedUserName() {
            return modifiedUserName;
        }

        public void setModifiedUserName(String modifiedUserName) {
            this.modifiedUserName = modifiedUserName;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getDeleteUnitId() {
            return deleteUnitId;
        }

        public void setDeleteUnitId(String deleteUnitId) {
            this.deleteUnitId = deleteUnitId;
        }

        public String getOperatedDate() {
            return operatedDate;
        }

        public void setOperatedDate(String operatedDate) {
            this.operatedDate = operatedDate;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getOperatedUnitCode() {
            return operatedUnitCode;
        }

        public void setOperatedUnitCode(String operatedUnitCode) {
            this.operatedUnitCode = operatedUnitCode;
        }

        public String getOperatedUnitName() {
            return operatedUnitName;
        }

        public void setOperatedUnitName(String operatedUnitName) {
            this.operatedUnitName = operatedUnitName;
        }

        public OperateRelationMapBean getOperateRelationMap() {
            return operateRelationMap;
        }

        public void setOperateRelationMap(OperateRelationMapBean operateRelationMap) {
            this.operateRelationMap = operateRelationMap;
        }

        public OperateRelationBean getOperateRelation() {
            return operateRelation;
        }

        public void setOperateRelation(OperateRelationBean operateRelation) {
            this.operateRelation = operateRelation;
        }

        public String getCriterionAlias() {
            return criterionAlias;
        }

        public void setCriterionAlias(String criterionAlias) {
            this.criterionAlias = criterionAlias;
        }

        public String getModifiedUserCode() {
            return modifiedUserCode;
        }

        public void setModifiedUserCode(String modifiedUserCode) {
            this.modifiedUserCode = modifiedUserCode;
        }

        public String getDeleteUserCode() {
            return deleteUserCode;
        }

        public void setDeleteUserCode(String deleteUserCode) {
            this.deleteUserCode = deleteUserCode;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLongModifiedDate() {
            return longModifiedDate;
        }

        public void setLongModifiedDate(int longModifiedDate) {
            this.longModifiedDate = longModifiedDate;
        }

        public OperateValueBean getOperateValue() {
            return operateValue;
        }

        public void setOperateValue(OperateValueBean operateValue) {
            this.operateValue = operateValue;
        }

        public int getLongDeleteDate() {
            return longDeleteDate;
        }

        public void setLongDeleteDate(int longDeleteDate) {
            this.longDeleteDate = longDeleteDate;
        }

        public String getOperatedName() {
            return operatedName;
        }

        public void setOperatedName(String operatedName) {
            this.operatedName = operatedName;
        }

        public Object getDefCriterion() {
            return defCriterion;
        }

        public void setDefCriterion(Object defCriterion) {
            this.defCriterion = defCriterion;
        }

        public String getHouseNumberId() {
            return houseNumberId;
        }

        public void setHouseNumberId(String houseNumberId) {
            this.houseNumberId = houseNumberId;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getCommunity() {
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getCreateUnitCode() {
            return createUnitCode;
        }

        public void setCreateUnitCode(String createUnitCode) {
            this.createUnitCode = createUnitCode;
        }

        public String getModifiedUnitCode() {
            return modifiedUnitCode;
        }

        public void setModifiedUnitCode(String modifiedUnitCode) {
            this.modifiedUnitCode = modifiedUnitCode;
        }

        public FilterMapBean getFilterMap() {
            return filterMap;
        }

        public void setFilterMap(FilterMapBean filterMap) {
            this.filterMap = filterMap;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public String getCriteriaLogicExpression() {
            return criteriaLogicExpression;
        }

        public void setCriteriaLogicExpression(String criteriaLogicExpression) {
            this.criteriaLogicExpression = criteriaLogicExpression;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getModifiedUnitId() {
            return modifiedUnitId;
        }

        public void setModifiedUnitId(String modifiedUnitId) {
            this.modifiedUnitId = modifiedUnitId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateUnitId() {
            return createUnitId;
        }

        public void setCreateUnitId(String createUnitId) {
            this.createUnitId = createUnitId;
        }

        public String getModifiedName() {
            return modifiedName;
        }

        public void setModifiedName(String modifiedName) {
            this.modifiedName = modifiedName;
        }

        public String getCreateUnitName() {
            return createUnitName;
        }

        public void setCreateUnitName(String createUnitName) {
            this.createUnitName = createUnitName;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getOperatedUserName() {
            return operatedUserName;
        }

        public void setOperatedUserName(String operatedUserName) {
            this.operatedUserName = operatedUserName;
        }

        public int getLongCreateDate() {
            return longCreateDate;
        }

        public void setLongCreateDate(int longCreateDate) {
            this.longCreateDate = longCreateDate;
        }

        public Object getCriteriaConfig() {
            return criteriaConfig;
        }

        public void setCriteriaConfig(Object criteriaConfig) {
            this.criteriaConfig = criteriaConfig;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public boolean isDelete() {
            return delete;
        }

        public void setDelete(boolean delete) {
            this.delete = delete;
        }

        public boolean isModelDTOSearchOnField() {
            return modelDTOSearchOnField;
        }

        public void setModelDTOSearchOnField(boolean modelDTOSearchOnField) {
            this.modelDTOSearchOnField = modelDTOSearchOnField;
        }

        public String getOperatedUserId() {
            return operatedUserId;
        }

        public void setOperatedUserId(String operatedUserId) {
            this.operatedUserId = operatedUserId;
        }

        public String getModifiedUnitName() {
            return modifiedUnitName;
        }

        public void setModifiedUnitName(String modifiedUnitName) {
            this.modifiedUnitName = modifiedUnitName;
        }

        public String getDeleteUnitName() {
            return deleteUnitName;
        }

        public void setDeleteUnitName(String deleteUnitName) {
            this.deleteUnitName = deleteUnitName;
        }

        public String getDeleteUserId() {
            return deleteUserId;
        }

        public void setDeleteUserId(String deleteUserId) {
            this.deleteUserId = deleteUserId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getDeleteDate() {
            return deleteDate;
        }

        public void setDeleteDate(String deleteDate) {
            this.deleteDate = deleteDate;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCreateUserCode() {
            return createUserCode;
        }

        public void setCreateUserCode(String createUserCode) {
            this.createUserCode = createUserCode;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getOperatedUnitId() {
            return operatedUnitId;
        }

        public void setOperatedUnitId(String operatedUnitId) {
            this.operatedUnitId = operatedUnitId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public boolean isDealRemaind() {
            return dealRemaind;
        }

        public void setDealRemaind(boolean dealRemaind) {
            this.dealRemaind = dealRemaind;
        }

        public int getLongOperatedDate() {
            return longOperatedDate;
        }

        public void setLongOperatedDate(int longOperatedDate) {
            this.longOperatedDate = longOperatedDate;
        }

        public String getDeleteUnitCode() {
            return deleteUnitCode;
        }

        public void setDeleteUnitCode(String deleteUnitCode) {
            this.deleteUnitCode = deleteUnitCode;
        }

        public String getModifiedUserId() {
            return modifiedUserId;
        }

        public void setModifiedUserId(String modifiedUserId) {
            this.modifiedUserId = modifiedUserId;
        }

        public String getOperatedUserCode() {
            return operatedUserCode;
        }

        public void setOperatedUserCode(String operatedUserCode) {
            this.operatedUserCode = operatedUserCode;
        }

        public String getPeopleNumber() {
            return peopleNumber;
        }

        public void setPeopleNumber(String peopleNumber) {
            this.peopleNumber = peopleNumber;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDeleteName() {
            return deleteName;
        }

        public void setDeleteName(String deleteName) {
            this.deleteName = deleteName;
        }

        public String getDeleteUserName() {
            return deleteUserName;
        }

        public void setDeleteUserName(String deleteUserName) {
            this.deleteUserName = deleteUserName;
        }

        public String getPartyMember() {
            return partyMember;
        }

        public void setPartyMember(String partyMember) {
            this.partyMember = partyMember;
        }

        public List<?> getGroupPropertyNameList() {
            return groupPropertyNameList;
        }

        public void setGroupPropertyNameList(List<?> groupPropertyNameList) {
            this.groupPropertyNameList = groupPropertyNameList;
        }

        public List<String> getExcludeCopyValueFieldNames() {
            return excludeCopyValueFieldNames;
        }

        public void setExcludeCopyValueFieldNames(List<String> excludeCopyValueFieldNames) {
            this.excludeCopyValueFieldNames = excludeCopyValueFieldNames;
        }

        public List<?> getCriterionOrders() {
            return criterionOrders;
        }

        public void setCriterionOrders(List<?> criterionOrders) {
            this.criterionOrders = criterionOrders;
        }

        public List<?> getOnlyCopyValueFieldNames() {
            return onlyCopyValueFieldNames;
        }

        public void setOnlyCopyValueFieldNames(List<?> onlyCopyValueFieldNames) {
            this.onlyCopyValueFieldNames = onlyCopyValueFieldNames;
        }

        public static class OperateRelationMapBean {
        }

        public static class OperateRelationBean {
        }

        public static class OperateValueBean {
        }

        public static class FilterMapBean {
        }
    }
}
