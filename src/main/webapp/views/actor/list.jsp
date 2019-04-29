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

<display:table name="actors" id="ac" requestURI="${requestURI}"
	pagesize="5" class="displaytag table">
	<display:column property="name" titleKey="actor.name" />
	<display:column property="surnames" titleKey="actor.surnames" />
	<display:column titleKey="actor.ban">
		<%-- <jstl:if test="${!ac.banned}"> --%>
		<jstl:if test="${ac.spammer && !ac.banned}">
			<acme:cancel url="/actor/administrator/banned.do?actorId=${ac.id}"
				code="actor.ban" />
		</jstl:if>
	</display:column>
	<display:column titleKey="actor.unban">
		<jstl:if test="${ac.banned}">
			<acme:cancel url="/actor/administrator/unbanned.do?actorId=${ac.id}"
				code="actor.unban" />
		</jstl:if>
	</display:column>
	<display:column titleKey="actor.spammer">
		<jstl:if test="${ac.spammer}">
			<h3 style="color: #CB3234;">
				<spring:message code="actor.spam" />
			</h3>
		</jstl:if>
		<jstl:if test="${!ac.spammer}">
			<h3 style="color: #008000;">
				<spring:message code="actor.nospam" />
			</h3>
		</jstl:if>

	</display:column>


</display:table>

<acme:cancel url="/spam/administrator/spammer.do" code="actor.isspammer" />

<jstl:if test="${msg eq 'true'}">
<h3 style="color: #008000;">
		<spring:message code="actor.msg" />
	</h3>

</jstl:if>


