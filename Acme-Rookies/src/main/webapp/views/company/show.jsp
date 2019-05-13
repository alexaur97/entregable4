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
<security:authorize access="hasRole('ADMINISTRATOR')">
	<jstl:if test="${company.spammer eq true}">
		<h3 style="color: #CB3234;">
			<spring:message code="company.spammer" />
		</h3>
	</jstl:if>
	<jstl:if test="${company.spammer eq false}">
		<h3 style="color: #008000;">
			<spring:message code="company.nospammer" />
		</h3>
	</jstl:if>
</security:authorize>


<h3>
	<spring:message code="company.score" />:

	<jstl:choose>
		<jstl:when test="${not empty score}">
			<jstl:out value="${score}"></jstl:out>
		</jstl:when>

		<jstl:otherwise>
			<spring:message code="company.score.null"/>
		</jstl:otherwise>
	</jstl:choose>
</h3>
<spring:message code="company.commercialName" />
:
<jstl:out value="${company.commercialName}"></jstl:out>
<br />
<spring:message code="company.name" />
:
<jstl:out value="${company.name}"></jstl:out>
<br />
<spring:message code="company.surnames" />
:
<jstl:out value="${company.surnames}"></jstl:out>
<br />
<spring:message code="company.VAT" />
:
<jstl:out value="${company.VAT}"></jstl:out>
<br />
<spring:message code="company.photo" />
:
<br />
	<img src="${company.photo}" width="280" height="250"></img>
<br />
<spring:message code="company.email" />
:
<jstl:out value="${company.email}"></jstl:out>
<br />
<spring:message code="company.phone" />
:
<jstl:out value="${company.phone}"></jstl:out>
<br />
<spring:message code="company.address" />
:
<jstl:out value="${company.address}"></jstl:out>
<br />
<jstl:if test="${b eq true}">
	<h2>
		<spring:message code="company.nopositions" />
	</h2>
</jstl:if>
<jstl:if test="${b eq false}">
	<h2>
		<spring:message code="company.positions" />
		:
	</h2>
	<ul>
		<jstl:forEach items="${positions}" var="x">
			<li><a href="position/show.do?positionId=${x.id}"><jstl:out
						value="${x.title}" /></a></li>
		</jstl:forEach>
	</ul>
</jstl:if>
<br />





