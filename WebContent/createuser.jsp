<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="http://localhost:8080/RestDemo/rest/user/createUser">
Username<input type="text" name="username" />
E-mail<input type="text" name="email" />
Password<input type="password" name="password" /> 
<input type="submit" value="CreateUser" name="submit" />

</form>
</body>
</html>