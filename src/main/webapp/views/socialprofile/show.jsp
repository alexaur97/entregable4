<%--
 * action-2.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<security:authorize access="hasRole('ADMINISTRATOR')">
<jstl:if test="${socialProfile.actor.spammer eq true}">
<h3 style="color: #CB3234;"><spring:message code="actor.spammer"/></h3>
</jstl:if>
<jstl:if test="${socialProfile.actor.spammer eq false}">
<h3 style="color: #008000;"><spring:message code="actor.nospammer"/></h3>
</jstl:if>
</security:authorize>
<acme:display code="socialProfile.nick" path="${socialProfile.nick}"/>
<acme:display code="socialProfile.link" path="${socialProfile.link}"/>
<acme:display code="socialProfile.socialNetwork" path="${socialProfile.socialNetwork}"/>
