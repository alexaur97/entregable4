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

<display:table pagesize="5" name="providers" id="provider"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="provider.name"  property="name" />
	<display:column titleKey="provider.surnames"  property="surnames" />
	
	
 	<display:column titleKey="provider.items">
		<acme:cancel url="/item/listByProvider.do?providerId=${provider.id}" code="provider.items" />
	</display:column> 
	

</display:table>

<br>
<br>
<br>

		<security:authorize access="hasRole('COMPANY')">
		<acme:cancel url="/position/company/create.do" code="position.create"/>
		</security:authorize>