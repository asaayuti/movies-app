[![asaayuti](https://circleci.com/gh/asaayuti/movies-app.svg?style=svg)](https://circleci.com/gh/asaayuti/movies-app)

# Clean Architecture Movies App

### Before run app. Add this parameter on local.properties and fill.
HOSTNAME=api.themoviedb.org  
BASE_URL=https://api.themoviedb.org/3/  
IMAGE_URL=https://image.tmdb.org/t/p/w500  
API_KEY=  
CERTIFICATE_KEY=  

## 📋 Fitur

### Halaman:
  - List Item
  - Detail Item
  - List Favorite (menggunakan database).

### Modularization
- 1 Android Library untuk **core**.
- 1 Dynamic Feature untuk **favorite**.

### Clean Architecture
- Tidak melanggar dependency rule.
- Model dipisah:
  - **Domain model ≠ Data model** (separation model).

### Dependency Injection
- **Menggunakan Dagger Hilt **.

### Reactive Programming
- **Mengguanakn RxJava**.
- Diterapkan untuk mengambil data dari **network dan database**.

### Fitur Tambahan
- Menambahkan fitur search.
- Reactive Programming untuk fitur lain (misalnya UI).
- Navigasi antar module dengan **Jetpack Navigation Component**.

### Continuous Integration (CI)
- Menggunakan **CircleCI**.

### Performa
- Gunakan **LeakCanary** — tidak ada memory leak.

### Security
- **Obfuscation** dengan ProGuard.
- Database **encrypted**.
- **Certificate pinning** untuk koneksi server.

