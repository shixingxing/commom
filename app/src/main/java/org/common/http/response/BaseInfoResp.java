package org.common.http.response;

import com.google.gson.annotations.SerializedName;

import org.common.model.BaseInfo;

import lombok.Data;

/**
 * Created by peter on 2016/3/28.
 */
@Data
public class BaseInfoResp {

    @SerializedName("result")
    private Result result;

    public class Result {
        @SerializedName("basicVO")
        private BaseInfo baseInfo;
    }
}
