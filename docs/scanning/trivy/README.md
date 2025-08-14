# Trivy - Vulnerability and Secret Scanner

## Présentation

**Trivy** est un outil open-source développé par [Aqua Security](https://www.aquasec.com/), permettant de scanner :

- Les **images Docker / conteneurs**
- Le **système de fichiers** (projets locaux)
- Les **dépôts Git**
- Les fichiers **Infrastructure as Code** (Terraform, Kubernetes, CloudFormation)
- Les fichiers **SBOM** (Software Bill of Materials)
- Les **secrets** (clés API, mots de passe, tokens)

Trivy aide à détecter les vulnérabilités connues et les secrets exposés dans vos projets.

---

## Fonctionnement de Trivy

1. **Analyse du type de cible** (image, dossier, repo, IaC)  
2. **Téléchargement et mise à jour automatique** de sa base de données de vulnérabilités, provenant de sources fiables comme le NVD, GitHub Security Advisories, et les fournisseurs OS (Debian, Ubuntu, Alpine…)  
3. **Scan des paquets et fichiers de dépendances** (ex : `pom.xml`, `package-lock.json`) pour détecter les vulnérabilités (CVE)  
4. **Détection des secrets** via des motifs regex prédéfinis  
5. **Génération de rapports** au format tableau, JSON ou SARIF pour intégration CI/CD

---

## Qu’est-ce qu’un CVE ?

- **CVE (Common Vulnerabilities and Exposures)** est un identifiant standardisé pour les vulnérabilités de sécurité publiquement connues, géré par [MITRE](https://cve.mitre.org/) et référencé dans la [NVD](https://nvd.nist.gov/).  
- Trivy utilise ces CVEs pour informer précisément sur les vulnérabilités présentes dans les dépendances de votre projet (package, version affectée, sévérité, correctif disponible).

---

## Utilisation basique de Trivy

### Scanner une image Docker

```bash
trivy image nginx:latest
```

### Scanner un dossier local (fichiers et dépendances)
```bash
trivy fs /chemin/vers/projet
```

### Scanner les secrets dans un projet
```bash
trivy fs --scanners secret /chemin/vers/projet
```

## Intégration avec GitLab CI
Exemple simple d’étape dans .gitlab-ci.yml pour scanner une image :

```bash
trivy_scan:
  image:
    name: aquasec/trivy:latest
    entrypoint: [""]
  stage: scan
  variables:
    TRIVY_EXIT_CODE: "0" # Set to 1 to fail pipeline on vulnerabilities
    TRIVY_SEVERITY: "CRITICAL,HIGH" # Scan for High and Critical vulnerabilities
  script:
    - trivy fs --exit-code $TRIVY_EXIT_CODE --severity $TRIVY_SEVERITY nginx:latest
```

## Bonnes pratiques
- Toujours éviter d’inclure des secrets en dur dans le code
- Utiliser des fichiers de dépendances standards pour que Trivy puisse détecter les vulnérabilités
- Mettre à jour régulièrement Trivy et sa base de données
- Compléter Trivy avec des outils SAST pour analyser le code source (ex : SonarQube, Semgrep)