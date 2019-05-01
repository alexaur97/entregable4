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

<form:form modelAttribute="curriculum">
<form:hidden path="id"/>
<form:hidden path="version"/>
<acme:textbox code="curriculum.idName" path="idName"/>
			<button type="submit" name="save">
				<spring:message code="curriculum.save" />
			</button>
				<acme:submitConfirmation name="delete" code="curriculum.delete"
								onclick="curriculum.delete.confirmation" />
			</button>
			<acme:button url="curriculum/rookie/show.do?curriculumId=${curriculum.id}" code="curriculum.back"/>
</form:form>
