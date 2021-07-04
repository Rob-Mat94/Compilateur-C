#ifndef POINTH
#define POINTH

#include <iostream>

using namespace std;
 
class Point {

public:
	Point(int a = 0, int b = 0);
    Point(const Point& p);
    virtual ~Point(){};
    
	int getX ( ) const { return x; }
	void setX (int a) { this->x = a; }
	int getY () const { return y; }
	void setY (int b) { this->y = b; }

    string toString()const
    {
        return "x : "+ to_string(getX())+" / y : "+ to_string(getY());
    }

	Point operator+ ( const Point & point ) const
    {
	    return Point(x + point.x, y + point.y);
    }

	Point & operator+= (const Point & point)
    {
        x += point.x;
        y += point.y;
        return *this;
    }

	Point & operator= (const Point & point)
    {
        x = point.x;
        y = point.y;
        return *this;
    }

	friend ostream & operator<< (ostream & os, const Point & point);

private:
	int x, y;
};

#endif
