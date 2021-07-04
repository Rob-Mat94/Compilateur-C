#include "variable.h"

std::map<std::string,double>  Variable ::_memoire ;

void Variable::effacerMemoire()
{
   _memoire.clear();
}

double Variable::eval() const
{   
    auto result = _memoire.find(_nom);
    if(result != _memoire.end())
        return _memoire[_nom];
    
    return 0;
}

double Variable::set(double val)
{
    _memoire[_nom] = val;
    return val;
}


std::string Variable::str()const
{
        return _nom;
}