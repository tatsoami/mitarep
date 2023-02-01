/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.employe;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

/**
 *
 * @author USER
 */
@WebServlet(name = "Insertion", urlPatterns = {"/employe"})
public class Insertion extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("page-header", "Insertion Employe");
        request.setAttribute("title", "Insertion employe - Etape 1/2");
        Connection connection = null;
        response.setContentType("text/html;charset=UTF-8");
        try {
            Genre genre=new Genre();
            Specialite specialite=new Specialite();
            connection= genre.getConnection(false);
            ArrayList<Genre> genres = genre.findAll(connection,"");
            ArrayList<Specialite> specialites = specialite.findAll(connection,"");
            ArrayList<Diplome> diplomes = new Diplome().findAll(connection,"");
            request.setAttribute("genres", genres);
            request.setAttribute("specialites", specialites);
            request.setAttribute("diplomes", diplomes);
            request.getRequestDispatcher("FormEmploye.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().println(Html.getMessage(e.getMessage(),true));
        }finally {
            try {
                if(connection!=null) connection.close();
            } catch (Exception e) {
                response.getWriter().println(Html.getMessage(e.getMessage(),true));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            HashMap<String, Object> data = new HashMap<>();
            data.put("nom", request.getParameter("nom"));
            data.put("prenom", request.getParameter("prenom"));
            data.put("genre", request.getParameter("genre"));
            data.put("specialite", request.getParameter("specialite"));
            data.put("diplome", request.getParameter("diplome"));
            data.put("salaire", request.getParameter("salaire"));
            Employe employe = new Employe();
            String dNaissance=request.getParameter("dateDeNaissance");
            if(request.getParameter("dateDeNaissance")==null || (String)request.getParameter("dateDeNaissance")==""){
                throw new Exception("Date de naissance");
            }
            Date d=Date.valueOf(request.getParameter("dateDeNaissance"));
            employe.setDate_naissance(d);
            data.put("date_naissance", request.getParameter("dateDeNaissance"));
            request.getSession().setAttribute("data", data);
            response.sendRedirect("employe_2");
        }catch (Exception e){
            request.getSession().removeAttribute("data");
            request.getSession().setAttribute("message", Html.getMessage(e.getMessage(),true));
            response.sendRedirect("employe");
        }
    }
}
