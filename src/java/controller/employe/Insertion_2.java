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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employe;
import model.Html;
import model.Specialite;

@WebServlet(name = "Insertion_2", urlPatterns = {"/employe_2"})
public class Insertion_2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("page-header", "Insertion Employe");
        request.setAttribute("title", "Insertion employe - Etape 2/2");
        HashMap<String, Object> data = (HashMap<String, Object>) request.getSession().getAttribute("data");
        Connection connection = null;
        try {
            if((String)data.get("specialite")==null || data.get("specialite").equals("")) throw new Exception(Html.getMessage("Aucune specialite n'a ete selectionnee", true));
            int specialite = Integer.parseInt((String) data.get("specialite"));
            request.setAttribute("specialite", specialite);
            Specialite specialiteModel = new Specialite();
            connection = specialiteModel.getConnection(false);
            ArrayList<Specialite> specialites = specialiteModel.findAll(connection, "");
            request.setAttribute("specialites", specialites);
            request.getRequestDispatcher("FormEmploye_2.jsp").forward(request, response);
        } catch (Exception e) {
            request.getSession().setAttribute("message",e.getMessage());
            response.sendRedirect("employe");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] specialites = request.getParameterValues("specialite");
        Connection connection = null;
        try {
            HashMap<String, Object> data = (HashMap<String, Object>) request.getSession().getAttribute("data");
            Employe employe = new Employe();
            employe.checkSpecialite(specialites);
            employe.setEmploye(data);
            connection = employe.getConnection(false);
            int id = employe.save(connection);
            for (String specialite : specialites) {
                employe.saveSpecialite(connection,id, Integer.parseInt(specialite));
            }
            connection.commit();
            request.getSession().removeAttribute("data");
            request.getSession().setAttribute("message", Html.getMessage("Employe ajouté avec succès",false));
            response.sendRedirect("employe");
        } catch (Exception e) {
            try {
                request.getSession().setAttribute("message", Html.getMessage(e.getMessage(),true));
                if(connection!=null)  {connection.rollback();}
            } catch (SQLException ex) {
                request.getSession().setAttribute("message", Html.getMessage(ex.getMessage(),true));
            }finally {
                response.sendRedirect("employe_2");
            }

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    request.getSession().setAttribute("message", Html.getMessage(e.getMessage(),true));
                    response.sendRedirect("employe_2");
                }
            }
        }
    }
}
