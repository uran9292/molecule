#include "czastka.h"

int X, Y;
std::vector <molecule*> new_molecules;
int counter = 0;
bool molecule::czy_collision;
int molecule::A;
 
void parameters(int tmp_width, int tmp_height){
	X = tmp_width;
	Y = tmp_height;
}


molecule::molecule(){

	this->load();
}

molecule::~molecule(){}


void molecule::gravity(molecule &g){
	
	float distance = sqrt(pow(g.x - x, 2) + pow(g.y - y, 2));
	
	if(distance < 2){
		
		float gravitational_shift = (2 - distance) * m * g.m * distance;


		//sprawdzam, które współrzędne sa większe, aby cząsteczki się przyciągały, a nie odpychały
		if(x < g.x){
			vx += gravitational_shift;
			g.vx -= gravitational_shift;
		}
		else{
			vx -= gravitational_shift;
			g.vx += gravitational_shift;
		}
		
		if(y < g.y){
			vy += gravitational_shift;
			g.vy -= gravitational_shift;
		}
		else{
			vy -= gravitational_shift;
			g.vy += gravitational_shift;
		}
	
	}	
}

void molecule::move(){

	x += vx;
	if(x < 2 || x > X - 2){ //sprawdzam czy wychodzi za oś X
		vx = -vx;
		if(x < 2)
			x = 2;
		if(x > X - 2)
			x = X - 2;
	}
	
	y += vy;
	if(y < 2 || y > Y - 2){ //sprawdzam czy wychodzi za oś y
		vy = -vy;
		if(y < 2)
			y = 2;
		if(y > Y - 2)
			y = Y - 2;
	}
}

float molecule::return_x(){
	
	float parametr_x = this->x;
	return parametr_x;	
}

float molecule::return_y(){
	
	float parametr_y = this->y;
	return parametr_y;	
}

float molecule::change_x(float tmp_x){
	
	this->x = tmp_x;
}

float molecule::change_y(float tmp_y){
	
	this->y = tmp_y;
}


void molecule::load(){
	
	x = (std::rand()%X-2) + 1;
	y = (std::rand()%Y-2) + 1;
	r = fabs((float)std::rand()/(RAND_MAX - 1) - 0.6) + 0.1;  
	vx = fabs((float)std::rand()/(RAND_MAX - 1) - 0.5) + 1;
	vy = fabs((float)std::rand()/(RAND_MAX - 1) - 0.5) + 1;
    	int tmp = rand()%2;
    	if(tmp) 
		vx=-vx;
    	tmp = rand()%2;
    	if(tmp) 
		vy=-vy;

	do{
        	m = (float)std::rand()/(RAND_MAX - 1); 
	}while(m < 0.1);
	
	std::stringstream ss;
	ss << A;
	A = A + 1;
	label = "p" + ss.str();

}

void molecule::is_collision(molecule &c){

	float distance = sqrt(pow(this->x - c.x, 2) + pow(this->y - c.y, 2));

	if(distance > (this->r + c.r)) 
		return;
	czy_collision = true;

	if(this->m == -1){ //sprawdzam czy to foton
		this->collision();
		return;
	}
	

	if(c.m == -1){
		c.collision();
		return;
	}

	do{
	
	float molecules_mass = this->m + c.m; //rekombinacja mas
	
	if(this->m || c.m <= 0)
		return;
	
	this->m = (float)std::rand()/(float)(RAND_MAX/molecules_mass);
	c.m = molecules_mass - this->m;
	
	}while(this->m > 1 || c.m > 1);
	

	// zmiana toru ruchu cząsteczek		
	this->vx = -vx;
	this->vy = -vy;
	c.vx = -vx;
	c.vy = -vy;
		
	if(this->m >= 0.01)
		this->collision(c); //
	if(c.m >= 0.01)
		c.collision(*this);
	
}



normal::normal():molecule(){
	this->type = "normal";
}

void normal::collision(molecule &c){}

normal::~normal(){}




dual::dual():molecule(){
	this->type = "dual";
}

void dual::collision(molecule &c){

	do{
		float radius = this->r + c.r;
		this->r = (float)std::rand()/(float)(RAND_MAX/radius);
		c.r = radius - this->r;
	}while(this->r > 1 || c.r > 1); //rekombinacja promieni
}

dual::~dual(){}



fissile::fissile():molecule(){
	this->type = "fissile";
}

void fissile::collision(molecule &c){
	if(this->m <= 0) 
		return;

	float new_m = this->m/2;
	this->m = 0;

	molecule *tmp = new fissile;
	tmp->m = new_m;
	tmp->vx = this->vy;
	tmp->vy = -this->vx;
	tmp->change_x(this->return_x());
	tmp->change_y(this->return_y());
	new_molecules.push_back(tmp);

	molecule *tmp2 = new fissile;
	tmp2->m = new_m;
	tmp2->vx = -this->vy;
	tmp2->vy = this->vx;
	tmp2->change_x(this->return_x());
	tmp2->change_y(this->return_y());
	new_molecules.push_back(tmp2);

	counter += 2;
}

fissile::~fissile(){}




dual_fissile::dual_fissile():molecule(){
	this->type = "dual_fissile";
}

void dual_fissile::collision(molecule &c){


	do{
		float radius = this->r + c.r;
		this->r = (float)std::rand()/(float)(RAND_MAX/radius);
		c.r = radius - this->r;
	}while(this->r > 1 || c.r > 1); //rekombinacja promieni
		


	if(this->m <= 0) 
		return;
	
	float new_m = this->m/2;
	this->m = 0;
	
	molecule *tmp = new dual_fissile;
	tmp->m = new_m;
	tmp->vx = this->vy;
	tmp->vy = -this->vx;
	tmp->change_x(this->return_x());
	tmp->change_y(this->return_y());
	new_molecules.push_back(tmp);

	molecule *tmp2 = new dual_fissile;
	tmp2->m = new_m;
	tmp2->vx = -this->vy;
	tmp2->vy = this->vx;
	tmp2->change_x(this->return_x());
	tmp2->change_y(this->return_y());
	new_molecules.push_back(tmp2);
	
	counter += 2;

}

dual_fissile::~dual_fissile(){}




photonic::photonic():molecule(){
	this->type = "photonic";
	this->m = -1;
}

void photonic::collision(){
	vx = -vx;
	vy = -vy;
}

photonic::~photonic(){}

bool is_new_molecule(){
	if(counter > 0)
		return 1;
	else return 0;
}


molecule *return_molecule(){
	counter--;
	molecule *tmp = new_molecules[counter];
	new_molecules.pop_back();
	return tmp;

}
