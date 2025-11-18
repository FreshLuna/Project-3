# Makefile for generating SSL files with auto-cleanup and password prompt

# ===============================
# CONFIGURATION
# ===============================
CA_KEY := localCA.key
CA_CERT := localCA.pem
SERVER_KEY := localhost.key
SERVER_CSR := localhost.csr
SERVER_CERT := localhost.crt
SAN_FILE := san.txt
P12_FILE := localhost.p12

# Target directories - FIXED PATHS
BACKEND_DIR := Backendp3
FRONTEND_DIR := Frontendp3
JKS_FILE := $(BACKEND_DIR)/keystore.jks
VITE_KEY := $(FRONTEND_DIR)/vite.key
VITE_CERT := $(FRONTEND_DIR)/vite.crt

# Keystore settings - will be set by user input
ALIAS := localhost

.PHONY: all clean help

all: install

install: get-password $(JKS_FILE) $(VITE_KEY) $(VITE_CERT)
	@echo "Cleaning up temporary files..."
	@rm -f $(CA_KEY) $(SERVER_KEY) $(SERVER_CSR) $(SERVER_CERT) $(SAN_FILE) $(P12_FILE) localCA.srl .temp_password
	@echo "Installation complete!"
	@echo "Backend:  $(JKS_FILE)"
	@echo "Frontend: $(VITE_KEY), $(VITE_CERT)"

# Get password from user
get-password:
	@echo "Enter password for keystore (or press Enter for default 'password'):"; \
	read -s pwd; \
	if [ -z "$$pwd" ]; then \
		STOREPASS="password"; \
		echo "Using default password: password"; \
	else \
		STOREPASS="$$pwd"; \
		echo "Password set."; \
	fi; \
	echo "$$STOREPASS" > .temp_password

# Create target directories
$(BACKEND_DIR) $(FRONTEND_DIR):
	@echo "Creating directory: $@"
	mkdir -p $@

# ===============================
# CERTIFICATE GENERATION
# ===============================

# First: Generate CA
$(CA_KEY):
	@echo "Creating Local Certificate Authority..."
	openssl genrsa -out "$(CA_KEY)" 2048

$(CA_CERT): $(CA_KEY)
	@echo "Creating CA certificate..."
	openssl req -x509 -new -nodes -key "$(CA_KEY)" \
	  -sha256 -days 3650 -out "$(CA_CERT)" \
	  -subj "/C=DK/ST=Local/L=Local/O=LocalDev CA/CN=LocalDev CA"

# Second: Generate server certificate
$(SERVER_KEY):
	@echo "Creating localhost private key..."
	openssl genrsa -out "$(SERVER_KEY)" 2048

$(SERVER_CSR): $(SERVER_KEY)
	@echo "Creating certificate signing request..."
	openssl req -new -key "$(SERVER_KEY)" -out "$(SERVER_CSR)" \
	  -subj "/C=DK/ST=Local/L=Local/O=LocalDev/CN=localhost"

$(SERVER_CERT): $(SERVER_CSR) $(CA_CERT) $(CA_KEY)
	@echo "Signing localhost certificate..."
	@echo "subjectAltName=DNS:localhost,DNS:127.0.0.1" > "$(SAN_FILE)"
	openssl x509 -req -in "$(SERVER_CSR)" \
	  -CA "$(CA_CERT)" -CAkey "$(CA_KEY)" -CAcreateserial \
	  -out "$(SERVER_CERT)" -days 825 -sha256 -extfile "$(SAN_FILE)"

# ===============================
# BACKEND KEYSTORE
# ===============================
$(P12_FILE): $(SERVER_KEY) $(SERVER_CERT)
	@STOREPASS=$$(cat .temp_password); \
	echo "Creating PKCS#12 bundle..."; \
	openssl pkcs12 -export \
	  -inkey "$(SERVER_KEY)" -in "$(SERVER_CERT)" \
	  -name "$(ALIAS)" \
	  -out "$(P12_FILE)" -password pass:$$STOREPASS

$(JKS_FILE): $(P12_FILE) | $(BACKEND_DIR)
	@STOREPASS=$$(cat .temp_password); \
	echo "Creating Java keystore for backend..."; \
	keytool -importkeystore \
	  -deststorepass "$$STOREPASS" -destkeypass "$$STOREPASS" -destkeystore "$(JKS_FILE)" \
	  -srckeystore "$(P12_FILE)" -srcstoretype PKCS12 -srcstorepass "$$STOREPASS" \
	  -alias "$(ALIAS)"; \
	echo "Backend keystore created: $(JKS_FILE)"

# ===============================
# FRONTEND CERTIFICATES
# ===============================
$(VITE_KEY): $(SERVER_KEY) | $(FRONTEND_DIR)
	@echo "Copying key to frontend..."
	cp "$(SERVER_KEY)" "$(VITE_KEY)"
	@echo "Frontend key created: $(VITE_KEY)"

$(VITE_CERT): $(SERVER_CERT) | $(FRONTEND_DIR)
	@echo "Copying certificate to frontend..."
	cp "$(SERVER_CERT)" "$(VITE_CERT)"
	@echo "Frontend certificate created: $(VITE_CERT)"

# ===============================
# MESSAGE
# ===============================

	@echo "if running localhost and using firefox remember to copy localCA.pem into firefox permissions"

# ===============================
# CLEANUP
# ===============================
uninstall:
	@echo "Removing installed files..."
	rm -f $(JKS_FILE) $(VITE_KEY) $(CA_CERT) $(VITE_CERT) .temp_password
	@echo "Clean complete."