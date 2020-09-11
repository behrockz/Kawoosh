curl --request POST \
  --url https://6f3f9dfc-f2e4-4efe-93f4-c8eae854a379-europe-west1.apps.astra.datastax.com/api/rest/v1/auth \
  --header 'accept: */*' \
  --header 'content-type: application/json' \
  --header 'x-cassandra-request-id: {unique-UUID}' \
  --data '{"username":"'"kawoosh"'", "password":"'"kawoosh"'"}'
