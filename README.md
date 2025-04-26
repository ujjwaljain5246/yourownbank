# YourOwnBank
YourOwnBank is a secure Android banking app built with Kotlin, Jetpack Compose, MVVM, and Coroutines. It features login, bank setup, transactions, validations, and seamless navigation with Retrofit, OkHttp, Gson, and Parcelable models for smooth, modern user experiences.


## Features

- **User Authentication:** Allows users to securely sign up and log in.
- **Account Management:** Enables users to create new accounts and delete existing ones.
- **Balance Retrieval:** Provides users with the ability to fetch and view their current account balance.
- **Fund Transfer:** 
  - Transfer funds via **UPI ID**.
  - Transfer using **Bank Account & IFSC Code**.
  - Transfer by **scanning a valid YOB QR Code**.
- **Transaction History:** Displays a complete history of past transactions for users to review.
- **Logout:** Encourages users to log out before exiting the application to maintain account security.


## Dependencies Used

- **Core Libraries**
  - androidx.core:core-ktx
  - androidx.lifecycle:lifecycle-runtime-ktx
  - androidx.activity:activity-compose

- **UI & Design**
  - androidx.compose.ui:ui
  - androidx.compose.ui:ui-graphics
  - androidx.compose.material3:material3-android
  - androidx.compose.ui:ui-tooling-preview
  - androidx.compose.ui:ui-tooling (for debug)
  - com.airbnb.android:lottie-compose (for animations)

- **Navigation**
  - androidx.navigation:navigation-compose

- **Architecture**
  - MVVM Pattern (ViewModel, LiveData)
  - androidx.lifecycle:lifecycle-viewmodel-ktx
  - androidx.lifecycle:lifecycle-livedata-ktx
  - androidx.compose.runtime:runtime-livedata

- **Networking**
  - com.squareup.retrofit2:retrofit
  - com.squareup.retrofit2:converter-gson
  - com.squareup.okhttp3:logging-interceptor
  - com.google.code.gson:gson

- **Concurrency**
  - org.jetbrains.kotlinx:kotlinx-coroutines-core
  - org.jetbrains.kotlinx:kotlinx-coroutines-android

- **QR Code (Generating & Scanning)**
  - com.journeyapps:zxing-android-embedded
  - com.google.zxing:core

- **Camera Access (for QR Scanning)**
  - androidx.camera:camera-core
  - androidx.camera:camera-camera2
  - androidx.camera:camera-lifecycle
  - androidx.camera:camera-view



## Tech Stack

- **Language:** Kotlin
- **UI Design:** Jetpack Compose
- **Architecture:** MVVM (Model-View-ViewModel)
- **Navigation:** Navigation Compose
- **UI Components:** Screens, LazyColumns, TextFields, Buttons
- **Networking:** Retrofit (REST API integration)
- **Data Handling:** Coroutines, Flow
- **Local Storage (Optional):** SharedPreferences / DataStore (for storing minimal user data)
- **Version Control:** Git & GitHub
- **API Source:** YourOwnBank API


## How to Run the Projet
1. Clone the repository
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
5. Build and run the project on an emulator or physical device.


## Screenshots
Sensitive information is hidden from screenshots for security purposes.

<div align="center">
    <table>
        <tr>
            <td align="center">
                <img src="https://github.com/user-attachments/assets/e382e99d-3193-4c90-89c1-be2486ea6ce7" alt="YOB Splash Screen" width="200"/>
                <p><em>YOB Splash Screen</em></p>
            </td>
            <td align="center">
                <img src="https://github.com/user-attachments/assets/19f0fe4b-e76f-45cc-ad03-4a0724f66631" alt="SingIn Screen" width="200"/>
                <p><em>SignIn Screen</em></p>
            </td>
            <td align="center">
                <img src="https://github.com/user-attachments/assets/4ccb92bb-24f6-4b6b-af6d-235aa5255858" alt="Delete User Screen" width="200"/>
                <p><em>Delete User Screen</em></p>
            </td>
        </tr>
    </table>
   <table>
        <tr>
            <td align="center">
                <img src="https://github.com/user-attachments/assets/076b4b95-3b45-418e-8900-6a063519a8aa" alt="Home I Screen" width="200"/>
              <img src="https://github.com/user-attachments/assets/519910ba-a2a3-4299-906e-cae4dc05209a" alt="Home II Screen" width="200"/>
                <p><em>Home Screen</em></p>
            </td>
            <td align="center">
                <img src="https://github.com/user-attachments/assets/e1fde4e1-3080-4a5b-97e5-b6bb7f2997a4" alt="SignUP I Screen" width="200"/>
              <img src="https://github.com/user-attachments/assets/1de8c5f6-9c38-4a8b-b990-51bbf53a5c0f" alt="SignUP II Screen" width="200"/>
                <p><em>Send Money Screen</em></p>
            </td>
        </tr>
    </table>
     <table>
        <tr>
            <td align="center">
                <img src="https://github.com/user-attachments/assets/56e7f9ac-ce59-4f4f-95a3-c09c4e17c479" alt="YOB QR Code Screen" width="200"/>
                <p><em>YOB QR Code Screen</em></p>
            </td>
           <td align="center">
                <img src="https://github.com/user-attachments/assets/468a6938-371d-4047-b57d-333302e099ff" alt="Send Money Screen" width="200"/>
                <p><em>Send Money Screen</em></p>
            </td>
            <td align="center">
                <img src="https://github.com/user-attachments/assets/a7fed860-15af-441f-87d2-b036e2bb0854" alt="Transaction Screen" width="200"/>
                <p><em>Transaction Screen</em></p>
            </td>
          <td align="center">
                <img src="https://github.com/user-attachments/assets/754c4f59-a98a-416f-b56b-d96dc8c75877" alt="Transaction History Screen" width="200"/>
                <p><em>Transaction History Screen</em></p>
            </td>
            <td align="center">
                <img src="https://github.com/user-attachments/assets/0c53fe85-9075-4c2e-96d8-53336d08717c" alt="Available Balance Screen" width="200"/>
                <p><em>Available Balance Screen</em></p>
            </td>
        </tr>
    </table>
       <table>
        <tr>
            <td align="center">
                <img src="https://github.com/user-attachments/assets/a49edf9f-de22-488a-830d-b3b6a209d82e" alt="User Logout Screen" width="200"/>
                <p><em>User Logout Screen</em></p>
            </td>
           <td align="center">
                <img src="https://github.com/user-attachments/assets/adc8db45-9ac5-4518-839d-7a2cbb5ed257" alt="Loading Screen" width="200"/>
                <p><em>Loading Screen</em></p>
            </td>
    </table>
</div>


