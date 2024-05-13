# coding=utf-8
import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np

# Charger les données à partir du fichier
data = np.loadtxt('testNbThreads_time1.txt')

# Séparer les données en nombre de threads et valeurs de délai
threads = data[:, 0]  # Première colonne : nombre de threads
delays = data[:, 1:]  # Les colonnes restantes : valeurs de délai

# Tracer les boîtes à moustaches avec Seaborn
sns.set(style="whitegrid")
plt.figure(figsize=(10, 6))
sns.boxplot(data=delays)
sns.boxplot(data=delays, medianprops={'linewidth': 2.5})  # Épaissir la ligne de la médiane
plt.xlabel('Nombre de threads')
plt.ylabel('Délai d\'attente')
plt.title('Boîte à moustaches du délai d\'attente en fonction du nombre de threads pour la stratégie GiantLock')
plt.xticks(range(len(threads), threads.astype(int), rotation=45)
plt.savefig("../plot/time/nbThreadsGiant.png")

# À DÉCOMMENTER SI ON VEUT LE GRAPHIQUE À L'EXÉCUTION
#plt.show()
