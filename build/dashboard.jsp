<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.Credit" %>
<%@ page import="main.Depense" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Credits</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h2>Liste des credits</h2>

        <div class="card-container">
        <%
            List<Credit> credits = (List<Credit>) request.getAttribute("credit");
            List<Depense> montants = (List<Depense>) request.getAttribute("montants");

            if (credits != null && !credits.isEmpty()) {
                for (Credit crd : credits) {
                    double montant = 0;

                    if (montants != null) {
                        for (Depense d : montants) {
                            if (d.getidCredit() == crd.getId()) {
                                montant = d.getMontant();
                                break;
                            }
                        }
                    }

                    double reste = crd.getSolde() - montant;
        %>
            <div class="credit-card">
                <h3><%= crd.getLibelle() != null ? crd.getLibelle() : "Libelle inconnu" %></h3>
                <p><strong>Montant total depense :</strong> <%= montant %> Ar</p>
                <p><strong>Reste :</strong> <%= reste %> Ar</p>
            </div>
        <%
                }
            } else {
        %>
            <p class="no-credit">Aucun credit disponible.</p>
        <%
            }
        %>
        </div>
    </div>
</body>
</html>
