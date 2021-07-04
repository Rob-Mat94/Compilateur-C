(**
  Module principal. Compile du code C sous forme d'AST décrit dans le module {!CLessType}
 en du code assembleur décris dans le module {!ASMType} par le type {!ASMType.asm_program} .
 *)

(**
[generate_asm_expression varl sp e p] génère les instructions implémentant l'expression [e] et l'ajoute
    au programme [p] passé en paramètre.
  - [varl] est une liste associant des noms de variable (locales ou globales) à des adresses
  - [e] est l' expression à compiler
  - [p] est le programme auquel la fonction ajoute le code compilé
*)
val generate_jvm_expression :
  (string*Tools.variable) list ->
  CLessType.expression ->
  Tools.jvm_program -> int * Tools.jvm_program

(**
[generate_asm_statement varl sp returnLabel s p] génère les instructions implémentant le statement [s] et l'ajoute
    au programme [p] passé en paramètre.
  - [varl] est une liste associant des noms de variable (locales ou globales) à des adresses
  - [sp] est la distance en octet entre le sommet et la base de la pile.
  - [s] est le statement à compiler
  - [p] est le programme auquel la fonction ajoute le code compilé
*)
val generate_jvm_statement :
  (string*Tools.variable) list -> int ->
  CLessType.statement ->
  Tools.jvm_program -> int *int * Tools.jvm_program


(**
[generate_asm_top varl p dec] génère les instructions implémentant la déclaration  [dec] et l'ajoute
    au programme [p] passé en paramètre.
  - [varl] est une liste associant des noms de variable globale à des adresses
  - [p] est le programme auquel la fonction ajoute le code compilé
  - [dec] est la déclaration à compiler
*)
val generate_jvm_top :
  (string*Tools.variable) list ->
  Tools.jvm_program ->
  CLessType.declaration -> Tools.jvm_program
