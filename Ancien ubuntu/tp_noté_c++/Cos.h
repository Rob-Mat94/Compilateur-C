#include "Unaire.h"
#include "math.h"

class Cos : public Unaire
{
public:
    Cos(Expression* op) : Unaire("cos", op) {}
    double eval() const
    {
        return cos(_op->eval());
    }
};


class Sin : public Unaire
{
public:
    Sin(Expression* op) : Unaire("sin", op) {}
    double eval() const
    {
        return sin(_op->eval());
    }
};

