long fact()
{
	long x;
	x = 5;
	long r;
	r = 1;
	while(x > 1)
	{
		r = x * r;
		x = x - 1;
	}
	return r;
}