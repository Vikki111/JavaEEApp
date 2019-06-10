
package servlets;

import beans.Login;
import DB.LoginDB;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class LoginServlet extends HttpServlet {

   private static final long serialVersionUID = 1L;
    private LoginDB loginDB;

   @Override
    public void init() {
        loginDB = new LoginDB();
    }

   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insert(request, response);
                    break;
                case "/delete":
                    delete(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/search":
                    search(request, response);
                    break;
                default:
                    list(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void list(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException, ServletException {
        List<Login> listUser = loginDB.selectAll();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("login_list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("login_form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Login login = loginDB.selectById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("login_form.jsp");
        request.setAttribute("login", login);
        dispatcher.forward(request, response);

    }

    private void insert(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
        if(request.getParameter("id")!=null){ //update
            int id = Integer.parseInt(request.getParameter("id"));
            Login newLogin = new Login(id, username, pass);
            loginDB.update(newLogin); 
        }else{
            Login newLogin = new Login(username, pass);
            loginDB.insert(newLogin);
        }
        response.sendRedirect("list");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
    throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        loginDB.delete(id);
        response.sendRedirect("list");

    }

    private void search(HttpServletRequest request, HttpServletResponse response) 
    throws SQLException, IOException, ServletException {
        String field = request.getParameter("field");
        String searchInput = request.getParameter("searchInput");
        List<Login> loginList;
        if (field.equals("-1")) {
            loginList = loginDB.selectByUsername(searchInput);
        } else {
            loginList = loginDB.selectByPass(searchInput);
        }
        request.setAttribute("listUser", loginList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("login_list.jsp");
        dispatcher.forward(request, response);
    }
}
