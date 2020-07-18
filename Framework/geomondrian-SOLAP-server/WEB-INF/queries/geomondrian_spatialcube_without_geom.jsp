<%@ page session="true" contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<jp:mondrianQuery id="query01" jdbcDriver="org.postgis.DriverWrapper" jdbcUrl="jdbc:postgresql_postGIS://localhost/simple_geofoodmart?user=test&password=test" catalogUri="/WEB-INF/queries/simple_geofoodmart_without_geom.xml">
select
  {[Measures].[Unit Sales], [Measures].[Store Cost], [Measures].[Store Sales]} on columns,
  {([Store].[All Stores], [Product].[All Products])} ON rows
from Sales
where ([Time].[1997])
</jp:mondrianQuery>

<c:set var="title01" scope="session">Test Query uses GeoMondrian SOLAP server</c:set>
