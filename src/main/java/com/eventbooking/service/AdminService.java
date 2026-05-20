package com.eventbooking.service;

import com.eventbooking.model.AdminAccount;
import com.eventbooking.util.AdminFileStore;
import com.eventbooking.util.PasswordHasher;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

public class AdminService {

    public static final String DEFAULT_ADMIN_EMAIL = "admin@lenscraft.studio";

    private final AdminFileStore store;

    public AdminService(AdminFileStore store) {
        this.store = store;
    }

    public Optional<String> adminEmail() throws IOException {
        return store.readAdmin().map(AdminAccount::getEmail);
    }

    public boolean isAdminEmail(String email) throws IOException {
        if (email == null || email.isBlank()) {
            return false;
        }
        Optional<String> admin = adminEmail();
        return admin.isPresent() && email.trim().equalsIgnoreCase(admin.get().trim());
    }

    public boolean authenticate(String email, char[] password) throws IOException, GeneralSecurityException {
        if (email == null || email.isBlank() || password == null || password.length == 0) {
            return false;
        }
        AdminAccount admin = store.readAdmin().orElse(null);
        if (admin == null) {
            return false;
        }
        if (!email.trim().equalsIgnoreCase(admin.getEmail())) {
            return false;
        }
        byte[] salt = PasswordHasher.fromBase64(admin.getSaltBase64());
        byte[] expected = PasswordHasher.fromBase64(admin.getPasswordHashBase64());
        return PasswordHasher.verify(password, salt, expected);
    }
}
