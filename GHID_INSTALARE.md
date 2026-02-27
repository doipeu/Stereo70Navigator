# Ghid Complet: Stereo70 Navigator - De la Cod la APK

## 📱 Despre Aplicație

**Stereo70 Navigator** este o aplicație Android care convertește coordonatele din sistemul românesc Stereo 70 în coordonate GPS (WGS84) și le deschide direct în Google Maps pentru navigație.

### Funcționalități:
- ✅ Introducere manuală coordonate Stereo 70
- ✅ Import coordonate din fișiere CSV/TXT
- ✅ Conversie precisă Stereo 70 → GPS (WGS84)
- ✅ Deschidere directă în Google Maps pentru navigație
- ✅ Salvare locații favorite
- ✅ Gestionare listă favorite (vizualizare, navigare, ștergere)

---

## 🛠️ Pasul 1: Instalare Android Studio

### 1.1. Descărcare Android Studio

1. Deschide browser-ul și accesează: https://developer.android.com/studio
2. Click pe **"Download Android Studio"**
3. Acceptă termenii și condițiile
4. Descarcă fișierul de instalare (aproximativ 1 GB)

### 1.2. Instalare Android Studio

**Pentru Windows:**
1. Rulează fișierul descărcat (android-studio-xxx-windows.exe)
2. Click "Next" la fiecare pas
3. Asigură-te că sunt bifate:
   - ☑️ Android Studio
   - ☑️ Android Virtual Device
4. Click "Install"
5. Așteaptă finalizarea instalării (10-20 minute)
6. Click "Finish"

### 1.3. Configurare Inițială Android Studio

1. La primul pornire, selectează **"Do not import settings"**
2. Click "OK"
3. Selectează **"Standard"** installation type
4. Alege tema (Light sau Dark)
5. Click "Next" și apoi "Finish"
6. Așteaptă descărcarea componentelor (SDK, tools) - poate dura 20-30 minute
7. Click "Finish" când se termină

---

## 📂 Pasul 2: Pregătire Proiect

### 2.1. Localizare Folderul Proiect

Proiectul tău se află la:
```
C:\Users\CristianLavre\OneDrive - Alfa IM\Lucru\tutorial-gitlab-claude\Stereo70Navigator
```

### 2.2. Deschidere Proiect în Android Studio

1. Pornește Android Studio
2. Click pe **"Open"** (sau File → Open)
3. Navighează la folderul proiectului
4. Selectează folderul **"Stereo70Navigator"**
5. Click "OK"

### 2.3. Sincronizare Gradle (Automată)

Android Studio va începe automat să sincronizeze proiectul:
- În partea de jos vei vedea "Gradle Sync" în progres
- Așteaptă finalizarea (prima dată poate dura 5-10 minute)
- Dacă apare eroare, verifică conexiunea la internet

**Notă:** Dacă sincronizarea nu pornește automat:
1. Click pe "File" → "Sync Project with Gradle Files"
2. Sau click pe iconița 🔄 (Sync Now) din notificarea galbenă de sus

---

## 🔧 Pasul 3: Verificare și Rezolvare Probleme

### 3.1. Verificare SDK Android

1. Click pe "File" → "Project Structure" (sau Ctrl+Alt+Shift+S)
2. În secțiunea **"SDK Location"**:
   - Verifică că există un path la Android SDK
   - Dacă nu există, click "Download Android SDK"
3. Click "OK"

### 3.2. Instalare SDK Platform (dacă este necesar)

1. Click pe "Tools" → "SDK Manager"
2. În tab-ul **"SDK Platforms"**:
   - Bifează ☑️ **Android 14.0 (API 34)**
   - Bifează ☑️ **Android 7.0 (API 24)** - versiunea minimă
3. Click "Apply" → "OK"
4. Așteaptă descărcarea

### 3.3. Verificare Build Tools

1. În SDK Manager, mergi la tab-ul **"SDK Tools"**
2. Asigură-te că sunt instalate:
   - ☑️ Android SDK Build-Tools
   - ☑️ Android Emulator
   - ☑️ Android SDK Platform-Tools
3. Dacă lipsește ceva, bifează și click "Apply"

---

## 📱 Pasul 4: Compilare APK

### 4.1. Pregătire pentru Build

1. În Android Studio, așteaptă ca Gradle Sync să se termine complet
2. Verifică că în partea de jos nu mai sunt erori (tab-ul "Build")
3. Dacă apar erori, citește mesajele și urmează sugestiile

### 4.2. Build APK - Metodă 1 (Debug APK)

**Cea mai simplă metodă pentru testare:**

1. Click pe **"Build"** din meniul de sus
2. Selectează **"Build Bundle(s) / APK(s)"**
3. Click **"Build APK(s)"**
4. Așteaptă procesul de build (2-5 minute prima dată)
5. Când apare notificarea "APK(s) generated successfully":
   - Click pe **"locate"**
   - Se va deschide folderul cu APK-ul

**Locația APK-ului:**
```
Stereo70Navigator\app\build\outputs\apk\debug\app-debug.apk
```

### 4.3. Build APK - Metodă 2 (Release APK, optimizat)

**Pentru distribuție (fără semnătură):**

1. Click pe **"Build"** → **"Generate Signed Bundle / APK"**
2. Selectează **"APK"**
3. Click "Next"
4. La "Create new..." pentru key store:
   - Key store path: alege o locație (ex: Desktop\my-key.jks)
   - Password: alege o parolă (notează-o!)
   - Alias: "stereo70key"
   - Password pentru alias: aceeași parolă
   - Validity: 25 (ani)
   - Certificate info:
     - First and Last Name: numele tău
     - Organizational Unit: nu e obligatoriu
     - Organization: nu e obligatoriu
     - City or Locality: București (opțional)
     - State or Province: România (opțional)
     - Country Code: RO
5. Click "OK"
6. Selectează "release" pentru Build Variants
7. Click "Create"
8. Așteaptă finalizarea build-ului

**Locația APK-ului release:**
```
Stereo70Navigator\app\build\outputs\apk\release\app-release.apk
```

### 4.4. Metodă Alternativă (Linie de Comandă)

Dacă Android Studio nu funcționează corect:

1. Deschide Command Prompt (Windows)
2. Navighează la folderul proiectului:
   ```cmd
   cd "C:\Users\CristianLavre\OneDrive - Alfa IM\Lucru\tutorial-gitlab-claude\Stereo70Navigator"
   ```
3. Rulează:
   ```cmd
   gradlew.bat assembleDebug
   ```
4. Așteaptă finalizarea
5. APK-ul va fi în: `app\build\outputs\apk\debug\app-debug.apk`

---

## 📲 Pasul 5: Instalare APK pe Telefon

### 5.1. Pregătire Telefon Android

1. Deschide **Setări** pe telefon
2. Mergi la **"Despre telefon"** sau **"About phone"**
3. Apasă de 7 ori pe **"Număr versiune"** sau **"Build number"**
   - Vei primi mesaj "Acum ești dezvoltator!"
4. Înapoi în Setări, intră în **"Opțiuni dezvoltator"** sau **"Developer options"**
5. Activează **"USB debugging"**

### 5.2. Instalare prin USB

**Metoda 1 - Direct din Android Studio:**
1. Conectează telefonul la PC cu USB
2. Pe telefon, aprobă **"Permite USB debugging"**
3. În Android Studio, click pe ▶️ (Run) butonul verde
4. Selectează dispozitivul tău din listă
5. Aplicația se va instala și porni automat

**Metoda 2 - Manual cu ADB:**
1. Conectează telefonul la PC
2. Deschide Command Prompt în folderul cu APK:
   ```cmd
   cd "C:\Users\CristianLavre\OneDrive - Alfa IM\Lucru\tutorial-gitlab-claude\Stereo70Navigator\app\build\outputs\apk\debug"
   ```
3. Rulează:
   ```cmd
   adb install app-debug.apk
   ```

### 5.3. Instalare fără USB (Transfer Fișier)

1. Copiază `app-debug.apk` pe telefon:
   - Prin email (trimite-ți ca atașament)
   - Prin cloud (Google Drive, Dropbox)
   - Prin Bluetooth
   - Prin cablu USB (copiază în Downloads)

2. Pe telefon:
   - Deschide fișierul APK din File Manager
   - Dacă apare "Instalare blocată":
     - Mergi la Setări → Securitate
     - Activează **"Surse necunoscute"** sau **"Unknown sources"**
     - Sau permite pentru aplicația respectivă (Chrome, Files)
   - Apasă **"Instalează"**
   - Așteaptă finalizarea
   - Apasă **"Deschide"** sau găsește "Stereo70 Navigator" în meniul de aplicații

---

## 🎯 Pasul 6: Utilizare Aplicație

### 6.1. Introducere Manuală Coordonate

1. Deschide aplicația **Stereo70 Navigator**
2. Introdu:
   - **Nume Locație** (opțional): ex. "Punct Măsurătoare 1"
   - **Coordonata X**: ex. 500000
   - **Coordonata Y**: ex. 450000
3. Apasă **"Convertește"** pentru a vedea GPS
4. Apasă **"Navighează"** pentru a deschide în Google Maps

### 6.2. Import Coordonate din Fișier

**Format fișier acceptat (CSV sau TXT):**

```
# Comentariu - liniile cu # sunt ignorate
Nume1,500000,450000
Nume2,520000,460000
480000,440000
```

**Formate acceptate:**
- `nume,x,y` - cu virgulă
- `nume;x;y` - cu punct și virgulă
- `x y` sau `x,y` - fără nume (va genera "Import 1", "Import 2", etc.)

**Pași:**
1. Creează un fișier pe telefon (cu orice editor de text)
2. În aplicație, apasă **"Importă din Fișier"**
3. Selectează fișierul
4. Coordonatele vor fi adăugate automat la favorite

### 6.3. Gestionare Favorite

1. Apasă **"Vezi Favorite"**
2. Vezi lista tuturor locațiilor salvate
3. Pentru fiecare favorit:
   - **"Navighează"** - deschide în Google Maps
   - **"Șterge"** - elimină din favorite

---

## 🔍 Rezolvare Probleme Comune

### Problema 1: "Gradle Sync Failed"

**Soluție:**
1. Verifică conexiunea la internet
2. Click pe "File" → "Invalidate Caches / Restart"
3. Așteaptă repornirea Android Studio
4. Încearcă din nou

### Problema 2: "SDK Not Found"

**Soluție:**
1. Tools → SDK Manager
2. Instalează Android 14.0 (API 34)
3. Click Apply și OK

### Problema 3: APK-ul nu se instalează pe telefon

**Soluție:**
1. Verifică că ai activat "Surse necunoscute"
2. Dezinstalează versiunea veche (dacă există)
3. Reîncearcă instalarea

### Problema 4: "App keeps stopping" pe telefon

**Soluție:**
1. Asigură-te că telefonul are Android 7.0 (API 24) sau mai nou
2. Verifică că Google Maps este instalat
3. Verifică permisiunile aplicației în Setări

### Problema 5: Google Maps nu se deschide

**Soluție:**
1. Instalează Google Maps din Play Store
2. Sau aplicația va deschide browser-ul cu Google Maps web

---

## 📋 Cerințe Sistem

### Pentru Dezvoltare (PC):
- **OS:** Windows 10/11, macOS, sau Linux
- **RAM:** Minim 8 GB (recomandat 16 GB)
- **Spațiu:** Minim 10 GB liber
- **Internet:** Necesar pentru download-uri inițiale

### Pentru Aplicație (Telefon):
- **Android:** 7.0 (Nougat) sau mai nou
- **Spațiu:** ~10 MB
- **Permisiuni:** Internet (pentru Google Maps)

---

## 📞 Suport și Modificări

### Modificare Parametri Conversie

Dacă vrei să ajustezi algoritmul de conversie, editează:
```
app/src/main/java/com/stereo70/navigator/Stereo70Converter.java
```

### Personalizare Interfață

Pentru modificare culori, editează:
```
app/src/main/res/values/colors.xml
```

Pentru modificare texte, editează:
```
app/src/main/res/values/strings.xml
```

---

## ✅ Checklist Final

- [ ] Android Studio instalat și configurat
- [ ] Proiect deschis și sincronizat (Gradle)
- [ ] SDK Android 34 instalat
- [ ] APK compilat cu succes
- [ ] APK copiat pe telefon
- [ ] Aplicație instalată pe telefon
- [ ] Aplicația pornește fără erori
- [ ] Testat conversie coordonate
- [ ] Testat navigație Google Maps
- [ ] Testat salvare favorite

---

## 🎉 Felicitări!

Acum ai o aplicație Android funcțională pentru navigație cu coordonate Stereo 70!

Pentru întrebări sau probleme, verifică secțiunea "Rezolvare Probleme Comune" sau caută eroarea specifică pe Google/Stack Overflow.
