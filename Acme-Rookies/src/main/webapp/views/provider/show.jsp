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
<jstl:if test="${provider.spammer eq true}">
<h3 style="color: #CB3234;"><spring:message code="provider.spammer"/></h3>
</jstl:if>
<jstl:if test="${provider.spammer eq false}">
<h3 style="color: #008000;"><spring:message code="provider.nospammer"/></h3>
</jstl:if>
</security:authorize>

<spring:message code="provider.photo"/>:
 <br/>
 <ul>
<img src="${provider.photo}" width="280" height="250"></img>
</ul>
<br/>

<spring:message code="provider.name"/>: <jstl:out value="${provider.name}"></jstl:out>
<br/>
<spring:message code="provider.surname"/>: <jstl:out value="${provider.surnames}"></jstl:out>
<br/>
<spring:message code="provider.VAT"/>: <jstl:out value="${provider.VAT}"></jstl:out>
<br/>
<spring:message code="provider.email"/>: <jstl:out value="${provider.email}"></jstl:out>
<br/>
<spring:message code="provider.phone"/>: <jstl:out value="${provider.phone}"></jstl:out>
<br/>
<spring:message code="provider.address"/>: <jstl:out value="${provider.address}"></jstl:out>
<br/>

<acme:button url="/item/list.do" code="provider.cancel"/>




	