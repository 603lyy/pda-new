package com.yaheen.pdaapp.bean;

import android.text.TextUtils;

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

        private String id;
        private String houseNumberId;
        private String telephone;
        private String community;
        private String address;
        private String sex;
        private String mobile;
        private String userName;
        private String userId;
        private String peopleNumber;
        private String category;
        private String partyMember;
        private String gloriousArmy;
        private String fiveGuarantees;
        private String beekeepingProfessionals;
        private String fireInspectionPoint;
        private String gridInspectionPoint;
        private String breedingSpecialist;
        private String technologyDemonstration;
        private String precisionPoverty;
        private String civilizationHouseholds;
        private String helper;
        private String agriculturalProductsInformation;

        public String getGloriousArmy() {
            if (!TextUtils.isEmpty(gloriousArmy)) {
                if (gloriousArmy.equals("Y")) {
                    return "是";
                } else if (gloriousArmy.equals("F")) {
                    return "否";
                }
            }
            return gloriousArmy;
        }

        public void setGloriousArmy(String gloriousArmy) {
            this.gloriousArmy = gloriousArmy;
        }

        public String getFiveGuarantees() {
            if (!TextUtils.isEmpty(fiveGuarantees)) {
                if (fiveGuarantees.equals("Y")) {
                    return "是";
                } else if (fiveGuarantees.equals("F")) {
                    return "否";
                }
            }
            return fiveGuarantees;
        }

        public void setFiveGuarantees(String fiveGuarantees) {
            this.fiveGuarantees = fiveGuarantees;
        }

        public String getBeekeepingProfessionals() {
            if (!TextUtils.isEmpty(beekeepingProfessionals)) {
                if (beekeepingProfessionals.equals("Y")) {
                    return "是";
                } else if (beekeepingProfessionals.equals("F")) {
                    return "否";
                }
            }
            return beekeepingProfessionals;
        }

        public void setBeekeepingProfessionals(String beekeepingProfessionals) {
            this.beekeepingProfessionals = beekeepingProfessionals;
        }

        public String getFireInspectionPoint() {
            return fireInspectionPoint;
        }

        public void setFireInspectionPoint(String fireInspectionPoint) {
            this.fireInspectionPoint = fireInspectionPoint;
        }

        public String getGridInspectionPoint() {
            return gridInspectionPoint;
        }

        public void setGridInspectionPoint(String gridInspectionPoint) {
            this.gridInspectionPoint = gridInspectionPoint;
        }

        public String getBreedingSpecialist() {
            if (!TextUtils.isEmpty(breedingSpecialist)) {
                if (breedingSpecialist.equals("Y")) {
                    return "是";
                } else if (breedingSpecialist.equals("F")) {
                    return "否";
                }
            }
            return breedingSpecialist;
        }

        public void setBreedingSpecialist(String breedingSpecialist) {
            this.breedingSpecialist = breedingSpecialist;
        }

        public String getTechnologyDemonstration() {
            if (!TextUtils.isEmpty(technologyDemonstration)) {
                if (technologyDemonstration.equals("Y")) {
                    return "是";
                } else if (technologyDemonstration.equals("F")) {
                    return "否";
                }
            }
            return technologyDemonstration;
        }

        public void setTechnologyDemonstration(String technologyDemonstration) {
            this.technologyDemonstration = technologyDemonstration;
        }

        public String getPrecisionPoverty() {
            if (!TextUtils.isEmpty(precisionPoverty)) {
                if (precisionPoverty.equals("Y")) {
                    return "是";
                } else if (precisionPoverty.equals("F")) {
                    return "否";
                }
            }
            return precisionPoverty;
        }

        public void setPrecisionPoverty(String precisionPoverty) {
            this.precisionPoverty = precisionPoverty;
        }

        public String getCivilizationHouseholds() {
            if (!TextUtils.isEmpty(civilizationHouseholds)) {
                if (civilizationHouseholds.equals("Y")) {
                    return "是";
                } else if (civilizationHouseholds.equals("F")) {
                    return "否";
                }
            }
            return civilizationHouseholds;
        }

        public void setCivilizationHouseholds(String civilizationHouseholds) {
            this.civilizationHouseholds = civilizationHouseholds;
        }

        public String getHelper() {
            return helper;
        }

        public void setHelper(String helper) {
            this.helper = helper;
        }

        public String getAgriculturalProductsInformation() {
            return agriculturalProductsInformation;
        }

        public void setAgriculturalProductsInformation(String agriculturalProductsInformation) {
            this.agriculturalProductsInformation = agriculturalProductsInformation;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
            if (!TextUtils.isEmpty(community)) {
                if (community.equals("A")) {
                    return "行政村";
                } else if (community.equals("N")) {
                    return "自然村";
                }
            }
            return community;
        }

        public void setCommunity(String community) {
            this.community = community;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSex() {
            if(!TextUtils.isEmpty(sex)){
                if(sex.equals("M")){
                    return "男";
                }else if(sex.equals("F")){
                    return "女";
                }
            }
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPeopleNumber() {
            return peopleNumber;
        }

        public void setPeopleNumber(String peopleNumber) {
            this.peopleNumber = peopleNumber;
        }

        public String getCategory() {
            if (!TextUtils.isEmpty(category)) {
                if (category.equals("S")) {
                    return "户主";
                } else if (category.equals("G")) {
                    return "村委";
                } else if (category.equals("B")) {
                    return "商旅";
                } else if (category.equals("M")) {
                    return "导视牌";
                }
            }
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPartyMember() {
            if (!TextUtils.isEmpty(partyMember)) {
                if (partyMember.equals("Y")) {
                    return "是";
                } else if (partyMember.equals("F")) {
                    return "否";
                }
            }
            return partyMember;
        }

        public void setPartyMember(String partyMember) {
            this.partyMember = partyMember;
        }

    }
}
