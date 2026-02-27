# 📁 Structura Proiectului Stereo70 Navigator

## Organizare Generală

```
Stereo70Navigator/
│
├── 📄 START_AICI.md                    ← ⭐ ÎNCEPE DE AICI (ghid super simplu)
├── 📄 QUICK_START.md                   ← Ghid rapid (5 min)
├── 📄 GHID_INSTALARE.md                ← Ghid complet detaliat
├── 📄 README.md                        ← Informații generale
├── 📄 ICOANE_INFO.md                   ← Cum să adaugi iconița
├── 📄 STRUCTURA_PROIECT.md            ← Acest fișier
│
├── 📄 exemplu_import.csv               ← Exemplu fișier import
├── 📄 coordonate_romania.csv           ← Coordonate utile România
│
├── 📂 app/                             ← CODUL APLICAȚIEI
│   ├── 📄 build.gradle                 ← Configurare și dependințe
│   │
│   └── 📂 src/main/
│       ├── 📄 AndroidManifest.xml      ← Configurare app (permisiuni, activități)
│       │
│       ├── 📂 java/com/stereo70/navigator/
│       │   ├── MainActivity.java              ← Ecran principal
│       │   ├── FavoritesActivity.java         ← Ecran favorite
│       │   ├── FavoritesAdapter.java          ← Adaptor listă favorite
│       │   ├── FavoritesManager.java          ← Salvare/citire favorite
│       │   ├── Coordinate.java                ← Model date coordonate
│       │   └── Stereo70Converter.java         ← Algoritm conversie Stereo70→GPS
│       │
│       └── 📂 res/                     ← RESURSE (interfață)
│           ├── 📂 layout/
│           │   ├── activity_main.xml           ← Design ecran principal
│           │   ├── activity_favorites.xml      ← Design ecran favorite
│           │   └── item_favorite.xml           ← Design item din listă
│           │
│           ├── 📂 values/
│           │   ├── strings.xml                 ← Texte interfață (română)
│           │   └── colors.xml                  ← Paleta de culori
│           │
│           └── 📂 mipmap-*/            ← Iconiță aplicație (diverse rezoluții)
│
├── 📄 build.gradle                     ← Configurare build proiect
├── 📄 settings.gradle                  ← Setări Gradle
├── 📄 gradle.properties                ← Proprietăți Gradle
│
└── 📂 gradle/wrapper/                  ← Gradle Wrapper (versiune automată)
    └── gradle-wrapper.properties
```

---

## 🎯 Fișiere Cheie - Ce Face Fiecare

### 📱 Cod Java (Logica Aplicației)

#### `MainActivity.java` (Ecran Principal)
```java
Funcționalități:
✅ Introducere manuală coordonate X, Y
✅ Validare input utilizator
✅ Conversie Stereo 70 → GPS
✅ Afișare rezultat conversie
✅ Deschidere Google Maps pentru navigație
✅ Salvare la favorite
✅ Import coordonate din fișier
✅ Navigare la ecranul favorite
```

#### `Stereo70Converter.java` (Nucleul Aplicației)
```java
Funcționalități:
🔄 Conversie precisă Stereo 70 → WGS84
📐 Transformare Helmert (Krasovsky → WGS84)
✓ Validare coordonate Stereo 70
📊 Calcule geodezice complexe

Parametri folosiți:
- Elipsoid Krasovsky 1940 (Stereo 70)
- Elipsoid WGS84 (GPS)
- Parametri proiecție Stereo 70 pentru România
```

#### `FavoritesManager.java` (Gestionare Date)
```java
Funcționalități:
💾 Salvare favorite (SharedPreferences + JSON)
📖 Citire favorite
🗑️ Ștergere favorite
✏️ Editare favorite
🔍 Verificare dacă coordonata e favorită
```

#### `FavoritesActivity.java` (Ecran Favorite)
```java
Funcționalități:
📋 Afișare listă favorite
🗺️ Navigare la favorit
🗑️ Ștergere cu confirmare
🔄 Refresh listă după modificări
```

#### `FavoritesAdapter.java` (Adaptor Listă)
```java
Funcționalități:
📱 Afișare items în RecyclerView
🎨 Binding date → interfață
👆 Gestionare click-uri pe butoane
♻️ Reciclare views pentru performanță
```

#### `Coordinate.java` (Model Date)
```java
Proprietăți:
- id: identificator unic
- name: nume locație
- stereoX: coordonata X (Stereo 70)
- stereoY: coordonata Y (Stereo 70)
- isFavorite: flag favorit
- timestamp: când a fost creată
```

---

### 🎨 Layout-uri XML (Interfața Vizuală)

#### `activity_main.xml` (Design Ecran Principal)
```xml
Componente:
📝 TextInputEditText - nume locație
📝 TextInputEditText - coordonata X
📝 TextInputEditText - coordonata Y
🔘 Button - Convertește
🔘 Button - Navighează
⭐ Button - Adaugă la Favorite
📂 Button - Importă din Fișier
📋 Button - Vezi Favorite
📊 MaterialCardView - Afișare rezultat GPS
```

#### `activity_favorites.xml` (Design Ecran Favorite)
```xml
Componente:
📱 RecyclerView - listă scrollabilă favorite
📄 TextView - mesaj "Nu aveți favorite"
```

#### `item_favorite.xml` (Design Item Favorit)
```xml
Componente:
📝 TextView - nume locație
📍 TextView - coordonate X, Y
🗺️ Button - Navighează
🗑️ Button - Șterge
```

---

### ⚙️ Fișiere Configurare

#### `AndroidManifest.xml`
```xml
Conține:
✅ Permisiuni (Internet, Read Storage)
✅ Nume aplicație
✅ Iconiță aplicație
✅ Temă aplicație
✅ Activități (MainActivity, FavoritesActivity)
✅ Activity launcher (MainActivity)
```

#### `app/build.gradle`
```gradle
Conține:
📦 Package name: com.stereo70.navigator
🎯 Target SDK: 34 (Android 14)
📱 Min SDK: 24 (Android 7.0)
📚 Dependințe:
   - AppCompat (compatibilitate)
   - Material Components (design modern)
   - ConstraintLayout (layout-uri)
   - RecyclerView (liste)
   - Gson (salvare JSON)
```

#### `strings.xml` (Texte Interfață)
```xml
Conține:
💬 Toate textele din aplicație în română
📝 Ușor de tradus în alte limbi
🔄 Modificabil fără a atinge codul
```

#### `colors.xml` (Paleta Culori)
```xml
Culori folosite:
🔵 Primary: #2196F3 (albastru navigație)
🟠 Accent: #FF5722 (portocaliu)
⚫ Variante dark/light
```

---

## 🔧 Cum să Modifici Aplicația

### 1️⃣ Schimbă Textele (Română → Altă Limbă)

**Editează:** `app/src/main/res/values/strings.xml`

```xml
<string name="enter_coordinates">Introduceți Coordonatele</string>
→
<string name="enter_coordinates">Enter Coordinates</string>
```

### 2️⃣ Schimbă Culorile

**Editează:** `app/src/main/res/values/colors.xml`

```xml
<color name="primary">#FF2196F3</color>
→
<color name="primary">#FF4CAF50</color>  <!-- Verde -->
```

### 3️⃣ Modifică Algoritmul de Conversie

**Editează:** `app/src/main/java/com/stereo70/navigator/Stereo70Converter.java`

```java
// Modifică parametrii de transformare Helmert
private static final double dx = 28.0;
private static final double dy = -121.0;
private static final double dz = -77.0;
```

### 4️⃣ Adaugă Validări Suplimentare

**În:** `MainActivity.java` → metoda `convertCoordinates()`

```java
if (!Stereo70Converter.isValidStereo70(x, y)) {
    Toast.makeText(this, "Coordonate invalide!", Toast.LENGTH_LONG).show();
    return;
}

// Adaugă validări personalizate aici
if (x < 400000) {
    Toast.makeText(this, "X prea mic pentru România!", Toast.LENGTH_SHORT).show();
    return;
}
```

### 5️⃣ Modifică Designul

**Editează layout-urile XML**

Exemplu - Schimbă culoarea butonului în `activity_main.xml`:
```xml
<Button
    android:id="@+id/btnNavigate"
    android:backgroundTint="#4CAF50"  <!-- Adaugă această linie -->
    ... />
```

---

## 🚀 După Modificări

**IMPORTANT:** După orice modificare în cod:

```
1. File → Save All (Ctrl+S)
2. Build → Clean Project
3. Build → Build APK(s)
4. Reinstalează APK-ul pe telefon
```

---

## 📊 Flux de Date în Aplicație

```
┌─────────────────────────────────────────────────────┐
│                   Utilizator                        │
└─────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────┐
│              MainActivity (UI)                      │
│  • Introduce X, Y                                   │
│  • Click "Navighează"                               │
└─────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────┐
│         Stereo70Converter.stereo70ToGPS()          │
│  • Validare coordonate                              │
│  • Conversie matematică Stereo70 → GPS              │
│  • Return GPSCoordinate(lat, lon)                   │
└─────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────┐
│         Intent către Google Maps                    │
│  • Construiește URI: "google.navigation:q=lat,lon"  │
│  • Deschide Google Maps pentru navigație            │
└─────────────────────────────────────────────────────┘
```

---

## 💾 Salvare Date (Favorite)

```
Coordinate object
       ↓
FavoritesManager.saveFavorite()
       ↓
Gson.toJson() → String JSON
       ↓
SharedPreferences.putString()
       ↓
Stocare permanentă pe telefon
```

**Locație fizică pe telefon:**
```
/data/data/com.stereo70.navigator/shared_prefs/Stereo70Favorites.xml
```

---

## 🔍 Debug și Testare

### Loguri în Android Studio:

În orice clasă Java, adaugă:
```java
import android.util.Log;

// În orice metodă:
Log.d("StereoApp", "X = " + x + ", Y = " + y);
Log.d("StereoApp", "GPS: " + gps.latitude + ", " + gps.longitude);
```

Vezi logurile:
```
Android Studio → Logcat (tab jos) → Filtrează "StereoApp"
```

### Testare Unitară:

Creează `Stereo70ConverterTest.java` în `app/src/test/`:
```java
@Test
public void testBucharestConversion() {
    GPSCoordinate gps = Stereo70Converter.stereo70ToGPS(500119, 357429);
    assertEquals(44.43, gps.latitude, 0.01);
    assertEquals(26.10, gps.longitude, 0.01);
}
```

---

## 📖 Resurse pentru Învățare

### Documentație Oficială:
- Android Developers: https://developer.android.com/
- Material Design: https://material.io/

### Tutoriale:
- Java pentru Android: https://www.javatpoint.com/android-tutorial
- Layout-uri XML: https://developer.android.com/guide/topics/ui/declaring-layout

### Comunitate:
- Stack Overflow: https://stackoverflow.com/questions/tagged/android
- Reddit: r/androiddev

---

## ✅ Checklist Modificări

Înainte de a da build final:

- [ ] Cod compilează fără erori
- [ ] Testat pe emulator sau telefon real
- [ ] Texte corecte în strings.xml
- [ ] Culori consistent în colors.xml
- [ ] Iconița adăugată (opțional)
- [ ] Versiune incrementată în build.gradle
- [ ] Comentarii adăugate pentru cod complex
- [ ] Loguri de debug eliminate (dacă nu sunt necesare)

---

**Pentru întrebări despre structură sau modificări, consultă celelalte fișiere de documentație!**
