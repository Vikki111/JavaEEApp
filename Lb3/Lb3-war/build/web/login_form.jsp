<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
 <title>Login Form</title>
</head>
<body>
  <h1>Login Form</h1>
    <h2>
     <a href="list">List All Users</a>
    </h2>
    <div>
   <form action="insert" method="post">
          <c:if test="${login != null}">
           <input type="hidden" name="id" value="<c:out value='${login.id}' />" />
          </c:if>  
           <table>
            <tr>
                <td>
                    Login:
                 <input type="text" name="username" size="45"
                   value="<c:out value='${login.username}' />"/>
                </td>
            </tr>
            <tr>
                <td>
                    Password:
                 <input type="password" name="pass" size="42"
                   value="<c:out value='${login.pass}' />"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Save" />
                </td>
            </tr>
        </table>
        </form>
    </div> 
</body>
</html>
