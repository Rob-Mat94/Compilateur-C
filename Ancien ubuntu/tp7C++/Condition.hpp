#ifndef CONDITION_H
#define CONDITION_H

#include "Figure.hpp"

class Condition
{
    public:
        virtual bool verif(const Figure* f)const{return false;};
};

#endif