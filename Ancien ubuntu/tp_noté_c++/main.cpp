#include <iostream>
#define _USE_MATH_DEFINES // for C++
#include <cmath>

#include "binaire.h"
#include "variable.h"
#include "Cos.h"
#include "Constante.h"
#include <set>
#include "expression.h"
#include "Conditionnel.h"
#include "Somme.h"

using namespace std;

int main()
{   

    Expression::_gest.insert(new Constante(45));
    Expression::_gest.insert(new Cos(new Constante(4)));
    Expression::_gest.insert(new Sin(new Constante(10)));
    for(auto e : Expression::getGest())
    {
        std::cout << *e;
    }
    std::cout << "size : " << Expression::_gest.size() << std::endl;

    Conditionnel* test = new Conditionnel(new InferieurEgal(new Variable("x"),  new Constante(0.0)),
     new Cos(new Variable("x")), new Cos(new Produit(new Constante(2.0), new Variable("x"))));
     Variable *x = new Variable("x", M_PI/3.0);
     cout << *x << " = " << x->eval() << endl;


    x->set( -M_PI/3.0); 
    
    cout << *x << " = " << x->eval() << endl;  
     
    /*
    cout << *test << " = " << endl << test->eval() << endl;

    x->set( -M_PI/3.0); cout << *x << " = " << x->eval() << endl;  cout << *test << " = " << test->eval() << endl;
     
    Conditionnel* test2 = new Conditionnel(new Constante(1), new Constante(2), new Constante(3));
    std::cout << *test2 << test2->eval();
    */
      
}
