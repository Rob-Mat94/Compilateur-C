#ifndef VARIABLE_H_INCLUDED
#define VARIABLE_H_INCLUDED

#include "binaire.h"

class Variable : public Expression
{
public:
    Variable(const std::string& nom):Expression(nom){}
    
    Variable(const std::string& nom, double init) : Expression(nom)
    {   
        Variable::_memoire[_nom] = init;
    }

    double eval() const;

    double set(double val);

    std::string str()const;


private:
    /* ensemble des variable sous la forme d'un tableau
    associatif : la cle est le nom de la variable stockee */
    static std::map<std::string,double>  _memoire;
public:
    static void effacerMemoire();
};

#endif // VARIABLE_H_INCLUDED