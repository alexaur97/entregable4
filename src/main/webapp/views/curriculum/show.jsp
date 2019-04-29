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

<acme:display code="curriculum.idName" path="${curriculum.idName}" />
<br />

<h4>
	<spring:message code="curriculum.personalData" />
	:
</h4>

<acme:display code="curriculum.personalData.fullname"
	path="${curriculum.personalData.fullname}" />
<acme:display code="curriculum.personalData.github"
	path="${curriculum.personalData.github}" isUrl="yes" />
<acme:display code="curriculum.personalData.linkedin"
	path="${curriculum.personalData.linkedin}" isUrl="yes" />
<acme:display code="curriculum.personalData.phone"
	path="${curriculum.personalData.phone}" />
<acme:display code="curriculum.personalData.statement"
	path="${curriculum.personalData.statement}" />

<security:authorize access="hasRole('ROOKIE')">
	<acme:button
		url="personalData/rookie/edit.do?personalDataId=${curriculum.personalData.id}"
		code="curriculum.edit" />
</security:authorize>
<h4>
	<spring:message code="curriculum.educationData" />
	:
</h4>

<display:table name="educationDatas" id="educationData"
	class="displaytag" pagesize="5" requestURI="${requestURI}">
	<display:column titleKey="curriculum.educationData.degree"
		property="degree" />
	<display:column titleKey="curriculum.educationData.institution"
		property="institution" />
	<display:column titleKey="curriculum.educationData.mark"
		property="mark" />
	<display:column titleKey="curriculum.educationData.startDate"
		property="startDate" />
	<display:column titleKey="curriculum.educationData.endDate"
		property="endDate" />
			<security:authorize access="hasRole('ROOKIE')">
		
	<display:column titleKey="curriculum.show">
		<acme:button
			url="/educationData/rookie/show.do?educationDataId=${educationData.id}"
			code="curriculum.show" />
	</display:column>
					</security:authorize>
	
</display:table>
<security:authorize access="hasRole('ROOKIE')">
	<acme:button
		url="/educationData/rookie/create.do?curriculumId=${curriculum.id}"
		code="curriculum.create2" />
	<br>
</security:authorize>
<h4>
	<spring:message code="curriculum.miscellaniusData" />
	:
</h4>

<display:table name="miscellaniusDatas" id="miscellaniusData"
	class="displaytag" pagesize="5" requestURI="${requestURI}">
	<display:column titleKey="curriculum.miscellaniusData.attachments"
		property="attachments" />
	<display:column titleKey="curriculum.miscellaniusData.text"
		property="text" />
			<security:authorize access="hasRole('ROOKIE')">
		
	<display:column titleKey="curriculum.show">
		<acme:button
			url="/miscellaneousData/rookie/show.do?miscellaneousDataId=${miscellaniusData.id}"
			code="curriculum.show" />
	</display:column>
					</security:authorize>
	
</display:table>

<security:authorize access="hasRole('ROOKIE')">
	<acme:button
		url="/miscellaneousData/rookie/create.do?curriculumId=${curriculum.id}"
		code="curriculum.create2" />
	<br>
</security:authorize>
<h4>
	<spring:message code="curriculum.positionData" />
	:
</h4>

<display:table name="positionDatas" id="positionData" class="displaytag"
	pagesize="5" requestURI="${requestURI}">

	<display:column titleKey="curriculum.positionData.title"
		property="title" />
	<display:column titleKey="curriculum.positionData.description"
		property="description" />
	<display:column titleKey="curriculum.positionData.startDate"
		property="startDate" />
	<display:column titleKey="curriculum.positionData.endDate"
		property="endDate" />
<security:authorize access="hasRole('ROOKIE')">
		<display:column titleKey="curriculum.show">
		<acme:button url="/positionData/rookie/show.do?positionDataId=${positionData.id}"
			code="curriculum.show" />
	</display:column>
	</security:authorize>
</display:table>

	<security:authorize access="hasRole('ROOKIE')">

	<acme:button url="/positionData/rookie/create.do?curriculumId=${curriculum.id}"
			code="curriculum.create2" />
							</security:authorize>
			
<br>
<br>
<br>
	<security:authorize access="hasRole('ROOKIE')">

<acme:button
	url="curriculum/rookie/edit.do?curriculumId=${curriculum.id}"
	code="curriculum.edit" />
<acme:button url="curriculum/rookie/list.do" code="curriculum.back" />
				</security:authorize>
<security:authorize access="hasRole('COMPANY')">

<acme:button url="application/company/show.do?applicationId=${application.id }" code="curriculum.back" />
				</security:authorize>
