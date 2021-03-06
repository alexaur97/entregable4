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

<display:table pagesize="5" name="items" id="item"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="item.name" property="name" />
	<display:column titleKey="item.description"
		property="description" />
		
			<display:column titleKey="item.provider">
		<acme:cancel url="/provider/showByItem.do?itemId=${item.id}" code="item.provider" />
	</display:column>
	
	<security:authorize access="hasRole('PROVIDER')">
		<display:column titleKey="item.show">
		<acme:cancel url="/item/provider/show.do?itemId=${item.id}" code="item.show" />
	</display:column>
	</security:authorize>		
	

</display:table>

<security:authorize access="hasRole('PROVIDER')">
	<acme:cancel url="/item/provider/create.do" code="item.create" />
</security:authorize>

<br>
<br>
<br>

