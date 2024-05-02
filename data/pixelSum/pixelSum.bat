:: Exécutable Windows
:: Remplacer py par votre commande Python

set PYTHON_CMD=py
%PYTHON_CMD% plot_DurationSum.py
%PYTHON_CMD% plot_ImgSizeSum.py
%PYTHON_CMD% plot_NbThreadsSum.py
%PYTHON_CMD% plot_TileSizeSum.py
@echo off
ECHO Voir les graphiques produits dans le repertoire data/plot/pixelSum
pause
