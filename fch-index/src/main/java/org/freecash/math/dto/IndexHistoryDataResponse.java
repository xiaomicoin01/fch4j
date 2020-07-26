package org.freecash.math.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanglint
 * @date 2020/7/13 21:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexHistoryDataResponse {
    private int code;
    private String message;
    private List<IndexHistoryDataResponseData> data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class IndexHistoryDataResponseData{
        private String mc;
        private String date;
        private String stockCode;
        private IndexHistoryDataResponseDataItem pb;
        @JSONField(name="pe_ttm")
        private IndexHistoryDataResponseDataItem peTtm;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class IndexHistoryDataResponseDataItem{
        private IndexHistoryDataResponseDataItemY10 y10;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class IndexHistoryDataResponseDataItemY10{
        private IndexHistoryDataResponseDataItemY10Mcw mcw;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class IndexHistoryDataResponseDataItemY10Mcw{
        private String avgv;
        private String cv;
        private String cvpos;
        private String maxpv;
        private String maxv;
        private String maxvd;
        private String minv;
        private String minvd;
        private String q2v;
        private String q5v;
        private String q8v;
    }
}


