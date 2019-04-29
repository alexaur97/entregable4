<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="container">
	<div class="row">
		<div class="col-sm-12 col-md-12 col-lg-12">
			<fieldset class="col-md-6 col-md-offset-3">

				<form:form action="miscellaneousData/rookie/edit.do?curriculumId=${curriculum.id}"
					modelAttribute="miscellaniusData" class="form-horizontal" method="post">
					<div class="form-group ">

						<form:hidden path="id"/>
						<form:hidden path="version"/>
								
						<acme:textbox code="miscellaneousData.text" path="text" />
						<acme:textarea code="miscellaneousData.attachments" path="attachments" />
						<spring:message code = "miscellaneousData.message.comma"/>
						
						
						<br>
						<br>
						
					
		
						
						<acme:submit name="save" code="miscellaneousData.save" />
						<jstl:if test="${miscellaniusData.id!=0}">
							<acme:submitConfirmation name="delete" code="miscellaneousData.delete"
								onclick="miscellaneousData.delete.confirmation" />
						</jstl:if>
						<acme:cancel url="/curriculum/rookie/show.do?curriculumId=${curriculum.id}"
							code="miscellaneousData.cancel" />
					</div>
				</form:form>
			</fieldset>
		

		</div>
	</div>
</div>
