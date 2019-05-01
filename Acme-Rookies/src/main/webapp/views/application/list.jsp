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

<security:authorize access="hasRole('COMPANY')">

<h3> Applications </h3>
<display:table name="applications" id="app" requestURI="${requestURI}"
	pagesize="5" class="displaytag table">
	<display:column property="moment" titleKey="application.moment" />
	<display:column property="explanation" titleKey="application.explanation" />
	<display:column property="codeLink" titleKey="application.codeLink" />
	<display:column property="submitMoment" titleKey="application.submitMoment" />
	<display:column property="status" titleKey="application.status" />
	<display:column titleKey="problem.show">
			<acme:button url="/application/company/show.do?applicationId=${app.id}"
			code="application.show" />
	</display:column>
		<display:column titleKey="application.accept">
			<jstl:if test="${app.status==s}">
			<acme:cancel url="/application/company/accept.do?applicationId=${app.id}" code="application.accept" />
		</jstl:if>
	</display:column>
	<display:column titleKey="application.reject">
			<jstl:if test="${app.status==s}">
			<acme:cancel url="/application/company/reject.do?applicationId=${app.id}" code="application.reject" />
		</jstl:if>
	</display:column>
</display:table>

<h3> Applications Pending</h3>
<display:table name="applicationsPending" id="app" requestURI="${requestURI}"
	pagesize="5" class="displaytag table">
	<display:column property="moment" titleKey="application.moment" />
	<display:column property="explanation" titleKey="application.explanation" />
	<display:column property="codeLink" titleKey="application.codeLink" />
	<display:column property="submitMoment" titleKey="application.submitMoment" />
	<display:column property="status" titleKey="application.status" />
	<display:column titleKey="problem.show">
			<acme:button url="/application/company/show.do?applicationId=${app.id}"
			code="application.show" />
	</display:column>
</display:table>
				</security:authorize>
				
				<security:authorize access="hasRole('ROOKIE')">
				
	<h3> Applications </h3>
<display:table name="applications" id="app" requestURI="${requestURI}"
	pagesize="5" class="displaytag table">
	<display:column property="moment" titleKey="application.moment" />
	<display:column property="explanation" titleKey="application.explanation" />
	<display:column property="codeLink" titleKey="application.codeLink" />
	<display:column property="submitMoment" titleKey="application.submitMoment" />
	<display:column property="status" titleKey="application.status" />
	<display:column titleKey="problem.show">
		<acme:button url="/application/rookie/show.do?applicationId=${app.id}"
			code="application.show" />
	</display:column>
		<display:column titleKey="application.edit">
			<jstl:if test="${app.status==p}">
			<acme:button url="/application/rookie/edit.do?applicationId=${app.id}" code="application.edit" />
		</jstl:if>
	</display:column>
</display:table>

<h3> Applications Pending </h3>
<display:table name="applicationsPending" id="app" requestURI="${requestURI}"
	pagesize="5" class="displaytag table">
	<display:column property="moment" titleKey="application.moment" />
	<display:column property="explanation" titleKey="application.explanation" />
	<display:column property="codeLink" titleKey="application.codeLink" />
	<display:column property="submitMoment" titleKey="application.submitMoment" />
	<display:column property="status" titleKey="application.status" />
	<display:column titleKey="problem.show">
		<acme:button url="/application/rookie/show.do?applicationId=${app.id}"
			code="application.show" />
	</display:column>
		<display:column titleKey="application.edit">
			<jstl:if test="${app.status==p}">
			<acme:button url="/application/rookie/edit.do?applicationId=${app.id}" code="application.edit" />
		</jstl:if>
	</display:column>
</display:table>
<br/>
<br/>
<acme:button url="/application/rookie/create.do" code="application.create"/>
				</security:authorize>
