package pixelwar;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

class TreeNode {
    int x;
    int y;
    ReentrantLock lock;
    TreeNode left;
    TreeNode right;

    public TreeNode(int x, int y) {
        this.x = x;
        this.y = y;
        this.lock = new ReentrantLock();
        this.left = null;
        this.right = null;
    }

    public boolean tryLock() {
        return lock.tryLock();
    }

    public void unlock() {
        lock.unlock();
    }
}

public class BinaryTree {
    TreeNode root;
    int imageSize;
    int tileSize; // Taille de la tuile

    public BinaryTree(int imageSize, int tileSize) {
        this.root = null;
        this.imageSize = imageSize;
        this.tileSize = tileSize;
    }

    // Méthode pour insérer un nœud dans l'arbre
    private TreeNode insertNode(TreeNode node, int x, int y) {
        if (node == null) {
            return new TreeNode(x, y);
        }

        // Si la coordonnée x est inférieure à celle du nœud actuel, insérez à gauche
        if (x < node.x) {
            node.left = insertNode(node.left, x, y);
        }
        // Sinon, insérez à droite
        else {
            node.right = insertNode(node.right, x, y);
        }

        return node;
    }

    // Méthode pour insérer un pixel dans l'arbre en fonction de ses coordonnées
    public void insertPixel(int x, int y) {
        root = insertNode(root, x, y);
    }

    // Méthode de test pour afficher l'arbre
    private void printTree(TreeNode node) {
        if (node == null) {
            return;
        }

        printTree(node.left);
        System.out.println("(" + node.x + ", " + node.y + ")");
        printTree(node.right);
    }

    public void printTree() {
        printTree(root);
    }

    public TreeNode getRandomNode() {
        Random random = new Random();
        int x = random.nextInt(imageSize - tileSize + 1);
        int y = random.nextInt(imageSize - tileSize + 1);
        return findNode(root, x, y);
    }

    private TreeNode findNode(TreeNode node, int x, int y) {
        if (node == null) {
            return null;
        }

        if (node.x <= x && node.x + tileSize > x && node.y <= y && node.y + tileSize > y) {
            return node;
        }

        if (x < node.x) {
            return findNode(node.left, x, y);
        } else {
            return findNode(node.right, x, y);
        }
    }

    public static void main(String[] args) {
        int imageSize = 10; // Taille de l'image
        int tileSize = 3; // Taille maximale de la tuile
        int numThreads = 5; // Nombre de threads pour placer les tuiles
        BinaryTree tree = new BinaryTree(imageSize, tileSize);

        // Insertion des pixels dans l'arbre en parcourant l'image
        for (int i = 0; i < imageSize; i++) {
            for (int j = 0; j < imageSize; j++) {
                tree.insertPixel(i, j);
            }
        }

        // Création et démarrage des threads
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                while (true) {
                    TreeNode node = tree.getRandomNode();
                    if (node != null && lockTile(node, tileSize)) {
                        // Tuile placée avec succès, traitement à effectuer ici
                        System.out.println("Thread " + Thread.currentThread().getId() + " placed tile at (" + node.x + ", " + node.y + ")");
                        unlockTile(node, tileSize);
                    }
                    // Simuler un délai avant d'essayer de placer une autre tuile
                    try {
                        Thread.sleep(100); // Temps de sommeil arbitraire
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads[i].start();
        }
    }

    private static boolean lockTile(TreeNode node, int tileSize) {
        for (int i = 0; i < tileSize; i++) {
            for (int j = 0; j < tileSize; j++) {
                if (!node.tryLock()) {
                    // Si un pixel est déjà verrouillé, libérer tous les pixels verrouillés jusqu'à présent
                    unlockTile(node, tileSize);
                    return false;
                }
            }
        }
        return true;
    }

    private static void unlockTile(TreeNode node, int tileSize) {
        for (int i = 0; i < tileSize; i++) {
            for (int j = 0; j < tileSize; j++) {
                node.unlock();
            }
        }
    }
}
