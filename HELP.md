# Getting Started

To use api through postman import these curl -


-- For Signin

curl --location --request GET 'http://localhost:8082/api/auth/signin' \
--header 'Content-Type: application/json' \
--data '{"username":"ram",
"password":"123456"}'



-- For Signup

curl --location 'http://localhost:8082/api/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{"username":"ram",
"password":"123456",
"email":"ram@gmail.com",
"role":["USER"] }'


-- For getting default all request url -

curl --location 'http://localhost:8082/api/user/'


-- For getting authorized api data

curl --location 'http://localhost:8082/api/user/all' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYmMiLCJpYXQiOjE3MDYwOTczMTQsImV4cCI6MTcwNjA5NzM3NH0.Lw1Vvk9rkr--HTdAmv8OoluEPb7ahmS7diaJu21a5PKQxL71xQreyUbmsZST-iYGa3Ih3Jrn3X25-cKPZYKXmA'

other urls -
/all
/profile
/allProfile