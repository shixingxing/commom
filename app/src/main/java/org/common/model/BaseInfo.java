package org.common.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class BaseInfo {
    // 我的工作
    @SerializedName("orgName")
    private String companyName;
    @SerializedName("orgAddr")
    private String companyAddress;
    private Province companyProvince;
    private City companyCity;
    private District companyDistrict;
    @SerializedName("orgPhn")
    private String companyPhone;// 完整号码
    // 我的住址
    private Province homeProvince;
    private City homeCity;
    private District homeDistrict;
    private String homeAddress;
    // 联系人
    @SerializedName("relativeName")
    private String familyName;
    @SerializedName("relativePhn")
    private String familyPhone;
    @SerializedName("friendName")
    private String friendName;
    @SerializedName("friendPhn")
    private String friendPhone;
    @SerializedName("colleagueName")
    private String workmateName;
    @SerializedName("colleaguePhn")
    private String workmatePhone;

}
