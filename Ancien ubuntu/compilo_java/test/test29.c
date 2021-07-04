long perm(long n,long k){
  if (n==k) {
    return 1;
  } else if (k>n) { 
		return 0; 
	} else return (n * perm(n-1,k));
}

int main(){
	return perm(4,2);
}
