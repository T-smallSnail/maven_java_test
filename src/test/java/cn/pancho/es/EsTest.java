package cn.pancho.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class EsTest {

	RestHighLevelClient client =null;

	@Before
	public void prepare() {
		client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("192.168.17.130", 9200, "http")));

	}

	@Test
	public void getCount(){

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(0);
		sourceBuilder.size(5);
		//sourceBuilder.fetchSource(new String[]{"N_001_001"}, new String[]{});//返回字段有哪些

		MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("N_001_001", "1");
		BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
		boolBuilder.must(matchQueryBuilder);
		//boolBuilder.must(termQueryBuilder);
		//boolBuilder.must(rangeQueryBuilder);

		sourceBuilder.query(boolBuilder);

		SearchRequest searchRequest = new SearchRequest("tag_201811");
		//searchRequest.types("tag_data");
		searchRequest.source(sourceBuilder);

		SearchResponse response;
		try {
			response = client.search(searchRequest, RequestOptions.DEFAULT);
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查询 N_001_001:2 AND N_001_003:8
	 */
	@Test
	public void getByMoreconf(){
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(0);
		sourceBuilder.size(5);
		sourceBuilder.query(QueryBuilders.queryStringQuery("N_001_001:2 AND N_001_003:5 OR N_005_002:苹果"));

        Scroll scroll = new Scroll(TimeValue.timeValueMinutes(30L));

		SearchRequest searchRequest = new SearchRequest("tag_201811");
		//searchRequest.types("tag_data");
		searchRequest.source(sourceBuilder);
        searchRequest.scroll(scroll);


		SearchResponse searchResponse;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            String scrollId = searchResponse.getScrollId();
            SearchHit[] searchHits = searchResponse.getHits().getHits();

            int i = 0;
            while(searchHits != null && searchHits.length > 0) {
                SearchHit[] var15 = searchHits;
                int var16 = searchHits.length;

                StringBuffer line = new StringBuffer();
                for(int var17 = 0; var17 < var16; ++var17) {
                    SearchHit searchHit = var15[var17];
                    String rowKey = searchHit.getId();
                    Map<String, Object> row = searchHit.getSourceAsMap();
                    line.append(rowKey);
                    Iterator var4 = row.keySet().iterator();

                    while(var4.hasNext()) {
                        String key = (String)var4.next();
                        line.append(",").append(row.get(key));
                    }

                    line.append("\n");
                }
                System.out.println("第"+ ++i +"次："+line.toString());
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                //searchResponse = client.scroll(scrollRequest);
                searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
                scrollId = searchResponse.getScrollId();
                searchHits = searchResponse.getHits().getHits();
            }

            //consumer.finishConsume();
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void bufferWrite(){

			FileWriter fw = null;
			BufferedWriter bw = null;
			try {
				fw = new FileWriter("C:\\Users\\PingTianMa\\Desktop\\a.txt");
				bw = new BufferedWriter(fw);
				bw.write("Hello");
				bw.newLine();
				//下一行
				bw.write("World");
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

