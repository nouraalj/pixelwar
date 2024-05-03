import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np

# Charger les données à partir du fichier
data = np.loadtxt('testNbThreads_time2.txt')

# Séparer les données en nombre de threads et valeurs de délai
threads = data[:, 0]  # Première colonne : nombre de threads
delays = data[:, 1:]  # Les colonnes restantes : valeurs de délai

# Créer un DataFrame avec les données
import pandas as pd
df = pd.DataFrame(delays, columns=[f"Thread {i+1}" for i in range(delays.shape[1])])

df_subset = df.iloc[:, :20]

x_labels = [f"{i+1}" for i in range(20)]

# Tracer les boîtes à moustaches avec Seaborn
plt.figure(figsize=(10, 6))
sns.boxplot(data=df_subset, medianprops={'linewidth': 2.5}, showfliers=False)  # Épaissir la ligne de la médiane
plt.xlabel('Nombre de threads')
plt.ylabel('Délai d\'attente')
plt.title('Boîte à moustaches du délai d\'attente en fonction du nombre de threads pour la stratégie 2')
plt.xticks(range(20), x_labels, rotation=45)
plt.savefig("../plot/time/nbThreads2.png")
# À DÉCOMMENTER SI ON VEUT LE GRAPHIQUE À L'EXÉCUTION
#plt.show()
