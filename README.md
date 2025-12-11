# Travail Pratique 2 : Authentification JWT

## Introduction


---

## Structure du projet

```
Authentication_Jwt
└── src
    ├── main
    │   ├── java
    │   │   └── org.example.authentication_jwt
    │   │       ├── config
    │   │       │   └── SecurityConfig.java          # Configuration Spring Security
    │   │       ├── controller
    │   │       │   └── AuthController.java         # Endpoint /auth/login
    │   │       ├── filter
    │   │       │   └── JwtAuthFilter.java          # Interception + validation du token JWT
    │   │       ├── model
    │   │       │   ├── AuthRequest.java            # Objet d'entrée : username/password
    │   │       │   └── AuthResponse.java           # Objet de sortie : JWT token
    │   │       └── service
    │   │           ├── JwtService.java             # Génération + validation du token JWT
    │   │           ├── MyUserDetailsService.java   # Chargement des utilisateurs
    │   │           └── AuthenticationJwtApplication.java # Main
    │   └── resources
    │       └── application.properties              # Configuration
```

---

## Conclusion

## Réalisé par :

 - **Nom :** Wendbénédo Albéric Darius Konsebo
 - **Module :** Sécurité des Systèmes Distribués
 - **Encadré par :** M. Abdelmajid BOUSSELHAM
 - **Année académique :** 2025 - 2026