___ Register: Create USER all info for account but no account id
    JSON includes username and password
    endpoint POST localhost:8080/register
    return code 200 for good with a JSON of the account with the account id
    
    return code 400 if fail must check if username does not exist and if password is 4 characters long

___ Login Send the username and password to
    POST localhost:8080/login
    successful if the username and password match return code 200
    fail return 401

