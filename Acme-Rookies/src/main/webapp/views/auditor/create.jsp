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

<form:form id="form" action="auditor/administrator/create.do"
	modelAttribute="auditorRegisterForm" method="POST">
	<fieldset style="border-width: 4px">
		<legend>
			<spring:message code="auditor.personal.data" />
		</legend>
		<acme:textbox code="auditor.name" path="name" />
		<acme:textbox code="auditor.surnames" path="surnames" />
		<acme:textbox code="auditor.vat" path="VAT" placeholder="ESANNNNNNNA"
			comment="auditor.vat.pattern" />
		<acme:textbox code="auditor.photo" path="photo" />
		<acme:textbox code="auditor.email" path="email" />
		<acme:textbox code="auditor.phone" path="phone" />
		<acme:textbox code="auditor.address" path="address" />
	</fieldset>
	<br />
	<fieldset style="border-width: 4px">
		<legend>
			<spring:message code="auditor.userAccount" />
		</legend>
		<acme:textbox code="auditor.username" path="username" />
		<acme:password code="auditor.password" path="password" />
		<acme:password code="auditor.confirmPassword" path="confirmPassword" />
	</fieldset>
	<br />
	<fieldset style="border-width: 4px">
		<legend>
			<spring:message code="auditor.creditCard" />
		</legend>
		<acme:textbox code="auditor.holderName" path="holderName" />
		<acme:textbox code="auditor.brandName" path="brandName" />
		<acme:textbox code="auditor.number" path="number" />
		<acme:textbox code="auditor.expirationMonth" path="expirationMonth" />
		<acme:textbox code="auditor.expirationYear" path="expirationYear" />
		<acme:textbox code="auditor.cvv" path="cvv" />
	</fieldset>
	<spring:message code="auditor.check" />
	<form:checkbox path="terms" />
	<form:errors path="terms" cssClass="error" />
	<br />

	<jstl:choose>
		<jstl:when test="${lang eq 'en'}">
			<button type="submit" onclick="return validatePhoneNumber()"
				name="save">
				<spring:message code="auditor.save" />
			</button>
		</jstl:when>
		<jstl:otherwise>
			<button type="submit" onclick="return validatePhoneNumberEs()"
				name="save">
				<spring:message code="auditor.save" />
			</button>
		</jstl:otherwise>
	</jstl:choose>
	<acme:cancel url="/#" code="auditor.cancel" />

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