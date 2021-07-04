/* Conditionnel *test =  
new Conditionnel
(
    newInferieurEgal(new Variable("x"),  newConstante(0.0))
    , newCos(newVariable("x")), newCos(newProduit(newConstante(2.0), newVariable("x"))));

*/

#ifndef CONDITIONNEL_H
#define CONDITIONNEL_H

#include "expression.h"
#include "variable.h"

class Conditionnel : public Expression
{
    public:
        Conditionnel(Expression * e1, Expression * e2, Expression * condition):Expression("cond")
        {
            _condition = condition;
            _e1 = e1;
            _e2 = e2;
        }

        std::string str()const
        {
            std::string chaine = "("+_condition->str()+")?";
            chaine += _e1->str()+":";
            chaine += _e2->str();
            return chaine;

        }   

        double eval()const
        {
            if(_condition->eval() == 0.0)
                return _e1->eval();
            return _e2->eval();
        }

    private:
        Expression * _condition;
        Expression * _e1;
        Expression * _e2;
};

#endif