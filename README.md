# Countries List App
This is a world countries demo, built with `XML with data-binding`, `Retrofit`, `Room`, `Glide`, `DataStore`  `Coroutines`, `Livedata`, `Hilt`, and `Material Design` and follows the `MVVM Architecture`. It shows a list of all the world's countries, the ability to search and the country details screen. The code has been uni-tested using `JUnit`, `Mockito` and the `kotlix coroutines` rest libraries.

## API 
```
https://restcountries.com/v3.1/all
```

## Artchitecture
The application uses the MVVM architecture and follows Google’s recommended [guideline](https://developer.android.com/topic/architecture) architecture. The app's overall architecture comprises three layers: the `Presentation Layer`, `Domain Layer`, and `Data Layer`. 
Each of these has different responsibilities. 

![Screenshot 2024-09-18 at 08 58 30](https://github.com/user-attachments/assets/07f95f4f-15db-4061-8b89-39b5de682857)

### PRESENTATION
On the UI Layer, the app uses LiveData for reactive state management. The XML views  observe data updates on the view model, and the view model updates the data that it fetches from the Repository.

![Screenshot 2024-09-18 at 09 12 30](https://github.com/user-attachments/assets/fe433e2c-fb5c-41fd-bae7-2828f14887bd)

### DOMAIN
The Domian layer, currenctly consits of the Repostory, and wrapper classes for handling responses from the data layer. In furture if the app grows, this later could also contain UseCases, as needed.

### DATA
This contains, the network API service calls implented with the assistance of `Retrofit`. It aslo contains the Local Persistance, which was implemented using `Room`. Data is intially fetched from the API, and then persisted on the Database, and cached in the Database for `5 minutes (abitrary minutes assuming that our data gets updates every 5 minutes)`. When the view model requests the data, the Repository implemntations first checks if we have in the database, and if it is less than 5 minutes old, if 5 minutes old, we then get fresh from the API, otherwise we get the cached data from the DB.

![Screenshot 2024-09-18 at 09 23 26](https://github.com/user-attachments/assets/5251c06d-b57e-4b46-997e-473a5d74aed2)

### ILLUSTRATIONS
LIST & DETAIL SCREEN

[List & Detail Screen](https://github.com/user-attachments/assets/faef1e54-2889-460f-8838-5012d9eb7a2e)

OFFLINE MODE

[OFFLINE](https://github.com/user-attachments/assets/f751876d-db0e-4206-86fe-d35676a37225)

PULL TO REFRESH

[pull_to_refresh.webm](https://github.com/user-attachments/assets/fb01c180-e327-4d58-8043-ee0dd09b84da)

ERROR HANDLING

[ERROR HANDLING](https://github.com/user-attachments/assets/310db008-519f-4073-860b-109a7b7a22c8)













