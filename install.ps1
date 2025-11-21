# ===============================
# CONFIGURATION
# ===============================
$CA_KEY       = "localCA.key"
$CA_CERT      = "localCA.pem"
$SERVER_KEY   = "localhost.key"
$SERVER_CSR   = "localhost.csr"
$SERVER_CERT  = "localhost.crt"
$SAN_FILE     = "san.txt"
$P12_FILE     = "localhost.p12"

$BACKEND_DIR  = "Backendp3"
$FRONTEND_DIR = "Frontendp3"

$JKS_FILE     = "$BACKEND_DIR\keystore.jks"
$VITE_KEY     = "$FRONTEND_DIR\vite.key"
$VITE_CERT    = "$FRONTEND_DIR\vite.crt"

$ALIAS = "localhost"
$TEMP_PASS = ".temp_password"

# ===============================
# FUNCTIONS
# ===============================

function Get-Password {
    Write-Host "Enter password for keystore (press Enter for default 'password'): " -NoNewline
    $Password = Read-Host -AsSecureString
    $Plain = [Runtime.InteropServices.Marshal]::PtrToStringAuto(
        [Runtime.InteropServices.Marshal]::SecureStringToBSTR($Password)
    )

    if ([string]::IsNullOrWhiteSpace($Plain)) {
        $Plain = "password"
        Write-Host "Using default password: password"
    } else {
        Write-Host "Password set."
    }

    $Plain | Out-File $TEMP_PASS -Encoding ASCII
}

function Ensure-Dir($dir) {
    if (-not (Test-Path $dir)) {
        Write-Host "Creating directory: $dir"
        New-Item -ItemType Directory -Path $dir | Out-Null
    }
}

# ===============================
# MAIN LOGIC
# ===============================

Write-Host "===== SSL INSTALLER (Windows) ====="

# --- Password ---
Get-Password
$STOREPASS = Get-Content $TEMP_PASS

# --- Create directories ---
Ensure-Dir $BACKEND_DIR
Ensure-Dir $FRONTEND_DIR

# ===============================
# CERTIFICATE GENERATION
# ===============================

Write-Host "Creating Local Certificate Authority..."
openssl genrsa -out $CA_KEY 2048

Write-Host "Creating CA certificate..."
openssl req -x509 -new -nodes -key $CA_KEY `
    -sha256 -days 3650 -out $CA_CERT `
    -subj "/C=DK/ST=Local/L=Local/O=LocalDev CA/CN=LocalDev CA"

Write-Host "Creating localhost private key..."
openssl genrsa -out $SERVER_KEY 2048

Write-Host "Creating certificate signing request..."
openssl req -new -key $SERVER_KEY -out $SERVER_CSR `
    -subj "/C=DK/ST=Local/L=Local/O=LocalDev/CN=localhost"

Write-Host "Signing localhost certificate..."
"subjectAltName=DNS:localhost,DNS:127.0.0.1" | Out-File $SAN_FILE -Encoding ASCII

openssl x509 -req -in $SERVER_CSR `
    -CA $CA_CERT -CAkey $CA_KEY -CAcreateserial `
    -out $SERVER_CERT -days 825 -sha256 -extfile $SAN_FILE

# ===============================
# BACKEND KEYSTORE
# ===============================

Write-Host "Creating PKCS#12 bundle..."
openssl pkcs12 -export `
    -inkey $SERVER_KEY -in $SERVER_CERT `
    -name $ALIAS `
    -out $P12_FILE -password pass:$STOREPASS

Write-Host "Creating Java keystore..."
keytool -importkeystore `
    -deststorepass $STOREPASS -destkeypass $STOREPASS -destkeystore $JKS_FILE `
    -srckeystore $P12_FILE -srcstoretype PKCS12 -srcstorepass $STOREPASS `
    -alias $ALIAS

# ===============================
# FRONTEND CERTIFICATES
# ===============================

Write-Host "Copying certs to frontend..."
Copy-Item $SERVER_KEY  $VITE_KEY  -Force
Copy-Item $SERVER_CERT $VITE_CERT -Force

# ===============================
# CLEANUP
# ===============================
Write-Host "Cleaning temporary files..."
Remove-Item $CA_KEY, $SERVER_KEY, $SERVER_CSR, $SERVER_CERT, $SAN_FILE, $P12_FILE, "localCA.srl", $TEMP_PASS -ErrorAction SilentlyContinue

Write-Host ""
Write-Host "Installation complete!"
Write-Host "Backend:  $JKS_FILE"
Write-Host "Frontend: $VITE_KEY, $VITE_CERT"
Write-Host ""
Write-Host "If using Firefox, import localCA.pem into Certificate Authorities."
Write-Host "======================================="
