open CLessType
open Tools
open ASMType

let reg_list = [RDI;RSI;RDX;RCX;R8;R9]
let reg_list_string = ["%rdi";"%rsi";"%rdx";"%rcx";"%r8";"%r9"]
let reg_list_string_ref = ref reg_list_string

type environnement = (string * ASMType.arg) list

let generate_bop_expression varl sp bop il = 
  match bop with
      | Mult -> il |% p "imulq (%rsp), %rax"
      | Div -> let il2 = (il |%p "move $0, %rdx") in il2 |% p "idivq (%rsp), %rax"
      | Add -> il  |% p "addq (%rsp), %rax"
      | Sub -> il  |% p "subq (%rsp), %rax"
      | EQ -> let il2 = il |%p "cmpq (%rsp), %rax" in (il2 |%p "sete %al") 
      | NEQ -> let il2 = il |%p "cmpq (%rsp), %rax" in (il2 |%p "setne %al")
      | LE ->  let il2 = il |%p "cmpq (%rsp), %rax" in (il2 |%p "setns %al")
      | LL ->  let il2 = il |%p "cmpq %rax, (%rsp)" in (il2 |%p "setg %al")
      | Mod -> let il2 = il |%pi "movq %i, %rdx" 0 in let il3 = (il2 |%p "idivq (%rsp), %rax") in (il3 |%p "movq %rdx, %rax")
      | Or -> il |% p "orq (%rsp), %rax"
      | And -> il |% p "andq (%rsp), %rax"

let generate_uop_expression varl sp uop il = 
  match uop with
  |Not -> il |% p "xorq %rax, %rax"
  |MinusM -> il |% p "negq %rax" 
  |Deref -> il |% p "movq (%rax), %rax"


let sp_is_mod_16 sp = 
  match sp mod 16 with
  |0 -> true
  |_ -> false


let rec taille_expr expr = 
match expr with 
|IntegerLiteral i -> 1
|StringLiteral s -> 1 
|Var str -> 1
|Set(str,e) ->  1 + taille_expr e
|Seq(e::[]) -> 1 + taille_expr e
|Seq(e :: rl) -> 1 + taille_expr e + taille_expr (Seq(rl))
|Call(_,e::[]) -> 1 + taille_expr e
|Call(str,e::rl) -> 1 + taille_expr e + taille_expr (Call(str,rl))
|UnaryOperator(uop,e) -> 1 + taille_expr e
|BinaryOperator(e1,bop,e2) -> 1 + taille_expr e1 + taille_expr e2
and
  taille_stat s = 
  match s with 
  |WhileStmt(expr,state) -> 1 + taille_expr expr + taille_stat s
  |IfStmt(expr, stv, stf) -> 1 + taille_expr expr + taille_stat stv + taille_stat stf
  |CompoundStmt (nvarl,s1 :: []) -> 1 + taille_stat s1
  |CompoundStmt (nvarl,s1 :: sl) -> 1 + taille_stat s1 + taille_stat (CompoundStmt(nvarl,sl))
  |Expr e -> 1 + taille_expr e
  |ReturnStmt Some(expr) -> 1 + taille_expr expr
  |ReturnStmt None -> 1
and
  taille_dec decl = 
  match decl with 
  |VarDecl _ -> 0
  |FunctionDecl(s,strlist,state) -> 1 + taille_stat state


  let opti_check strname = try Some (getFunDec strname) with notFound -> None

  let rec check_inlining strname = 
    match opti_check strname with
    |Some(_)->( match getFunDec strname with 
                |str::rl,state -> if taille_stat state < 40 then true else false
                |_->false
              )
    |None -> false

let rec add_arg varl sp fundec exprlist il fct_name = 
  match fundec,exprlist with 
  |(str::rl,state),(e1::el) -> let sp2 = sp + 8 
                      in
                        let varl2 = ((str,(ARI(IL(-sp2),RBP))) :: varl) 
                      in
                        let il2 = (il |%pi "subq %i, %rsp" (sp2 -sp))  
                      in 
                        let il3 = generate_asm_expression varl2 sp2 e1 il2
                      in 
                        let il4 = generate_asm_expression varl2 sp2 (Call(str,el)) il3
                      in 
                        let il5 = (il4 |%pi "addq %i, %rsp" (sp2 -sp))
                      in 
                        add_arg varl2 sp2 (rl,state) el il5 fct_name

  |([],state),(e1 :: el) -> let il2 = generate_asm_expression varl sp e1 il
                            in generate_asm_expression varl sp (Call(fct_name,el)) il2      
  |([],state),([]) -> generate_asm_statement varl sp state il

and
 generate_asm_expression = fun varl sp e il ->
  try match e with

  |Call(str,exprlist) -> (
  match check_inlining str with 
  |true -> add_arg varl sp (getFunDec str) exprlist il str
  |false ->(
    let is_mod = sp_is_mod_16 sp in
   ( match exprlist,!reg_list_string_ref with 
    |e1 :: rl,r1 :: r2 ->
                          let il2 = generate_asm_expression varl sp e1 il
                          in let il3 = (il2 |% p ("movq %rax, "^r1))   
                          in ( 
                              (reg_list_string_ref := r2);
                              (generate_asm_expression varl sp (Call((str,rl))) il3) 
                             )
                             
    |e1 :: e2 , [] -> let il2 = generate_asm_expression varl sp e1 il 
                      in generate_asm_expression varl sp (Call(str,e2)) il2 
    |[],_ -> ( 
              (reg_list_string_ref := reg_list_string);
              (if is_mod then (il |%p ("callq "^str)) 
                else (let il2 = (il|%p "subq $8, %rsp") in let il3 = (il2 |%p ("callq "^str)) in (il3 |%p "addq $8, %rsp")))
            
             )
   )
  )



  )
  
(*
  |Call(str,exprlist) -> 
    let is_mod = sp_is_mod_16 sp in
   ( match exprlist,!reg_list_string_ref with 
    |e1 :: rl,r1 :: r2 ->
                          let il2 = generate_asm_expression varl sp e1 il
                          in let il3 = (il2 |% p ("movq %rax, "^r1))   
                          in ( 
                              (reg_list_string_ref := r2);
                              (generate_asm_expression varl sp (Call((str,rl))) il3) 
                             )
                             
    |e1 :: e2 , [] -> let il2 = generate_asm_expression varl sp e1 il 
                      in generate_asm_expression varl sp (Call(str,e2)) il2 
    |[],_ -> ( 
              (reg_list_string_ref := reg_list_string);
              (if is_mod then (il |%p ("callq "^str)) 
                else (let il2 = (il|%p "subq $8, %rsp") in let il3 = (il2 |%p ("callq "^str)) in (il3 |%p "addq $8, %rsp")))
            
             )
   )
  
  *)
  |StringLiteral(str)-> (il |% pa "leaq %a, %rax" (addr_lbl_of_string str))

  |Seq(exprlist) -> 
    (match exprlist with
    |e1 :: [] -> generate_asm_expression varl sp e1 il
    |e1 :: rl -> let il2 = generate_asm_expression varl sp e1 il in
                  generate_asm_expression varl sp (Seq(rl)) il2
    |[] -> il  
    )

  | Var(str) -> let adr = List.assoc str varl in il |% pa "movq %a, %rax" adr
  | Set(str, expr) -> let il2 = generate_asm_expression varl sp expr il in
    (il2 |% pa "movq %rax, %a" (List.assoc str varl))
  | UnaryOperator(uop,expr) -> 
   let il2 = generate_asm_expression varl sp expr il in  
    generate_uop_expression varl sp uop il2 
  | BinaryOperator(exp1,bop,exp2) -> 
   let il2 = generate_asm_expression varl sp exp2 il in
   let il3 = (il2 |% p "pushq %rax") in
   let il4 = generate_asm_expression varl sp exp1 il3 in 
   let il5 = 
   generate_bop_expression varl sp bop il4 in  
    (il5 |% p "addq $8, %rsp");
  | IntegerLiteral i ->  il |% pi "movq %i, %rax" i 

  with Match_failure(_) -> raise (Code_gen_failure_expression e)


(******************************)
and 
  generate_asm_statement ?retlbl = fun varl sp s il ->
  try match s with
  |WhileStmt(expr,state) -> 
    let label_debut = (fresh_lbl "debut_while") in 
    let label_fin = (fresh_lbl "fin_while") in 
    let il2 = il|%p (label_debut^":") in 
    let il3 = generate_asm_expression varl sp expr il2 in 
    let il4 = il3 |% p "testq %rax, %rax" in 
    let il5 = il4 |% p ("jz "^label_fin) in 
    let il6 = generate_asm_statement ?retlbl varl sp state il5 in 
    let il7 = il6 |% p ("jmp "^label_debut) in 
    il7 |% p (label_fin^":")
  |IfStmt(expr, stv, stf) -> 
    let label_fin = (fresh_lbl "fin_if") in 
    let label_else_if = (fresh_lbl "else_if") in 
    let il2 = generate_asm_expression varl sp expr il in 
    let il3 = il2 |%p "testq %rax, %rax" in 
    let il4 = il3 |%p ("jz "^ label_else_if) in 
    let il5 = generate_asm_statement ?retlbl varl sp stv il4 in 
    let il6 = il5 |%p ("jmp "^ label_fin) in 
    let il7 = il6 |%p (label_else_if^":") in 
    let il8 = generate_asm_statement ?retlbl varl sp stf il7 in 
    il8 |%p (label_fin^":")
   | CompoundStmt (nvarl,statl) -> ( 
   match nvarl,statl with
    | [],[] -> il
    | str::rl,[] -> il
    | [],s1 :: sl ->  let il2 = generate_asm_statement ?retlbl varl sp s1 il in generate_asm_statement ?retlbl varl sp (CompoundStmt([],sl)) il2 
    | str::rl ,s1 :: sl -> let sp2 = sp + 8 
                          in
                            let varl2 = ((str,(ARI(IL(-sp2),RBP))) :: varl) 
                          in
                            let il2 = (il |%pi "subq %i, %rsp" (sp2 -sp))  
                          in 
                            let il3 = generate_asm_statement ?retlbl varl2 sp2 s1 il2
                          in 
                            let il4 = generate_asm_statement ?retlbl varl2 sp2 (CompoundStmt(rl,sl)) il3
                          in 
                            (il4 |%pi "addq %i, %rsp" (sp2 -sp)) 

  )
  | Expr exp -> generate_asm_expression varl sp exp il
  | ReturnStmt Some(expr) -> 
  let il2 =  generate_asm_expression varl sp expr il in 
     generate_asm_statement ?retlbl varl sp (ReturnStmt(None)) il2
  

  | ReturnStmt None ->  
     (il
         |% pi "addq %i,%rsp" sp 
         |% p  "popq %rbp"
         |% p  "retq"
     )


  with Match_failure(_) -> raise (Code_gen_failure_statment s)


(******************************)



    
(*
  | FunctionDecl(name,[],state) ->
  let il2 = 
   (il 
       |% p (name^":")
       |% p "pushq %rbp"
       |% p "movq %rsp, %rbp"
   ) 
 in let il3 = generate_asm_statement varl 0 state il2
 in generate_asm_statement varl 0 (ReturnStmt (Some(IntegerLiteral 0))) il3 
 *)


 (*
  |FunctionDecl(name,strlist,state) ->  
    let rec generate_parameters sp varl strlist il reg_list = 
      let sp2 = sp + 8 in (
        match (strlist,reg_list) with 
        |str::rl,[] -> let varl2 = ((str,(ARI(IL(sp2),RBP))) :: varl)  in generate_parameters sp2 varl2 rl il reg_list 
        |str::rl,reg::r_reg -> let varl2 = ((str,R(reg)) :: varl) in generate_parameters sp2 varl2 rl il r_reg
        |[],_-> let il2 = 
        (il 
            |% p (name^":")
            |% p "pushq %rbp"
            |% p "movq %rsp, %rbp"
        ) 
      in let il3 = generate_asm_statement varl 0 state il2
      in generate_asm_statement varl 0 (ReturnStmt (Some(IntegerLiteral 0))) il3 
      )
      in (generate_parameters 16 varl strlist il reg_list) 
 *)
 let rec gen_arg = fun argl regl varl sp il ->
      match (argl,regl) with
        ([],_) -> (varl,sp,il)
       |  (_,[]) -> failwith "plus que 6 argument"
       | (a1::qarg, r1::qreg) -> 
           let sp2 = sp +8 in
           let nvarl = (a1, parse_arg "-%i(%rbp)" sp2) :: varl in
           let il2 = il |% p ("pushq "^r1) in
           gen_arg qarg qreg nvarl sp2 il2

 let generate_asm_top = fun varl il decl ->
  match decl with
  | FunctionDecl(name,al,s) ->
     let il2 = il
               |% p (name^":")
               |% p "pushq %rbp"
               |% p "movq %rsp, %rbp" in
     let (nvarl, sp2, il3) = gen_arg al reg_list_string varl 0 il2 in
     let il4 = generate_asm_statement nvarl sp2 s il3 in 
     generate_asm_statement nvarl sp2 (ReturnStmt (Some (IntegerLiteral 0))) il4
  | VarDecl(_) -> il
    (* les variables globals sont déjà geré dans le fichier compilo.ml.
       On ne fait donc rien ici. *)

(*  JAVA
let rec generate_expr varl exprlist il = 
	match exprlist with
	| expr::rl -> let il2 = generate_jsm_expression varl expr il in generate_expr varl rl il2
	| [] -> il
let rec generete_asm_statement varl e il = match e with 
|Call(fname, argl) -> let il2 = (il |%p (get_type_specifier fname argl))
					in 
					   let il3 = generate_expr varl argl il2 
					in 
					   let il4 = (il3 |%p "ireturn")
					in
						(il4 |%p ".methodend")
*)