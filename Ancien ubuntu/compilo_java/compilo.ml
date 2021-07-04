open CLessType
open Tools

let input = ref stdin
let output = ref stdout
let inputName = ref "stdin"
let outputName = ref "a.out"
let doLinking = ref true
let pp = ref false
let pt = ref false
let ptdot = ref false
let exec = ref None

let _ = Arg.parse [
  "-o", Arg.Set_string outputName, "Output file";
  "-S", Arg.Clear doLinking, "Output assembly";
  "-p", Arg.Set pp, "print preprocessing";
  "-d", Arg.Set ptdot, "print AST as dot";
  "-t", Arg.Set pt, "print AST";
  "--", Arg.Rest (fun s -> exec := Some s), "Execute the outputted program";
]
                  (function s ->
                            inputName := s;
                            input := open_in s)
    "usage"

module StringSet = Set.Make(String)

let className = (
  let n2 = try let n = String.rindex !inputName '.' in
      String.sub !inputName 0 n
    with Not_found -> !inputName in
  try let n = String.rindex n2 '/' in
    String.sub n2 (n+1) (String.length n2 -n -1)
  with Not_found -> n2
)

let _ =
  Tools.compileClassName := className;
  if !doLinking then output := open_out (className^".j")

let _ =
  let defList = try
    let lexbuf = Lexing.from_channel (!input) in
    CLessGram.translation_unit CLessLex.ctoken lexbuf
    with
      Parsing.Parse_error ->
      Printf.fprintf stderr "%s:%i:%i: Parse Error\n" !inputName !cline !ccol;
      exit 1 in

  if (!pp) then List.iter (fun x ->
                 Printf.fprintf stdout "%a\n" PrintC.print_declaration x) defList;
  if (!pt) then PrintAST.print_dec_list Format.std_formatter defList;
  if (!ptdot) then begin
      output_string stdout "digraph AST {"; (*\nrankdir=\"LR\";\n";*)
      ignore (PrintAST.print_dot_dec_list stdout defList);
      output_string stdout "}"
    end;

  let initprog = []
              (* |% ".source "^(!inputName)*)
            |% (".class public "^className)
            |% ".super java/lang/Object"

           (* |% ".method public <init>()V"
            |% "\taload_0"
            |% "\tinvokespecial java/lang/Object/<init>()V"
            |% "\treturn"
            |% ".end method"
           *)

  in
  let prog,globVar =
    defList
    |> List.filter (function FunctionDecl(_) -> false | _ -> true )
    |> List.map (function FunctionDecl(_)-> assert false | VarDecl(name,vopt) ->
        (name,vopt))
    |> List.fold_left (fun (il,gv) (name,vopt) ->
        let value = match vopt with None -> "" | Some x -> " = "^(string_of_int x) in
        il |% ".field static "^name^" I"^value , (name,Static)::gv) (initprog,[])
  in

  let prog2 = prog
              |% ".method public static main([Ljava/lang/String;)V"
              |% ".limit stack 2"
              |% ".limit locals 1"
              |% "getstatic java/lang/System/out Ljava/io/PrintStream;"
              |% "invokestatic "^className^"/main()I"
              |% "invokevirtual java/io/PrintStream/println(I)V"
              |% "return"
              |% ".end method" in


  let asm_prog = defList
    |> (fun x ->try List.fold_left (Generate.generate_jvm_top globVar) prog2 x
                with
                | Code_gen_failure_expression e as exn -> PrintAST.perror_exp e; raise exn;
                | Code_gen_failure_statment s as exn -> PrintAST.perror_stat s; raise exn;
                | e -> Printf.fprintf stderr "Exception in Generate.ml\n"; raise e)
    |> List.rev

  in

  List.iter (fun s -> if s<> "" then
                if s.[0] = '.' then output_string !output (s^"\n")
                else output_string !output ("\t"^s^"\n")
            ) asm_prog;
  close_out !output;

  if !doLinking then
    let o = Unix.open_process_out ("java -jar jasmin.jar "^ className ^".j") in
    ignore (Unix.close_process_out o)

let _ = match !exec with
    Some e -> ignore (Sys.command ("java "^className^" "^e))
  | _ -> ()
