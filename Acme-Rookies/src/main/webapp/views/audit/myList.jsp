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

<display:table pagesize="5" name="audits" id="audit"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="audit.text" property="text" />
	<display:column titleKey="audit.moment" property="moment" />
	<display:column titleKey="audit.score" property="score" />
	<display:column titleKey="audit.mode" property="mode" />
	
	<display:column titleKey="audit.edit">
	<jstl:if test="${audit.mode eq 'DRAFT'}">
		<acme:cancel url="/audit/auditor/edit.do?auditId=${audit.id}"
			code="audit.edit" />
		</jstl:if>
	</display:column>
	
	
	
</display:table>

<br>
<br>
<br>
	
<security:authorize access="hasRole('AUDITOR')">
	<acme:cancel url="/audit/auditor/create.do" code="audit.create" />
</security:authorize>