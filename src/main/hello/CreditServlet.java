package milay;
import main.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class CreditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        String libelle = request.getParameter("libelle");
        double solde = Double.parseDouble(request.getParameter("solde"));

        try {
            Credit credit= new Credit(libelle, solde);
            credit.save();

            request.getRequestDispatcher("accueil.jsp").forward(request, response);
        } catch (Exception e) {
            handleError(request, response, e);
        }
        
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Credit credit = new Credit();
        try {
            List<Credit> list = credit.findAll();
            
            System.out.println("Nombre de credit trouvés : " + list.size()); // Log
            request.setAttribute("credit", list);
            request.getRequestDispatcher("depense.jsp").forward(request, response);
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException {
        e.printStackTrace(); // Affiche l'erreur dans les logs du serveur
        request.setAttribute("errorMessage", e.getMessage());
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }
}
