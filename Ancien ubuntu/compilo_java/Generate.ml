open CLessType
open Tools

let rec generate_jvm_expression varl e il =
  try match e with
    (* *)
    | IntegerLiteral i -> 1, (il |% pi "ldc %i" i)
    | StringLiteral s -> 1, (il |% p ("ldc "^s))
    | BinaryOperator (expr1,bop,expr2) -> 

  with Match_failure(_) -> raise (Code_gen_failure_expression e)

let rec generate_jvm_statement varl ln s il =
  try match s with
    (* *)
    | Expr(e) ->
      let sp,il2 = generate_jvm_expression varl e il in
      ln,sp,il2

    | CompoundStmt ([],[]) -> ln,0,il
    | CompoundStmt ([],ts::qs) ->
      let v1,sp1,il2 = generate_jvm_statement varl ln ts il in
      let v2,sp2,il3 = generate_jvm_statement varl ln (CompoundStmt ([],qs)) il2 in
      (max v1 v2),(max sp1 sp2),il3

    | ReturnStmt (Some e) ->
      let sp,il2 = generate_jvm_expression varl e il in
      ln,sp,(il2 |% p "ireturn")
    | ReturnStmt None ->
      ln,1,(il
            |% p "bipush 0"
            |% p "ireturn")


  with Match_failure(_) -> raise (Code_gen_failure_statment s)

let generate_jvm_top varl il = function
  | FunctionDecl(name,[],stat) ->
    let il2 =
      il |% ps ".method public static %s()I" name in
    let (var_size,stack_size,il3) = generate_jvm_statement varl 0 stat il2 in
    let _,_,il4 = generate_jvm_statement varl 0 (ReturnStmt None) il3 in
    il4
    |% pi ".limit stack %i" (max 1 stack_size)
    |% pi ".limit locals %i" var_size
    |% p ".end method"

  | VarDecl(_) -> il
    (* les variables globals sont déjà geré dans le fichier compilo.ml.
       On ne fait donc rien ici. *)
