#ifndef ESTPETITE_H
#define ESTPETITE_H
#include "Condition.hpp"

class EstPetite : public Condition
{
    public :
        EstPetite(int seuil_surface):seuil(seuil_surface){}
        virtual ~EstPetite(){}
        virtual bool verif(const Figure* f)const
        {
            return (f->getSurface() < seuil);
        }

    private :
        const int seuil;
};

#endif