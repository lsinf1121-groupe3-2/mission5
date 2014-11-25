package business;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class BTree<K, V> {
	private Node root;
	
	private Comparator<K> comparator;
	
	/**
	 * Constructeur
	 */
	public BTree(Comparator<K> comparator) {
		this.comparator = comparator;
	}
	
	/**
	 * Implémentation de Node
	 */
	private class Node {
		private K key;
		private TreeSet<V> values = new TreeSet<V>();
		private Node left;
		private Node right;
		
		/**
		 * Constructeur
		 */
		public Node(K key, V value) {
			this.key = key;
			this.values.add(value);
			this.left = null;
			this.right = null;
		}
	}
	
	
	/**
	 *  @pre	--
	 *  @post	-	retourne null si la clé recherchée k n'est pas dans 
	 * 				l'arbre
	 * 			-	retourne un itérateur contenant les valeurs du noeud
	 * 				correspondant à la clé k 
	 */
	public Iterator<V> get(K key) {
		return get(root, key);
	}
	/**
	 * @pre  	--
	 * @post 	- 	retourne null si le noeud est vide
	 * 			- 	lance une recherche dans l'arbre de gauche si la clé
	 * 				recherchée k est inférieure à la clé de node
	 * 			-	lance une recherche dans l'arbre de droite si la clé
	 * 				recherchée k est supérieur à la clé de node
	 * 			- 	retourne un itérateur contenant les valeurs du noeud
	 * 				si la clé recherchée k est égale à la clé de node
	 */
	private Iterator<V> get(Node node, K key) {
		if(node == null) return null;
		int comp = comparator.compare(key, node.key);
		if(comp < 0)
			return get(node.left, key);
		else if(comp > 0)
			return get(node.right, key);
		return node.values.iterator();
	}
	
	/**
	 * @pre		--
	 * @post	value est ajouté à l'arbre root
	 */
	public void put(K key, V value) {
		root = put(root, key, value);
	}
	
	/**
	 * @pre		--
	 * @post	- 	crée un nouvelle arbre si root est vide
	 * 			-	ajoute value à l'arbre root
	 */
	private Node put(Node node, K key, V value) {
		if(node == null) return new  Node(key, value);
		int comp = comparator.compare(key, node.key);
		if(comp  < 0) node.left = put(node.left, key, value);
		else if(comp > 0) node.right = put(node.right, key, value);
		else node.values.add(value);
		return node;
	}
}
