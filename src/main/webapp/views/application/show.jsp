<%--
 * list.jsp
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
<h2><spring:message code="problem" />:</h2>
<spring:message code="problem.title"/>: <jstl:out value="${application.problem.title}"></jstl:out>
<br/><spring:message code="problem.statement"/>: <jstl:out value="${application.problem.statement}"></jstl:out>
<br/><spring:message code="problem.hint"/>: <jstl:out value="${application.problem.hint}"></jstl:out>


<jstl:if test="${b eq true}">
<h2><spring:message code="problem.noattachments"/></h2>
</jstl:if>
<jstl:if test="${b eq false}">
<h2><spring:message code="problem.attachments"/>:</h2>
<ul>
	<jstl:forEach items="${application.problem.attachments}" var="x">
		<li><a href="${x}"><jstl:out value="${x}"/></a></li>
	</jstl:forEach>
</ul>
</jstl:if>
<br/>

<br/><spring:message code="application.moment"/>: <jstl:out value="${application.moment}"></jstl:out>
<br/><spring:message code="application.status"/>: <jstl:out value="${application.status}"></jstl:out>
<br/><spring:message code="application.submitMoment"/>: <jstl:out value="${application.submitMoment}"></jstl:out>
<br/><spring:message code="application.rookie"/>: <jstl:out value="${application.rookie.name}"></jstl:out>
<br/><spring:message code="application.curriculum"/>: <jstl:out value="${application.curriculum.idName}"></jstl:out>
			
<security:authorize access="hasRole('COMPANY')">
<acme:button url="/curriculum/company/show.do?applicationId=${application.id}" code="application.show"/>
</security:authorize>

<br/><spring:message code="application.position"/>: <jstl:out value="${application.position.title}"></jstl:out>
<br/><spring:message code="application.company"/>: <jstl:out value="${application.position.company.commercialName}"></jstl:out>
<br/><spring:message code="application.explanation"/>: <jstl:out value="${application.explanation}"></jstl:out>
<br/><spring:message code="application.codeLink"/>: <jstl:out value="${application.codeLink}"></jstl:out>

			<security:authorize access="hasRole('COMPANY')">
<br/><acme:button url="application/company/list.do" code="curriculum.back" />
				</security:authorize>
							<security:authorize access="hasRole('ROOKIE')">			
				<br/><acme:button url="application/rookie/list.do" code="curriculum.back" />
				</security:authorize>
