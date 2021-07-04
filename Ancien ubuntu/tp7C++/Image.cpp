#include <stdio.h>

#include "Image.hpp"
#include "Ligne.hpp"
#include "Cercle.hpp"


Image::Image(const Point & a)
{
    this->origine = Point(a);
}

// ajout d' une figure a une image
void Image::ajouter(const Figure & f)
{   

    Figure * fc = const_cast<Figure*>(&f);
    if(fc != this && nombre < IMAGE_MAX)
    {
        _figures.push_back(const_cast<Figure*>(&f));
        nombre++;
    }    
    
}

void Image::deplacer(const Point & p)
{

}

void Image::dessiner () const
{
    cout << *this << endl;
}

Image::~Image()
{
    /*
    for(Figure *f : _figures)
    {
        if(f != nullptr)
            delete f;
    }
    */
}

std::string Image::toString()const
{  
    std::string rtur = ""; 
    rtur += "Point : " + origine.toString();
    for(Figure* f : _figures)
    {
        rtur += f->toString();
    }

    rtur+= std::to_string(nombre);
    return rtur;
}
