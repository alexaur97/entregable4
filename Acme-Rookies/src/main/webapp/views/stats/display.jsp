<%--
 * footer.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h3>
<spring:message code="stats.position.company" /> :
</h3>
<spring:message code="stats.position.company.average" />
:
<jstl:out value="${positionsPerCompany[0][0]}" />
<br />
<spring:message code="stats.position.company.min" />
:
<jstl:out value="${positionsPerCompany[0][1]}" />
<br />
<spring:message code="stats.position.company.max" />
:
<jstl:out value="${positionsPerCompany[0][2]}" />
<br />
<spring:message code="stats.position.company.stddev" />
:
<jstl:out value="${positionsPerCompany[0][3]}" />
<br />

<h3>
<spring:message code="stats.application.rookie" /> :
</h3>
<spring:message code="stats.application.rookie.average" />
:
<jstl:out value="${applicationsPerRookie[0][0]}" />
<br />
<spring:message code="stats.application.rookie.min" />
:
<jstl:out value="${applicationsPerRookie[0][1]}" />
<br />
<spring:message code="stats.application.rookie.max" />
:
<jstl:out value="${applicationsPerRookie[0][2]}" />
<br />
<spring:message code="stats.application.rookie.stddev" />
:
<jstl:out value="${applicationsPerRookie[0][3]}" />
<br />

<h3>
<spring:message code="stats.companiesHaveOfferedMorePositions" /> :
</h3>
<ul>
	<jstl:forEach items="${companiesHaveOfferedMorePositions}" var="x">
		<li><jstl:out value="${x.commercialName}" /></li>
	</jstl:forEach>
</ul>
<br />

<h3>
<spring:message code="stats.rookiesHaveMadeMoreApplications" /> :
</h3>
<ul>
	<jstl:forEach items="${rookiesHaveMadeMoreApplications}" var="x">
		<li><jstl:out value="${x.name} ${x.surnames}" /></li>
	</jstl:forEach>
</ul>
<br />

<h3>
<spring:message code="stats.salaryOfferedPerPosition" /> :
</h3>
<spring:message code="stats.salaryOfferedPerPosition.average" />
:
<jstl:out value="${salaryOfferedPerPosition[0][0]}" />
<br />
<spring:message code="stats.salaryOfferedPerPosition.min" />
:
<jstl:out value="${salaryOfferedPerPosition[0][1]}" />
<br />
<spring:message code="stats.salaryOfferedPerPosition.max" />
:
<jstl:out value="${salaryOfferedPerPosition[0][2]}" />
<br />
<spring:message code="stats.salaryOfferedPerPosition.stddev" />
:
<jstl:out value="${salaryOfferedPerPosition[0][3]}" />
<br />
<br />

<h3>
<spring:message code="stats.worstPositionsSalary" /> :
</h3>
<ul>
	<jstl:forEach items="${worstPositionsSalary}" var="x">
		<li><jstl:out value="${x.title} - ${x.company.commercialName}" /></li>
	</jstl:forEach>
</ul>

<h3>
<spring:message code="stats.bestPositionsSalary" /> :
</h3>
<ul>
	<jstl:forEach items="${bestPositionsSalary}" var="x">
		<li><jstl:out value="${x.title} - ${x.company.commercialName}" /></li>
	</jstl:forEach>
</ul>
<br />

<h3>
<spring:message code="stats.curriculaPerRookie" /> :
</h3>
<spring:message code="stats.curriculaPerRookie.average" />
:
<jstl:out value="${curriculaPerRookie[0][0]}" />
<br />
<spring:message code="stats.curriculaPerRookie.min" />
:
<jstl:out value="${curriculaPerRookie[0][1]}" />
<br />
<spring:message code="stats.curriculaPerRookie.max" />
:
<jstl:out value="${curriculaPerRookie[0][2]}" />
<br />
<spring:message code="stats.curriculaPerRookie.stddev" />
:
<jstl:out value="${curriculaPerRookie[0][3]}" />
<br />

<h4><spring:message code="stats.finder" />:</h4> 

<table>
	
	<tr>
		<th><div style="text-align:center"><spring:message code="stats.finder.avg" /></th>
		<th><div style="text-align:center"><spring:message code="stats.finder.min" /></th>
		<th><div style="text-align:center"><spring:message code="stats.finder.max" /></th>
		<th><div style="text-align:center"><spring:message code="stats.finder.stddev" /></th>
	</tr>
	
	<tr>
		<td><div style="text-align:center"><jstl:out value="${statsResultsFinders[0][0]}" /></div></td>
		<td><div style="text-align:center"><jstl:out value="${statsResultsFinders[0][1]}" /></div></td>
		<td><div style="text-align:center"><jstl:out value="${statsResultsFinders[0][2]}" /></div></td>
		<td><div style="text-align:center"><jstl:out value="${statsResultsFinders[0][3]}" /></div></td>
	</tr>
</table>

<table>
	<tr>
		<th><div style="text-align:center"><spring:message code="stats.finder.empty" /></div></th>
		<th><div style="text-align:center"><spring:message code="stats.finder.nonEmpty" /></div></th>
	</tr>
	<tr>
			<td><div style="text-align:center"><jstl:out value="${emptyVsNonEmptyFindersRatio[0][0]}" /></div></td>
		<td><div style="text-align:center"><jstl:out value="${emptyVsNonEmptyFindersRatio[0][1]}" /></div></td>
	</tr>
</table>

<br />
<h4><spring:message code="stats.audit.position" />:</h4> 
<table>
	<tr>
		<th><div style="text-align:center"><spring:message code="stats.position" /></div></th>
		<th><div style="text-align:center"><spring:message code="stats.finder.avg" /></div></th>
		<th><div style="text-align:center"><spring:message code="stats.finder.min" /></div></th>
		<th><div style="text-align:center"><spring:message code="stats.finder.max" /></div></th>
		<th><div style="text-align:center"><spring:message code="stats.finder.stddev" /></div></th>
	</tr>
	<jstl:forEach items="${statsAuditScorePerPosition}" var="x">
	<tr>
		<td><div style="text-align:center"><jstl:out value="${x[0]}" /></div></td>
		<td><div style="text-align:center"><jstl:out value="${x[1]}" /></div></td>
		<td><div style="text-align:center"><jstl:out value="${x[2]}" /></div></td>
		<td><div style="text-align:center"><jstl:out value="${x[3]}" /></div></td>
		<td><div style="text-align:center"><jstl:out value="${x[4]}" /></div></td>
	</tr>
	</jstl:forEach>
	
</table>

<br />
<h4><spring:message code="stats.audit.company" />:</h4> 
<table>
	<tr>
		<th><div style="text-align:center"><spring:message code="stats.company" /></div></th>
		<th><div style="text-align:center"><spring:message code="stats.finder.avg" /></div></th>
		<th><div style="text-align:center"><spring:message code="stats.finder.min" /></div></th>
		<th><div style="text-align:center"><spring:message code="stats.finder.max" /></div></th>
		<th><div style="text-align:center"><spring:message code="stats.finder.stddev" /></div></th>
	</tr>
	<jstl:forEach items="${statsAuditScorePerCompany}" var="x">
	<tr>
		<td><div style="text-align:center"><jstl:out value="${x[0]}" /></div></td>
		<td><div style="text-align:center"><jstl:out value="${x[1]}" /></div></td>
		<td><div style="text-align:center"><jstl:out value="${x[2]}" /></div></td>
		<td><div style="text-align:center"><jstl:out value="${x[3]}" /></div></td>
		<td><div style="text-align:center"><jstl:out value="${x[4]}" /></div></td>
	</tr>
	</jstl:forEach>
</table>
<br />
<h3>
<spring:message code="stats.companiesWithHighestAuditScore" /> :
</h3>
<ul>
	<jstl:forEach items="${companiesWithHighestAuditScore}" var="x">
		<li><jstl:out value="${x.commercialName}" /></li>
	</jstl:forEach>
</ul>
<br />

<h3>
<spring:message code="stats.avg.salary.highest.positions" /> :
<jstl:out value="${avgSalaryHighestPositions}" />
</h3>

<h3>
<spring:message code="stats.item.provider" /> :
</h3>
<spring:message code="stats.item.provider.average" />
:
<jstl:out value="${statsNumberItemsPerProvider[0][0]}" />
<br />
<spring:message code="stats.item.provider.min" />
:
<jstl:out value="${statsNumberItemsPerProvider[0][1]}" />
<br />
<spring:message code="stats.item.provider.max" />
:
<jstl:out value="${statsNumberItemsPerProvider[0][2]}" />
<br />
<spring:message code="stats.item.provider.stddev" />
:
<jstl:out value="${statsNumberItemsPerProvider[0][3]}" />
<br />

<br />
<h3>
<spring:message code="stats.top5ProvidersPerItems" /> :
</h3>
<ul>
	<jstl:forEach items="${top5ProvidersPerItems}" var="x">
		<li><jstl:out value="${x.name} ${x.surnames} : ${fn:length(x.items)}" /></li>
	</jstl:forEach>
</ul>
<br />

