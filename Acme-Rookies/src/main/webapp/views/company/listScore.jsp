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

<table>
	<tr>
		<th><spring:message code="company.commercialName" /></th>
		<th><spring:message code="company.score" /></th>
		
	</tr>
	<jstl:forEach  var="x" begin="0" end="${end}">
		<tr>
				<td><jstl:out value="${companies[x].commercialName}" /></td>
		<jstl:choose>
				<jstl:when test="${scores[x]==null}">
								<td><jstl:out value="-" /></td>
				
				</jstl:when>
				<jstl:otherwise>
							<td><jstl:out value="${scores[x]}" /></td>
				
				
				</jstl:otherwise>
			</jstl:choose>
		</tr>
	</jstl:forEach>

</table>