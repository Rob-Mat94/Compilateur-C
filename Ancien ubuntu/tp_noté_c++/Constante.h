#include "expression.h"

/* retourne une valeur reelle (val) sous forme de chaine de caracteres */
std::string string_from_double(double val) { std::ostringstream os; os << val; return os.str(); }

class Constante : public Expression
{
    double _value;
public:
    Constante(double val) : Expression(string_from_double(val)), _value(val){}
    ~Constante(){}
    double eval()const
    {
        return _value;
    }

    std::string str()const
    {
        return string_from_double(_value);
    }


};


