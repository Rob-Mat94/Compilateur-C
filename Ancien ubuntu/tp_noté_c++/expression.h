
#ifndef EXPRESSION_H_INCLUDED
#define EXPRESSION_H_INCLUDED

#include <iostream>
#include <sstream>
#include <string>
#include <math.h>
#include <map>
#include <set>


class Expression
{
protected:
    std::string _nom;
private:
    // static std::set<Expression*> _gest ;
public:
   
    static std::set<Expression*> _gest ;
    
    static std::set<Expression*>& getGest();

    static void toutLiberer();

    Expression(const std::string& nom):_nom(nom){};

    virtual ~Expression()
    {
        toutLiberer();
    }

    virtual double eval() const = 0;

    virtual std::string str()const = 0;

    friend std::ostream& operator<<(std::ostream& os, const Expression& exp)
    {
        os << exp.str() << std::endl;
        return os;
    }
};

#endif // EXPRESSION_H_INCLUDED
