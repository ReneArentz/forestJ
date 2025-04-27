If (Test-Path "TrustStore-mail.p12") {
    Remove-Item "TrustStore-mail.p12"
}

keytool -keystore TrustStore-mail.p12 -alias test_mail -import -file certificate.crt -storepass 123456 -deststoretype pkcs12 -noprompt