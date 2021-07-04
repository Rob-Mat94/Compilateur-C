#ifndef FILTRAGE_H
#define FILTRAGE_H

#include "Figure.hpp"
#include <random>
#include <list>
#include "Condition.hpp"

class Filtrage
{  
      public:
      static void essai();
       
    private:
      static const Figure* getUneForme(int x, int y);
      static const std::list<const Figure*> creerFormes(int n);
      static int compterSi(std::list<const Figure*> collection, Condition * c);
      static bool supprimerSi(std::list<const Figure*> collection, Condition * c);



};

#endif