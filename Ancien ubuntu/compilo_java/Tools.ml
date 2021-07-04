open CLessType

(** Ce module contient des fonctions d'aide à la compilation qui pourrons vous être utile. *)


(** {2 Exceptions pendant la génération de code } *)

(** L'exception [Code_gen_failure_expression] doit être levée lorsqu’une expression ne peut pas être compilée.
Cette exception est rattrapée par le compilateur pour afficher un message d'erreur.*)
exception Code_gen_failure_expression of expression

(** L'exception [Code_gen_failure_statement] doit être levée lorsqu’un statement ne peut pas être compiler.
Cette exception est rattrapée par le compilateur pour afficher un message d'erreur.*)
exception Code_gen_failure_statment of statement

(** {2 Fonctions d'aide à la manipulation des listes }*)

(** [ q |% t ] renvoie une liste dont la tête est [t] et la queue est [q] (i.e. [t::q])*)
let (|%) l i = i::l

(** [ x |> f ] renvoie l'évaluation de [f] sur [ x ] (i.e. [f x]) *)
let (|>) x f = f x


(** {2 Fonctions de manipulation d'assembleur } *)

(** Le compilateur utilise une représentation symbolique d'un programme assembleur ({!ASMType.asm_program})
plutôt que sous forme de chaine de caractère pour permettre d'écrire des optimisations du code généré.

Les fonctions suivantes permettent de parser des instructions assembleur décrites par des chaines de caractères et de renvoyer une représentation de l'instruction.
 *)


type jvm_code = string
type jvm_program = jvm_code list

(** [p s] renvoie l'instruction assembleur décrite par [s].

 Ex d'utilisation [ p "movq $3, %rax" ]. *)
let p s = (s:jvm_code)

(** [pi s i] renvoie l'instruction assembleur décrit par [s] où les occurrences de '%i' sont remplacé par [i].

Ex d'utilisation [ pi "movq %i, %rax" 3 ]. *)
let pi s i = (Printf.sprintf (Scanf.format_from_string s "%i") i:jvm_code)


(** [pa s a] renvoie l'instruction assembleur décrite par [s] ou les occurrences de '%a' sont remplacé par [a].
*)

let pa s a = (Printf.sprintf (Scanf.format_from_string s "%a") (fun () s -> s) a: jvm_code)

let ps s a = (Printf.sprintf (Scanf.format_from_string s "%s") a: jvm_code)

(** {2 Fonctions d'aide à la compilation } *)

(**/**)
let labcount = ref (-1)
(**/**)

(** Retourne un nom unique contenant la chaine de caractère donnée en argument *)
let fresh_lbl s =
  incr labcount;
  s^"_"^(string_of_int !labcount)

(**/**)
let string_tab:(string, string) Hashtbl.t = Hashtbl.create 10
(**/**)

(**/**)
module StringMap = Map.Make(String)

let funDecList = ref StringMap.empty
(**/**)

(** [getFunDec s] renvoi la définition de la fonction de nom [s] *)
let getFunDec (f:string) = ((StringMap.find f !funDecList):string list* statement)

let compileClassName = ref ""

let getClassName () =
  !compileClassName

type variable = Local of int | Static
