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

<form:form id="form" action="rookie/signup.do"
	modelAttribute="rookieRegisterForm" method="POST">
	<fieldset style="border-width: 4px">
		<legend>
			<spring:message code="rookie.personal.data" />
		</legend>
		<acme:textbox code="rookie.name" path="name" />
		<acme:textbox code="rookie.surnames" path="surnames" />
		<acme:textbox code="rookie.vat" path="VAT" placeholder="ESANNNNNNNA"
			comment="rookie.vat.pattern"/>
		<acme:textbox code="rookie.photo" path="photo" />
		<acme:textbox code="rookie.email" path="email" />
		<acme:textbox code="rookie.phone" path="phone" />
		<acme:textbox code="rookie.address" path="address" />
	</fieldset>
	<br />
	<fieldset style="border-width: 4px">
		<legend>
			<spring:message code="rookie.userAccount" />
		</legend>
		<acme:textbox code="rookie.username" path="username" />
		<acme:password code="rookie.password" path="password" />
		<acme:password code="rookie.confirmPassword" path="confirmPassword" />
	</fieldset>
	<br />
	<fieldset style="border-width: 4px">
		<legend>
			<spring:message code="rookie.creditCard" />
		</legend>
		<acme:textbox code="rookie.holderName" path="holderName" />
		<acme:textbox code="rookie.brandName" path="brandName" />
		<acme:textbox code="rookie.number" path="number" />
		<acme:textbox code="rookie.expirationMonth" path="expirationMonth" />
		<acme:textbox code="rookie.expirationYear" path="expirationYear" />
		<acme:textbox code="rookie.cvv" path="cvv" />
	</fieldset>
	<spring:message code="rookie.check" />
	<form:checkbox path="terms" />
	<form:errors path="terms" cssClass="error" />
	<br />

	<jstl:choose>
		<jstl:when test="${lang eq 'en'}">
			<button type="submit" onclick="return validatePhoneNumber()"
				name="save">
				<spring:message code="rookie.save" />
			</button>
		</jstl:when>
		<jstl:otherwise>
			<button type="submit" onclick="return validatePhoneNumberEs()"
				name="save">
				<spring:message code="rookie.save" />
			</button>
		</jstl:otherwise>
	</jstl:choose>
	<acme:cancel url="/#" code="rookie.cancel" />

	<script type="text/javascript">
		function validatePhoneNumber() {
			var phoneNumber = document.getElementById("phone");
			if (!(phoneNumber.value).match("^\\+\\d{1,3}([ ]{1}[(]{1}\\d{1,3}[)]{1})?[ ]{1}\\d{4,}$|^\\d{4,}$|^$")) { return confirm("Phone number doesn't adhere to the correct pattern. Do you want to continue?"); }
		}
		function validatePhoneNumberEs() {
			var phoneNumber = document.getElementById("phone");
			if (!(phoneNumber.value).match("^\\+\\d{1,3}([ ]{1}[(]{1}\\d{1,3}[)]{1})?[ ]{1}\\d{4,}$|^\\d{4,}$|^$")) { return confirm("El tel�fono no se ajusta al patr�n correcto. �Desea continuar?"); }
		}
	</script>
</form:form>