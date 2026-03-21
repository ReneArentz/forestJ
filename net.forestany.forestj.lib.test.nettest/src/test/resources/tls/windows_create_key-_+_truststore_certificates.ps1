if (Test-Path server -PathType Container) {
    rm server -r -fo
}

if (Test-Path client -PathType Container) {
    rm client -r -fo
}

if (Test-Path all -PathType Container) {
    rm all -r -fo
}

mkdir server
cd server

#Keystore Server
Write-Host "SERVER KEYSTORE"
keytool -genkey -alias test_server -keyalg RSA -keystore KeyStore-srv.p12 -keysize 4096 -storepass 123456 -dname "CN=forestJava Server, OU=server.forestjava.de, O=forestjava, L=Hagen, S=NRW, C=DE" -deststoretype pkcs12
openssl req -new -x509 -keyout ca-key-srv.pem -out ca-cert-srv.pem -subj "/CN=forestJava Server/OU=server.forestjava.de/O=forestjava/L=Hagen/ST=NRW/C=DE" -passout pass:12345678
keytool -keystore KeyStore-srv.p12 -alias test_server -certreq -file cert-file-srv.csr -storepass 123456
openssl x509 -req -CA ca-cert-srv.pem -CAkey ca-key-srv.pem -in cert-file-srv.csr -out cert-signed-srv.cer -days 365 -CAcreateserial -passin pass:12345678
keytool -keystore KeyStore-srv.p12 -alias CARoot -import -file ca-cert-srv.pem -storepass 123456 -noprompt
keytool -keystore KeyStore-srv.p12 -alias test_server -import -file cert-signed-srv.cer -storepass 123456 -noprompt

cd ..
mkdir client
cd client

#Keystore Client
Write-Host "CLIENT KEYSTORE"
keytool -genkey -alias test_client -keyalg RSA -keystore KeyStore-clt.p12 -keysize 4096 -storepass 123456 -dname "CN=forestJava Client, OU=client.forestjava.de, O=forestjava, L=Hagen, S=NRW, C=DE" -deststoretype pkcs12
openssl req -new -x509 -keyout ca-key-clt.pem -out ca-cert-clt.pem -subj "/CN=forestJava Client/OU=client.forestjava.de/O=forestjava/L=Hagen/ST=NRW/C=DE" -passout pass:12345678
keytool -keystore KeyStore-clt.p12 -alias test_client -certreq -file cert-file-clt.csr -storepass 123456
openssl x509 -req -CA ca-cert-clt.pem -CAkey ca-key-clt.pem -in cert-file-clt.csr -out cert-signed-clt.cer -days 365 -CAcreateserial -passin pass:12345678
keytool -keystore KeyStore-clt.p12 -alias CARoot -import -file ca-cert-clt.pem -storepass 123456 -noprompt
keytool -keystore KeyStore-clt.p12 -alias test_client -import -file cert-signed-clt.cer -storepass 123456 -noprompt

#Truststore Client
Write-Host "CLIENT TRUSTSTORE"
keytool -keystore TrustStore-clt.p12 -alias test_server -import -file ../server/cert-signed-srv.cer -storepass 123456 -deststoretype pkcs12 -noprompt

cd ..
cd server

#Truststore Server
Write-Host "SERVER TRUSTSTORE"
keytool -keystore TrustStore-srv.p12 -alias test_client -import -file ../client/cert-signed-clt.cer -storepass 123456 -deststoretype pkcs12 -noprompt

#Keystore Server, add test_server2 certificate
Write-Host "SERVER KEYSTORE ADD test_server2"
keytool -genkey -alias test_server2 -keyalg RSA -keystore KeyStore-srv.p12 -keysize 4096 -storepass 123456 -dname "CN=forestJava Server2, OU=server2.forestjava.de, O=forestjava, L=Hagen, S=NRW, C=DE" -deststoretype pkcs12
openssl req -new -x509 -keyout ca-key-srv-2nd.pem -out ca-cert-srv-2nd.pem -subj "/CN=forestJava Server2/OU=server2.forestjava.de/O=forestjava/L=Hagen/ST=NRW/C=DE" -passout pass:12345678
keytool -keystore KeyStore-srv.p12 -alias test_server2 -certreq -file cert-file-srv-2nd.csr -storepass 123456
openssl x509 -req -CA ca-cert-srv-2nd.pem -CAkey ca-key-srv-2nd.pem -in cert-file-srv-2nd.csr -out cert-signed-srv-2nd.cer -days 365 -CAcreateserial -passin pass:12345678
keytool -delete -noprompt -alias CARoot -keystore KeyStore-srv.p12 -storepass 123456
keytool -keystore KeyStore-srv.p12 -alias CARoot -import -file ca-cert-srv-2nd.pem -storepass 123456 -noprompt
keytool -keystore KeyStore-srv.p12 -alias test_server2 -import -file cert-signed-srv-2nd.cer -storepass 123456 -noprompt

cd ..
cd client
#Delete Truststore Client
Get-ChildItem TrustStore-clt.p12 -recurse | Where { ! $_.PSIsContainer } | Foreach-Object {Remove-Item $_.FullName}

#New Truststore Client
Write-Host "NEW CLIENT TRUSTSTORE"
keytool -keystore TrustStore-clt.p12 -alias test_server2 -import -file ../server/cert-signed-srv-2nd.cer -storepass 123456 -deststoretype pkcs12 -noprompt

cd ..

if (Test-Path mail -PathType Container) {
    rm mail -r -fo
}

mkdir mail
cd mail

#Mail Certificate
Write-Host "MAIL CERTIFICATE"
openssl req -new -x509 -keyout ca-key-mail.pem -out ca-cert-mail.pem -subj "/CN=forestJava Mail/OU=mail.forestjava.de/O=forestjava/L=Hagen/ST=NRW/C=DE" -passout pass:12345678
openssl rsa -in ca-key-mail.pem -out ca-key-mail.pem -passin pass:12345678
openssl req -new -key ca-key-mail.pem -out ca-sign-request-mail.csr -subj "/CN=forestJava Mail/OU=mail.forestjava.de/O=forestjava/L=Hagen/ST=NRW/C=DE"
openssl x509 -req -days 365 -in ca-sign-request-mail.csr -signkey ca-key-mail.pem -out ca-cert-mail.crt
# use .crt and *-key-*.pem file for mail server
# test connection with 'openssl s_client -connect ip:port'

#Truststore Mail
Write-Host "TRUSTSTORE MAIL"
keytool -keystore TrustStore-mail.p12 -alias test_mail -import -file ca-cert-mail.crt -storepass 123456 -deststoretype pkcs12 -noprompt

cd ..

mkdir all
cd all

#Truststore All
Write-Host "TRUSTSTORE ALL"
keytool -keystore TrustStore-all.p12 -alias test_client -import -file ../client/cert-signed-clt.cer -storepass 123456 -deststoretype pkcs12 -noprompt
keytool -keystore TrustStore-all.p12 -alias test_server -import -file ../server/cert-signed-srv.cer -storepass 123456 -deststoretype pkcs12 -noprompt
keytool -keystore TrustStore-all.p12 -alias test_server2 -import -file ../server/cert-signed-srv-2nd.cer -storepass 123456 -deststoretype pkcs12 -noprompt

$webRequest = [Net.WebRequest]::Create("https://httpbin.org")
try { $webRequest.GetResponse() } catch {}
$cert = $webRequest.ServicePoint.Certificate
$bytes = $cert.Export([Security.Cryptography.X509Certificates.X509ContentType]::Cert)
set-content -value $bytes -encoding byte -path "httpbin.org.cer"

keytool -keystore TrustStore-all.p12 -alias httpbin -import -file httpbin.org.cer -storepass 123456 -deststoretype pkcs12 -noprompt

cd ..