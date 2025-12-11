# Travail Pratique 2 : Authentification JWT

## Introduction

Ce projet a été réalisé dans le cadre d’un TP visant à comprendre et implémenter une authentification sécurisée basée sur **Spring Security** et **JSON Web Tokens (JWT)**.
L’objectif principal est de mettre en place :

* un **endpoint de connexion** (`/api/auth/login`) capable de générer un JWT valide,
* un **filtre d’autorisation** inspectant chaque requête entrante,
* une **protection des routes** nécessitant la présence d’un token valide,
* un système d’utilisateurs géré via **UserDetailsService**.

Ce projet constitue une base solide pour la construction d’API sécurisées dans un environnement Spring Boot moderne.

---

## I. **Structure du projet**

L’arborescence principale du projet est la suivante :

```
Authentication_Jwt
└── src
    ├── main
    │   ├── java
    │   │   └── org.example.authentication_jwt
    │   │       ├── config
    │   │       │   └── SecurityConfig.java
    │   │       ├── controller
    │   │       │   └── AuthController.java
    │   │       ├── filter
    │   │       │   └── JwtAuthFilter.java
    │   │       ├── model
    │   │       │   ├── AuthRequest.java
    │   │       │   └── AuthResponse.java
    │   │       └── service
    │   │           ├── JwtService.java
    │   │           ├── MyUserDetailsService.java
    │   │           └── AuthenticationJwtApplication.java
    │   └── resources
    │       └── application.properties
```

Chaque package est organisé pour respecter une architecture propre, claire et modulable :
`controller` pour les endpoints, `service` pour la logique métier, `filter` pour les inspections de requêtes, `config` pour la sécurité, etc.

---

## II. **Implémentation**

### **1. Configuration – `config/`**

### **SecurityConfig**

Cette classe configure Spring Security :

* **désactivation du CSRF** (utile pour une API REST),
* définition des routes publiques (`/api/auth/**`),
* obligation d’être authentifié pour toutes les autres routes,
* intégration du filtre personnalisé `JwtAuthFilter`,
* déclaration du `AuthenticationManager`.

Elle constitue le cœur du système de protection de l’API.

### **2. Contrôleur – `controller/`**

### **AuthController**

Expose deux endpoints :

✔ **POST /api/auth/login**

* prend un `username` et un `password`,
* vérifie les identifiants via `AuthenticationManager`,
* génère un JWT via `JwtService`,
* retourne un objet `AuthResponse` contenant le token.

✔ **GET /api/hello**

* endpoint protégé,
* nécessite un JWT valide dans l’en-tête `Authorization: Bearer <token>`.

### **3. Filtres – `filter/`**

### **JwtAuthFilter**

Ce filtre s’exécute **à chaque requête** (OncePerRequestFilter) :

1. vérifie si l’en-tête `Authorization` contient un `Bearer <token>`,
2. extrait et valide le JWT,
3. récupère l’utilisateur associé,
4. charge ses rôles depuis `UserDetailsService`,
5. place l’utilisateur dans le `SecurityContext`,
6. laisse la requête continuer si le token est valide.

Il assure ainsi l’autorisation des requêtes après la connexion.

### **4. Modèles – `model/`**

### **AuthRequest**

Représente la structure JSON envoyée lors du login :

```json
{
  "username": "...",
  "password": "..."
}
```

### **AuthResponse**

Réponse renvoyée après un login réussi :

```json
{
  "token": "..."
}
```

Des modèles simples mais essentiels pour communiquer avec Postman ou un frontend.

### **5. Services – `service/`**

### **JwtService**

Gère toute la logique liée au JWT :

* signature via une clé secrète,
* génération du token avec expiration,
* extraction du username,
* validation (signature + expiration).

Cette classe centralise la sécurité autour du token.

### **MyUserDetailsService**

Implémente `UserDetailsService` pour fournir des utilisateurs en mémoire :

* `user` avec rôle `USER`,
* `admin` avec rôle `ADMIN`.

C’est cette classe que Spring Security interroge pour authentifier un utilisateur lors du login.

---

## III. **Tests des Endpoints**

Les tests ont été effectués avec **Postman**.

### **1. Test du login**

**Endpoint :**

```
POST /api/auth/login
```

**Body (JSON) :**

```json
{
  "username": "user",
  "password": "password123"
}
```

**Résultat attendu :**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### **2. Test d’un endpoint protégé**

**Endpoint :**

```
GET /api/hello
```

**Header à ajouter :**

```
Authorization: Bearer <TOKEN_RECU>
```

**Réponse attendue :**

```json
{
  "message": "Bonjour, endpoint protégé OK "
}
```

Si le token est absent ou invalide → **403 Forbidden**
Si expiré → **403 Forbidden** avec message de refus du filtre.

---

## Conclusion

Ce TP a permis de mettre en place une authentification complète basée sur Spring Security et JWT.
Il illustre les concepts suivants :

* construction d’une API REST sécurisée,
* gestion d’utilisateurs via UserDetailsService,
* génération et validation de tokens JWT,
* filtrage des requêtes via un filtre personnalisé,
* sécurisation de routes et protection d’accès.

Le projet constitue une base solide pour des applications nécessitant une authentification stateless, comme des microservices ou des architectures orientées API.

---

## Réalisé par :

 - **Nom :** Wendbénédo Albéric Darius Konsebo
 - **Module :** Sécurité des Systèmes Distribués
 - **Encadré par :** M. Abdelmajid BOUSSELHAM
 - **Année académique :** 2025 - 2026