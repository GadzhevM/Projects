<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IBAN</title>
</head>
<body>
	<center>
		<fieldset>
			<legend>IBAN</legend>
			<form:form method="POST" action="/IBAN/validate">
				<table>
					<tr>
						<td><form:label path="iban">IBAN</form:label></td>
						<td><form:input path="iban" value="${iban}"/></td>
					</tr>	
					<tr>
						<td colspan="2">
							<input type="submit" value="Check IBAN" />
						</td>
					</tr>
				</table>
			</form:form>
			
			<p>${msg}</p>
			
			<form:form method="POST" action="">
				<table>
					<tr>
						<td><form:label path="swift">SWIFT code</form:label></td>
						<td><form:input path="swift" value="${swift}"/></td>
						<td><form:label path="bank">Bank name</form:label></td>
						<td><form:input path="bank" value="${bank}"/></td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="Submit" />
						</td>
					</tr>
				</table>
			</form:form>
		</fieldset>
	</center>
</body>
</html>