import matplotlib.pyplot as plt
import numpy as np

# Charger les données à partir du fichier
donnees = np.loadtxt('testImgSize_sum.txt')

# Récupération des données pour les différentes courbes
taille_image = donnees[:, 0]
donnees_courbe1 = donnees[:, 1]
donnees_courbe2 = donnees[:, 2]
donnees_courbe3 = donnees[:, 3]

# Création du graphique
plt.figure(figsize=(10, 6))  # Définir la taille du graphique si nécessaire

# Tracer les courbes
plt.plot(taille_image, donnees_courbe1, label='Stratégie GiantLock')
plt.plot(taille_image, donnees_courbe2, label='Stratégie InterLock')
plt.plot(taille_image, donnees_courbe3, label='Stratégie PixelLock')

# Ajouter des titres et des légendes
plt.title('Débit de pixels posés en fonction de la taille d\'image')
plt.xlabel('Taille d\'image')
plt.ylabel('Débit de pixels posés')
plt.legend()

# Afficher le graphique
plt.grid(True)
plt.savefig("../plot/pixelSum/imgSize.png")
# À DÉCOMMENTER SI ON VEUT LE GRAPHIQUE À L'EXÉCUTION
#plt.show()
