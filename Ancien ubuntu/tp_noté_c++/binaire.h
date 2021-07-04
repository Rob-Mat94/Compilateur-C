#ifndef BINAIRE_H_INCLUDED
#define BINAIRE_H_INCLUDED

#include "expression.h"

class Binaire : public Expression
{
protected:
    Expression  *_opleft, *_opright;

public:
    Binaire(const std::string& nom, Expression* left, Expression* right) : Expression(nom),
        _opleft(left), _opright(right) {}

    double eval() const = 0;

    std::string str() const = 0;
};

#endif // BINAIRE_H_INCLUDED

