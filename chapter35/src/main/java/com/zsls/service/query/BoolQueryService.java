package com.zsls.service.query;

import com.alibaba.fastjson.JSON;
import com.zsls.model.entity.MyLogInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 布尔查询
 */
@Slf4j
@Service
public class BoolQueryService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 布尔查询
     */
    public Object boolQuery() {
        Object result = "";
        try {
            // 创建 Bool 查询构建器
//            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//            // 构建查询条件
//            boolQueryBuilder.must(QueryBuilders.termsQuery("address.keyword", "北京市昌平区", "北京市大兴区", "北京市房山区"))
//                    .filter().add(QueryBuilders.rangeQuery("birthDate").format("yyyy").gte("1990").lte("1995"));
            // 构建查询源构建器
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchSourceBuilder.from(0);
            searchSourceBuilder.size(100);
            // 创建查询请求对象，将查询对象配置到其中
            SearchRequest searchRequest = new SearchRequest("index_5_4_20210209");
//            searchRequest.types("type_5_4");
            searchRequest.source(searchSourceBuilder);
            // 执行查询，然后处理响应结果
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
            // 根据状态和数据条数验证是否返回了数据
            if (RestStatus.OK.equals(searchResponse.status()) && searchResponse.getHits().totalHits > 0) {
                SearchHits hits = searchResponse.getHits();
                for (SearchHit hit : hits) {
                    // 将 JSON 转换成对象
                    MyLogInfo logInfo = JSON.parseObject(hit.getSourceAsString(), MyLogInfo.class);
                    // 写sql 插入吧  todo
                    // 输出查询信息
                    log.info(logInfo.toString());
                }
            }
            result = searchResponse.getHits();
        }catch (IOException e){
            log.error("",e);
        }
        return result;
    }

}