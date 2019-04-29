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

<td><acme:cancel url="/socialprofile/create.do" code="socialProfile.create" /></td>

<display:table pagesize="5" name="socialProfiles" id="socialProfile"
	requestURI="${requestURI}" class="displaytag table">
	<display:column titleKey="socialProfile.nick" property="nick" />
	<display:column titleKey="socialProfile.socialNetwork"
		property="socialNetwork" />
	<display:column titleKey="socialProfile.link"
		property="link" />
	<display:column titleKey="socialProfile.show">
		<acme:cancel url="/socialprofile/show.do?socialProfileId=${socialProfile.id}"
			code="socialProfile.show" />
	</display:column>
	<display:column titleKey="socialProfile.edit">
		<acme:cancel url="/socialprofile/edit.do?socialProfileId=${socialProfile.id}"
			code="socialProfile.edit" />
	</display:column>


</display:table>
	


