# GMBN Youtube Channel

### Minimun Project Requirements 
* Android Studio 4.0 or higher
* Minimum android SDK version 22
* Java version 1.8
* Kotlin version 1.3.7 or higher

### App Installation
* Clone Repository using Android Studio
* Android Studio will to change your android sdk path to your local machine since obviously it would 
 not be able to fine mine. 
* Make sure you are on the master branch and allow allow android studio to finish building.
* (Optional) Replace the youtube API_KEY in app/src/main/java/com/ijikod/gmbn_youtube/data/remote/HttpInstance.kt with 
yours. Also the app has offline support, this will be necessary if you want to load more data from the remote save which has not been
saved locally yet.

### Api Documantation
* [Search Api](https://developers.google.com/youtube/v3/docs/videos) To list vidoes from channel
* [Comments Api](https://developers.google.com/youtube/v3/docs/commentThreads) To get list of vidoe comments authurs
* [Details Api](https://developers.google.com/youtube/v3/docs/videos) To fetch video details ie. duration

### App Features
* Recommended [App Architecture](https://developer.android.com/jetpack/guide)for android development with repository patten.
* Displaying paginated data with new [Android Paging](https://developer.android.com/topic/libraries/architecture/paging) 
* Implementation of [Android Navigation library](https://developer.android.com/guide/navigation))
* [Data binding](https://developer.android.com/jetpack/androidx/releases/databinding) of components in layouts and data sources. 
* Offline support with as data is saved to sql light database using [ROOM](https://developer.android.com/topic/libraries/architecture/room)


## Screenshots
 <table>
  <tr>
    <td><img src="/Images/list.png" width=250 height=480></td>
    <td><img src="/Images/details.png" width=250 height=480></td>
  </tr>
  <tr>
    <td><img src="/Images/more_details.png" width=250 height=480></td>
    <td><img src="/Images/details_land.png" width=500 height=250></td>
  </tr>
 </table>

 


