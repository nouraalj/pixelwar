:: Exécutable Windows
:: Remplacer py par votre commande Python

set PYTHON_CMD=py
%PYTHON_CMD% plot_ImgSizeTime_1.py
%PYTHON_CMD% plot_ImgSizeTime_2.py
%PYTHON_CMD% plot_ImgSizeTime_3.py

%PYTHON_CMD% plot_NbThreadsTime_1.py
%PYTHON_CMD% plot_NbThreadsTime_2.py
%PYTHON_CMD% plot_NbThreadsTime_3.py

%PYTHON_CMD% plot_TileSizeTime_1.py
%PYTHON_CMD% plot_TileSizeTime_2.py
%PYTHON_CMD% plot_TileSizeTime_3.py

%PYTHON_CMD% plot_TileVariableTime_1.py
%PYTHON_CMD% plot_TileVariableTime_2.py
%PYTHON_CMD% plot_TileVariableTime_3.py
@echo off
ECHO Voir les graphiques produits dans le repertoire data/plot/time
pause
