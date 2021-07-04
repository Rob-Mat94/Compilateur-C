#include "Filtrage.hpp"
#include <ctime>
#include "Cercle.hpp"
#include "Ligne.hpp"
#include "Image.hpp"
#include "EstUn.h"
#include "Non.hpp"
#include "EstPetite.hpp"
#include "Et.hpp"
const Figure* Filtrage::getUneForme(int x, int y)
{   
    srand((unsigned int)time(0));

    switch (rand()%3 + 1)
    {
    case 1:
        return new Cercle(Point(x,y), rand()%20 + 1);
        break;
    case 2:
        return new Ligne(Point(x,y),Point(rand()%50,rand()%50));
        break;
    case 3:
    default:
        return new Image(Point(x,y));
        break;
    }
}

const std::list<const Figure*> Filtrage::creerFormes(int n)
{   
    std::list<const Figure*> list;
    for(int i(0); i < n; i++)
    {    
        srand((unsigned int)time(0));
        list.push_back(getUneForme(i,i));
    }
    return list;
}

int Filtrage::compterSi(std::list<const Figure*> collection, Condition* c)
{   
    int cpt = 0;
    for(auto f : collection)
    {
        if(c->verif(f))
            cpt++;
    }

    return cpt;
}

bool Filtrage::supprimerSi(std::list<const Figure*> collection, Condition* c)
{
    bool had_delete = false;
    for(auto f : collection)
    {
        if(c->verif(f))
        {
            collection.remove(f);
            had_delete = true;
        }
    }
    return had_delete;
}


void Filtrage::essai()
{
    std::list<const Figure*> list = creerFormes(10);
    for(auto f : list)
    {
        std::cout << *f << " / ";
    }

    Figure * temp = new Cercle(Point(0,0),0);
    Condition c1 = Non(EstUn(temp));
    Condition c2 = Et(EstPetite(1000),c1);

    std::cout << compterSi(list,&c1);
    std::cout << compterSi(list,&c2);

    supprimerSi(list,&c2);

    for(auto f : list)
    {
        std::cout << *f << " / ";
    }

     delete temp;



}