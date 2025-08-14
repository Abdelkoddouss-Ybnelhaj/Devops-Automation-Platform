## C'est quoi Jmeter ?

JMeter est un outil open-source développé par la Fondation Apache. Il permet de simuler plusieurs utilisateurs virtuels envoyant des requêtes à une application web/API/etc.


## 📌 Caractéristiques clés :
- Gratuit, open-source.
- Interface graphique et exécution en ligne de commande.
- Prend en charge HTTP, HTTPS, FTP, JDBC, WebServices, SOAP, REST, etc.
- Génère des rapports détaillés (HTML).
- Extensible avec des plugins.

## 📦 Installation Jmeter

```bash
# 1. Télécharger
wget https://downloads.apache.org//jmeter/binaries/apache-jmeter-5.6.3.tgz

# 2. Extraire
tar -xvzf apache-jmeter-5.6.3.tgz

# 3. Lancer
cd apache-jmeter-5.6.3/bin
./jmeter
```

## 📋 Composants visibles :

| Élément             | Rôle                                                |
| ------------------- | --------------------------------------------------- |
| **Test Plan**       | Contient tous les composants.                       |
| **Thread Group**    | Simule les utilisateurs (nombre, ramp-up, boucles). |
| **Samplers**        | Type de requêtes envoyées (HTTP, JDBC…).            |
| **Listeners**       | Rapports de résultats (tableaux, graphiques…).      |
| **Timers & Config** | Contrôle du temps et config des requêtes.           |


## ✅ 1.1 Test Plan
### 📘 Définition :
- Le Test Plan est le conteneur principal de tous les éléments dans JMeter. Il représente le scénario de test complet.

### 📌 Ce qu’il contient :

- Thread Groups
- Config Elements
- Samplers
- Listeners
- Logic Controllers, etc.

**🎯 Exemple :**
Imaginez le Test Plan comme une recette de cuisine. Chaque ingrédient (Thread Group, Sampler…) joue un rôle pour "préparer" le test.

## ✅ 1.2 Thread Group (Groupe de Threads)
### 📘 Définition :
- Le Thread Group définit le nombre d’utilisateurs virtuels, la montée en charge, et la durée du test.

### ⚙️ Paramètres clés :
| Paramètre                       | Description                                                |
| ------------------------------- | ---------------------------------------------------------- |
| **Number of Threads (users)**   | Combien d’utilisateurs virtuels (simulés).                 |
| **Ramp-Up Period (in seconds)** | Combien de temps pour lancer tous les utilisateurs.        |
| **Loop Count**                  | Nombre de fois que chaque utilisateur exécute le scénario. |

**🎯 Exemple :**
10 threads + 10 sec de ramp-up = 1 utilisateur toutes les secondes.


## ✅ 1.3 Sampler
### 📘 Définition :
- Les Samplers définissent le type de requêtes que les threads vont exécuter : HTTP, JDBC, FTP, SOAP, etc.

### 📌 Types courants :
- HTTP Request – pour tester une API REST ou site web.
- JDBC Request – pour tester une base de données.
- SOAP/XML-RPC Request – pour tester des web services.

**🎯 Exemple :**
Un Sampler "HTTP Request" peut envoyer une requête GET sur https://api.example.com/users.


## ✅ 1.4 Listeners
### 📘 Définition :
- Les Listeners affichent les résultats du test sous différentes formes : graphiques, tableaux, fichiers, etc.

### 📌 Types utiles :
| Listener              | Rôle                                             |
| --------------------- | ------------------------------------------------ |
| **View Results Tree** | Affiche les détails de chaque requête.           |
| **Summary Report**    | Affiche moyennes, erreurs, taux de réussite.     |
| **Aggregate Report**  | Version plus avancée avec percentiles.           |
| **Graph Results**     | Visualise graphiquement la latence, temps moyen. |

## ✅ 1.5 Config Elements (Éléments de Configuration)
### 📘 Définition :
- Les Config Elements définissent des valeurs de configuration utilisées par les Samplers, comme l’URL de base, les paramètres, headers, données CSV.

### 📌 Éléments communs :
- HTTP Request Defaults : pour éviter de répéter l’URL à chaque requête.
- CSV Data Set Config : injecter des données dynamiques.
- HTTP Header Manager : ajouter des headers comme Authorization.

## ✅ 1.6 Assertions
### 📘 Définition :
- Les Assertions permettent de valider les réponses (code, contenu, JSON, etc.).

### 📌 Types :

| Assertion                   | Exemple d'usage                                  |
| --------------------------- | ------------------------------------------------ |
| **Response Code Assertion** | Attendre un `200 OK`.                            |
| **Response Assertion**      | Chercher un texte dans la réponse (`"success"`). |
| **JSON Assertion**          | Vérifier une clé JSON comme `"status": "ok"`.    |


## ✅ 1.7 Timers
### 📘 Définition :
- Les Timers permettent d’ajouter des délais (think time) entre les requêtes pour simuler un comportement humain.

### 📌 Types :
- Constant Timer : ajoute X ms avant chaque requête.
- Gaussian Random Timer : délai aléatoire autour d’une moyenne.
- Uniform Random Timer : délai aléatoire dans un intervalle fixe.


## ✅ 1.8 Logic Controllers
### 📘 Définition :
- Les Logic Controllers définissent la logique d’exécution des requêtes, comme des conditions if, des boucles, ou des regroupements.

### 📌 Types utiles :

| Controller                 | Usage                                                        |
| -------------------------- | ------------------------------------------------------------ |
| **Loop Controller**        | Répéter un sous-ensemble de requêtes.                        |
| **If Controller**          | Exécuter une requête si une condition est vraie.             |
| **Transaction Controller** | Regrouper plusieurs requêtes comme un seul "bloc mesurable". |


## 🎯 Résumé Visuel
```bash
Test Plan
│
├── Thread Group (10 users, 5 loops)
│   ├── HTTP Request → GET /login
│   ├── HTTP Request → POST /dashboard
│   ├── Response Assertion → status == 200
│   ├── View Results Tree
│   └── Constant Timer (1s delay)
│
└── HTTP Header Manager → Authorization: Bearer {{token}}
```


## ✅ 2.1 PARAMÉTRAGE DYNAMIQUE – CSV Data Set Config

### 🎯 Objectif :
- Lire dynamiquement des données (par exemple : emails, ids, etc.) depuis un fichier CSV pour simuler des requêtes variées.

### 📁 Exemple de fichier users.csv
```bash
id,email
2,janet.weaver@reqres.in
3,emma.wong@reqres.in
```

### 🧱 Étapes :
**> Ajouter le fichier users.csv dans le même dossier que le .jmx.**

**> Ajouter le CSV Config :**

- Clic droit sur Thread Group
→ Add → Config Element → CSV Data Set Config

**> Configurer :**

| Champ               | Valeur        |
| ------------------- | ------------- |
| Filename            | `users.csv`   |
| Variable Names      | `id,email`    |
| Delimiter           | `,`           |
| Recycle on EOF      | `True`        |
| Stop thread on EOF? | `False`       |
| Sharing Mode        | `All threads` |


## ✅ 3.1 LOGIC CONTROLLERS — Contrôle de flux et regroupements

### 🎯 Objectif :
- Simuler des scénarios réalistes avec boucles, conditions et regroupements.


**🔁 Loop Controller**
---
- Permet de répéter un sous-ensemble d’actions.

**➕ Ajouter :**

- Clic droit sur Thread Group
→ Add → Logic Controller → Loop Controller

| Paramètre  | Valeur |
| ---------- | ------ |
| Loop Count | `3`    |

→ Glissez dedans vos requêtes (ex: GET /users/${id})

**🤔 If Controller**
---
- Permet d’exécuter une requête seulement si une condition est vraie.

**➕ Ajouter :**

- Clic droit sur Thread Group → Add → Logic Controller → If Controller

→ Cela exécute le contenu du bloc seulement si id == 2.

## 🔄 Transaction Controller
Regroupe plusieurs requêtes comme une transaction unique pour mesurer leur temps combiné.

**➕ Ajouter :**

- Thread Group → Add → Logic Controller → Transaction Controller

→ Cochez “Generate parent sample” pour avoir des mesures agrégées.

## ✅ 4.1 ASSERTIONS — Vérification avancée
### 🎯 Objectif :
- S’assurer que la réponse est correcte (statut, contenu, format JSON...).

**✅ Response Assertion**

- Vérifie que la réponse contient un mot/valeur.

**➕ Ajouter :**

- Sampler → Add → Assertions → Response Assertion

| Champ                 | Valeur          |
| --------------------- | --------------- |
| Response Field        | `Response Body` |
| Pattern Matching Rule | `Contains`      |
| Patterns to Test      | `email`         |

**📦 JSON Assertion (Plugin requis)**
Permet de valider une clé JSON dans la réponse, exemple :