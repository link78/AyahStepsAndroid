# Launcher Icons Documentation

## Overview
This document describes the launcher icons for the DeenLearn Android app.

## Icon Files

The app includes launcher icons for all standard Android screen densities:

| Density | Size | Files |
|---------|------|-------|
| mdpi | 48x48 | ic_launcher.png, ic_launcher_round.png |
| hdpi | 72x72 | ic_launcher.png, ic_launcher_round.png |
| xhdpi | 96x96 | ic_launcher.png, ic_launcher_round.png |
| xxhdpi | 144x144 | ic_launcher.png, ic_launcher_round.png |
| xxxhdpi | 192x192 | ic_launcher.png, ic_launcher_round.png |

## Design

The current launcher icons are **placeholder designs** featuring:
- **Background**: Green color (#4CAF50) - representing Islamic theme and learning
- **Design Element**: Letter "D" for DeenLearn in white
- **Shape**: Square design for regular icon, circular for round icon
- **Style**: Simple, modern, and recognizable

## Location

Icons are located in:
```
app/src/main/res/
├── mipmap-mdpi/
│   ├── ic_launcher.png
│   └── ic_launcher_round.png
├── mipmap-hdpi/
│   ├── ic_launcher.png
│   └── ic_launcher_round.png
├── mipmap-xhdpi/
│   ├── ic_launcher.png
│   └── ic_launcher_round.png
├── mipmap-xxhdpi/
│   ├── ic_launcher.png
│   └── ic_launcher_round.png
└── mipmap-xxxhdpi/
    ├── ic_launcher.png
    └── ic_launcher_round.png
```

## Usage

The icons are referenced in `AndroidManifest.xml`:

```xml
<application
    android:icon="@mipmap/ic_launcher"
    android:roundIcon="@mipmap/ic_launcher_round"
    ...>
```

## Customization

To replace these placeholder icons with custom designs:

### Option 1: Using Android Studio
1. Right-click on `res` folder
2. Select **New → Image Asset**
3. Choose **Launcher Icons (Adaptive and Legacy)**
4. Upload your icon image or create one using the built-in tools
5. Click **Next** and **Finish**

### Option 2: Manual Replacement
1. Create icon images in the correct sizes for each density
2. Replace the PNG files in each `mipmap-*` directory
3. Ensure files are named `ic_launcher.png` and `ic_launcher_round.png`

### Option 3: Using Python Script
Run the icon generation script from the project root:
```bash
python3 scripts/generate_launcher_icons.py --input your_icon.png
```

## Best Practices

1. **Size**: Always provide icons for all densities (mdpi through xxxhdpi)
2. **Format**: Use PNG format with transparency support
3. **Design**: Follow [Android's icon design guidelines](https://developer.android.com/guide/practices/ui_guidelines/icon_design_launcher)
4. **Testing**: Test icons on different Android versions and screen sizes
5. **Branding**: Icons should be recognizable and represent the app's purpose

## Adaptive Icons (Future Enhancement)

Consider implementing adaptive icons for Android 8.0+ (API 26+):
- Create foreground and background layers
- Support dynamic shapes (circle, squircle, rounded square, square)
- Better integration with device themes

## Color Scheme

Current placeholder colors:
- Primary Green: #4CAF50
- Dark Green: #2E7D32
- Darker Green: #1B5E20
- Light Green: #66BB6A
- Text: White (#FFFFFF)

These colors align with the app's Islamic learning theme and can be updated to match the final branding.

## File Generation

The current icons were generated using Python PIL (Pillow) library with the following specifications:
- Vector-based design for scalability
- Optimized PNG compression
- Consistent color scheme across all densities
- Clean, professional appearance

---

**Note**: These are placeholder icons. Please replace them with professionally designed icons that match the DeenLearn brand identity before final release.
