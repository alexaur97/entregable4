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
<spring:message code="positionData.title"/>: <jstl:out value="${positionData.title}"></jstl:out>
<br/>
<spring:message code="positionData.description"/>: <jstl:out value="${positionData.description}"></jstl:out>
<br/>
<spring:message code="positionData.startDate"/>: <jstl:out value="${positionData.startDate}"></jstl:out>
<br/>
<spring:message code="positionData.endDate"/>: <jstl:out value="${positionData.endDate}"></jstl:out>
<br/>

<acme:button
	url="positionData/rookie/edit.do?positionDataId=${positionData.id}" code="positionData.edit" />
<acme:button url="curriculum/rookie/show.do?curriculumId=${curriculum.id}" code="positionData.back" />


	