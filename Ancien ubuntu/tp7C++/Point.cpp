#include "Point.hpp"

Point::Point(const Point &p)
{
	this->x = p.x;
	this->y = p.y;
}

Point::Point(int x, int y)
{
	this->x = x;
	this->y = y;
}

ostream & operator<< (ostream & os, const Point & p)
{	
	std::cout << "x : " << p.getX() << " / y : " << p.getY() << std::endl;	
	return os;
}

 