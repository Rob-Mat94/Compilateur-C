1) La méthode éval de classe expression est une méthode virtuelle,
elle est destinée à être utilisé et redéfinis par les classes fille de la classe 
expression. Cette méthode est appelé normalement, le compilateur déterminera quelle redéfiniton de la méthode utiliser (polymorhphisme)

2) L'attribut _gest est un ensemble d'expression(std::set), il pourra contenir des instances de classes filles, et
    permet le polymorhphisme. il est static.

3) le constructeur de la classe expression prend une référence constance string en paramètre pour l'attribut nom.
Le destructeur de la classe expression devra lui, libérer la mémoire dans le set _gest si nécessaire.

4) La méthode toutLibérer va vider le set _gest.

5) la fonction friendostream& operator<<(ostream&, constExpression&) n'est pas une méthode de la classe expression,
elle est amie de cette dernière, elle a alors accès aux attributs de expression. 
C'est la redéfiniton de l'opérateur de sortie. 

/// PARTIE 1 //
La méthode eval() peut être virtuelle pure dans la classe Expressino et Unaire, Binaire par exemple. Ce qui rend ces classe abstraite.
Nous n'avons besoin que des classes filles.  


/// Partie 3 //
1) stocker la valeur d’une variable dans un attribut d’instance de la classe Variable ici peut porter à confusion
et n'est pas vraiment une solution logique. 

2) l'attribut static _memoire permet de stocker la valeur de chaque instance de Variable avec leur nom comme clef.

3) 
La méthode effacerMemoire efface le map contenant les variables, On pourra l'appeler pour effacer toute les variables,
ou à la fin d'une evaluation de plusieurs expression par exemple  

4) La méthode éval de la classe affection va mettre dans le map static de Variable la valeur de l'évaluation de 
l'expression. Ici on retourne la nouvelle valeur de la variable par exemple.