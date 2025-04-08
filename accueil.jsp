<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="main.Client" %>
<%
    if (session == null || session.getAttribute("id_client") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Client client = (Client) session.getAttribute("id_client");
    int id = client.getId(); 
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajout de Credit</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <div class="credit-container">
        <h1>Bienvenue, client ID : <%= id %></h1>
        <h2>Ajouter une ligne de credit</h2>

        <form name="form" method="post" action="ajoutCredit">
            <div class="form-group">
                <label for="libelle">Libelle :</label>
                <input type="text" name="libelle" id="libelle">
            </div>

            <div class="form-group">
                <label for="solde">Solde :</label>
                <input type="number" name="solde" id="solde" required placeholder="ex: 5000">
            </div>

            <input type="submit" value="Soumettre" class="submit-btn">
        </form>

        <div class="link-box">
            <a href="ajoutCredit">Faire une depense</a>
        </div>
    </div>
</body>
</html>
