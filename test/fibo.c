
long fibo(long n){
  if (n<=1){
    return 1;
  } else return fibo( n-1) + fibo (n-2);
}


int main(){
  return fibo (5);
}
