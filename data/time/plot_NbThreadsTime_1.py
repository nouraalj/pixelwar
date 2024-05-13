# coding=utf-8
import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np

# Charger les données à partir du fichier
data = np.loadtxt('testNbThreads_time1.txt')

# Séparer les données en nombre de threads et valeurs de délai
threads = data[:, 0]  # Première colonne : nombre de threads
delays = data[:, 1:]  # Les colonnes restantes : valeurs de délai

# Créer un DataFrame avec les données
import pandas as pd
df = pd.DataFrame(delays)

df['Nb threads'] = threads

df_long = pd.melt(df, id_vars=['Nb threads'], value_name='Delay')

# Tracer les boîtes à moustaches avec Seaborn
sns.set(style="whitegrid")
plt.figure(figsize=(10, 7))
sns.boxplot(data=df_long, x='Nb threads', y='Delay', showfliers=False, medianprops={'linewidth': 2.5})
plt.xlabel('Nombre de threads')
plt.ylabel('Délai d\'attente')
plt.title('Boîte à moustaches du délai d\'attente en fonction du nombre de threads pour la stratégie GiantLock')
plt.xticks(rotation=45)  # Rotation de 45 degrés pour une meilleure lisibilité
plt.savefig("../plot/time/nbThreadsGiant.png")

# À DÉCOMMENTER SI ON VEUT LE GRAPHIQUE À L'EXÉCUTION
#plt.show()
