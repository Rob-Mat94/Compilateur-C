#include <stdio.h>
#include <iostream>
#include "Image.hpp"
#include "Ligne.hpp"
#include "Cercle.hpp"
#include "Filtrage.hpp"
void testPoint(void);
void testCercle(void);
void testLigne(void);
void testImage(void);

int main(void)
{
   /* testPoint();
    testLigne();
    testCercle();
	testImage();
	*/

	Filtrage::essai();
	return 0;
}

void testPoint(void)
{
	Point x;
	Point y(10, 20);
	cout << x << y << endl;

	Point z = x + y;
	cout << z << endl;

	z += y;
	cout << z << endl;
}

void testLigne(void)
{
	Ligne l1(Point(10, 20), Point(16, 30));
	l1.dessiner();
    cout << "Translation(5,8)" << endl;
	l1.deplacer(Point(5,8));
	l1.dessiner();
}

void testCercle(void)
{
	Cercle c1(Point(10, 20), 22);
	c1.dessiner();
    cout << "Translation(5,8)" << endl;
	c1.deplacer(Point(5,8));
	c1.dessiner();
}

void testImage(void)
{
	Ligne l1(Point(10, 20), Point(16, 30));
	Cercle c1 ( Point (10, 20), 22 );

    Image i1;
    cout << "image no 1 = ligne no 1 + cercle n� 1" << endl;
	i1.ajouter(l1);
	i1.ajouter(c1);
	i1.dessiner();
	cout << "Translation(40,50)" << endl;
	l1.deplacer(Point(40, 50));
	i1.dessiner();
	cout << "image no 1 += image no 1" << endl;

	i1.ajouter(i1);
	i1.dessiner();
    cout << "Translation(11,13)" << endl;
	c1.deplacer(Point(11, 13));
	i1.dessiner();
	cout << "Translation(15,40)" << endl;
	i1.deplacer(Point(15, 40));
	i1.dessiner();

	Image i2;
	cout << "image no 2 = image no 1 + ligne no 1" << endl;
	i2.ajouter(i1);
	i2.ajouter(l1);
	i2.dessiner();
	cout << "Translation(150,400)" << endl;
	i2.deplacer(Point(150, 400));
	i2.dessiner();

	Image i3;
	cout << "image no 2 = image no 1 + ligne no 1" << endl;
	i3.ajouter(i1);
	i3.ajouter(i2);
	i3.dessiner();
	
	
}
