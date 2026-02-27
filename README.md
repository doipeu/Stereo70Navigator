# Stereo70 Navigator 🧭

Aplicație Android pentru convertirea coordonatelor Stereo 70 (sistemul românesc de proiecție) în coordonate GPS și navigare cu Google Maps.

## Funcționalități

- 📍 **Introducere manuală** coordonate Stereo 70
- 📂 **Import coordonate** din fișiere CSV/TXT
- 🔄 **Conversie precisă** Stereo 70 → WGS84 (GPS)
- 🗺️ **Navigație directă** cu Google Maps
- ⭐ **Salvare favorite** pentru acces rapid
- 📱 **Interfață intuitivă** Material Design

## Instalare și Compilare

Vezi **[GHID_INSTALARE.md](GHID_INSTALARE.md)** pentru instrucțiuni complete pas cu pas.

### Rezumat rapid:

1. Instalează Android Studio
2. Deschide proiectul
3. Build → Build APK(s)
4. Instalează APK-ul pe telefon

## Utilizare

### Introducere Manuală
1. Introdu coordonatele X și Y (Stereo 70)
2. Apasă "Convertește" pentru GPS sau "Navighează" pentru Google Maps

### Import Fișier
Format: `nume,x,y` sau `x,y` (câte o linie)

Exemplu fișier:
```
Punct 1,500000,450000
Punct 2,520000,460000
480000,440000
```

## Cerințe

- **Dezvoltare:** Android Studio, JDK 8+
- **Telefon:** Android 7.0+ (API 24)

## Tehnologii

- Java
- Android SDK 34
- Material Design Components
- Gson pentru salvare date
- RecyclerView pentru liste

## Algoritm Conversie

Conversie precisă folosind:
- Elipsoidul Krasovsky 1940 (Stereo 70)
- Elipsoidul WGS84 (GPS)
- Transformare Helmert cu parametrii oficiali pentru România

## Licență

Acest proiect este creat pentru uz personal/educațional.

## Suport

Pentru probleme și întrebări, consultă `GHID_INSTALARE.md` secțiunea "Rezolvare Probleme Comune".
