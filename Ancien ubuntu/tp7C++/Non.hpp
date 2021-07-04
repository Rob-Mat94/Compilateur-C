#ifndef NON_H
#define NON_H
#include "Condition.hpp"
class Non : public Condition
{
    public:
        Non(const Condition& assert):assert(assert){}
        virtual bool verif(const Figure* f)const
        {
            return !(assert.verif(f));
        }

    private:
        const Condition& assert;
};


#endif