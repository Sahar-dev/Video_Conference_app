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

### 🛠️ Installation

1. **Clone the repo**  
   ```bash
   git clone https://github.com/your‑org/avempace‑video‑chat.git
   cd avempace‑video‑chat
