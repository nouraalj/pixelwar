import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np

data = np.loadtxt('testTileSize_time1.txt')

# Séparer les données en taille de l'image et valeurs de délai
sizes = data[:, 0]  # Première colonne : taille de la tuile
delays = data[:, 1:]  # Les colonnes restantes : valeurs de délai

# Créer un DataFrame avec les données
import pandas as pd
df = pd.DataFrame(delays)

df['Tile Size'] = sizes

df_long = pd.melt(df, id_vars=['Tile Size'], value_name='Delay')

sns.set(style="whitegrid")
plt.figure(figsize=(12, 8))  # Taille de l'image : largeur = 12 pouces, hauteur = 8 pouces
sns.boxplot(data=df_long, x='Tile Size', y='Delay', showfliers=False, medianprops={'linewidth': 2.5})
plt.xlabel('Taille de la tuile')
plt.ylabel('Délai d\'attente')
plt.title('Boîte à moustaches du délai d\'attente en fonction de la taille de la tuile pour la stratégie GiantLock')
plt.xticks(rotation=45)  # Rotation de 45 degrés pour une meilleure lisibilité
plt.savefig("../plot/time/tileSize1.png")
# À DÉCOMMENTER SI ON VEUT LE GRAPHIQUE À L'EXÉCUTION
#plt.show()
