# Quick Start - Stereo70 Navigator ⚡

## Compilare Rapidă (5 minute)

### Opțiunea 1: Android Studio (Recomandat)

1. **Instalează Android Studio** (30 min prima dată)
   - Download: https://developer.android.com/studio
   - Rulează installer-ul și urmează pașii

2. **Deschide Proiectul**
   ```
   Android Studio → Open → Selectează folderul "Stereo70Navigator"
   ```

3. **Așteaptă Gradle Sync** (automat, ~3 min)
   - Bară de progres în partea de jos
   - Dacă apare eroare la SDK: Tools → SDK Manager → Instalează Android 14.0

4. **Build APK**
   ```
   Build → Build Bundle(s) / APK(s) → Build APK(s)
   ```

5. **Găsește APK-ul**
   ```
   Notificare "APK generated" → Click "locate"
   Sau: app\build\outputs\apk\debug\app-debug.apk
   ```

### Opțiunea 2: Linie de Comandă (Rapid)

**Cerință:** JDK 8 sau mai nou instalat

```cmd
cd "C:\Users\CristianLavre\OneDrive - Alfa IM\Lucru\tutorial-gitlab-claude\Stereo70Navigator"
gradlew.bat assembleDebug
```

APK va fi în: `app\build\outputs\apk\debug\app-debug.apk`

---

## Instalare pe Telefon (2 minute)

### Metoda 1: USB + ADB (Instant)

1. Activează "USB Debugging" pe telefon:
   - Setări → Despre telefon → Apasă 7 ori "Număr versiune"
   - Setări → Opțiuni dezvoltator → USB debugging ON

2. Conectează telefonul și din Android Studio:
   ```
   Click ▶️ (Run) → Selectează telefonul → OK
   ```

### Metoda 2: Transfer Manual

1. **Copiază APK pe telefon** (oricare metodă):
   - Email ca atașament
   - Google Drive / Dropbox
   - USB (copiază în Downloads)
   - Bluetooth

2. **Pe telefon:**
   - Deschide File Manager → Downloads
   - Click pe `app-debug.apk`
   - Permite "Surse necunoscute" (dacă cere)
   - Instalează

---

## Testare Rapidă (30 secunde)

### Test 1: Conversie Simplă

```
X: 500000
Y: 500000
```
→ Click "Convertește"
→ Ar trebui să afișeze: ~46.0°N, 25.0°E (aproximativ centrul României)

### Test 2: Navigație

```
X: 500000
Y: 450000
```
→ Click "Navighează"
→ Ar trebui să se deschidă Google Maps

### Test 3: Favorite

```
Nume: Test Locație
X: 520000
Y: 480000
```
→ Click "Adaugă la Favorite"
→ Click "Vezi Favorite"
→ Ar trebui să apară în listă

---

## Rezolvare Probleme Express

| Problemă | Soluție Rapidă |
|----------|----------------|
| Gradle Sync eșuează | File → Invalidate Caches / Restart |
| SDK lipsește | Tools → SDK Manager → Instalează Android 14.0 |
| APK nu se instalează | Setări telefon → Securitate → "Surse necunoscute" ON |
| App crashes | Verifică că Android >= 7.0 |
| Google Maps nu se deschide | Instalează Google Maps din Play Store |

---

## Coordonate Românești Comune (Pentru Testare)

| Oraș | X (Stereo 70) | Y (Stereo 70) |
|------|---------------|---------------|
| București | 500119 | 357429 |
| Cluj-Napoca | 465816 | 542649 |
| Timișoara | 399344 | 447279 |
| Iași | 594284 | 538654 |
| Constanța | 578861 | 325304 |

---

## Format Import Fișier

Creează `coordonate.txt` sau `coordonate.csv`:

```
# Comentariu (opțional)
Birou,500000,450000
Depozit,520000,460000
480000,440000
```

→ În app: "Importă din Fișier" → Selectează fișierul

---

## Comenzi Utile

```bash
# Build debug APK
gradlew assembleDebug

# Build release APK
gradlew assembleRelease

# Instalare directă pe telefon conectat
gradlew installDebug

# Curățare build
gradlew clean

# Vezi toate task-urile disponibile
gradlew tasks
```

---

## Structura Proiectului (Pentru Modificări)

```
Stereo70Navigator/
├── app/
│   ├── src/main/
│   │   ├── java/com/stereo70/navigator/
│   │   │   ├── MainActivity.java          # Ecran principal
│   │   │   ├── FavoritesActivity.java     # Ecran favorite
│   │   │   ├── Stereo70Converter.java     # Algoritm conversie
│   │   │   ├── FavoritesManager.java      # Gestionare salvare
│   │   │   └── ...
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml      # Design ecran principal
│   │   │   │   └── activity_favorites.xml # Design favorite
│   │   │   ├── values/
│   │   │   │   ├── strings.xml            # Texte interfață
│   │   │   │   └── colors.xml             # Culori
│   │   └── AndroidManifest.xml            # Configurare app
│   └── build.gradle                       # Dependințe
├── GHID_INSTALARE.md                      # Ghid complet detaliat
└── README.md                              # Documentație generală
```

---

## Link-uri Utile

- **Android Studio:** https://developer.android.com/studio
- **JDK Download:** https://adoptium.net/
- **ADB Drivers:** https://developer.android.com/studio/run/win-usb
- **Stereo 70 Info:** https://epsg.io/31700

---

Pentru ghid complet și detaliat, vezi **[GHID_INSTALARE.md](GHID_INSTALARE.md)**
