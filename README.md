[![asaayuti](https://circleci.com/gh/asaayuti/movies-app.svg?style=svg)](https://circleci.com/gh/asaayuti/movies-app)

# Clean Architecture Movies App

### Overview

Shows list of movies is fetched from an API and stored locally in a database for offline access.
Users can mark movies as favorites and search movies.

### Preview
<table align="start">
  <tr>
    <td align="center">
      <img src="preview/welcome.jpg" alt="Welcome" width="200" height="400"/><br/>
      <b>Welcome</b>
    </td>
    <td align="center">
      <img src="preview/home.jpg" alt="Home" width="200" height="400"/><br/>
      <b>Home</b>
    </td>
    <td align="center">
      <img src="preview/detail.jpg" alt="Detail" width="200" height="400"/><br/>
      <b>Detail</b>
    </td>
    <td align="center">
      <img src="preview/favorite.jpg" alt="Favorite" width="200" height="400"/><br/>
      <b>Favorite</b>
    </td>
  </tr>
</table>



## Tech Stack

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
