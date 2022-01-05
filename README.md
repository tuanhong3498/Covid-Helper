# Covid-Helper

This project is part of the assignment for the SWE401 Embedded programming course in Xiamen University Malaysia. 

The main idea of this application is to help, help the government to manage the pandemic and help the general public to get the latest and accurate information about the pandemic. Besides that, we also aim to provide some improvement over the MySejahtera app – Malaysia version of “Covid-19 mobile application”. 

## Instruction to experience different use cases
### Vaccination 
There are multiple users accounts that are created, each with different vaccination stage. The available valid user accounts are shown in the table below. Besides the vaccination tool, the vaccination status will also be reflected in the check-in page. The digital vaccine certificate will also be displayed in the profile page if it has been issued to the user. 
|User IC number|Password|Vaccination stage                                  |
|--------------|--------|---------------------------------------------------|
|1             |1234    |Registered                                         |
|2             |1234    |Received dose 1 appointment                        |
|3             |1234    |Received dose 2 appointment                        |
|4             |1234    |Received 2 dose, waiting for the digital certifical|
|5             |1234    |Fully vaccinated                                   |

### Hotspot
To view the effect of the hotspot feature, please visit the following location (via the search box or set virtual location using emulator) as dummy data is only created in these locations:
* Xiamen University Malaysia
* KLCC
*The feature relies on paid service provided by Google, the feature may stop to work after the free trail end.* 

## Current issues

Currently, the application still have some issues that are yet to be fixed.

### Wrong password on login
The application may report the user has entered the wrong password or username when the user sign in for the first time after installation (or after clearing the data of the application). This is bacause the database is yet to be created.  
Work around: sign in again with the correct username and password

### The main activity may sometime crach (not the entire application)
This is cause by the observer of the live data is not correctly detached or destroied. The developers are still investigating the problem. 


This project is publicly available on GitHub: https://github.com/tuanhong3498/Covid-Helper
