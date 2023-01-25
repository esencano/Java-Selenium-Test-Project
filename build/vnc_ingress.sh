# Get the list of pod names
POD_NAMES=$(kubectl get pods -l app=chrome-driver -o jsonpath={.items[*].metadata.name})

# Loop through the list of pod names
for POD_NAME in $POD_NAMES; do
   POD_NAME_SUBSTRING=$(echo $POD_NAME | sed 's/^chrome-driver-deployment-//')
   kubectl label pods $POD_NAME pod-hash=$POD_NAME_SUBSTRING --overwrite
   SERVICE_NAME=chrome-driver-pod-$POD_NAME_SUBSTRING-vnc-service
   INGRESS_NAME=chrome-driver-pod-$POD_NAME_SUBSTRING-vnc-ingress
  # Create a new service for the pod
  cat <<EOF | kubectl apply -f -
apiVersion: v1
kind: Service
metadata:
  name: $SERVICE_NAME
spec:
  selector:
    app: chrome-driver
    pod-hash: $POD_NAME_SUBSTRING
  ports:
  - name: http
    port: 5900
    targetPort: 5900
  type: ClusterIP

---

apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: $INGRESS_NAME
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
    kubernetes.io/ingress.class: "nginx"
spec:
  rules:
  - host: selenium.ercansencanoglu.com
    http:
      paths:
      - path: /driver/chrome/$POD_NAME_SUBSTRING
        pathType: Prefix
        backend:
          service:
            name: $SERVICE_NAME
            port: 
               number: 5900
EOF
done



