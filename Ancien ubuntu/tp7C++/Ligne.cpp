#include "Ligne.hpp"

Ligne::Ligne(const Point &a, const Point &b)
{
    this->origine = Point(a);
    this->extremite = Point(b);
}
// deplacement-translation de valeur le point trans
void Ligne::deplacer(const Point & p)
{
    origine.setX(origine.getX() + p.getX());
    origine.setY(origine.getY() + p.getY());
    extremite.setX(extremite.getX() + p.getX());
    extremite.setY(extremite.getY() + p.getY());
}

// dessin d' une ligne de son origine a son extremite
void Ligne::dessiner() const
{
	cout << *this << endl;;
}


