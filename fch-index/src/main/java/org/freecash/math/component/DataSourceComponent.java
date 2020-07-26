package org.freecash.math.component;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.HttpPost;
import org.freecash.math.config.IndexConfig;
import org.freecash.math.dto.IndexHistoryDataRequest;
import org.freecash.math.dto.IndexHistoryDataResponse;
import org.freecash.utils.DateUtils;
import org.freecash.utils.HttpClientComponent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

/**
 * @author wanglint
 * @date 2020/7/13 21:32
 **/
@Component
public class DataSourceComponent {
    @Resource
    private IndexConfig indexConfig;

    @PostConstruct
    public void init() throws Exception{
        if(indexConfig.isInit()){
            IndexHistoryDataRequest request = new IndexHistoryDataRequest(indexConfig.getToken(),
                    indexConfig.getBeginDate(), DateUtils.dateStr(new Date(),DateUtils.DEFAULT_PATTERN),
                    indexConfig.getStockCodes(),indexConfig.getMetricsList());
            HttpPost post = HttpClientComponent.getPostForJson(indexConfig.getUrl(), JSON.toJSONString(request));
            String res = HttpClientComponent.send(post);
            IndexHistoryDataResponse response = JSON.parseObject(res,IndexHistoryDataResponse.class);
        }
    }
}
