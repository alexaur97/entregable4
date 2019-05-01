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

<display:table pagesize="5" name="positions" id="position"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="position.title" property="title" />
	<display:column titleKey="position.salaryOffered"
		property="salaryOffered" />
		<display:column titleKey="position.ticker"
		property="ticker" />
		<display:column titleKey="position.mode"
		property="mode" />
		
	<display:column titleKey="position.showPosition">
		<acme:cancel url="/position/company/show.do?positionId=${position.id}"
			code="position.show" />
	</display:column>
	<display:column titleKey="position.company">
		<acme:cancel url="/company/show.do?companyId=${position.company.id}"
			code="position.company" />
	</display:column>
	
	<display:column titleKey="position.edit">
		<jstl:if test="${position.mode=='DRAFT'}">
		<acme:cancel url="/position/company/edit.do?positionId=${position.id}"
			code="position.edit" />
		</jstl:if>
	</display:column>
	
	
					
	<display:column titleKey="position.editMode">
		<jstl:if test="${position.problems.size()>1 and position.mode=='DRAFT' }">
		<acme:cancel url="/position/company/editMode.do?positionId=${position.id}"
			code="position.change" />
		</jstl:if>
	</display:column>
	
	<display:column titleKey="position.cancelPos">
		<jstl:if test="${position.mode=='FINAL' }">
		<acme:cancel url="/position/company/cancel.do?positionId=${position.id}"
			code="position.cancel" />
		</jstl:if>
	</display:column>

</display:table>


<br>
<br>
<h3>
<spring:message code = "position.cancelled"/>
</h3>
<display:table pagesize="5" name="positionsCancelled" id="positionC"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="position.title" property="title" />
	<display:column titleKey="position.salaryOffered"
		property="salaryOffered" />
		<display:column titleKey="position.ticker"
		property="ticker" />
		<display:column titleKey="position.mode"
		property="mode" />
		
	<display:column titleKey="position.showPosition">
		<acme:cancel url="/position/show.do?positionId=${positionC.id}"
			code="position.show" />
	</display:column>
	<display:column titleKey="position.company">
		<acme:cancel url="/company/show.do?companyId=${positionC.company.id}"
			code="position.company" />
	</display:column>
</display:table>

<br>
<br>
<br>

<security:authorize access="hasRole('COMPANY')">
	<acme:cancel url="/position/company/create.do" code="position.create" />
</security:authorize>
