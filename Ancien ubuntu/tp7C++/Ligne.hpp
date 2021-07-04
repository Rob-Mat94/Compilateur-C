#ifndef LIGNEH
#define LIGNEH

#include <iostream>
#include "Figure.hpp"

class Ligne : public Figure{

public:
	Ligne(const Point & a, const Point & b);

    ~Ligne() {}

    // deplacement-translation de valeur le point trans
	virtual void deplacer(const Point & trans);
	virtual void dessiner() const;

	virtual string toString()const
	{
		 return "Origine : "+origine.toString() + " / Extremité : " + extremite.toString();
	}
private:
	Point origine;
	Point extremite;
};

#endif

