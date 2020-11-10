# ThrwAt-URL-Shortener
Short any URL right within your Android app. This lightweight library uses powerful ThrwAt services to short and manages URLs. Links can be password protected and shows variety of stats to keep track of each user visiting your link.

## Installation

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
## Shortening
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
## Managing URLs
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
Force sync your URLs from the server
```
ThrwAtURLManager.getInstance(getApplicationContext()).doForceSyncFromServer();
```
Password protect your URL
(Add or update the password to protect your URLs)
```
@params
URL_ID = URL id of the shorten URL to add or update password
PASSWORD = Password to set for the URL

ThrwAtURLManager.getInstance(getApplicationContext()).updatePassword("URL_ID", "PASSWORD", new onURLPasswordUpdateListener() {
                    @Override
                    public void onComplete(ThrwAtURLPasswordUpdateTask task) {
                        if(task.isSuccessful()){
                            Log.e("Success message:", task.getMessage());
                        } else {
                            Log.e("Error message:", task.getMessage());
                        }
                    }
                });
```
Remove password protection from URL
```
@params
URL_ID = Url ID of the shorten url

ThrwAtURLManager.getInstance(getApplicationContext()).removePassword("URL_ID", new onURLPasswordRemoveListener() {
                    @Override
                    public void onComplete(ThrwAtURLPasswordRemoveListener task) {
                        if(task.isSuccessful()){
                            Log.e("Success message:", task.getMessage());
                        } else {
                            Log.e("Error message:", task.getMessage());
                        }
                    }
                });
```
Add tags to URL
(An easy way to organize your URLs using tags)
```
@params
URL_ID = URL id of the shorten URL to add tag
YOUR_TAG = Your custom tag

//Adding a tag must be done on a background thread.
//You can declare your own logic to run the task on a background thread.
//This AsyncTask will leak memory
new AsyncTask<Void, Void, Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        ThrwAtURLManager.getInstance(getApplicationContext()).addTag("URL_ID", "YOUR_TAG");
                        return null;
                    }
                }.execute();
```
Get all tags of an URL
(All tags are stored as JSON inside a single String variable. We provided the option to parse tags by default. See example.)
```
@params
URL_ID = URL id of the shorten URL to all its tags

//Tags are returned as a String containing JSON file
//A default function is availavle to parse tags
//The tags are returned as an String array
ThrwAtURLManager.getInstance(getApplicationContext()).getAllTagsLive(items.getUrlId()).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String[] mTags = ThrwAtURLManager.getInstance(getApplicationContext()).parseTags(s);
                if(mTags == null || mTags.length == 0){
                    //No tags found for the given URL
                } else {
                    //Tags are stored in mTags variable
                }
            }
        });
```
Remove a tag from an URL
```
@params
URL_ID = URL id of the shorten URL to remove the tag from
TAG = The tag you want to remove

//Removing a tag must be called from the background thread
//This example will leak memory
new AsyncTask<Void, Void, Void>(){
                    @Override
                    protected Void doInBackground(Void... voids) {
                        ThrwAtURLManager.getInstance(getApplicationContext()).removeTag(URL_ID, TAG);
                        return null;
                    }
                }.execute();
```
Search all URLs by a tag
```
@params
TAG = The tag you want to search

//Returns a list of the class URLItems
List<URLItems> mList;
new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                mList = ThrwAtURLManager.getInstance(getApplicationContext()).getAllURLByTags(TAG);
                return null;
            }
        }.execute();
```
Delete an URL
```
@params
URL_ID = URL id of the shorten URL to delete

ThrwAtURLManager.getInstance(getApplicationContext()).deleteURL("URL_ID", new onURLDeleteListener() {
            @Override
            public void onComplete(ThrwAtURLDeleteTask task) {
                Log.e("Message:", task.getMessage());
            }
        });
```
Get total count of visitors
```
@params
URL_ID = URL id of the shorten URL to get total count

ThrwAtURLManager.getInstance(getApplicationContext()).getURLStatsCount(URL_ID, new onURLStatsCountFetchListener() {
            @Override
            public void onComplete(ThrwAtURLStatsCountTask task) {
                if(task.isSuccessful()){
                    Log.e("Total count:", String.valueOf(task.getCount()));
                }
            }
        });
```
Get all stats of an URL
(Check URLStatsItems class below for the stats variable available)
```
@params
URL_ID = URL id of the shorten Url to get stats

//Check URLStatsItems class for each stats variable available
List<URLStatsItems> mStatsList;
ThrwAtURLManager.getInstance(getApplicationContext()).getURLStats("URL_ID", new onURLStatsFetchCompleteListener() {
                      @Override
                      public void onComplete(ThrwAtURLStatsFetchTask task) {
                          if(task.isSuccessful()){
                              mStatsList = task.getURLStats();
                          } else {
                              Log.e("Error message:", task.getMessage());
                          }
                      }
                  });
```
URLStatsItems Class
(Getter and Setter method also available for each item)
Check for 'null' as a String value for each variable while decoding
```
public class URLStatsItems implements Serializable {

    //Free version
    public String referrer, userAgentName, userRegion, userCountryName, userTimezone, timestamp;

    //Premium version (with everything included in the free version)
    public String stats_id, url_id, userCity, host, port, userAgentVersion, userAgentPlatform, userContinentName, userCurrency;
    
    //Getter and Setter method available for each variable
}
```
[Example decoding premium stats](https://gist.github.com/basusingh/bccf0e5bf9cc25d5dec08af2f4b32a1b)

## Managing User
Get current user (User ID and API Key)
```
@return
Returns an instance of ThrwAtUser

ThrwAtUser user = ThrwAt.getInstance(getApplicationContext()).getCurrentUser();
Log.e("User ID:", user.getUserId());
Log.e("API Key:", user.getApiKey());
```
Get premium status
(Check whether you have premium features or not | [You can request one here](https://developer.thrw.at/premium))
```
ThrwAt.getInstance(getApplicationContext()).getPremiumStatus(new onPremiumStatusFetchListener() {
            @Override
            public void onComplete(ThrwAtPremiumStatusTask task) {
                if(task.isSuccessful()){
                    Log.e("Premium Status", task.getPremium());
                    //Returns a 'yes' or 'no'
                } else {
                    Log.e("Error message:", task.getMessage());
                }
            }
        });
```
Reset Password
(If you have forgotten your password, you can reset your password using this function)
```
@params
EMAIL = Your email id

ThrwAt.getInstance(getApplicationContext()).resetPassword("EMAIL", new onPasswordResetCompleteListener(){
                    @Override
                    public void onComplete(ThrwAtPasswordResetTask task) {
                        Log.e("Response", task.getMessage());
                    }
                });
```


## Miscellaneous
ThrwAt.class
```
//Check if you are registered or not
public boolean isRegistered()

//Get your API key
public String getApiKey()

//Get your User ID
public String getUserId()
    
//Logout yourself
public void logoutUser()

//Set automatic server sync enabled or disabled
public void setServerSyncEnabled(boolean val)

//Check server auto sync status
public boolean getServerSyncStatus()

//Set interval for automatic server sync
//Default value is 5 hours
public void setServerSyncInterval(int hour)

//Get automatic server sync interval
public int getServerSyncInterval()
```
ThrwAtURLManager.java
```
//Delete all URLs locally
public void deleteAll()

//Parse tags that are stored as JSON
public String[] parseTags(String tags)

//Get a String URL for QR Code
public String getQRCodeURL(String urlId)
```
