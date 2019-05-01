<%--
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
<jstl:if test="${c eq true}">
<a href="${sponsorship.targetPage}"><img src="${sponsorship.banner}" width="400" height="100"></img></a>
<br/>
<br/>
</jstl:if>
<spring:message code="position.title"/>: <jstl:out value="${position.title}"></jstl:out>
<br/>
<spring:message code="position.description"/>: <jstl:out value="${position.description}"></jstl:out>
<br/>
<spring:message code="position.deadline"/>: <jstl:out value="${position.deadline}"></jstl:out>
<br/>
<spring:message code="position.ticker"/>: <jstl:out value="${position.ticker}"></jstl:out>
<br/>
<spring:message code="position.salaryOffered"/>: <jstl:out value="${position.salaryOffered}"></jstl:out>
<br/>
<spring:message code="position.mode"/>: <jstl:out value="${position.mode}"></jstl:out>
<br/>
<h2><spring:message code="position.company"/>:</h2>
<a href="company/show.do?companyId=${position.company.id}"><jstl:out value="${position.company.commercialName}" /></a>
<br/>
<jstl:if test="${b eq true}">
<h2><spring:message code="position.noproblems"/></h2>
</jstl:if>
<jstl:if test="${b eq false}">
<h2><spring:message code="position.problems"/>:</h2>
<ul>
	<jstl:forEach items="${position.problems}" var="x">
		<li><a href="problem/display.do?problemId=${x.id}"><jstl:out value="${x.title}" /></a></li>
	</jstl:forEach>
</ul>
</jstl:if>
<br/>

<jstl:if test="${position.cancelled eq true}">
<h3 style="color: #DD8833;"><spring:message code="position.cancele"/></h3>
</jstl:if>




	