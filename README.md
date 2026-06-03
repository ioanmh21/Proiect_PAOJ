# Vehicle Rental System

Proiectul reprezinta un sistem de gestiune pentru inchirierea de vehicule, structurat pe mai multe categorii de permise auto (A2, B, C, CE, D). Aplicatia a fost dezvoltata in Java si respecta cerintele impuse pentru materia Programare Avansata pe Obiecte (PAO), incluzand atat logica in memorie, cat si persistenta cu baze de date.

## Tehnologii si Concepte Utilizate

* **Limbaj:** Java 17+
* **Paradigme:** Programare Orientata pe Obiecte (OOP - incapsulare, mostenire, polimorfism)
* **Design Patterns:** Singleton (pentru Repozitorii, Conexiunea la DB si Serviciul de Audit)
* **Baza de date:** PostgreSQL (JDBC)
* **Altele:** Maven (pentru gestionarea dependentelor), Operatii cu fisiere (CSV)

## Structura Proiectului (Tipuri de obiecte)

Sistemul modeleaza domeniul de inchirieri auto folosind 9 clase principale de obiecte:

| # | Tip | Descriere |
|---|-----|-----------|
| 1 | `Vehicle` | Clasa abstracta de baza pentru toate vehiculele |
| 2 | `Motorcycle` | Extinde Vehicle (Necesita categoria A1/A2) |
| 3 | `Car` | Extinde Vehicle (Necesita categoria B) |
| 4 | `Truck` | Extinde Vehicle (Necesita categoria C) |
| 5 | `TrailerTruck` | Extinde Vehicle (Necesita categoria CE) |
| 6 | `Bus` | Extinde Vehicle (Necesita categoria D) |
| 7 | `Client` | Modeleaza clientul si lista de categorii de permis detinute |
| 8 | `Rental` | Modeleaza actiunea de inchiriere (asocierea Client - Vehicul) |
| 9 | `Location` | Reprezinta sucursalele/locatiile firmei |

## Actiuni Disponibile (Servicii)

Sistemul ofera peste 10 actiuni de business, impartite in trei servicii principale (`VehicleService`, `ClientService`, `BookingService`):

| # | Actiune |
|---|---------|
| 1 | `Adauga un vehicul nou in flota` |
| 2 | `Elimina un vehicul din flota (ex. in caz de dauna totala)` |
| 3 | `Afiseaza toate vehiculele disponibile pentru inchiriere` |
| 4 | `Cauta vehicule disponibile dupa o categorie de permis specifica` |
| 5 | `Inregistreaza un client nou` |
| 6 | `Inchirieaza un vehicul (valideaza automat daca clientul are categoria necesara)` |
| 7 | `Returneaza un vehicul inchiriat` |
| 8 | `Afiseaza toate inchirierile active curente` |
| 9 | `Afiseaza istoricul completar al inchirierilor pentru un anumit client` |
| 10 | `Calculeaza venitul total generat din toate inchirierile finalizate` |

## Persistenta (Baza de Date si Audit)

* **JDBC & PostgreSQL:** Toate intrarile adaugate prin servicii sunt persistate automat in baza de date PostgreSQL. Baza de date contine 4 tabele principale: `clients`, `vehicles`, `rentals`, `locations`. Crearea tabelelor se face automat la initierea conexiunii.
* **Audit (CSV):** De fiecare data cand este executata o operatiune din serviciile principale (ex. `addVehicle`, `rentVehicle`), o inregistrare este adaugata automat in fisierul local `audit.csv`, in formatul `nume_actiune, timestamp`.
