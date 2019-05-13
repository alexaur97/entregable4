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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="problem/company/edit.do?problemId=${problem.id}" modelAttribute="problem" >
<form:hidden path="id"/>
<form:hidden path="version"/>
<acme:textbox code="problem.title" path="title"/>
<acme:textbox code="problem.statement" path="statement"/>
<acme:textbox code="problem.hint" path="hint"/>
<acme:textarea code="problem.attachments" path="attachments"/>
<spring:message code="problem.mode"/>
<form:select path="mode">
<form:option value="DRAFT"/>
<form:option value="FINAL" />
</form:select>
<br/>
<jstl:if test="${problem.mode ne 'FINAL'}">
<acme:submit name="save" code="problem.save"/>
</jstl:if>
<jstl:choose>
<jstl:when test="${problem.id ne 0}"><acme:button url="/problem/company/show.do?problemId=${problem.id}" code="problem.cancel"/></jstl:when>
<jstl:otherwise><acme:button url="/problem/company/list.do" code="problem.cancel"/></jstl:otherwise>
</jstl:choose>


</form:form>
