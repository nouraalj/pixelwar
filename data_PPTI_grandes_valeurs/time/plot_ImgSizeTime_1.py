import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np

# Charger les données à partir du fichier
data = np.loadtxt('testImgSize_time1.txt')

# Séparer les données en taille de l'image et valeurs de délai
sizes = data[:, 0]  # Première colonne : taille de l'image
delays = data[:, 1:]  # Les colonnes restantes : valeurs de délai

# Créer un DataFrame avec les données
import pandas as pd
df = pd.DataFrame(delays)

df['Image Size'] = sizes

df_long = pd.melt(df, id_vars=['Image Size'], value_name='Delay')

# Tracer les boîtes à moustaches avec Seaborn
plt.figure(figsize=(12, 8)) 
sns.set(style="whitegrid")
# Pour afficher les données aberrantes, enlever showfliers=False
sns.boxplot(data=df_long, x='Image Size', y='Delay', showfliers=False, medianprops={'linewidth': 2.5})
plt.xlabel('Taille de l\'image')
plt.ylabel('Délai d\'attente')
plt.title('Boîte à moustaches du délai d\'attente en fonction de la taille de l\'image pour la stratégie GiantLock')
plt.xticks(rotation=45) 
plt.savefig("../plot/time/imgSize1.png")
# À DÉCOMMENTER SI ON VEUT LE GRAPHIQUE À L'EXÉCUTION
#plt.show()