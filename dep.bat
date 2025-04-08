@echo off

REM Nom de l'application et chemins principaux
set APPLICATION=ETU003290
set WORK_DIR=C:\Users\Kiady\Documents\S4\Reseaux\FormulaireSession

REM Définition des chemins
set BUILD_DIR=%WORK_DIR%\build
set LIB_DIR=%WORK_DIR%\lib
set XML_FILE=%WORK_DIR%\src\webapp\WEB-INF
set SRC_DIR=%WORK_DIR%\src
set DEPLOYMENT_DIR=C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps

REM Suppression du dossier build
echo Suppression de l'ancien répertoire build...
if exist "%BUILD_DIR%" (
    rmdir /s /q "%BUILD_DIR%"
)

REM Création de la structure du dossier build
echo Création de la structure du répertoire build...

mkdir "%BUILD_DIR%\WEB-INF"
mkdir "%BUILD_DIR%\WEB-INF\classes"
mkdir "%BUILD_DIR%\WEB-INF\lib"

REM Copie des fichiers nécessaires
echo Copie des fichiers depuis les répertoires sources...
xcopy /Q "%XML_FILE%\*.xml" "%BUILD_DIR%\WEB-INF\"
xcopy /E /Q "%LIB_DIR%" "%BUILD_DIR%\WEB-INF\lib"
xcopy /Q "%WORK_DIR%\*.html" "%BUILD_DIR%\"
xcopy /Q "%WORK_DIR%\*.jsp" "%BUILD_DIR%\"
xcopy /Q "%WORK_DIR%\*.css" "%BUILD_DIR%\"




REM Compilation des fichiers Java
echo Compilation des fichiers Java...
cd "%SRC_DIR%"
dir /s /B *.java > sources.txt
javac -d "%BUILD_DIR%\WEB-INF\classes" -cp "%BUILD_DIR%\WEB-INF\lib\*" @sources.txt
if errorlevel 1 (
    echo Erreur lors de la compilation. Vérifiez votre code.
    del sources.txt
    exit /b 1
)
del sources.txt



REM Création du fichier .war
echo Création du fichier WAR...
cd "%BUILD_DIR%"
jar cf "%APPLICATION%.war" -C "%BUILD_DIR%" .
if not exist "%APPLICATION%.war" (
    echo Échec de la création du fichier WAR.
    exit /b 1
)

REM Déploiement vers le répertoire webapps de Tomcat
echo Déploiement de l'application vers Tomcat...
xcopy /Y "%BUILD_DIR%\%APPLICATION%.war" "%DEPLOYMENT_DIR%\"
if errorlevel 1 (
    echo Erreur lors du déploiement. Vérifiez les permissions et le chemin.
    exit /b 1
)

REM Optionnel : redémarrage de Tomcat
REM call "C:\Program Files\apache-tomcat-9.0.82\bin\shutdown.bat"
REM timeout /t 5 /nobreak > nul
REM call "C:\Program Files\apache-tomcat-9.0.82\bin\startup.bat"
REM echo Tomcat a été redémarré.

echo Déploiement terminé avec succès. Accédez à votre application via Tomcat.
pause
