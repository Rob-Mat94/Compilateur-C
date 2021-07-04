int f (int x, int y)
{	
	int i;
	i = 3;
	return i*x*(y-2);
}

int main()
{
	return f(1,5);
}




pushq %rbp 
movq %rsp, %rbp
pushq %rdi
pushq %rsi
subq $8, %rsp
movq $3, %rax
movq %rax, -24(%rbp)
movq $2, %rax
pushq %rax
movq -16(%rbp), %rax
subq (%rsp),%rax
addq $8, %rsp
pushq %rax
movq -8(%rbp), %rax
imulq (%rsp), %rax
addq $8, %rsp
imulq (%rsp), %rax
addq $24, %rsp
popq %rbp
retq 



(x+2) * (-y)

pushq %rbp
movq %rsp,%rbp

movq x, %rax
pushq %rax
movq $2, %rax
addq (%rsp), %rax
addq $8, %rsp

pushq %rax                 x+2
movq y, %rax
negq %rax
imulq (%rsp), %rax
addq $8, %rsp

popq %rsp
retq 
















