# Project-3

### Installing dependencies
1. Install openssl and jdk 25 
2. Configuring Environment Variables
3. Right-click the “Start” button and select “System” from the context menu.
4. In the System window, click “Advanced system settings” on the right-hand side.
5. In the System Properties window, click the “Environment Variables…” button.
6. In the Environment Variables window, under the “System variables” section, select the “Path” variable and click on “Edit…“
7. In the Edit Environment Variable window, click “New” and add the path to the OpenSSL bin directory. The default path is C:\Program Files\OpenSSL\bin. If you chose a different installation directory, adjust the path accordingly.
8. Click “OK” to save the changes and close all the open windows




### Permissions setup guide Windows:
1. Open powershell as administrator and run (press y to accept)
```Set-ExecutionPolicy -Scope Process Bypass```
2. Navigate to your correct folder (remember to input your user)
```cd C:\Users\*your user goes here*\Documents\GitHub\Project-3```
3. run the install file
```.\install.ps1```


### Permissions setup guide Linux:
1. Open the project in your IDE of choice
2. ```make install```


*obs* If using Firefox, you must trust the generated local certificate authority.
1. Open Firefox and go to: about:settings#privacy
2. Find the section for certificates.
3. Under "Authorities", import the generated file: localCA.pem
4. Enable trust when prompted.


### For running the front and backend
1. [Setup Frontend](FRONTENDSETUP.md) _for devs_
2. [Setup Backend](BACKENDSETUP.md) _for devs_
3. [License](LICENSE.md)
