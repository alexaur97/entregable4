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
<spring:message code="miscellaneousData.text"/>: <jstl:out value="${miscellaniusData.text}"></jstl:out>
<br/>


	
		<h2><spring:message code="miscellaneousData.attachments"/>:</h2>
<ul>
	<jstl:forEach items="${miscellaniusData.attachments}" var="x">
		<li><jstl:out value="${x}"></jstl:out></li>
	</jstl:forEach>
</ul>


<br/>

<acme:button
	url="miscellaneousData/rookie/edit.do?miscellaneousDataId=${miscellaniusData.id}"
	code="miscellaneousData.edit" />
<acme:button url="curriculum/rookie/show.do?curriculumId=${curriculum.id}" code="miscellaneousData.back" />


	