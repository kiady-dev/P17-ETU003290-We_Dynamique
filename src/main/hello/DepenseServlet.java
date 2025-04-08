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



public class DepenseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        String credit = request.getParameter("credit");
        int idCredit = Integer.parseInt(credit);
        double montant = Double.parseDouble(request.getParameter("montant"));

        try {
            Depense depense= new Depense(montant, idCredit);
            Credit crd = new Credit(montant,idCredit).findById(idCredit);            
            depense.save();
            crd.updateSolde(montant);
        } catch (Exception e) {
            handleError(request, response, e);
        }

        try {
            List<Credit> list = new Credit().findAll();
            
            System.out.println("Nombre de credit trouvés : " + list.size()); // Log
            request.setAttribute("credit", list);
            request.getRequestDispatcher("depense.jsp").forward(request, response);
        } catch (Exception e) {
            handleError(request, response, e);
        }
        
        
    }

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        Credit credit = new Credit();
        Depense dep = new Depense();

      
        List<Credit> listCredits = credit.findAll();
        request.setAttribute("credit", listCredits);

        List<Depense> listeTotaux = new ArrayList<>();
        for (Credit c : listCredits) {
            Depense total = dep.getMontantTotal(c.getId());
            if (total != null) {
                listeTotaux.add(total);
            }
        }
        request.setAttribute("montants", listeTotaux);

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
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
