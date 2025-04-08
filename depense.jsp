<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.Credit" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Nouvelle Depense</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="depense-container">
        <h2>Ajouter une ligne de depense</h2>

        <form name="form" method="post" action="depense">
            <div class="form-group">
                <label for="credit">Choisir un credit :</label>
                <select name="credit" id="credit" class="form-select" required>
                    <%
                        List<Credit> credit = (List<Credit>) request.getAttribute("credit");
                        if (credit != null && !credit.isEmpty()) {
                            for (Credit crd : credit) {
                    %>
                                <option value="<%= crd.getId() %>">
                                    <%= crd.getLibelle() != null ? crd.getLibelle() : "Libelle non disponible" %>
                                </option>
                    <%
                            }
                        } else {
                    %>
                            <option disabled selected>Aucun credit disponible</option>
                    <%
                        }
                    %>
                </select>
            </div>

            <div class="form-group">
                <label for="montant">Montant :</label>
                <input type="number" name="montant" id="montant" required placeholder="ex: 25000">
            </div>

            <input type="submit" value="Soumettre" class="submit-btn">
        </form>

        <div class="link-box">
            <a href="depense">Retour au Dashboard</a>
        </div>
    </div>
</body>
</html>
