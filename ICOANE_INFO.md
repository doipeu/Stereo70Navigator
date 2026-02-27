# Informații despre Iconița Aplicației

## 📌 Notă Importantă

Momentan aplicația va folosi iconița default Android (robotul verde).

Pentru a adăuga o iconiță personalizată, ai două opțiuni:

---

## Opțiunea 1: Folosește Asset Studio (Recomandat) ⭐

### Pași în Android Studio:

1. **Click dreapta** pe `app` → **New** → **Image Asset**

2. În fereastră:
   - **Icon Type:** Launcher Icons (Adaptive and Legacy)
   - **Name:** ic_launcher

3. În **Foreground Layer:**
   - **Source Asset:**
     - **Clip Art:** Alege o iconiță (ex: hartă, locație, busolă)
     - SAU **Image:** Încarcă propria ta imagine PNG
   - Ajustează **Resize:** 50-80% pentru margini frumoase
   - Alege culoare fundal în **Color**

4. Click **Next** → **Finish**

5. Android Studio va genera automat toate dimensiunile:
   - mipmap-mdpi (48x48)
   - mipmap-hdpi (72x72)
   - mipmap-xhdpi (96x96)
   - mipmap-xxhdpi (144x144)
   - mipmap-xxxhdpi (192x192)

---

## Opțiunea 2: Manual (Pentru Control Total)

### Cerințe Dimensiuni:

Creează fișiere `ic_launcher.png` în următoarele dimensiuni:

```
app/src/main/res/
├── mipmap-mdpi/ic_launcher.png      (48x48 px)
├── mipmap-hdpi/ic_launcher.png      (72x72 px)
├── mipmap-xhdpi/ic_launcher.png     (96x96 px)
├── mipmap-xxhdpi/ic_launcher.png    (144x144 px)
└── mipmap-xxxhdpi/ic_launcher.png   (192x192 px)
```

### Tools pentru Creare Iconiță:

1. **Online (Gratis):**
   - https://icon.kitchen/ - cel mai simplu
   - https://romannurik.github.io/AndroidAssetStudio/
   - https://appicon.co/

2. **Software Desktop:**
   - GIMP (gratis)
   - Photoshop
   - Inkscape (vector, gratis)

### Sfaturi Design:

- ✅ Folosește imagini simple, clare
- ✅ Culori contrastate
- ✅ Vizibilă pe fundal deschis și întunecat
- ✅ Iconiță reprezentativă (hartă, busolă, pin locație)
- ❌ Evită text prea mic
- ❌ Evită detalii fine (se pierd la dimensiuni mici)

---

## Sugestii Iconiță pentru Stereo70 Navigator:

### Concepte vizuale:

1. **Busolă + Hartă** 🧭
   - Simbolizează navigație și orientare

2. **Pin Locație + Coordonate** 📍
   - Sugerează puncte pe hartă

3. **Grid + Pin** 🗺️
   - Reprezintă sistemul de coordonate

4. **Busolă cu litera "S70"**
   - Brand specific pentru Stereo 70

### Paleta de culori sugerată:

```
Albastru navigație: #2196F3 (folosit în app)
Portocaliu accent:  #FF5722 (folosit în app)
Verde GPS:          #4CAF50
Gri închis:         #424242
```

---

## Verificare Iconiță

După adăugare, pentru a vedea iconița nouă:

1. **În Android Studio:** Rebuild project
2. **Pe telefon:**
   - Dezinstalează aplicația veche
   - Reinstalează noua versiune
   - Sau: Setări → Apps → Stereo70 Navigator → Clear cache

---

## Resurse Gratuite pentru Iconiță

### Iconiță Gata Făcută:

**Material Design Icons:**
- https://fonts.google.com/icons
- Caută: "map", "navigation", "location_on", "explore"

**Flaticon:**
- https://www.flaticon.com/
- Caută: "compass", "gps", "coordinates"

### Tutoriale:

- **Android Developers - Icon Design:**
  https://developer.android.com/guide/practices/ui_guidelines/icon_design_launcher

- **Material Design Guidelines:**
  https://material.io/design/iconography

---

## Exemplu Rapid: Iconița cu Clip Art

Dacă vrei ceva rapid și arătos:

1. Asset Studio → Clip Art → Caută "map" sau "explore"
2. Culoare fundal: #2196F3 (albastru)
3. Foreground: #FFFFFF (alb)
4. Resize: 60%
5. Shape: Circle sau Rounded Square
6. Click Finish!

---

**Notă:** Iconița este opțională și nu afectează funcționalitatea aplicației!
