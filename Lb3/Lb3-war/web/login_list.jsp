<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
 <title>Login List</title>
</head>
<body>
  <h1>Login List</h1>
    <div>
     <a href="new">Add New User</a>
     <a href="list">List All Users</a>
    </div>
    <br>
    <div>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Pass</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            <c:forEach var="login" items="${listUser}">
                <tr>
                    <td><c:out value="${login.id}" /></td>
                    <td><c:out value="${login.username}" /></td>
                    <td><c:out value="${login.pass}" /></td>
                    <td>
                     <a href="edit?id=<c:out value='${login.id}' />">Edit</a>
                    </td>
                    <td>
                     <a href="delete?id=<c:out value='${login.id}' />">Delete</a>                     
                    </td>
                </tr>
            </c:forEach>
        </table>
        <br>
        <form action="search" method="post">
            <select name="field">
                <option value="-1">Name</option>
                <option value="Year">Pass</option>
            </select>
            <input name="searchInput"/>
            <input type="submit" value="Search"/>
        </form>
    </div> 
</body>
</html>
