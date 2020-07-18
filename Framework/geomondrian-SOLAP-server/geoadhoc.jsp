<%@page contentType="text/html"%>
<%--
// $Id: //open/mondrian/webapp/adhoc.jsp#12 $
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// (C) Copyright 2001-2005 Kana Software, Inc. and others.
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
//
// jhyde, 6 August, 2001
// tbadard, 3 september, 2011
--%>
<%
    final String nl = System.getProperty("line.separator");
    String[] queries = new String[] {
	// spatial calculated measure
	"with member [Measures].[Geom_Union] as 'ST_UnionAgg([Store].[All Stores].CurrentMember.Children, \"geom\")'"
	+nl+"select {[Measures].[Unit Sales], [Measures].[Geom_Union]} ON COLUMNS,"
    +nl+"	{[Store].[All Stores].[USA]} ON ROWS"
	+nl+"from [Sales]"
	+nl+"where [Time].[1997]",

	// ST_Transform operator
	"with member [Measures].[Geom_area_in_km2] as 'ST_Area(ST_Transform(ST_UnionAgg([Store].[All Stores].CurrentMember.Children, \"geom\"),4326,2991))/1E6'"
	+nl+"select {[Measures].[Unit Sales], [Measures].[Geom_area_in_km2]} ON COLUMNS,"
	+nl+"	{[Store].[All Stores].[USA]} ON ROWS"
	+nl+"from [Sales]"
	+nl+"where [Time].[1997]",

	// ST_Distance predicate
	"SELECT"
        +nl+"	{[Measures].[Unit Sales]} on columns,"
        +nl+"	Filter("
        +nl+"        {[Store].[Store State].members},"
        +nl+"        ST_Distance(ST_Transform([Store].CurrentMember.Properties(\"geom\"),4326,2991),"
        +nl+"                ST_Transform([Store].[Store State].[WA].Properties(\"geom\"),4326,2991)) < 2000"
        +nl+") on rows"
	+nl+"FROM [Sales]"
	+nl+"WHERE [Time].[1997]",
	
	// ST_GeomFromText constructor
	"SELECT"
        +nl+"	{[Measures].[Unit Sales]} on columns,"
        +nl+"	Filter("
        +nl+"        {[Store].[Store State].members},"
        +nl+"        ST_Distance(ST_Transform([Store].CurrentMember.Properties(\"geom\"),4326,2991),"
        +nl+"                ST_Transform(ST_GeomFromText(\"POINT (-120.215149 44.998796)\"),4326,2991)) < 200000"
        +nl+"	) on rows"
	+nl+"FROM [Sales]"
	+nl+"WHERE [Time].[1997]",

	// Mixing previous samples
	"with member"
 	+nl+"	[Measures].[Geom_area_in_km2] as 'ST_Area(ST_Transform(ST_UnionAgg([Store].[All Stores].CurrentMember.Children, \"geom\"),4326,2991))/1E6'"
	+nl+"SELECT"
        +nl+"	{[Measures].[Unit Sales], [Measures].[Geom_area_in_km2]} on columns,"
        +nl+"	Filter("
        +nl+"        {[Store].[Store State].members},"
        +nl+"        ST_Distance(ST_Transform([Store].CurrentMember.Properties(\"geom\"),4326,2991),"
        +nl+"                ST_Transform([Store].[Store State].[WA].Properties(\"geom\"),4326,2991)) < 2000"
        +nl+") on rows"
	+nl+"FROM [Sales]"
	+nl+"WHERE [Time].[1997]",

	// ST_Within predicate
	"SELECT"
        +nl+"	{[Measures].[Unit Sales],[Measures].[Store Sales]} on columns,"
        +nl+"	Filter("
        +nl+"        {[Store].[Store City].members},"
        +nl+"                ST_Within([Store].CurrentMember.Properties(\"geom\"),"
        +nl+"                [Store].[Store Country].[USA].Properties(\"geom\"))"
        +nl+"	) on rows"
	+nl+"FROM [Sales]"
	+nl+"WHERE [Time].[1997]",

	// Same but within a given geometry envelope
	"SELECT"
        +nl+"	{[Measures].[Unit Sales],[Measures].[Store Sales]} on columns,"
        +nl+"	Filter("
        +nl+"        {[Store].[Store City].members},"
        +nl+"                ST_Within([Store].CurrentMember.Properties(\"geom\"),"
        +nl+"                ST_GeomFromText(\"POLYGON((-124.727645874023 45.5432071685791,-124.727645874023 49.0021112747092,-116.918151855469 49.0021112747092,-116.918151855469 45.5432071685791,-124.727645874023 45.5432071685791))\"))) on rows"
	+nl+"FROM [Sales]"
	+nl+"WHERE [Time].[1997]",

	// Same but crossed with products
	"select {[Measures].[Unit Sales], [Measures].[Store Sales]} ON COLUMNS,"
  	+nl+"	Crossjoin(Filter({[Store].[Store City].Members}, ST_Within([Store].CurrentMember.Properties(\"geom\"), ST_GeomFromText(\"POLYGON((-124.727645874023 45.5432071685791,-124.727645874023 49.0021112747092,-116.918151855469 49.0021112747092,-116.918151855469 45.5432071685791,-124.727645874023 45.5432071685791))\"))), {[Product].[All Products].Children}) ON ROWS"
	+nl+"from [Sales]"
	+nl+"where [Time].[1997]",

	// calculated member
	"WITH MEMBER [Store].[Store State].[OR].[Around Somewhere in Oregon]"
        +nl+"	AS Aggregate("
        +nl+"        Filter("
        +nl+"                [Store].[Store State].members,"
        +nl+"                ST_Distance(ST_Transform([Store].CurrentMember.Properties(\"geom\"),4326,2991),"
        +nl+"                ST_Transform(ST_GeomFromText(\"POINT (-120.215149 44.998796)\"),4326,2991)) < 1000"
        +nl+"        )"
        +nl+"	), geom=ST_Transform(ST_GeomFromText(\"POINT (-120.215149 44.998796)\"),4326,2991)"
	+nl+"SELECT"
        +nl+"	{[Measures].[Unit Sales]} on columns,"
        +nl+"	{"
        +nl+"        [Store].[Store State].[OR].children,"
        +nl+"        [Store].[All Stores].[USA].[OR].[Around Somewhere in Oregon]"
        +nl+"	} on rows"
	+nl+"FROM [Sales]"
	+nl+"WHERE [Time].[1997]"

    };
%>

<html>
<head>
<link rel="stylesheet" href="stylesheet.css" type="text/css" />
<style>
.{
font-family:"verdana";
}

.resulttable {
background-color:#AAAAAA;
}

.slicer {
background-color:#DDDDDD;
font-size:10pt;
}

.columnheading {
background-color:#DDDDDD;
font-size:10pt;
}

.rowheading {
background-color:#DDDDDD;
font-size:10pt;
}

.cell {
font-family:"courier";
background-color:#FFFFFF;
font-size:10pt;
text-align:right;
}

</style>

<title>JSP Page</title>
</head>
<body>

<a href=".">back to index</a><p/>

    <form action="geoadhoc.jsp" method="post">
    <table>
        <tr>
            <td>
                <select name="whichquery">
        <%

        for (int i=0; i<queries.length; i++) {

            %>
            <option
            <%

            if (request.getParameter("whichquery") != null) {
                if (Integer.valueOf(request.getParameter("whichquery")).intValue() == i) {
                    out.print(" selected");
                }
            }
            %>

            value="<% out.print(i);%>">Sample GeoMDX Query #<%out.print(i);

            %>

            </option>

        <%
        }
        %>

                </select>
            </td>
        </tr>

        <tr>
            <td>
                <input type="submit" value="show query">
            </td>
        </tr>
    </table>
    </form>

    <form action="geomdxquery">
        <table>
        <tr>
        <td>
        <tr>
            <td>
                <textarea id='queryArea' name="queryString" rows=10 cols=120><%
            if (request.getParameter("whichquery") != null) {
                out.println(queries[Integer.valueOf(request.getParameter("whichquery")).intValue()]);
            }
            if (request.getParameter("queryString") != null) {
                out.println(request.getParameter("queryString"));
            }
        %></textarea>
           <input type="hidden" name="redirect" value="/geoadhoc.jsp" />
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="process MDX query">
            </td>
        </tr>

        <% if (request.getAttribute("result") != null) { %>
        <tr>
            <td valign=top>Results:
            <% out.println(request.getAttribute("result")); %>
            </td>
        </tr>
        <% } %>

    </table>
    </form>

<a href=".">back to index</a><p/>

</body>
</html>
