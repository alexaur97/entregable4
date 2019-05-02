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


<a href="${sponsorship.targetPage}"><img src="${ sponsorship.banner}"
		alt="Acme Rookies Co., Inc."style="width: 150px; height: 50px;"/></a>
	
	<legend>
			</br><spring:message code="sponsorship.position" />
		</legend>
	<br/><spring:message code="application.position"/>: <jstl:out value="${sponsorship.position.title}"></jstl:out>
		<br/><spring:message code="sponsorship.description"/>: <jstl:out value="${sponsorship.position.description}"></jstl:out>
			<br/><spring:message code="sponsorship.salary"/>: <jstl:out value="${sponsorship.position.salaryOffered}"></jstl:out>
			<br/><spring:message code="sponsorship.profile"/>: <jstl:out value="${sponsorship.position.profileRequired}"></jstl:out>
	
<br/><spring:message code="application.company"/>: <jstl:out value="${sponsorship.position.company.commercialName}"></jstl:out>

	
<legend>
			</br><spring:message code="company.creditCard" />
		</legend>
<br/><spring:message code="company.holderName"/>: <jstl:out value="${sponsorship.creditCard.holderName}"></jstl:out>
<br/><spring:message code="company.brandName"/>: <jstl:out value="${sponsorship.creditCard.brandName}"></jstl:out>
<br/><spring:message code="company.number"/>: <jstl:out value="${sponsorship.creditCard.number}"></jstl:out>
<br/><spring:message code="company.expirationMonth"/>: <jstl:out value="${sponsorship.creditCard.expirationMonth}"></jstl:out>
<br/><spring:message code="company.expirationYear"/>: <jstl:out value="${sponsorship.creditCard.expirationYear}"></jstl:out>
	<br/><spring:message code="company.cvv"/>: <jstl:out value="${sponsorship.creditCard.cvv}"></jstl:out>
			
