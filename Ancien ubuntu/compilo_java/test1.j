.class public test1
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
.limit stack 2
.limit locals 1
	getstatic java/lang/System/out Ljava/io/PrintStream;
	invokestatic test1/main()I
	invokevirtual java/io/PrintStream/println(I)V
	return
.end method
.method public static main()I
	ldc 42
	ireturn
	bipush 0
	ireturn
.limit stack 1
.limit locals 0
.end method
