# ThrwAt-URL-Shortener
Short any URL right within your Android app. This lightweight library uses powerful ThrwAt services to short and manages URLs. Links can be password protected and shows variety of stats to keep track of each user visiting your link.

# Get Started

Add it in your root build.gradle at the end of repositories
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add the dependency
```
dependencies {
	        implementation 'com.github.basusingh:ThrwAt-URL-Shortener:VERSION'
	}
```

Current version:

[![Current Version](https://jitpack.io/v/basusingh/ThrwAt-URL-Shortener.svg)](https://jitpack.io/#basusingh/ThrwAt-URL-Shortener)

Register and generate User ID and API Key
```
@params
NAME = Enter a name to identify you or your app
EMAIL = Provide your email id. Make sure to use a valid email.
PASSWORD = Enter a password to use. If you have registered before, enter the same password. You can reset it, if forgotten. 
ThrwAt.getInstance(getApplicationContext()).registerUser("NAME", "EMAIL", "PASSWORD", new onSignupCompleteListener() {
            @Override
            public void onComplete(ThrwAtTask task) {
                if(task.isSuccessful()){
                    Log.e("User ID:", task.getUserId());
                    Log.e("API Key", task.getApiKey());
                } else {
                    Log.e("Error:", task.getMessage());
                }
            }
        });
```
# Shortening
Short a long URL
(Note: if the URL doesn't have a protocol (http or https), the library will automatically add http to your URL)
```

@params
LONG_URL = Enter the long URL to short

ThrwAtShortener.getInstance(getApplicationContext()).createShortURL("LONG_URL", new onUrlShortListener() {
            @Override
            public void onComplete(ThrwAtShortenerTask object) {
                if(object.isSuccessful()){
                    Log.e("Shorten URL:", "thrw.at/" + object.getTinyUrl());
                    Log.e("Long URL:", object.getUrl());
                    Log.e("URL ID:", object.getUrlId());
                } else {
                    Log.e("Error Message:", object.getMessage());
                }
            }
        });
```
Update custom name
```
@params
URL_ID = URL Id of the shorten URL to update custom name
LONG_URL = The original URL
CUSTOM = Custom name to update

ThrwAtShortener.getInstance(getApplicationContext()).updateCustomShortURL("URL_ID", "LONG_URL", "CUSTOM", new onUrlShortListener() {
                    @Override
                    public void onComplete(ThrwAtShortenerTask task) {
                        if(task.isSuccessful()){
                            Log.e("New short URL", "thrw.at/" + task.getTinyUrl());
                        } else {
                            Log.e("Error Message:", task.getMessage());
                        }
                    }
                });
```
# Managing URLs
Get all URLs
```
//All items are returned in descending order unless specified

//Get all URLs
List<URLItems>getAllShortenURLS()

//Get {count} number of URLs
List<URLItems>getShortenURLS(int count)

//Get all URLs in ascending order
List<URLItems> getAllShortenURLSAscending()

Get {count} number of URLs in ascending order
List<URLItems> getShortenURLSAscending(int count)

//Get all shorten URLs in live data
LiveData<List<URLItems>> getAllShortenURLSLive()

//Get {count} shorten URLs in live data
LiveData<List<URLItems>> getShortenURLSLive(int count)

//Get {count} shorten URLs in live data in ascending order
LiveData<List<URLItems>> getShortenURLSLiveAscending(int count)
```

URLItems class
(This class is returned whenever you request your URLs)
```
//URLItems variables

//Unique ID of your URL
public String urlId;

//Long URL
public String url;

//Shorten URL
public String tinyUrl;

//Timestamp of the shorten URL
public String timestamp;

//Tags of URL (if any)
public String tags;

//Password protected status of URL (yes or no)
public String urlProtected;
```
