#ifndef IMAGEH
#define IMAGEH

#include <iostream>
#include "Figure.hpp"
#include <list>

using namespace std;

class Image : public Figure {

public:
    enum {IMAGE_MAX = 50};

	Image (const Point & a = Point(0,0));

    ~Image();

    // ajout d' une figure a une image
	void ajouter(const Figure & f);

	virtual void deplacer(const Point & trans);
    virtual void dessiner() const;

	virtual string toString()const;
	virtual int getSurface()const
	{
		int surface = 0;
		for(Figure * f : _figures)
		{
			surface += f -> getSurface();
		}
		return surface;
	}
	private:
   	Point origine;
    //Conteneur de figures allou�es dynamiquement
   	list<Figure *> _figures;
	// nombre de figures dans une image
	int nombre;
};

#endif
