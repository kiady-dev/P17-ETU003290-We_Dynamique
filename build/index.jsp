<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion Client</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <div class="login-container">
        <h2>Connexion</h2>
        <form action="login" method="post">
            <input type="text" name="email" placeholder="test@email.com" required /><br />
            <input type="password" name="motdepasse" placeholder="1234" required /><br />
            <input type="submit" value="Se connecter" />
        </form>

        <%
            String error = (String) request.getAttribute("erreur");
            if (error != null) {
                out.println("<p class='error-message'>" + error + "</p>");
            }
        %>
    </div>
</body>
</html>
