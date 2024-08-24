# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep specific classes and their members
-keep class com.example.core.data.source.MovieRepository { *; }
-keep class com.example.core.data.source.Resource$Error { *; }
-keep class com.example.core.data.source.Resource$Loading { *; }
-keep class com.example.core.data.source.Resource$Success { *; }
-keep class com.example.core.data.source.Resource { *; }
-keep class com.example.core.data.source.local.LocalDataSource { *; }
-keep class com.example.core.data.source.remote.RemoteDataSource { *; }
-keep class com.example.core.data.source.remote.network.ApiService { *; }
-keep class com.example.core.di.DatabaseModule { *; }
-keep class com.example.core.di.DatabaseModule_ProvideDatabaseFactory { *; }
-keep class com.example.core.di.DatabaseModule_ProvideMovieDaoFactory { *; }
-keep class com.example.core.di.NetworkModule { *; }
-keep class com.example.core.di.NetworkModule_ProvideApiServiceFactory { *; }
-keep class com.example.core.di.NetworkModule_ProvideOkHttpClientFactory { *; }
-keep class com.example.core.domain.model.Movie { *; }
-keep class com.example.core.domain.repository.IMovieRepository { *; }
-keep class com.example.core.domain.usecase.MovieInteractor { *; }
-keep class com.example.core.domain.usecase.MovieUseCase { *; }
-keep class com.example.core.ui.MovieListAdapter { *; }
-keep class com.example.core.utils.AppExecutors { *; }
-keep class com.example.core.utils.UtilsKt { *; }

-keep class com.example.core.di.RepositoryModule { *; }

