/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.produit;

import controller.vente.*;
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
import model.Produit;
import model.Service;
import model.view.V_PrixProduit;
import model.view.V_PrixService;
import model.view.V_VenteService;

/**
 *
 * @author USER
 */
@WebServlet(name = "Vente", urlPatterns = {"/VenteProduit"})
public class VenteProduit extends HttpServlet {
@Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws jakarta.servlet.ServletException, IOException {
              Connection connection = null;
             request.setAttribute("page-header", "Devis");
             request.setAttribute("title", "Demande de devis");
             try {
             Produit o=new Produit();
            connection=o.getConnection(false);
            ArrayList<Service> services =o.findAll(connection, "");
            request.setAttribute("produits", services);
            request.getRequestDispatcher("FormProduit.jsp").forward(request, response);
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
            String produit=request.getParameter("produit");
            if (produit==null) {
                throw new Exception("Veuillez selectionner un produit");
            }
            V_PrixProduit vp = new V_PrixProduit();
            vp.setId(Integer.parseInt(produit));
            vp.setServiceValue();
            request.setAttribute("produit", vp);
            request.getRequestDispatcher("Produit.jsp").forward(request, response);
        }catch(Exception e){
            request.getSession().setAttribute("message", Html.getMessage(e.getMessage(),true));
            response.sendRedirect("devis");
        }
    }
}
