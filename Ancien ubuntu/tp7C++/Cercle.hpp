#ifndef CERCLEH
#define CERCLEH

#include <iostream>
#include "Figure.hpp"

class Cercle : public Figure{

public:
	Cercle(const Point & a, int r);

    ~Cercle() {}

    // deplacement-translation de valeur le point trans
	virtual void deplacer(const Point & trans);
	virtual void dessiner() const;
    virtual string type_of() const { return "Cercle"; }

	virtual string toString()const
	{
		 return "Centre : " + centre.toString()  + " / rayon : " + std::to_string(rayon) +  "\n";
	}

	virtual int getSurface()const
	{
		return 3.14 * rayon * rayon;
	}


private:
	Point centre;
	int rayon;
};

#endif
