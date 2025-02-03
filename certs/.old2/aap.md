# Get the service account token
TOKEN=$(kubectl get secret $(kubectl get serviceaccount your-service-account -n your-namespace -o jsonpath='{.secrets[0].name}') -n your-namespace -o jsonpath='{.data.token}' | base64 --decode)

# Test the endpoint
curl -H "Authorization: Bearer $TOKEN" https://your-service/api/secure





TOKEN=$(kubectl get secret $(kubectl get serviceaccount graxon-core -n graxon -o jsonpath='{.metadata.name}') -n graxon -o jsonpath='{.data.token}' | base64 --decode)




kubectl create token graxon-core -n graxon



kubectl get --raw /openid/v1/jwks


kubectl get --raw /.well-known/openid-configuration