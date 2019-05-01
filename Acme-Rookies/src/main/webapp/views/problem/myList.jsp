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

<display:table name="problems" id="problem" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<display:column property="title" titleKey="problem.title" />
	<display:column property="statement" titleKey="problem.statement" />
	<display:column property="hint" titleKey="problem.hint" />
	<display:column titleKey="problem.show">
		<acme:button url="/problem/company/show.do?problemId=${problem.id}"
			code="problem.show" />
	</display:column>
	
</display:table>
<acme:button url="/problem/company/create.do" code="problem.create" />