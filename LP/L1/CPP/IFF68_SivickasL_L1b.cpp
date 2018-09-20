/*
Lukas Šivickas IFF-6/8
L1b
Atsakymai i klausimus:
1) Visos lygiagrecios srities gijos pradedamos vykdyti tuo paciu metu (skaidrese taip parasyta)
2) Atsitiktine
3) Vienos dali
4) Atsitiktine
*/

#include <stdio.h>
#include <omp.h>
#include <string>
#include <iostream>
#include <iomanip>
#include <fstream>
#include <vector>

using namespace std;

// Nustatoma kiek duomenu irasu bus faile
const int n = 16;

// Sukuriamas struct giju rezultatams rasyti
struct DataResult{
  int gijosNr;
  string autoPavadinimas;
  int autoMetai;
  double autoVidKuroSanaudos;
};

/*
Duomenu nuskaitymo funkcija
@param loc - duomenu failo vieta
@param S - masyvas, kuriame saugomi automobilio pavadinimai
@param I - masyvas, kuriame saugomi automobilio pagaminio metai
@param D - masyvas, kuriame saugomos automobilio vid. kuro sanaudos
*/
void readf(string loc, string S[], int I[], double D[]);

/*
Duomenu isspausdinimo funkcija
@param loc - rezultatu failo vieta
@param S - masyvas, kuriame saugomi automobilio pavadinimai
@param I - masyvas, kuriame saugomi automobilio pagaminio metai
@param D - masyvas, kuriame saugomos automobilio vid. kuro sanaudos
*/
void printData(string loc, string S[], int I[], double D[]);

/*
Rezultato spausdinimo funkcija
@param P - DataResult masyvas
*/
void printResults(string loc, DataResult P[]);

int main(){

  const string dataLocation = "IFF68_SivickasL_L1b_dat.txt";
  const string outputLocation = "IFF68_SivickasL_L1b_rez.txt";

  string S[n]; // automobiliu pavadinimu masyvas
	int I[n]; // automobiliu metu masyvas
	double D[n]; // automobiliu vidutiniu kuro sanaudu masyvas
  DataResult P[n]; // rezultatu masyvas
	
  // Nuskaitom duomenis ir padedam juos i atitinkamus masyvus
	readf(dataLocation, S, I, D);
  // Isspausdinam nuskaitytus duomenis
  printData(outputLocation, S, I, D);

  // Skaitliukas rezultatu masyvui
  int count = 0;
  
  // nustatome kad giju skaicius butu n
  #pragma omp parallel num_threads(n)
  {
    // Gauname gijos numeri
    int i = omp_get_thread_num();
    // Sukuriam objekta rezultatam uzrasyt
    DataResult outp;
    // Pagal gijos numeri pasiemame duomenis ir juos priskiriam outp objektui
    outp.gijosNr = i;
    outp.autoPavadinimas = S[i];
    outp.autoMetai = I[i];
    outp.autoVidKuroSanaudos = D[i];
    // Irasom i rezultatu masyvo gala outp ir padidinam vienetu skaitliuka
    P[count++] = outp;    
  }

  // Isvedame rezultatu masyva i rezultatu faila
  printResults(outputLocation, P);
  return 0;
}


void readf(string loc, string S[], int I[], double D[]) 
{
	ifstream dataFile(loc);
	for (int i = 0; i < n; i++)
		dataFile >> S[i] >> I[i] >> D[i];
	dataFile.close();
}

void printData(string loc, string S[], int I[], double D[]) 
{
	ofstream outputFile(loc);
  outputFile << "+-----------------PRADINIAI--DUOMENYS-----------------+" << endl;
	outputFile << "#" << setw(14) << right << "Automobilis" << setw(11) << right << "Metai" << setw(20) << right << "Vid. kuro sanaudos" << endl;
	for (int i = 0; i < n; i++)
		outputFile << setw(4) << left << to_string(i + 1) + ")" << setw(17) << left << S[i] << setw(7) << I[i] << D[i] << endl;
	outputFile.close();
}

void printResults(string loc, DataResult P[]) {
  // ios:app - append (ne perraso, o papildo faila)
	ofstream outputFile(loc, ios::app);
	outputFile << "+----------------GALUTINIAI-REZULTATAI----------------+" << endl;
	outputFile << "#" << setw(10) << right << "Gijos #" << setw(13) << right << "Automobilis" << setw(10) << right << "Metai" << setw(20) << right << "Vid. kuro sanaudos" << endl;
	for (int i = 0; i < n; i++) {
		outputFile << setw(4) << left << to_string(i + 1) + ")" << setw(9) << left << P[i].gijosNr << setw(16) << left << P[i].autoPavadinimas << setw(7) << P[i].autoMetai << P[i].autoVidKuroSanaudos << endl;
	}
  outputFile << "+----------------------------------------------------+" << endl;
	outputFile.close();
}