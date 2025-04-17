# Avempace Video Conferencing App 🎥💬

A professional, secure Android app for real‑time video conferencing and chat, built with WebRTC (via Jitsi Meet SDK) and Firebase Cloud Messaging.

---

## 🚀 Key Features

- **🔐 Authentication**  
  Register & log in with email/password; JWT tokens stored securely in SharedPreferences.

- **👥 User Directory**  
  Browse online users, start one‑on‑one calls with a tap, or initiate group calls via multi‑select.

- **📹 High‑Quality Video Calls**  
  Powered by Jitsi Meet SDK (WebRTC) with customizable server URL (`https://meet.jit.si` by default).

- **🔔 Push Notifications**  
  Incoming‑call alerts delivered via Firebase Cloud Messaging; handled by a custom FCM service and EventBus.

- **🗄️ Background Jobs**  
  WorkManager stub for handling any long‑running tasks or data sync in the background.

- **🛠️ Utilities & Tools**  
  - Retrofit 2 + Gson for networking  
  - Timber for comprehensive logging  
  - SweetAlertDialog for polished dialogs  
  - Centralized API response wrapper (`ApiResponse<T>`)  

---

## 🎯 Getting Started

### 🔧 Prerequisites

- Android Studio Arctic Fox or newer  
- Android SDK API level 21+  
- Java 8 or Kotlin support  
- A running backend REST API  
- Firebase project with Cloud Messaging enabled
## Getting Started

### Open in Android Studio
- Import the Gradle project.  
- Place your `google‑services.json` in the `app/` directory.

### Configure APIs
- In `ServiceBuilder.java`, set `BASE_URL` to your backend.  
- Verify endpoints in `ApiService.java`.

### Sync & Build
- Sync Gradle to download dependencies.  
- Build the project (**Build → Make Project**).

---

## 🚀 Running the App
1. Launch an emulator or connect a device (with Google Play services).  
2. Press **Run ▶️** in Android Studio.  
3. The splash screen appears, then prompts for login or registration.

---

## 📖 Usage

### Sign Up / Log In
- Create a new account or authenticate with existing credentials.

### Home Screen
- **Tap** a user to start a one‑on‑one call.  
- **Long‑press** to multi‑select users, then tap the group‑call button.

### Incoming Calls
- Receive a push notification dialog.  
- **Accept** to join the conference or **Decline** to dismiss.

---

## 🔧 Customization

### Jitsi Server
Change the URL in `CallFragment.java`:
```java
new URL("https://your.jitsi.server");
```
### API Endpoints
Update `@GET` / `@POST` annotations in `ApiService.java` to match your backend routes.

### Notifications
Tweak `sendNotification(`) in ` MyFirebaseMessagingService.java ` to customize channel, icon, or sound.

### Storage
Replace `SharedPreferenceHelper` with `DataStore` or `EncryptedSharedPreferences` if desired.

### 🤝 Contributing
Fork this repository.

1. Create a branch:

```
git checkout -b feature/YourFeature
```
2. Commit your changes & push to your fork.

3. Open a Pull Request against main.



