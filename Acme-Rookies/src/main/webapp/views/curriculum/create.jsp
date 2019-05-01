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

<form:form modelAttribute="curriculumCreateForm">
<acme:textbox code="curriculum.idName" path="idName"/>
<fieldset style="border-width: 4px">
<legend><spring:message code="curriculum.personalData" /></legend>
<acme:textbox code="curriculum.personalData.fullname" path="fullname"/>
<acme:textbox code="curriculum.personalData.github" path="github"/>
<acme:textbox code="curriculum.personalData.linkedin" path="linkedin"/>
<acme:textbox code="curriculum.personalData.phone" path="phone"/>
<acme:textbox code="curriculum.personalData.statement" path="statement"/>
</fieldset>

<jstl:choose>
		<jstl:when test="${lang == 'en'}">
		<button type="submit" onclick="return validatePhoneNumber()" name="save">
				<spring:message code="curriculum.save" />
			</button>
		</jstl:when>
		<jstl:otherwise>
			<button type="submit" onclick="return validatePhoneNumberEs()" name="save">
				<spring:message code="curriculum.save" />
			</button>
		</jstl:otherwise>
	</jstl:choose>
<acme:button url="curriculum/rookie/list.do" code="curriculum.cancel"/>

<script type="text/javascript">
		function validatePhoneNumber() {
			var phoneNumber = document.getElementById("phone");
			if (!(phoneNumber.value).match("^\\+\\d{1,3}([ ]{1}[(]{1}\\d{1,3}[)]{1})?[ ]{1}\\d{4,}$|^\\d{4,}$|^$")) { return confirm("Phone number doesn't adhere to the correct pattern. Do you want to continue?"); }
		}
		function validatePhoneNumberEs() {
			var phoneNumber = document.getElementById("phone");
			if (!(phoneNumber.value).match("^\\+\\d{1,3}([ ]{1}[(]{1}\\d{1,3}[)]{1})?[ ]{1}\\d{4,}$|^\\d{4,}$|^$")) { return confirm("El teléfono no se ajusta al patrón correcto. ¿Desea continuar?"); }
		}
	</script>
</form:form>
