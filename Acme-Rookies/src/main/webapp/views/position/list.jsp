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
	<display:column titleKey="position.showPosition">
		<acme:cancel url="/position/show.do?positionId=${position.id}"
			code="position.show" />
	</display:column>
	<display:column titleKey="position.company">
		<acme:cancel url="/company/show.do?companyId=${position.company.id}"
			code="position.company" />
	</display:column>


</display:table>

<br>
<br>
<br>
	<security:authorize access="hasRole('AUDITOR')">
	<acme:cancel url="/auditor/auditor/editPosition.do" code="position.editPos" />
	</security:authorize>
<security:authorize access="hasRole('COMPANY')">
	<acme:cancel url="/position/company/create.do" code="position.create" />
</security:authorize>