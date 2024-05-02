#!/bin/bash
# Executable Linux/MacOs
PYTHON_CMD=py # Remplacer par votre commande python
$PYTHON_CMD plot_ImgSizeTime_1.py
$PYTHON_CMD plot_ImgSizeTime_2.py
$PYTHON_CMD plot_ImgSizeTime_3.py

$PYTHON_CMD plot_NbThreadsTime_1.py
$PYTHON_CMD plot_NbThreadsTime_2.py
$PYTHON_CMD plot_NbThreadsTime_3.py

$PYTHON_CMD plot_TileSizeTime_1.py
$PYTHON_CMD plot_TileSizeTime_2.py
$PYTHON_CMD plot_TileSizeTime_3.py

$PYTHON_CMD plot_TileVariableTime_1.py
$PYTHON_CMD plot_TileVariableTime_2.py
$PYTHON_CMD plot_TileVariableTime_3.py
echo "Voir les graphiques produits dans le repertoire data/plot/time"