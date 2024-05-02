#!/bin/bash
# Executable Linux/MacOs

PYTHON_CMD=py # Remplacer par votre commande python si nécessaire
$PYTHON_CMD plot_DurationSum.py
$PYTHON_CMD plot_ImgSizeSum.py
$PYTHON_CMD plot_NbThreadsSum.py
$PYTHON_CMD plot_TileSizeSum.py

echo "Voir les graphiques produits dans le repertoire data/plot/pixelSum"