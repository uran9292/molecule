#include "czastka.h"


std::fstream data;

std::vector <int> collisions;


int width, height, number_of_molecules, timer, collisions_counter = 0;

std::fstream & operator<<(std::fstream & tmp, molecule &c){
	tmp << c.return_x() << " " << c.return_y() << " " << c.vx << " " << c.vy << " " << c.r << " " << c.m << " " << c.type << " " << c.label << " " << std::endl; 
}


int main(){

	data.open("data.txt", std::ios::in);
	data >> width >> height >> timer >> number_of_molecules;
	parameters(width, height);
	data.close();
	
	data.open("source.txt", std::ios::out);
	std::ios_base::sync_with_stdio(0);
	srand(time(NULL));
	
	std::vector <molecule*> pointer(number_of_molecules);

	for(int i = 0; i < number_of_molecules; i++){
		
		int var = rand()%5;


		if(var == 0)
			pointer[i] = new normal;
		else if(var == 1)
			pointer[i] = new dual;
		else if(var == 2)
			pointer[i] = new fissile;
		else if(var == 3)
			pointer[i] = new dual_fissile;
		else if(var == 4)
			pointer[i] = new photonic;
	}


	for(int i = 0; i < timer; i++){
				
		data << i << " " << number_of_molecules << std::endl; 		
		

		//przesuwanie cząsteczek
		for(int j = 0; j < number_of_molecules; j++){
						
			pointer[j]->move();
			data << *pointer[j];			
		}
		


		//sprawdzanie kolizji
		pointer[0]->czy_collision = false;
		
		for(int k = 0; k < number_of_molecules; k++){
		
			for(int m = k + 1; m < number_of_molecules; m++){
				pointer[k]->is_collision(*pointer[m]);
				if(pointer[k]->czy_collision){
					collisions.push_back(k);
					collisions.push_back(m);
					collisions_counter += 2;
					pointer[k]->czy_collision = false;
				}
			}

		}

		//sprawdzanie oddziaływań pomiędzy cząsteczkami
		for(int k = 0; k < number_of_molecules; k++){
		
			for(int m = k + 1; m < number_of_molecules; m++){
				pointer[k]->gravity(*pointer[m]);
			}
		}
		
		//usuwanie małych cząsteczek
		for(int k = 0; k < number_of_molecules; k++){
			if(pointer[k]->m < 0.01 && pointer[k]->m != -1){
				pointer.erase(pointer.begin() + k);
				k--;
				number_of_molecules--;
			}
		}
		

		//dodawanie nowych cząsteczek
		while(is_new_molecule()){
			number_of_molecules++;
			pointer.push_back(return_molecule());
		}

		//dodawanie kolizji
		if(!collisions.empty()){
			
			data << collisions_counter/2 << " ";
			
			while(!collisions.empty()){
				data << collisions[collisions_counter - 1] << " " << collisions[collisions_counter - 2] << " ";
				collisions.pop_back();
				collisions.pop_back();
				collisions_counter -= 2;
			}
			data << std::endl;
			pointer[0]->czy_collision = false;
		}
		else
			data << "Brak" << std::endl;
	}	

	data.close();

	return 0;
}
