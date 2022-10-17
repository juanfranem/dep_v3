# dep_v3

Create a file called `keystore.properties` in the root, put this configuration inside this file:
````shell
    RELEASE_STORE_FILE=your/key/store/path
    RELEASE_KEY_ALIAS=your_key_alias
    RELEASE_STORE_PASSWORD=store_password
    RELEASE_KEY_PASSWORD=key_password
````

Add this line into your `local.properties`:
````shell
    keystore.props.file=../keytore.properties
````