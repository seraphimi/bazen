# Bazen Management System

Kompletna RESTful web aplikacija za upravljanje bazenom korišćenjem Spring Boot 3.0, JDK 17 i MySQL baze podataka.

## Funkcionalnosti

### 1. Pregled bazena
- Prikaz svih bazena sa informacijama o nazivu, kapacitetu, tipu (otvoreni/zatvoreni) i statusu (aktivno/u održavanju)
- REST API endpoints za CRUD operacije nad bazenima

### 2. Upravljanje članovima
- CRUD operacije za članove uključujući ime, prezime, kontakt podatke i status članarine
- Praćenje istorije poseta članova
- Pretraga članova po imenu i prezimenu

### 3. Rezervacije **SA KRITIČNOM VALIDACIJOM**
- Omogućava rezervaciju termina za bazene
- **AUTOMATSKO ZATVARANJE APLIKACIJE** ako broj rezervacija prekorači kapacitet bazena
- Validacija: ID člana, ID bazena, datum i vreme, broj osoba
- Proverava da li je član aktivan i da li je bazen dostupan

### 4. Planiranje održavanja
- Evidencija održavanja: ID bazena, opis radova, datum početka, predviđeno trajanje
- Praćenje statusa održavanja (planirano, u toku, završeno, otkazano)

### 5. Izveštaji
- Generisanje izveštaja o posećenosti bazena
- Izveštaji o prihodima
- Izveštaji o statusu članova

## Tehničke karakteristike

### Stack
- **Spring Boot 3.1.0**
- **JDK 17**
- **MySQL** baza podataka
- **Maven** za upravljanje zavisnostima
- **JPA/Hibernate** za perzistenciju
- **Logback** za logovanje

### Arhitektura
- Višeslojna arhitektura (Controller, Service, Repository)
- REST API sa JSON razmenom podataka
- Kompletno implementirano logovanje na svim nivoima

### Entiteti
1. **Bazen** - naziv, kapacitet, tip, status
2. **Clan** - ime, prezime, kontakt podaci, status članarine
3. **Rezervacija** - ID člana, ID bazena, datum i vreme, broj osoba
4. **Odrzavanje** - ID bazena, opis radova, datum početka, predviđeno trajanje

## REST API Endpoints

### Bazeni (`/api/bazeni`)
- `GET /api/bazeni` - Dohvata sve bazene
- `GET /api/bazeni/{id}` - Dohvata bazen po ID
- `POST /api/bazeni` - Kreira novi bazen
- `PUT /api/bazeni/{id}` - Ažurira bazen
- `DELETE /api/bazeni/{id}` - Briše bazen
- `GET /api/bazeni/aktivni` - Dohvata aktivne bazene
- `GET /api/bazeni/tip/{tip}` - Dohvata bazene po tipu
- `GET /api/bazeni/status/{status}` - Dohvata bazene po statusu

### Članovi (`/api/clanovi`)
- `GET /api/clanovi` - Dohvata sve članove
- `GET /api/clanovi/{id}` - Dohvata člana po ID
- `POST /api/clanovi` - Kreira novog člana
- `PUT /api/clanovi/{id}` - Ažurira člana
- `DELETE /api/clanovi/{id}` - Briše člana
- `GET /api/clanovi/aktivni` - Dohvata aktivne članove
- `GET /api/clanovi/pretrazi/{searchTerm}` - Pretražuje članove
- `GET /api/clanovi/count/aktivni` - Broji aktivne članove

### Rezervacije (`/api/rezervacije`) **KRITIČNE**
- `GET /api/rezervacije` - Dohvata sve rezervacije
- `GET /api/rezervacije/{id}` - Dohvata rezervaciju po ID
- `POST /api/rezervacije` - Kreira novu rezervaciju **SA VALIDACIJOM KAPACITETA**
- `POST /api/rezervacije/create` - Kreira rezervaciju sa parametrima
- `PUT /api/rezervacije/{id}` - Ažurira rezervaciju
- `DELETE /api/rezervacije/{id}` - Briše rezervaciju
- `GET /api/rezervacije/bazen/{bazenId}` - Rezervacije po bazenu
- `GET /api/rezervacije/clan/{clanId}` - Rezervacije po članu
- `GET /api/rezervacije/buduće` - Buduće rezervacije

### Održavanje (`/api/odrzavanje`)
- `GET /api/odrzavanje` - Dohvata sva održavanja
- `GET /api/odrzavanje/{id}` - Dohvata održavanje po ID
- `POST /api/odrzavanje` - Kreira novo održavanje
- `POST /api/odrzavanje/create` - Kreira održavanje sa parametrima
- `PUT /api/odrzavanje/{id}` - Ažurira održavanje
- `DELETE /api/odrzavanje/{id}` - Briše održavanje
- `GET /api/odrzavanje/bazen/{bazenId}` - Održavanje po bazenu
- `PUT /api/odrzavanje/{id}/pokreni` - Pokreće održavanje
- `PUT /api/odrzavanje/{id}/zavrsi` - Završava održavanje

### Izveštaji (`/api/izvestaji`)
- `GET /api/izvestaji/opsti` - Opšti izveštaj
- `GET /api/izvestaji/posecenost` - Izveštaj o posećenosti
- `GET /api/izvestaji/clanovi` - Izveštaj o članovima
- `GET /api/izvestaji/prihodi` - Izveštaj o prihodima

## Instalacija i pokretanje

### Preduslovi
- JDK 17
- Maven 3.6+
- MySQL 8.0+

### Pokretanje

1. **Kloniraj repository:**
```bash
git clone <repository-url>
cd bazen
```

2. **Konfiguriši MySQL bazu podataka:**
```sql
CREATE DATABASE bazen_db;
CREATE USER 'bazen_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON bazen_db.* TO 'bazen_user'@'localhost';
FLUSH PRIVILEGES;
```

3. **Ažuriraj application.properties:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bazen_db
spring.datasource.username=bazen_user
spring.datasource.password=password
```

4. **Izgradi aplikaciju:**
```bash
mvn clean package
```

5. **Pokreni aplikaciju:**
```bash
java -jar target/bazen-management-1.0.0.jar
```

Aplikacija će biti dostupna na `http://localhost:8080`

## Testiranje

### Pokretanje testova
```bash
mvn test
```

### Testiranje REST API-ja
Možete koristiti tools poput Postman-a ili curl-a za testiranje endpoints.

**Primer kreiranja bazena:**
```bash
curl -X POST http://localhost:8080/api/bazeni \
  -H "Content-Type: application/json" \
  -d '{
    "naziv": "Glavni bazen",
    "kapacitet": 50,
    "tip": "OTVORENI",
    "status": "AKTIVNO"
  }'
```

**Primer kreiranja rezervacije:**
```bash
curl -X POST "http://localhost:8080/api/rezervacije/create?clanId=1&bazenId=1&datumVreme=2025-09-15T10:00:00&brojOsoba=5"
```

## ⚠️ KRITIČNA FUNKCIONALNOST - VALIDACIJA KAPACITETA

**VAŽNO:** Aplikacija implementira kritičnu validaciju kapaciteta bazena. Ako ukupan broj rezervacija za bilo koji dan prekorači kapacitet bazena, aplikacija će:

1. Prikazati detaljnu grešku u console
2. Logovati grešku u log fajlove
3. **AUTOMATSKI SE ZATVORITI** sa exit kodom 1

Ova funkcionalnost je implementirana u `RezervacijaService.validatePoolCapacity()` metodi.

## Logovanje

Aplikacija koristi Logback za logovanje:
- **Console output** - za development
- **File logging** - `logs/bazen-management.log`
- **Error logging** - `logs/bazen-management-errors.log`

Log nivoi:
- DEBUG za interne operacije
- INFO za business operacije
- ERROR za greške i kritične situacije

## Struktura projekta

```
src/
├── main/
│   ├── java/com/bazen/management/
│   │   ├── entity/          # JPA entiteti
│   │   ├── repository/      # Spring Data repositories
│   │   ├── service/         # Business logika
│   │   ├── controller/      # REST kontroleri
│   │   └── exception/       # Custom exception handling
│   └── resources/
│       ├── application.properties
│       └── logback-spring.xml
└── test/
    ├── java/                # Unit testovi
    └── resources/
        └── application-test.properties
```

## Licenca

Ova aplikacija je kreirana za upravljanje bazenom sa fokusom na sigurnost i validaciju kapaciteta.

---

**Napomena:** Uvek testirajte validaciju kapaciteta u test okruženju pre produkcije jer će aplikacija biti automatski zatvorena ako se kapacitet prekorači!
