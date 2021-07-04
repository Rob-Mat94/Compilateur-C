#include "binaire.h"

class Somme : public Binaire {
public:
    Somme(Expression* left, Expression* right):Binaire("BinaryAdd",left,right){}
    double eval() const
    {
      return _opleft->eval() + _opright->eval();
    }

     std::string str()const
    {
        std::string str ="";
        str+= _opleft->str() + " + ";
        str+= _opright->str();

         return str;
    }
};

class Produit : public Binaire
{
public:
    Produit(Expression * left, Expression * right):Binaire("BinaryMult",left,right){}
    double eval() const
    {
        return _opleft->eval() * _opright->eval();
    }

    std::string str()const
    {
        std::string str ="";
        str+= _opleft->str() + " * ";
        str+= _opright->str();

        return str;
    }
};

class Superieur : public Binaire {
public:
Superieur(Expression* left, Expression* right):Binaire("Supp",_opleft,_opright){}
double eval() const
{   
    if(_opleft->eval() > _opright->eval())
        return 0.0;
    return -1.0; 
}

 std::string str()const
    {
        std::string str ="";
        str+= _opleft->str() + " > ";
        str+= _opright->str();

        return str;
    }
};

class InferieurEgal : public Binaire
{
 public:
    InferieurEgal(Expression* left, Expression *right):Binaire("InfEqual",_opleft,_opright){}
    double eval()const
    {
        if(_opleft->eval() <= _opright->eval())
            return 0.0;
        return -1.0;
    }

    std::string str()const
    {
        std::string str ="";
        str+= _opleft->str() + " <= ";
        str+= _opright->str();

         return str;
    }
};

