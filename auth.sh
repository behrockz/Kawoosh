ASTRA_CLUSTER_ID=6ec8bebd-96f5-4cf5-983b-47eed9b3cbc0
ASTRA_CLUSTER_REGION=europe-west1
ASTRA_DB_USERNAME=kawoosh
ASTRA_DB_PASSWORD=kawoosh

curl --request POST \
  --url https://${ASTRA_CLUSTER_ID}-${ASTRA_CLUSTER_REGION}.apps.astra.datastax.com/api/rest/v1/auth \
  --header 'accept: */*' \
  --header 'content-type: application/json' \
  --header 'x-cassandra-request-id: {unique-UUID}' \
  --data '{"username":"'"${ASTRA_DB_USERNAME}"'", "password":"'"${ASTRA_DB_PASSWORD}"'"}'
