/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.vente;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Html;
import model.Service;
import model.view.V_PrixService;
import model.view.V_VenteService;

/**
 *
 * @author USER
 */
@WebServlet(name = "Vente", urlPatterns = {"/Vente"})
public class Vente extends HttpServlet {
@Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws jakarta.servlet.ServletException, IOException {
              Connection connection = null;
             request.setAttribute("page-header", "Devis");
             request.setAttribute("title", "Demande de devis");
             try {
             Service o=new Service();
            connection=o.getConnection(false);
            ArrayList<Service> services =o.findAll(connection, "");
            request.setAttribute("services", services);
            request.getRequestDispatcher("FormVente.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().print(e.getMessage());
                request.getSession().setAttribute("message",e.getMessage());
                //response.sendRedirect("benefice");
            }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("page-header", "Devis");
        request.setAttribute("title", "Resultat de la demande de devis");
        try{
            String service=request.getParameter("service");
            if (service==null) {
                throw new Exception("Veuillez selectionner un service");
            }
            V_VenteService v_Service=new V_VenteService();
            v_Service.setId_service(Integer.parseInt(service));
            v_Service.setServiceValue();
            Service s=new Service();
            s.setId(v_Service.getId_service());
            s=s.getService();
            request.setAttribute("service", v_Service);
            request.setAttribute("service_obj", s);
            request.getRequestDispatcher("Devis.jsp").forward(request, response);
        }catch(Exception e){
            request.getSession().setAttribute("message", Html.getMessage(e.getMessage(),true));
            response.sendRedirect("devis");
        }
    }
}
