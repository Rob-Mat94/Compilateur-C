#include "Cercle.hpp"

Cercle::Cercle(const Point & a, int r)
{
    this->rayon = r;
    this->centre = Point(a);
}

// deplacement-translation de valeur le point trans
void Cercle::deplacer(const Point & p)
{
    this->centre.setX(centre.getX() + p.getX());
    centre.setY(centre.getY() + p.getY());
}

void Cercle::dessiner ( ) const
{
    cout << *this << endl;;
}

