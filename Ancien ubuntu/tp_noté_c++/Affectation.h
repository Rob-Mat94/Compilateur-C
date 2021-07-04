#include "binaire.h"
#include "variable.h"
class Affectation : public Binaire
{
public:
    Affectation(Variable* var, Expression* exp) : Binaire("<-", var, exp) {}
    double eval()const
    {
        Variable* v = dynamic_cast<Variable*>(_opleft);
        return v->set(_opright->eval()); 
    }

    std::string str()const
    {
        std::string chaine;
        chaine += _opleft->str();
        chaine += " -> ";
        chaine += _opright->str();

        return chaine;
    }
};
