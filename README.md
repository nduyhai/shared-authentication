# shared-authentication
Auto config for multiple issuer oauth2


## Setup docker

```bash 

docker-compose up
```

## Setup Keycloak

1. Login Keycloak admin console: http://localhost:8080/ with user/pass inside ```docker-compose.yml```
2. Create new user for each realm (ex: user:user-2, pwd: 2)
3. Retrieve access token for each user

```bash

curl --location 'localhost:8080/realms/merchant-auth/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=admin-cli \
--data-urlencode 'username=user-2' \
--data-urlencode 'password=2' \
--data-urlencode 'grant_type=password'
```
4. Test api with access token

```bash

curl --location 'localhost:8081/greeting' \
--header 'Authorization: Bearer <access_token>'
```