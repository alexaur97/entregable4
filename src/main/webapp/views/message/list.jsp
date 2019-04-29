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

<td><acme:cancel url="/message/create.do" code="message.create" /></td>
<security:authorize access="hasRole('ADMINISTRATOR')">
	<td><acme:cancel url="/message/administrator/create.do"
			code="message.admin.create" /></td>
</security:authorize>

<h4>
	<spring:message code="message.received" />
</h4>
<table>
	<tr>
		<th><spring:message code="message.subject" /></th>
		<th><spring:message code="message.date" /></th>
		<th><spring:message code="message.sender" /></th>
		<th><spring:message code="message.showMessage" /></th>
		<th><spring:message code="message.delete" /></th>
	</tr>

	<jstl:forEach items="${messagesRecived}" var="x">
		<tr>
			<td><jstl:out value="${x.subject}" /></td>
			<td><jstl:out value="${x.moment}" /></td>
			<jstl:choose>
				<jstl:when test="${not empty x.sender}">
					<td><jstl:out value="${x.sender.name} ${x.sender.surnames}" /></td>
				</jstl:when>
				<jstl:otherwise>
				<td><jstl:out value="SYSTEM" /></td>
				</jstl:otherwise>
			</jstl:choose>
			<td><acme:cancel url="/message/show.do?messageId=${x.id}"
					code="message.show" /></td>
			<td><acme:cancel url="/message/delete.do?messageId=${x.id}"
					code="message.delete" /></td>
		</tr>
	</jstl:forEach>

</table>

<h4>
	<spring:message code="message.sended" />
</h4>
<table>
	<tr>
		<th><spring:message code="message.subject" /></th>
		<th><spring:message code="message.date" /></th>
		<th><spring:message code="message.recipient" /></th>
		<th><spring:message code="message.showMessage" /></th>
		<th><spring:message code="message.delete" /></th>
	</tr>
	<jstl:forEach items="${messagesSend}" var="x">
		<tr>
			<td><jstl:out value="${x.subject}" /></td>
			<td><jstl:out value="${x.moment}" /></td>
			<td><jstl:out
					value="${x.recipient.name} ${x.recipient.surnames}" /></td>
			<td><acme:cancel url="/message/show.do?messageId=${x.id}"
					code="message.show" /></td>
			<td><acme:cancel url="/message/delete.do?messageId=${x.id}"
					code="message.delete" /></td>
		</tr>
	</jstl:forEach>

</table>

<h4>
	<spring:message code="message.spam" />
</h4>
<table>
	<tr>
		<th><spring:message code="message.subject" /></th>
		<th><spring:message code="message.date" /></th>
		<th><spring:message code="message.sender" /></th>
		<th><spring:message code="message.showMessage" /></th>
		<th><spring:message code="message.delete" /></th>
	</tr>
	<jstl:forEach items="${messagesSpam}" var="x">
		<tr>
			<td><jstl:out value="${x.subject}" /></td>
			<td><jstl:out value="${x.moment}" /></td>
			<td><jstl:out value="${x.sender.name} ${x.sender.surnames}" /></td>
			<td><acme:cancel url="/message/show.do?messageId=${x.id}"
					code="message.show" /></td>
			<td><acme:cancel url="/message/delete.do?messageId=${x.id}"
					code="message.delete" /></td>
		</tr>
	</jstl:forEach>

</table>

<h4>
	<spring:message code="message.deleted" />
</h4>
<table>
	<tr>
		<th><spring:message code="message.subject" /></th>
		<th><spring:message code="message.date" /></th>
		<th><spring:message code="message.sender" /></th>
		<th><spring:message code="message.recipient" /></th>
		<th><spring:message code="message.showMessage" /></th>
		<th><spring:message code="message.delete" /></th>
	</tr>
	<jstl:forEach items="${messagesDeleted}" var="x">
		<tr>
			<td><jstl:out value="${x.subject}" /></td>
			<td><jstl:out value="${x.moment}" /></td>
			<td><jstl:out value="${x.sender.name} ${x.sender.surnames}" /></td>
			<td><jstl:out
					value="${x.recipient.name} ${x.recipient.surnames}" /></td>
			<td><acme:cancel url="/message/show.do?messageId=${x.id}"
					code="message.show" /></td>
			<td><acme:cancel url="/message/delete.do?messageId=${x.id}"
					code="message.delete" /></td>
		</tr>
	</jstl:forEach>

</table>

