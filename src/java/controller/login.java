/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.LutwaffeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.registry.infomodel.PersonName;
import obj.Kontrol;
import obj.Personal;

/**
 *
 * @author daw2
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {

    LutwaffeDAO dao = new LutwaffeDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Kontrol tmp = null;
        boolean validUsu = false;
        try {
            validUsu = dao.validaKontrol(username, password);
        } catch (SQLException e) {
            request.setAttribute("status", "No se ha podido proceder");
            request.getRequestDispatcher("/final.jsp").forward(request, response);
        }
        if (validUsu) {
            try {
                tmp = dao.getKontrolByUsername(username);
                request.getSession(true).setAttribute("user", tmp);
                request.setAttribute("status", "Exito al logear-se");
                response.sendRedirect(request.getContextPath() + "/final.jsp");
            } catch (SQLException e) {
                request.setAttribute("status", e.getMessage());
                request.getRequestDispatcher("/errorUser.jsp").forward(request, response);
            }
        }else{
            request.setAttribute("status", "Acceso Incorrecto");
            request.getRequestDispatcher("/final.jsp").forward(request, response);
        }
        //Check if user exists

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
