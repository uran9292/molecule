#ifndef CZASTKA_H
#define CZASTKA_H

#include <iostream>
#include <string>
#include <fstream>
#include <cmath>
#include <ctime>
#include <cstdlib>
#include <vector>
#include <sstream>


class molecule{
	
protected:
	float x; //położenie na osi x
	float y; //położenie na osi y
public:
	
	float vx; //zmiana x
	float vy; //zmiana y
	float r; //promień
	float m; // masa
	float return_x();
	float return_y();
	float change_x(float tmp);
	float change_y(float tmp); 
	static bool czy_collision;
	static int A;
	std::string label; //oznaczenie
	std::string type; //typ
	molecule();
	void load();
	void is_collision(molecule &c);
	virtual void collision(molecule &c){};
	virtual void collision(){};
	void move();
	void gravity(molecule &g);
	virtual ~molecule();
	

};


class normal: public molecule{

public:
	normal();	
	~normal();
	void collision(molecule &c);	
	

};

class dual: public molecule{

public:
	dual();
	~dual();
	void collision(molecule &c);

};

class fissile: public molecule{

public:
	fissile();
	~fissile();
	void collision(molecule &c);

};

class dual_fissile: public molecule{

public:
	dual_fissile();
	~dual_fissile();
	void collision(molecule &c);

};
class photonic: public molecule{

public:
	photonic();
	~photonic();
	void collision();
};



void parameters(int tmp_width, int tmp_height);
bool is_new_molecule();
molecule *return_molecule();





#endif
