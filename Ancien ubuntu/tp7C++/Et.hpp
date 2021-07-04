#ifndef ET_H
#define ET_H

#include "Condition.hpp"

class Et : public Condition
{
    public:
        Et(const Condition& assert1, const Condition& assert2):assert2(assert2),assert1(assert1){}
        virtual bool verif(const Figure* f)const
        {
            return (assert1.verif(f) && assert2.verif(f));
        }
    private:
        const Condition& assert1;
        const Condition& assert2;
};

#endif 