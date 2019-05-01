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

<div class="container">
	<div class="row">
		<div class="col-sm-12 col-md-12 col-lg-12">
			<fieldset class="col-md-6 col-md-offset-3">

				<form:form action="message/edit.do"
					modelAttribute="msg" class="form-horizontal" method="post">
					<div class="form-group ">

						<form:hidden path="id"/>
						<form:hidden path="version"/>	
						<acme:textbox code="message.subject" path="subject" />
						<acme:textarea code="message.body" path="body" />
						<acme:textbox code="message.tags" path="tags" />
						<acme:select items="${actors}" itemLabel="email" code="message.recipient" path="recipient"/>
					
						<acme:submit name="save" code="msg.save" />						
						<acme:cancel url="/message/list.do"
							code="msg.cancel" />
						<jstl:if test="${b eq false}">
							<h3 style="color: #CB3234;"><spring:message code="msg.attachment.error"/></h3>
						</jstl:if>
					</div>
				</form:form>
			</fieldset>
		

		</div>
	</div>
</div>
