#ifndef ESTUN_H
#define ESTUN_H
#include "Condition.hpp"

class EstUn : public Condition
{
    public :
        EstUn(const Figure * f_temoin): f_temoin(f_temoin){}
        virtual bool verif(const Figure* f)const
        {
            return (typeid(f).name() == typeid(f_temoin).name());
        }

    private :
    const Figure * f_temoin;
};




#endif