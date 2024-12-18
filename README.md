﻿
# DialogMe

**DialogMe** is a fully customizable Android dialog library that supports both **XML-based projects** and **Jetpack Compose projects**. It provides an easy-to-use API to create visually appealing and interactive dialogs with animations, customizable colors, buttons, and more.

---

## Features

- 🌟 **Customizable Title**: Add titles with icons, custom colors, and animations.
- 💬 **Rich Message Support**: Display messages with alignment and color options.
- 🎨 **Customizable Buttons**: Add up to three buttons with text, icons, colors, and click actions.
- 🖌️ **Background Customization**: Set custom background colors for the dialog.
- 🛠️ **XML and Compose Support**: Use DialogMe in both XML-based and Compose-based Android projects.
- 🎥 **Smooth Animations**: Built-in entrance and exit animations.

---

## Installation

Add the following dependency to your project using **JitPack**:

1. Include JitPack in your root `settings.gradle` file:

   ```gradle
   dependencyResolutionManagement {
       repositories {
           google()
           mavenCentral()
           maven { url 'https://jitpack.io' }
       }
   }
   ```

2. Add the library to your module's `build.gradle` file:

   ```gradle
   dependencies {
       implementation ("com.github.hassanwasfy:DialogMe:1.0.0")
   }
   ```

---

## Usage

### **1. XML-Based Projects**

Create and display a dialog in an XML-based project:

```kotlin
val dialog = DialogMe(this)
    .setTitle("Warning!", R.drawable.ic_warning, textColor = Color.RED)
    .setMessage("Are you sure you want to proceed?", textColor = Color.DKGRAY)
    .setDialogBackgroundColor(Color.LTGRAY)
    .addButton("Yes", R.drawable.ic_check, textColor = Color.WHITE, backgroundColor = Color.GREEN) {
        Toast.makeText(this, "Yes clicked", Toast.LENGTH_SHORT).show()
    }
    .addButton("No", R.drawable.ic_close, textColor = Color.WHITE, backgroundColor = Color.RED) {
        Toast.makeText(this, "No clicked", Toast.LENGTH_SHORT).show()
    }
    .build()

dialog.show()
```

---

### **2. Jetpack Compose Projects**

Use `DialogMe` in a Compose-based project:

```kotlin
@Composable
fun MyComposeScreen() {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        DialogMe(activity = LocalContext.current as Activity)
            .setTitle("Warning!", R.drawable.ic_warning, textColor = Color.Red.toArgb())
            .setMessage("Are you sure you want to proceed?", textColor = Color.DarkGray.toArgb())
            .setDialogBackgroundColor(Color.LightGray.toArgb())
            .addButton("Yes", R.drawable.ic_check, textColor = Color.White.toArgb(), backgroundColor = Color.Green.toArgb()) {
                Toast.makeText(LocalContext.current, "Yes clicked", Toast.LENGTH_SHORT).show()
                showDialog.value = false
            }
            .addButton("No", R.drawable.ic_close, textColor = Color.White.toArgb(), backgroundColor = Color.Red.toArgb()) {
                Toast.makeText(LocalContext.current, "No clicked", Toast.LENGTH_SHORT).show()
                showDialog.value = false
            }
            .ShowComposeDialog { showDialog.value = false }
    }
}
```

---

## Customization

### Title Customization

- **Title Text**: Provide a title string.
- **Title Icon**: Add an optional icon.
- **Title Text Color**: Set a custom text color.

```kotlin
.setTitle("Custom Title", R.drawable.ic_warning, textColor = Color.RED)
```

---

### Message Customization

- **Message Text**: Provide the message string.
- **Message Alignment**: Align text to `START` or `CENTER` (default).
- **Message Text Color**: Set a custom text color.

```kotlin
.setMessage("This is a message.", alignCenter = false, textColor = Color.DKGRAY)
```

---

### Button Customization

- Add up to three buttons with the following options:
    - **Text**: Provide button text.
    - **Icon**: Add an optional icon.
    - **Text Color**: Set a custom text color.
    - **Background Color**: Set a custom background color.
    - **OnClick Action**: Define a click action.

```kotlin
.addButton(
    text = "Confirm",
    iconResId = R.drawable.ic_check,
    textColor = Color.WHITE,
    backgroundColor = Color.GREEN
) {
    // Handle click
}
```

---

### Background Customization

Set the dialog's background color:

```kotlin
.setDialogBackgroundColor(Color.LTGRAY)
```

---

### Animations

DialogMe includes smooth entrance and exit animations by default. Customize animations by modifying the `R.style.DialogMeAnimation` in your styles.

---

## Contributing

Contributions are welcome! Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch (`feature/your-feature-name`).
3. Commit your changes and push to your fork.
4. Open a pull request.

---

## License

DialogMe is distributed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---

## Author

Developed with ❤️ by [Hassan Wasfy](https://github.com/hassanwasfy).
