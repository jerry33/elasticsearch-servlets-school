<%@ page import="java.util.ArrayList" %>
<%@ page import="org.elasticsearch.search.SearchHit" %>
<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: Jerry
  Date: 29/10/15
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<div id="wrap">
    <div id="left_col">
        <h2>Match value</h2>
        <form name="searchForm" method="post" action="main-servlet">
            Field:
            <select name="field1">
                <option value="title">title</option>
                <option value="price">price</option>
                <option value="year">year</option>
                <option value="kilometres">kilometres</option>
                <option value="fuel">fuel</option>
                <option value="engine_volume">engine volume</option>
                <option value="power">power</option>
            </select>
            Value: <input type="text" name="value1"/>
            <br>
            Field:
            <select name="field2">
                <option value="title">title</option>
                <option value="price">price</option>
                <option value="year">year</option>
                <option value="kilometres">kilometres</option>
                <option value="fuel">fuel</option>
                <option value="engine_volume">engine volume</option>
                <option value="power">power</option>
            </select>
            Value: <input type="text" name="value2"/>
            <br>
            Field:
            <select name="field3">
                <option value="title">title</option>
                <option value="price">price</option>
                <option value="year">year</option>
                <option value="kilometres">kilometres</option>
                <option value="fuel">fuel</option>
                <option value="engine_volume">engine volume</option>
                <option value="power">power</option>
            </select>
            Value: <input type="text" name="value3"/>
            <h2>Range</h2>
            <select name="field_range">
                <option value="year">year</option>
                <option value="price">price</option>
                <option value="kilometres">kilometres</option>
                <option value="engine_volume">engine volume</option>
                <option value="consumption_combined">consumption combined</option>
                <option value="consumption_urban">consumption urban</option>
                <option value="consumption_extra_urban">consumption extra urban</option>
            </select>
            <input type="text" name="from"/><input type="text" name="to"/>
            <br><br>
            <input type="submit" value="query" name="action" />

        </form>
    </div>
    <div id="right_col">
        <%
            SearchHit[] searchHits = (SearchHit[]) request.getAttribute("dataSet");
            if (searchHits != null) {
                Map<String, Object> result;
                for (SearchHit hit : searchHits) {
                    result = hit.getSource();
        %>
                <b>Title: </b><%=result.get("title").toString()%><br>
                <b>Price: </b><%=result.get("price").toString()%><br>
                <b>Year: </b><%=result.get("year").toString()%><br>
                <b>Kilometres: </b><%=result.get("kilometres").toString()%><br>
                <b>Fuel: </b><%=result.get("fuel").toString()%><br>
                <b>Engine volume: </b><%=result.get("engine_volume").toString()%><br>
                <b>Power: </b><%=result.get("power").toString()%><br>
                <b>Consumption combined: </b><%=result.get("consumption_combined").toString()%><br>
                <b>Consumption urban: </b><%=result.get("consumption_urban").toString()%><br>
                <b>Consumption extra urban: </b><%=result.get("consumption_extra_urban").toString()%><br>
                <br><br>
        <%}
        }
        %>
    </div>
</div>


</body>
</html>

<style type="text/css">
    #wrap {
        width: 1300px;
        margin: 0 0;
    }
    #left_col {
        float: left;
        padding: 20px;
    }
    #right_col {
        float: left;
        width:600px;
        border-left: double;
        padding: 20px;
    }
</style>
