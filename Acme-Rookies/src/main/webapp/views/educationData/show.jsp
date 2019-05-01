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
<spring:message code="educationData.degree"/>: <jstl:out value="${educationData.degree}"></jstl:out>
<br/>
<spring:message code="educationData.institution"/>: <jstl:out value="${educationData.institution}"></jstl:out>
<br/>
<spring:message code="educationData.mark"/>: <jstl:out value="${educationData.mark}"></jstl:out>
<br/>
<spring:message code="educationData.startDate"/>: <jstl:out value="${educationData.startDate}"></jstl:out>
<br/>
<spring:message code="educationData.endDate"/>: <jstl:out value="${educationData.endDate}"></jstl:out>
<br/>

<acme:button
	url="educationData/rookie/edit.do?educationDataId=${educationData.id}"
	code="educationData.edit" />
<acme:button url="/curriculum/rookie/show.do?curriculumId=${curriculum.id}" code="educationData.back" />


	