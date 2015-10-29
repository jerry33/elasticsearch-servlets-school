import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import utils.Strings;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.elasticsearch.index.query.FilterBuilders.rangeFilter;

/**
 * Created by Jerry on 29/10/15.
 */
public abstract class AbsElasticServlet extends HttpServlet {

    public static final String FIELD1 = "field1";
    public static final String FIELD2 = "field2";
    public static final String FIELD3 = "field3";
    public static final String VALUE1 = "value1";
    public static final String VALUE2 = "value2";
    public static final String VALUE3 = "value3";

    protected SearchRequestBuilder searchRequestBuilder;

    protected PrintWriter out;
    public static Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
    }

    public SearchHit[] searchDocument(Client client, String index, String type, String field, String value) {

        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.QUERY_AND_FETCH)
                .setQuery(QueryBuilders.termQuery(field, value))
                .setSize(100)
                .execute()
                .actionGet();
        return response.getHits().getHits();
    }

    public SearchHit[] searchDocument(Client client, String index, String type, String query) {
        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.QUERY_AND_FETCH)
                .setQuery(query)
                .execute()
                .actionGet();
        return response.getHits().getHits();
    }

    public void searchDocumentWithBoolQueryBuidler
            (String index, String type, HttpServletRequest request) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        String field1 = request.getParameter(FIELD1);
        String field2 = request.getParameter(FIELD2);
        String field3 = request.getParameter(FIELD3);

        String value1 = request.getParameter(VALUE1);
        String value2 = request.getParameter(VALUE2);
        String value3 = request.getParameter(VALUE3);

        if (Strings.isValid(value1)) {
            boolQueryBuilder.must(QueryBuilders.termQuery(field1, value1));
        }

        if (Strings.isValid(value2)) {
            boolQueryBuilder.must(QueryBuilders.termQuery(field2, value2));
        }

        if (Strings.isValid(value3)) {
            boolQueryBuilder.must(QueryBuilders.termQuery(field3, value3));
        }

        setSearchRequestBuilder(index, type, boolQueryBuilder, 100);
    }

    public void addRangeFilter(String field, String from, String to) {
        FilterBuilder filter = rangeFilter(field).gte(from).lte(to);
        getSearchRequestBuilder()
                .setPostFilter(filter);
    }

    protected SearchHit[] getSearchHits() {
        return searchRequestBuilder.execute().actionGet().getHits().getHits();
    }

    private void setSearchRequestBuilder(String index, String type, QueryBuilder queryBuilder, int size) {
        searchRequestBuilder = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.QUERY_AND_FETCH)
                .setQuery(queryBuilder)
                .setSize(size);
    }

    private SearchRequestBuilder getSearchRequestBuilder() {
        return searchRequestBuilder;
    }

}
