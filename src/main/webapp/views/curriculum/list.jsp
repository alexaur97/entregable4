<%--
 * action-1.jsp
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

<display:table pagesize="5" name="curriculums" id="row"
	requestURI="curriculum/rookie/list.do" class="displaytag" >
	<display:column property="idName" titleKey="curriculum.idName"/>
	<display:column property="personalData.statement" titleKey="curriculum.personalData.statement" />
	<display:column titleKey="curriculum.show">
	<acme:button url="/curriculum/rookie/show.do?curriculumId=${row.id}" code="curriculum.show"/>
	</display:column>
</display:table>

<acme:button code="curriculum.create" url="/curriculum/rookie/create.do"/>

