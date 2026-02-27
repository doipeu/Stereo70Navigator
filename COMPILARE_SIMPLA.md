# ⚡ Ghid ULTRA-SIMPLU: Compilare APK în 10 Minute

## 🎯 Metoda Cea Mai Simplă (Fără GitHub)

Dacă vrei să compilezi local rapid, iată pașii ESENȚIALI:

---

## Pasul 1: Descarcă Android Studio (15 min)

1. **Du-te la:** https://developer.android.com/studio
2. **Click:** "Download Android Studio"
3. **Rulează:** fișierul descărcat
4. **Click:** Next → Next → Install
5. **Prima pornire:**
   - "Do not import settings"
   - "Standard" installation
   - Așteaptă să descarce SDK-ul (~10 minute)

---

## Pasul 2: Deschide Proiectul (2 min)

1. **Pornește** Android Studio
2. **Click:** "Open"
3. **Selectează** folderul:
   ```
   C:\Users\CristianLavre\OneDrive - Alfa IM\Lucru\tutorial-gitlab-claude\Stereo70Navigator
   ```
4. **Click:** OK
5. **Așteaptă** Gradle Sync (3-5 min)

---

## Pasul 3: Compilează APK (3 min)

1. **În Android Studio, sus în meniu:**
   ```
   Build → Build Bundle(s) / APK(s) → Build APK(s)
   ```

2. **Așteaptă** bara de progres (2-4 min)

3. **Când apare notificare "APK generated":**
   - Click pe **"locate"**
   - SAU găsește manual:
   ```
   Stereo70Navigator\app\build\outputs\apk\debug\app-debug.apk
   ```

---

## Pasul 4: Instalează pe Telefon (2 min)

### Metoda 1: Email
```
1. Trimite-ți app-debug.apk pe email
2. Pe telefon, deschide emailul
3. Descarcă și instalează APK
```

### Metoda 2: Google Drive
```
1. Încarcă app-debug.apk pe Drive
2. Pe telefon, descarcă din Drive
3. Instalează APK
```

### Metoda 3: USB
```
1. Conectează telefonul la PC
2. Copiază app-debug.apk în Downloads
3. Pe telefon, deschide Downloads
4. Instalează APK
```

---

## ⚠️ Dacă Apare "Nu se poate instala"

```
Telefon → Setări → Securitate
→ Activează "Surse necunoscute" sau "Unknown sources"
```

---

## 🎉 GATA!

**Total timp:** ~20 minute prima dată (majoritatea = download-uri)

**Build-uri viitoare:** ~3 minute (doar Pasul 3)

---

## 🐛 Probleme?

### "Gradle sync failed"
```
File → Invalidate Caches / Restart
```

### "SDK not found"
```
Tools → SDK Manager → Instalează Android 14.0 (API 34)
```

### Build foarte lent
```
Normal prima dată! Download-ează dependințe.
Build-ul 2, 3, 4... vor fi mult mai rapide (1-2 min).
```

---

## 💡 Alternative Dacă Android Studio Nu Merge

### Opțiunea A: GitHub Actions
**Vezi:** [GITHUB_BUILD.md](GITHUB_BUILD.md)
- Compilare în cloud
- Zero instalări pe PC
- Gratuit

### Opțiunea B: Apktool Online
Servicii online (limitări):
- https://www.apkonline.net/
- https://appsgeyser.com/

**Dar** acestea nu funcționează bine cu proiecte Android Studio native.

---

## ✅ Cea Mai Bună Metodă: Android Studio

**De ce?**
- ✅ Oficial de la Google
- ✅ Tot ce ai nevoie într-un singur tool
- ✅ Gratuit
- ✅ Updates automate
- ✅ Debugging și testare
- ✅ Modifici codul ușor

**Dezavantaj:**
- ❌ Download mare (~1GB)
- ❌ Necesită ~10GB spațiu pe disk
- ❌ RAM: minim 8GB recomandat

---

## 🚀 Quick Start Absolut

**Ai foarte puțin timp? Acestea sunt SINGURII pași:**

```
1. Instalează Android Studio
   ↓
2. Deschide folderul Stereo70Navigator
   ↓
3. Așteaptă Gradle Sync
   ↓
4. Build → Build APK
   ↓
5. Copiază app-debug.apk pe telefon
   ↓
6. Instalează
   ↓
7. DONE! 🎉
```

**Restul sunt detalii!**

---

**Pentru ghid complet cu imagini și explicații:** [START_AICI.md](START_AICI.md)

**Pentru build în cloud fără instalări:** [GITHUB_BUILD.md](GITHUB_BUILD.md)
