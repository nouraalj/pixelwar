import matplotlib.pyplot as plt
import numpy as np

# Charger les données à partir du fichier
donnees = np.loadtxt('PixelWarDemo.txt')

# Récupération des données pour les différentes courbes
taille_image = donnees[:, 0]
donnees_courbeF = donnees[:, 1]
donnees_courbeE = donnees[:, 2]

# Création du graphique
plt.figure(figsize=(10, 6))  # Définir la taille du graphique si nécessaire

# Tracer les courbes
plt.plot(taille_image, donnees_courbeF, label='France')
plt.plot(taille_image, donnees_courbeE, label='Espagne')

# Ajouter des titres et des légendes
plt.title('Débit de pixels posés en fonction de la durée expérience')
plt.xlabel('Durée de l\'expérience (en millisecondes)')
plt.ylabel('Débit de pixels posés')
plt.legend()

# Afficher le graphique
plt.grid(True)
plt.savefig("../plot/FranceVsEspagne.png")

# À DÉCOMMENTER SI ON VEUT LE GRAPHIQUE À L'EXÉCUTION
plt.show()
