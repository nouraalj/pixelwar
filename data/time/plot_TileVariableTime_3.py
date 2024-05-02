import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np

# Charger les données à partir du fichier
data = np.loadtxt('testTileVariable_time3.txt')

# Séparer les données en taille de la tuile et valeurs de délai
sizes = data[:, 0]  # Première colonne : taille de la tuile
delays = data[:, 1:]  # Les colonnes restantes : valeurs de délai

# Créer un DataFrame avec les données
import pandas as pd
df = pd.DataFrame(delays)

# Ajouter la taille de la tuile en tant que colonne dans le DataFrame
df['Tile Size'] = sizes

# Transformer les données en format long
df_long = pd.melt(df, id_vars=['Tile Size'], value_name='Delay')

# Tracer les boîtes à moustaches avec Seaborn
sns.set(style="whitegrid")
plt.figure(figsize=(12, 8))  # Taille de l'image : largeur = 12 pouces, hauteur = 8 pouces
sns.boxplot(data=df_long, x='Tile Size', y='Delay', showfliers=False, medianprops={'linewidth': 2.5})
plt.xlabel('Taille de la tuile')
plt.ylabel('Délai d\'attente')
plt.title('Boîte à moustaches du délai d\'attente en fonction de la taille de la tuile (variable) pour la stratégie 3')
plt.xticks(rotation=45)  # Rotation de 45 degrés pour une meilleure lisibilité
plt.savefig("../plot/time/tileVariableSize3.png")
plt.show()
