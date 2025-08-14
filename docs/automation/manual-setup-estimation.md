## Manual Setup Time Estimation

- Vous avez déjà un environnement (VMs prêtes, accès root, OS Linux type Ubuntu 22.04 ou CentOS Stream).

- Les configurations réseau et firewall sont déjà ouvertes.

- Vous êtes familiarisé avec chaque outil mais suivez une checklist précise sans automatisation complète.

| **#** | **Tâche**                                           | **Estimation (h)** | **Justification (sources et pratique)**                                                                                                                                                                                                                                                                                                  | **Automatable?** | **Status** | **Notes**                                         |
| ----- | --------------------------------------------------- | ------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- | ---------- | ------------------------------------------------- |
| 1     | **SonarQube setup**                                 | 2 - 3h             | - Installation binaire (30-45min) [Sonar Docs](https://docs.sonarsource.com/sonarqube/latest/setup/install-server/)<br>- Config `vm.max_map_count` + systemd (15min)<br>- Setup DB user & schema (15min)<br>- Change admin password, generate token, create webhook (30min)<br>- Users + permissions + quality gate + HTTPS config (1h). | ✅                | Partial    | Automated with Ansible role                       |
| 2     | **Nexus installation & setup**                      | 1.5 - 2h           | - Download & extract (10min) [Nexus Docs](https://help.sonatype.com/repomanager3/installation)<br>- Configure as service (20min)<br>- Initial admin setup + change password + create repos (1h)<br>- Optional HTTPS config (30min).                                                                                                      | ✅                | Not Yet    | Planned with Ansible + Docker                     |
| 3     | **Jenkins installation**                            | 1h                 | - Install Java + Jenkins package + start service (30-45min) [Jenkins Docs](https://www.jenkins.io/doc/book/installing/)<br>- Configure admin user, URL, basic security (15min).                                                                                                                                                          | ✅                | Done       | Automated with JCasC + Ansible + Docker           |
| 4     | **Plugin installation**                             | 0.5 - 1h           | - Using plugins.txt or UI [JCasC Plugin Config](https://github.com/jenkinsci/configuration-as-code-plugin).                                                                                                                                                                                                                              | ✅                | Done       | Automated via plugins.txt + Docker                |
| 5     | **Tools configuration (Maven, Sonar Scanner, JDK)** | 0.5h               | - Define Maven + SonarScanner + JDK in Jenkins global config (each \~10min).                                                                                                                                                                                                                                                             | ✅                | Not Yet    | Planned via JCasC                                 |
| 6     | **Secrets / credentials setup**                     | 1h                 | - Add Sonar token, git creds, docker creds, SMTP creds, SSH keys for agents (each 5-10min). [Jenkins Credentials Plugin](https://plugins.jenkins.io/credentials/)                                                                                                                                                                        | ✅                | Partial    | Some secrets automated, some manually configured  |
| 7     | **SonarQube integration with Jenkins**              | 0.5h               | - Configure Sonar Server + tool + test with pipeline. [Jenkins Sonar Plugin](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-jenkins/)                                                                                                                                                                                  | ✅                | Done       | Configured via JCasC                              |
| 8     | **Nexus integration with Jenkins**                  | 0.5h               | - Add repo URL + upload creds + pipeline snippet test. [Nexus Jenkins Integration](https://help.sonatype.com/repomanager3/integrations/nexus-repository-manager-jenkins-pipeline-integration)                                                                                                                                            | ✅                | Not Yet    | Planned in pipeline scripts                       |
| 9     | **Shared Library setup**                            | 0.5h               | - Define global shared library in Jenkins (Git repo, version, SCM config). [Shared Libraries Guide](https://www.jenkins.io/doc/book/pipeline/shared-libraries/)                                                                                                                                                                          | ✅                | Done       | Configured via JCasC                              |
| 10    | **Create folder job + pipelines + settings.xml**    | 1 - 2h             | - Create folder (5min)<br>- Create pipeline jobs (30-60min each)<br>- Configure settings.xml with repo credentials (15-30min).                                                                                                                                                                                                           | ✅                | Partial    | Configured via Ansible + Jenkins Pipeline         |
| 11    | **Nodes configuration (agents)**                    | 0.5 - 1h           | - If remote agents exist: add node, configure SSH, test connection. [Jenkins Agents Guide](https://www.jenkins.io/doc/book/using/using-agents/)                                                                                                                                                                                          | ✅                | Not Yet    | Linux agents automated; Windows pending           |
| 12    | **GitLab Runner setup & config**                    | 0.5 - 1h           | - Install GitLab Runner [GitLab Runner Install Docs](https://docs.gitlab.com/runner/install/)<br>- Register runner with token (10-15min).                                                                                                                                                                                                | ✅                | Not Yet       | Automated with Ansible                            |
| 13    | **Project GitLab CI pipeline & secrets creation**   | 0.5 - 1h           | - Define .gitlab-ci.yml pipelines (20-30min)<br>- Add secrets/variables in GitLab UI (15-30min). [GitLab CI/CD Docs](https://docs.gitlab.com/ee/ci/)                                                                                                                                                                                     | ❌                | Not Yet    | Pipelines scripted, secrets manual                |
| 14    | **Get App Service token for Jenkins from email**    | 0.25h              | - Retrieve token manually from service account email and store in Jenkins credentials (10-15min).                                                                                                                                                                                                                                        | ❌                | Not Yet    | Requires manual retrieval for security compliance |
                                                         


### 🔢 ⏱️ Temps total estimé
- ✅ Minimum : ~ 9 heures (si tout se déroule sans blocage)
- ✅ Maximum réaliste : ~ 13 heures (si latence réseau, configuration HTTPS, ou problèmes mineurs)

### ⚠️ Facteurs de variation

- Si vous utilisez Infrastructure as Code (Terraform + Ansible + JCasC) : le temps manuel sera réduit à 1-3h pour lancer et monitorer les pipelines.
- Si c’est la première fois que vous réalisez l’installation complète, comptez +25-50% de temps supplémentaire pour lecture et validation.
- Intégration avec Active Directory ou external secrets manager : ajouter +1-2h selon complexité.
- Si Nexus ou SonarQube nécessitent un reverse proxy nginx/traefik avec HTTPS certbot, ajouter +1h.


### ✔️ Status Breakdown:

| Status      | Count | Notes                    |
| ----------- | ----- | ------------------------ |
| **Done**    | 4     | Tasks: 3,4,7,9           |
| **Partial** | 3     | Tasks: 1,6,10            |
| **Not Yet** | 7     | Tasks: 2,5,8,11,12,13,14 |


📊 Méthode de calcul
Hypothèse pondérée (standard DevOps reporting – Atlassian source):

Done = 1 point

Partial = 0.5 point

Not Yet = 0 point

➗ Calcul détaillé
✅ Points achieved = (4 * 1) + (3 * 0.5) = 4 + 1.5 = 5.5

✅ Total possible points = 14

🔬 % Automation
(
5.5
/
14
)
∗
100
(5.5/14)∗100
= 39.3%


### manual steps left:
- get the email service token
- SonarQube:
    - define quality gates
    - users creation & permissions
- Jenkins:
    - configure .m2/settings.xml after the first build
    - configure the SMTP authentification for mailer plugin
- nexus:
    - create dedicated project