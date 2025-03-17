[![asaayuti](https://circleci.com/gh/asaayuti/movies-app.svg?style=svg)](https://circleci.com/gh/asaayuti/movies-app)

# Clean Architecture Movies App

### Overview

Movie App is an Android application that displays a list of movies, movie details, and a favorites list. It is designed with a modular architecture and implements Clean Architecture principles to clearly separate business logic, data, and presentation layers.

Movie data is fetched from an API and stored locally in a database for offline access. Users can mark movies as favorites, which are securely saved in the database.

The app uses reactive programming for responsive data flow and dynamic UI. Navigation between screens is smooth using Navigation Component. The app is optimized for performance, free from memory leaks, and secured through encryption, certificate pinning, and obfuscation.

<!-- ### App Preview  
![Movie App](https://strapi.dhiwise.com/uploads/jusplay_movie_streaming_app_flutter_2_3dfd9cbbfc.jpg) -->

##  Tech Stack

- **Language**: Kotlin  
- **Architecture**: Clean Architecture (Presentation, Domain, Data)  
- **Modularization**: 
  - Core module (Android Library)  
  - Favorite feature (Dynamic Feature Module)  
- **Reactive Programming**: RxJava (data flow + UI updates)  
- **Dependency Injection**: Dagger Hilt  
- **Navigation**: Jetpack Navigation Component  
- **Networking**: Retrofit + OkHttp  
- **JSON Parsing**: Gson  
- **Image Loading**: Glide  
- **Local Database**: Room (with SQLCipher encryption)  
- **Security**:
  - Certificate Pinning (OkHttp)  
  - Code Obfuscation (ProGuard/R8)  
- **UI**: XML Layouts, ViewModel + RxJava  
- **Performance**: LeakCanary, Code inspection
- **Extra Feature**: Search (RxJava)


### Before run app. Add this parameter on local.properties and fill.
HOSTNAME=api.themoviedb.org  
BASE_URL=https://api.themoviedb.org/3/  
IMAGE_URL=https://image.tmdb.org/t/p/w500  
API_KEY=  
CERTIFICATE_KEY=  
