<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EGN</title>
</head>
<body>
	<center>
		<fieldset>
			<legend>EGN</legend>
			<form:form method="POST" action="/Validator/EGN_validate">
				<table>
					<tr>
						<td><form:label path="egn">EGN</form:label></td>
						<td><form:input path="egn" value="${egn}"/></td>
					</tr>	
					<tr>
						<td colspan="2">
							<input type="submit" value="Check EGN" />
						</td>
					</tr>
				</table>
			</form:form>
			
			<p>${warning}</p>
			<p>${desc}</p>
			
			<table>
				<tr>
					<td>Birth date:</td>
					<td>${birthDate}</td>
				</tr>
				<tr>
					<td>Sex:</td>
					<td>${sex}</td>
				</tr>
			</table>
		</fieldset>
	</center>
</body>
</html>