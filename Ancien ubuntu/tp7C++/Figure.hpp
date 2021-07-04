#ifndef FIGUREH
#define FIGUREH

#include "Point.hpp"
#include <string>
using namespace std;

// classe abstraite de base
class Figure {

friend ostream& operator<<(ostream &flux, const Figure &f)
{	
	flux << f.toString();
	return flux;
}

public:
    virtual ~Figure() { };

	virtual void deplacer ( const Point & trans ) = 0;
	virtual void dessiner ( ) const = 0;

	virtual string toString()const = 0;
	virtual int getSurface()const{return 0;}

	bool operator== (const Figure & f) const
	{
	    return (this == &f);
    }
};

#endif
