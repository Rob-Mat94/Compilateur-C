#include "expression.h"

class Unaire : public Expression
{
protected:
    Expression *_op;
public:
    Unaire(const std::string& nom, Expression* op) : Expression(nom), _op(op) {}
    
    double eval()const = 0;

    std::string str() const
    {   
        std::string str = _nom + "(";
        return str += (_op->str()+")");
    }

};



