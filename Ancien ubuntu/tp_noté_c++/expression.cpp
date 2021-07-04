#include "expression.h"

std::set<Expression*> Expression::_gest = {};

std::set<Expression*>& Expression :: getGest()
{
    return Expression::_gest;
}
void Expression::toutLiberer()
{
    for(auto element : Expression::_gest)
    {
        if(element != nullptr)
        {
                delete element;
        }
    }    
}