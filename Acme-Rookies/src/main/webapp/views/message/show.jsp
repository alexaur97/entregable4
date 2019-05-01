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
<spring:message code="message.subject"/>: <jstl:out value="${msg.subject}"></jstl:out>
<br/>
<spring:message code="message.body"/>: <jstl:out value="${msg.body}"></jstl:out>
<br/>
<spring:message code="message.date"/>: <jstl:out value="${msg.moment}"></jstl:out>
<br/>
<spring:message code="message.recipient"/>: <jstl:out value="${msg.recipient.name} ${msg.recipient.surnames}"></jstl:out>
<br/>
<spring:message code="message.sender"/>: <jstl:out value="${msg.sender.name} ${msg.sender.surnames}"></jstl:out>

<br/>
<jstl:if test="${b eq true}">
<h2><spring:message code="message.notags"/></h2>
</jstl:if>
<jstl:if test="${b eq false}">
<h2><spring:message code="message.tags"/>:</h2>
<ul>
	<jstl:forEach items="${msg.tags}" var="x">
		<li><jstl:out value="${x}"></jstl:out></li>
	</jstl:forEach>
</ul>
</jstl:if>
<br/>



	